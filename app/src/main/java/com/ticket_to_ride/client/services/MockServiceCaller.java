//package com.ticket_to_ride.client.services;
//
//import com.ticket_to_ride.client.model.ClientM;
//import com.ticket_to_ride.client.model.DeckM;
//import com.ticket_to_ride.client.model.DestCardOptions;
//import com.ticket_to_ride.client.model.PlayerInfoM;
//import com.ticket_to_ride.client.model.UserM;
//import com.ticket_to_ride.common.model.CardM;
//import com.ticket_to_ride.common.model.CityM;
//import com.ticket_to_ride.common.model.DestinationCardM;
//import com.ticket_to_ride.common.model.HandM;
//import com.ticket_to_ride.common.model.TrainCardColor;
//import com.ticket_to_ride.common.model.TrainCardM;
//import com.ticket_to_ride.common.model.TrayM;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//
//import static com.ticket_to_ride.common.model.TrainCardColor.BLACK;
//import static com.ticket_to_ride.common.model.TrainCardColor.WHITE;
//import static com.ticket_to_ride.common.model.TrainCardColor.WILD;
//
///**
// * Created by jared on 3/6/19.
// */
//
////TODO: add this functionality to the responses from the service requests in phase 3
//public class MockServiceCaller {
//
//    ClientM clientM;
//
//    public MockServiceCaller() {
//        clientM = ClientM.get();
//    }
//
//    public void initStatusDisplayForPhase2() {
//        initMyStats();
//        //setNumInDecks(99);
//        //addOneOfEachTrainCard();
//        //addFaceUpTCards();
////        if (clientM.getDestinationCards() == null || clientM.getDestinationCards().isEmpty()) {
////            getNewDestinationCards();
////        }
//        initOpponentStats();
//        clientM.notifyMyStatsChange();
//    }
//
//    public void runStatusDisplay() throws InterruptedException {
//        Random random = new Random();
//        // Random turn changer
//        int rando = random.nextInt(clientM.getOpponentStats().size());
//        clientM.getOpponentStats().get(rando).setTurn(true);
//        clientM.getMyStats().setTurn(false);
//        clientM.notifyPlayerStatsChange();
//        clientM.notifyMyStatsChange();
//        for (int i = 0; i < clientM.getOpponentStats().size(); i++) {
//            if (i != rando) {
//                clientM.getOpponentStats().get(i).setTurn(false);
//            }
//        }
//        clientM.notifyPlayerStatsChange();
//        // Change destination card hand
//        //getNewDestinationCards();
//
//        // Change opponent status
//        int rando1 = random.nextInt(clientM.getOpponentStats().size());
//        clientM.getOpponentStats().get(rando1).setNumDestinationCards(random.nextInt(10));
//        clientM.getOpponentStats().get(rando1).setNumTrainCards(random.nextInt(30));
//        clientM.getOpponentStats().get(rando1).setNumTrains(random.nextInt(45));
//        clientM.getOpponentStats().get(rando1).setPoints(random.nextInt(100));
//        clientM.notifyPlayerStatsChange();
//    }
//
//
//    private void addOneOfEachTrainCard() {
//        for (TrainCardColor color :  TrainCardColor.values()) {
//            TrainCardM trainCard = new TrainCardM(color);
////            clientM.addTCardToDeck(trainCard);
//        }
//    }
//
//    private void addFaceUpTCards() {
//        int i = 0;
//        for (TrainCardColor color : TrainCardColor.values()) {
//            TrainCardM trainCard = new TrainCardM(color);
//            clientM.addFaceUpTCard(trainCard);
//            if (i >= 3) {
//                TrainCardM wild = new TrainCardM(WILD);
//                clientM.addFaceUpTCard(wild);
//                break;
//            }
//            i++;
//        }
//    }
//
//    public void takeFaceUpTCard(int cardPosition) {
//
//        TrainCardM tCard = clientM.getFaceUpTCards().removeFromTray(cardPosition);
////        clientM.addTCardToDeck(tCard);
//        int pick = new Random().nextInt(TrainCardColor.values().length);
//        TrainCardM newTCard = new TrainCardM(TrainCardColor.values()[pick]);
//        clientM.addFaceUpTCard(newTCard);
//        clientM.setNumTrainCards(clientM.getNumTrainCards()-1);
//        clientM.notifyMyStatsChange();
//    }
//
//    public void drawTCardFromDeck() {
//        int pick = new Random().nextInt(TrainCardColor.values().length);
//        TrainCardM newTCard = new TrainCardM(TrainCardColor.values()[pick]);
////        clientM.addTCardToDeck(newTCard);
//        clientM.setNumTrainCards(clientM.getNumTrainCards()-1);
//        clientM.notifyMyStatsChange();
//    }
//
//    public void getNewDestinationCards() {
//        //TODO: CREATE destination cards and display them
//        String[] cityStrings = {"Buffalo", "Winnapeg", "Reno", "Death Valley",
//                "Pocatello", "BYU"};
//        ArrayList<CityM> cities = new ArrayList<>();
//        for (int i = 0; i < cityStrings.length; i++) {
//            CityM city = new CityM(cityStrings[i]);
//            cities.add(city);
//        }
//
//        ArrayList<DestinationCardM> cards = new ArrayList<>();
//        for (int i = 0; i < cities.size(); i+=2) {
//            DestinationCardM card = new DestinationCardM(cities.get(i), cities.get(i+1),
//                    (i+1)*4);
//            cards.add(card);
//        }
//        DestCardOptions destCardOptions = new DestCardOptions();
//        destCardOptions.setOptions(cards);
//        clientM.setDestCardOptions(destCardOptions);
//    }
//
//
//    private void initOpponentStats() {
//        ArrayList<PlayerInfoM> players = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            UserM user = new UserM("player" + String.valueOf(i), String.valueOf(i));
//            PlayerInfoM player = new PlayerInfoM(user);
//            player.setPoints(0);
//            player.setNumTrainCards(3);
//            player.setNumDestinationCards(2);
//            player.setNumTrains(45);
//            player.setTurn(false);
//            players.add(player);
//        }
////        clientM.setOpponentStats(players);
//    }
//
//    private void initMyStats() {
//        UserM me = new UserM("me", "me");
//        PlayerInfoM Me = new PlayerInfoM(me);
//        Me.setPoints(0);
//        Me.setNumTrainCards(4);
//        Me.setNumTrains(45);
//        Me.setNumDestinationCards(clientM.getDestinationCards().getHandSize());
//        Me.setTurn(true);
////        clientM.setMyStats(Me);
//    }
//
//    private void setNumInDecks(int num) {
//        clientM.setNumTrainCards(num);
//        clientM.setNumDestCards(num);
//    }
//}
