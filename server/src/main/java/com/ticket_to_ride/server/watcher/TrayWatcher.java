package com.ticket_to_ride.server.watcher;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.common.model.TrayM;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.SessionM;

import java.util.Observable;
import java.util.Observer;

public class TrayWatcher implements Observer {
    private GameInfoM context;
    private ServerM serverM;
    private Serializer ser;

    public TrayWatcher(GameInfoM context) {
        this.context = context;
        serverM = ServerM.get();
        ser = new Serializer();
    }

    @Override
    public void update(Observable target, Object arg) {
        if (arg == null) return;
        if (arg.getClass() != TrayM.class)
            return;
        else
        {
            for (PlayerInfoM player : context.getPlayers()) {
                SessionM session = serverM.getUserSession(player.getUserM());
                if (session != null) {
                    Thread th = new Thread() {
                        @Override
                        public void run() {
                            Command com = new Command();
                            com.setOperation("updateTray");
                            String trayData = ser.serialize(context.getTray());
                            com.setArg("tray", "TrayM", trayData);
                            session.getProxy().updateTray(com);
                        }
                    };
                    th.start();
                }
            }
        }

    }
}
