package com.ticket_to_ride.common.model;

public class DestinationCardM extends CardM
{
    private CityM cityM1;
    private CityM cityM2;
    private int points;
    private boolean completed;

    public DestinationCardM(CityM cityM1, CityM cityM2, int points)
    {
        this.cityM1 = cityM1;
        this.cityM2 = cityM2;
        this.points = points;
        this.completed = false;
    }

    public DestinationCardM(CityM cityM1, CityM cityM2, int points, boolean completed) {
        this.cityM1 = cityM1;
        this.cityM2 = cityM2;
        this.points = points;
        this.completed = completed;
    }

    public CityM getCityM1() {
        return cityM1;
    }

    public CityM getCityM2() {
        return cityM2;
    }

    public int getPoints() {
        return points;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;

        DestinationCardM object = (DestinationCardM) o;

        return object.cityM1.getName().equals(cityM1.getName()) &&
                object.cityM2.getName().equals(cityM2.getName()) &&
                object.points == points;
    }
}
