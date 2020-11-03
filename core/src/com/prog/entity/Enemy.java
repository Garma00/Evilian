package com.prog.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Enemy extends Entity implements Poolable{
    float life;
    //public perche' dovra' essere accessibile dal level contact listener
    public boolean alive;
    //da assegnare solo al leader dopo l'impl del formation motion
    
    
    public abstract void init();
    public abstract void init(Vector2 loadingPosition, float hp);
    public void damage(float dmg)
    {
        life -= dmg;
        if(life <= 0)
            alive = false;
        System.out.println(this + " vita = " + life);
    }

    public void salvStato(FileWriter wr) throws IOException
    {
        /*posizione, vita, velocità, id*/
        
        String toWrite = "E " + pos.x + " " + pos.y + " " + life + "\n";
        
        System.out.println(toWrite);
        wr.write(toWrite);
        
    }
   
}
