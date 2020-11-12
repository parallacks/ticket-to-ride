package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.PlayerColor;

public class UpdateRouteService {
    private Command command;
    private ClientM model;
    private Serializer ser;
    private PlayerColor playerColor;
    private int routeID;

    public UpdateRouteService(Command command)
    {
        this.command = command;
        model = ClientM.get();
        ser = new Serializer();
    }

    public CommandResult UpdateRoute()
    {
        CommandResult res = new CommandResult(command.getUUID());

        RouteID routeID = (RouteID)ser.deserialize(command.getArg("claimedRoute").getData(), RouteID.class);
        PlayerInfoM player = model.getActiveGame().getPlayerByUsername((String)ser.deserialize(command.getArg("playerName").getData(), String.class));
        model.addClaimedRoutes(routeID, player);

        return res;
    }
}
