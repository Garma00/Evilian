package com.prog.world;

import com.badlogic.gdx.utils.Array;
import com.prog.entity.Entity;
import com.prog.evilian.Evilian;
import static com.prog.world.Livello.debug;
import static com.prog.world.Livello.world;
import java.io.IOException;

public class ManagerScreen 
{
    //valori di index con screen corrispondenti
    //main menu default
    //gioca     1
    //opzioni   2
    public static int index;
    private final static ManagerScreen MANAGER_SCREEN = new ManagerScreen();
    
    public ManagerScreen()
    {
        index = 0;
    }
    
    public void changeScreen(Array<Entity> entities, Evilian game) throws IOException
    {
        switch(index)
        {
            case 1:
                //l'ultimo booleano serve per dire se il livello deve leggere lo stato dal file
                //nel primo caso e' messo a false perche' stiamo iniziando una nuova partita
                if(clear(entities))
                    game.setScreen(new Livello1(-9, false, "tsx/map2.tmx", game.getScreenWidth(), 525,800,75, game, false));
                else
                    return;
                break;
            case 2:
                if(clear(entities))
                    game.setScreen(new Opzioni(game.getScreenWidth(), game.getScreenHeight(), game));
                else
                    return;
                break;

            case 3:
                if(clear(entities))                    
                    game.setScreen(new Livello1(-9, false, "tsx/map2.tmx", game.getScreenWidth(), 525,800,75, game, true));
                else
                    return;
                break;  
                
            case 4:
                if(clear(entities))                    
                    game.setScreen(new MainMenu(game.getScreenWidth(), game.getScreenHeight(), game));
                else
                    return;
                break;
                
            case 5:
                if(clear(entities))                    
                    game.setScreen(new EndLevel(game.getScreenWidth(), game.getScreenHeight(), game));
                else
                    return;
                break;
                
                
                
            default:
                if(clear(entities))
                    game.setScreen(new MainMenu(game.getScreenWidth(), game.getScreenHeight(), game));
                else
                    return;
                break;
        }
                
    }

    public boolean clear(Array<Entity> entities)
    {
        boolean  done = false;
            
        
        if(!world.isLocked())
        {
            //entities e' null quando passiamo da endLevel a main menu
            if(entities != null)
            {
                for(Entity e : entities)
                {
                    done = true;
                    //System.out.println("entrato" + e.body);
                    e.body.setActive(false);
                    world.destroyBody(e.body);
                }

                entities.clear();
            }
            
            else
                done = true;
            
            debug.dispose();
            world.dispose();
        }
            return done;
    }
    
    
    public static ManagerScreen getManagerScreen()
    {
        return MANAGER_SCREEN;
    }
}
