package com.prog.world.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class BackGround implements UIElement 
{
    private final Texture tex;
    final Rectangle pos=new Rectangle();
    
    public BackGround(float x,float y,float width,float height,String path)
    {
        pos.set(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
    }

    @Override
    public void draw() 
    {
        batch.draw(tex,pos.x,pos.y,pos.width,pos.height);
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
        tex.dispose();
    }
    
    
}
