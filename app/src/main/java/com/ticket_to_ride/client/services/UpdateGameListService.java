package com.ticket_to_ride.client.services;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.GameInfoM;

public class UpdateGameListService
{
    private Command command;
    private Serializer serializer;
    private ClientM model;

    public UpdateGameListService(Command command)
    {
        this.command = command;
        this.serializer = new Serializer();
        this.model = ClientM.get();
    }

    public CommandResult UpdateGameList()
    {

        GameInfoM game = (GameInfoM)serializer.deserialize(command.getArg("game").getData(), GameInfoM.class);
        if(model.getGameByName(game.getGameName()) != null) {
            model.removeLobbyGame(game.getGameName());
        }
        else
            model.addGameToLobby(game);
        CommandResult result = new CommandResult(command.getUUID());
        return result;
    }
}
