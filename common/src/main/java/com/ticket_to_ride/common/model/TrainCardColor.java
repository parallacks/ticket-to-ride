package com.ticket_to_ride.common.model;

public enum TrainCardColor
{
    RED("RED"), BLUE("BLUE"), YELLOW("YELLOW"), GREEN("GREEN"), ORANGE("ORANGE"),
    PINK("PINK"), WHITE("WHITE"), BLACK("BLACK"), WILD("WILD");

    private String colorStr;

    TrainCardColor(String name) {
        colorStr = name;
    }

    @Override
    public String toString() {
        return colorStr;
    }
}

