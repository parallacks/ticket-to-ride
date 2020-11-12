package com.ticket_to_ride.server.service;

import com.google.gson.reflect.TypeToken;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.RouteM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.UserM;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ServerChooseMoveService {
    private Command data;
    private Serializer ser;
    private GameInfoM context;
    private UserM caller;
    private PlayerInfoM player;

    public ServerChooseMoveService(Command data, UserM caller)
    {
        this.data = data;
        ser = new Serializer();
        this.caller = caller;
        context = caller.getGame();
        player = context.getPlayerByUsername(caller.getUsername());
    }

    public CommandResult execute()
    {
        MoveID move = (MoveID) ser.deserialize(data.getArg("move").getData(), MoveID.class);
        switch (move) {
            //Claiming routes
            case MOVE_CLAIM_ROUTE_ORGAHILL_TO_MADINO_1:
                return ClaimRoute(RouteID.ORGAHILL_TO_MADINO_1);
            case MOVE_CLAIM_ROUTE_ORGAHILL_TO_MADINO_2:
                return ClaimRoute(RouteID.ORGAHILL_TO_MADINO_2);
            case MOVE_CLAIM_ROUTE_ANPHONY_TO_MADINO_1:
                return ClaimRoute(RouteID.ANPHONY_TO_MADINO_1);
            case MOVE_CLAIM_ROUTE_ANPHONY_TO_MADINO_2:
                return ClaimRoute(RouteID.ANPHONY_TO_MADINO_2);
            case MOVE_CLAIM_ROUTE_SAILANE_TO_MADINO_1:
                return ClaimRoute(RouteID.SAILANE_TO_MADINO_1);
            case MOVE_CLAIM_ROUTE_SAILANE_TO_MADINO_2:
                return ClaimRoute(RouteID.SAILANE_TO_MADINO_2);
            case MOVE_CLAIM_ROUTE_ANPHONY_TO_AUGUSTY:
                return ClaimRoute(RouteID.ANPHONY_TO_AUGUSTY);
            case MOVE_CLAIM_ROUTE_ANPHONY_TO_HEIRHEIN:
                return ClaimRoute(RouteID.ANPHONY_TO_HEIRHEIN);
            case MOVE_CLAIM_ROUTE_HEIRHEIN_TO_EVANS:
                return ClaimRoute(RouteID.HEIRHEIN_TO_EVANS);
            case MOVE_CLAIM_ROUTE_AUGUSTY_TO_MACKILY:
                return ClaimRoute(RouteID.AUGUSTY_TO_MACKILY);
            case MOVE_CLAIM_ROUTE_AUGUSTY_TO_FREEGE:
                return ClaimRoute(RouteID.AUGUSTY_TO_FREEGE);
            case MOVE_CLAIM_ROUTE_MACKILY_TO_EVANS:
                return ClaimRoute(RouteID.MACKILY_TO_EVANS);
            case MOVE_CLAIM_ROUTE_MACKILY_TO_EDDA:
                return ClaimRoute(RouteID.MACKILY_TO_EDDA);
            case MOVE_CLAIM_ROUTE_EVANS_TO_VERDANE_1:
                return ClaimRoute(RouteID.EVANS_TO_VERDANE_1);
            case MOVE_CLAIM_ROUTE_EVANS_TO_VERDANE_2:
                return ClaimRoute(RouteID.EVANS_TO_VERDANE_2);
            case MOVE_CLAIM_ROUTE_EVANS_TO_JUNGBY:
                return ClaimRoute(RouteID.EVANS_TO_JUNGBY);
            case MOVE_CLAIM_ROUTE_VERDANE_TO_MARPHA_1:
                return ClaimRoute(RouteID.VERDANE_TO_MARPHA_1);
            case MOVE_CLAIM_ROUTE_VERDANE_TO_MARPHA_2:
                return ClaimRoute(RouteID.VERDANE_TO_MARPHA_2);
            case MOVE_CLAIM_ROUTE_MARPHA_TO_GENOA_1:
                return ClaimRoute(RouteID.MARPHA_TO_GENOA_1);
            case MOVE_CLAIM_ROUTE_MARPHA_TO_GENOA_2:
                return ClaimRoute(RouteID.MARPHA_TO_GENOA_2);
            case MOVE_CLAIM_ROUTE_GENOA_TO_MILETOS:
                return ClaimRoute(RouteID.GENOA_TO_MILETOS);
            case MOVE_CLAIM_ROUTE_GENOA_TO_RADOS:
                return ClaimRoute(RouteID.GENOA_TO_RADOS);
            case MOVE_CLAIM_ROUTE_RADOS_TO_CHRONOS:
                return ClaimRoute(RouteID.RADOS_TO_CHRONOS);
            case MOVE_CLAIM_ROUTE_CHRONOS_TO_MILETOS:
                return ClaimRoute(RouteID.CHRONOS_TO_MILETOS);
            case MOVE_CLAIM_ROUTE_CHRONOS_TO_MELGEN_1:
                return ClaimRoute(RouteID.CHRONOS_TO_MELGEN_1);
            case MOVE_CLAIM_ROUTE_CHRONOS_TO_MELGEN_2:
                return ClaimRoute(RouteID.CHRONOS_TO_MELGEN_2);
            case MOVE_CLAIM_ROUTE_JUNGBY_TO_EDDA:
                return ClaimRoute(RouteID.JUNGBY_TO_EDDA);
            case MOVE_CLAIM_ROUTE_FREEGE_TO_DOZEL:
                return ClaimRoute(RouteID.FREEGE_TO_DOZEL);
            case MOVE_CLAIM_ROUTE_DOZEL_TO_VELTHOME:
                return ClaimRoute(RouteID.DOZEL_TO_VELTHOME);
            case MOVE_CLAIM_ROUTE_VELTHOME_TO_EDDA_1:
                return ClaimRoute(RouteID.VELTHOME_TO_EDDA_1);
            case MOVE_CLAIM_ROUTE_VELTHOME_TO_EDDA_2:
                return ClaimRoute(RouteID.VELTHOME_TO_EDDA_2);
            case MOVE_CLAIM_ROUTE_VELTHOME_TO_YIED_1:
                return ClaimRoute(RouteID.VELTHOME_TO_YIED_1);
            case MOVE_CLAIM_ROUTE_VELTHOME_TO_YIED_2:
                return ClaimRoute(RouteID.VELTHOME_TO_YIED_2);
            case MOVE_CLAIM_ROUTE_VELTHOME_TO_PHINORA:
                return ClaimRoute(RouteID.VELTHOME_TO_PHINORA);
            case MOVE_CLAIM_ROUTE_YIED_TO_RIVOUGH_1:
                return ClaimRoute(RouteID.YIED_TO_RIVOUGH_1);
            case MOVE_CLAIM_ROUTE_YIED_TO_RIVOUGH_2:
                return ClaimRoute(RouteID.YIED_TO_RIVOUGH_2);
            case MOVE_CLAIM_ROUTE_SAILANE_TO_SILESIA:
                return ClaimRoute(RouteID.SAILANE_TO_SILESIA);
            case MOVE_CLAIM_ROUTE_SILESIA_TO_ZAXON:
                return ClaimRoute(RouteID.SILESIA_TO_ZAXON);
            case MOVE_CLAIM_ROUTE_SAILANE_TO_THROVE:
                return ClaimRoute(RouteID.SAILANE_TO_THROVE);
            case MOVE_CLAIM_ROUTE_THROVE_TO_ZAXON:
                return ClaimRoute(RouteID.THROVE_TO_ZAXON);
            case MOVE_CLAIM_ROUTE_ZAXON_TO_PHINORA_1:
                return ClaimRoute(RouteID.ZAXON_TO_PHINORA_1);
            case MOVE_CLAIM_ROUTE_ZAXON_TO_PHINORA_2:
                return ClaimRoute(RouteID.ZAXON_TO_PHINORA_2);
            case MOVE_CLAIM_ROUTE_PHINORA_TO_TIRNANOG:
                return ClaimRoute(RouteID.PHINORA_TO_TIRNANOG);
            case MOVE_CLAIM_ROUTE_TIRNANOG_TO_SOPHARA:
                return ClaimRoute(RouteID.TIRNANOG_TO_SOPHARA);
            case MOVE_CLAIM_ROUTE_SOPHARA_TO_RIVOUGH:
                return ClaimRoute(RouteID.SOPHARA_TO_RIVOUGH);
            case MOVE_CLAIM_ROUTE_TIRNANOG_TO_ISAAC:
                return ClaimRoute(RouteID.TIRNANOG_TO_ISAAC);
            case MOVE_CLAIM_ROUTE_ISAAC_TO_RIVOUGH:
                return ClaimRoute(RouteID.ISAAC_TO_RIVOUGH);
            case MOVE_CLAIM_ROUTE_EDDA_TO_MELGEN:
                return ClaimRoute(RouteID.EDDA_TO_MELGEN);
            case MOVE_CLAIM_ROUTE_LUTHECIA_TO_MELGEN:
                return ClaimRoute(RouteID.LUTHECIA_TO_MELGEN);
            case MOVE_CLAIM_ROUTE_LUTHECIA_TO_GRUTIA:
                return ClaimRoute(RouteID.LUTHECIA_TO_GRUTIA);
            case MOVE_CLAIM_ROUTE_GRUTIA_TO_THRACIA:
                return ClaimRoute(RouteID.GRUTIA_TO_THRACIA);
            case MOVE_CLAIM_ROUTE_THRACIA_TO_KAPATHOGIA:
                return ClaimRoute(RouteID.THRACIA_TO_KAPATHOGIA);
            case MOVE_CLAIM_ROUTE_THRACIA_TO_MANSTER:
                return ClaimRoute(RouteID.THRACIA_TO_MANSTER);
            case MOVE_CLAIM_ROUTE_ALSTER_TO_KAPATHOGIA:
                return ClaimRoute(RouteID.ALSTER_TO_KAPATHOGIA);
            case MOVE_CLAIM_ROUTE_ALSTER_TO_MELGEN:
                return ClaimRoute(RouteID.ALSTER_TO_MELGEN);
            case MOVE_CLAIM_ROUTE_ALSTER_TO_MANSTER:
                return ClaimRoute(RouteID.ALSTER_TO_MANSTER);
            case MOVE_CLAIM_ROUTE_MANSTER_TO_CONOTE_1:
                return ClaimRoute(RouteID.MANSTER_TO_CONOTE_1);
            case MOVE_CLAIM_ROUTE_MANSTER_TO_CONOTE_2:
                return ClaimRoute(RouteID.MANSTER_TO_CONOTE_2);
            // Draw Train Card move
            case MOVE_DRAW_TRAIN_CARD:
                return DrawTrainCard();
            // Take Shop card moves
            case MOVE_TAKE_SHOP_CARD_1:
                return DrawTrayCard(0);
            case MOVE_TAKE_SHOP_CARD_2:
                return DrawTrayCard(1);
            case MOVE_TAKE_SHOP_CARD_3:
                return DrawTrayCard(2);
            case MOVE_TAKE_SHOP_CARD_4:
                return DrawTrayCard(3);
            case MOVE_TAKE_SHOP_CARD_5:
                return DrawTrayCard(4);
            // Draw Destination Cards
            case MOVE_DRAW_DESTINATION_CARDS:
                return DrawDestinationCard();
            // Keep Destination Cards
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_KEEP:
                return ChooseDestinationCards();
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_RTRN:
                return ChooseDestinationCards();
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_KEEP:
                return ChooseDestinationCards();
            case MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_RTRN:
                return ChooseDestinationCards();
            case MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_KEEP:
                return ChooseDestinationCards();
            case MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_RTRN:
                return ChooseDestinationCards();
            case MOVE_CONFIRM_DESTINATION_CARDS_RTRN_RTRN_KEEP:
                return ChooseDestinationCards();
            default:
                return null;
        }
    }

    private CommandResult DrawTrayCard(int position)
    {
        CommandResult res = new CommandResult(data.getUUID());
        TrainCardM card = context.drawFromTray(position);
        if (card.getColor().equals(TrainCardColor.WILD) && player.getMoveCount() == 1)
        {
            res.setError("Cannot draw wild card from tray as a second move");
            return res;
        }
        String cardData = ser.serialize(card);
        res.setArg("trayCard", "TrainCardM", cardData);
        String trayData = ser.serialize(context.getTray());
        res.setArg("tray", "TrayM", trayData);
        //Add another card to the tray from the deck
        context.historyDrawTrayCard(caller.getUsername(), position);
        if (card.getColor().equals(TrainCardColor.WILD))
            player.setEndTurn();
        else
            player.incrementMoveCount();
        player.setChooseCards(false);
        context.addPlayerTrainCard(player, card);

        return res;
    }

    private CommandResult DrawTrainCard()
    {
        CommandResult res = new CommandResult(data.getUUID());
        TrainCardM card = context.drawFromTrainDeck();
        String cardData = ser.serialize(card);
        res.setArg("trainCard", "TrainCardM", cardData);
        context.historyDrawTrainCard(caller.getUsername());
        player.incrementMoveCount();
        player.setChooseCards(false);
        context.addPlayerTrainCard(player, card);

        return res;
    }

    private CommandResult DrawDestinationCard()
    {
        CommandResult res = new CommandResult(data.getUUID());
        ArrayList<DestinationCardM> destinationCards = context.drawFromDestinationDeck();
        String destinationCardData = ser.serialize(destinationCards);
        res.setArg("destinationCards", "ArrayList<DestinationCardM>", destinationCardData);
        context.historyDrawDestinationCards(caller.getUsername());
        player.incrementMoveCount();
        player.setChooseCards(true);
        context.addPlayerDestinationCard(player, destinationCards);

        return res;
    }

    private CommandResult ChooseDestinationCards()
    {
        CommandResult res = new CommandResult(data.getUUID());
        Type DestinationCardMType = new TypeToken<ArrayList<DestinationCardM>>(){}.getType();
        ArrayList<DestinationCardM> destinationDiscard = (ArrayList<DestinationCardM>) ser.deserialize(data.getArg("destinationDiscard").getData(), DestinationCardMType);
        for(DestinationCardM dest : destinationDiscard)
            player.getDestinationCards().getHand().remove(dest);
        for (DestinationCardM card : destinationDiscard)
        {
            context.addToDestinationDeck(card);
        }
        player.setEndTurn();

        return res;
    }

    private CommandResult ClaimRoute(RouteID routeID)
    {
        PlayerInfoM player = context.getPlayerByUsername(caller.getUsername());
        player.setEndTurn();
        player.setChooseCards(false);
        RouteM route = context.getMap().getRouteById(routeID);
        ArrayList<TrainCardM> discardedCards = player.getTrainCards().removeFromHand(route.getRouteColor(), route.getTrainCount());
        player.setNumTrains(player.getNumTrains() - route.getTrainCount());
        player.setPoints(player.getPoints() + route.getPoints());
        for (TrainCardM card : discardedCards)
            context.addToDiscard(card);
        context.historyClaimRoute(caller.getUsername(), route);
        context.addClaimedRoute(player, routeID);
        context.setClaimedRoute(routeID);
        CommandResult res = new CommandResult(data.getUUID());
        String routeData = ser.serialize(routeID);
        res.setArg("routeID", "RouteID", routeData);
        String handString = ser.serialize(player.getTrainCards().getHand());
        res.setArg("hand", "ArrayList<TrainCardM>", handString);
        return res;
    }
}
