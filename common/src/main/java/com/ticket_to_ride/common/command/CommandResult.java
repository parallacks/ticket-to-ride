package com.ticket_to_ride.common.command;

import java.util.UUID;
import com.ticket_to_ride.common.Serializer;

public class CommandResult extends DataMessage {

    private boolean success;
    private String error;

    public CommandResult(UUID commandId) {
        success = true;
        error = null;
        this.commandId = commandId;
    }

    public boolean successful() {
        return success;
    }

    public void setError(String error) {
        success = false;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        Serializer s = new Serializer();
        return s.serialize(this);
    }
}
