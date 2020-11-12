package com.ticket_to_ride.common.api;

import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;

import java.util.List;

public interface ServerAPI {

    /*
        Register endpoint

        @operation register

        @param String username
        @param String password

     */
    public CommandResult register(Command command);
    /*
        Login endpoint

        @operation login

        @param String username
        @param String password

     */
    public CommandResult login(Command command);
    /*
        CreateGame endpoint

        @operation createGame

        @param NONE
     */
    public CommandResult createGame(Command command);
    /*
        JoinGame endpoint

        @operation joinGame

        @param String gameName
     */
    public CommandResult joinGame(Command command);
    /*
        SetReady endpoint

        @operation setReady

        @param NONE
     */
    public CommandResult setReady(Command command);
    /*
        SendChat endpoint

        @operation sendChat

        @param String message
     */
    public CommandResult sendChat(Command command);
    /*
        ChooseCards endpoint

        @operation chooseCards

        //TODO @Params here depending on how we want to pick cards

     */
    public CommandResult chooseCards(Command command);
    /*
        chooseMove endpoint

        @operation chooseMove

        @param MoveID move
     */
    public CommandResult chooseMove(Command command);
    /*
        GameHistory endpoint

        @operation sendGameHistory
     */
    public CommandResult sendGameHistory(Command command);
    // ...etc
    // Add calls the client makes to the server here.
    CommandResult turnOver(Command command);
}
