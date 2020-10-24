package com.prog.world.AI;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class NodeGraph implements IndexedGraph<Node>{

    NodeHeuristic nodeHeuristic = new NodeHeuristic();
    public Array<Node> nodeArray = new Array<>();
    Array<NodeConnection> connessioni = new Array<>();

    // mappe di nodi partendo dal punto iniziale
    ObjectMap<Node, Array<Connection<Node>>> connectionMap = new ObjectMap<>();

    private int lastNodeIndex = 0;

    //aggiunge il nodo al grafo
    public void addNode(Node nodo){
      nodo.index = lastNodeIndex;
      lastNodeIndex++;

      nodeArray.add(nodo);
    }

    //crea un collegamento che va da un nodo a un altro
    public void connectNode(Node fromNode, Node toNode){
      NodeConnection collegamento = new NodeConnection(fromNode, toNode);
      if(!connectionMap.containsKey(fromNode)){
        connectionMap.put(fromNode, new Array<Connection<Node>>());
      }
      connectionMap.get(fromNode).add(collegamento);
      connessioni.add(collegamento);
    }

    //calcola il path da un nodo start ad un nodo goal e popola resultPath con il risultato
    public GraphPath<Node> findPath(Node startNode, Node goalNode){
      GraphPath<Node> resultPath = new DefaultGraphPath<>();
      new IndexedAStarPathFinder<>(this).searchNodePath(startNode, goalNode, nodeHeuristic, resultPath);
      return resultPath;
    }

    @Override
    public int getIndex(Node node) {
      return node.index;
    }

    @Override
    public int getNodeCount() {
      return lastNodeIndex;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
      if(connectionMap.containsKey(fromNode)){
        return connectionMap.get(fromNode);
      }

      return new Array<>(0);
    }
    
}
