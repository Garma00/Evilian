package com.prog.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.prog.world.ManagerMusic;
import static com.prog.world.ManagerScreen.index;
import static com.prog.evilian.Evilian.MANAGER_MUSIC;



public class OpzioniContactListener implements ContactListener{
    public boolean collided = false;
    ManagerMusic m = new ManagerMusic();
    

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
        //se viene premuto il bottone musica il volume passa da 0 a 0.1 e viceversa
        if(c.getFixtureA().getUserData() == "mouse")
        {
            if(c.getFixtureB().getUserData() == "musica")
            {
                System.out.println("musica");
                //collided = true;
                if(MANAGER_MUSIC.getVolume() > 0)
                {
                    System.out.println("Volume musica 0");
                    MANAGER_MUSIC.setVolume(0f);
                }
                    
                    
                else
                {
                    MANAGER_MUSIC.setVolume(0.5f);
                    System.out.println("Volume musica 0.5");
                }
            }
                
        }
        
        if(c.getFixtureB().getUserData() == "mouse")
        {
            if(c.getFixtureA().getUserData() == "musica")
            {
                System.out.println("musica");
                //collided = true;
                
                if(MANAGER_MUSIC.getVolume() > 0)
                {
                    System.out.println("Volume musica 0");
                    MANAGER_MUSIC.setVolume(0f);
                }
                    
                    
                else
                {
                    MANAGER_MUSIC.setVolume(0.5f);
                    System.out.println("Volume musica 0.5");
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
