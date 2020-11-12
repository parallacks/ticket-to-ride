//Created By Lance

package com.ticket_to_ride.server.model;

import com.google.gson.annotations.Expose;
import com.ticket_to_ride.common.StatusType;

public class UserM {

    private String username;
    private String password;
    private transient StatusType status;
    private transient GameInfoM game;

    public UserM(String username, String password) {
        this.username = username;
        this.password = password;
        this.status = StatusType.INLIST;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StatusType getStatus() {
        return this.status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public void setGame(GameInfoM game)
    {
        this.game = game;
    }

    public GameInfoM getGame()
    {
        return this.game;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;

        UserM object = (UserM)o;

        return
            object.password.equals(password) &&
            object.username.equals(username) &&
            object.status == status;
    }

}
