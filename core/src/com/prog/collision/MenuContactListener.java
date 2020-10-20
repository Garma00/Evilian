package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.prog.entity.Entity;
import static com.prog.world.ManagerScreen.index;

public class MenuContactListener implements ContactListener{
    //il contact listener si setta tramite world.setContactListener(istanza di questa classe)
    public boolean collided = false; 
    Entity a,b;
    //chiamato quando due fixture cominciano a collidere
    @Override
    public void beginContact(Contact c) {
        a=(Entity)c.getFixtureA().getUserData();
        b=(Entity)c.getFixtureB().getUserData();
        //System.out.println("rilevato contatto tra "+c.getFixtureA().getUserData()+" e "+c.getFixtureB().getUserData());
        if(a.entity_type == "mouse")
        {
            if(b.entity_type == "gioca")
            {
                //System.out.println("Gioca");
                collided = true;
                index = 1;
                
            }
                
        }
        
        if(b.entity_type == "mouse")
        {
            if(a.entity_type == "gioca")
            {
                //System.out.println("Gioca");
                collided = true;
                index = 1;
            }
                
        }
        
        if(a.entity_type == "mouse")
        {
            if(b.entity_type == "opzioni")
            {
                //System.out.println("opzioni");
                collided = true;
                index = 2;
                
            }
                
        }
        
        if(b.entity_type == "mouse")
        {
            if(a.entity_type == "opzioni")
            {
                //System.out.println("opzioni");
                collided = true;
                index = 2;
            }
                
        }
        
    }

    //chiamato quando due fixture finiscono di collidere
    @Override
    public void endContact(Contact c) {
        a=(Entity)c.getFixtureA().getUserData();
        b=(Entity)c.getFixtureB().getUserData();
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
