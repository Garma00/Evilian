package com.prog.entity;

import com.badlogic.gdx.graphics.Texture;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class Button extends Entity 
{
    Texture img;
    public Button(float x, float y, float width, float height, String userData, String img)
    {
        body = createBody(x, y, width, height, 0, userData, 0, 0, 0);
        this.img = new Texture (img);
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
        batch.draw(img, body.getWorldCenter().x * Evilian.PPM - 75, body.getWorldCenter().y * Evilian.PPM - 25);
    }

    @Override
    public void dispose() {
    }
    
}
