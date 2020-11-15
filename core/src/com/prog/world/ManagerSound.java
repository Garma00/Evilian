package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.prog.entity.Player;


public class ManagerSound 
{
    /*
    0       fireball
    1       iceball
    2       cura
    3       meteora
    */
    private static ManagerSound INSTANCE = null;
    private boolean soundOn = true;
    private float volume=0.7f;
    private final Sound fbEffect =Gdx.audio.newSound(Gdx.files.internal("music/effects/fireball.mp3"));;
    private final Sound ibEffect =Gdx.audio.newSound(Gdx.files.internal("music/effects/iceball.mp3"));
    private final Sound healEffect =Gdx.audio.newSound(Gdx.files.internal("music/effects/heal.mp3"));
    private final Sound meteorEffect =Gdx.audio.newSound(Gdx.files.internal("music/effects/meteora.mp3"));
    
    
    public void selectSound(int sound)
    {
        switch(sound)
        {
            case 0:
                if(soundOn)
                {
                    fbEffect.play(volume);
                    break;
                }
                
            case 1:
                 
                if(soundOn)
                {
                    ibEffect.play(volume);
                    break;
                }
                
            case 2:
                
                if(soundOn)
                {
                    if(Player.getHP() < 1)
                    {
                        healEffect.play(volume);
                    }
                    break;
                }

            case 3:
                
                if(soundOn)
                {
                    meteorEffect.play(volume);
                    break;
                }
                
            default:
                System.out.println("no effect found");
                break;
        }
    }
    
    public void setVolume(float v)
    {
        if(v >=0 && v <=1)
            this.volume=v;
    }
    
    public float getVolume()
    {
        if(soundOn)
            return volume;
        else
            return 0f;
    }
    
    public void dispose()
    {
        fbEffect.dispose();
        ibEffect.dispose();
        healEffect.dispose();
        meteorEffect.dispose();
    }
    
    public boolean isSoundOn()
    {
        return soundOn;
    }
    
    public void setSoundOn(boolean f)
    {
        soundOn=f;
    }
    
    public static ManagerSound getInstance()
    {
        if(INSTANCE == null)
            INSTANCE = new ManagerSound();
        return INSTANCE;
    }
}
