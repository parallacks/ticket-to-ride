package com.ticket_to_ride.server.DAO;

import com.ticket_to_ride.common.database_interfaces.IDatabase;

public class UsersDAO implements IDataAccess {
    private IDatabase db;

    public UsersDAO(IDatabase db) {
        this.db = db;
    }

    @Override
    public boolean add(Object obj) {
        return false;
    }

    @Override
    public Object get(Object obj) {
        return null;
    }

    @Override
    public boolean remove(Object obj) {
        return false;
    }

    @Override
    public boolean clear() {
        return false;
    }
}
