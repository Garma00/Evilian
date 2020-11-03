package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.prog.collision.OpzioniContactListener;
import com.prog.entity.Button;
import com.prog.entity.Entity;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.world.Livello.world;
import static com.prog.world.ManagerScreen.MANAGER_SCREEN;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Opzioni extends Livello implements Screen {

    OpzioniContactListener c;
    Texture bg;
    public Opzioni(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian root)
    {
        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, root);
        c = new OpzioniContactListener();
        entities.add(mouse);
        bg = new Texture("images/menu.png");
        world.setContactListener(c);
        
        entities.add(new Button(root.SCREEN_WIDTH / 2, root.SCREEN_HEIGHT -75, 150, 50, "riprendi", "images/riprendi.png", false));
        entities.add(new Button(root.SCREEN_WIDTH / 2, root.SCREEN_HEIGHT -150, 150, 50, "musica", "images/musica.png", true));
        entities.add(new Button(0 + 185, 0 + 50, 150, 50, "MainMenu", "images/indietro.png", false));
        entities.add(new Button(root.SCREEN_WIDTH / 2, root.SCREEN_HEIGHT -225, 150, 50, "sound", "images/sound.png", true));
        mvfx.enableBlend(true);
        mvfx.addEffect(ManagerVfx.GBLUR_EFFECT);
        mvfx.addEffect(ManagerVfx.BLOOM_EFFECT);
    }
    
    

    @Override
    public void show() {
    }

    @Override
    public void render(float f)
    {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        mouse.handleInput();
        
        //qui ontrolliamo se c'è una collisione nello screen opzioni, ma se c'è una collisione con il
        //bottone musica viene comuqneu chiamata la change screen che non essendo modificato l'index setta lo screen
        //nuovamente ad opzioni quindi credo che questo metodo vada rivisto
        if(c.collided)
        {
            //System.out.println("collided in opzioni");
            super.dispose();
            try {
                MANAGER_SCREEN.changeScreen(entities, root);
            } catch (IOException ex) {
                Logger.getLogger(Opzioni.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(Entity e:entities)
            e.update(f);
            
        draw();
        world.step(1/60f, 6, 2);
    
        
    }

    public void draw()
    {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        mvfx.initCapture();
        batch.begin();
        batch.draw(bg, 0, 0, root.SCREEN_WIDTH/Evilian.PPM, root.SCREEN_HEIGHT/Evilian.PPM);
        batch.end();
        mvfx.endCapture();
        mvfx.render();
        
        batch.begin();
        for(Entity e: entities)
            e.draw();
        batch.end();
        
        debug.render(world, cam.combined);
    }
    
    @Override
    public void resize(int width, int height) {
        camvp.update(width, height);
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
    public void dispose() {
    }
    
}
