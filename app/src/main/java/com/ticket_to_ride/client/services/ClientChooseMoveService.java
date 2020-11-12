package com.ticket_to_ride.client.services;

import com.google.gson.reflect.TypeToken;
import com.ticket_to_ride.client.ServerProxy;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.DestCardOptions;
import com.ticket_to_ride.client.util.MovesCalculator;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.common.model.TrayM;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ClientChooseMoveService {
    private ClientM model;
    private Serializer ser;
    private MoveID move;
    private MovesCalculator calculator;

    public ClientChooseMoveService(MoveID move)
    {
        model = ClientM.get();
        ser = new Serializer();
        this.move = move;
        calculator = new MovesCalculator();
    }

    public boolean ChooseMove()
    {
        switch (move)
        {
            //Claiming routes
            case MOVE_CLAIM_ROUTE_ORGAHILL_TO_MADINO_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ORGAHILL_TO_MADINO_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ANPHONY_TO_MADINO_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ANPHONY_TO_MADINO_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_SAILANE_TO_MADINO_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_SAILANE_TO_MADINO_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ANPHONY_TO_AUGUSTY:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ANPHONY_TO_HEIRHEIN:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_HEIRHEIN_TO_EVANS:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_AUGUSTY_TO_MACKILY:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_AUGUSTY_TO_FREEGE:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_MACKILY_TO_EVANS:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_MACKILY_TO_EDDA:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_EVANS_TO_VERDANE_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_EVANS_TO_VERDANE_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_EVANS_TO_JUNGBY:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_VERDANE_TO_MARPHA_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_VERDANE_TO_MARPHA_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_MARPHA_TO_GENOA_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_MARPHA_TO_GENOA_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_GENOA_TO_MILETOS:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_GENOA_TO_RADOS:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_RADOS_TO_CHRONOS:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_CHRONOS_TO_MILETOS:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_CHRONOS_TO_MELGEN_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_CHRONOS_TO_MELGEN_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_JUNGBY_TO_EDDA:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_FREEGE_TO_DOZEL:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_DOZEL_TO_VELTHOME:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_VELTHOME_TO_EDDA_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_VELTHOME_TO_EDDA_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_VELTHOME_TO_YIED_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_VELTHOME_TO_YIED_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_VELTHOME_TO_PHINORA:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_YIED_TO_RIVOUGH_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_YIED_TO_RIVOUGH_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_SAILANE_TO_SILESIA:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_SILESIA_TO_ZAXON:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_SAILANE_TO_THROVE:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_THROVE_TO_ZAXON:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ZAXON_TO_PHINORA_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ZAXON_TO_PHINORA_2:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_PHINORA_TO_TIRNANOG:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_TIRNANOG_TO_SOPHARA:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_SOPHARA_TO_RIVOUGH:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_TIRNANOG_TO_ISAAC:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ISAAC_TO_RIVOUGH:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_EDDA_TO_MELGEN:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_LUTHECIA_TO_MELGEN:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_LUTHECIA_TO_GRUTIA:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_GRUTIA_TO_THRACIA:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_THRACIA_TO_KAPATHOGIA:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_THRACIA_TO_MANSTER:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ALSTER_TO_KAPATHOGIA:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ALSTER_TO_MELGEN:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_ALSTER_TO_MANSTER:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_MANSTER_TO_CONOTE_1:
                return ClaimRoute(move);
            case MOVE_CLAIM_ROUTE_MANSTER_TO_CONOTE_2:
                return ClaimRoute(move);
                // Draw Train Card move
            case MOVE_DRAW_TRAIN_CARD:
                return DrawTrainCard(move);
                // Take Shop card moves
            case MOVE_TAKE_SHOP_CARD_1:
                return DrawTrayCard(move);
            case MOVE_TAKE_SHOP_CARD_2:
                return DrawTrayCard(move);
            case MOVE_TAKE_SHOP_CARD_3:
                return DrawTrayCard(move);
            case MOVE_TAKE_SHOP_CARD_4:
                return DrawTrayCard(move);
            case MOVE_TAKE_SHOP_CARD_5:
                return DrawTrayCard(move);
                // Draw Destination Cards
            case MOVE_DRAW_DESTINATION_CARDS:
                return DrawDestinationCard(move);
            // Keeping Destination Cards
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_KEEP:
                return ChooseDestinationCards(move);
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_RTRN:
                return ChooseDestinationCards(move);
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_KEEP:
                return ChooseDestinationCards(move);
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_RTRN:
                return ChooseDestinationCards(move);
            case MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_KEEP:
                return ChooseDestinationCards(move);
            case MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_RTRN:
                return ChooseDestinationCards(move);
            case MOVE_CONFIRM_DESTINATION_CARDS_RTRN_RTRN_KEEP:
                return ChooseDestinationCards(move);
            default:
                return false;
        }
    }

    private boolean ClaimRoute(MoveID move)
    {
        Command com = new Command();
        com.setOperation("chooseMove");

        com.setArg("move", "MoveID", move.toString());

        CommandResult res = ServerProxy.get().chooseMove(com);

        if (!res.successful())
        {
            model.setErrorMessage(res.getError());
        }
        else
        {
            //TODO: Update the view for the claimed route; send back player info
            RouteID routeID = (RouteID)ser.deserialize(res.getArg("routeID").getData(), RouteID.class);
            model.addToMyClaimedRoutes(routeID);
            Type TrainCardMType = new TypeToken<ArrayList<TrainCardM>>(){}.getType();
            ArrayList<TrainCardM> trainCards = (ArrayList<TrainCardM>)ser.deserialize(res.getArg("hand").getData(), TrainCardMType);
            model.setTrainCards(trainCards);
            model.getMyStats().setEndTurn();
            calculator.CalculateMoves();
        }

        return res.successful();
    }

    private boolean DrawDestinationCard(MoveID move)
    {
        Command com = new Command();
        com.setOperation("chooseMove");

        com.setArg("move", "MoveID", move.toString());

        CommandResult res = ServerProxy.get().chooseMove(com);

        if (!res.successful())
        {
            model.setErrorMessage(res.getError());
        }
        else
        {
            CommandArg destinationCards = res.getArg("destinationCards");
            Type DestinationCardMType = new TypeToken<ArrayList<DestinationCardM>>(){}.getType();
            ArrayList<DestinationCardM> cards = (ArrayList<DestinationCardM>)ser.deserialize(destinationCards.getData(), DestinationCardMType);
            DestCardOptions options = new DestCardOptions();
            options.setOptions(cards);
            model.setDestCardOptions(options);
            model.getMyStats().incrementMoveCount();
            calculator.CalculateMoves();
            //This would now go to ClientChooseCards from the presenter
        }

        return res.successful();
    }

    private boolean ChooseDestinationCards(MoveID move)
    {
        Command com = new Command();
        com.setOperation("chooseMove");

        com.setArg("move", "MoveID", move.toString());
        ArrayList<DestinationCardM> discard = new ArrayList<>();
        String cardData;
        switch (move)
        {
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_RTRN:
                discard.add(model.getDestCardOptions().getOptions().remove(2));
                cardData = ser.serialize(discard);
                com.setArg("destinationDiscard", "ArrayList<DestinationCardM>", cardData);
                break;
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_KEEP:
                discard.add(model.getDestCardOptions().getOptions().remove(1));
                cardData = ser.serialize(discard);
                com.setArg("destinationDiscard", "ArrayList<DestinationCardM>", cardData);
                break;
            case MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_KEEP:
                discard.add(model.getDestCardOptions().getOptions().remove(0));
                cardData = ser.serialize(discard);
                com.setArg("destinationDiscard", "ArrayList<DestinationCardM>", cardData);
                break;
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_RTRN:
                discard.add(model.getDestCardOptions().getOptions().get(1));
                discard.add(model.getDestCardOptions().getOptions().get(2));
                cardData = ser.serialize(discard);
                com.setArg("destinationDiscard", "ArrayList<DestinationCardM>", cardData);
                break;
            case MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_RTRN:
                discard.add(model.getDestCardOptions().getOptions().get(0));
                discard.add(model.getDestCardOptions().getOptions().get(2));
                cardData = ser.serialize(discard);
                com.setArg("destinationDiscard", "ArrayList<DestinationCardM>", cardData);
                break;
            case MOVE_CONFIRM_DESTINATION_CARDS_RTRN_RTRN_KEEP:
                discard.add(model.getDestCardOptions().getOptions().get(0));
                discard.add(model.getDestCardOptions().getOptions().get(1));
                cardData = ser.serialize(discard);
                com.setArg("destinationDiscard", "ArrayList<DestinationCardM>", cardData);
                break;
        }

        CommandResult res = ServerProxy.get().chooseMove(com);

        if (!res.successful())
        {
            model.setErrorMessage(res.getError());
        }
        else
        {
            model.clearOptions();
            model.getMyStats().setEndTurn();
            calculator.CalculateMoves();
        }

        return res.successful();
    }

    private boolean DrawTrainCard(MoveID move)
    {
        Command command = new Command();
        command.setOperation("chooseMove");

        command.setArg("move", "MoveID", move.toString());
        CommandResult res = ServerProxy.get().chooseMove(command);

        if (!res.successful())
        {
            model.setErrorMessage(res.getError());
        }
        else
        {
            TrainCardM card = (TrainCardM)ser.deserialize(res.getArg("trainCard").getData(), TrainCardM.class);
            model.addTrainCard(card);
            model.getMyStats().incrementMoveCount();
            calculator.CalculateMoves();
        }

        return res.successful();
    }

    private boolean DrawTrayCard(MoveID move)
    {
        Command command = new Command();
        command.setOperation("chooseMove");

        command.setArg("move", "MoveID", move.toString());
        CommandResult res = ServerProxy.get().chooseMove(command);

        if (!res.successful())
        {
            model.setErrorMessage(res.getError());
        }
        else
        {
            TrainCardM card = (TrainCardM)ser.deserialize(res.getArg("trayCard").getData(), TrainCardM.class);
            TrayM tray = (TrayM)ser.deserialize(res.getArg("tray").getData(), TrayM.class);
            model.addTrainCard(card);
            model.setFaceUpTCards(tray);
            if (card.getColor().equals(TrainCardColor.WILD))
                model.getMyStats().setEndTurn();
            else
                model.getMyStats().incrementMoveCount();
            calculator.CalculateMoves();
        }

        return res.successful();
    }
}
