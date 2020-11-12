package com.ticket_to_ride.client.model;

public class DeckSizeM
{
    private int size;
    private String type;

    public DeckSizeM(int size, String type)
    {
        this.size = size;
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
