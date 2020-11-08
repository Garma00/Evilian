package com.prog.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import static com.prog.world.Livello.world;

public class Mouse extends Entity
{
    OrthographicCamera cam;
    Vector3 realPos;
    public boolean toReposition;
    public static Mouse INSTANCE=null;
    
    public Mouse()
    {
        toReposition=false;
        //se il mouse non e' di tipo dinamico non vengono rilevate le collisioni coi bottoni
    }
    
    public void addCamToMouse(OrthographicCamera cam)
    {
        this.cam=cam;
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
        if(toReposition)
            if(!world.isLocked())
            {
                body.setTransform(0, 0, 0);
                toReposition=false;
            }
    }

    @Override
    public void handleInput() 
    {
        Vector3 pos;
        if(Gdx.input.justTouched())
        {
            pos = fixedPosition(Gdx.input.getX(), Gdx.input.getY(), cam);
            this.body.setTransform(pos.x, pos.y, 0);
            //System.out.println(pos.x + "\t" + pos.y);
        }
    }

    @Override
    public void draw() {
    }

    @Override
    public void dispose() {
    }
    
    public OrthographicCamera getCam()
    {
        return this.cam;
    }
    
    public static Mouse getInstance()
    {
        if(INSTANCE == null)
            INSTANCE=new Mouse();
        
        return INSTANCE;
    }
    
    public void addToWorld()
    {
        super.createBody(0, 0, 16, 16, 1, "mouse", 0,  0, 0,(short)2,(short)1);
    }
    
}
