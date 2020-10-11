package com.prog.evilian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.prog.world.MainMenu;

public class Evilian extends Game 
{

        public static SpriteBatch batch;
        public final int SCREEN_WIDTH=800;
        public final int SCREEN_HEIGHT=600;
        public static final float PPM=100f;
        //musica è dichiarata qui static perchè altrimenti ogni volta che cambio screen ne viene istanziata una nuova
        //non ho trovato soluzioni migliori
        public static Music musica;

        
    
	@Override
	public void create () 
        {
            batch=new SpriteBatch();
            //da cambiare livello1 con il menu'
            this.setScreen(new MainMenu(SCREEN_WIDTH,SCREEN_HEIGHT,this));
            //se non ti piace sta canzone la cambiamo ne ho presa una random <3
            musica = Gdx.audio.newMusic(Gdx.files.internal("opening.mp3"));
            musica.setLooping(true);
            musica.play();
            musica.setVolume(0.1f);

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
