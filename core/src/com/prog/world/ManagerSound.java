package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.prog.entity.Button;

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
    private Button button;
    public static boolean soundOn = true;
    
    public void selectSound(int sound)
    {
        switch(sound)
        {
            case 0:
                if(soundOn)
                {
                    effetto = Gdx.audio.newMusic(Gdx.files.internal("music/effects/fireball.mp3"));
                    System.out.println(effetto.isPlaying());
                    effetto.setVolume(0.7f);
                    effetto.play();

                    break;

                }
                
            case 1:
                 
                if(soundOn)
                {
                    
                    effetto = Gdx.audio.newMusic(Gdx.files.internal("music/effects/iceball.mp3"));
                    effetto.setVolume(0.7f);
                    effetto.play();

                    break;

                }
                
            case 2:
                
                if(soundOn)
                {
                    
                    effetto = Gdx.audio.newMusic(Gdx.files.internal("music/effects/heal.mp3"));
                    effetto.setVolume(0.7f);
                    effetto.play();

                    

                    break;
                }

            case 3:
                
                if(soundOn)
                {
                    
                    effetto = Gdx.audio.newMusic(Gdx.files.internal("music/effects/meteora.mp3"));
                    effetto.setVolume(0.7f);
                    effetto.play();

                    break;

                }
                
            default:
                System.out.println("no effect found");
                break;
        }
    }
    
    public void setVolume(float v)
    {
        effetto.setVolume(v);
        if(v == 0f)
        {
            button.isActive = false;
            System.out.println("isActive = false");
        }
            
        else
        {
            button.isActive = true;
            System.out.println("isActive");
        }
            
    }
    
    public float getVolume()
    {
        return effetto.getVolume();
    }
    
    public void addSoundButton(Button b)
    {
        button=b;
    }
    
    public void update()
    {
        button.isActive = soundOn;
    }
    

}