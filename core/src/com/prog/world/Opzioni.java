package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.prog.collision.OpzioniContactListener;
import com.prog.entity.Button;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.world.Livello.world;
import static com.prog.world.ManagerScreen.MANAGER_SCREEN;

public class Opzioni extends Livello implements Screen {

    OpzioniContactListener c;
    public Opzioni(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian root)
    {
        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, root);
        c = new OpzioniContactListener();
        entities.add(mouse);
        
        world.setContactListener(c);
        
        entities.add(new Button(root.SCREEN_WIDTH / 2 -75, root.SCREEN_HEIGHT / 2 - 25, 150, 50, "riprendi"));
        entities.add(new Button(root.SCREEN_WIDTH / 2 -75, root.SCREEN_HEIGHT / 4 - 25, 150, 50, "MainMenu"));

        
    }
    
    

    @Override
    public void show() {
    }

    @Override
    public void render(float f)
    {
        cam.update();
        mouse.handleInput();
        if(c.collided)
        {
            System.out.println("collided in opzioni");
            MANAGER_SCREEN.changeScreen(entities, root);
        }
            
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    

        batch.begin();

        batch.end();
        
        debug.render(world, cam.combined);
        world.step(1/60f, 6, 2);
    
        
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
