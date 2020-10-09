package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import static com.prog.world.ManagerScreen.index;

public class MenuContactListener implements ContactListener{
    //il contact listener si setta tramite world.setContactListener(istanza di questa classe)
    public boolean collided = false; 
    //chiamato quando due fixture cominciano a collidere
    @Override
    public void beginContact(Contact c) {
        //System.out.println("rilevato contatto tra "+c.getFixtureA().getUserData()+" e "+c.getFixtureB().getUserData());
        if(c.getFixtureA().getUserData() == "mouse")
        {
            if(c.getFixtureB().getUserData() == "gioca")
            {
                //System.out.println("Gioca");
                collided = true;
                index = 1;
                
            }
                
        }
        
        if(c.getFixtureB().getUserData() == "mouse")
        {
            if(c.getFixtureA().getUserData() == "gioca")
            {
                //System.out.println("Gioca");
                collided = true;
                index = 1;
            }
                
        }
        
        if(c.getFixtureA().getUserData() == "mouse")
        {
            if(c.getFixtureB().getUserData() == "opzioni")
            {
                //System.out.println("opzioni");
                collided = true;
                index = 2;
                
            }
                
        }
        
        if(c.getFixtureB().getUserData() == "mouse")
        {
            if(c.getFixtureA().getUserData() == "opzioni")
            {
                //System.out.println("Gioca");
                collided = true;
                index = 2;
            }
                
        }
        
    }

    //chiamato quando due fixture finiscono di collidere
    @Override
    public void endContact(Contact c) {
        if(index == 0)
        {
            //System.out.println("non c'e' piu' contatto tra "+c.getFixtureA().getUserData()+" e "+c.getFixtureB().getUserData());
            
        }
        collided = false;
    }

    
    //collision detection
    //presolve
    //collision handling
    //postsolve
    @Override
    public void preSolve(Contact c, Manifold m) {
        
    }

    @Override
    public void postSolve(Contact c, ContactImpulse ci) {
        
    }
}
