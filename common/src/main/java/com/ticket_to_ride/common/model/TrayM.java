package com.ticket_to_ride.common.model;

import java.util.ArrayList;

public class TrayM
{
    ArrayList<TrainCardM> tray;

    public TrayM()
    {
        tray = new ArrayList<>();
    }

    public TrainCardM removeFromTray(int position)
    {
        return tray.remove(position);
    }

    public void addToTray(TrainCardM card)
    {
        tray.add(card);
    }

    public ArrayList<TrainCardM> getTray() {
        return tray;
    }

    //TODO: TrayCardService needs to be done for phase 3
}
