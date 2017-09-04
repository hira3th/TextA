package com.example.mathias.texta.util.graph;

import com.example.mathias.texta.game.levels.level01.RequestedInput;
import com.example.mathias.texta.util.action.Action;
import com.example.mathias.texta.util.action.DisableUserTextInput;
import com.example.mathias.texta.util.action.EnableUserTextInput;
import com.example.mathias.texta.util.action.Message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Mathias on 02.02.2017.
 */

public class Node {
    private LinkedList<Action> actions;
    private Map<Transition, Node> successors;
    private boolean needsTextInput;
    private RequestedInput requestedInput;

    public Node() {
        this.actions = new LinkedList<>();
        this.successors = new HashMap<>();
        needsTextInput = false;
    }

    public void addSuccessor(Transition transition, Node node) {
        this.successors.put(transition, node);
    }

    public void setNeedsTextInput(boolean bool, RequestedInput requestedInput) {
        this.needsTextInput = bool;
        this.requestedInput = requestedInput;
    }

    public Node getNext(Transition transition) {
        return successors.get(transition);
    }

    public void addAction(String text) {
        this.actions.offer(new Message(text));
    }

    public void enableUserTextInput() {
        this.actions.offer(new EnableUserTextInput());
    }

    public void disableUserTextInput() {
        this.actions.offer(new DisableUserTextInput());
    }

    public Action getAction() {
        return this.actions.poll();
    }

    public LinkedList<Action> getActions() {
        return this.actions;
    }

    public void addAction(String text, int waitingTime) {
        this.actions.offer(new Message(text, waitingTime));
    }

    public void addAction(Action action) {
        this.actions.offer(action);
    }

    public boolean needsTextInput() {
        return needsTextInput;
    }

    public boolean hasMessages() {
        return (!actions.isEmpty());
    }

    public RequestedInput getRequestedInput() {
        return this.requestedInput;
    }
}
