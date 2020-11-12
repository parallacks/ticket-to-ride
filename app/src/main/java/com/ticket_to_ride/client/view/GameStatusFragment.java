package com.ticket_to_ride.client.view;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.client.presenter.GameStatusPresenter;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.common.model.TrayM;

import java.util.ArrayList;
import java.util.List;



public class GameStatusFragment extends Fragment implements IGameStatusView {

    private IGameBoardActivity mListener;
    private GameStatusPresenter gameStatusPresenter;

    private Button trainCardDeckBtn;
    private View myStatsLayout;
    private TextView myStatsTitleText;
    private TextView myScoreText;
    private TextView myDestCardNumText;
    private TextView myCarsLeftNumText;
    private TextView myTrainCardsNumText;
    private Button destCardDeckBtn;
    private RecyclerView opponentStatsRecyclerView;
    private RecyclerView faceUpTCardsRecyclerView;
    private RecyclerView myTCardNumsRecyclerView;
    private RecyclerView myDestCardInfoRecyclerView;
    private FaceUpTCardAdapter faceUpTCardAdapter;
    private TrainCardNumbersAdapter trainCardNumbersAdapter;
    private OpponentStatsAdapter opponentStatsAdapter;
    private DestCardInfoAdapter destCardInfoAdapter;
    private Button closeFragmentBtn;
    private boolean grayOutWildCards = false;

//    //TODO: delete this (if we ever get the communication from server working)
//    private Button gameOverTestButton;


    public GameStatusFragment() {
        // Required empty public constructor
    }

    public static GameStatusFragment newInstance() {
        GameStatusFragment fragment = new GameStatusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_status,
                container, false);

        trainCardDeckBtn = view.findViewById(R.id.drawTrainCardDeckBtn);
        destCardDeckBtn = view.findViewById(R.id.destCardDeckBtn);
        closeFragmentBtn = view.findViewById(R.id.closeBtn);

        opponentStatsRecyclerView = view.findViewById(R.id.player_stats_recycler_view);
        opponentStatsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        faceUpTCardsRecyclerView = view.findViewById(R.id.face_up_tcard_recycler_view);
        faceUpTCardsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        myTCardNumsRecyclerView = view.findViewById(R.id.t_card_num_recycler_view);
        myTCardNumsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        myDestCardInfoRecyclerView = view.findViewById(R.id.dest_card_info_recycler_view);
        myDestCardInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        myStatsLayout = view.findViewById(R.id.myStats);
        myStatsTitleText = myStatsLayout.findViewById(R.id.playerUsername);
        myScoreText = myStatsLayout.findViewById(R.id.playerScore);
        myDestCardNumText = myStatsLayout.findViewById(R.id.playerDestCards);
        myTrainCardsNumText = myStatsLayout.findViewById(R.id.playerTrainCards);
        myCarsLeftNumText = myStatsLayout.findViewById(R.id.playerCarsLeft);

        gameStatusPresenter = new GameStatusPresenter(this);
        gameStatusPresenter.setObserver(true);

//        //TODO: delete between these lines if we get it working (only to show GameOverView works)
//        gameOverTestButton = view.findViewById(R.id.testRunBtn);
//        gameOverTestButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayToast("Changing data in ClientM (Model)");
//                ClientM.get().setGameOver(true);
//            }
//        });
//        //TODO:

        setBtnListeners();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IGameBoardActivity) {
            mListener = (IGameBoardActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setBtnListeners() {
        closeFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStatusPresenter.setObserver(false);
                mListener.closeGameStatusView();
            }
        });
        trainCardDeckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTCardDeck();
            }
        });
        destCardDeckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDestCardDeck();
            }
        });
    }


    @Override
    public void selectDestCardDeck() {
        gameStatusPresenter.selectNewDestCards();
    }

    @Override
    public void selectFaceUpTCard(TrainCardM trainCard, int pos) {
        gameStatusPresenter.takeFaceUpTCard(trainCard, pos);
    }

    @Override
    public void selectTCardDeck() {
        gameStatusPresenter.takeTCardFromDeck();
    }


    @Override
    public void updateFaceUpTCards(TrayM faceUpTCards) {
        System.out.println("Updating face up t cards in GameStatusFragment");
        faceUpTCardAdapter = new FaceUpTCardAdapter(getActivity(), faceUpTCards.getTray());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                faceUpTCardsRecyclerView.setAdapter(faceUpTCardAdapter);
            }
        });
    }


    @Override
    public void updateOpponentStats(List<PlayerInfoM> opposingPlayers) {
        opponentStatsAdapter = new OpponentStatsAdapter(getActivity(), opposingPlayers);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                opponentStatsRecyclerView.setAdapter(opponentStatsAdapter);
            }
        });
    }

    @Override
    public void updateMyStats(final PlayerInfoM me) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Resources res = getResources();
                myStatsTitleText.setText(res.getString(R.string.myStatsText));
                myStatsTitleText.setTextSize(20);
                myScoreText.setText(String.format(res.getString(R.string.scoreText), me.getPoints()));
                myScoreText.setTextSize(16);
                myTrainCardsNumText.setText(String.format(res.getString(R.string.numTrainCardsText),
                        me.getNumTrainCards()));
                myTrainCardsNumText.setTextSize(16);
                myDestCardNumText.setText(String.format(res.getString(R.string.numDestCardsText),
                        me.getNumDestinationCards()));
                myDestCardNumText.setTextSize(16);
                myCarsLeftNumText.setText(String.format(res.getString(R.string.numCarsLeftText),
                        me.getNumTrains()));
                myCarsLeftNumText.setTextSize(16);
                if (me.isTurn()) {
                    setTextColorBlue(myStatsTitleText);
                    setTextColorBlue(myScoreText);
                    setTextColorBlue(myTrainCardsNumText);
                    setTextColorBlue(myDestCardNumText);
                    setTextColorBlue(myCarsLeftNumText);
                }
                else {
                    setTextColorDefault(myStatsTitleText);
                    setTextColorDefault(myScoreText);
                    setTextColorDefault(myTrainCardsNumText);
                    setTextColorDefault(myDestCardNumText);
                    setTextColorDefault(myCarsLeftNumText);
                }
            }
        });
    }

    @Override
    public void switchDestCardSelectionFragment() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListener.openDestCardSelectionView();
            }
        });
    }

    @Override
    public void updateMyTrainCards(List<TrainCardColor> myTCards) {
        System.out.println("Updating my train cards");
        trainCardNumbersAdapter = new TrainCardNumbersAdapter(getActivity(), myTCards);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myTCardNumsRecyclerView.setAdapter(trainCardNumbersAdapter);
            }
        });
    }

    @Override
    public void displayToast(String toDisplay) {
        final String message = toDisplay;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void grayOutWildFaceUpTCards(boolean grayOut) {
        this.grayOutWildCards = grayOut;
    }

    @Override
    public void switchToGameOverView() {
      getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
              mListener.openGameOverView();
          }
      });
    }

    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void updateDestDeckNum(final int deckSize) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Resources res = getResources();
                String destDeckBtnText =
                        String.format(res.getString(R.string.drawDestCardsBtnText), deckSize);
                destCardDeckBtn.setText(destDeckBtnText);
            }
        });
    }

    @Override
    public void updateTCardDeckNum(final int deckSizeM) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Resources res = getResources();
                String trainDeckBtnText =
                        String.format(res.getString(R.string.drawTrainCardBtnText), deckSizeM);
                trainCardDeckBtn.setText(trainDeckBtnText);
            }
        });
    }

    @Override
    public void updateDestCardInfo(ArrayList<DestinationCardM> destCards) {
        destCardInfoAdapter = new DestCardInfoAdapter(getActivity(), destCards);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myDestCardInfoRecyclerView.setAdapter(destCardInfoAdapter);
            }
        });
    }

    private void setTextColorDefault(TextView text) {
        text.setTextColor(getResources().getColor(R.color.defaultTextColor, null));
    }

    private void setTextColorBlue(TextView text) {
        text.setTextColor(getResources().getColor(R.color.pleasingBlue, null));
    }

    // Adaptor for Face up Train Cards recycler view
    class FaceUpTCardAdapter extends RecyclerView.Adapter<FaceUpTCardHolder> {

        private List<TrainCardM> items;
        private LayoutInflater inflater;

        public FaceUpTCardAdapter(Context context, List<TrainCardM> items) {
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public FaceUpTCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.face_up_t_cards, parent, false);
            return new FaceUpTCardHolder(view);
        }

        @Override//TODO: display error toast on fragment
        public void onBindViewHolder(FaceUpTCardHolder holder, int position) {
            TrainCardM item = items.get(position);
            holder.bind(item, position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

    }

    // Holder for Face up Train Cards recycler view
    class FaceUpTCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView faceUpTCardView;
        private TrainCardM item;
        private int position;

        public FaceUpTCardHolder(View view) {
            super(view);
            faceUpTCardView = view.findViewById(R.id.faceUpTCardDisplay);
        }

        void bind(TrainCardM item, int position) {
            this.item = item;
            this.position = position;
            Drawable trainCardDrawable = getFaceUpTCardDrawable(this.item);
            faceUpTCardView.setImageDrawable(trainCardDrawable);
            faceUpTCardView.setOnClickListener(this);
        }

        private Drawable getFaceUpTCardDrawable(TrainCardM card) {
            switch(card.getColor()) {
                case RED:
                    return getResources().getDrawable(R.drawable.red_train_card,
                            null);
                case BLUE:
                    return getResources().getDrawable(R.drawable.blue_train_card,
                            null);
                case YELLOW:
                    return getResources().getDrawable(R.drawable.yellow_train_card,
                            null);
                case GREEN:
                    return getResources().getDrawable(R.drawable.green_train_card,
                            null);
                case ORANGE:
                    return getResources().getDrawable(R.drawable.orange_train_card,
                            null);
                case PINK:
                    return getResources().getDrawable(R.drawable.pink_train_card,
                            null);
                case WHITE:
                    return getResources().getDrawable(R.drawable.white_train_card,
                            null);
                case BLACK:
                    return getResources().getDrawable(R.drawable.black_train_card,
                            null);
                default:
                    Drawable trainCardDrawable = getResources().getDrawable(
                            R.drawable.rainbow_train_card,null);
                    if (grayOutWildCards) {
                        trainCardDrawable.setAlpha(100);
                    }
                    else {
                        trainCardDrawable.setAlpha(255);
                    }
                    return trainCardDrawable;
            }
        }

        @Override
        public void onClick(View v) {
            System.out.println("Clicked Face Up T Card");
            selectFaceUpTCard(this.item, this.position);
        }
    }

    // Adaptor for Face up Train Cards recycler view
    class TrainCardNumbersAdapter extends RecyclerView.Adapter<TrainCardNumbersHolder> {

        private List<TrainCardColor> items;
        private LayoutInflater inflater;

        public TrainCardNumbersAdapter(Context context, List<TrainCardColor> items) {
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public TrainCardNumbersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.train_card_num_item, parent, false);
            return new TrainCardNumbersHolder(view);
        }

        @Override
        public void onBindViewHolder(TrainCardNumbersHolder holder, int position) {
            TrainCardColor item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

    }

    // Holder for Face up Train Cards recycler view
    class TrainCardNumbersHolder extends RecyclerView.ViewHolder {

        private TextView TCardNumText;
        private TrainCardColor item;

        public TrainCardNumbersHolder(View view) {
            super(view);
            TCardNumText = view.findViewById(R.id.TCardNum);
        }

        void bind(TrainCardColor item) {
            this.item = item;
            setTCardNumText(gameStatusPresenter.getCardNum(this.item));
        }

        private void setTCardNumText(int numTCards) {

            Resources res = getResources();
            String text = String.format(res.getString(R.string.TCardNumText),
                    item.toString(), numTCards);
            System.out.println(text);
            TCardNumText.setText(text);
        }

    }


    // Adaptor for Opponent stats
    class OpponentStatsAdapter extends RecyclerView.Adapter<OpponentStatsHolder> {

        private List<PlayerInfoM> items;
        private LayoutInflater inflater;

        public OpponentStatsAdapter(Context context, List<PlayerInfoM> items) {
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public OpponentStatsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.player_stats_item, parent, false);
            return new OpponentStatsHolder(view);
        }

        @Override
        public void onBindViewHolder(OpponentStatsHolder holder, int position) {
            PlayerInfoM item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

    }

    // Holder for Face up Train Cards recycler view
    class OpponentStatsHolder extends RecyclerView.ViewHolder {

        private PlayerInfoM item;
        private TextView usernameText;
        private TextView scoreText;
        private TextView trainCardsText;
        private TextView destCardsText;
        private TextView trainsLeftText;

        public OpponentStatsHolder(View view) {
            super(view);
            usernameText = view.findViewById(R.id.playerUsername);
            scoreText = view.findViewById(R.id.playerScore);
            trainCardsText = view.findViewById(R.id.playerTrainCards);
            destCardsText = view.findViewById(R.id.playerDestCards);
            trainsLeftText = view.findViewById(R.id.playerCarsLeft);
        }

        void bind(PlayerInfoM item) {
            this.item = item;
            setTextValues();
            indicateTurn();
        }

        private void setTextValues() {
            usernameText.setText(item.getUserM().getUsername());
            Resources res = getResources();
            scoreText.setText(String.format(res.getString(R.string.scoreText), item.getPoints()));
            trainCardsText.setText(String.format(res.getString(R.string.numTrainCardsText),
                    item.getNumTrainCards()));
            destCardsText.setText(String.format(res.getString(R.string.numDestCardsText),
                    item.getNumDestinationCards()));
            trainsLeftText.setText(String.format(res.getString(R.string.numCarsLeftText),
                    item.getNumTrains()));
        }

        private void indicateTurn() {
            if (item.isTurn()) {
                setTextColorBlue(usernameText);
                setTextColorBlue(scoreText);
                setTextColorBlue(trainCardsText);
                setTextColorBlue(destCardsText);
                setTextColorBlue(trainsLeftText);
            }
            else {
                setTextColorDefault(usernameText);
                setTextColorDefault(scoreText);
                setTextColorDefault(trainCardsText);
                setTextColorDefault(destCardsText);
                setTextColorDefault(trainsLeftText);
            }
        }
    }

    // Adaptor for Opponent stats
    class DestCardInfoAdapter extends RecyclerView.Adapter<DestCardInfoHolder> {

        private List<DestinationCardM> items;
        private LayoutInflater inflater;

        public DestCardInfoAdapter(Context context, List<DestinationCardM> items) {
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public DestCardInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.layout_dest_card_info_item, parent,
                    false);
            return new DestCardInfoHolder(view);
        }

        @Override
        public void onBindViewHolder(DestCardInfoHolder holder, int position) {
            DestinationCardM item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

    }

    // Holder for Face up Train Cards recycler view
    class DestCardInfoHolder extends RecyclerView.ViewHolder {

        private DestinationCardM item;
        private TextView city1Text;
        private TextView city2Text;
        private TextView pointsText;
//        private TextView completedText;

        public DestCardInfoHolder(View view) {
            super(view);
            city1Text = view.findViewById(R.id.city1);
            city2Text = view.findViewById(R.id.city2);
            pointsText = view.findViewById(R.id.points);
//            completedText = view.findViewById(R.id.completed);

        }

        void bind(DestinationCardM item) {
            this.item = item;
            setTextValues();
        }

        private void setTextValues() {
            Resources res = getResources();
            city1Text.setText(String.format(res.getString(R.string.destCardCity1Text),
                    item.getCityM1().getName()));
            city2Text.setText(String.format(res.getString(R.string.destCardCity2Text),
                    item.getCityM2().getName()));
            pointsText.setText(String.format(res.getString(R.string.pointsPossibleText),
                    item.getPoints()));
            String completed = (item.isCompleted()) ? "Yes" : "No";
 //           completedText.setText(String.format(res.getString(R.string.completedText), completed));
        }
    }
}
