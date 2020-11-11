package com.prog.world.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import com.prog.world.UI.UI.ElementType;

public class HealthBar extends UIElement
{
    //lo dichiaro una volta sola e lo uso nel codice
    private final Rectangle r;
    
    public HealthBar(float x,float y,float width,float height,String path,ElementType type)
    {
        super();
        r=getPos();
        
        setPos(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
        this.type=type;
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

        batch.draw(tex,r.x,r.y,r.width*playerHP,r.height);

        batch.setColor(Color.WHITE);
    }

    @Override
    public void update() {
    }
    
    
}
