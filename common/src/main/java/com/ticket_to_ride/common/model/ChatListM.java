package com.ticket_to_ride.common.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChatListM {
    private ArrayList<MessageM> chatList;
    private Comparator comparator;

    public ChatListM()
    {
        comparator = new Comparator() {
            @Override
            public int compare(Object o, Object t1)
            {
                MessageM c1 = (MessageM) o;
                MessageM c2 = (MessageM) t1;
                return c1.getTimeStamp().compareTo(c2.getTimeStamp());
            }
        };
        chatList = new ArrayList<MessageM>() {
            @Override
            public boolean add(MessageM chat) {
                super.add(chat);
                Collections.sort(chatList, comparator);
                return true;
            }
        };
    }

    public void addChat(MessageM chat)
    {
        chatList.add(chat);
    }

    public ArrayList<MessageM> getChatList()
    {
        return chatList;
    }
}
