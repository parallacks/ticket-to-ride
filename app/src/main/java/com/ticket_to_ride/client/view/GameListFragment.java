package com.ticket_to_ride.client.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ticket_to_ride.client.presenter.GameListPresenter;
import com.ticket_to_ride.client.model.GameInfoM;

import java.util.ArrayList;
import java.util.List;

// Jared

public class GameListFragment extends Fragment {

    private IGameWaiting gameWaitingActivity;
    private Button logoutBtn;
    private Button joinGameBtn;
    private Button createGameBtn;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<TextView> gameTexts;

    private GameListPresenter gameListPresenter;
    // Other variables for recycler view for game list

    public GameListFragment() {
        gameTexts = new ArrayList<>();
    }

    public static GameListFragment newInstance() {
        GameListFragment fragment = new GameListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_game_list, container, false);

        logoutBtn = view.findViewById(R.id.logoutBtn);
        joinGameBtn = view.findViewById(R.id.joinGameBtn);
        createGameBtn = view.findViewById(R.id.createGameBtn);

        recyclerView = view.findViewById(R.id.game_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        gameListPresenter = new GameListPresenter(this);

        setButtonListeners();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameWaitingActivity = (IGameWaiting) context;

    }

    private void setButtonListeners() {
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutBtnPressed();
            }
        });

        joinGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGameBtnPressed();
            }
        });

        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGameBtnPressed();
            }
        });
    }

    // Respond to logout button press by alerting presenter
    public void logoutBtnPressed() {
        gameListPresenter.respondLogout();
    }

    public void joinGameBtnPressed() {
        gameListPresenter.respondJoinGame();
    }

    public void createGameBtnPressed() {
        gameListPresenter.respondCreateGame();
    }

    public void displayErrorMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void displaySuccessMsg(String msg) {
        final String message = msg;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    public void setJoinGameBtn(boolean state) {
        joinGameBtn.setEnabled(state);
    }

    public void switchFragments() {
        gameWaitingActivity.switchToGameLobby();
    }


    public void updateGameListItems(List<GameInfoM> items) {
        System.out.println("Calling updateGameLIstItems\n");
        gameTexts.clear();
        adapter = new Adapter(getActivity(), items);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(adapter);
            }
        });
    }


    // Adaptor for the recycler view
    class Adapter extends RecyclerView.Adapter<Holder> {

        private List<GameInfoM> items;
        private LayoutInflater inflater;

        public Adapter(Context context, List<GameInfoM> items) {
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.game_list_item, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            GameInfoM item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

    }

    // Holds the information for each game list item
    class Holder extends RecyclerView.ViewHolder {

        // Display text and description for each setting
        private TextView gameInfo;
        private LinearLayout clickableGameNameArea;
        private GameInfoM item;

        public Holder(View view) {
            super(view);
            gameInfo = view.findViewById(R.id.gameListName);
            clickableGameNameArea = view.findViewById(R.id.clickableGameListItem);
        }

        void bind(GameInfoM item) {
            this.item = item;
            gameInfo.setText(this.item.getGameName());
            gameTexts.add(gameInfo);
            clickableGameNameArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameListPresenter.updateSelected(Holder.this.item);
                }

            });
            gameListPresenter.showSelected();

        }
    }

    public void highlightText(String selectedGame) {
        for (TextView text : gameTexts) {
            if (text.getText().toString().equals(selectedGame)) {
                text.setTextColor(ResourcesCompat.getColor(getResources(),
                        R.color.superRed, null));
            } else {
                text.setTextColor(ResourcesCompat.getColor(getResources(),
                        R.color.superBlack, null));
            }
        }
    }
}
