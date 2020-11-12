package com.ticket_to_ride.client;

import java.net.UnknownHostException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.ticket_to_ride.common.api.ServerAPI;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.Connection;
import com.ticket_to_ride.common.Serializer;

public class ServerProxy implements ServerAPI {

    private static ServerProxy singleton;

    public static ServerProxy get() {
        return singleton;
    }

    public static void init(String hostname) throws UnknownHostException, IOException {
        singleton = new ServerProxy(hostname, 3000);
    }

    private Connection conn;

    private ServerProxy(String hostname, int port) throws UnknownHostException, IOException {
        conn = new Connection(hostname, port);
        //conn.setCommandHandler(ClientFacade.get());
        conn.setCommandHandler(ServerCommandManager.get());
    }

    @Override
    public CommandResult register(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;    }

    @Override
    public CommandResult login(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult createGame(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;    }

    @Override
    public CommandResult joinGame(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;    }

    @Override
    public CommandResult setReady(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult sendChat(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult chooseCards (Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult chooseMove(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

    @Override
    public CommandResult sendGameHistory(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }
    @Override
    public CommandResult turnOver(Command command) {
        CommandResult res = conn.sendCommand(command);
        return res;
    }

}
