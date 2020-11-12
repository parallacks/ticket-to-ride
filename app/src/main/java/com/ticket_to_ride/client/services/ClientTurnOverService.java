package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.ServerProxy;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;

public class ClientTurnOverService {
    private Serializer ser;
    private ClientM model;

    public ClientTurnOverService()
    {
        ser = new Serializer();
        model = ClientM.get();
    }

    public void TurnOver()
    {
        Command command = new Command();
        command.setOperation("turnOver");
        command.setArg("gameName", "String", model.getActiveGame().getGameName());
        CommandResult res = ServerProxy.get().turnOver(command);
        if(!res.successful()){
            model.setErrorMessage(res.getError());
        }
    }
}
