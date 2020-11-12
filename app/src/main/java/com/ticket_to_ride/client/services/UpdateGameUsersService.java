package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.GameInfoM;
import com.ticket_to_ride.common.model.MapM;

public class UpdateGameUsersService
{
    private Command command;
    private Serializer serializer;
    private ClientM model;

    public UpdateGameUsersService(Command command)
    {
        this.command = command;
        this.serializer = new Serializer();
        this.model = ClientM.get();
    }

    public CommandResult UpdateGameUsers()
    {
//        GameInfoM game = (GameInfoM)serializer.deserialize(command.getArg("game").getData(), GameInfoM.class);
//        MapM map = (MapM)serializer.deserialize(command.getArg("map").getData(), MapM.class);
//        game.setMap(map);
//        model.setActiveGame(game);
        if(model.getActiveGame() != null) {
            PlayerInfoM player = (PlayerInfoM) serializer.deserialize(command.getArg("player").getData(), PlayerInfoM.class);
            model.addPlayerToActiveGame(player);
        }
        CommandResult result = new CommandResult(command.getUUID());
        return result;
    }
}
