package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.ServerProxy;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.DestinationCardM;

import java.util.ArrayList;

public class ClientChooseCardService {

    private ClientM model;
    private Serializer serializer;
    private ArrayList<DestinationCardM> destinationDiscard;


    public ClientChooseCardService (ArrayList<DestinationCardM> destinationDiscard) {
        model = ClientM.get();
        this.serializer = new Serializer();
        this.destinationDiscard = destinationDiscard;
    }

    public ClientChooseCardService()
    {
        model = ClientM.get();
        this.serializer = new Serializer();
        this.destinationDiscard = null;
    }

    public boolean ChooseCards() {
        Command com = new Command();
        com.setOperation("chooseCards");

        if (this.destinationDiscard != null) {
            String destinationDiscardData = serializer.serialize(this.destinationDiscard);
            com.setArg("destinationDiscard", "ArrayList<DestinationCardM>", destinationDiscardData);
            //com.setArg("gameName", "String", model.getActiveGame().getGameName());
        }

        CommandResult res = ServerProxy.get().chooseCards(com);
        if (!res.successful())
        {
            //TODO set the error message otherwise just wait for the command to come back from the server
            model.setErrorMessage(res.getError());
        }
        else
        {
            //TODO: Here we should be adding the destination cards to the player's hand
            model.clearOptions();
        }

        return res.successful();
    }

}
