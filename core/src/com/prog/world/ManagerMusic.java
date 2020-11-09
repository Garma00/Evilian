package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class ManagerMusic 
{
    private final Music musica=Gdx.audio.newMusic(Gdx.files.internal("music/main_theme.mp3"));
    
    public void selectMusic()
    {
        if(!musica.isPlaying())
        {
            musica.setVolume(0.4f);
            musica.play();
        }
    }
    
    public void setVolume(float volume)
    {
        if(musica.isPlaying() && volume >= 0 && volume <= 1)
            musica.setVolume(volume);
        else
            System.out.println("volume errato");
    }

    public float getVolume() 
    {
        return musica.getVolume();
    }
    
    public void dispose()
    {
        if(musica.isPlaying())
            musica.pause();
        musica.dispose();
    }

}
