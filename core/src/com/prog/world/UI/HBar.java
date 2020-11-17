package com.prog.world.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.entity.Cura;
import com.prog.entity.SpellFactory;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class HBar extends Bar implements UIElement
{
    private final Texture tex;
    private float cd;
    private final SpellFactory sp;
    private final Rectangle r;
    
    public HBar(float x,float y,float width,float height,String path)
    {
        r=getPos();
        r.set(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
        cd=1;
        sp=getSP();
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

    @Override
    public void dispose() {
        tex.dispose();
    }
}
