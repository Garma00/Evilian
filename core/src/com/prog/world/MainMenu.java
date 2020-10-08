package com.prog.world;
//bottoni con body, anche il mouse, world per aggiungere questi ultimi.

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.prog.collision.MenuContactListener;
import com.prog.entity.Button;
import com.prog.entity.Entity;
import com.prog.entity.Mouse;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;


//dichiara una istanza di custom contact listener
public class MainMenu extends Livello implements Screen
{
    MenuContactListener c;
    Texture bg;
    Mouse m;
    
    public MainMenu(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian game)
    {
        
        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, game);
        c = new MenuContactListener();//istanzio il contactlistener
        world.setContactListener(c);
        m = new Mouse(cam);
        entities.add(m);
        entities.add(new Button(root.SCREEN_WIDTH / 2 -75, root.SCREEN_HEIGHT / 2 - 25, 150, 50, "gioca"));
        entities.add(new Button(root.SCREEN_WIDTH / 2 -75, root.SCREEN_HEIGHT / 4 - 25, 150, 50, "opzioni"));
        bg = new Texture("bg.png");
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
    
        //il metodo draw sta sopra il debug perchè se lo metto dopo i body vengono coperti dalla 
        //texture di bg ed è scomodo, la foto di sfondo l'ho presa random
        batch.begin();
        draw();
        batch.end();
        
        debug.render(world, cam.combined);
        world.step(1/60f, 6, 2);
       
    }

    public void clear()
    {
        for(Entity e : entities)
            if(!world.isLocked())
            {
                //System.out.println("entrato" + e.body);
                e.body.setActive(false);
                world.destroyBody(e.body);
            }

        entities.clear();
    }
    
    public void changeScreen()
    {
        if(c.index == 1)
        {
            //adesso la pulizia la fa la funzione clear per riscrivere meno codice
            clear();
            //cambio screen dopo aver pulito tutto
            root.setScreen(new Livello1(-9, false, "map.tmx", root.SCREEN_WIDTH, root.SCREEN_HEIGHT, root));
        }
        else if(c.index == 2)
        {
            clear();
            //se viene cliccato opzioni setto lo screen ad opzioni dopo aver pulito
            root.setScreen(new Opzioni(root.SCREEN_WIDTH, root.SCREEN_HEIGHT, root));

        }
            
    }
    //chiamata tra batch begin e batch end;
    public void draw()
    {
        batch.draw(bg, 0, 0, root.SCREEN_WIDTH, root.SCREEN_HEIGHT);
    
    }
    
    @Override
    public void resize(int i, int i1) {
        cam.setToOrtho(false, i, i1);
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
