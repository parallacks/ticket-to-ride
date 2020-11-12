package com.ticket_to_ride.client.model;

import com.google.gson.annotations.Expose;
import com.ticket_to_ride.common.model.MapM;

import java.util.ArrayList;

public class GameInfoM {
    private ArrayList<PlayerInfoM> players = new ArrayList<>();
    private String gameName;
    private String status;
    private MapM map;


    public GameInfoM(String gameName, PlayerInfoM player)
    {
        this.gameName = gameName;
        this.players.add(player);
    }
    public GameInfoM(String gameName, PlayerInfoM player, MapM m)
    {
        this.gameName = gameName;
        this.players.add(player);
        map = m;
    }

    public ArrayList<PlayerInfoM> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerInfoM> players)
    {
        this.players = players;
    }

    public void addPlayer(PlayerInfoM player)
    {
        players.add(player);
    }

    public PlayerInfoM getPlayerByUsername(String username)
    {
        for (PlayerInfoM player : players)
        {
            if (player.getUserM().getUsername().equals(username))
                return player;
        }
        return null;
    }

    public String getGameName() {
        return gameName;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public MapM getMap () {
        return map;
    }
    public void setMap (MapM m){
        map = m;
    }
}