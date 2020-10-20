package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.prog.entity.Entity;
import com.prog.entity.magia.Magia;

public class LevelContactListener implements ContactListener{
    //true se faccio spawnare il player in aria, false se lo faccio spawnare a terra
    public boolean inAir = true;
    Entity a,b;
    String sensorA,sensorB;
    boolean isSensorA,isSensorB;
    
    @Override
    public void beginContact(Contact c) {
        check(c);
        
        //check player
        if(isSensorA && sensorA=="player_foot")
        {
            inAir=false;
        }
        
        if(isSensorB && sensorB=="player_foot")
        {
            inAir=false;
        }
        
        //check magie
        if(!isSensorA && a.entity_type == "magia")
        {
            Magia spellA=(Magia)a;
            spellA.alive=false;
        }
        
        if(!isSensorB && b.entity_type == "magia")
        {
            Magia spellB=(Magia)b;
            spellB.alive=false;
        }
    }

    @Override
    public void endContact(Contact c) {
        check(c);
        
        //check player
        if(isSensorA && sensorA=="player_foot")
        {
            inAir=true;
        }
        
        if(isSensorB && sensorB=="player_foot")
        {
            inAir=true;
        }
        
        //check magie
        
    }

    @Override
    public void preSolve(Contact c, Manifold m) {
    }

    @Override
    public void postSolve(Contact c, ContactImpulse ci) {
    }
    
    public void check(Contact c)
    {
        if(c.getFixtureA().getUserData() instanceof String)
        {
            //allora la fixture e' un sensore
            sensorA=(String)c.getFixtureA().getUserData();
            isSensorA=true;
        }
        if(c.getFixtureB().getUserData() instanceof String)
        {
             //allora la fixture e' un sensore
            sensorB=(String)c.getFixtureB().getUserData();
            isSensorB=true;
        }
        
        if(c.getFixtureA().getUserData() instanceof Entity)
        {
            //allora non e' un sensore---> il suo userdata e' l'oggetto
            a=(Entity)c.getFixtureA().getUserData();
            isSensorA=false;
        }
        
        if(c.getFixtureB().getUserData() instanceof Entity)
        {
            //allora non e' un sensore---> il suo userdata e' l'oggetto
            b=(Entity)c.getFixtureB().getUserData();
            isSensorB=false;
        }
    }
}
