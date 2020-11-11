package com.prog.world.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.entity.magia.Cura;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class HBar extends UIElement
{
    private final Rectangle r;
    private float cd;
    
    public HBar(float x,float y,float width,float height,String path,UI.ElementType type)
    {
        super();
        r=getPos();
        
        setPos(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
        this.type=type;
        cd=1;
    }
    
    @Override
    public void draw()
    {
        batch.setColor(0f,0.43f,1f,1f);
        cd=map(clamp(sp.getTime()-sp.getLastLaunch()[2],0,Cura.getCD()),0,Cura.getCD(),1,0);
        batch.draw(tex,r.x,r.y,r.width*cd,r.height);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void update() {
    }
}
