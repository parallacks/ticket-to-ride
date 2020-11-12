package com.ticket_to_ride.common.command;

import java.util.UUID;
import com.ticket_to_ride.common.Serializer;

public class Command extends DataMessage {

    private String operation;

    public Command () {
        operation = "";
        commandId = UUID.randomUUID();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        Serializer s = new Serializer();
        return s.serialize(this);
    }
}
