package com.ticket_to_ride.client.services;

import com.google.gson.reflect.TypeToken;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.client.util.MovesCalculator;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.ChatListM;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.HandM;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.common.model.TrayM;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UpdatePlayerStatsService {
    private Command command;
    private ClientM model;
    private Serializer ser;
    private PlayerInfoM playerToUpdate;
    private MovesCalculator calculator;

    public UpdatePlayerStatsService(Command command)
    {
        this.command = command;
        model = ClientM.get();
        ser = new Serializer();
        calculator = new MovesCalculator();
    }

    public CommandResult UpdatePlayerStats()
    {
        CommandResult res = new CommandResult(command.getUUID());

        if (command.getOperation().equals("updatePlayerStats")) {
            String playerName = (String) ser.deserialize(command.getArg("playerName").getData(), String.class);
            //playerToUpdate = model.getActiveGame().getPlayerByUsername(playerName);
//            Type TrainHandMType = new TypeToken<HandM<TrainCardM>>(){}.getType();
//            HandM<TrainCardM> trainCards = (HandM<TrainCardM>) ser.deserialize(command.getArg("trainCards").getData(), TrainHandMType);
//            Type DestHandMType = new TypeToken<HandM<DestinationCardM>>(){}.getType();
//            HandM<DestinationCardM> destinationCards = (HandM<DestinationCardM>) ser.deserialize(command.getArg("destinationCards").getData(), DestHandMType);
            int numTrainCards = (Integer) ser.deserialize(command.getArg("numTrainCards").getData(), Integer.class);
            int numDestinationCards = (Integer) ser.deserialize(command.getArg("numDestinationCards").getData(), Integer.class);
            int numTrainCars = (Integer) ser.deserialize(command.getArg("numTrainCars").getData(), Integer.class);
            int points = (Integer) ser.deserialize(command.getArg("points").getData(), Integer.class);
            RouteID claimedRoute = (RouteID) ser.deserialize(command.getArg("claimedRoute").getData(),RouteID.class);
            TrayM trayM = (TrayM) ser.deserialize(command.getArg("tray").getData(), TrayM.class);
            int trainDeckCount = (Integer) ser.deserialize(command.getArg("trainDeckCount").getData(), Integer.class);
            int destinationDeckCount = (Integer) ser.deserialize(command.getArg("destinationDeckCount").getData(), Integer.class);
            Type BooleanMType = new TypeToken<ArrayList<Boolean>>(){}.getType();
            ArrayList<Boolean> playerTurns = (ArrayList<Boolean>) ser.deserialize(command.getArg("playerTurns").getData(), BooleanMType);
            String lastRound = (String)ser.deserialize(command.getArg("lastRound").getData(), String.class);
            ChatListM chatList = (ChatListM)ser.deserialize(command.getArg("chatList").getData(), ChatListM.class);

            if(claimedRoute!=null){
                model.getActiveGame().getPlayerByUsername(playerName).addClaimedRoute(claimedRoute);
                model.addClaimedRoutes(claimedRoute, model.getActiveGame().getPlayerByUsername(playerName));
            }
            model.setFaceUpTCards(trayM);
            model.setPlayerNumTrainCards(playerName, numTrainCards);
            model.setPlayerNumDestinationCards(playerName, numDestinationCards);
            model.setPlayerNumTrains(playerName, numTrainCars);
            model.setPlayerPoints(playerName, points);
            if (lastRound.equals("true"))
                model.setLastRound(true);
            else
                model.setLastRound(false);
            model.setPlayerTurns(playerTurns);
            model.setNumDestCards(destinationDeckCount);
            model.setNumTrainCards(trainDeckCount);
            model.setChatList(chatList);

//            if (playerName.equals(model.getMyStats().getUserM().getUsername()))
//            {
//                model.setTrainCards(trainCards);
//                model.setDestinationCards(destinationCards);
//            }

            if ((ser.deserialize(command.getArg("lastRound").getData(), String.class)).equals("true")) {
                model.setLastRound(true);
            }

            if (model.getActiveGame().getPlayerByUsername(model.getUser().getUsername()).isTurn())
            {
                calculator.CalculateMoves();
            }
        }
        else
        {
            res.setError("Incorrect operation");
        }
        return res;
    }
}
