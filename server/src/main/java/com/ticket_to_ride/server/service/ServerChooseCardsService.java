package com.ticket_to_ride.server.service;

import com.google.gson.reflect.TypeToken;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.UserM;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

public class ServerChooseCardsService {

    private UUID commandId;
    private UserM user;
    private Serializer serializer;
    private ArrayList<DestinationCardM> destinationDiscard;
    //private String gameName;

    public ServerChooseCardsService (Command data, UserM user){
        commandId = data.getUUID();
        this.user = user;
        serializer = new Serializer();
        Type DestinationCardMType = new TypeToken<ArrayList<DestinationCardM>>(){}.getType();
        this.destinationDiscard = (ArrayList<DestinationCardM>) serializer.deserialize(data.getArg("destinationDiscard").getData(), DestinationCardMType);
    }

    public CommandResult execute () {
        GameInfoM context = user.getGame();
        if (this.destinationDiscard != null) {
            for (DestinationCardM card : this.destinationDiscard)
                context.addToDestinationDeck(card);
            PlayerInfoM player = context.getPlayerByUsername(user.getUsername());
            for(DestinationCardM dest : destinationDiscard)
                player.getDestinationCards().getHand().remove(dest);
        }
        CommandResult res = new CommandResult(commandId);
        return res;
    }
}
