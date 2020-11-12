package com.ticket_to_ride.common.model;

import com.ticket_to_ride.common.data.RouteID;


public class RouteM
{
    private CityM cityM1;
    private CityM cityM2;
    private TrainCardColor routeColor;
    private int trainCount;
    private RouteID id;
    private int points;
    private boolean isDouble;

    public RouteM(CityM cityM1, CityM cityM2, TrainCardColor routeColor, int trainCount, RouteID id, int points) {
        this.cityM1 = cityM1;
        this.cityM2 = cityM2;
        this.routeColor = routeColor;
        this.trainCount = trainCount;
        this.id = id;
        this.points = points;
        this.isDouble = false;
    }

    public RouteM(CityM cityM1, CityM cityM2, TrainCardColor routeColor, int trainCount, RouteID id, int points, boolean isDouble)
    {
        this.cityM1 = cityM1;
        this.cityM2 = cityM2;
        this.routeColor = routeColor;
        this.trainCount = trainCount;
        this.id = id;
        this.points = points;
        this.isDouble = isDouble;
    }

    public RouteM(RouteID id)
    {
        this.id = id;
    }

    public CityM getCityM1() {
        return cityM1;
    }

    public CityM getCityM2() {
        return cityM2;
    }

    public TrainCardColor getRouteColor() {
        return routeColor;
    }

    public int getTrainCount() {
        return trainCount;
    }

    public RouteID getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public boolean isDouble(){
        return isDouble;
    }

    public boolean containsCity(CityM city) {
        return cityM1.equals(city) || cityM2.equals(city);
    }

}
