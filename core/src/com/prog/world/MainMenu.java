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

//dichiara una istanza di custom contact listener
public class MainMenu extends Livello implements Screen
{
    private MenuContactListener c;
    private Texture bg;
    private static final ManagerVfx mvfx=ManagerVfx.getInstance();
    
    public MainMenu(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian game)
    {
        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, game);
        
        c = new MenuContactListener();//istanzio il contactlistener
        getWorld().setContactListener(c);

        entities.add(mouse);
        entities.add(new Button(root.getScreenWidth() / 2 , root.getScreenHeight() / 2 , 150, 50, "gioca", "images/gioca.png", false));
        entities.add(new Button(root.getScreenWidth() / 2 , root.getScreenHeight() / 4 , 150, 50, "opzioni","images/opzioni.png", false));
        bg = new Texture("images/bg2.png");
        //0 = main theme
        ManagerMusic.getInstance().selectMusic(0);
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
        
        getCam().update();
        batch.setProjectionMatrix(getCam().combined);
        
        mouse.handleInput();
        //se il mouse collide con qualche button
        if(c.getCollided())
        {
            super.dispose();
            super.changeScreenTo();
        }

        for(Entity e:entities)
            e.update(f);
        
        draw();
        getWorld().step(1/60f, 6, 2);
       
    }

    public void draw()
    {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mvfx.initCapture();
        batch.begin();
        batch.draw(bg, 0, 0, root.getScreenWidth()/Evilian.PPM, root.getScreenHeight()/Evilian.PPM);
        batch.end();
        mvfx.endCapture();
        mvfx.render();
        
        batch.begin();
        for(Entity e: entities)
            e.draw();
        batch.end();
        
        //getDebug().render(getWorld(), getCam().combined);
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
}
