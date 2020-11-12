package com.ticket_to_ride.client.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.GameInfoM;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.client.model.UserM;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.RouteM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameOverActivity extends AppCompatActivity {

    private TextView winnerDeclarationText;
    private TextView longestRouteDeclarationText;
    private ClientM clientM;
    private RecyclerView endGameStatsRecycler;
    private HashMap<PlayerInfoM, Integer> playersInOrder;

    //TODO: delete if we ever get it all working
    int otherUserPtsGained = 35;
    int otherUserPtsLost = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        clientM = ClientM.get();

        playersInOrder = new HashMap<>();

        winnerDeclarationText = findViewById(R.id.winnerDeclaration);
        longestRouteDeclarationText = findViewById(R.id.longestRouteDeclaration);

        endGameStatsRecycler = findViewById(R.id.game_over_recycler_view);
        endGameStatsRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        displayEndGameInfo();
    }

    private void displayWinner(PlayerInfoM player) {
        winnerDeclarationText.setText(getResources().getString(R.string.winnerDeclarationText,
                player.getUserM().getUsername()));
    }

    private void displayLongestRoute(String mostRoutesPlayer) {
        longestRouteDeclarationText.setText(getResources().getString(R.string.longestRouteDeclarationText,
                mostRoutesPlayer));
    }

    private void displayPlayersStats(ArrayList<PlayerInfoM> players) {
        EndGameStatsAdaptor endGameStatsAdaptor= new EndGameStatsAdaptor(this, players);
        endGameStatsRecycler.setAdapter(endGameStatsAdaptor);
    }

    private ArrayList<PlayerInfoM> calculatePlayerOrder() {
        ArrayList<PlayerInfoM> players = clientM.getOpponentStats();
        players.add(clientM.getMyStats());

        Collections.sort(players);

        return players;
    }

    private void addPlacements(ArrayList<PlayerInfoM> players) {
        for (int i = 0; i < players.size(); i++) {
            playersInOrder.put(players.get(i), i+1);
        }
    }

    public void displayEndGameInfo() {
        ArrayList<PlayerInfoM> players = calculatePlayerOrder();
        addPlacements(players);

        displayWinner(players.get(0));
        displayPlayersStats(players);
        displayLongestRoute(clientM.getMostRoutesPlayer());
    }

    // Adaptor for Face up Train Cards recycler view
    class EndGameStatsAdaptor extends RecyclerView.Adapter<EndGameStatsHolder> {

        private List<PlayerInfoM> items;
        private LayoutInflater inflater;

        public EndGameStatsAdaptor(Context context, List<PlayerInfoM> items) {
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public EndGameStatsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.layout_game_over_stats_item, parent, false);
            return new EndGameStatsHolder(view);
        }

        @Override
        public void onBindViewHolder(EndGameStatsHolder holder, int position) {
            PlayerInfoM item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

    }

    // Holder for Face up Train Cards recycler view
    class EndGameStatsHolder extends RecyclerView.ViewHolder {

        private PlayerInfoM item;

        private TextView placeIndicatorText;
        private TextView usernameIndicatorText;
        private TextView totalPointsIndicatorText;
        private TextView pointsGainedIndicatorText;
        private TextView pointsLostIndicatorText;

        public EndGameStatsHolder(View view) {
            super(view);

            placeIndicatorText = view.findViewById(R.id.placeIndicator);
            usernameIndicatorText = view.findViewById(R.id.usernameIndicator);
            totalPointsIndicatorText = view.findViewById(R.id.totalPointsIndicator);
            pointsGainedIndicatorText = view.findViewById(R.id.pointsGainedIndicator);
            pointsLostIndicatorText = view.findViewById(R.id.pointsLostIndicator);
        }

        void bind(PlayerInfoM item) {
            this.item = item;
            setTextFields();
        }

        private void setTextFields() {
            Resources res = getResources();
            placeIndicatorText.setText(determinePlaceText());
            usernameIndicatorText.setText(res.getString(R.string.usernameText,
                    this.item.getUserM().getUsername()));
            totalPointsIndicatorText.setText(res.getString(R.string.totalPtsText,
                    this.item.getPoints()));
            pointsGainedIndicatorText.setText(res.getString(R.string.ptsGainedText,
                    this.item.getPointsGained()));
            pointsLostIndicatorText.setText(res.getString(R.string.ptsLostTexxt,
                    this.item.getPointsLost()));
            /*
            //TODO: change this logic if we implement it for reals
            if (this.item.getUserM().getUsername().equals(clientM.getUser().getUsername())) {
                pointsGainedIndicatorText.setText(res.getString(R.string.ptsGainedText,
                        calculatePointsGained(clientM.getDestinationCards().getHand())));
                pointsLostIndicatorText.setText(res.getString(R.string.ptsLostTexxt,
                        calculatePointsLost(clientM.getDestinationCards().getHand())));
            }
            else {
                pointsGainedIndicatorText.setText(res.getString(R.string.ptsGainedText,
                        otherUserPtsGained));
                pointsLostIndicatorText.setText(res.getString(R.string.ptsLostTexxt,
                        otherUserPtsLost));
            }

            //TODO: display points lost and gained once logic has been implemented in server
            */
        }

        private String determinePlaceText() {
            switch(playersInOrder.get(this.item)) {
                case 1:
                    return "1st:";
                case 2:
                    return "2nd:";
                case 3:
                    return "3rd:";
                case 4:
                    return "4th:";
                case 5:
                    return "5th:";
                default:
                    return null;
            }
        }
    }

    // Test functions
    private void createTestGame() {
        GameInfoM testGame = null;
        Random random = new Random();
//        int rando = random.nextInt(clientM.getOpponentStats().size());

        String usernames[] = {"Jared", "Jayden", "Lance", "Steven", "Alex"};
        ArrayList<PlayerInfoM> players = new ArrayList<>();
        int i = 0;
        for (String username : usernames) {
            UserM user = new UserM(username, "");
            PlayerInfoM player = new PlayerInfoM(user);
            int randomPoints = random.nextInt(200);
            player.setPoints(randomPoints);
            players.add(player);
            if (i == 0) {
                testGame = new GameInfoM("test game", player);
                clientM.setUser(user);
            }
            i++;
        }
        if (testGame != null) {
            testGame.setPlayers(players);
            clientM.setActiveGame(testGame);
        }

    }

    private void setPointsAlg() {
        int userPoints = 0;
        for (RouteID routeID : clientM.getMyClaimedRoutes()) {
            userPoints+= clientM.getActiveGame().getMap().getRouteById(routeID).getPoints();
        }

        // Set some dest cards of user completed, some not
        for (int i = 0; i < clientM.getDestinationCards().getHand().size(); i++) {
            if (i == clientM.getDestinationCards().getHand().size() -1) {
                clientM.getDestinationCards().getHand().get(i).setCompleted(false);
            }
            else {
                clientM.getDestinationCards().getHand().get(i).setCompleted(true);
            }
        }

        userPoints += calculatePointsGained(clientM.getDestinationCards().getHand());
        userPoints -= calculatePointsLost(clientM.getDestinationCards().getHand());

//        for (DestinationCardM destCard : clientM.getDestinationCards().getHand()) {
//            if (destCard.isCompleted()) {
//                userPoints+=destCard.getPoints();
//            }
//            else {
//                userPoints-=destCard.getPoints();
//            }
//        }

        clientM.getActiveGame().getPlayerByUsername(clientM.getUser().getUsername()).setPoints(userPoints);

        int otherUserPoints = 0;
        otherUserPoints = otherUserPtsGained - otherUserPtsLost + 10; // add 10 for random claimed route
        for (PlayerInfoM player : clientM.getActiveGame().getPlayers()) {
            if (!player.getUserM().getUsername().equals(clientM.getUser().getUsername())) {
                player.setPoints(otherUserPoints);
            }
        }

    }

    private int calculatePointsGained(ArrayList<DestinationCardM> destCards) {
        int points = 0;
        for (DestinationCardM destCard : destCards) {
            if (destCard.isCompleted()) {
                points+=destCard.getPoints();
            }
        }
        return points;
    }

    private int calculatePointsLost(ArrayList<DestinationCardM> destCards) {
        int points = 0;
        for (DestinationCardM destCard : destCards) {
            if (!destCard.isCompleted()) {
                points+=destCard.getPoints();
            }
        }
        return points;
    }

}
