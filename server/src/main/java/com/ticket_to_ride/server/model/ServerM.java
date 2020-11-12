package com.ticket_to_ride.server.model;

import com.ticket_to_ride.common.model.MessageM;
import com.ticket_to_ride.server.DAO.CommandsDAO;
import com.ticket_to_ride.server.DAO.DAOFactory;
import com.ticket_to_ride.server.DAO.GamesDAO;
import com.ticket_to_ride.server.DAO.IDAOFactory;
import com.ticket_to_ride.server.DAO.UsersDAO;
import com.ticket_to_ride.common.database_interfaces.IDatabase;
import com.ticket_to_ride.server.model.util.ModelFlag;
import com.ticket_to_ride.server.model.util.GameFactory;
import com.ticket_to_ride.server.watcher.GameListWatcher;
import com.ticket_to_ride.common.model.MapM;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class ServerM {

    private static ServerM instance;
    private static IDAOFactory daoFactory;
    private static UsersDAO usersDAO;
    private static GamesDAO gamesDAO;
    private static CommandsDAO commandsDAO;

    public static ServerM get() {
        if (instance == null) {
            instance = new ServerM();
        }
        return instance;
    }

    public static void setDBData(String dbDirectory, String dbJarName, String dbClassName) {
        IDatabase db = loadJars(dbDirectory, dbJarName, dbClassName);
        daoFactory = new DAOFactory(db);
        usersDAO = daoFactory.createUsersDAO();
        gamesDAO = daoFactory.createGamesDAO();
        commandsDAO = daoFactory.createCommandsDAO();
    }

    public static IDatabase loadJars(String dbDirectory, String dbJarName, String dbClassName) {
        // Get a class loader and set it up to load the jar file
        try {
            File pluginJarFile = new File(dbDirectory, dbJarName);
            URL pluginURL = pluginJarFile.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{pluginURL});

            // Load the jar file's plugin class, create and return an instance
            Class<? extends IDatabase> dbPlugin = (Class<IDatabase>) loader.loadClass(dbClassName);
            return dbPlugin.getDeclaredConstructor(null).newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<SessionM> sessions;
    private ArrayList<UserM> users;
    private ModelFlag gamesFlag;
    private ArrayList<GameInfoM> games;
    private GameFactory gameFactory;

    private ServerM () {
        users = new ArrayList<>();
        games = new ArrayList<GameInfoM>();
        gamesFlag = new ModelFlag();
        gamesFlag.addObserver(new GameListWatcher());
        sessions = new ArrayList<SessionM>();
        gameFactory = new GameFactory();
    }

    public void setGame(MapM map, DeckM destinationCards, DeckM trainCards)
    {
        gameFactory.setGame(map, destinationCards, trainCards);
    }

    public void addSession(SessionM session) {
        sessions.add(session);
    }

    public void removeSession(SessionM session) {
        sessions.remove(session);
    }

    public SessionM getUserSession(UserM user) {
        for (SessionM session : sessions) {
            if (user.equals(session.getUser())) {
                return session;
            }
        }
        return null;
    }

    public ArrayList<UserM> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserM> users) {
        this.users = users;
    }

    public ArrayList<GameInfoM> getGames() {
        return games;
    }

    public void addGame(GameInfoM g) {
        g.setGameName(gameFactory.getNew());
        games.add(g);
        gamesFlag.set(g);
    }

    public GameInfoM getGameByName(String name){
        for(GameInfoM g : games){
            if(g.getGameName().equals(name))
                return g;
        }
        return null;
    }

    public void addPlayerToGame(GameInfoM game, PlayerInfoM player)
    {
        game.addPlayer(player);
        gamesFlag.set(game);
    }

    public void addUser (UserM user) {
        users.add(user);
    }

    public void removeUser (UserM user) {
        users.remove(user);
    }

    public UserM getUserByUsername (String username) {
        if (username == null)
            return null;

        for(UserM u : users) {
            if (username.equals(u.getUsername()))
                return u;
        }
        return null;
    }
    public void setGameStatus(GameInfoM game, String status){
        game.setStatus(status);
        gamesFlag.set(game);
    }

    public void addToChatList(MessageM chat, String gameName)
    {
        GameInfoM game = getGameByName(gameName);
        game.addToChatList(chat);
        return;
    }
}
