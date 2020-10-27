package com.prog.entity;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Queue;
import com.prog.world.AI.Node;
import com.prog.world.AI.NodeGraph;
import com.prog.world.Livello1;

public abstract class Enemy extends Entity implements Poolable{
    float life;
    //public perche' dovra' essere accessibile dal level contact listener
    public boolean alive;
    //da assegnare solo al leader dopo l'impl del formation motion
    NodeGraph mapGraph=Livello1.graph;
    Queue<Node> pathQueue = new Queue<>();
    GraphPath<Node> pathToEnemy;
    long time,lastCall;
    
    
    public abstract void init();
}
