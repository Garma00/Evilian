package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.prog.collision.MenuContactListener;
import com.prog.evilian.Evilian;

public class Livello1 extends Livello implements Screen{
    
    
    public Livello1(float gravity, boolean Sleep, String path, int cameraWidth, int cameraHeight, Evilian game)
    {
        super(gravity, Sleep, path, cameraWidth, cameraHeight,game);
        world.setContactListener(new MenuContactListener());
        
        //prendo i poligoni della mappa e li inserisco nel mondo
        parseCollisions(world,map.getLayers().get("Collision_layer").getObjects());
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
        
        //handleinput del livello
        handleInput();
        
        //guardo entities e faccio gli update
        //DA FARE
        
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //guardo entities e renderizzo cose
        //DA FARE
        mapRenderer.setView(cam);
        mapRenderer.render();
        
        debug.render(world, cam.combined);
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
    
    public void handleInput()
    {
        if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
        {
            dispose();
            root.dispose();
            Gdx.app.exit();
        }
    }
}
