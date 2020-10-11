package com.prog.evilian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.prog.world.MainMenu;
import com.prog.world.ManagerMusic;

public class Evilian extends Game 
{

        public static SpriteBatch batch;
        public final int SCREEN_WIDTH=800;
        public final int SCREEN_HEIGHT=600;
        public static final float PPM=100f;
        public static ManagerMusic MANAGER_MUSIC;

        
    
	@Override
	public void create () 
        {
            batch=new SpriteBatch();
            //da cambiare livello1 con il menu'
            MANAGER_MUSIC = new ManagerMusic();
            this.setScreen(new MainMenu(SCREEN_WIDTH,SCREEN_HEIGHT,this));
           

	}

	@Override
	public void render () 
        {
            super.render();
	}
	
	@Override
	public void dispose () 
        {
            batch.dispose();
	}
}
