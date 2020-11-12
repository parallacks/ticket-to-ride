package com.example.sqlite;

import com.ticket_to_ride.common.database_interfaces.IDatabase;
import com.ticket_to_ride.common.database_interfaces.IObjectID;
import com.ticket_to_ride.common.database_interfaces.ITableID;

public class SqliteAdapter implements IDatabase {
    @Override
    public boolean startTransaction() {
        return false;
    }

    @Override
    public IObjectID store(ITableID tableID, String data) {
        return null;
    }

    @Override
    public boolean delete(ITableID tableID, IObjectID objectID) {
        return false;
    }

    @Override
    public String get(ITableID tableID, IObjectID objectID) {
        return null;
    }

    @Override
    public boolean update(ITableID tableID, IObjectID objectID, String data) {
        return false;
    }

    @Override
    public boolean closeTransaction() {
        return false;
    }
}
