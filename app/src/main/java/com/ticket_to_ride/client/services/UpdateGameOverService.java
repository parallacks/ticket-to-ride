package com.ticket_to_ride.client.services;

import com.google.gson.reflect.TypeToken;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class UpdateGameOverService {
    private Command command;
    private ClientM model;
    private Serializer ser;

    public UpdateGameOverService(Command command)
    {
        this.command = command;
        model = ClientM.get();
        ser = new Serializer();
    }

    public CommandResult UpdateGameOver()
    {
        CommandResult res = new CommandResult(command.getUUID());

        Type PlayerMType = new TypeToken<ArrayList<PlayerInfoM>>(){}.getType();
        ArrayList<PlayerInfoM> players = (ArrayList<PlayerInfoM>) ser.deserialize(command.getArg("players").getData(), PlayerMType);
        String mostRoutesPlayer = (String)ser.deserialize(command.getArg("biggestBoi").getData(), String.class);
        model.setMostRoutesPlayer(mostRoutesPlayer);
        model.getActiveGame().setPlayers(players);
        model.setGameOver(true);
        return res;
    }
}
