package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CustomContactListener implements ContactListener{
    //il contact listener si setta tramite world.setContactListener(istanza di questa classe)
    
    //chiamato quando due fixture cominciano a collidere
    @Override
    public void beginContact(Contact c) {
        System.out.println("rilevato contatto tra "+c.getFixtureA().getUserData()+" e "+c.getFixtureB().getUserData());
    }

    //chiamato quando due fixture finiscono di collidere
    @Override
    public void endContact(Contact c) {
         System.out.println("non c'e' piu' contatto tra "+c.getFixtureA().getUserData()+" e "+c.getFixtureB().getUserData());
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
