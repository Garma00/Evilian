package com.prog.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.prog.world.Livello;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Enemy extends Entity implements Poolable{
    private float life;
    //public perche' dovra' essere accessibile dal level contact listener
    private boolean alive;
    private boolean walkLeft;
    private float SPEED;
    
    abstract void init(Vector2 loadingPosition, float hp);
    
    public void damage(float dmg)
    {
        life -= dmg;
        if(life <= 0)
        {
            //assegno 20 punti ogni volta che muore un nemico 
            Livello.addPoints(20);
            alive = false;
        }
    }

    public abstract void salvStato(FileWriter wr) throws IOException;
    
    public void setWalkLeft(boolean f)  {walkLeft=f;}
    protected boolean getWalk()         {return walkLeft;}
    protected boolean isAlive()         {return alive;}
    protected void setAlive(boolean f)  {alive=f;}
    protected float getSpeed()          {return SPEED;}
    public void setSpeed(float f)       {this.SPEED=f;}
    protected void setLife(float f)     {this.life=f;}
    protected float getLife()           {return this.life;}
}
