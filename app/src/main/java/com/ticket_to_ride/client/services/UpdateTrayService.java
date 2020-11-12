package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.TrayM;

public class UpdateTrayService {
    private Command command;
    private ClientM model;
    private Serializer ser;

    public UpdateTrayService(Command command)
    {
        this.command = command;
        model = ClientM.get();
        ser = new Serializer();
    }

    public CommandResult UpdateTray()
    {
        CommandResult res = new CommandResult(command.getUUID());

        if (command.getOperation().equals("updateTray"))
        {
            TrayM tray = (TrayM)ser.deserialize(command.getArg("tray").getData(), TrayM.class);
            model.setFaceUpTCards(tray);
        }
        else
        {
            res.setError("Incorrect operation");
        }

        return res;
    }
}
