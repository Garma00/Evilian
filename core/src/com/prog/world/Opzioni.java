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

public class Opzioni extends Livello implements Screen {

    OpzioniContactListener c;
    Texture bg;
    public Opzioni(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian root)
    {
        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, root);
        c = new OpzioniContactListener();
        entities.add(mouse);
        bg = new Texture("menu.png");
        world.setContactListener(c);
        
        entities.add(new Button(root.SCREEN_WIDTH / 2, root.SCREEN_HEIGHT / 2, 150, 50, "riprendi", "riprendi.png"));
        entities.add(new Button(root.SCREEN_WIDTH / 2, root.SCREEN_HEIGHT / 4, 150, 50, "musica", "musica.png"));
        //entities.add(new Button(root.SCREEN_WIDTH / 2, root.SCREEN_HEIGHT / 4, 150, 50, "musica", "musica_off.png"));
        entities.add(new Button(0 + 185, 0 + 50, 150, 50, "MainMenu", "indietro.png"));
        
        
        
    }
    
    

    @Override
    public void show() {
    }

    @Override
    public void render(float f)
    {
        cam.update();
        mouse.handleInput();
        
        //qui ontrolliamo se c'è una collisione nello screen opzioni, ma se c'è una collisione con il
        //bottone musica viene comuqneu chiamata la change screen che non essendo modificato l'index setta lo screen
        //nuovamente ad opzioni quindi credo che questo metodo vada rivisto
        if(c.collided)
        {
            //System.out.println("collided in opzioni");
            MANAGER_SCREEN.changeScreen(entities, root);
        }
            
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    

        batch.begin();
        draw();
        batch.end();
        
        debug.render(world, cam.combined);
        world.step(1/60f, 6, 2);
    
        
    }

    public void draw()
    {
        batch.draw(bg, 0, 0, root.SCREEN_WIDTH, root.SCREEN_HEIGHT);
        for(Entity e: entities)
        {
            e.draw();
        }
    }
    
    @Override
    public void resize(int i, int i1) {
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
