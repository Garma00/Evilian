package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.prog.entity.EnemyA;
import com.prog.entity.Entity.userDataContainer;
import com.prog.entity.Player;
import com.prog.entity.magia.Magia;
import com.prog.entity.magia.Meteora;
import com.prog.entity.magia.PallaDiFuoco;
import com.prog.entity.magia.PallaDiGhiaccio;

public class LevelContactListener implements ContactListener{
    userDataContainer a,b;
    boolean isSensorA,isSensorB;
    
    @Override
    public void beginContact(Contact c) {
        check(c);
        
        //System.out.println("Collisione tra "+c.getFixtureA().getUserData()+ " e "+c.getFixtureB().getUserData());
        
        //check player
        if(isSensorA && a.type=="player_foot")
        {
            System.out.println("Personaggio a terra");
            //System.out.println(a+" "+b);
            ((Player)a.e).inAir=false;
        }
        
        if(isSensorB && b.type=="player_foot")
        {
            System.out.println("Personaggio a terra");
            //System.out.println(a+" "+b);
            ((Player)b.e).inAir=false;
        }
        
        //check magie
        //check di null perche' non e' detto che la funzione check instanzi a e b
        //(ovvero non entra negli if)
        
        if(!isSensorA && a!=null && a.type == "magia")
        {
            Magia spellA=(Magia)a.e;
            spellA.alive=false;
        }
        
        if(!isSensorB && b != null && b.type == "magia")
        {
            Magia spellB=(Magia)b.e;
            spellB.alive=false;
        }
        
        if(!isSensorB && !isSensorA && a != null && a.type == "magia" &&  b != null && b.type == "enemyA")
        {
            if(a.e instanceof PallaDiFuoco)
                ((EnemyA)b.e).damage(0.4f);
            else if(a.e instanceof PallaDiGhiaccio)
            {
                ((EnemyA)b.e).debuffVelocita();
                ((EnemyA)b.e).damage(0.2f);
            }
            else if(a.e instanceof Meteora)
                ((EnemyA)b.e).damage(0.8f);
                
        }
        
        if(!isSensorA && !isSensorB && b != null && a != null &&  b.type == "magia" && a.type == "enemyA")
        {
            if(b.e instanceof PallaDiFuoco)
                ((EnemyA)a.e).damage(0.4f);
            else if(b.e instanceof PallaDiGhiaccio)
            {
                ((EnemyA)a.e).damage(0.2f);
                ((EnemyA)a.e).debuffVelocita();
            }
            else if(b.e instanceof Meteora)
                ((EnemyA)a.e).damage(0.8f);
        }
        
        
        
        
        
        //se un nemico tocca un muro
        if(isSensorA && a.type=="enemyLeftFoot")
        {
            ((EnemyA)a.e).walkLeft=false;
        }
        
        if(isSensorB && b.type=="enemyLeftFoot")
        {
            ((EnemyA)b.e).walkLeft=false;
        }
        
        if(isSensorA && a.type=="enemyRightFoot")
        {
            ((EnemyA)a.e).walkLeft=true;
        }
        
        if(isSensorB && b.type=="enemyRightFoot")
        {
            ((EnemyA)b.e).walkLeft=true;
        }
        
        //fine collisione piedi del nemico con le mura
        
    }

    @Override
    public void endContact(Contact c) {
        check(c);
        
        //check player
        if(isSensorA && a.type=="player_foot")
        {
            System.out.println("Personaggio in aria");
            //System.out.println("fine "+a+" "+b);
            ((Player)a.e).inAir=true;
        }
        
        if(isSensorB && b.type=="player_foot")
        {
            System.out.println("Personaggio in aria");
            //System.out.println("fine "+a+" "+b);
            ((Player)b.e).inAir=true;
        }
        
        //piediiiii
        if(isSensorA && a.type=="enemyLeftFoot")
        {
            ((EnemyA)a.e).walkLeft=false;
        }
        
        if(isSensorB && b.type=="enemyLeftFoot")
        {
            ((EnemyA)b.e).walkLeft=false;
        }
        
        if(isSensorA && a.type=="enemyRightFoot")
        {
            ((EnemyA)a.e).walkLeft=true;
        }
        
        if(isSensorB && b.type=="enemyRightFoot")
        {
            ((EnemyA)b.e).walkLeft=true;
        }
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
            a=(userDataContainer)c.getFixtureA().getUserData();
            isSensorA=true;
        }else if(!(c.getFixtureA().getUserData() instanceof String))
        {
            a=(userDataContainer)c.getFixtureA().getUserData();
            isSensorA=false;
        }
        
        if(c.getFixtureB().isSensor())
        {
            //allora la fixture e' un sensore
            //nella classe entity ho assegnato ai sensori la stringa come userdata
            b=(userDataContainer)c.getFixtureB().getUserData();
            isSensorB=true;
        }else if(!(c.getFixtureB().getUserData() instanceof String))
        {
            b=(userDataContainer)c.getFixtureB().getUserData();
            isSensorB=false;
        }
    }
}
