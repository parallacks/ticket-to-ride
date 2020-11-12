package com.ticket_to_ride.server.watcher;

import java.util.Observer;
import java.util.Observable;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.model.ChatM;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.SessionM;
import com.ticket_to_ride.server.model.UserM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.ClientProxy;

public class ChatWatcher implements Observer {

    private GameInfoM context;
    private Serializer ser;

    public ChatWatcher(GameInfoM context) {
        this.context = context;
        ser = new Serializer();
    }

    @Override
    public void update(Observable target, Object arg) {
        if (arg == null) return;
        if (arg.getClass() != ChatM.class) return;
        ChatM chat = (ChatM)arg;



        for (PlayerInfoM player : context.getPlayers()) {
            SessionM session = ServerM.get().getUserSession(player.getUserM());
            if (session != null) {
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        Command com = new Command();
                        com.setOperation("updateChat");
                        com.setArg("chatMessage", "ChatM", ser.serialize(chat));
                        session.getProxy().updateChat(com);
                    }
                };
                th.start();
            }
        }

    }
}
