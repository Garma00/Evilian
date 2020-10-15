package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class LevelContactListener implements ContactListener{
    //true se faccio spawnare il player in aria, false se lo faccio spawnare a terra
    public boolean inAir = true;
    
    @Override
    public void beginContact(Contact c) {
        if(c.getFixtureA().getUserData() == "player_foot" || c.getFixtureB().getUserData() == "player_foot")
        {
            System.out.println("Contatto con la terra");
            inAir=false;
        }
    }

    @Override
    public void endContact(Contact c) {
        if(c.getFixtureA().getUserData() == "player_foot" || c.getFixtureB().getUserData() == "player_foot")
        {
            System.out.println("Fine contatto con la terra");
            inAir=true;
        }
    }

    @Override
    public void preSolve(Contact c, Manifold m) {
    }

    @Override
    public void postSolve(Contact c, ContactImpulse ci) {
    }
    
}
