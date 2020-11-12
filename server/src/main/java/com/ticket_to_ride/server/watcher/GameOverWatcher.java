package com.ticket_to_ride.server.watcher;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.server.DestinationCardChecker;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.SessionM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameOverWatcher implements Observer {
    private GameInfoM context;
    private Serializer ser;

    public GameOverWatcher(GameInfoM context)
    {
        this.context = context;
        ser = new Serializer();
    }

    @Override
    public void update(Observable observable, Object o) {
        DestinationCardChecker destinationCardChecker = new DestinationCardChecker(context.getMap());
        int maxRoutes = 0;
        PlayerInfoM biggestBoi = null;
        for(PlayerInfoM player : context.getPlayers()){
            for(DestinationCardM dest : player.getDestinationCards().getHand()){
                dest.setCompleted(destinationCardChecker.check(dest, player));
                if(dest.isCompleted()) {
                    player.setPoints(player.getPoints() + dest.getPoints());
                    int pointsGained = player.getPointsGained();
                    player.setPointsGained(pointsGained + dest.getPoints());
                }
                else {
                    player.setPoints(player.getPoints() - dest.getPoints());
                    int pointsLost = player.getPointsLost();
                    player.setPointsLost(pointsLost + dest.getPoints());
                }
            }
            if(player.getClaimedRoutes().size() > maxRoutes){
                maxRoutes = player.getClaimedRoutes().size();
                biggestBoi = player;
            }
        }
        biggestBoi.setPoints(biggestBoi.getPoints()+10);
        final String mostRoutesPlayer = biggestBoi.getUserM().getUsername();
        for(PlayerInfoM playerInfoM : context.getPlayers()){
            SessionM session = ServerM.get().getUserSession(playerInfoM.getUserM());
            if (session != null) {
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        Command com = new Command();
                        com.setOperation("gameOver");
                        String playerData = ser.serialize(context.getPlayers());
                        com.setArg("players", "ArrayList<PlayerInfoM>", playerData);
                        com.setArg("biggestBoi", "String", mostRoutesPlayer);
                        session.getProxy().gameOver(com);
                    }
                };
                th.start();
            }
        }
    }
}
