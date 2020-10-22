
package com.prog.world.AI;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;

//connessione da un nodo a un altro
public class NodeConnection implements Connection<Node> {
  Node fromNode;
  Node toNode;
  float cost;

  public NodeConnection(Node fromNode, Node toNode){
    this.fromNode = fromNode;
    this.toNode = toNode;
    cost = Vector2.dst(fromNode.x, fromNode.y, toNode.x, toNode.y);
  }

  @Override
  public float getCost() {
    return cost;
  }

  @Override
  public Node getFromNode() {
    return fromNode;
  }

  @Override
  public Node getToNode() {
    return toNode;
  }
}
