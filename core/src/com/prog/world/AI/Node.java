package com.prog.world.AI;

public class Node {
    public enum NodeType
    {
        FLOOR,
        CORNER,
        MIDDLE
    }
    public float x;
    public float y;
    public NodeType type;


    //Nodo generico. uso un index perche' l'ai usera' l'algoritmo A* indexed che richiede che i nodi siano indicizzati
    int index;

    public Node(float x, float y,NodeType t){
      this.x = x;
      this.y = y;
      this.type=t;
    }

    public void setIndex(int index){
      this.index = index;
    }
  
}
