package com.ticket_to_ride.client.services;

import com.google.gson.reflect.TypeToken;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.data.MoveID;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UpdateMovesService {
    private Command command;
    private ClientM model;
    private Serializer ser;

    public UpdateMovesService(Command command)
    {
        this.command = command;
        model = ClientM.get();
        ser = new Serializer();
    }

    public CommandResult UpdateMoves()
    {
        CommandResult res = new CommandResult(command.getUUID());

        if (command.getOperation().equals("updateMoves"))
        {
            Type MoveMType = new TypeToken<ArrayList<MoveID>>(){}.getType();
            ArrayList<MoveID> availableMoves = (ArrayList<MoveID>)ser.deserialize(command.getArg("availableMoves").getData(), MoveMType);
            //TODO: Set available moves in client
            model.setValidMoves(availableMoves);
        }
        else
        {
            res.setError("Incorrect operation");
        }

        return res;
    }
}
