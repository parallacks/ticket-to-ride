package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.GameInfoM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.ChatM;

public class UpdateChatService {
    private Command command;
    private Serializer serializer;
    private ClientM model;

    public UpdateChatService(Command command)
    {
        this.command = command;
        this.serializer = new Serializer();
        this.model = ClientM.get();
    }

    public CommandResult UpdateChat()
    {
        CommandResult res;
        if(command.getArg("chatMessage").getType().equals("ChatM")) {
            ChatM chatMessage = (ChatM) this.serializer.deserialize(this.command.getArg("chatMessage").getData(), ChatM.class);
            this.model.addChat(chatMessage);
            res = new CommandResult(this.command.getUUID());
        }
        else {
            res = new CommandResult(command.getUUID());
            res.setError("Wrong type for ChatM");
        }
        return res;
    }
}
