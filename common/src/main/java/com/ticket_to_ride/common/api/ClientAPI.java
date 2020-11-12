package com.ticket_to_ride.common.api;

import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;


public interface ClientAPI {
    /*
        UpdateGameUsers

        @operation updateGameUsers

        @param

     */
    public CommandResult updateGameUsers(Command command);
    /*
        StartGame

        @operation startGame

        @param NONE
     */
    public CommandResult startGame(Command command);
    /*
        UpdateGameList

        @operation updateGameList

        @param ArrayList<GameInfoM> games
     */
    public CommandResult updateGameList(Command command);

    /*
        UpdateChat

        @operation updateChat

        @param ChatM chatMessage
     */
    public CommandResult updateChat(Command command);

    /*
        UpdateDeck

        @operation updateDeck

        @param int deckSize
     */
    public CommandResult updateDeck(Command command);

    /*
        UpdateTray

        @operation updateTray

        @param TrayM tray
     */
    public CommandResult updateTray(Command command);

    /*
        UpdateRoutes

        @operation updateRoutes
     */
    public CommandResult updateRoutes(Command command);
    /*
        UpdateRoutes

        @operation updateLastRound
     */
    public CommandResult updateLastRound(Command command);
    /*
        UpdateGameHistory

        @operation updateGameHistory
     */
    public CommandResult updateGameHistory(Command command);
    /*
        UpdatePlayerStats

        @operation updatePlayerStats
     */
    public CommandResult updatePlayerStats(Command command);
    /*
        UpdatePlayerTurns

        @operation updatePlayerTurns
     */
    public CommandResult updatePlayerTurns(Command command);

    /*
        Prompt client for move

        @operation updateMoves

        @param ArrayList<MoveID> moves
     */
    public CommandResult updateMoves(Command command);

    public CommandResult gameOver(Command command);
}
