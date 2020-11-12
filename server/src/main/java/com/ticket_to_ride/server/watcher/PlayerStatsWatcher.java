package com.ticket_to_ride.server.watcher;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.SessionM;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PlayerStatsWatcher implements Observer {
    private GameInfoM context;
    private PlayerInfoM player;
    private ServerM serverM;
    private Serializer ser;

    public PlayerStatsWatcher(GameInfoM context)
    {
        this.context = context;
        player = null;
        serverM = ServerM.get();
        ser = new Serializer();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (!o.getClass().equals(PlayerInfoM.class))
            return;
        player = (PlayerInfoM) o;
        RouteID claimedRoute = context.getClaimedRoute();
        context.setClaimedRoute(null);
        //Game over Arg
        if(player.equals(context.getLastRoundPlayer())){
            context.setGameOver(true);
        }
        for (PlayerInfoM p : context.getPlayers()) {
            SessionM session = serverM.getUserSession(p.getUserM());
            Command com = new Command();
            com.setOperation("updatePlayerStats");
            com.setArg("numTrainCards", "int", ser.serialize(player.getTrainCards().getHandSize()));
            com.setArg("numDestinationCards", "int", ser.serialize(player.getDestinationCards().getHandSize()));
            com.setArg("numTrainCars", "int", Integer.toString(player.getNumTrains()));
            com.setArg("points", "int", Integer.toString(player.getPoints()));
            com.setArg("playerName", "String", player.getUserM().getUsername());
            //Send the claimed route if it exists
            com.setArg("claimedRoute", "RouteID", ser.serialize(claimedRoute));
            //Chat data
            String chatListData = ser.serialize(context.getTempChatList());

            //Last Round
            if (player.getNumTrains() < 3) {
                context.setLastRoundPlayer(player);
                com.setArg("lastRound", "String", "true");
            }
            else {
                com.setArg("lastRound", "String", "false");
            }
            com.setArg("chatList", "ChatListM", chatListData);
            //Tray data
            com.setArg("tray", "TrayM", ser.serialize(context.getTray()));
            //Train Deck Count
            com.setArg("trainDeckCount", "int", Integer.toString(context.getTrainCards().getDeckSize()));
            //Destination Deck Count
            com.setArg("destinationDeckCount", "int", Integer.toString(context.getDestinationCards().getDeckSize()));
            //Player Turn calculate and arg
            ArrayList<Boolean> turns = new ArrayList<>();
            for (Object obj : context.getPlayers())
            {
                turns.add(((PlayerInfoM)obj).isTurn());
            }
            com.setArg("playerTurns", "ArrayList<Boolean>", ser.serialize(turns));

            if (session != null) {
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        session.getProxy().updatePlayerStats(com);
                    }
                };
                th.start();
            }
        }
        context.clearTempChatList();
    }
}
