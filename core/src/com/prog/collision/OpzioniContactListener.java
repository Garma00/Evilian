package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import static com.prog.world.ManagerScreen.index;

public class OpzioniContactListener implements ContactListener{
    public boolean collided = false; 
    

    @Override
    public void beginContact(Contact c) {
        if(c.getFixtureA().getUserData() == "mouse")
        {
            if(c.getFixtureB().getUserData() == "riprendi")
            {
                System.out.println("Riprendi");
                collided = true;
                index = 1;//se si vuole riprendere viene settato l'index a gioca
                
            }
                
        }
        if(c.getFixtureB().getUserData() == "mouse")
        {
            if(c.getFixtureA().getUserData() == "riprendi")
            {
                System.out.println("Gioca");
                collided = true;
                index = 1;
                
            }
                
        }
        if(c.getFixtureB().getUserData() == "mouse")
        {
            if(c.getFixtureA().getUserData() == "MainMenu")
            {
                System.out.println("MainMenu");
                collided = true;
                index = 0;//l'index andrà a default
                
            }
                
        }
        if(c.getFixtureA().getUserData() == "mouse")
        {
            if(c.getFixtureB().getUserData() == "MainMenu")
            {
                System.out.println("MainMenu");
                collided = true;
                index = 0;
                
            }
                
        }
        
    }

    @Override
    public void endContact(Contact c) {
        collided = false;
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
    }
    
}
