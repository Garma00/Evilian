package com.prog.evilian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.prog.world.MainMenu;

public class Evilian extends Game 
{
        public static SpriteBatch batch;
        private final int SCREEN_WIDTH=800;
        private final int SCREEN_HEIGHT=600;
        public static final float PPM=100f;
            
	@Override
	public void create () 
        {
            batch=new SpriteBatch();
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
	}
        
        public int getScreenWidth()
        {
            return SCREEN_WIDTH;
        }
        
        public int getScreenHeight()
        {
            return SCREEN_HEIGHT;
        }
        
}
