package com.example.mathias.texta.util.action;

import static com.example.mathias.texta.util.action.MessageType.EMPTY_ACTION;

/**
 * Created by Mathias on 07.02.2017.
 */

public class EmptyAction extends Action {
    String front, end;
    public EmptyAction(){
        super(EMPTY_ACTION);
        front = "";
        end= "";
    }

    public EmptyAction(String front, String end){
        super(EMPTY_ACTION);
        this.front = front;
        this.end= end;
    }

    public String getFront(){
        return front;
    }

    public String getEnd(){
        return end;
    }
}
