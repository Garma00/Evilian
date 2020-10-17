package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class ManagerSound 
{
    /*
    0       fireball
    1       iceball
    2       cura
    3       meteora
    */
    
    int sound = -1;
    public Music effetto;
    
    public void selectSound(int sound)
    {
        switch(sound)
        {
            case 0:
                
                effetto = Gdx.audio.newMusic(Gdx.files.internal("music/effects/fireball.mp3"));
                effetto.setVolume(0.4f);
                effetto.play();
                
                if(!effetto.isPlaying())
                    effetto.dispose();
                
                break;
            
            case 1:
                 
                effetto = Gdx.audio.newMusic(Gdx.files.internal("music/effects/iceball.mp3"));
                effetto.setVolume(0.4f);
                effetto.play();
                
                if(!effetto.isPlaying())
                    effetto.dispose();
                
                break;
                
            case 2:
                 
                effetto = Gdx.audio.newMusic(Gdx.files.internal("music/effects/heal.mp3"));
                effetto.setVolume(0.4f);
                effetto.play();
                
                if(!effetto.isPlaying())
                    effetto.dispose();
                
                break;
                
            case 3:
                 
                effetto = Gdx.audio.newMusic(Gdx.files.internal("music/effects/meteora.mp3"));
                effetto.setVolume(0.4f);
                effetto.play();
                
                if(!effetto.isPlaying())
                    effetto.dispose();
                
                break;
                
            default:
                System.out.println("no effect found");
                break;
        }
    }
    
    public void setVolume(float v)
    {
        effetto.setVolume(v);
    }
    
}
