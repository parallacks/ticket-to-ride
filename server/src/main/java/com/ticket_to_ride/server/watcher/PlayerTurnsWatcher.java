package com.ticket_to_ride.server.watcher;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.SessionM;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PlayerTurnsWatcher implements Observer {
    private GameInfoM context;
    private PlayerInfoM player;
    private ServerM serverM;
    private Serializer ser;

    public PlayerTurnsWatcher(GameInfoM context)
    {
        this.context = context;
        player = null;
        serverM = ServerM.get();
        ser = new Serializer();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (!o.getClass().equals(ArrayList.class))
            return;
        else {
            ArrayList<Object> playerObjects = (ArrayList<Object>) o;
            if (playerObjects.size() > 0 && playerObjects.get(0).getClass().equals(PlayerInfoM.class)) {
                ArrayList<Boolean> players = new ArrayList<>();
                for (Object obj : playerObjects)
                {
                    players.add(((PlayerInfoM)obj).isTurn());
                }

                for (PlayerInfoM player : context.getPlayers()) {
                    SessionM session = serverM.getUserSession(player.getUserM());
                    if (session != null) {
                        Thread th = new Thread() {
                            @Override
                            public void run() {
                                Command com = new Command();
                                com.setOperation("updatePlayerTurns");
                                String turnData = ser.serialize(players);
                                com.setArg("playerTurns", "ArrayList<Boolean>", turnData);

                                session.getProxy().updatePlayerTurns(com);
                            }
                        };
                        th.start();
                    }
                }
            }
        }
    }
}
