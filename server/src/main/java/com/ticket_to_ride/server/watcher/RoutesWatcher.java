package com.ticket_to_ride.server.watcher;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.SessionM;

import java.util.Observable;
import java.util.Observer;

public class RoutesWatcher implements Observer {
    private GameInfoM context;
    private PlayerInfoM player;
    private Serializer ser;

    public RoutesWatcher(GameInfoM context)
    {
        this.context = context;
        this.player = null;
        ser = new Serializer();
    }

    public void setPlayer(PlayerInfoM player)
    {
        this.player = player;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (!o.getClass().equals(RouteID.class))
            return;

        RouteID routeID = (RouteID)o;

        for (PlayerInfoM player : context.getPlayers()) {
            SessionM session = ServerM.get().getUserSession(player.getUserM());
            if (session != null) {
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        Command com = new Command();
                        com.setOperation("updateRoutes");
                        com.setArg("claimedRoute", "RouteID", routeID.toString());
                        String playerData = ser.serialize(player.getUserM().getUsername());
                        com.setArg("playerName", "String", playerData);

                        session.getProxy().updateRoutes(com);
                    }
                };
                th.start();
            }
        }
    }
}
