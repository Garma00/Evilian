package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.prog.collision.CustomContactListener;

public class Livello1 extends Livello implements Screen{
    
    public Livello1(float gravity, boolean Sleep, String path, int cameraWidth, int cameraHeight)
    {
        super(gravity, Sleep, path, cameraWidth, cameraHeight);
        world.setContactListener(new CustomContactListener());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        world.step(1/60f,6,2);
        cam.update();
        //guardo la lista entities e faccio gli handleinput
        //DA FARE
        
        //guardo entities e faccio gli update
        //DA FARE
        
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //guardo entities e renderizzo cose
        //DA FARE
    }

    @Override
    public void resize(int i, int i1) {
        cam.setToOrtho(false, i, i1);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
    
    public void dispose()
    {
        super.dispose();
    }
}
