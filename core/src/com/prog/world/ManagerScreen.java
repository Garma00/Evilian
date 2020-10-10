package com.prog.world;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.prog.entity.Entity;
import com.prog.evilian.Evilian;
import static com.prog.world.Livello.world;

public class ManagerScreen 
{
    //valori di index con screen corrispondenti
    //main menu default
    //gioca     1
    //opzioni   2
    public static int index;
    public final static ManagerScreen MANAGER_SCREEN = new ManagerScreen();
    
    public ManagerScreen()
    {
        index = 0;
    }
    
    public void changeScreen(Array<Entity> entities, Evilian game)
    {
        switch(index)
        {
            case 1:
                if(clear(entities))
                    game.setScreen(new Livello1(-9, false, "map2.tmx", game.SCREEN_WIDTH, game.SCREEN_HEIGHT, game));
                else
                    return;
                break;
            case 2:
                if(clear(entities))
                    game.setScreen(new Opzioni(game.SCREEN_WIDTH, game.SCREEN_HEIGHT, game));
                else
                    return;
                break;
                
            default:
                if(clear(entities))
                    game.setScreen(new MainMenu(game.SCREEN_WIDTH, game.SCREEN_HEIGHT, game));
                else
                    return;
                break;
                
                
        }
                
    }

    public boolean clear(Array<Entity> entities)
    {
        boolean  done = false;
        
        if(!world.isLocked())
            for(Entity e : entities)
            {
                done = true;
                //System.out.println("entrato" + e.body);
                e.body.setActive(false);
                world.destroyBody(e.body);
            }

        entities.clear();
        return done;
    }
    
}
