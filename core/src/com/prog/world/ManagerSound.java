package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

public final class ManagerSound 
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
    private Array<Sound> sounds;
    
    private ManagerSound()
    {
        sounds = new Array<Sound>();
        sounds.add(fbEffect);
        sounds.add(ibEffect);
        sounds.add(healEffect);
        sounds.add(meteorEffect);
    }
    
    public void addSound(String path)
    {
        sounds.add(Gdx.audio.newSound(Gdx.files.internal(path)));
    }
    
    public void selectSound(int sound)
    { 
        if(sound >= sounds.size)
            return;
        if(soundOn)
            sounds.get(sound).play();
    }
    
    public void dispose()
    {
        fbEffect.dispose();
        ibEffect.dispose();
        healEffect.dispose();
        meteorEffect.dispose();
    }
    
    public boolean isSoundOn()          {return soundOn;}
    public void setSoundOn(boolean f)   {soundOn=f;}
    
    protected void setVolume(float v)
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
    
    public static ManagerSound getInstance()
    {
        if(INSTANCE == null)
            INSTANCE = new ManagerSound();
        return INSTANCE;
    }
}
