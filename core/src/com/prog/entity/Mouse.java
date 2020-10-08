package com.prog.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class Mouse extends Entity
{
    OrthographicCamera cam;
    Vector3 realPos;
    public Mouse(OrthographicCamera cam)
    {
        this.cam = cam;
        realPos = fixedPosition(Gdx.input.getX(), Gdx.input.getY(), cam);
        this.body = createBody(realPos.x, realPos.y, 16, 16, 1, "mouse", 0,  0, 0);
        
    }
    
    public Vector3 fixedPosition(float x, float y, OrthographicCamera cam)
    {
        Vector3 mousePos = new Vector3();
        mousePos.x = x;
        mousePos.y = y;
        return cam.unproject(mousePos);
        
     
    }
    
    @Override
    public void update(float delta) {
    }

    @Override
    public void handleInput() 
    {
        Vector3 pos;
        if(Gdx.input.justTouched())
        {
            pos = fixedPosition(Gdx.input.getX(), Gdx.input.getY(), cam);
            this.body.setTransform(pos.x, pos.y, 0);
            System.out.println(pos.x + "\t" + pos.y);
        }
    }

    @Override
    public void draw() {
    }

    @Override
    public void dispose() {
    }
    
}
