package com.ticket_to_ride.server.watcher;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;


import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.model.CardM;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.server.model.DeckM;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.SessionM;
import com.ticket_to_ride.common.StatusType;
import com.ticket_to_ride.server.model.UserM;

public class GameListWatcher implements Observer {

    private Serializer serializer;

    public GameListWatcher() {
        serializer = new Serializer();
    }

    @Override
    public void update(Observable target, Object arg) {
        if (arg == null) return;
        if (arg.getClass() != GameInfoM.class) return;
        GameInfoM game = (GameInfoM)arg;

        ServerM serverM = ServerM.get();

        if (game.getStatus().equals("Active")){
            //Command command = new Command();
            DeckM destinationCards = game.getDestinationCards();
            DeckM trainCards = game.getTrainCards();
            //command.setOperation("startGame");
            game.drawStartingTrayCards();
            for (PlayerInfoM player : game.getPlayers()){
                Thread th = new Thread() {
                  @Override
                  public void run () {
                      Command command = new Command();
                      command.setOperation("startGame");
                      ArrayList<DestinationCardM> startingDestinations = game.drawStartingDestinationCards();
                      ArrayList<TrainCardM> startingTrainCards = game.drawStartingTrainCards();
                      String destinationCardData = serializer.serialize(startingDestinations);
                      String trainCardData = serializer.serialize(startingTrainCards);
                      String trayData = serializer.serialize(game.getTray());
                      command.setArg("startingDestinations", "ArrayList<DestinationCardM>", destinationCardData);
                      command.setArg("startingTrainCards", "ArrayList<TrainCardM>", trainCardData);
                      command.setArg("tray", "TrayM", trayData);
                      command.setArg("destinationDeckSize", "int", Integer.toString(destinationCards.getDeckSize()));
                      command.setArg("trainDeckSize", "int", Integer.toString(trainCards.getDeckSize()));
                      command.setArg("turn", "Boolean", Boolean.toString(player.isTurn()));
                      command.setArg("color", "PlayerColor", player.getPlayerColor().toString());
                      UserM user = serverM.getUserByUsername(player.getUserM().getUsername());
                      SessionM session = serverM.getUserSession(user);

                      if (session != null) {
                          session.getProxy().startGame(command);
                      }

                      //TODO: Add cards to playerinfo as well
                      for (TrainCardM card : startingTrainCards)
                      {
                          player.getTrainCards().addToHand(card);
                      }
                      for (DestinationCardM card : startingDestinations)
                      {
                          player.getDestinationCards().addToHand(card);
                      }
                  }
                };
                th.start();

            }
            for (UserM user : serverM.getUsers())
            {
                Command command = new Command();
                command.setOperation("updateGameList");
                String gameString = serializer.serialize(game);
                command.setArg("game", "GameInfoM", gameString);
                if (user.getStatus() == StatusType.INLIST)
                {
                    Thread th = new Thread() {
                        @Override
                        public void run() {
                            SessionM session = serverM.getUserSession(user);

                            //If user is logged out, don't send anything
                            if (session != null) {
                                session.getProxy().updateGameList(command);
                            }
                        }
                    };
                    th.start();
                }
            }
        }
        else if (game.getPlayers().size() > 1)
        {
            //Join

//            String gameString = serializer.serialize(game);
//            command.setArg("game", "GameInfoM", gameString);
//            String mapString = serializer.serialize(game.getMap());
//            command.setArg("map", "MapM", mapString);

            for (PlayerInfoM player : game.getPlayers())
            {
                Thread th = new Thread() {
                    @Override
                    public void run() {
                        if (!player.equals(game.getPlayers().get(game.getPlayers().size() - 1)))
                        {
                            UserM user = serverM.getUserByUsername(player.getUserM().getUsername());
                            SessionM session = serverM.getUserSession(user);
                            if (session != null) {
                                Command command = new Command();
                                command.setOperation("updateGameUsers");
                                String playerString = serializer.serialize(game.getPlayers().get(game.getPlayers().size() - 1));
                                command.setArg("player", "PlayerInfoM", playerString);
                                session.getProxy().updateGameUsers(command);
                            }
                        }
                    }
                };
                th.start();
            }
        }
        else
        {
            //Create
            Command command = new Command();
            command.setOperation("updateGameList");
            String gameString = serializer.serialize(game);
            command.setArg("game", "GameInfoM", gameString);

            for (UserM user : serverM.getUsers())
            {
                if (user.getStatus() == StatusType.INLIST)
                {
                    Thread th = new Thread() {
                        @Override
                        public void run() {
                            SessionM session = serverM.getUserSession(user);

                            //If user is logged out, don't send anything
                            if (session != null) {
                                session.getProxy().updateGameList(command);
                            }
                        }
                    };
                    th.start();
                }
            }
        }
    }
}
