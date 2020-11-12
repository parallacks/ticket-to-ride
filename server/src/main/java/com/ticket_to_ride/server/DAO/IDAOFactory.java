package com.ticket_to_ride.server.DAO;

public interface IDAOFactory {
    UsersDAO createUsersDAO();
    GamesDAO createGamesDAO();
    CommandsDAO createCommandsDAO();
}
