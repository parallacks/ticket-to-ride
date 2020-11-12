package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.ChatM;
import com.ticket_to_ride.common.model.GameHistoryM;

public class UpdateGameHistoryService {
    private Command command;
    private ClientM model;
    private Serializer ser;

    public UpdateGameHistoryService(Command command)
    {
        this.command = command;
        model = ClientM.get();
        ser = new Serializer();
    }

    public CommandResult UpdateGameHistory()
    {
        CommandResult res;
        if(command.getArg("gameHistory").getType().equals("GameHistoryM")) {
            GameHistoryM gameHistory = (GameHistoryM) this.ser.deserialize(this.command.getArg("gameHistory").getData(), GameHistoryM.class);
            this.model.addChat(gameHistory);
            res = new CommandResult(this.command.getUUID());
        }
        else {
            res = new CommandResult(command.getUUID());
            res.setError("Wrong type for GameHistoryM");
        }
        return res;
    }
}
