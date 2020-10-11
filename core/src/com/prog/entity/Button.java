package com.prog.entity;

import com.badlogic.gdx.graphics.Texture;
import com.prog.collision.OpzioniContactListener;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.evilian.Evilian.MANAGER_MUSIC;

public class Button extends Entity 
{
    Texture img;
    Texture img_off;
    OpzioniContactListener c;
    public boolean isActive;
    public Button(float x, float y, float width, float height, String userData, String path, boolean isDouble)
    {
        isActive = true;
        body = createBody(x, y, width, height, 0, userData, 0, 0, 0);
        this.img = new Texture (path);
        if(isDouble)
        {
            this.img_off = new Texture(path.replace(".png", "_off.png"));
        }
    }
    
    
    
    @Override
    public void update(float delta) {
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
            batch.draw(img, body.getWorldCenter().x * Evilian.PPM - 75, body.getWorldCenter().y * Evilian.PPM - 25);
        else
            batch.draw(img_off, body.getWorldCenter().x * Evilian.PPM - 75, body.getWorldCenter().y * Evilian.PPM - 25);
    }

    @Override
    public void dispose() {
    }
    
}
