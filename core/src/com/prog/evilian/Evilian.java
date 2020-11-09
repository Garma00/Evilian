package com.prog.evilian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.prog.world.MainMenu;
import com.prog.world.ManagerMusic;
import com.prog.world.ManagerSound;

public class Evilian extends Game 
{
        public static SpriteBatch batch;
        private final int SCREEN_WIDTH=800;
        private final int SCREEN_HEIGHT=600;
        public static final float PPM=100f;
        private static ManagerMusic MANAGER_MUSIC;
        private static ManagerSound MANAGER_SOUND;
    
	@Override
	public void create () 
        {
            batch=new SpriteBatch();
            //da cambiare livello1 con il menu'
            MANAGER_MUSIC = new ManagerMusic();
            MANAGER_SOUND = new ManagerSound();
            this.setScreen(new MainMenu(SCREEN_WIDTH,SCREEN_HEIGHT,this));
	}

	@Override
	public void render () 
        {
            super.render();
	}
	
        //chiamata quando l'applicazione viene terminata
	@Override
	public void dispose () 
        {
            batch.dispose();
            MANAGER_MUSIC.dispose();
            MANAGER_SOUND.dispose();
	}
        
        public int getScreenWidth()
        {
            return SCREEN_WIDTH;
        }
        
        public int getScreenHeight()
        {
            return SCREEN_HEIGHT;
        }
        
        public static ManagerMusic getManagerMusic()
        {
            return MANAGER_MUSIC;
        }
        
        public static ManagerSound getManagerSound()
        {
            return MANAGER_SOUND;
        }
}
