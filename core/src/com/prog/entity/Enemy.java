package com.prog.entity;

import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class Enemy extends Entity implements Poolable{
    float life;
    //public perche' dovra' essere accessibile dal level contact listener
    public boolean alive;
    //da assegnare solo al leader dopo l'impl del formation motion
    
    
    public abstract void init();
    public void damage(float dmg)
    {
        life -= dmg;
        if(life <= 0)
            alive = false;
        System.out.println(this + " vita = " + life);
    }

    public void save state()
    {
        /*posizione, vita, velocità, id*/
    }
}
