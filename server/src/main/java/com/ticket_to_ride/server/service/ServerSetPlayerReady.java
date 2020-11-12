package com.ticket_to_ride.server.service;

import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.server.model.GameInfoM;
import com.ticket_to_ride.server.model.PlayerInfoM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.common.StatusType;
import com.ticket_to_ride.server.model.UserM;

import java.util.UUID;

public class ServerSetPlayerReady {

    private String gameName;
    private UserM user;
    private UUID commandId;
    private ServerM serverM;

    public ServerSetPlayerReady (Command data, UserM user) {
        commandId = data.getUUID();
        this.user = user;
        CommandArg gameArg = data.getArg("gameName");
        if (gameArg.getType().equals("String"))
            this.gameName = gameArg.getData();
        serverM = ServerM.get();
    }
    public CommandResult execute() {
        GameInfoM game = serverM.getGameByName(this.gameName);
        for(PlayerInfoM player : game.getPlayers()){
            if(player.getUserM().getUsername().equals(this.user.getUsername()))
                if(player.getUserM().getStatus() == StatusType.INLOBBY)
                    player.getUserM().setStatus(StatusType.READY);
                else
                    player.getUserM().setStatus(StatusType.INLOBBY);
        }

        Thread th = new Thread() {
            @Override
            public void run() {
                if (game.getPlayers().size() > 1) {
                    boolean allReady = true;
                    for (PlayerInfoM player : game.getPlayers()) {
                        if (!player.getUserM().getStatus().equals(StatusType.READY)) {
                            allReady = false;
                        }
                    }

                    if (allReady) {
                        Command startGame = new Command();
                        startGame.setArg("gameName", "String", game.getGameName());
                        ServerStartGameService startGameService = new ServerStartGameService(startGame);
                        startGameService.execute();
                    }
                }
            }
        };
        th.start();

        CommandResult command = new CommandResult(commandId);
        return command;
    }
}
