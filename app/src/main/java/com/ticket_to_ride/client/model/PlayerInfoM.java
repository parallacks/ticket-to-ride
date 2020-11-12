//Created By Lance

package com.ticket_to_ride.client.model;

import com.ticket_to_ride.client.model.UserM;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.HandM;
import com.ticket_to_ride.common.model.PlayerColor;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;

import java.util.ArrayList;

public class PlayerInfoM implements Comparable<PlayerInfoM>{

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
    private int numTrainCards;
    private int numDestinationCards;
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
        this.numTrainCards = 4;
        this.numDestinationCards = 0;
    }

    public int getPointsGained() {
        return pointsGained;
    }

    public int getPointsLost() {
        return pointsLost;
    }

    public int getNumTrainCards() {
        return numTrainCards;
    }

    public void setNumTrainCards(int numTrainCards) {
        this.numTrainCards = numTrainCards;
    }

    public int getNumDestinationCards() {
        return numDestinationCards;
    }

    public void setNumDestinationCards(int numDestinationCards) {
        this.numDestinationCards = numDestinationCards;
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

    public void setColor(PlayerColor color)
    {
        this.color = color;
    }

    public PlayerColor getColor()
    {
        return color;
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

    public int getNumCardsByColor(TrainCardColor color, HandM<TrainCardM> trainCards)
    {
        int count = 0;
        for (TrainCardM card : trainCards.getHand())
        {
            if (card.getColor().equals(color))
                count++;
        }
        return count;
    }

    public int getMaxNumCardsOfColor(HandM<TrainCardM> trainCards)
    {
        int max = 0;
        if (getNumCardsByColor(TrainCardColor.RED, trainCards) > max)
            max = getNumCardsByColor(TrainCardColor.RED, trainCards);
        if (getNumCardsByColor(TrainCardColor.YELLOW, trainCards) > max)
            max = getNumCardsByColor(TrainCardColor.YELLOW, trainCards);
        if (getNumCardsByColor(TrainCardColor.ORANGE, trainCards) > max)
            max = getNumCardsByColor(TrainCardColor.ORANGE, trainCards);
        if (getNumCardsByColor(TrainCardColor.GREEN, trainCards) > max)
            max = getNumCardsByColor(TrainCardColor.GREEN, trainCards);
        if (getNumCardsByColor(TrainCardColor.BLUE, trainCards) > max)
            max = getNumCardsByColor(TrainCardColor.BLUE, trainCards);
        if (getNumCardsByColor(TrainCardColor.PINK, trainCards) > max)
            max = getNumCardsByColor(TrainCardColor.PINK, trainCards);
        if (getNumCardsByColor(TrainCardColor.WHITE, trainCards) > max)
            max = getNumCardsByColor(TrainCardColor.WHITE, trainCards);
        if (getNumCardsByColor(TrainCardColor.BLACK, trainCards) > max)
            max = getNumCardsByColor(TrainCardColor.BLACK, trainCards);
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;

        PlayerInfoM object = (PlayerInfoM)o;

        return object.userM.equals(userM);
    }

    @Override
    public int compareTo(PlayerInfoM playerInfoM) {
        return playerInfoM.points - this.points;
        //return this.points  playerInfoM.points;
    }
}
