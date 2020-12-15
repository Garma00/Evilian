package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.prog.entity.Enemy;
import com.prog.entity.Entity.userDataContainer;
import com.prog.entity.Player;
import com.prog.entity.Magia;

public class LevelContactListener implements ContactListener{
    private userDataContainer a,b;
    private boolean isSensorA,isSensorB;
    
    @Override
    public void beginContact(Contact c) {
        check(c);
        
        //check player
        if(isSensorA && a.type=="player_foot")
        {
            //System.out.println("Personaggio a terra");
            //System.out.println(a+" "+b);
            ((Player)a.e).setInAir(false);
        }
        
        if(isSensorB && b.type=="player_foot")
        {
            //System.out.println("Personaggio a terra");
            //System.out.println(a+" "+b);
            ((Player)b.e).setInAir(false);
        }
        
        //check magie
        //check di null perche' non e' detto che la funzione check instanzi a e b
        //(ovvero non entra negli if)
        
        if(!isSensorA && a!=null && a.type == "magia")
        {
            Magia spellA=(Magia)a.e;
            spellA.setAlive(false);
        }
        
        if(!isSensorB && b != null && b.type == "magia")
        {
            Magia spellB=(Magia)b.e;
            spellB.setAlive(false);
        }
        
        if(!isSensorB && !isSensorA && a != null && a.type == "magia" &&  b != null && (b.type == "enemyA" || b.type == "enemyB"))
        {
            ((Magia)a.e).applyDamage((Enemy)b.e);
        }
        
        if(!isSensorA && !isSensorB && b != null && a != null &&  b.type == "magia" && (a.type == "enemyA" || a.type == "enemyB"))
        {
           ((Magia)b.e).applyDamage((Enemy)a.e);
        }
        
        
        if(!isSensorA && !isSensorB && b != null && a != null && a.type == "player" && (b.type == "enemyA" || b.type == "enemyB"))
            ((Player)a.e).applyDmg(0.25f);
        
        if(!isSensorA && !isSensorB && b != null && a != null && b.type == "player" && (a.type == "enemyA" || a.type == "enemyB"))
            ((Player)b.e).applyDmg(0.25f);
        
        
        //se un nemico tocca qualunque cosa che non sia il player 
        if(isSensorA && a.type=="enemyLeftFoot" &&!(b != null && (b.type == "player" || b.type == "player_foot")))
        {
            ((Enemy)a.e).setWalkLeft(false);
        }
        
        if(isSensorB && b.type=="enemyLeftFoot" && !(a != null && (a.type == "player" || a.type == "player_foot")))
        {
            ((Enemy)b.e).setWalkLeft(false);
        }
        
        if(isSensorA && a.type=="enemyRightFoot" &&!(b != null && (b.type == "player" || b.type == "player_foot")))
        {
            ((Enemy)a.e).setWalkLeft(true);
        }
        
        if(isSensorB && b.type=="enemyRightFoot"&& !(a != null && (a.type == "player" || a.type == "player_foot")))
        {
            ((Enemy)b.e).setWalkLeft(true);
        }
        
        //fine collisione piedi del nemico con le mura
        
        //collisione fine livello
        if(!isSensorA && c.getFixtureA().getUserData() instanceof String && c.getFixtureA().getUserData() == "end_level")
        {
            //b deve essere per forza il palyer per come abbiamo settato i bit di maschera
            ((Player)b.e).setLevelCompleted(true);
        }
        
        if(!isSensorB && c.getFixtureB().getUserData() instanceof String && c.getFixtureB().getUserData() == "end_level")
        {
            //b deve essere per forza il palyer per come abbiamo settato i bit di maschera
            ((Player)a.e).setLevelCompleted(true);
        }
        
    }

    @Override
    public void endContact(Contact c) {
        check(c);
        
        //check player
        //se il piede del player smette di collidere con qualunque cosa che non siano i piedi del nemico 
        if(isSensorA && a.type=="player_foot" && !(isSensorB && (b.type == "enemyLeftFoot" || b.type == "enemyRightFoot")))
        {
            //System.out.println("Personaggio in aria");
            //System.out.println("fine "+a+" "+b);
            ((Player)a.e).setInAir(true);
        }
        
        
        if(isSensorB && b.type=="player_foot" && !(isSensorA && (a.type == "enemyLeftFoot" || a.type == "enemyRightFoot")))
        {
            //System.out.println("Personaggio in aria");
            //System.out.println("fine "+a+" "+b);
            ((Player)b.e).setInAir(true);
        }
        
        //piediiiii
        if(isSensorA && a.type=="enemyLeftFoot")
        {
            ((Enemy)a.e).setWalkLeft(false);
        }
        
        if(isSensorB && b.type=="enemyLeftFoot")
        {
            ((Enemy)b.e).setWalkLeft(false);
        }
        
        if(isSensorA && a.type=="enemyRightFoot")
        {
            ((Enemy)a.e).setWalkLeft(true);
        }
        
        if(isSensorB && b.type=="enemyRightFoot")
        {
            ((Enemy)b.e).setWalkLeft(true);
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
        a=null;
        b=null;
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
        }else
            isSensorA=false;

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
        }else
            isSensorB=false;
    }
}
