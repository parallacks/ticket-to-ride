package com.ticket_to_ride.client.model;

import com.ticket_to_ride.client.states.GameStatusState;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.ChatListM;
import com.ticket_to_ride.common.model.ChatM;
import com.ticket_to_ride.common.model.CityM;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.HandM;
import com.ticket_to_ride.common.model.MapM;
import com.ticket_to_ride.common.model.MessageM;
import com.ticket_to_ride.common.model.RouteM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.common.model.TrayM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

public class ClientM extends Observable {

    private static ClientM instance;

    public static ClientM get() {
        if (instance == null)
            instance = new ClientM();
        return instance;
    }

    private ClientM()
    {
        this.chatList = new ChatListM();
        this.destinationCards = new HandM<>();
        this.trainCards = new HandM<>();
        this.myClaimedRoutes = new ArrayList<>();
        this.claimedRoutes = new TreeMap<>();
        this.numEachTrainCard = new HashMap<>();
        this.destCardOptions = new DestCardOptions();
        this.faceUpTCards = new TrayM();
        this.trainCars = 45;
        this.validMoves = new ArrayList<>();
        this.lastRound = false;
    }

    private UserM user;
    private GameInfoM activeGame;
    private GameInfoM selectedGame;
    private ArrayList<GameInfoM> lobbyGames;
    private ArrayList<String> gameNames;
    private ChatListM chatList;
    private String errorMessage;
    private boolean isReady;
    private DestCardOptions destCardOptions;
    private TrayM faceUpTCards;
    private HandM<DestinationCardM> destinationCards;
    private HandM<TrainCardM> trainCards;
    private ArrayList<RouteID> myClaimedRoutes;
    private Map<RouteID, PlayerInfoM> claimedRoutes;
    private int trainCars;
    private HashMap<TrainCardColor, Integer> numEachTrainCard;
    private int numDestCards;
    private int numTrainCards;
    private ArrayList<MoveID> validMoves;
    private GameStatusState currentGameStatusState;
    private boolean lastRound;
    private boolean gameOver;
    private String mostRoutesPlayer;

    public String getMostRoutesPlayer() {
        return mostRoutesPlayer;
    }

    public void setMostRoutesPlayer(String mostRoutesPlayer) {
        this.mostRoutesPlayer = mostRoutesPlayer;
    }

    public void setChatList(ChatListM chatList) {
        for (MessageM m : chatList.getChatList())
        {
            addChat(m);
        }
    }

    public HandM<TrainCardM> getTrainCards()
    {
        return trainCards;
    }

    public void setTrainCards(HandM<TrainCardM> trainCards) {
        this.trainCards = trainCards;
    }

    public void addTrainCard(TrainCardM card)
    {
        this.trainCards.addToHand(card);
        addNewTrainCard(card);
        setChanged();
        notifyObservers(this.trainCards);
    }

    public void setTrainCards(ArrayList<TrainCardM> trainCards)
    {
        this.trainCards.setHand(trainCards);
        calculateNumEachTrainCard();
        setChanged();
        notifyObservers(this.trainCards);
    }

    public void addDestinationCard(DestinationCardM card)
    {
        this.destinationCards.addToHand(card);
        setChanged();
        notifyObservers(destinationCards);
    }

    public UserM getUser() {
        return user;
    }

    public GameInfoM getActiveGame() {
        return activeGame;
    }

    public GameInfoM getGameByName(String gameName)
    {
        GameInfoM game = null;

        for (int i = 0; i < lobbyGames.size(); i++)
        {
            if (lobbyGames.get(i).getGameName().equals(gameName))
            {
                game = lobbyGames.get(i);
                break;
            }
        }

        return game;
    }

    public void addGameToLobby(GameInfoM game) {
        lobbyGames.add(game);
        setChanged();
        notifyObservers(lobbyGames);
    }
    public void removeLobbyGame(String gamename){
        getLobbyGames().remove(getGameByName(gamename));
        setChanged();
        notifyObservers(lobbyGames);
    }

    public int getNumTCard(TrainCardColor tCardColor) {
        return numEachTrainCard.get(tCardColor);
    }


    public void addFaceUpTCard(TrainCardM tCard) {
        faceUpTCards.addToTray(tCard);
        setChanged();
        notifyObservers(this.faceUpTCards);
    }

    private void addNewTrainCard(TrainCardM tCard) {
        if (numEachTrainCard.containsKey(tCard.getColor())) {
            int num = numEachTrainCard.get(tCard.getColor());
            numEachTrainCard.put(tCard.getColor(), ++num);
        }
        else {
            numEachTrainCard.put(tCard.getColor(), 1);
        }
    }

    private void calculateNumEachTrainCard()
    {
        numEachTrainCard.clear();
        for (TrainCardM tCard : trainCards.getHand())
        {
            if (numEachTrainCard.containsKey(tCard.getColor())) {
                int num = numEachTrainCard.get(tCard.getColor());
                numEachTrainCard.put(tCard.getColor(), ++num);
            }
            else {
                numEachTrainCard.put(tCard.getColor(), 1);
            }
        }
    }

    private void updateEachTrainCard(ArrayList<TrainCardM> trainCards) {
        for (TrainCardM tCard : trainCards) {
            if (numEachTrainCard.containsKey(tCard.getColor())) {
                int num = numEachTrainCard.get(tCard.getColor());
                numEachTrainCard.put(tCard.getColor(), ++num);
            }
            else {
                numEachTrainCard.put(tCard.getColor(), 1);
            }
        }
    }

    public void setStartingCards(ArrayList<DestinationCardM> destinationCards, ArrayList<TrainCardM> trainCards)
    {
        for (TrainCardM t : trainCards)
        {
            this.trainCards.addToHand(t);
        }
        updateEachTrainCard(this.trainCards.getHand());

        this.destCardOptions.setOptions(destinationCards);
        setChanged();
        notifyObservers(this.destCardOptions);
    }


    //TODO: might not need these functions once the server takes care of updating model
    public void notifyPlayerStatsChange() {
        setChanged();
        notifyObservers(getOpponentStats());
        setChanged();
        notifyObservers(getMyStats());
    }
    public void notifyMyStatsChange() {
        setChanged();
        notifyObservers(getMyStats());
    }
    //TODO:************************************************************************

    public void addDestinationCards(ArrayList<DestinationCardM> newDestCards) {
        for (DestinationCardM card : newDestCards)
            this.destinationCards.addToHand(card);
        getMyStats().setNumDestinationCards(this.destinationCards.getHandSize());
        //TODO: Don't put this here
        notifyMyStatsChange();
        setChanged();
        notifyObservers(this.destinationCards);
    }


    public boolean isValidMove(MoveID proposedMove) {
        for (MoveID move : validMoves) {
            if (move == proposedMove) {
                return true;
            }
        }
        return false;
    }

    /*
                          GETTERS
     */

    public GameStatusState getCurrentGameStatusState() {
        return currentGameStatusState;
    }

    public boolean isReady() {
        return isReady;
    }

    public HandM<DestinationCardM> getDestinationCards() {
        return destinationCards;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public GameInfoM getSelectedGame() {
        return selectedGame;
    }

    public ChatListM getChatList()
    {
        return chatList;
    }

    public ArrayList<GameInfoM> getLobbyGames() {
        return lobbyGames;
    }

    public Map<TrainCardColor, Integer> getNumEachTrainCard() {
        return numEachTrainCard;
    }

    public TrayM getFaceUpTCards() {
        return faceUpTCards;
    }

    public int getNumDestCards() {
        return numDestCards;
    }

    public DestCardOptions getDestCardOptions() {
        return destCardOptions;
    }

    public ArrayList<PlayerInfoM> getOpponentStats() {
        ArrayList<PlayerInfoM> opponentStats = new ArrayList<>();
        for(PlayerInfoM player : activeGame.getPlayers()) {
            if (!player.getUserM().getUsername().equals(user.getUsername())) {
                opponentStats.add(player);
                System.out.println(player);
            }
        }
        return opponentStats;
    }

    public PlayerInfoM getMyStats() {
        return activeGame.getPlayerByUsername(user.getUsername());
    }

    public ArrayList<MoveID> getValidMoves()
    {
        return validMoves;
    }

    public boolean getLastRound()
    {
        return lastRound;
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public ArrayList<RouteID> getMyClaimedRoutes() {
        return myClaimedRoutes;
    }

    public Map<RouteID, PlayerInfoM> getClaimedRoutes() {
        return claimedRoutes;
    }

    /*
                                   SETTERS
             */
    public void setGameMap(MapM map)
    {
        activeGame.setMap(map);
    }

    public void addToMyClaimedRoutes(RouteID routeID)
    {
        this.myClaimedRoutes.add(routeID);
        setChanged();
        notifyObservers(this.myClaimedRoutes);
    }

    public void addClaimedRoutes(RouteID routeID, PlayerInfoM player)
    {
        this.claimedRoutes.put(routeID, player);
        setChanged();
        notifyObservers(this.claimedRoutes);
    }

    public void setCurrentGameStatusState(GameStatusState currentGameStatusState) {
        this.currentGameStatusState = currentGameStatusState;
    }

    public void setDestinationCards(HandM<DestinationCardM> destinationCards) {
        this.destinationCards = destinationCards;
        setChanged();
        notifyObservers(this.destinationCards);
    }

    public void setFaceUpTCards(TrayM faceUpTCards) {
        this.faceUpTCards = faceUpTCards;
        setChanged();
        notifyObservers(this.faceUpTCards);
    }

    public void clearOptions() {
        this.destCardOptions.getOptions().clear();
        setChanged();
        notifyObservers(this.destCardOptions);
    }

    public void setReady(boolean ready) {
        isReady = ready;
        setChanged();
        notifyObservers(isReady);
    }

    public void setActiveGameStatus(String status) {
        this.activeGame.setStatus(status);
        setChanged();
        notifyObservers(this.activeGame);
    }

    public void addChat(MessageM chatMessage)
    {
        this.chatList.addChat(chatMessage);
        setChanged();
        notifyObservers(chatMessage);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        setChanged();
        notifyObservers(this.errorMessage);
    }

    public void setSelectedGame(GameInfoM selectedGame) {
        this.selectedGame = selectedGame;
        setChanged();
        notifyObservers(this.selectedGame);
    }

    public void setLobbyGames(ArrayList<GameInfoM> lobbyGames) {
        this.lobbyGames = lobbyGames;
        setChanged();
        notifyObservers(this.lobbyGames);
    }

    public void setActiveGame(GameInfoM activeGame) {
        this.activeGame = activeGame;
        setChanged();
        notifyObservers(this.activeGame);
    }

    public void setUser(UserM user) {
        this.user = user;
        setChanged();
        notifyObservers(this.user);
    }

    public void setNumDestCards(int numDestCards) {
        this.numDestCards = numDestCards;
        DeckSizeM destinationCards = new DeckSizeM(numDestCards, "destinationCards");
        setChanged();
        notifyObservers(destinationCards);
    }

    public int getNumTrainCards() {
        return numTrainCards;
    }

    public void setNumTrainCards(int numTrainCards) {
        this.numTrainCards = numTrainCards;
        DeckSizeM trainCards = new DeckSizeM(numTrainCards, "trainCards");
        setChanged();
        notifyObservers(trainCards);
    }

    public void setDestCardOptions(DestCardOptions destCardOptions) {
        this.destCardOptions = destCardOptions;
        setChanged();
        notifyObservers(this.destCardOptions);
    }

    public void setValidMoves(ArrayList<MoveID> validMoves)
    {
        this.validMoves = validMoves;
        setChanged();
        notifyObservers(this.validMoves);
    }

    public void setPlayerNumTrainCards(String player, int numTrainCards)
    {
        activeGame.getPlayerByUsername(player).setNumTrainCards(numTrainCards);
        //player.setNumTrainCards(numTrainCards);
        notifyPlayerStatsChange();
    }

    public void setPlayerNumDestinationCards(String player, int numDestCards)
    {
        activeGame.getPlayerByUsername(player).setNumDestinationCards(numDestCards);
        //player.setNumDestinationCards(numDestCards);
        notifyPlayerStatsChange();
    }

    public void setPlayerNumTrains(String player, int numTrains)
    {
        activeGame.getPlayerByUsername(player).setNumTrains(numTrains);
        //player.setNumTrains(numTrains);
        notifyPlayerStatsChange();
    }

    public void setPlayerTurns(ArrayList<Boolean> turns)
    {
        for (int i = 0; i < turns.size(); i++)
        {
            activeGame.getPlayers().get(i).setTurn(turns.get(i));
        }
        //TODO: Observe and update turns
        notifyPlayerStatsChange();
    }

    public void setPlayerPoints(String player, int points)
    {
        activeGame.getPlayerByUsername(player).setPoints(points);
        //player.setPoints(points);
        notifyPlayerStatsChange();
    }

    public void setLastRound(boolean lastRound)
    {
        this.lastRound = lastRound;
        if(lastRound){
            setChanged();
            notifyObservers(lastRound);
        }
        //TODO: Observe give a warning that it's the last turn
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
        setChanged();
        notifyObservers(0.0);
    }

    public void addPlayerToActiveGame(PlayerInfoM player) {
        this.getActiveGame().addPlayer(player);
        setChanged();
        notifyObservers(0.0);
    }


    /*
                            TEST FUNCTIONS
     */

    //For testing purposes
    private void printDestinationCards() {
        System.out.println(destinationCards.toString());
    }

    private void testDestinationCardUpdate() {
        //TODO: CREATE destination cards and display them
        String[] cityStrings = {"Spanish Fork", "Provo", "Las Vegas", "LA",
                "New York", "Denver"};
        ArrayList<CityM> cities = new ArrayList<>();
        for (String cityString : cityStrings) {
            CityM city = new CityM(cityString);
            cities.add(city);
        }

        DestCardOptions options = new DestCardOptions();
        ArrayList<DestinationCardM> cards = new ArrayList<>();
        for (int i = 0; i < cities.size(); i += 2) {
            DestinationCardM card = new DestinationCardM(cities.get(i), cities.get(i + 1),
                    (i + 1) * 4);
            cards.add(card);
        }
        options.setOptions(cards);
        setDestCardOptions(options);
    }

    public void setCities(MapM map)
    {
        //Hard code all cities
        map.addCity(new CityM("Orgahill"));
        map.addCity(new CityM("Madino"));
        map.addCity(new CityM("Anphony"));
        map.addCity(new CityM("Heirhein"));
        map.addCity(new CityM("Verdane"));
        map.addCity(new CityM("Marpha"));
        map.addCity(new CityM("Genoa"));
        map.addCity(new CityM("Evans"));
        map.addCity(new CityM("Mackily"));
        map.addCity(new CityM("Augusty"));
        map.addCity(new CityM("Sailane"));
        map.addCity(new CityM("Silesia"));
        map.addCity(new CityM("Freege"));
        map.addCity(new CityM("Dozel"));
        map.addCity(new CityM("Jungby"));
        map.addCity(new CityM("Miletos"));
        map.addCity(new CityM("Rados"));
        map.addCity(new CityM("Chronos"));
        map.addCity(new CityM("Edda"));
        map.addCity(new CityM("Velthome"));
        map.addCity(new CityM("Zaxon"));
        map.addCity(new CityM("Throve"));
        map.addCity(new CityM("Phinora"));
        map.addCity(new CityM("Yied"));
        map.addCity(new CityM("Melgen"));
        map.addCity(new CityM("Luthecia"));
        map.addCity(new CityM("Grutia"));
        map.addCity(new CityM("Kapathogia"));
        map.addCity(new CityM("Alster"));
        map.addCity(new CityM("Rivough"));
        map.addCity(new CityM("Sophara"));
        map.addCity(new CityM("Tirnanog"));
        map.addCity(new CityM("Isaac"));
        map.addCity(new CityM("Conote"));
        map.addCity(new CityM("Manster"));
        map.addCity(new CityM("Thracia"));
    }

    public void setRoutes(MapM map)
    {
        //Hard code all routeMS
        map.addRoutes(new RouteM(map.getCityByName("Orgahill"), map.getCityByName("Madino"),    TrainCardColor.WILD,    2, RouteID.ORGAHILL_TO_MADINO_1, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Orgahill"), map.getCityByName("Madino"),    TrainCardColor.WILD,    2, RouteID.ORGAHILL_TO_MADINO_2, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Anphony"),  map.getCityByName("Madino"),    TrainCardColor.RED,     3, RouteID.ANPHONY_TO_MADINO_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Anphony"),  map.getCityByName("Madino"),    TrainCardColor.YELLOW,  3, RouteID.ANPHONY_TO_MADINO_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Sailane"),  map.getCityByName("Madino"),    TrainCardColor.WILD,    2, RouteID.SAILANE_TO_MADINO_1, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Sailane"),  map.getCityByName("Madino"),    TrainCardColor.WILD,    2, RouteID.SAILANE_TO_MADINO_2, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Anphony"),  map.getCityByName("Augusty"),   TrainCardColor.BLUE,    3, RouteID.ANPHONY_TO_AUGUSTY, 4));
        map.addRoutes(new RouteM(map.getCityByName("Anphony"),  map.getCityByName("Heirhein"),  TrainCardColor.ORANGE,  3, RouteID.ANPHONY_TO_HEIRHEIN, 4));
        map.addRoutes(new RouteM(map.getCityByName("Heirhein"), map.getCityByName("Evans"),     TrainCardColor.YELLOW,  4, RouteID.HEIRHEIN_TO_EVANS, 7));
        map.addRoutes(new RouteM(map.getCityByName("Augusty"),  map.getCityByName("Mackily"),   TrainCardColor.WHITE,   1, RouteID.AUGUSTY_TO_MACKILY, 1));
        map.addRoutes(new RouteM(map.getCityByName("Augusty"),  map.getCityByName("Freege"),    TrainCardColor.GREEN,   2, RouteID.AUGUSTY_TO_FREEGE, 2));
        map.addRoutes(new RouteM(map.getCityByName("Mackily"),  map.getCityByName("Evans"),     TrainCardColor.PINK,    2, RouteID.MACKILY_TO_EVANS, 2));
        map.addRoutes(new RouteM(map.getCityByName("Mackily"),  map.getCityByName("Edda"),      TrainCardColor.BLACK,   6, RouteID.MACKILY_TO_EDDA, 15));
        map.addRoutes(new RouteM(map.getCityByName("Evans"),    map.getCityByName("Verdane"),   TrainCardColor.BLACK,   4, RouteID.EVANS_TO_VERDANE_1, 7, true));
        map.addRoutes(new RouteM(map.getCityByName("Evans"),    map.getCityByName("Verdane"),   TrainCardColor.GREEN,   4, RouteID.EVANS_TO_VERDANE_2, 7, true));
        map.addRoutes(new RouteM(map.getCityByName("Evans"),    map.getCityByName("Jungby"),    TrainCardColor.RED,     2, RouteID.EVANS_TO_JUNGBY, 2));
        map.addRoutes(new RouteM(map.getCityByName("Verdane"),  map.getCityByName("Marpha"),    TrainCardColor.WHITE,   3, RouteID.VERDANE_TO_MARPHA_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Verdane"),  map.getCityByName("Marpha"),    TrainCardColor.BLACK,   3, RouteID.VERDANE_TO_MARPHA_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Marpha"),   map.getCityByName("Genoa"),     TrainCardColor.WILD,    3, RouteID.MARPHA_TO_GENOA_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Marpha"),   map.getCityByName("Genoa"),     TrainCardColor.WILD,    3, RouteID.MARPHA_TO_GENOA_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Genoa"),    map.getCityByName("Miletos"),   TrainCardColor.GREEN,   2, RouteID.GENOA_TO_MILETOS, 2));
        map.addRoutes(new RouteM(map.getCityByName("Genoa"),    map.getCityByName("Rados"),     TrainCardColor.WHITE,   2, RouteID.GENOA_TO_RADOS, 2));
        map.addRoutes(new RouteM(map.getCityByName("Rados"),    map.getCityByName("Chronos"),   TrainCardColor.BLUE,    2, RouteID.RADOS_TO_CHRONOS, 2));
        map.addRoutes(new RouteM(map.getCityByName("Chronos"),  map.getCityByName("Miletos"),   TrainCardColor.PINK,    2, RouteID.CHRONOS_TO_MILETOS, 2));
        map.addRoutes(new RouteM(map.getCityByName("Chronos"),  map.getCityByName("Melgen"),    TrainCardColor.YELLOW,  4, RouteID.CHRONOS_TO_MELGEN_1, 7, true));
        map.addRoutes(new RouteM(map.getCityByName("Chronos"),  map.getCityByName("Melgen"),    TrainCardColor.PINK,    4, RouteID.CHRONOS_TO_MELGEN_2, 7, true));
        map.addRoutes(new RouteM(map.getCityByName("Jungby"),   map.getCityByName("Edda"),      TrainCardColor.ORANGE,  4, RouteID.JUNGBY_TO_EDDA, 7));
        map.addRoutes(new RouteM(map.getCityByName("Freege"),   map.getCityByName("Dozel"),     TrainCardColor.BLACK,   1, RouteID.FREEGE_TO_DOZEL, 1));
        map.addRoutes(new RouteM(map.getCityByName("Dozel"),    map.getCityByName("Velthome"),  TrainCardColor.YELLOW,  2, RouteID.DOZEL_TO_VELTHOME, 2));
        map.addRoutes(new RouteM(map.getCityByName("Velthome"), map.getCityByName("Edda"),      TrainCardColor.WILD,    3, RouteID.VELTHOME_TO_EDDA_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Velthome"), map.getCityByName("Edda"),      TrainCardColor.WILD,    3, RouteID.VELTHOME_TO_EDDA_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Velthome"), map.getCityByName("Yied"),      TrainCardColor.WILD,    3, RouteID.VELTHOME_TO_YIED_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Velthome"), map.getCityByName("Yied"),      TrainCardColor.WILD,    3, RouteID.VELTHOME_TO_YIED_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Velthome"), map.getCityByName("Phinora"),   TrainCardColor.RED,     2, RouteID.VELTHOME_TO_PHINORA, 2));
        map.addRoutes(new RouteM(map.getCityByName("Yied"),     map.getCityByName("Rivough"),   TrainCardColor.WILD,    3, RouteID.YIED_TO_RIVOUGH_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Yied"),     map.getCityByName("Rivough"),   TrainCardColor.WILD,    3, RouteID.YIED_TO_RIVOUGH_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Sailane"),  map.getCityByName("Silesia"),   TrainCardColor.GREEN,   2, RouteID.SAILANE_TO_SILESIA, 2));
        map.addRoutes(new RouteM(map.getCityByName("Silesia"),  map.getCityByName("Zaxon"),     TrainCardColor.WHITE,   2, RouteID.SILESIA_TO_ZAXON, 2));
        map.addRoutes(new RouteM(map.getCityByName("Sailane"),  map.getCityByName("Throve"),    TrainCardColor.BLUE,    5, RouteID.SAILANE_TO_THROVE, 10));
        map.addRoutes(new RouteM(map.getCityByName("Throve"),   map.getCityByName("Zaxon"),     TrainCardColor.PINK,    3, RouteID.THROVE_TO_ZAXON, 4));
        map.addRoutes(new RouteM(map.getCityByName("Zaxon"),    map.getCityByName("Phinora"),   TrainCardColor.ORANGE,  2, RouteID.ZAXON_TO_PHINORA_1, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Zaxon"),    map.getCityByName("Phinora"),   TrainCardColor.GREEN,   2, RouteID.ZAXON_TO_PHINORA_2, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Phinora"),  map.getCityByName("Tirnanog"),  TrainCardColor.ORANGE,  6, RouteID.PHINORA_TO_TIRNANOG, 15));
        map.addRoutes(new RouteM(map.getCityByName("Tirnanog"), map.getCityByName("Sophara"),   TrainCardColor.BLACK,   2, RouteID.TIRNANOG_TO_SOPHARA, 2));
        map.addRoutes(new RouteM(map.getCityByName("Sophara"),  map.getCityByName("Rivough"),   TrainCardColor.RED,     1, RouteID.SOPHARA_TO_RIVOUGH, 1));
        map.addRoutes(new RouteM(map.getCityByName("Tirnanog"), map.getCityByName("Isaac"),     TrainCardColor.YELLOW,  4, RouteID.TIRNANOG_TO_ISAAC, 7));
        map.addRoutes(new RouteM(map.getCityByName("Isaac"),    map.getCityByName("Rivough"),   TrainCardColor.GREEN,   2, RouteID.ISAAC_TO_RIVOUGH, 2));
        map.addRoutes(new RouteM(map.getCityByName("Edda"),     map.getCityByName("Melgen"),    TrainCardColor.BLUE,    2, RouteID.EDDA_TO_MELGEN, 2));
        map.addRoutes(new RouteM(map.getCityByName("Luthecia"), map.getCityByName("Melgen"),    TrainCardColor.ORANGE,  3, RouteID.LUTHECIA_TO_MELGEN, 4));
        map.addRoutes(new RouteM(map.getCityByName("Luthecia"), map.getCityByName("Grutia"),    TrainCardColor.WHITE,   2, RouteID.LUTHECIA_TO_GRUTIA, 2));
        map.addRoutes(new RouteM(map.getCityByName("Grutia"),   map.getCityByName("Thracia"),   TrainCardColor.PINK,    3, RouteID.GRUTIA_TO_THRACIA, 4));
        map.addRoutes(new RouteM(map.getCityByName("Thracia"),  map.getCityByName("Kapathogia"),TrainCardColor.RED,     1, RouteID.THRACIA_TO_KAPATHOGIA, 1));
        map.addRoutes(new RouteM(map.getCityByName("Thracia"),  map.getCityByName("Manster"),   TrainCardColor.BLACK,   4, RouteID.THRACIA_TO_MANSTER, 7));
        map.addRoutes(new RouteM(map.getCityByName("Alster"),   map.getCityByName("Kapathogia"),TrainCardColor.GREEN,   3, RouteID.ALSTER_TO_KAPATHOGIA, 4));
        map.addRoutes(new RouteM(map.getCityByName("Alster"),   map.getCityByName("Melgen"),    TrainCardColor.ORANGE,  2, RouteID.ALSTER_TO_MELGEN, 2));
        map.addRoutes(new RouteM(map.getCityByName("Alster"),   map.getCityByName("Manster"),   TrainCardColor.RED,     2, RouteID.ALSTER_TO_MANSTER, 2));
        map.addRoutes(new RouteM(map.getCityByName("Manster"),  map.getCityByName("Conote"),    TrainCardColor.PINK,    1, RouteID.MANSTER_TO_CONOTE_1, 1, true));
        map.addRoutes(new RouteM(map.getCityByName("Manster"),  map.getCityByName("Conote"),    TrainCardColor.WHITE,   1, RouteID.MANSTER_TO_CONOTE_2, 1, true));
    }

}
