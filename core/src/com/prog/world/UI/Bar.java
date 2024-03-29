package com.prog.world.UI;

import com.badlogic.gdx.math.Rectangle;
import com.prog.entity.SpellFactory;

public class Bar
{
    private final Rectangle pos;
    private final static SpellFactory sp=SpellFactory.getInstance();
    
    protected Bar()
    {
        this.pos=new Rectangle();
    }
    
    public float map(float value,float istart,float iend,float ostart,float oend)
    {
        return ostart + (oend - ostart) * ((value - istart) / (iend - istart));
    }
    
    public float clamp(float v,float min,float max)
    {
        if(v<min)
            v=min;
        else if(v>max)
            v=max;
        
        return v;  
    }
    
    protected Rectangle getPos()    {return this.pos;}
    protected SpellFactory getSP()  {return this.sp;}
}
