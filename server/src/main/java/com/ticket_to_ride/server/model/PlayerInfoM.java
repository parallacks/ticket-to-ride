//Created By Lance

package com.ticket_to_ride.server.model;

import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.HandM;
import com.ticket_to_ride.common.model.PlayerColor;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.server.model.util.ModelFlag;
import com.ticket_to_ride.server.watcher.MovesWatcher;

import java.util.ArrayList;

public class PlayerInfoM {

    private UserM userM;
    private PlayerColor color;
    private HandM<DestinationCardM> destinationCards;
    private HandM<TrainCardM> trainCards;
    private ArrayList<RouteID> claimedRoutes;
    private int points;
    private int turnOrder;
    private boolean isTurn;
    private int numTrains;
    private int moveCount;
    private boolean chooseCards;
    private int pointsGained;
    private int pointsLost;


    public PlayerInfoM(UserM user){
        this.userM = user;
        this.destinationCards = new HandM<>();
        this.trainCards = new HandM<>();
        this.claimedRoutes = new ArrayList<>();
        this.numTrains = 20;
        this.points = 0;
        this.moveCount = 0;
        this.chooseCards = false;
    }

    public UserM getUserM() {
        return userM;
    }

    public void setUserM(UserM userM) {
        this.userM = userM;
    }

    public PlayerColor getPlayerColor() {
        return color;
    }

    public void setPlayerColor(PlayerColor color) {
        this.color = color;
    }

    public HandM<DestinationCardM> getDestinationCards() {
        return destinationCards;
    }

    public HandM<TrainCardM> getTrainCards() {
        return trainCards;
    }

    public void addTrainCard(TrainCardM trainCard)
    {
        trainCards.addToHand(trainCard);
    }

    public void addDestinationCard(DestinationCardM destinationCard)
    {
        destinationCards.addToHand(destinationCard);
    }

    public void addClaimedRoute(RouteID routeID)
    {
        claimedRoutes.add(routeID);
    }

    public ArrayList<RouteID> getClaimedRoutes()
    {
        return claimedRoutes;
    }

    public int getPointsGained() {
        return pointsGained;
    }

    public int getPointsLost() {
        return pointsLost;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public int getNumTrains() {
        return numTrains;
    }

    public void setNumTrains(int numTrains) {
        this.numTrains = numTrains;
    }

    public void incrementMoveCount()
    {
        moveCount++;
    }

    public int getMoveCount()
    {
        return moveCount;
    }

    public void resetMoveCount()
    {
        moveCount = 0;
    }

    public void setEndTurn()
    {
        moveCount = 2;
    }

    public boolean getChooseCards()
    {
        return chooseCards;
    }

    public void setChooseCards(boolean chooseCards)
    {
        this.chooseCards = chooseCards;
    }

    public void setPointsGained(int pointsGained) {
        this.pointsGained = pointsGained;
    }

    public void setPointsLost(int pointsLost) {
        this.pointsLost = pointsLost;
    }

    public int getNumCardsByColor(TrainCardColor color)
    {
        int count = 0;
        for (TrainCardM card : trainCards.getHand())
        {
            if (card.getColor().equals(color))
                count++;
        }
        return count;
    }

    public int getMaxNumCardsOfColor()
    {
        int max = 0;
        if (getNumCardsByColor(TrainCardColor.RED) > max)
            max = getNumCardsByColor(TrainCardColor.RED);
        if (getNumCardsByColor(TrainCardColor.YELLOW) > max)
            max = getNumCardsByColor(TrainCardColor.YELLOW);
        if (getNumCardsByColor(TrainCardColor.ORANGE) > max)
            max = getNumCardsByColor(TrainCardColor.ORANGE);
        if (getNumCardsByColor(TrainCardColor.GREEN) > max)
            max = getNumCardsByColor(TrainCardColor.GREEN);
        if (getNumCardsByColor(TrainCardColor.BLUE) > max)
            max = getNumCardsByColor(TrainCardColor.BLUE);
        if (getNumCardsByColor(TrainCardColor.PINK) > max)
            max = getNumCardsByColor(TrainCardColor.PINK);
        if (getNumCardsByColor(TrainCardColor.WHITE) > max)
            max = getNumCardsByColor(TrainCardColor.WHITE);
        if (getNumCardsByColor(TrainCardColor.BLACK) > max)
            max = getNumCardsByColor(TrainCardColor.BLACK);
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;

        PlayerInfoM object = (PlayerInfoM)o;

        return object.userM.equals(userM);
    }

}
