package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.ServerProxy;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.client.model.ClientM;

public class ClientSetPlayerReadyService {
    private ClientM model;

    public ClientSetPlayerReadyService()
    {
        this.model = ClientM.get();
    }

    public void execute() {
        Command command = new Command();
        command.setOperation("setReady");
        command.setArg("gameName", "String", model.getActiveGame().getGameName());
        CommandResult res = ServerProxy.get().setReady(command);
        if (!res.successful()) {
            model.setErrorMessage(res.getError());

        } else {
            model.setReady(true);
        }
    }
}
