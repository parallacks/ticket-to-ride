package com.ticket_to_ride.client.services;

import com.google.gson.reflect.TypeToken;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UpdatePlayerTurnsService {
    private Command command;
    private ClientM model;
    private Serializer ser;

    public UpdatePlayerTurnsService(Command command)
    {
        this.command = command;
        model = ClientM.get();
        ser = new Serializer();
    }

    public CommandResult UpdatePlayerTurns() {
        CommandResult res = new CommandResult(command.getUUID());

        if (command.getOperation().equals("updatePlayerTurns")) {
            Type BooleanMType = new TypeToken<ArrayList<Boolean>>(){}.getType();
            ArrayList<Boolean> playerTurns = (ArrayList<Boolean>) ser.deserialize(command.getArg("playerTurns").getData(), BooleanMType);
            model.setPlayerTurns(playerTurns);
        }
        else {
            res.setError("Incorrect operation");
        }
        return res;
    }
}
