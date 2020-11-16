package com.prog.world.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class HealthBar extends Bar implements UIElement
{
    //lo dichiaro una volta sola e lo uso nel codice
    private final Texture tex;
    
    public HealthBar(float x,float y,float width,float height,String path)
    {
        pos.set(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
    }
    
    @Override
    public void draw()
    {
        float playerHP=Player.getHP();
        
        if(playerHP > 0.26f && playerHP < 0.7f)
            batch.setColor(0.89f,0.76f,0.25f,1f);
        else if(playerHP <= 0.26f)
            batch.setColor(0.67f,0.19f,0.19f,1f);
        else
            batch.setColor(0f,0.76f,0.45f,1f);

        batch.draw(tex,pos.x,pos.y,pos.width*playerHP,pos.height);

        batch.setColor(Color.WHITE);
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() 
    {
        tex.dispose();
    }
    
    
}
