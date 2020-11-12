package com.ticket_to_ride.client.model;

import com.google.gson.annotations.Expose;

public class UserM {

    private String username;
    private String password;

    public UserM (String username, String password) {
        this.username = username;
        this.password = password;
    }
    public UserM (String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { return password; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
