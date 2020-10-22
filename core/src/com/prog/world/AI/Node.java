package com.prog.world.AI;

public class Node {
  float x;
  float y;
  public String name;

  //Nodo generico. uso un index perche' l'ai usera' l'algoritmo A* indexed che richiede che i nodi siano indicizzati
  int index;

  public Node(float x, float y,String name){
    this.x = x;
    this.y = y;
    this.name=name;
  }

  public void setIndex(int index){
    this.index = index;
  }
  
}
