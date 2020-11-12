//Created By Lance

package com.ticket_to_ride.server.service;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.common.StatusType;
import com.ticket_to_ride.server.model.UserM;

import java.util.UUID;

public class ServerCreateGameService {

    private UUID commandId;
    private UserM user;
    private Serializer serializer;
    private ServerM serverM;

    public ServerCreateGameService (Command data, UserM user) {
        commandId = data.getUUID();
        this.user = user;
        serializer = new Serializer();
        serverM = ServerM.get();
    }

    public CommandResult execute(){
        this.user.setStatus(StatusType.INLOBBY);
        GameInfoM game = new GameInfoM();
        game.addPlayer(new PlayerInfoM(this.user));
        game.setStatus("Inactive");
        this.user.setGame(game);
        serverM.addGame(game);

        String gameString = serializer.serialize(game);
        CommandResult com = new CommandResult(commandId);
        CommandArg gameInfo = new CommandArg("game", "GameInfoM", gameString);
        com.addArg(gameInfo);
        //String mapInfo = serializer.serialize(game.getMap());
        //com.setArg("map", "MapM", mapInfo);

        return com;
    }
}
