package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.prog.entity.Button;


public class ManagerMusic 
{
    /*elenco musice corrispondenti a traccia
    1           mainmenu
    2           livello1
    default     dispose della musica
    */
    private Music musica;
    private Button button;
    
    public void selectMusic(int traccia)
    {
        
        switch(traccia)
        {
            case 1:
                if(musica != null)
                    musica.dispose();
                musica = Gdx.audio.newMusic(Gdx.files.internal("opening.mp3"));
                musica.play();
                musica.setLooping(true);
                break;
        
            case 2:
                if(musica != null)
                    musica.dispose();
                musica = Gdx.audio.newMusic(Gdx.files.internal("ope.mp3"));
                musica.play();
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
        
        if(volume==0)
            button.isActive=false;
        else
            button.isActive=true;
    }

    public float getVolume() {
        return musica.getVolume();
    }

    public void addMusicButton(Button b)
    {
        button=b;
    }
}