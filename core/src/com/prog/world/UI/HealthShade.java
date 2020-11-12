package com.prog.world.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class HealthShade implements UIElement
{
    private Texture tex;
    final Rectangle pos=new Rectangle();
    
    public HealthShade(float x,float y,float width,float height,String path)
    {
        pos.set(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
    }
    
    @Override
    public void draw()
    {
        float playerHP=Player.getHP();
        
        if(playerHP > 0.26f && playerHP < 0.7f)
            batch.setColor(0.58f,0.47f,0f,1f);
        else if(playerHP <= 0.26f)
            batch.setColor(0.48f,0.14f,0.14f,1f);
        else
            batch.setColor(0.19f,0.5f,0.37f,1f);

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
