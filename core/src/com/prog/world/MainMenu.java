package com.prog.world;
//bottoni con body, anche il mouse, world per aggiungere questi ultimi.

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Texture;

import com.prog.collision.MenuContactListener;
import com.prog.entity.Button;
import com.prog.entity.Entity;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.world.ManagerScreen.MANAGER_SCREEN;
import static com.prog.evilian.Evilian.MANAGER_MUSIC;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//dichiara una istanza di custom contact listener
public class MainMenu extends Livello implements Screen
{
    MenuContactListener c;
    Texture bg;
    
    public MainMenu(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian game)
    {
        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, game);
        
        c = new MenuContactListener();//istanzio il contactlistener
        world.setContactListener(c);

        entities.add(mouse);
        entities.add(new Button(root.SCREEN_WIDTH / 2 , root.SCREEN_HEIGHT / 2 , 150, 50, "gioca", "images/gioca.png", false));
        entities.add(new Button(root.SCREEN_WIDTH / 2 , root.SCREEN_HEIGHT / 4 , 150, 50, "opzioni","images/opzioni.png", false));
        bg = new Texture("images/menu.png");
        MANAGER_MUSIC.selectMusic(1);
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
        if(c.collided)
        {
            System.out.println("Collisione fra mouse e opzioni");

            super.dispose();
            try {
                MANAGER_SCREEN.changeScreen(entities, root);
            } catch (IOException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        for(Entity e:entities)
            e.update(f);
        
        draw();
        world.step(1/60f, 6, 2);
       
    }

    //chiamata tra batch begin e batch end;
    //questo metodo potrebbe essere astratto ed essere riscritto per opzioni, salva ecc
    public void draw()
    {

        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mvfx.initCapture();
        //il metodo draw sta sopra il debug perchè se lo metto dopo i body vengono coperti dalla 
        //texture di bg ed è scomodo, la foto di sfondo l'ho presa random
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
        //camvp.update(width, height);
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
}
