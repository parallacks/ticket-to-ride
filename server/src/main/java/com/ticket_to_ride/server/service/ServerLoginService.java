//Created By Lance

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

public class ServerLoginService {
    private String username, password;
    private SessionM session;
    private UUID commandId;
    private ServerM serverM;
    private Serializer serializer;

    public ServerLoginService(Command data, SessionM session){
        this.session = session;
        CommandArg usernameArg = data.getArg("username");
        commandId = data.getUUID();
        if (usernameArg.getType().equals("String"))
            this.username = usernameArg.getData();
        CommandArg passwordArg = data.getArg("password");
        if (passwordArg.getType().equals("String"))
            this.password = passwordArg.getData();
        serializer = new Serializer();
        this.serverM = ServerM.get();
    }

    public CommandResult execute(){
        CommandResult result = new CommandResult(commandId);

        UserM user = serverM.getUserByUsername(username);

        if(user != null) {
            if (user.getPassword().equals(password)) {
                if (serverM.getUserSession(user) == null){
                    session.setUser(user);
                    user.setStatus(StatusType.INLIST);
                    String gameListString = serializer.serialize(serverM.getGames());
                    result.addArg(new CommandArg("gameList", "games", gameListString));
                } else {
                    result.setError("You are already logged in");
                }
            } else {
                result.setError("Wrong password");
            }
        } else {
            result.setError("User doesn't exist");
        }

        return result;
    }
}
