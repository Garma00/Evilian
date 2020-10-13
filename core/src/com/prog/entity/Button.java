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
    public Button(float x, float y, float width, float height, String userData, String path, boolean isDouble)
    {
        isActive = true;
        this.pos=new Rectangle(x,y,width/Evilian.PPM,height/Evilian.PPM);
        body = createBody(pos.x,pos.y, width, height, 0, userData, 0, 0, 0);
        this.img = new Texture (path);
        if(isDouble)
        {
            this.img_off = new Texture(path.replace(".png", "_off.png"));
        }
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
        //da fare conversione in tutti i menu
        //da implementare larghezza altezza e posizione sul pos
        if(isActive)
            batch.draw(img, pos.x, pos.y, pos.width, pos.height);
        else
            batch.draw(img_off, pos.x, pos.y, pos.width, pos.height);
    }

    @Override
    public void dispose() {
    }
    
}
