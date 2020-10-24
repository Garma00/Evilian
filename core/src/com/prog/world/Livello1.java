package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.prog.collision.LevelContactListener;
import com.prog.entity.Entity;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.evilian.Evilian.MANAGER_MUSIC;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Livello1 extends Livello implements Screen{
    float delta;
    long start, time;
    LevelContactListener lcl;
    
    
    //test tutorial
    Texture tex, tex2, tex3, tex4;
    
    public Livello1(float gravity, boolean Sleep, String path, float cameraWidth, float cameraHeight,float uiWidth,float uiHeight, Evilian game, boolean resume) throws IOException
    {
        super(gravity, Sleep, path, cameraWidth, cameraHeight,uiWidth,uiHeight,game);
        lcl=new LevelContactListener();
        world.setContactListener(lcl);

        //prendo i poligoni della mappa e li inserisco nel mondo
        parseCollisions(world,map.getLayers().get("Collision_layer").getObjects());
        
        //non spostare assolutamente da qui questione di vita o morte 
        //ho bisgno di passare il listener come parametro per avere il flag inAir
        this.resume = resume;
        if(this.resume)
        {
            Vector2 pos = loadState();
            p=new Player(mouse, pos.x, pos.y);
            entities.add(p);
        }
        else
        {
            p = new Player(mouse, 50, 150);
            entities.add(p);
        }
        
        
        
        //bisogna distruggere il mouse altrimenti il mouse nel livello1 avrebbe la gravit� applicata essendo un body
        world.destroyBody(mouse.body);
        
        MANAGER_MUSIC.selectMusic(2);
        
        //diamo un po' di zoom alla telecamera per un gameplay migliore
        cam.zoom-=0.5;
        
        //test UI
        tex=new Texture("images/ui/bg.png");
        tex2=new Texture("images/ui/fireball_2.png");
        
        //cam.translate(0f,-1f);
        //NOTA: METTI GLI ELEMENTI IN ORDINE
        level_ui.add(0,0,800,75,"images/ui/bg.png",UI.ElementType.BACKGROUND);
        //fb
        level_ui.add(300,15,40,40,"images/ui/fireball_2.png",UI.ElementType.FOREGROUND);
        level_ui.add(301,56,38,4,"images/ui/health_only.png", UI.ElementType.FB_BAR);
        level_ui.add(300, 55, 40, 6, "images/ui/skill_bar.png", UI.ElementType.FOREGROUND);
        
        //ib
        level_ui.add(400, 15, 40, 40, "images/ui/iceball.png", UI.ElementType.FOREGROUND);
        level_ui.add(401,56,38,4,"images/ui/health_only.png", UI.ElementType.IB_BAR);
        level_ui.add(400, 55, 40, 6, "images/ui/skill_bar.png", UI.ElementType.FOREGROUND);
        
        //heal
        level_ui.add(500, 15, 40, 40, "images/ui/heal.png", UI.ElementType.FOREGROUND);
        level_ui.add(501,56,38,4,"images/ui/health_only.png", UI.ElementType.H_BAR);
        level_ui.add(500, 55, 40, 6, "images/ui/skill_bar.png", UI.ElementType.FOREGROUND);
        
        
        //meteor
        level_ui.add(600, 15, 40, 40, "images/ui/meteor.png", UI.ElementType.FOREGROUND);
        level_ui.add(601,56,38,4,"images/ui/health_only.png", UI.ElementType.M_BAR);
        level_ui.add(600, 55, 40, 6, "images/ui/skill_bar.png", UI.ElementType.FOREGROUND);
        
        //health
        level_ui.add(56,31,50*3,4*3,"images/ui/health_only.png", UI.ElementType.HEALTH_BAR);
        level_ui.add(56,31,50*3,1*3,"images/ui/health_only_shade.png", UI.ElementType.HEALTH_SHADE);
        level_ui.add(20,25,64*3,8*3,"images/ui/health_bar_empty.png",UI.ElementType.FOREGROUND);
        
        //selector
        level_ui.add(257,25,40,16,"images/ui/sword.png", UI.ElementType.SELECTOR);
        
            
        start = TimeUtils.millis();  
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
        time = TimeUtils.millis();
        cam.position.set(Math.max(p.pos.x+0.5f,2f), Math.max(p.pos.y+0.2f,1.4f),0f);
        cam.update();
        level_ui.update();
        batch.setProjectionMatrix(cam.combined);
        
        //handleinput entita'
        for(Entity e:entities)
            e.handleInput();
        
        
        try {
            //handleinput del livello
            handleInput();
        } catch (IOException ex) {
            Logger.getLogger(Livello1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //guardo entities e faccio gli update
        for(Entity e:entities)
            e.update(f);
        
        //draw
        draw();
        level_ui.draw();
        
        world.step(1/60f,6,2);
    }

    @Override
    public void resize(int width, int height) {
        //da sistemare il discorso del resize(molto confusionario con le viewport)
        //mvfx.resize(width, height);
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
        //guardo entities e renderizzo cose
        batch.begin();
        for(Entity e:entities)
            e.draw();
        if(time - start<= 12000 && !resume)
            tutorial();
        batch.end();
        debug.render(world, cam.combined);
    }
    
    public void tutorial()
    {
                    
        if(time - start < 3000)
        {
            System.out.println("Stampo  prima texture");
                    batch.draw(tex, p.pos.x - 0.75f, p.pos.y + 0.50f, 1.5f, 0.5f);
        }
            
        else if(time - start >= 3000 && time -start < 6000)
            batch.draw(tex2, p.pos.x - 0.75f, p.pos.y + 0.50f, 1.5f, 0.5f);
        else if(time-start >= 6000 && time -start < 9000)
            batch.draw(tex3, p.pos.x - 0.75f, p.pos.y + 0.50f, 1.5f, 0.5f);
        else
            batch.draw(tex4, p.pos.x - 0.75f, p.pos.y + 0.50f, 1.5f, 0.5f);
        System.out.println(time - start);
    }

}
