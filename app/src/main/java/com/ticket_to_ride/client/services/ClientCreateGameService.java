//Created by Alex Yancey

package com.ticket_to_ride.client.services;

import com.google.gson.reflect.TypeToken;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.client.ServerProxy;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.GameInfoM;
import com.ticket_to_ride.common.model.CityM;
import com.ticket_to_ride.common.model.MapM;
import com.ticket_to_ride.common.model.RouteM;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ClientCreateGameService
{
    private ClientM model;
    private Serializer serializer;

    public ClientCreateGameService() {
        this.model = ClientM.get();
        this.serializer = new Serializer();
    }

    public void CreateGame()
    {
        //TODO: Tell ProxyServer to make a CreateGame Command for the server
        Command command = new Command();
        command.setOperation("createGame");
        CommandResult res = ServerProxy.get().createGame(command);
        if (!res.successful())
        {
            //TODO set the error message otherwise just wait for the command to come back from the server
            model.setErrorMessage(res.getError());
        }
        else
        {
            CommandArg gameInfo = res.getArg("game");
            GameInfoM game = (GameInfoM)serializer.deserialize(gameInfo.getData(), GameInfoM.class);
            //MapM map = (MapM)serializer.deserialize(res.getArg("map").getData(), MapM.class);
            MapM map = new MapM();
            model.setCities(map);
            model.setRoutes(map);
            model.setActiveGame(game);
            model.setGameMap(map);
        }
        return;
    }

}
