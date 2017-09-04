package com.example.mathias.texta.game.levels;

import com.example.mathias.texta.game.GameThread;
import com.example.mathias.texta.util.graph.Node;
import com.example.mathias.texta.util.graph.Transition;

/**
 * Created by Mathias on 26.01.2017.
 */

public abstract class Level extends Thread{
    protected Node currentNode;
    protected GameThread gameThread;

    public Level(GameThread gameThread, Node start){
        this.gameThread = gameThread;
        this.currentNode = start;
    }

    public Level(){};

    public void setCurrentNode(Node node){
        this.currentNode = node;
    }

    public Node getCurrentNode(){
        return currentNode;
    }

    public void setGameThread(GameThread gameThread){
        this.gameThread = gameThread;
    }
    public abstract void init();
    public abstract void run();
    public abstract void kill();
    public abstract void handleTransition(Transition transition);
}
