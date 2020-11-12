package com.ticket_to_ride.common.model;

public class TrainCardM extends CardM
{
    private TrainCardColor color;

    public TrainCardM(TrainCardColor color)
    {
        this.color = color;
    }

    public TrainCardColor getColor()
    {
        return this.color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;

        TrainCardM object = (TrainCardM) o;

        return object.color.equals(color);
    }
}
