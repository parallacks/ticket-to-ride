package com.ticket_to_ride.server.watcher;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.model.ChatM;
import com.ticket_to_ride.common.model.GameHistoryM;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.SessionM;

import java.util.Observable;
import java.util.Observer;

public class GameHistoryWatcher implements Observer {
    private GameInfoM context;
    private Serializer ser;

    public GameHistoryWatcher(GameInfoM context) {
        this.context = context;
        ser = new Serializer();
    }

    @Override
    public void update(Observable target, Object arg) {
        if (arg == null) return;
        if (arg.getClass() != GameHistoryM.class) return;
        GameHistoryM history = (GameHistoryM) arg;


        for (PlayerInfoM player : context.getPlayers()) {
            SessionM session = ServerM.get().getUserSession(player.getUserM());
            if (session != null) {
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        Command com = new Command();
                        com.setOperation("updateGameHistory");
                        com.setArg("gameHistory", "GameHistoryM", ser.serialize(history));
                        session.getProxy().updateGameHistory(com);
                    }
                };
                th.start();
            }
        }

    }
}
