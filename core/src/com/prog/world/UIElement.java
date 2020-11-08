package com.prog.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.prog.entity.Player;
import com.prog.entity.magia.Cura;
import com.prog.entity.magia.Meteora;
import com.prog.entity.magia.PallaDiFuoco;
import com.prog.entity.magia.PallaDiGhiaccio;
import com.prog.entity.magia.SpellFactory;
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
    float cd;
    float original_pos;
    SpellFactory sp=SpellFactory.INSTANCE;
    
    
    
    public UIElement(float x,float y,float width,float height,String path,ElementType type)
    {
        
        this.pos=new Rectangle(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
        this.type=type;
        cd=1;
        this.original_pos=this.pos.x;
        
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
            case FB_BAR:
                batch.setColor(0f,0.43f,1f,1f);
                cd=map(clamp(sp.getTime()-sp.getLastLaunch()[0],0,PallaDiFuoco.UI_CD),0,PallaDiFuoco.UI_CD,1,0);
                batch.draw(tex,pos.x,pos.y,pos.width*cd,pos.height);
                batch.setColor(Color.WHITE);
                break;
            case IB_BAR:
                batch.setColor(0f,0.43f,1f,1f);
                cd=map(clamp(sp.getTime()-sp.getLastLaunch()[1],0,PallaDiGhiaccio.UI_CD),0,PallaDiGhiaccio.UI_CD,1,0);
                batch.draw(tex,pos.x,pos.y,pos.width*cd,pos.height);
                batch.setColor(Color.WHITE);
                break;
            case H_BAR:
                batch.setColor(0f,0.43f,1f,1f);
                cd=map(clamp(sp.getTime()-sp.getLastLaunch()[2],0,Cura.UI_CD),0,Cura.UI_CD,1,0);
                batch.draw(tex,pos.x,pos.y,pos.width*cd,pos.height);
                batch.setColor(Color.WHITE);
                break;
            case M_BAR:
                batch.setColor(0f,0.43f,1f,1f);
                cd=map(clamp(sp.getTime()-sp.getLastLaunch()[3],0,Meteora.UI_CD),0,Meteora.UI_CD,1,0);
                batch.draw(tex,pos.x,pos.y,pos.width*cd,pos.height);
                batch.setColor(Color.WHITE);
                break;
                
            default:
                //da aggiungere le animazioni, punto di rotazione, flip e quant'altro
                batch.draw(tex,pos.x,pos.y,pos.width,pos.height);
        }
            
    }
    
    

    public void update() 
    {
        if(type==ElementType.SELECTOR)
        {
            if(sp.getSelectorPressed())
            {
                if(sp.getSpellSelector() == 0)
                    this.pos.x=original_pos;
                else
                    //1m vale 100px
                    this.pos.x+=1;
                sp.setSelectorPressed(false);
            }
        }
    }

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
}
