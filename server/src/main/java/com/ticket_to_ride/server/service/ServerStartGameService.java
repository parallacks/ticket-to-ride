package com.ticket_to_ride.server.service;

import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.PlayerColor;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.common.StatusType;

import java.util.ArrayList;
import java.util.UUID;

public class ServerStartGameService {

    private ServerM serverM;
    private UUID commandId;
    private String gameName;

    public ServerStartGameService (Command data) {
        CommandArg gameNameArg = data.getArg("gameName");
        commandId = data.getUUID();
        if (gameNameArg.getType().equals("String")){
            this.gameName = gameNameArg.getData();
        }
        serverM = ServerM.get();
    }

    public CommandResult execute() {
        GameInfoM game = serverM.getGameByName(gameName);
        ArrayList<PlayerInfoM> players = game.getPlayers();
        for (PlayerInfoM player : players)
        {
            player.getUserM().setStatus(StatusType.INGAME);
        }

        players.get(0).setPlayerColor(PlayerColor.RED);
        players.get(0).setTurnOrder(1);
        players.get(0).setTurn(true);
        players.get(1).setPlayerColor(PlayerColor.BLUE);
        players.get(1).setTurnOrder(2);
        players.get(1).setTurn(false);
        if (players.size() > 2) {
            players.get(2).setPlayerColor(PlayerColor.GREEN);
            players.get(2).setTurnOrder(3);
            players.get(2).setTurn(false);
        }
        if (players.size() > 3) {
            players.get(3).setPlayerColor(PlayerColor.YELLOW);
            players.get(3).setTurnOrder(4);
            players.get(3).setTurn(false);
        }
        if (players.size() > 4) {
            players.get(4).setPlayerColor(PlayerColor.PURPLE);
            players.get(4).setTurnOrder(5);
            players.get(4).setTurn(false);
        }

        serverM.setGameStatus(game, "Active");
//        serverM.getGames().remove(game);
        CommandResult com = new CommandResult(commandId);

        //com.setArg("gameName", "String", game.getGameName());
        return com;
    }
}
