package com.ticket_to_ride.server.DAO;

import com.ticket_to_ride.common.database_interfaces.IDatabase;

public class DAOFactory implements IDAOFactory {
    private IDatabase db;

    public DAOFactory(IDatabase db) {
        this.db = db;
    }

    @Override
    public UsersDAO createUsersDAO() {
        return new UsersDAO(db);
    }

    @Override
    public GamesDAO createGamesDAO() {
        return new GamesDAO(db);
    }

    @Override
    public CommandsDAO createCommandsDAO() {
        return new CommandsDAO(db);
    }
}
