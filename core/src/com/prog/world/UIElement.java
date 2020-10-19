package com.prog.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import com.prog.world.UI.ElementType;

class UIElement 
{
    public Animation<TextureAtlas.AtlasRegion> anim;
    public Rectangle pos;
    //elementi della ui posson oavere animazioni
    public float animationTime;
    public boolean flipX;
    public boolean flipY;
    Texture tex;
    ElementType type;
    
    public UIElement(float x,float y,float width,float height,String path,ElementType type)
    {
        
        this.pos=new Rectangle(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
        this.type=type;
    }
    
    public void draw()
    {
        switch(type)
        {
            case HEALTH_BAR:
                if(Player.hp > 0.26f && Player.hp < 0.7f)
                    batch.setColor(0.89f,0.76f,0.25f,1f);
                else if(Player.hp <= 0.26f)
                    batch.setColor(0.67f,0.19f,0.19f,1f);
                else
                    batch.setColor(0f,0.76f,0.45f,1f);

                batch.draw(tex,pos.x,pos.y,pos.width*Player.hp,pos.height);

                batch.setColor(Color.WHITE);
                break;
            case HEALTH_SHADE:
                if(Player.hp > 0.26f && Player.hp < 0.7f)
                    batch.setColor(0.58f,0.47f,0f,1f);
                else if(Player.hp <= 0.26f)
                    batch.setColor(0.48f,0.14f,0.14f,1f);
                else
                    batch.setColor(0.19f,0.5f,0.37f,1f);

                batch.draw(tex,pos.x,pos.y,pos.width*Player.hp,pos.height);

                batch.setColor(Color.WHITE);
                break;
            default:
                //da aggiungere le animazioni, punto di rotazione, flip e quant'altro
                batch.draw(tex,pos.x,pos.y,pos.width,pos.height);
        }
            
    }

    public void update() 
    {
        
    }

    public void dispose() 
    {
        tex.dispose();
    }
}
