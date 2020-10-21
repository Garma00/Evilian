package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.prog.entity.Entity;
import com.prog.entity.Player;
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
        
        System.out.println("Collisione tra "+c.getFixtureA().getUserData()+ " e "+c.getFixtureB().getUserData());
        
        //check player
        if(isSensorA && sensorA=="player_foot")
        {
            //System.out.println(a+" "+b);
            inAir=false;
        }
        
        if(isSensorB && sensorB=="player_foot")
        {
            //System.out.println(a+" "+b);
            inAir=false;
        }
        
        //check magie
        //check di null perchè non è detto che la funzione check instanzi a e b
        //(ovvero non entra negli if)
        if(!isSensorA && a!=null && a.entity_type == "magia")
        {
            Magia spellA=(Magia)a;
            spellA.alive=false;
        }
        
        if(!isSensorB && b != null && b.entity_type == "magia")
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
            System.out.println("fine "+a+" "+b);
            inAir=true;
        }
        
        if(isSensorB && sensorB=="player_foot")
        {
            System.out.println("fine "+a+" "+b);
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
        //ATTENZIONE:
        //gli oggetti della mappa hanno come userdata la stringa map_object e non loro stessi
        if(c.getFixtureA().isSensor())
        {
            //allora la fixture e' un sensore
            //nella classe entity ho assegnato ai sensori la stringa come userdata
            sensorA=(String)c.getFixtureA().getUserData();
            isSensorA=true;
        }
        if(c.getFixtureB().isSensor())
        {
             //allora la fixture e' un sensore
            //nella classe entity ho assegnato ai sensori la stringa come userdata
            sensorB=(String)c.getFixtureB().getUserData();
            isSensorB=true;
        }
        
        if(c.getFixtureA().getUserData() instanceof Entity)
        {
            //allora non e' un sensore---> il suo userdata e' l'oggetto stesso
            a=(Entity)c.getFixtureA().getUserData();
            isSensorA=false;
        }
        
        if(c.getFixtureB().getUserData() instanceof Entity)
        {
            //allora non e' un sensore---> il suo userdata e' l'oggetto stesso
            b=(Entity)c.getFixtureB().getUserData();
            isSensorB=false;
        }
    }
}
