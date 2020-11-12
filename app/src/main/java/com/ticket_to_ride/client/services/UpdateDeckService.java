package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.ChatM;

public class UpdateDeckService {
    private Command command;
    private Serializer serializer;
    private ClientM model;

    public UpdateDeckService(Command command)
    {
        this.command = command;
        this.serializer = new Serializer();
        this.model = ClientM.get();
    }

    public CommandResult UpdateDeck()
    {
        CommandResult res = new CommandResult(this.command.getUUID());

        if (command.getOperation().equals("updateDestinationDeck"))
        {
            int destinationDeckSize = Integer.parseInt((String)this.serializer.deserialize(this.command.getArg("deckCount").getData(), String.class));
            model.setNumDestCards(destinationDeckSize);
        }
        else if (command.getOperation().equals("updateTrainDeck"))
        {
            int trainCardDeckSize = Integer.parseInt((String)this.serializer.deserialize(this.command.getArg("deckCount").getData(), String.class));
            model.setNumTrainCards(trainCardDeckSize);
        }
        else
        {
            res.setError("Incorrect operation");
        }
        return res;
    }
}
