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

public class ClientLoginService
{
    private String username;
    private String password;
    private ClientM model;
    private Serializer serializer;

    public ClientLoginService(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.model = ClientM.get();
        this.serializer = new Serializer();
    }

    public void Login()
    {
        Command command = new Command();
        command.setOperation("login");
        command.setArg("username", "String", this.username);
        command.setArg("password", "String", this.password);
        CommandResult res = ServerProxy.get().login(command);
        if (!res.successful()) {
            model.setErrorMessage(res.getError());
        } else {
            UserM user = new UserM(this.username);
            //Set the username
            CommandArg arg = res.getArg("gameList");
//            System.out.println(serializer.deserialize(arg.getData(), new ArrayList<GameInfoM>().getClass()));
            Type GameInfoMType = new TypeToken<ArrayList<GameInfoM>>(){}.getType();
            ArrayList<GameInfoM> games = (ArrayList<GameInfoM>)serializer.deserialize(arg.getData(), GameInfoMType);
//            System.out.println(games.getClass());
//            System.out.println(games.get(0).getGameName());
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
