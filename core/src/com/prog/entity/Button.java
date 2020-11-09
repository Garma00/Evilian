package com.prog.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.collision.OpzioniContactListener;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class Button extends Entity 
{
    Texture img;
    Texture img_off;
    OpzioniContactListener c;
    public boolean isActive;
    private boolean hasDouble;
    public Button(float x, float y, float width, float height, String userData, String path, boolean isDouble)
    {
        this.pos=new Rectangle(x,y,width/Evilian.PPM,height/Evilian.PPM);
        createBody(pos.x,pos.y, width, height, 0, userData, 0, 0, 0,(short)1,(short)2);
        this.img = new Texture (path);
        
        switch(((userDataContainer)this.body.getFixtureList().get(0).getUserData()).type)
        {
            case "sound":
                isActive=Evilian.getManagerSound().getVolume() == 0?false:true;
                break;
            case "music":
                isActive=Evilian.getManagerMusic().getVolume() == 0?false:true;
                break;
        }
        
        this.hasDouble=isDouble;
        if(isDouble)
            this.img_off = new Texture(path.replace(".png", "_off.png"));
    }
    
    
    
    @Override
    public void update(float delta) {
        pos.x=body.getWorldCenter().x - (pos.width/2);
        pos.y=body.getWorldCenter().y - (pos.height/2);
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void draw()
    {
        if(hasDouble)
            batch.draw(isActive==true?img:img_off, pos.x, pos.y, pos.width, pos.height);
        else
            batch.draw(img, pos.x, pos.y, pos.width, pos.height);
    }

    @Override
    public void dispose() {
    }
    
}
