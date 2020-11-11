package com.prog.world.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class HealthShade extends UIElement
{
    private final Rectangle r;
    
    public HealthShade(float x,float y,float width,float height,String path,UI.ElementType type)
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
            batch.setColor(0.58f,0.47f,0f,1f);
        else if(playerHP <= 0.26f)
            batch.setColor(0.48f,0.14f,0.14f,1f);
        else
            batch.setColor(0.19f,0.5f,0.37f,1f);

        batch.draw(tex,r.x,r.y,r.width*playerHP,r.height);

        batch.setColor(Color.WHITE);
    }

    @Override
    public void update() {
    }
}
