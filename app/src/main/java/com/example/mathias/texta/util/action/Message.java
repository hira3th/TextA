package com.example.mathias.texta.util.action;

/**
 * Created by Mathias on 25.01.2017.
 */

public class Message extends Action{
    private String text;
    private boolean incoming;
    private int waitingTime;

    public Message(String text, boolean incoming){
        super(MessageType.MESSAGE);
        this.text = text;
        this.incoming = incoming;
        this.waitingTime = 0;
    }

    public Message(String text, boolean incoming, int waitingTime){
        super(MessageType.MESSAGE);
        this.text = text;
        this.incoming = incoming;
        this.waitingTime = waitingTime;
    }

    public Message(String text){
        super(MessageType.MESSAGE);
        this.text = text;
        this.incoming = true;
        waitingTime = 0;
    }

    public Message(String text, int waitingTime){
        super(MessageType.MESSAGE);
        this.text = text;
        this.incoming = true;
        this.waitingTime = waitingTime;
    }

    public String getText() {
        return text;
    }

    public boolean isIncoming(){
        return incoming;
    }

    public int getWaitingTime(){
        return waitingTime;
    }
}
