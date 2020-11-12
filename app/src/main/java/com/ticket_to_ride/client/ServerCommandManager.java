package com.ticket_to_ride.client;

import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.command.ICommandHandler;

public class ServerCommandManager implements ICommandHandler
{
    private ClientFacade clientFacade;
    private static ServerCommandManager instance;

    public static ServerCommandManager get()
    {
        if (instance == null)
            instance = new ServerCommandManager();
        return instance;
    }

    private ServerCommandManager()
    {
        clientFacade = ClientFacade.get();
    }

    @Override
    public CommandResult handle(Command command) {
        CommandResult res = new CommandResult(command.getUUID());

        switch(command.getOperation()) {
            case "updateGameUsers":
                return clientFacade.updateGameUsers(command);
            case "startGame":
                return clientFacade.startGame(command);
            case "updateGameList":
                return clientFacade.updateGameList(command);
            case "updateChat":
                return clientFacade.updateChat(command);
            case "updateTrainDeck":
                return clientFacade.updateDeck(command);
            case "updateDestinationDeck":
                return clientFacade.updateDeck(command);
            case "updateTray":
                return clientFacade.updateTray(command);
            case "updateRoutes":
                return clientFacade.updateRoutes(command);
            case "updateLastRound":
                return clientFacade.updateLastRound(command);
            case "updateGameHistory":
                return clientFacade.updateGameHistory(command);
            case "updatePlayerStats":
                return clientFacade.updatePlayerStats(command);
            case "updatePlayerTurns":
                return clientFacade.updatePlayerTurns(command);
            case "updateMoves":
                return clientFacade.updateMoves(command);
            case "gameOver":
                return clientFacade.gameOver(command);
            default:
                res.setError("Unknown API operation");
                return res;
        }
    }

    @Override
    public void close() {

    }
}
