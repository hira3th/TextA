package com.example.mathias.texta.util.action;

import com.example.mathias.texta.util.graph.Transition;

/**
 * Created by Mathias on 03.02.2017.
 */

public class TransitionMessage extends Action{
    Transition transition;

    public TransitionMessage(Transition transition){
        super(MessageType.TRANSITION);
        this.transition = transition;
    }

    public Transition getTransition() {
        return transition;
    }
}
