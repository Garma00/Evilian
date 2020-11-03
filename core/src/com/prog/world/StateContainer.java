package com.prog.world;

import com.badlogic.gdx.math.Vector2;


public class StateContainer
{
    
    float hp;
    Vector2 pos;
    String type;
    
    public StateContainer(Vector2 pos, float hp)
    {
        this.pos = pos;
        this.hp = hp;
    }
    
    public StateContainer(Vector2 pos, float hp, String type)
    {
        this.pos = pos;
        this.hp = hp;
        this.type = type;
    }
    
    public float getHp()
    {
        return this.hp;
    }

    public Vector2 getPos()
    {
        return this.pos;
    }
    
    public String getType()
    {
        return this.type;
    }


}
