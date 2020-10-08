package com.prog.evilian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.prog.world.Livello1;

public class Evilian extends Game 
{

        public static SpriteBatch batch;
        public final int SCREEN_WIDTH=800;
        public final int SCREEN_HEIGHT=600;
        
    
	@Override
	public void create () 
        {
            batch=new SpriteBatch();
            //da cambiare livello1 con il menu'
            this.setScreen(new Livello1(-9,true,"map.tmx",SCREEN_WIDTH,SCREEN_HEIGHT,this));
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
