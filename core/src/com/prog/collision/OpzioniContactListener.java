package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.prog.entity.Button;
import com.prog.entity.Entity.userDataContainer;
import com.prog.entity.Mouse;
import com.prog.world.ManagerMusic;
import static com.prog.world.ManagerScreen.index;
import static com.prog.evilian.Evilian.MANAGER_MUSIC;
import static com.prog.evilian.Evilian.MANAGER_SOUND;



public class OpzioniContactListener implements ContactListener{
    public boolean collided = false;
    ManagerMusic m = new ManagerMusic();
    userDataContainer a,b;
    

    @Override
    public void beginContact(Contact c) {
        a=(userDataContainer)c.getFixtureA().getUserData();
        b=(userDataContainer)c.getFixtureB().getUserData();
        if(a.type == "mouse")
        {
            if(b.type == "riprendi")
            {
                System.out.println("Riprendi");
                collided = true;
                index = 3;//se si vuole riprendere viene settato l'index a riprendi
                
            }
                
        }
        if(b.type == "mouse")
        {
            if(a.type == "riprendi")
            {
                System.out.println("Riprendi");
                collided = true;
                index = 3;
                
            }
                
        }
        if(b.type == "mouse")
        {
            if(a.type == "MainMenu")
            {
                System.out.println("MainMenu");
                collided = true;
                index = 0;//l'index andrà a default
                
            }
                
        }
        if(a.type == "mouse")
        {
            if(b.type == "MainMenu")
            {
                System.out.println("MainMenu");
                collided = true;
                index = 0;
                
            }
                
        }
        //se viene premuto il bottone musica il volume passa da 0 a 0.1 e viceversa
        if(a.type == "mouse")
        {
            if(b.type == "musica")
            {
                ((Mouse)a.e).toReposition=true;
                System.out.println("musica");
                if(MANAGER_MUSIC.getVolume() > 0)
                {
                    System.out.println("Volume musica 0");
                    MANAGER_MUSIC.setVolume(0f);
                    ((Button)b.e).isActive=false;
                }  
                else
                {
                    MANAGER_MUSIC.setVolume(0.5f);
                    System.out.println("Volume musica 0.5");
                    ((Button)b.e).isActive=true;
                }
            }
                
        }
        
        if(b.type == "mouse")
        {
            if(a.type == "musica")
            {
                ((Mouse)b.e).toReposition=true;
                System.out.println("musica");
                
                if(MANAGER_MUSIC.getVolume() > 0)
                {
                    System.out.println("Volume musica 0");
                    MANAGER_MUSIC.setVolume(0f);
                    ((Button)a.e).isActive=false;
                    
                }                    
                else
                {
                    MANAGER_MUSIC.setVolume(0.5f);
                    System.out.println("Volume musica 0.5");
                    ((Button)a.e).isActive=false;
                }
            }
                
        }
        
        if(b.type == "mouse")
        {
            if(a.type == "sound")
            {
                ((Mouse)b.e).toReposition=true;
                System.out.println("sound");
                
                if(MANAGER_SOUND.soundOn)
                {
                    System.out.println("Suoni disattivati");
                    MANAGER_SOUND.soundOn = false;
                    ((Button)a.e).isActive=false;
                }
                    
                    
                else
                {
                    MANAGER_SOUND.soundOn = true;
                    System.out.println("Suoni attivi");
                    ((Button)a.e).isActive=true;
                }
            }
                
        }
        
        if(a.type == "mouse")
        {
            if(b.type == "sound")
            {
                ((Mouse)a.e).toReposition=true;
                System.out.println("sound");
                if(MANAGER_SOUND.soundOn)
                {
                    System.out.println("Suoni disattivati");
                    MANAGER_SOUND.soundOn = false;
                    ((Button)b.e).isActive=false;
                }
                
                else
                {
                    MANAGER_SOUND.soundOn = true;
                    System.out.println("Suoni attivi");
                    ((Button)b.e).isActive=true;
                }
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
