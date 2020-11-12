package com.ticket_to_ride.common.command;

public interface ICommandHandler {
    public CommandResult handle(Command com);
    public void close();
}
