package com.example.mathias.texta.util.action;

/**
 * Created by Mathias on 03.02.2017.
 */

public abstract class Action {
    MessageType type;

    public Action(MessageType type){
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }
}


