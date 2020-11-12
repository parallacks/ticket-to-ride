package com.ticket_to_ride.server.watcher;
//Created By Alex
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.RouteM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.util.RouteMoveMatcher;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.SessionM;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

public class MovesWatcher implements Observer {
    private GameInfoM context;
    private PlayerInfoM player;
    private ServerM serverM;
    private Serializer ser;
    private ArrayList<MoveID> availableMoves;

    public MovesWatcher(GameInfoM context) {
        this.context = context;
        this.player = null;
        serverM = ServerM.get();
        ser = new Serializer();
        availableMoves = new ArrayList<>();
    }

    //Modified by Lance
    private void addAvailableRoutes()
    {
        RouteMoveMatcher match = new RouteMoveMatcher();
        ArrayList<RouteID> availableRoutes = context.getNonClaimedRoutes();
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
                for (RouteID routeID : player.getClaimedRoutes()){
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
            RouteM route = context.getMap().getRouteById(match.matchRouteToMove(move));
            TrainCardColor color = route.getRouteColor();

            if (color.equals(TrainCardColor.WILD))
            {
                if (player.getMaxNumCardsOfColor() + player.getNumCardsByColor(TrainCardColor.WILD) < route.getTrainCount())
                    availableMoves.remove(move);
            }
            else
            {
                if (player.getNumCardsByColor(color) + player.getNumCardsByColor(TrainCardColor.WILD) < route.getTrainCount())
                    availableMoves.remove(move);
            }
        }
    }

    @Override
    public void update(Observable target, Object arg) {
        if (!arg.getClass().equals(PlayerInfoM.class))
            return;
        player = (PlayerInfoM)arg;


        switch (player.getMoveCount())
        {
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
                break;
            case 1:
                if (player.getChooseCards())
                {
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_KEEP);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_RTRN);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_KEEP);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_RTRN);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_KEEP);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_RTRN);
                    availableMoves.add(MoveID.MOVE_CONFIRM_DESTINATION_CARDS_RTRN_RTRN_KEEP);
                }
                else
                {
                    availableMoves.add(MoveID.MOVE_DRAW_TRAIN_CARD);
                    if (!context.getTray().getTray().get(0).getColor().equals(TrainCardColor.WILD))
                        availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_1);
                    if (!context.getTray().getTray().get(1).getColor().equals(TrainCardColor.WILD))
                        availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_2);
                    if (!context.getTray().getTray().get(2).getColor().equals(TrainCardColor.WILD))
                        availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_3);
                    if (!context.getTray().getTray().get(3).getColor().equals(TrainCardColor.WILD))
                        availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_4);
                    if (!context.getTray().getTray().get(4).getColor().equals(TrainCardColor.WILD))
                        availableMoves.add(MoveID.MOVE_TAKE_SHOP_CARD_5);
                }
                break;
            case 2:
                player.resetMoveCount();
                context.nextTurn();
                if (context.getLastRoundPlayer() != null && context.getLastRoundPlayer().getTurnOrder() == context.getTurnOrder())
                    context.setGameOver(true);
                break;
        }




        SessionM session = serverM.getUserSession(player.getUserM());
        if (session != null) {
            Thread th = new Thread() {
                @Override
                public void run() {
                    Command com = new Command();
                    com.setOperation("updateMoves");
                    String moveData = ser.serialize(availableMoves);
                    availableMoves.clear();
                    com.setArg("availableMoves", "ArrayList<MoveID>", moveData);
                    session.getProxy().updateMoves(com);
                }
            };
            th.start();
        }

    }
}
