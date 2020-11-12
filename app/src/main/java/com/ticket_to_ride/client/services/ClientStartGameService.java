//Alex Yancey
package com.ticket_to_ride.client.services;

import com.google.gson.reflect.TypeToken;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.GameInfoM;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.client.util.MovesCalculator;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.HandM;
import com.ticket_to_ride.common.model.PlayerColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.common.model.TrayM;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ClientStartGameService
{
    private Command command;
    private Serializer serializer;
    private ClientM model;

    public ClientStartGameService(Command command)
    {
        this.command = command;
        this.serializer = new Serializer();
        this.model = ClientM.get();
    }

    public void StartGame()
    {
        CommandArg destinations = command.getArg("startingDestinations");
        Type DestinationCardMType = new TypeToken<ArrayList<DestinationCardM>>(){}.getType();
        ArrayList<DestinationCardM> startingDestinations = (ArrayList<DestinationCardM>)serializer.deserialize(destinations.getData(), DestinationCardMType);
        CommandArg trainCards = command.getArg("startingTrainCards");
        Type TrainCardMType = new TypeToken<ArrayList<TrainCardM>>(){}.getType();
        ArrayList<TrainCardM> startingTrainCards = (ArrayList<TrainCardM>)serializer.deserialize(trainCards.getData(), TrainCardMType);
        int destinationDeckSize = Integer.parseInt(command.getArg("destinationDeckSize").getData());
        int trainDeckSize = Integer.parseInt(command.getArg("trainDeckSize").getData());
        TrayM tray = (TrayM)serializer.deserialize(command.getArg("tray").getData(), TrayM.class);
        boolean isTurn = (Boolean)serializer.deserialize(command.getArg("turn").getData(), Boolean.class);
        PlayerColor color = (PlayerColor)serializer.deserialize(command.getArg("color").getData(), PlayerColor.class);

        this.model.setStartingCards(startingDestinations, startingTrainCards);
        this.model.setNumTrainCards(trainDeckSize);
        this.model.setNumDestCards(destinationDeckSize);
        this.model.setFaceUpTCards(tray);
        this.model.getMyStats().setTurn(isTurn);
        this.model.getMyStats().setColor(color);
        this.model.getActiveGame().getPlayers().get(0).setColor(PlayerColor.RED);
        this.model.getActiveGame().getPlayers().get(1).setColor(PlayerColor.BLUE);
        if (this.model.getActiveGame().getPlayers().size() > 2) {
            this.model.getActiveGame().getPlayers().get(2).setColor(PlayerColor.GREEN);
        }
        if (this.model.getActiveGame().getPlayers().size() > 3) {
            this.model.getActiveGame().getPlayers().get(3).setColor(PlayerColor.YELLOW);
        }
        if (this.model.getActiveGame().getPlayers().size() > 4) {
            this.model.getActiveGame().getPlayers().get(4).setColor(PlayerColor.PURPLE);
        }
        for (PlayerInfoM player : this.model.getActiveGame().getPlayers())
        {
            player.setPoints(0);
            player.setNumTrains(20);
            player.setNumTrainCards(4);
        }
        ClientM.get().setActiveGameStatus("Active");

        if (isTurn)
        {
            MovesCalculator movesCalculator = new MovesCalculator();
            movesCalculator.CalculateMoves();
        }

        //TODO: DELETE IN BETWEEN THIS COMMENT
        //MockServiceCaller mockServiceCaller = new MockServiceCaller();
        //mockServiceCaller.initStatusDisplayForPhase2();
        //TODO: AND THIS COMMENT (only for phase 2 pass off)
    }
}
