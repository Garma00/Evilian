package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.prog.collision.LevelContactListener;
import com.prog.entity.Entity;
import com.prog.entity.Player;
import com.prog.entity.magia.SpellFactory;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Livello1 extends Livello implements Screen{
    LevelContactListener lcl;
    Texture tex, tex2, tex3, tex4;
    
    public Livello1(float gravity, boolean Sleep, String path, float cameraWidth, float cameraHeight,float uiWidth,float uiHeight, Evilian game, boolean resume) throws IOException
    {
        super(gravity, Sleep, path, cameraWidth, cameraHeight,uiWidth,uiHeight,game);
        lcl=new LevelContactListener();
        world.setContactListener(lcl);
        //prendo i poligoni della mappa e li inserisco nel mondo
        parseCollisions(map.getLayers().get("Collision_layer").getObjects());
        this.resume = resume;
        
        //se stiamo riprendendo il gioco
        if(this.resume)
        {
            StateContainer playerContainer = caricaStatoPlayer();
            p=new Player(playerContainer.pos.x * Evilian.PPM, playerContainer.pos.y * Evilian.PPM, playerContainer.hp);
            entities.add(p);
            
            //instanzio n nemici 
            Array<StateContainer> arr = caricaStatoNemico();
            ef.addEnemies(arr);
            //pulisci tutte le magie
            SpellFactory.getInstance().clearActiveSpells();
        }
        else
        {
            p = new Player(50, 150, 1f);
            entities.add(p);
            //carico le posizioni dei nemici
            super.parseEnemiesSpawnPoints(map.getLayers().get("enemy_spawn").getObjects());
        }
        
        //bisogna distruggere il mouse altrimenti il mouse nel livello1 avrebbe la gravita' applicata essendo un body
        mouse.getBody().setActive(false);
        
        Evilian.getManagerMusic().selectMusic();
        
        //diamo un po' di zoom alla telecamera per un gameplay migliore
        cam.zoom-=0.5;
        super.loadUI();
        
        //texture per il tutorial
        tex = new Texture("images/tutorial1.png");
        tex2 = new Texture("images/tutorial2.png");
        tex3 = new Texture("images/tutorial3.png");
        tex4 = new Texture("images/tutorial4.png"); 
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        gameplayTime += f;
        //setto la posizione della camera per seguire il player
        super.adjustCameraToPlayer();
        cam.update();
        level_ui.update();
        batch.setProjectionMatrix(cam.combined);
        
        //se il player non e' vivo cambio la schermata al gameover
        if(!p.isAlive() || p.isLevelCompleted())
            try {
                super.endLevel();
            } catch (IOException ex) {
                Logger.getLogger(Livello1.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        //handleinput entita'
        for(Entity e:entities)
            e.handleInput();
        
        //enemyfactory update
        ef.update(f);
        
        try {
            //handleinput del livello
            super.handleInput();
        } catch (IOException ex) {
            Logger.getLogger(Livello1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update entita'
        for(Entity e:entities)
            e.update(f);
        
        //draw
        draw();
        level_ui.draw();
        
        world.step(1/60f,6,2);
    }

    @Override
    public void resize(int width, int height) {
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
        Gdx.gl.glViewport(0,75,800, 525);
        //prima renderizzo la mappa e poi il player o altre cose
        mapRenderer.setView(cam);
        mapRenderer.render();
        //guardo entities e renderizzo
        batch.begin();
        for(Entity e:entities)
            e.draw();
        //display tutorial
        if(gameplayTime <= 12 && !resume)
            tutorial();
        ef.draw();
        
        batch.end();
        debug.render(world, cam.combined);
    }
    
    private void tutorial()
    {
        Rectangle r=p.getPos();
        if(gameplayTime < 3)
            batch.draw(tex, r.x - 0.75f, r.y + 0.50f, 1.5f, 0.5f);
        else if(gameplayTime >= 3 && gameplayTime < 6)
            batch.draw(tex2, r.x - 0.75f, r.y + 0.50f, 1.5f, 0.5f);
        else if(gameplayTime >= 6 && gameplayTime < 9)
            batch.draw(tex3, r.x - 0.75f, r.y + 0.50f, 1.5f, 0.5f);
        else
            batch.draw(tex4, r.x - 0.75f, r.y + 0.50f, 1.5f, 0.5f);
    }
    
    

}
