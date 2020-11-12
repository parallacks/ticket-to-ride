package com.ticket_to_ride.server.service;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.StatusType;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.UserM;
import com.ticket_to_ride.server.model.SessionM;

import java.util.UUID;

public class ServerRegisterService {
    private String username, password;
    private SessionM session;
    private UUID commandId;
    private ServerM serverM;
    private Serializer serializer;


    public ServerRegisterService(Command data, SessionM session){
        this.session = session;
        CommandArg usernameArg = data.getArg("username");
        commandId = data.getUUID();
        if (usernameArg.getType().equals("String"))
            this.username = usernameArg.getData();
        CommandArg passwordArg = data.getArg("password");
        if (passwordArg.getType().equals("String"))
            this.password = passwordArg.getData();
        serializer = new Serializer();
        this.serverM = serverM.get();
    }

    public CommandResult execute(){
        CommandResult result = new CommandResult(commandId);

        UserM verifyUser = serverM.getUserByUsername(username);
        if( verifyUser == null) {
            //Make user
            UserM user = new UserM(username, password);
            user.setStatus(StatusType.INLIST);

            serverM.addUser(user);
            session.setUser(user);

            String gameListString = serializer.serialize(serverM.getGames());
            result.addArg(new CommandArg("gameList", "games", gameListString));
        } else {
            result.setError("User already exists");
        }

        return result;
    }
}
