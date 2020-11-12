package com.ticket_to_ride.server.service;

import com.ticket_to_ride.common.Serializer;
import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandArg;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.model.ChatM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.UserM;

import java.util.UUID;

public class ServerChatService
{
    private Serializer serializer;
    private ServerM serverM;
    private UserM caller;
    private UUID commandId;
    private ChatM chat;
    private String gameName;

    public ServerChatService(Command data, UserM caller) {
        this.caller = caller;
        this.commandId = data.getUUID();
        this.serializer = new Serializer();
        CommandArg messageArg = data.getArg("chatMessage");
        //CommandArg gameNameArg = data.getArg("gameName");
        this.chat = (ChatM)serializer.deserialize(messageArg.getData(), ChatM.class);
        //this.gameName = (String)serializer.deserialize(gameNameArg.getData(), String.class);
        this.gameName = this.caller.getGame().getGameName();
        this.serverM = ServerM.get();
    }

    public CommandResult execute()
    {
        CommandResult res = new CommandResult(this.commandId);

        this.serverM.getGameByName(this.gameName).addToChatList(this.chat);
        this.serverM.getGameByName(this.gameName).addToTempChatList(this.chat);

        return res;
    }
}
