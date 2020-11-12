package com.ticket_to_ride.server.service;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.ChatM;
import com.ticket_to_ride.common.model.GameHistoryM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.UserM;

import java.util.UUID;

public class ServerGameHistoryService {
    private Serializer serializer;
    private ServerM serverM;
    private UserM caller;
    private UUID commandId;
    private GameHistoryM history;
    private String gameName;

    public ServerGameHistoryService(Command data, UserM caller) {
        this.caller = caller;
        this.commandId = data.getUUID();
        this.serializer = new Serializer();
        CommandArg messageArg = data.getArg("gameHistory");
        this.history = (GameHistoryM)serializer.deserialize(messageArg.getData(), GameHistoryM.class);
        this.gameName = this.caller.getGame().getGameName();
        this.serverM = ServerM.get();
    }

    public CommandResult execute()
    {
        CommandResult res = new CommandResult(this.commandId);

        this.serverM.addToChatList(this.history, this.gameName);

        return res;
    }
}
