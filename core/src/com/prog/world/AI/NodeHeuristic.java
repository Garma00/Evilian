package com.prog.world.AI;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

//funzione heuristic(n) che stima la distanza dal nodo corrente al nodo target
//Vector2.dst torna un float che rappresenta la distanza del vettore distanza
public class NodeHeuristic  implements Heuristic<Node> {

  @Override
  public float estimate(Node currentNode, Node goalNode) {
    return Vector2.dst(currentNode.x, currentNode.y, goalNode.x, goalNode.y);
  }
}
