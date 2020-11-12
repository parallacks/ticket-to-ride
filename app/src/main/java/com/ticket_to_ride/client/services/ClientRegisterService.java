//Created by Alex Yancey
package com.ticket_to_ride.client.services;

import com.google.gson.reflect.TypeToken;
import com.ticket_to_ride.client.ServerProxy;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.GameInfoM;
import com.ticket_to_ride.client.model.UserM;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ClientRegisterService
{
    private String username;
    private String password;
    private ClientM model;
    private Serializer serializer;

    public ClientRegisterService(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.model = ClientM.get();
        serializer = new Serializer();
    }

    public void Register()
    {
        Command command = new Command();
        command.setOperation("register");
        command.setArg("username", "String", this.username);
        command.setArg("password", "String", this.password);
        CommandResult res = ServerProxy.get().register(command);
        if (!res.successful()) {
            model.setErrorMessage(res.getError());
        } else {
            UserM user = new UserM(this.username);
            CommandArg arg = res.getArg("gameList");
            Type GameInfoMType = new TypeToken<ArrayList<GameInfoM>>(){}.getType();
            ArrayList<GameInfoM> games = (ArrayList<GameInfoM>)serializer.deserialize(arg.getData(), GameInfoMType);
            model.setLobbyGames(games);
            model.setUser(user);
        }
        return;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
