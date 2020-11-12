package com.ticket_to_ride.client.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.client.model.RouteConnector;
import com.ticket_to_ride.client.presenter.GameBoardPresenter;
//import com.ticket_to_ride.client.services.MockServiceCaller;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.RouteM;
import com.ticket_to_ride.common.util.RouteMoveMatcher;

import java.util.Map;
import java.util.TreeMap;

// Jared
// Jayden

public class GameBoardActivity extends AppCompatActivity implements IGameBoardActivity {
    private ImageView mImageView;
    private Map<Integer, RouteConnector> mRoutes = new TreeMap<>();
    private Map<RouteID, RouteConnector> mServerRoutes = new TreeMap<>();
    private Button mChatButton;
    private ChatFragment mChatFragment;
    private View mChatView;
    private DestCardSelectionFragment mDestCardSelectionFragment;
    private TextView mPlayerTurnIndicator;
    private GameStatusFragment mGameStatusFragment;
    private Button mGameStatusButton;
    private Button mClaimRouteButton;
    private TextView mClaimRouteInfo;
    private MoveID mChosenMove;
    private static final int[] sRedRoutes = {3, 16, 34, 45, 52, 56};
    private static final int[] sOrangeRoutes = {8, 27, 41, 43, 49, 55};
    private static final int[] sYellowRoutes = {4, 9, 25, 29, 46};
    private static final int[] sGreenRoutes = {11, 15, 21, 37, 42, 47, 54};
    private static final int[] sBlueRoutes = {7, 23, 39, 48};
    private static final int[] sPinkRoutes = {12, 26, 40, 51, 57};
    private static final int[] sBlackRoutes = {13, 14, 18, 28, 44, 53};
    private static final int[] sWhiteRoutes = {10, 17, 22, 38, 50, 58};
    private static final int[] sWildRoutes = {1, 2, 5, 6, 19, 20, 30, 31, 32, 33, 35, 36};
    private GameBoardPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        mImageView = (ImageView) findViewById(R.id.map_view);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeClaimUnavailable();
            }
        });

        mChatButton = (Button) findViewById(R.id.show_chat_button);
        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeClaimUnavailable();
                presenter.respondOpenChat();
            }
        });
        mGameStatusButton = findViewById(R.id.gameStatusButton);
        mGameStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeClaimUnavailable();
                presenter.respondOpenGameStatus();
            }
        });

        mClaimRouteButton = findViewById(R.id.claim_route_button);
        mClaimRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.claimRoute(mChosenMove);
            }
        });

        mClaimRouteInfo = findViewById(R.id.route_info_text);

        mPlayerTurnIndicator = (TextView) findViewById(R.id.player_turn_text);
        mPlayerTurnIndicator.setText(R.string.player_turn_text);
        mPlayerTurnIndicator.setVisibility(View.INVISIBLE);

        mChatView = findViewById(R.id.chat_fragment_container);
        mChatView.setVisibility(View.INVISIBLE);

        FragmentManager fm = getSupportFragmentManager();
        if (mChatFragment == null) {
            mChatFragment = new ChatFragment();
            fm.beginTransaction().add(R.id.chat_fragment_container, mChatFragment).commit();
        }


//TODO This all needs to wait until we get the map info from the server
        //TODO The client "should" have the map by the time it get to this activity.
        //Modified by Lance
        for (RouteM serverRoute : ClientM.get().getActiveGame().getMap().getRoutes()) {
            Resources res = getResources();
            int id = res.getIdentifier(serverRoute.getId().toString(), "id", this.getPackageName());
            ImageView route = (ImageView) findViewById(id);
            route.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView view = (ImageView)v;
                    makeClaimAvailable(view);
                }
            });
            mRoutes.put(id, new RouteConnector(route,serverRoute));
            RouteConnector con = (RouteConnector)mRoutes.get(id);
            mServerRoutes.put(con.getServerRoute().getId(), con);
            route.setColorFilter(con.getRouteColor(), PorterDuff.Mode.OVERLAY);
        }

        presenter = new GameBoardPresenter(this);

        presenter.respondOpenDestCard();
    }

    @Override
    public void showChat() {
        slideViewUp(mChatView);
    }

    @Override
    public void hideChat() {
        slideViewDown(mChatView);
    }

    @Override
    public void closeDestCardSelectionView() {
        FragmentManager fm = getSupportFragmentManager();
        if (mDestCardSelectionFragment != null) {
            fm.beginTransaction().remove(mDestCardSelectionFragment).commit();
        }
        setGameBoardViewsVisible(View.VISIBLE);
        presenter.toggleObserver(true);
        presenter.togglePlayerTurnIndicator(true);
    }

    @Override
    public void openDestCardSelectionView() {
        presenter.toggleObserver(false);
        FragmentManager fm = getSupportFragmentManager();
        if (mGameStatusFragment != null) {
            presenter.respondCloseGameStatus();
        }
        if (mDestCardSelectionFragment == null) {
            mDestCardSelectionFragment = DestCardSelectionFragment.newInstance(2);
        } else {
            Bundle args = new Bundle();
            args.putInt("minNumDestCards", 1);
            mDestCardSelectionFragment.setArguments(args);
        }
        fm.beginTransaction().add(R.id.game_board_full_screen_fragment,
                mDestCardSelectionFragment).commit();

        setGameBoardViewsVisible(View.INVISIBLE);
        presenter.togglePlayerTurnIndicator(false);
    }

    @Override
    public void closeGameStatusView() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().remove(mGameStatusFragment).commit();

        toggleChatFragment(true);
        presenter.toggleObserver(true);
        presenter.togglePlayerTurnIndicator(true);
        highlightRoutes(ClientM.get().getClaimedRoutes());
    }

    @Override
    public void openGameStatusView() {
        presenter.toggleObserver(false);
        FragmentManager fm = getSupportFragmentManager();
        if (mGameStatusFragment == null) {
            mGameStatusFragment = GameStatusFragment.newInstance();
        }
        fm.beginTransaction().add(R.id.game_board_full_screen_fragment,
                mGameStatusFragment).commit();

        toggleChatFragment(false);
        presenter.togglePlayerTurnIndicator(false);
    }

    private void slideViewUp(@NonNull View view) {
        view.setVisibility(View.VISIBLE);
        ViewCompat.setTranslationZ(view, 20);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void slideViewDown(@NonNull View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveChatToBack();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
    }

    private void moveChatToBack() {
        ViewCompat.setTranslationZ(mChatView, 0);
    }

    private void makeClaimAvailable(ImageView imageView) {
        //TODO: store route information and check viability of claiming route
        int id = imageView.getId();
        RouteConnector con = mRoutes.get(id);
        String info = con.getServerRoute().getCityM1().getName();
        info += " to ";
        info += con.getServerRoute().getCityM2().getName();
        info += "\n" + con.getServerRoute().getRouteColor().toString();
        mClaimRouteInfo.setText(info);
        if(presenter.canClaimRoute(con.getMoveID())) {
            mClaimRouteButton.setEnabled(true);
            mChosenMove = con.getMoveID();
        }
        else {
            mClaimRouteButton.setEnabled(false);
        }
        mClaimRouteInfo.setVisibility(View.VISIBLE);
        mClaimRouteButton.setVisibility(View.VISIBLE);
    }

    private void makeClaimUnavailable () {
        mClaimRouteButton.setVisibility(View.GONE);
        mClaimRouteInfo.setVisibility(View.INVISIBLE);
    }

        // toggle visibility of gameBoard View items
    private void setGameBoardViewsVisible(int visible) {
        mChatButton.setVisibility(visible);
        mImageView.setVisibility(visible);
        mGameStatusButton.setVisibility(visible);
        for (Map.Entry<Integer, RouteConnector> entry :  mRoutes.entrySet()) {
            entry.getValue().getDisplayRoute().setVisibility(visible);
        }
    }

    private void toggleChatFragment(boolean open) {
        FragmentManager fm = getSupportFragmentManager();
        if (open) {
            if (mChatFragment == null) {
                mChatFragment = new ChatFragment();
            }
            fm.beginTransaction().add(R.id.chat_fragment_container, mChatFragment).commit();
            setGameBoardViewsVisible(View.VISIBLE);
        }
        else {
            setGameBoardViewsVisible(View.INVISIBLE);
            fm.beginTransaction().remove(mChatFragment).commit();
        }
    }

    @Override
    public void hideTurn() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPlayerTurnIndicator.setVisibility(View.INVISIBLE);
            }
        });    }

    @Override
    public void showTurn() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPlayerTurnIndicator.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void disableClaimRoute() {
        mClaimRouteButton.setEnabled(false);
    }

    @Override
    public void enableClaimRoute() {
        mClaimRouteButton.setEnabled(true);
    }

    @Override
    public void highlightRoutes(Map<RouteID, PlayerInfoM> claimedRoutes) {
        for(Map.Entry<RouteID, PlayerInfoM> entry : claimedRoutes.entrySet())
        {
            RouteID key = entry.getKey();
            PlayerInfoM player = entry.getValue();
            RouteConnector con = mServerRoutes.get(key);
            if(con.isClaimed()) {
                continue;
            }
            con.setClaimed(true);
            int color;
            switch (player.getColor()) {
                case RED:
                    color = Color.RED;
                    break;
                case BLUE:
                    color = Color.BLUE;
                    break;
                case GREEN:
                    color = Color.GREEN;
                    break;
                case YELLOW:
                    color = Color.YELLOW;
                default:
                    color = Color.rgb(199, 47, 179);
            }
            ImageView route = con.getDisplayRoute();
            route.setColorFilter(color, PorterDuff.Mode.OVERLAY);
            String tag = route.getTag().toString();
            int routeLength = Integer.parseInt(tag);
            switch (routeLength) {
                case 1:
                    route.setImageResource(R.mipmap.claimed1);
                    break;
                case 2:
                    route.setImageResource(R.mipmap.claimed2);
                    break;
                case 3:
                    route.setImageResource(R.mipmap.claimed3);
                    break;
                case 4:
                    route.setImageResource(R.mipmap.claimed4);
                    break;
                case 5:
                    route.setImageResource(R.mipmap.claimed5);
                    break;
                case 6:
                    route.setImageResource(R.mipmap.claimed6);
            }
        }
    }

    @Override
    public void openGameOverView() {
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
    }

    public void displayToast(String msg) {
        final String message = msg;
        final Context context = this;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });

    }
}
