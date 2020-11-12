package com.prog.world.UI;

import com.badlogic.gdx.math.Rectangle;

public class Bar
{
    final Rectangle pos=new Rectangle();
    
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
}
