package com.ticket_to_ride.server.watcher;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.SessionM;

import java.util.Observable;
import java.util.Observer;

public class TrainDeckWatcher implements Observer {
    private GameInfoM context;
    private ServerM serverM;

    public TrainDeckWatcher(GameInfoM context) {
        this.context = context;
        serverM = ServerM.get();
    }

    @Override
    public void update(Observable target, Object arg) {
        if (arg == null) return;
        if (arg.getClass() != TrainCardM.class)
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
                            com.setOperation("updateTrainDeck");
                            com.setArg("deckCount", "int", Integer.toString(context.getTrainCards().getDeckSize()));
                            session.getProxy().updateDeck(com);
                        }
                    };
                    th.start();
                }
            }
        }

    }
}
