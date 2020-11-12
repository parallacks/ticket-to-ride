package com.ticket_to_ride.server;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;

import com.ticket_to_ride.common.Connection;
import com.ticket_to_ride.server.model.UserM;
import com.ticket_to_ride.server.model.ServerM;
import com.ticket_to_ride.server.model.SessionM;

public class Server {

    ServerM sm;

    public Server(String dbDirectory, String dbJarName, String dbClassName) {
        sm = ServerM.get();
        sm.setDBData(dbDirectory, dbJarName, dbClassName);

    }
    public void listen(int port) {

        try {
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                Socket s = ss.accept();

                Thread th = new Thread(){
                    @Override
                    public void run(){
                        try {
                            SessionM session = new SessionM(s);
                            sm.addSession(session);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                th.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
