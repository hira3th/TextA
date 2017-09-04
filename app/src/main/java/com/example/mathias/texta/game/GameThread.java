package com.example.mathias.texta.game;

import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;
import android.widget.TextView;

import com.example.mathias.texta.MainActivity;
import com.example.mathias.texta.R;
import com.example.mathias.texta.game.levels.Level;
import com.example.mathias.texta.game.levels.level01.Level01;
import com.example.mathias.texta.util.action.Action;
import com.example.mathias.texta.util.action.Message;
import com.example.mathias.texta.util.MessageThread;
import com.example.mathias.texta.util.action.TransitionMessage;
import com.example.mathias.texta.util.graph.Transition;

import java.util.LinkedList;


/**
 * Created by Mathias on 24.01.2017.
 */

public class GameThread extends Thread {
    private MainActivity mainActivity;
    private MessageThread console;
    private volatile boolean running = true;
    private LinkedList<Action> incActions;
    private AppCompatEditText userInputField;
    private Button btnSend;
    private boolean consoleIsWorking;
    private Level level;
    private Message currentMessage;
    private String lastUserinput;

    public GameThread(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        console = new MessageThread(mainActivity, this, ((TextView) mainActivity.findViewById(R.id.tvMessage)));
        console.start();
        userInputField = (AppCompatEditText) mainActivity.findViewById(R.id.ownMessage);
        btnSend = (Button) mainActivity.findViewById(R.id.btnSend);
        incActions = new LinkedList<>();
        level = new Level01(this);
        level.start();
    }

    public void run() {
        while (running) {
            try {
                sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (incActions.isEmpty()) continue;

            Action action = incActions.poll();

            switch (action.getType()) {
                case MESSAGE:
                    displayMessage((Message) action);
                    break;
                case TRANSITION:
                    handleTransition(((TransitionMessage) action).getTransition());
                    break;
                case ENABLE_TEXT_INPUT:
                    enableUserTextInput();
                    break;
                case DISABLE_TEXT_INPUT:
                    disableUserTextInput();
                    break;
            }
        }
    }

    public void queueAction(Action action) {
        incActions.offer(action);
    }

    public void messageDisplayed() {
        synchronized (this) {
            this.consoleIsWorking = false;
            notifyAll();
        }

        try {
            sleep(currentMessage.getWaitingTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void displayMessage(Message message) {
        currentMessage = message;
        if (!message.isIncoming()) this.lastUserinput = message.getText();

        console.displayMessage(message.getText(), message.isIncoming());
        consoleIsWorking = true;
        synchronized (this) {
            while (consoleIsWorking) {
                try {
                    wait();
                } catch (Exception e) {
                }
            }
        }
    }

    public void kill() {
        this.running = false;
    }

    public void disableUserTextInput() {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                userInputField.setEnabled(false);
                userInputField.setClickable(false);
                btnSend.setEnabled(false);
                btnSend.setClickable(false);
            }
        });
    }

    public void enableUserTextInput() {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                userInputField.setEnabled(true);
                userInputField.setClickable(true);
                btnSend.setEnabled(true);
                btnSend.setClickable(true);
            }
        });
    }

    public void handleTransition(Transition transition) {
        level.handleTransition(transition);
    }

    public String getLastUserinput(){
        return lastUserinput;
    }
}
