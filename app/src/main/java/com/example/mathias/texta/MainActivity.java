package com.example.mathias.texta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.example.mathias.texta.game.GameThread;
import com.example.mathias.texta.util.action.Message;
import com.example.mathias.texta.util.action.TransitionMessage;
import com.example.mathias.texta.util.graph.Transition;

import static com.example.mathias.texta.R.id.ownMessage;

public class MainActivity extends AppCompatActivity {
    GameThread gameThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameThread = new GameThread(this);
        gameThread.start();
    }

    public void displayMessage(View view){
        AppCompatEditText temp = (AppCompatEditText) findViewById(ownMessage);
        String message = temp.getText().toString();
        temp.setText("");
        gameThread.queueAction(new Message(message, false));
        gameThread.queueAction(new TransitionMessage(Transition.TEXT));
    }

    public void btnAffirmativeClicked(View view){
        gameThread.queueAction(new TransitionMessage(Transition.AFFIRMATIVE));
    }

    public void btnNegativeClicked(View view){
        gameThread.queueAction(new TransitionMessage(Transition.NEGATIVE));
    }
}
