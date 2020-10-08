package com.prog.world;
//bottoni con body, anche il mouse, world per aggiungere questi ultimi.

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.prog.collision.MenuContactListener;
import com.prog.entity.Button;
import com.prog.entity.Entity;
import com.prog.entity.Mouse;
import com.prog.evilian.Evilian;


//dichiara una istanza di custom contact listener
public class MainMenu extends Livello implements Screen
{
    MenuContactListener c;
    Mouse m;
    
    public MainMenu(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian game)
    {

        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, game);
        c = new MenuContactListener();//istanzio il contactlistener
        world.setContactListener(c);
        m = new Mouse(cam);
        entities.add(m);
        entities.add(new Button(root.SCREEN_WIDTH / 2 -75, root.SCREEN_HEIGHT / 2 - 25, 150, 50, "gioca"));
       
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f)
    {
        
        cam.update();
        m.handleInput();
        changeScreen();
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
        debug.render(world, cam.combined);
        world.step(1/60f, 6, 2);
    }

    public void changeScreen()
    {
        if(c.index == 1)
        {
            root.setScreen(new Livello1(-9, false, "map.tmx", root.SCREEN_WIDTH, root.SCREEN_HEIGHT, root));
            for(Entity e : entities)
            {
                if(!world.isLocked())
                {
                    System.out.println("entrato" + e.body);
                    e.body.setActive(false);
                    world.destroyBody(e.body);
                }
            }
            /*for(Body b :c.toClean)
                world.destroyBody(b);
            */
            
            entities.clear();
        
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
}
