package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.prog.entity.Button;
import com.prog.entity.Entity.userDataContainer;
import com.prog.entity.Mouse;
import com.prog.evilian.Evilian;
import com.prog.world.ManagerMusic;
import com.prog.world.ManagerScreen;
import com.prog.world.ManagerSound;



public class OpzioniContactListener implements ContactListener{
    private boolean collided = false;
    private userDataContainer a,b;

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
                ManagerScreen.setIndex(3);
            }
                
        }
        if(b.type == "mouse")
        {
            if(a.type == "riprendi")
            {
                System.out.println("Riprendi");
                collided = true;
                ManagerScreen.setIndex(3);
            }
                
        }
        if(b.type == "mouse")
        {
            if(a.type == "MainMenu")
            {
                System.out.println("MainMenu");
                collided = true;
                ManagerScreen.setIndex(0);//l'index andra' a default
            }
                
        }
        if(a.type == "mouse")
        {
            if(b.type == "MainMenu")
            {
                System.out.println("MainMenu");
                collided = true;
                ManagerScreen.setIndex(0);
            }
                
        }
        //se viene premuto il bottone music il volume passa da 0 a 0.1 e viceversa
        if(a.type == "mouse")
        {
            if(b.type == "music")
            {
                ((Mouse)a.e).setToReposition(true);
                System.out.println("music");
                if(ManagerMusic.getInstance().getVolume() > 0)
                {
                    System.out.println("Volume music 0");
                    ManagerMusic.getInstance().setVolume(0f);
                    ManagerMusic.getInstance().setMusicOn(false);
                    ((Button)b.e).setActive(false);
                }  
                else
                {
                   ManagerMusic.getInstance().setVolume(0.5f);
                    System.out.println("Volume music 0.5");
                    ManagerMusic.getInstance().setMusicOn(true);
                    ((Button)b.e).setActive(true);
                }
            }
                
        }
        
        if(b.type == "mouse")
        {
            if(a.type == "music")
            {
                ((Mouse)b.e).setToReposition(true);
                System.out.println("music");
                
                if(ManagerMusic.getInstance().getVolume() > 0)
                {
                    System.out.println("Volume music 0");
                    ManagerMusic.getInstance().setVolume(0f);
                    ManagerMusic.getInstance().setMusicOn(false);
                    ((Button)a.e).setActive(false);
                    
                }                    
                else
                {
                    ManagerMusic.getInstance().setVolume(0.5f);
                    System.out.println("Volume music 0.5");
                    ManagerMusic.getInstance().setMusicOn(true);
                    ((Button)a.e).setActive(false);
                }
            }
                
        }
        
        if(b.type == "mouse")
        {
            if(a.type == "sound")
            {
                ((Mouse)b.e).setToReposition(true);
                System.out.println("sound");
                
                if(ManagerSound.getInstance().isSoundOn())
                {
                    System.out.println("Suoni disattivati");
                    ManagerSound.getInstance().setSoundOn(false);
                    ((Button)a.e).setActive(false);
                }
                else
                {
                    ManagerSound.getInstance().setSoundOn(true);
                    System.out.println("Suoni attivi");
                    ((Button)a.e).setActive(true);
                }
            }
                
        }
        
        if(a.type == "mouse")
        {
            if(b.type == "sound")
            {
                ((Mouse)a.e).setToReposition(true);
                System.out.println("sound");
                if(ManagerSound.getInstance().isSoundOn())
                {
                    System.out.println("Suoni disattivati");
                    ManagerSound.getInstance().setSoundOn(false);
                    ((Button)b.e).setActive(false);
                }
                
                else
                {
                   ManagerSound.getInstance().setSoundOn(true);
                    System.out.println("Suoni attivi");
                    ((Button)b.e).setActive(true);
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
    
    public boolean getCollided()
    {
        return collided;
    }
}
