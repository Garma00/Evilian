package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.prog.entity.Entity.userDataContainer;
import com.prog.world.ManagerScreen;

public class MenuContactListener implements ContactListener{
    //il contact listener si setta tramite world.setContactListener(istanza di questa classe)
    private boolean collided = false; 
    private userDataContainer a,b;
    //chiamato quando due fixture cominciano a collidere
    @Override
    public void beginContact(Contact c) {
        a=((userDataContainer)c.getFixtureA().getUserData());
        b=((userDataContainer)c.getFixtureB().getUserData());
        //System.out.println("rilevato contatto tra "+c.getFixtureA().getUserData()+" e "+c.getFixtureB().getUserData());
        if(a.type == "mouse")
        {
            if(b.type == "gioca")
            {
                //System.out.println("Gioca");
                collided = true;
                ManagerScreen.getInstance().setIndex(1);
            }
                
        }
        
        if(b.type == "mouse")
        {
            if(a.type == "gioca")
            {
                //System.out.println("Gioca");
                collided = true;
                ManagerScreen.getInstance().setIndex(1);
            }
                
        }
        
        if(a.type == "mouse")
        {
            if(b.type == "opzioni")
            {
                //System.out.println("opzioni");
                collided = true;
                ManagerScreen.getInstance().setIndex(2);
            }
                
        }
        
        if(b.type == "mouse")
        {
            if(a.type == "opzioni")
            {
                //System.out.println("opzioni");
                collided = true;
                ManagerScreen.getInstance().setIndex(2);
            }
                
        }
        
    }

    //chiamato quando due fixture finiscono di collidere
    @Override
    public void endContact(Contact c) {
        a=(userDataContainer)c.getFixtureA().getUserData();
        b=(userDataContainer)c.getFixtureB().getUserData();
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
    
    public boolean getCollided()
    {
        return collided;
    }
}
