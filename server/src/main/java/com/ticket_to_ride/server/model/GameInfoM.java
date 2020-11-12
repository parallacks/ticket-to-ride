//Created By Lance

package com.ticket_to_ride.server.model;

import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.ChatListM;
import com.ticket_to_ride.common.model.ChatM;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.GameHistoryM;
import com.ticket_to_ride.common.model.MapM;
import com.ticket_to_ride.common.model.MessageM;
import com.ticket_to_ride.common.model.RouteM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.common.model.TrayM;
import com.ticket_to_ride.server.model.util.IHistoryUpdates;
import com.ticket_to_ride.server.model.util.ModelFlag;
import com.ticket_to_ride.server.watcher.ChatWatcher;
import com.ticket_to_ride.server.watcher.DestinationDeckWatcher;
import com.ticket_to_ride.server.watcher.GameHistoryWatcher;
import com.ticket_to_ride.server.watcher.GameOverWatcher;
import com.ticket_to_ride.server.watcher.MovesWatcher;
import com.ticket_to_ride.server.watcher.PlayerStatsWatcher;
import com.ticket_to_ride.server.watcher.PlayerTurnsWatcher;
import com.ticket_to_ride.server.watcher.RoutesWatcher;
import com.ticket_to_ride.server.watcher.TrainDeckWatcher;
import com.ticket_to_ride.server.watcher.TrayWatcher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class GameInfoM implements IHistoryUpdates {

    private String gameName;
    private String status;
    private ArrayList<PlayerInfoM> players;
    private transient ChatListM chatList;
    private transient ChatListM tempChatList;
    //Model Flags
    private transient ModelFlag chatFlag;
    private transient ModelFlag gameHistoryFlag;
    private transient ModelFlag destinationDeckFlag;
    private transient ModelFlag trainCardFlag;
    private transient ModelFlag trayFlag;
    private transient ModelFlag movesFlag;
    private transient ModelFlag routesFlag;
    private transient RoutesWatcher routesWatcher;
    private transient ModelFlag playerStatsFlag;
    private transient ModelFlag playerTurnsFlag;
    private transient ModelFlag gameOverFlag;
    //End Model Flags
    private transient DeckM<TrainCardM> trainCards;
    private transient DeckM<DestinationCardM> destinationCards;
    private transient DeckM<TrainCardM> discardedTrainCards;
    private transient TrayM tray;
    //TODO: make sure this gets sent to the clients
    private transient MapM map;
    private transient PlayerInfoM lastRoundPlayer;
    private transient int turnOrder;
    private transient boolean gameOver;
    //this stores the claimed route until we send to the clients
    private transient RouteID claimedRoute;


    public GameInfoM()
    {
        players = new ArrayList<>();
        chatList = new ChatListM();
        tempChatList = new ChatListM();
        //Model Flag Initialization
        chatFlag = new ModelFlag();
        chatFlag.addObserver(new ChatWatcher(this));
        gameHistoryFlag = new ModelFlag();
        gameHistoryFlag.addObserver(new GameHistoryWatcher(this));
        destinationDeckFlag = new ModelFlag();
        destinationDeckFlag.addObserver(new DestinationDeckWatcher(this));
        trainCardFlag = new ModelFlag();
        trainCardFlag.addObserver(new TrainDeckWatcher(this));
        trayFlag = new ModelFlag();
        trayFlag.addObserver(new TrayWatcher(this));
        movesFlag = new ModelFlag();
        movesFlag.addObserver(new MovesWatcher(this));
        routesFlag = new ModelFlag();
        routesWatcher = new RoutesWatcher(this);
        routesFlag.addObserver(routesWatcher);
        playerStatsFlag = new ModelFlag();
        playerStatsFlag.addObserver(new PlayerStatsWatcher(this));
        playerTurnsFlag = new ModelFlag();
        playerTurnsFlag.addObserver(new PlayerTurnsWatcher(this));
        gameOverFlag = new ModelFlag();
        gameOverFlag.addObserver(new GameOverWatcher(this));
        //End Model Flags
        destinationCards = new DeckM<>();
        trainCards = new DeckM<>();
        discardedTrainCards = new DeckM<>();
        map = new MapM();
        tray = new TrayM();
        ServerM.get().setGame(map, destinationCards, trainCards);
        lastRoundPlayer = null;
        turnOrder = 0;
        gameOver = false;
        claimedRoute = null;
    }

    public RouteID getClaimedRoute() {
        return claimedRoute;
    }

    public void setClaimedRoute(RouteID claimedRoute) {
        this.claimedRoute = claimedRoute;
    }

    public ChatListM getChatList()
    {
        return chatList;
    }

    public int getTurnOrder()
    {
        return turnOrder;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public PlayerInfoM getLastRoundPlayer()
    {
        return lastRoundPlayer;
    }

    public void setLastRoundPlayer(PlayerInfoM lastRoundPlayer)
    {
        this.lastRoundPlayer = lastRoundPlayer;
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
        gameOverFlag.set();
    }

    public ArrayList<PlayerInfoM> getPlayers () {
        return players;
    }

    public PlayerInfoM getPlayerByUsername(String username)
    {
        for (PlayerInfoM p : players)
        {
            if (p.getUserM().getUsername().equals(username))
                return p;
        }
        return null;
    }

    public boolean addPlayer(PlayerInfoM p){
        for (PlayerInfoM currentPlayer : players) {
            if (currentPlayer.equals(p)) {
                return false;
            }
        }
        if(players.size() < 5) {
            players.add(p);
            return true;
        }
        else
            return false;
    }
    public boolean removePlayer(PlayerInfoM p){
        if (players.contains(p)) {
            players.remove(p);
            return false;
        } else {
            return false;
        }
    }

    public void addPlayerTrainCard(PlayerInfoM player, TrainCardM card)
    {
        player.addTrainCard(card);
//        movesFlag.set(player);
//        playerStatsFlag.set(player);
    }

    public void addPlayerDestinationCard(PlayerInfoM player, ArrayList<DestinationCardM> cards)
    {
        for (DestinationCardM card : cards)
            player.addDestinationCard(card);
//        movesFlag.set(player);
//        playerStatsFlag.set(player);
    }

    public void setMovesFlag(PlayerInfoM player)
    {
        movesFlag.set(player);
    }

    public void addClaimedRoute(PlayerInfoM player, RouteID routeID)
    {
        player.addClaimedRoute(routeID);
//        movesFlag.set(player);
//        routesWatcher.setPlayer(player);
//        routesFlag.set(routeID);
//        playerStatsFlag.set(player);
    }

    public void addToChatList(MessageM chat)
    {
        this.chatList.addChat(chat);
        //this.chatFlag.set(chat);
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public DeckM getTrainCards() {
        return trainCards;
    }

    public DeckM getDestinationCards() {
        return destinationCards;
    }

    public ArrayList<DestinationCardM> drawFromDestinationDeck()
    {
        ArrayList<DestinationCardM> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cards.add(destinationCards.drawFromDeck());
        }
//        destinationDeckFlag.set(cards);
        return cards;
    }

    public TrainCardM drawFromTrainDeck()
    {
        if (trainCards.getDeckSize() < 1)
        {
            discardedTrainCards.shuffleDeck();
            for (TrainCardM t : discardedTrainCards.getDeck())
            {
                trainCards.addToDeck(t);
                discardedTrainCards.getDeck().remove(t);
            }
        }
        TrainCardM card = trainCards.drawFromDeck();
//        trainCardFlag.set(card);
        return card;
    }

    public void checkTrayWilds(boolean init)
    {
        int wildCount = 0;
        for (TrainCardM c : tray.getTray())
        {
            if (c.getColor().equals(TrainCardColor.WILD))
                wildCount++;
        }
        if (wildCount >= 3)
        {
            TrayM temp = new TrayM();
            for (int i = 0; i < tray.getTray().size(); i++)
                temp.getTray().add(tray.getTray().get(i));

            Iterator<TrainCardM> it = temp.getTray().iterator();
            while (it.hasNext())
            {
                TrainCardM card = it.next();
                tray.getTray().remove(card);
                discardedTrainCards.addToDeck(card);
            }
            for (int i = 0; i < 5; i++)
            {
                tray.addToTray(drawFromTrainDeck());
            }
            checkTrayWilds(false);
        }
//        if (!init)
//            trayFlag.set(tray);
    }

    public TrainCardM drawFromTray(int position)
    {
        TrainCardM card = tray.removeFromTray(position);
        tray.addToTray(drawFromTrainDeck());
        checkTrayWilds(false);
        return card;
    }

    public ArrayList<DestinationCardM> drawStartingDestinationCards()
    {
        ArrayList<DestinationCardM> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            cards.add(destinationCards.drawFromDeck());
        }
        return cards;
    }

    public ArrayList<TrainCardM> drawStartingTrainCards()
    {
        ArrayList<TrainCardM> cards = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            cards.add(trainCards.drawFromDeck());
        }
        return cards;
    }

    public void drawStartingTrayCards()
    {
        for (int i = 0; i < 5; i++)
        {
            tray.addToTray(trainCards.drawFromDeck());
        }
        checkTrayWilds(true);
    }

    public void addToDiscard(TrainCardM card)
    {
        discardedTrainCards.addToDeck(card);
    }

    public void addToDestinationDeck(DestinationCardM card)
    {
        destinationCards.addToDeck(card);
//        destinationDeckFlag.set(card);
    }

    public void addToTrainDeck(TrainCardM card)
    {
        trainCards.addToDeck(card);
//        trainCardFlag.set(card);
    }

    //Modified by Lance
    public ArrayList<RouteID> getNonClaimedRoutes()
    {
        ArrayList<RouteID> nonClaimedRoutes = new ArrayList<>();
        for (RouteM route : map.getRoutes())
        {
            nonClaimedRoutes.add(route.getId());
        }
        for (PlayerInfoM player : players)
        {
            for (RouteID id : player.getClaimedRoutes())
            {
                if (map.getRouteById(id).isDouble()){
                    if(players.size()<4){
                        //Find the match and remove
                        nonClaimedRoutes.remove(map.findDoubleMatch(id));
                    }
                }
                nonClaimedRoutes.remove(id);
            }
        }
        return nonClaimedRoutes;
    }

    public void sendStartPlayerMoves(PlayerInfoM player)
    {
//        movesFlag.set(player);
    }

    public void nextTurn()
    {
        PlayerInfoM player = players.get(turnOrder);
        if (turnOrder == players.size() - 1)
            turnOrder = 0;
        else
            turnOrder++;
        for (int i = 0; i < players.size(); i++)
        {
            if (i == turnOrder) {
                players.get(i).setTurn(true);
//                movesFlag.set(players.get(i));
            }
            else
                players.get(i).setTurn(false);
        }
//        playerTurnsFlag.set(players);
        playerStatsFlag.set(player);

    }

    public MapM getMap() {
        return map;
    }

    public TrayM getTray()
    {
        return this.tray;
    }

    @Override
    public void historyDrawTrayCard(String username, int position) {
        String message = username + " drew the tray card in position " + position;
        GameHistoryM history = new GameHistoryM(username, LocalDateTime.now().toString(), message);
        addToChatList(history);
        addToTempChatList(history);
        //gameHistoryFlag.set(history);
    }

    @Override
    public void historyDrawTrainCard(String username) {
        String message = username + " drew a card from the train card deck";
        GameHistoryM history = new GameHistoryM(username, LocalDateTime.now().toString(), message);
        addToChatList(history);
        addToTempChatList(history);
        //gameHistoryFlag.set(history);
    }

    @Override
    public void historyDrawDestinationCards(String username) {
        String message = username + " drew destination cards from the destination card deck";
        GameHistoryM history = new GameHistoryM(username, LocalDateTime.now().toString(), message);
        addToChatList(history);
        addToTempChatList(history);
        //gameHistoryFlag.set(history);
    }

    @Override
    public void historyClaimRoute(String username, RouteM route) {
        String message = username + " claimed the route from " + route.getCityM1().getName() + " to " + route.getCityM2().getName();
        GameHistoryM history = new GameHistoryM(username, LocalDateTime.now().toString(), message);
        addToChatList(history);
        addToTempChatList(history);
        //gameHistoryFlag.set(history);
    }
    public void addToTempChatList(MessageM messageM) {
        tempChatList.addChat(messageM);
    }
    public ChatListM getTempChatList() {
        return tempChatList;
    }
    public void clearTempChatList() {
        tempChatList.getChatList().clear();
    }
}
