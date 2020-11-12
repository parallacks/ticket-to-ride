package com.ticket_to_ride.common;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ticket_to_ride.common.command.Command;
import com.ticket_to_ride.common.command.CommandResult;
import com.ticket_to_ride.common.command.ICommandHandler;
import com.ticket_to_ride.common.util.AwaitWrapper;

public class Connection {

    private Socket socket;
    private ICommandHandler handler;
    private Serializer ser;
    private Lock resultsLock;
    private Lock socketWriteLock;
    private Map<UUID,AwaitWrapper<CommandResult>> reservations;

    public Connection(Socket socket) {
        ser = new Serializer();
        reservations = new TreeMap<>();
        handler = null;
        this.socket = socket;

        // Set up locks
        resultsLock = new ReentrantLock();
        socketWriteLock = new ReentrantLock();
    }

    public Connection(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        ser = new Serializer();
        reservations = new TreeMap<>();
        handler = null;

        // Set up locks
        resultsLock = new ReentrantLock(true);
        socketWriteLock = new ReentrantLock(true);
    }

    /* Sends a command on the socket, and waits for the response
     *
     * @param com The command to be sent
     * @return The Result of the command operation.
     */
    public CommandResult sendCommand(Command com) {
        try {
            AwaitWrapper<CommandResult> delayedResult = reserveResult(com);
            write(new Message(com));

            CommandResult res = delayedResult.waitOnResource();
            removeReservation(com);
            return res;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO decompose: listens and sets the handlers
    /* Sets handler for incoming Commands. Also starts listening to the socket
     *
     * @param handler The handler for incoming Commands
     */
    public void setCommandHandler(ICommandHandler handler) {
        if (handler == null) return;
        this.handler = handler;
        startListening();
    }

    /* Starts a thread that listens to the socket
     *
     * Note: don't call this until handler has been set!
     */
    private void startListening() {
        Thread listener = new Thread() {
            @Override
            public void run() {
                while (receiveMessage());
                handler.close();
            }
        };
        listener.start();
    }

    /* Receives all messages (responses _and_ results) on a socket
     *
     * Note: don't call this until handler has been set!
     *
     * @return true if successful, false if error
     */
    private boolean receiveMessage() {
        InputStream in = null;
        try {
            in = socket.getInputStream();

            Message message = (Message)ser.deserialize(in, Message.class);
            if (message == null) return false;

            if (message.containsCommand()) {
                // Handle receiving a command
                Command com = message.getCommand();
                CommandResult res = handler.handle(com);
                write(new Message(res));

            } else if (message.containsResult()) {
                // Handle receiving a result
                CommandResult res = message.getResult();
                fulfilReservation(res);
            } else {
                // Neither command or result...?
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //--------------------------
    // Thread safe IO operations

    private void write(Message mes) throws IOException {
        OutputStream out = socket.getOutputStream();
        socketWriteLock.lock();
        ser.serialize(out, mes);

        /* BEHOLD THE GREAT AND MIGHTY HACK.
         * After 20+ hours of debugging why some commands were being
         * completely missed on the command-recieving end of the
         * connection, I finally figured it out.
         *
         * Apparently, when writes to the socket were close enough
         * to each other, the OS would write these to the same tcp
         * packet. This is fine and really shouldn't matter.
         *
         * BUT SOMEHOW, GSON MANAGES TO JUST COMPLETELY FORGET ANY
         * EXISTENCE OF COMMANDS AFTER THE FIRST WHEN THEY ARE IN
         * THE SAME TCP PACKET. HOW WOULD IT EVEN DO THAT???
         *
         * Anyways, this sleep will ensure that indiviual
         * serializations get their own packets. It's ugly, but
         * I shamelessly blame GSON.
         */
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
        socketWriteLock.unlock();
    }


    //----------------------------------
    //Thread safe reservation operations

    private AwaitWrapper<CommandResult> reserveResult(Command com) {
        AwaitWrapper<CommandResult> delayedResult = new AwaitWrapper<CommandResult>();

        resultsLock.lock();
        try {
            reservations.put(com.getUUID(), delayedResult);
        }finally {
            resultsLock.unlock();
        }


        return delayedResult;
    }

    private void fulfilReservation(CommandResult res) {
        resultsLock.lock();
        reservations.get(res.getUUID()).setResource(res);
        resultsLock.unlock();
    }

    private void removeReservation(Command com) {
        resultsLock.lock();
        reservations.remove(com.getUUID());
        resultsLock.unlock();
    }


    //-------------------------------------------------------------------
    // A Message wraps both commands and results for easy deserialization

    private class Message {
        private Command command;
        private CommandResult result;

        public Message(Command command) {
            this.command = command;
            this.result = null;
        }

        public Message(CommandResult result) {
            this.command = null;
            this.result = result;
        }

        public Command getCommand() {
            return command;
        }

        public CommandResult getResult() {
            return result;
        }

        public boolean containsCommand() {
            return command != null;
        }

        public boolean containsResult() {
            return result != null;
        }

        @Override
        public String toString() {
            if (command != null) return command.toString();
            if (result != null) return result.toString();
            return super.toString();
        }
    }
}
