package com.ticket_to_ride.server;

import java.util.List;

import com.ticket_to_ride.common.api.ClientAPI;
import com.ticket_to_ride.common.Connection;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;

public class ClientProxy implements ClientAPI {

    private Connection conn;

    public ClientProxy(Connection conn) {
        this.conn = conn;
    }

    public CommandResult updateGameUsers(Command command) {

        CommandResult res = conn.sendCommand(command);
        return res;
    }

    public CommandResult startGame(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    public CommandResult updateGameList(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    public CommandResult updateChat(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult updateDeck(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult updateTray(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult updateRoutes(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult updateLastRound(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult updateGameHistory(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult updatePlayerStats(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult updatePlayerTurns(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult updateMoves(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult gameOver(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }
}
