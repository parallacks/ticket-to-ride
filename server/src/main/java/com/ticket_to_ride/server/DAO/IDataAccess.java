package com.ticket_to_ride.server.DAO;

public interface IDataAccess {
    boolean add(Object obj);
    Object get(Object obj);
    boolean remove(Object obj);
    boolean clear();
}
