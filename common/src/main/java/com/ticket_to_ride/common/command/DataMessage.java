package com.ticket_to_ride.common.command;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public abstract class DataMessage {

    private List<CommandArg> data;
    protected UUID commandId;

    public DataMessage() {
        data = new ArrayList<CommandArg>();
    }

    public UUID getUUID() {
        return commandId;
    }

    public CommandArg getArg(String id) {
        for (CommandArg arg : data) {
            if ( id.equals(arg.getId()) ) {
                return arg;
            }
        }
        return null;
    }

    public void setArg(String id, String type, String data) {
        CommandArg arg = getArg(id);

        if (arg == null) {
            arg = new CommandArg();
            this.data.add(arg);
        }

        arg.setId(id);
        arg.setType(type);
        arg.setData(data);
    }

    public void setArg(String id, String type, ArrayList<CommandArg> data) {
        CommandArg arg = getArg(id);

        if (arg == null) {
            arg = new CommandArg();
            this.data.add(arg);
        }

        arg.setId(id);
        arg.setType(type);
    }

    public void addArg(CommandArg arg)
    {
        this.data.add(arg);
    }

    public void removeArg(String id) {
        for (int i = 0; i < data.size(); i++) {
            if ( id.equals(data.get(i).getId()) ) {
                data.remove(i);
                break;
            }
        }
    }
}
