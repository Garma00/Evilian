package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.prog.collision.LevelContactListener;
import com.prog.entity.Entity;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.evilian.Evilian.MANAGER_MUSIC;

public class Livello1 extends Livello implements Screen{
    Player p;
    float delta;
    LevelContactListener lcl;
    
    public Livello1(float gravity, boolean Sleep, String path, int cameraWidth, int cameraHeight, Evilian game)
    {
        super(gravity, Sleep, path, cameraWidth, cameraHeight,game);
        lcl=new LevelContactListener();
        world.setContactListener(lcl);

        //prendo i poligoni della mappa e li inserisco nel mondo
        parseCollisions(world,map.getLayers().get("Collision_layer").getObjects());
        
        //ho bisgno di passare il listener come parametro per avere il flag inAir
        p=new Player(lcl, mouse);
        
        entities.add(p);
        
        //bisogna distruggere il mouse altrimenti il mouse nel livello1 avrebbe la gravit� applicata essendo un body
        world.destroyBody(mouse.body);
        
        MANAGER_MUSIC.selectMusic(2);
        
        //diamo un po' di zoom alla telecamera per un gameplay migliore
        cam.zoom-=0.5;
        mvfx.removeAllEffects();
        mvfx.addEffect(ManagerVfx.BLOOM_EFFECT);
        mvfx.enableBlend(true);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        
        cam.position.set(Math.max(p.pos.x+0.5f,2f), Math.max(p.pos.y+0.2f,1.7f),0f);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        
        //handleinput entita'
        for(Entity e:entities)
            e.handleInput();
        
        
        //handleinput del livello
        handleInput();
        
        //guardo entities e faccio gli update
        for(Entity e:entities)
            e.update(f);
        
        //draw
        draw();
        
        world.step(1/60f,6,2);
    }

    @Override
    public void resize(int width, int height) {
        camvp.update(width,height);
        mvfx.resize(width, height);
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
    
    @Override
    public void dispose()
    {
        super.dispose();
    }
    
    public void draw()
    {   
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //prima renderizzo la mappa e poi il player o altre cose
        mapRenderer.setView(cam);
        mapRenderer.render();
        //guardo entities e renderizzo cose
        batch.begin();
        for(Entity e:entities)
            e.draw();
        batch.end();        
        debug.render(world, cam.combined);
    }

}
