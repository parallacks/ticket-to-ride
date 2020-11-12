package com.ticket_to_ride.server.model.util;

import java.util.Observable;

//TODO make ModelFlag generic class and type T implement Watcher
public class ModelFlag extends Observable {

    public ModelFlag() {
        super();
    }

    public void set() {
        setChanged();
        notifyObservers();
    }

    public void set(Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
