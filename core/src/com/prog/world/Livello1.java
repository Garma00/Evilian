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
import com.prog.entity.SpellFactory;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Livello1 extends Livello implements Screen{
    private final LevelContactListener lcl;
    private Texture tex, tex2, tex3, tex4;
    
    public Livello1(float gravity, boolean Sleep, String path, float cameraWidth, float cameraHeight,float uiWidth,float uiHeight, Evilian game, boolean resume) throws IOException
    {
        super(gravity, Sleep, path, cameraWidth, cameraHeight,uiWidth,uiHeight,game);
        lcl=new LevelContactListener();
        getWorld().setContactListener(lcl);
        //prendo i poligoni della getMap()pa e li inserisco nel mondo
        parseCollisions(getMap().getLayers().get("Collision_layer").getObjects());
        this.resume = resume;
        
        //se stiamo riprendendo il gioco
        if(this.resume)
        {
            StateContainer playerContainer = caricaStatoPlayer();
            if(playerContainer == null)
                p = new Player(50, 150, 1f);
            else
                p=new Player(playerContainer.getPos().x * Evilian.PPM, playerContainer.getPos().y * Evilian.PPM, playerContainer.getHp());
            p=new Player(playerContainer.getPos().x * Evilian.PPM, playerContainer.getPos().y * Evilian.PPM, playerContainer.getHp());
            entities.add(p);
            
            //instanzio n nemici 
            Array<StateContainer> arr = caricaStatoNemico();
            ef.addEnemies(arr);
            //pulisci tutte le magie
            SpellFactory.getInstance().clearActiveSpells();
        }
        else
        {
            p = new Player(3000, 800, 1f);
            entities.add(p);
            //carico le posizioni dei nemici
            super.parseEnemiesSpawnPoints(getMap().getLayers().get("enemy_spawn").getObjects());
            ManagerMusic.getInstance().selectMusic(1);

        }
        
        //bisogna distruggere il mouse altrimenti il mouse nel livello1 avrebbe la gravita' applicata essendo un body
        mouse.getBody().setActive(false);
        
        
        //diamo un po' di zoom alla telecamera per un gameplay migliore
        getCam().zoom-=0.5;
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
        addToGPTime(f);
        //setto la posizione della camera per seguire il player
        super.adjustCameraToPlayer();
        getCam().update();
        getLevelUI().update();
        batch.setProjectionMatrix(getCam().combined);
        
        //se il player non e' vivo cambio la schermata al gameover
        if(!p.isAlive() || p.isLevelCompleted())
            try {
                super.endLevel();
                ef.clearEnemies();
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
        getLevelUI().draw();
        
        getWorld().step(1/60f,6,2);
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
        //prima renderizzo la getMap()pa e poi il player o altre cose
        getMapRenderer().setView(getCam());
        getMapRenderer().render();
        //guardo entities e renderizzo
        batch.begin();
        for(Entity e:entities)
            e.draw();
        //display tutorial
        if(getGameplayTime() <= 12 && !resume)
            tutorial();
        ef.draw();
        
        batch.end();
        //getDebug().render(getWorld(), cam.combined);
    }
    
    private void tutorial()
    {
        Rectangle r=p.getPos();
        if(getGameplayTime() < 3)
            batch.draw(tex, r.x - 0.75f, r.y + 0.50f, 1.5f, 0.5f);
        else if(getGameplayTime() >= 3 && getGameplayTime() < 6)
            batch.draw(tex2, r.x - 0.75f, r.y + 0.50f, 1.5f, 0.5f);
        else if(getGameplayTime() >= 6 && getGameplayTime() < 9)
            batch.draw(tex3, r.x - 0.75f, r.y + 0.50f, 1.5f, 0.5f);
        else
            batch.draw(tex4, r.x - 0.75f, r.y + 0.50f, 1.5f, 0.5f);
    }
}
