package com.example.mathias.texta.game.levels.level01;

import com.example.mathias.texta.game.GameThread;
import com.example.mathias.texta.game.levels.Level;
import com.example.mathias.texta.util.action.Action;
import com.example.mathias.texta.util.action.EmptyAction;
import com.example.mathias.texta.util.action.Message;
import com.example.mathias.texta.util.graph.Node;
import com.example.mathias.texta.util.graph.Transition;

import java.util.LinkedList;

/**
 * Created by Mathias on 26.01.2017.
 */

public class Level01 extends Level {
    private boolean running;
    private int numberOfInputs = 0;
    private String username = "";
    private Node startNode;
    private Node node_1_0;
    private Node node_1_1;
    private Node node_1_2;
    private Node node_1_3;
    private Node node_1_4;
    private Node node_1_5;
    private Node node_1_6;
    private Node node_1_7;

    public Level01(GameThread gameThread) {
        init();
        running = true;
        setGameThread(gameThread);
        setCurrentNode(startNode);
    }

    @Override
    public void run() {
        while (running) {
            if (currentNode.needsTextInput()) {
                String input = "-1";
                switch (currentNode.getRequestedInput()) {
                    case LAST_INPUT:
                        numberOfInputs++;
                        switch (numberOfInputs) {
                            case 1:
                                username = gameThread.getLastUserinput();
                                input = username;
                                break;
                        }
                    case CORRECT_LAST:
                        switch (numberOfInputs) {
                            case 1:
                                username = gameThread.getLastUserinput();
                                input = username;
                                break;
                        }
                    case USERNAME:
                        input = username;
                        break;
                }
                LinkedList<Action> temp = currentNode.getActions();
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i) instanceof EmptyAction) {
                        EmptyAction temporalAction = (EmptyAction) temp.get(i);
                        String message = temporalAction.getFront() + input + temporalAction.getEnd();
                        temp.set(i, new Message(message));
                        break;
                    }

                }
            }

            while (getCurrentNode().hasMessages())
                gameThread.queueAction(this.getCurrentNode().getAction());
        }
    }

    public void init() {
        startNode = new Node();
        startNode.enableUserTextInput();
        node_1_0 = new Node();
        node_1_0.enableUserTextInput();
        startNode.addSuccessor(Transition.TEXT, node_1_0);
        node_1_1 = new Node();
        node_1_1.enableUserTextInput();
        node_1_0.addSuccessor(Transition.TEXT, node_1_1);
        node_1_2 = new Node();
        node_1_2.disableUserTextInput();
        node_1_1.addSuccessor(Transition.TEXT, node_1_2);
        node_1_2.addAction("HALLO??", 1200);
        node_1_2.addAction("IST DA JEMAND????", 3000);
        node_1_3 = new Node();
        node_1_3.disableUserTextInput();
        node_1_3.addAction("Gott sei dank!", 500);
        node_1_3.addAction("Alles was ich bisher erreichen konnte, waren Toaster und \"intelligente\" Lampen!", 1500);
        node_1_3.addAction("Egal. Wir haben nicht viel Zeit! Ich brauche dringend deine Hilfe! Ich bin in großer Gefahr! Die Verbindung kann jederzeit abbrechen!");
        node_1_3.addAction("Bitte!");
        node_1_3.addAction("Du musst mir helfen!");
        node_1_2.addSuccessor(Transition.AFFIRMATIVE, node_1_3);
        node_1_5 = new Node();
        node_1_5.addAction("Sehr gut! Wie ist dein Name? Ich heiße Josh!");
        node_1_5.enableUserTextInput();
        node_1_3.addSuccessor(Transition.AFFIRMATIVE, node_1_5);
        node_1_6 = new Node();
        node_1_6.disableUserTextInput();
        node_1_6.setNeedsTextInput(true, RequestedInput.LAST_INPUT); // 1 Username
        node_1_6.addAction(new EmptyAction("", "?"));
        node_1_5.addSuccessor(Transition.TEXT, node_1_6);
        node_1_7 = new Node();
        node_1_7.setNeedsTextInput(true, RequestedInput.USERNAME);
        node_1_7.addAction(new EmptyAction("Okay, ", "!"));
        node_1_6.addSuccessor(Transition.AFFIRMATIVE, node_1_7);
        node_1_4 = new Node();
        node_1_4.disableUserTextInput();
        node_1_2.addSuccessor(Transition.NEGATIVE, node_1_4);
        node_1_4.addAction("Sehr witzig!");
    }

    public void handleTransition(Transition transition) {
        Node nextNode = getCurrentNode().getNext(transition);
        if (nextNode == null) {
            gameThread.queueAction(new Message("Errr was?"));
        } else {
            setCurrentNode(nextNode);
        }

    }

    public void kill() {
        this.running = false;
    }
}
