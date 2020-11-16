package com.prog.world.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.prog.entity.PallaDiFuoco;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class FBBar extends Bar implements UIElement
{
    private float cd;
    private Texture tex;
    
    public FBBar(float x,float y,float width,float height,String path)
    {
        pos.set(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
        cd=1;
    }
    
    @Override
    public void draw()
    {
        batch.setColor(0f,0.43f,1f,1f);
        cd=map(clamp(sp.getTime()-sp.getLastLaunch()[0],0,PallaDiFuoco.getCD()),0,PallaDiFuoco.getCD(),1,0);
        batch.draw(tex,pos.x,pos.y,pos.width*cd,pos.height);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
        
    }
}
