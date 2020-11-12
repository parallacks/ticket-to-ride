package com.ticket_to_ride.common.model;

public class MessageM {
    private String author;
    private String timeStamp;
    private String message;

    public MessageM (String author, String timeStamp, String message) {
        this.author = author;
        this.timeStamp = timeStamp;
        this.message = message;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
