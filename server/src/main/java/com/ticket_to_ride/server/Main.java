package com.ticket_to_ride.server;


public class Main {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Error! Usage: java [program] [dbDirectory] [dbJarName] [dbClassName]");
            return;
        }
        Server serv = new Server(args[0], args[1], args[2]);

        serv.listen(3000);
    }

}
