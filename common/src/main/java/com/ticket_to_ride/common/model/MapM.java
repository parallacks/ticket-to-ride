package com.ticket_to_ride.common.model;

import com.ticket_to_ride.common.data.RouteID;

import java.util.ArrayList;

public class MapM
{
    private ArrayList<CityM> cities;
    private ArrayList<RouteM> routes;

    public MapM()
    {
        this.cities = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    public ArrayList<CityM> getCities() {
        return cities;
    }

    public ArrayList<RouteM> getRoutes() {
        return routes;
    }

    public void addCity(CityM city)
    {
        this.cities.add(city);
    }

    public void addRoutes(RouteM route)
    {
        this.routes.add(route);
    }

    public CityM getCityByName(String name)
    {
        for (CityM c: this.cities)
        {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    public RouteM getRouteById(RouteID id)
    {
        for (RouteM r: this.routes)
        {
            if (r.getId() == id)
                return r;
        }
        return null;
    }
    //Created by Lance
    public RouteID findDoubleMatch(RouteID route1){

        if(Character.isDigit(route1.toString().charAt(route1.toString().length()-1))) {
            int routeNum= (int)route1.toString().charAt(route1.toString().length()-1);
            String route1String = route1.toString().substring(0, route1.toString().length() - 2);

            for (RouteM r : this.routes) {
                if (r.getId().toString().contains(route1String)) {
                    int route2Num = (int)r.getId().toString().charAt(r.getId().toString().length()-1);
                    if(routeNum!=route2Num)
                        return r.getId();
                }
            }
        }
        return null;
    }
}
