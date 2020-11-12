package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.ServerProxy;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.GameHistoryM;

public class ClientGameHistoryService {
    private ClientM model;
    private Serializer serializer;
    private GameHistoryM history;

    public ClientGameHistoryService(GameHistoryM history)
    {
        model = ClientM.get();
        serializer = new Serializer();
        this.history = history;
    }

    public void SendGameHistory()
    {
        Command command = new Command();
        command.setOperation("sendGameHistory");
        String historyData = serializer.serialize(this.history);
        command.setArg("gameHistory", "GameHistoryM", historyData);
        CommandResult res = ServerProxy.get().sendGameHistory(command);
        if (!res.successful())
        {
            model.setErrorMessage(res.getError());
        }
    }
}
