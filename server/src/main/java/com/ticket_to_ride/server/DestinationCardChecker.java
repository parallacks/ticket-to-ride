package com.ticket_to_ride.server;

import java.util.List;
import java.util.ArrayList;

import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.CityM;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.RouteM;
import com.ticket_to_ride.common.model.MapM;
import com.ticket_to_ride.server.model.PlayerInfoM;


public class DestinationCardChecker {

    private MapM map;

    public DestinationCardChecker(MapM map) {
        this.map = map;
    }

    public boolean check(DestinationCardM card, PlayerInfoM player) {
        CityM startCity = card.getCityM1();
        CityM endCity = card.getCityM2();

        List<RouteM> routes = new ArrayList<>();
        for (RouteID routeID : player.getClaimedRoutes()) {
            RouteM route = map.getRouteById(routeID);
            routes.add(route);
        }

        RouteM startRoute = null;
        for (RouteM route : routes) {
            if (route.containsCity(startCity)) {
                startRoute = route;
                break;
            }
        }
        if (startRoute == null) return false;


        List<CityM> connectedCities = new ArrayList<>();
        getConnectedCities(routes, connectedCities, startCity);

        return connectedCities.contains(endCity);
    }

    private void getConnectedCities(List<RouteM> paths, List<CityM> connected, CityM city) {
        if (!connected.contains(city)) {
            connected.add(city);

            List<CityM> neighbors = new ArrayList<>();
            for (RouteM path : paths) {
                if (path.containsCity(city)) {
                    //only add the city that doesn't equal `city`
                    if (!path.getCityM1().equals(city)) {
                        neighbors.add(path.getCityM1());
                    }
                    if (!path.getCityM2().equals(city)) {
                        neighbors.add(path.getCityM2());
                    }
                }
            }

            for (CityM neighbor : neighbors) {
                getConnectedCities(paths, connected, neighbor);
            }
        }
    }
}
