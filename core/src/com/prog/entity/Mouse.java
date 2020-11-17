package com.prog.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.prog.world.Livello;

public class Mouse extends Entity
{
    private OrthographicCamera cam;
    private boolean toReposition;
    private static Mouse INSTANCE=null;
    
    private Mouse()
    {
        toReposition=false;
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
            if(!Livello.getWorld().isLocked())
            {
                getBody().setTransform(0, 0, 0);
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
            getBody().setTransform(pos.x, pos.y, 0);
            //System.out.println(pos.x + "\t" + pos.y);
        }
    }

    @Override
    public void draw() {
    }

    @Override
    public void dispose() {
    }
    
    
    public static Mouse getInstance()
    {
        if(INSTANCE == null)
            INSTANCE=new Mouse();
        
        return INSTANCE;
    }
    
    public void addToWorld()                {super.createBody(0, 0, 16, 16, 1, "mouse", 0,  0, 0,(short)2,(short)1);}
    public void setToReposition(boolean f)  {toReposition=f;}
    protected OrthographicCamera getCam()   {return this.cam;}

    
}
