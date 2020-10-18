package com.prog.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

class UIElement 
{
    public Animation<TextureAtlas.AtlasRegion> anim;
    public Rectangle pos;
    //elementi della ui posson oavere animazioni
    public float animationTime;
    public boolean flipX;
    public boolean flipY;
    Texture tex;
    
    public UIElement(float x,float y,float width,float height,String path)
    {
        
        this.pos=new Rectangle(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
    }
    
    public void draw()
    {
        //da aggiungere le animazioni, punto di rotazione, flip e quant'altro
        batch.draw(tex,pos.x,pos.y,pos.width,pos.height);
    }
}
