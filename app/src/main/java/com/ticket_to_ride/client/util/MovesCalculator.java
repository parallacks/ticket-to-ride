package com.ticket_to_ride.client.util;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.client.services.ClientTurnOverService;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.RouteM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.util.RouteMoveMatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class MovesCalculator {
    //Modified by Lance

    private ClientM model;
    private ArrayList<MoveID> availableMoves;

    public MovesCalculator()
    {
        model = ClientM.get();
        availableMoves = new ArrayList<>();
    }

    public ArrayList<RouteID> getNonClaimedRoutes()
    {
        ArrayList<RouteID> nonClaimedRoutes = new ArrayList<>();
        for (RouteM route : model.getActiveGame().getMap().getRoutes())
        {
            nonClaimedRoutes.add(route.getId());
        }
        for (PlayerInfoM player : model.getActiveGame().getPlayers())
        {
            for (RouteID id : player.getClaimedRoutes())
            {
                if (model.getActiveGame().getMap().getRouteById(id).isDouble()){
                    if(model.getActiveGame().getPlayers().size()<4){
                        //Find the match and remove
                        nonClaimedRoutes.remove(model.getActiveGame().getMap().findDoubleMatch(id));
                    }
                }
                nonClaimedRoutes.remove(id);
            }
        }
        return nonClaimedRoutes;
    }

    private void addAvailableRoutes()
    {
        RouteMoveMatcher match = new RouteMoveMatcher();
        ArrayList<RouteID> availableRoutes = getNonClaimedRoutes();
        for (RouteID routeID : availableRoutes)
        {
            availableMoves.add(match.matchMoveToRoute(routeID));
        }
    }

    private void filterDoubleRoutes()
    {
        //Created by Lance
        RouteMoveMatcher match = new RouteMoveMatcher();
        Iterator<MoveID> it = availableMoves.iterator();
        while(it.hasNext()) {
            MoveID move = it.next();
            if(move.toString().contains("CLAIM_ROUTE")) {
                String moveString = match.matchRouteToMove(move).toString();
                String baseRoute = moveString.substring(0, moveString.length()-2);
                for (RouteID routeID : model.getMyStats().getClaimedRoutes()){
                    if(routeID.toString().contains(baseRoute)){
                        it.remove();
                    }
                }
            }
        }
    }

    private void filterRoutesByAvailableCards()
    {
        RouteMoveMatcher match = new RouteMoveMatcher();
        ArrayList<MoveID> tempMoves = new ArrayList<>(availableMoves);
        Iterator<MoveID> it = tempMoves.iterator();
        while (it.hasNext())
        {
            MoveID move = it.next();
            RouteM route = model.getActiveGame().getMap().getRouteById(match.matchRouteToMove(move));
            TrainCardColor color = route.getRouteColor();

            if (color.equals(TrainCardColor.WILD))
            {
                if (model.getMyStats().getMaxNumCardsOfColor(model.getTrainCards()) + model.getMyStats().getNumCardsByColor(TrainCardColor.WILD, model.getTrainCards()) < route.getTrainCount())
                    availableMoves.remove(move);
            }
            else
            {
                if (model.getMyStats().getNumCardsByColor(color, model.getTrainCards()) + model.getMyStats().getNumCardsByColor(TrainCardColor.WILD, model.getTrainCards()) < route.getTrainCount())
                    availableMoves.remove(move);
            }
        }
    }

    public void CalculateMoves() {
        PlayerInfoM player = model.getMyStats();

        switch (player.getMoveCount()) {
            case 0:
                addAvailableRoutes();
                filterDoubleRoutes();
                filterRoutesByAvailableCards();
                availableMoves.add(MoveID.MOVE_DRAW_TRAIN_CARD);
                availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_1);
                availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_2);
                availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_3);
                availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_4);
                availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_5);
                availableMoves.add(MoveID.MOVE_DRAW_DESTINATION_CARDS);
                model.setValidMoves(availableMoves);
                break;
            case 1:
                if (player.getChooseCards()) {
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_KEEP);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_RTRN);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_KEEP);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_RTRN);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_KEEP);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_RTRN);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_RTRN_RTRN_KEEP);
                } else {
                    availableMoves.add(MoveID.MOVE_DRAW_TRAIN_CARD);
                    if (!model.getFaceUpTCards().getTray().get(0).getColor().equals(TrainCardColor.WILD))
                        availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_1);
                    if (!model.getFaceUpTCards().getTray().get(1).getColor().equals(TrainCardColor.WILD))
                        availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_2);
                    if (!model.getFaceUpTCards().getTray().get(2).getColor().equals(TrainCardColor.WILD))
                        availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_3);
                    if (!model.getFaceUpTCards().getTray().get(3).getColor().equals(TrainCardColor.WILD))
                        availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_4);
                    if (!model.getFaceUpTCards().getTray().get(4).getColor().equals(TrainCardColor.WILD))
                        availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_5);
                }
                model.setValidMoves(availableMoves);
                break;
            case 2:
                player.resetMoveCount();
                model.setValidMoves(availableMoves);
                ClientTurnOverService clientTurnOverService = new ClientTurnOverService();
                clientTurnOverService.TurnOver();
                //Prompt server for stats update
                break;
        }
    }
}
