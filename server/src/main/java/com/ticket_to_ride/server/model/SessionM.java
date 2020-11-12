package com.ticket_to_ride.server.model;

import java.net.Socket;

import com.ticket_to_ride.common.Connection;
import com.ticket_to_ride.server.ClientProxy;
import com.ticket_to_ride.server.ServerFacade;

public class SessionM {
    private ClientProxy proxy;
    private ServerFacade facade;
    private UserM user;

    public SessionM(Socket sock) {
        user = null;
        Connection conn = new Connection(sock);
        proxy = new ClientProxy(conn);
        facade = new ServerFacade(this);
        conn.setCommandHandler(facade);
    }

    public ClientProxy getProxy() {
        return proxy;
    }

    public void setUser(UserM user) {
        this.user = user;
    }

    public UserM getUser() {
        return user;
    }
}
