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

public class ServerJoinGameService {

    private Serializer serializer;
    private ServerM serverM;
    private UserM user;
    private UUID commandId;
    private String gameName;

    public ServerJoinGameService(Command data, UserM user) {
        this.user = user;
        commandId = data.getUUID();
        CommandArg gameNameArg = data.getArg("gameName");
        if (gameNameArg.getType().equals("String"))
            this.gameName = gameNameArg.getData();
        serializer = new Serializer();
        serverM = ServerM.get();
    }

    public CommandResult execute() {
        this.user.setStatus(StatusType.INLOBBY);
        GameInfoM game = serverM.getGameByName(gameName);
        this.user.setGame(game);

        CommandResult com = new CommandResult(commandId);

        //if (game.getPlayers().size() >= 5) {
            serverM.addPlayerToGame(game, new PlayerInfoM(this.user));
            String gameString = serializer.serialize(game);
            CommandArg gameInfo = new CommandArg("game", "GameInfoM", gameString);
            com.addArg(gameInfo);
            //String mapString = serializer.serialize(game.getMap());
            //com.setArg("map", "MapM", mapString);
        /*}
        else
        {
            com.setError("Game is full!");
        }*/
        return com;
    }
}
