package com.ticket_to_ride.server;

import com.ticket_to_ride.common.api.ServerAPI;
import com.ticket_to_ride.common.command.ICommandHandler;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.server.model.UserM;

import com.ticket_to_ride.server.service.ServerChatService;
import com.ticket_to_ride.server.model.SessionM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.service.ServerChooseCardsService;
import com.ticket_to_ride.server.service.ServerChooseMoveService;
import com.ticket_to_ride.server.service.ServerCreateGameService;
import com.ticket_to_ride.server.service.ServerGameHistoryService;
import com.ticket_to_ride.server.service.ServerJoinGameService;
import com.ticket_to_ride.server.service.ServerLoginService;
import com.ticket_to_ride.server.service.ServerRegisterService;
import com.ticket_to_ride.server.service.ServerSetPlayerReady;
import com.ticket_to_ride.server.service.ServerTurnOverService;


public class ServerFacade implements ServerAPI, ICommandHandler {

    private SessionM session;

    public ServerFacade(SessionM session) {
        this.session = session;
    }

    public UserM getUser() {
        return this.session.getUser();
    }

    public void setUser(UserM user) {
        session.setUser(user);
    }

    public CommandResult handle(Command command) {
        CommandResult res = new CommandResult(command.getUUID());

        switch(command.getOperation()) {
            case "register":
                return this.register(command);
            case "login":
                return this.login(command);
            case "createGame":
                return this.createGame(command);
            case "joinGame":
                return this.joinGame(command);
            case "setReady":
                return this.setReady(command);
            case "sendChat":
                return this.sendChat(command);
            case "chooseCards":
                return this.chooseCards(command);
            case "chooseMove":
                return this.chooseMove(command);
            case "sendGameHistory":
                return this.sendGameHistory(command);
            case "turnOver":
                return this.turnOver(command);
            default:
                res.setError("Unknown API operation");
                return res;
        }
    }

    public void close() {
        ServerM.get().removeSession(session);
    }

    public CommandResult register(Command command) {
        ServerRegisterService registerService = new ServerRegisterService(command, session);
        return registerService.execute();
    }

    public CommandResult login(Command command) {
        ServerLoginService loginService = new ServerLoginService(command, session);
        return loginService.execute();
    }

    public CommandResult createGame(Command command) {
        ServerCreateGameService createGameService = new ServerCreateGameService(command, session.getUser());
        return createGameService.execute();
    }

    public CommandResult joinGame(Command command) {
        ServerJoinGameService joinGameService = new ServerJoinGameService(command, session.getUser());
        return joinGameService.execute();
    }

    public CommandResult setReady(Command command) {
        ServerSetPlayerReady setPlayerReadyService = new ServerSetPlayerReady(command, session.getUser());
        return setPlayerReadyService.execute();
    }

    public CommandResult sendChat(Command command) {
        ServerChatService serverChatService = new ServerChatService(command, session.getUser());
        return serverChatService.execute();
    }
    public CommandResult chooseCards(Command command) {
        ServerChooseCardsService serverChooseCardsService = new ServerChooseCardsService(command, session.getUser());
        return serverChooseCardsService.execute();
    }

    @Override
    public CommandResult chooseMove(Command command) {
        ServerChooseMoveService serverChooseMoveService = new ServerChooseMoveService(command, session.getUser());
        return serverChooseMoveService.execute();
    }

    @Override
    public CommandResult sendGameHistory(Command command) {
        ServerGameHistoryService serverGameHistoryService = new ServerGameHistoryService(command, session.getUser());
        return serverGameHistoryService.execute();
    }
    @Override
    public CommandResult turnOver(Command command){
        ServerTurnOverService serverTurnOverService = new ServerTurnOverService(command, session.getUser());
        return serverTurnOverService.execute();
    }
}
