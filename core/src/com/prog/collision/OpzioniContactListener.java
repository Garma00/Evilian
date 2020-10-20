package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.prog.entity.Button;
import com.prog.entity.Entity;
import com.prog.world.ManagerMusic;
import static com.prog.world.ManagerScreen.index;
import static com.prog.evilian.Evilian.MANAGER_MUSIC;
import static com.prog.evilian.Evilian.MANAGER_SOUND;



public class OpzioniContactListener implements ContactListener{
    public boolean collided = false;
    ManagerMusic m = new ManagerMusic();
    Entity a,b;
    

    @Override
    public void beginContact(Contact c) {
        a=(Entity)c.getFixtureA().getUserData();
        b=(Entity)c.getFixtureB().getUserData();
        if(a.entity_type == "mouse")
        {
            if(b.entity_type == "riprendi")
            {
                System.out.println("Riprendi");
                collided = true;
                index = 1;//se si vuole riprendere viene settato l'index a gioca
                
            }
                
        }
        if(b.entity_type == "mouse")
        {
            if(a.entity_type == "riprendi")
            {
                System.out.println("Gioca");
                collided = true;
                index = 1;
                
            }
                
        }
        if(b.entity_type == "mouse")
        {
            if(a.entity_type == "MainMenu")
            {
                System.out.println("MainMenu");
                collided = true;
                index = 0;//l'index andrà a default
                
            }
                
        }
        if(a.entity_type == "mouse")
        {
            if(b.entity_type == "MainMenu")
            {
                System.out.println("MainMenu");
                collided = true;
                index = 0;
                
            }
                
        }
        //se viene premuto il bottone musica il volume passa da 0 a 0.1 e viceversa
        if(a.entity_type == "mouse")
        {
            if(b.entity_type == "musica")
            {
                System.out.println("musica");
                if(MANAGER_MUSIC.getVolume() > 0)
                {
                    System.out.println("Volume musica 0");
                    MANAGER_MUSIC.setVolume(0f);
                    ((Button)b).isActive=false;
                }  
                else
                {
                    MANAGER_MUSIC.setVolume(0.5f);
                    System.out.println("Volume musica 0.5");
                    ((Button)b).isActive=true;
                }
            }
                
        }
        
        if(b.entity_type == "mouse")
        {
            if(a.entity_type == "musica")
            {
                System.out.println("musica");
                
                if(MANAGER_MUSIC.getVolume() > 0)
                {
                    System.out.println("Volume musica 0");
                    MANAGER_MUSIC.setVolume(0f);
                    ((Button)a).isActive=false;
                    
                }                    
                else
                {
                    MANAGER_MUSIC.setVolume(0.5f);
                    System.out.println("Volume musica 0.5");
                    ((Button)a).isActive=false;
                }
            }
                
        }
        
        if(b.entity_type == "mouse")
        {
            if(a.entity_type == "sound")
            {
                System.out.println("sound");
                
                if(MANAGER_SOUND.soundOn)
                {
                    System.out.println("Suoni disattivati");
                    MANAGER_SOUND.soundOn = false;
                    ((Button)a).isActive=false;
                }
                    
                    
                else
                {
                    MANAGER_SOUND.soundOn = true;
                    System.out.println("Suoni attivi");
                    ((Button)a).isActive=true;
                }
            }
                
        }
        
        if(a.entity_type == "mouse")
        {
            if(b.entity_type == "sound")
            {
                System.out.println("sound");
                
                if(MANAGER_SOUND.soundOn)
                {
                    System.out.println("Suoni disattivati");
                    MANAGER_SOUND.soundOn = false;
                    ((Button)b).isActive=false;
                }
                
                else
                {
                    MANAGER_SOUND.soundOn = true;
                    System.out.println("Suoni attivi");
                    ((Button)b).isActive=true;
                }
            }
                
        }
        
        
        
        
        /*da fixare  
        if(c.getFixtureA().getUserData() == "mouse")
            c.getFixtureA().getBody().setTransform(-100, -100, 0);
        
        if(c.getFixtureB().getUserData() == "mouse")
            c.getFixtureB().getBody().setTransform(-100, -100, 0);
        */
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
