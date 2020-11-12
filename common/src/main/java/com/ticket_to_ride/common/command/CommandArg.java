package com.ticket_to_ride.common.command;

import java.util.ArrayList;

public class CommandArg {
    private String id;
    private String type;
    private String data;

    public CommandArg() {
        id = "";
        type = "";
        data = "";
    }

    public CommandArg(String id, String type, String data)
    {
        this.id = id;
        this.type = type;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
