package com.ticket_to_ride.client;

import com.ticket_to_ride.client.services.UpdateChatService;
import com.ticket_to_ride.client.services.UpdateDeckService;
import com.ticket_to_ride.client.services.UpdateGameHistoryService;
import com.ticket_to_ride.client.services.UpdateGameOverService;
import com.ticket_to_ride.client.services.UpdateMovesService;
import com.ticket_to_ride.client.services.UpdatePlayerStatsService;
import com.ticket_to_ride.client.services.UpdatePlayerTurnsService;
import com.ticket_to_ride.client.services.UpdateRouteService;
import com.ticket_to_ride.client.services.UpdateTrayService;
import com.ticket_to_ride.common.api.ClientAPI;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.command.ICommandHandler;

import com.ticket_to_ride.client.services.ClientStartGameService;
import com.ticket_to_ride.client.services.UpdateGameListService;
import com.ticket_to_ride.client.services.UpdateGameUsersService;

public class ClientFacade implements ClientAPI {

    private static ClientFacade singleton;

    public static ClientFacade get() {
        if (singleton == null) {
            singleton = new ClientFacade();
        }
        return singleton;
    }

    private ClientFacade() {}

    public void close() {
        //Connection has been closed

        //TODO put call to service for a what the app should do when a
        //     connection closes. This will probably be just logging out and
        //     showing a toast.
    }

    @Override
    public CommandResult updateGameUsers(Command command) {
        UpdateGameUsersService gameUsersService = new UpdateGameUsersService(command);
        CommandResult result = gameUsersService.UpdateGameUsers();
        return result;
    }

    @Override
    public CommandResult startGame(Command command) {

        ClientStartGameService startGameService = new ClientStartGameService(command);
        startGameService.StartGame();
        CommandResult result = new CommandResult(command.getUUID());
        return result;
    }

    @Override
    public CommandResult updateGameList(Command command) {
        UpdateGameListService gameListService = new UpdateGameListService(command);
        CommandResult result = gameListService.UpdateGameList();
        return result;
    }

    @Override
    public CommandResult updateChat(Command command) {
        UpdateChatService updateChatService = new UpdateChatService(command);
        CommandResult result = updateChatService.UpdateChat();
        return result;
    }

    @Override
    public CommandResult updateDeck(Command command) {
        UpdateDeckService updateDeckService = new UpdateDeckService(command);
        CommandResult result = updateDeckService.UpdateDeck();
        return result;
    }

    @Override
    public CommandResult updateTray(Command command) {
        UpdateTrayService updateTrayService = new UpdateTrayService(command);
        CommandResult result = updateTrayService.UpdateTray();
        return result;
    }

    @Override
    public CommandResult updateRoutes(Command command) {
        UpdateRouteService updateRouteService = new UpdateRouteService(command);
        CommandResult result = updateRouteService.UpdateRoute();
        return result;
    }

    @Override
    public CommandResult updateLastRound(Command command) {
        //TODO
        return new CommandResult(command.getUUID());
    }

    @Override
    public CommandResult updateGameHistory(Command command) {
        UpdateGameHistoryService updateGameHistoryService = new UpdateGameHistoryService(command);
        CommandResult result = updateGameHistoryService.UpdateGameHistory();
        return result;
    }

    @Override
    public CommandResult updatePlayerStats(Command command) {
        UpdatePlayerStatsService updatePlayerStatsService = new UpdatePlayerStatsService(command);
        CommandResult result = updatePlayerStatsService.UpdatePlayerStats();
        return result;
    }

    @Override
    public CommandResult updatePlayerTurns(Command command) {
        UpdatePlayerTurnsService updatePlayerTurnsService = new UpdatePlayerTurnsService(command);
        CommandResult result = updatePlayerTurnsService.UpdatePlayerTurns();
        return result;
    }

    @Override
    public CommandResult updateMoves(Command command) {
        UpdateMovesService updateMovesService = new UpdateMovesService(command);
        CommandResult result = updateMovesService.UpdateMoves();
        return result;
    }

    @Override
    public CommandResult gameOver(Command command) {
        UpdateGameOverService updateGameOverService = new UpdateGameOverService(command);
        CommandResult result = updateGameOverService.UpdateGameOver();
        return result;
    }
}
