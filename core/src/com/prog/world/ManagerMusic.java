package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;


public class ManagerMusic 
{
    private static ManagerMusic INSTANCE = null;
    private Array<Music> songs;
    private Music musica;
    private boolean musicOn;
    
    private ManagerMusic()
    {
        songs = new Array<Music>();
        songs.add(Gdx.audio.newMusic(Gdx.files.internal("music/main_theme.mp3")));
        songs.add(Gdx.audio.newMusic(Gdx.files.internal("music/level1.mp3")));
        musica = songs.first();
        musicOn = true;
    }
    
    public void addMusic(String path)
    {
        Music m = Gdx.audio.newMusic(Gdx.files.internal(path));
        songs.add(m);
    }
    
    public void selectMusic(int i)
    {
        if(i >= songs.size || !musicOn)
            return;
        if(musica.isPlaying())
            musica.stop();
        musica = songs.get(i);
        musica.setVolume(0.4f);
        musica.setLooping(true);
        musica.play();
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
    
    public void setMusicOn(boolean b)
    {
        this.musicOn = b;
    }

    public static ManagerMusic getInstance()
    {
        if(INSTANCE == null)
            INSTANCE = new ManagerMusic();
        return INSTANCE;
    }
    
}
