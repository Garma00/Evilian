package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class ManagerMusic 
{
    /*elenco musice corrispondenti a traccia
    1           mainmenu
    2           livello1
    default     dispose della musica
    */
    private Music musica;
    
    public void selectMusic(int traccia)
    {
        
        switch(traccia)
        {
            case 1:
                if(musica != null)
                    musica.dispose();
                musica = Gdx.audio.newMusic(Gdx.files.internal("music/main_theme.mp3"));
                musica.play();
                musica.setVolume(0.4f);
                musica.setLooping(true);
                break;
        
            case 2:
                if(musica != null)
                    musica.dispose();
                musica = Gdx.audio.newMusic(Gdx.files.internal("music/main_theme.mp3"));
                musica.play();
                musica.setVolume(0.4f);
                musica.setLooping(true);
                break;
        
            default:
                if(musica != null)
                    musica.dispose();
        }
    }
    
    public void setVolume(float volume)
    {
        if(musica.isPlaying() && volume >= 0 && volume <= 1)
            musica.setVolume(volume);
        else
            System.out.println("volume errato");
    }

    public float getVolume() {
        return musica.getVolume();
    }

}
