package com.example.mathias.texta.util;

import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.TextView;

import com.example.mathias.texta.MainActivity;
import com.example.mathias.texta.game.GameThread;

import java.util.LinkedList;

import static com.example.mathias.texta.R.id.ownMessage;


/**
 * Created by Mathias on 22.01.2017.
 */

public class MessageThread extends Thread {
    private TextView tvConsole;
    private MainActivity mainActivity;
    private GameThread gameThread;
    private String message = "";
    private boolean running = true, gameThreadNotified = true;

    public MessageThread(MainActivity mainActivity, GameThread gameThread, TextView textView) {
        this.mainActivity = mainActivity;
        this.tvConsole = textView;
        this.gameThread = gameThread;
    }

    public void run() {
        while (running) {
            if (message.length() > 0) {
                gameThreadNotified = false;
                mainActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        String temp = tvConsole.getText().toString();
                        String newMessage = "";

                        if (temp.length() > 0) {
                            if (message.length() == 1) {
                                if (temp.contains("\u2588"))
                                    newMessage += temp.substring(0, temp.length() - 1) + message.charAt(0);
                                else
                                    newMessage += temp.substring(0, temp.length()) + message.charAt(0);
                            } else {
                                if (temp.contains("\u2588"))
                                    newMessage += temp.substring(0, temp.length() - 1) + message.charAt(0) + "\u2588";
                                else
                                    newMessage += temp.substring(0, temp.length()) + message.charAt(0) + "\u2588";
                            }
                        } else {
                            newMessage = message.charAt(0) + "\u2588";
                        }

                        message = message.substring(1);
                        tvConsole.setText(newMessage);
                    }
                });

                try {
                    sleep(105);
                } catch (InterruptedException e) {
                }

            } else {
                if (!gameThreadNotified) {
                    gameThread.messageDisplayed();
                    gameThreadNotified = true;
                }
                mainActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        String temp = tvConsole.getText().toString();

                        tvConsole.setText(temp.replace("\u2588", ""));
                    }
                });

                try {
                    sleep(750);
                } catch (InterruptedException e) {
                }

                mainActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        tvConsole.append("\u2588");
                    }
                });

                try {
                    sleep(750);
                } catch (InterruptedException e) {
                }
            }

        }
    }

    /***
     * @param msg      String der Nachricht
     * @param incoming true, falls incoming, false, falls outgoing
     */

    public void displayMessage(String msg, Boolean incoming) {
        String string = tvConsole.getText().toString();
        string = string.replace("\u2588", "");

        if (string.length() > 0) {
            if (!incoming)
                displayMessage("\n<" + msg);
            else
                displayMessage("\n>" + msg);
        } else {
            if (!incoming)
                displayMessage("<" + msg);
            else
                displayMessage(">" + msg);
        }
    }

    public void displayMessage(String message) {
        this.message = message;
    }

    public void kill() {
        this.running = false;
    }

}
