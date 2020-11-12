package com.ticket_to_ride.server.service;

import com.ticket_to_ride.common.StatusType;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.UserM;

import java.util.UUID;

public class ServerTurnOverService {

    private String gameName;
    private UserM user;
    private UUID commandId;
    private ServerM serverM;

    public ServerTurnOverService (Command data, UserM user) {
        commandId = data.getUUID();
        this.user = user;
        CommandArg gameArg = data.getArg("gameName");
        if (gameArg.getType().equals("String"))
            this.gameName = gameArg.getData();
        serverM = ServerM.get();
    }
    public CommandResult execute() {
        GameInfoM game = serverM.getGameByName(this.gameName);

        game.nextTurn();

        CommandResult command = new CommandResult(commandId);
        return command;
    }
}

