package com.ticket_to_ride.client.services;

import com.ticket_to_ride.client.ServerProxy;
import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.ChatM;
import com.ticket_to_ride.common.model.MessageM;

public class ClientChatService
{
    private ClientM model;
    private Serializer serializer;
    private MessageM chatMessage;

    public ClientChatService(MessageM chatMessage) {
        //The Chat object needs author, timestamp, and message
        this.model = ClientM.get();
        this.serializer = new Serializer();
        this.chatMessage = chatMessage;
    }

    public void SendChat()
    {
        Command command = new Command();
        command.setOperation("sendChat");
        String chatData = serializer.serialize(this.chatMessage);
        command.setArg("chatMessage", "ChatM", chatData);
        //command.setArg("gameName", "String", model.getActiveGame().getGameName());
        CommandResult res = ServerProxy.get().sendChat(command);
        if (!res.successful())
        {
            //TODO set the error message otherwise just wait for the command to come back from the server
            model.setErrorMessage(res.getError());
        }
    }
}
