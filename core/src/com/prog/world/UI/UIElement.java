package com.prog.world.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.entity.magia.SpellFactory;
import com.prog.world.UI.UI.ElementType;

abstract class UIElement
{
    private Rectangle pos;
    Texture tex;
    ElementType type;
    SpellFactory sp=SpellFactory.getInstance();
    
    public UIElement()
    {
        this.pos=new Rectangle();
    }

    public abstract void draw();

    public abstract void update();

    public void dispose() 
    {
        tex.dispose();
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
    
    public Rectangle getPos()
    {
        return pos;
    }
    
    public void setPos(float x, float y, float width, float height)
    {
        pos.set(x, y, width, height);
    }
    
    public void setPosX(float x)
    {
        pos.x=x;
    }
    
    public void setPosY(float y)
    {
        pos.y=y;
    }
    
    public void setPosWidth(float w)
    {
        pos.width=w;
    }
    
    public void setPosHeight(float h)
    {
        pos.height=h;
    }
    
    public void setPos(float x,float y)
    {
        pos.x=x;
        pos.y=y;
    }
}
