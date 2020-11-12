package com.ticket_to_ride.common.database_interfaces;

public interface IDatabase {
    boolean startTransaction();
    IObjectID store(ITableID tableID, String data);
    boolean delete(ITableID tableID, IObjectID objectID);
    String get(ITableID tableID, IObjectID objectID);
    boolean update(ITableID tableID, IObjectID objectID, String data);
    boolean closeTransaction();
}
