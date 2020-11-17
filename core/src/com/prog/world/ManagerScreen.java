package com.prog.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.prog.entity.Entity;
import com.prog.evilian.Evilian;
import static com.prog.world.Livello.getDebug;
import static com.prog.world.Livello.getWorld;
import java.io.IOException;

public final class ManagerScreen 
{
    //valori di index con screen corrispondenti
    //main menu default
    //gioca     1
    //opzioni   2
    private static int index;
    private static ManagerScreen INSTANCE = null;
    
    private ManagerScreen()
    {
        
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
        
        
        if(!getWorld().isLocked())
        {
            //entities e' null quando passiamo da endLevel a main menu
            if(entities != null)
            {
                for(Entity e : entities)
                {
                    Body b=e.getBody();
                    done = true;
                    //System.out.println("entrato" + e.body);
                    b.setActive(false);
                    getWorld().destroyBody(b);
                }

                entities.clear();
            }
            
            else
                done = true;
            
            getDebug().dispose();
            getWorld().dispose();
        }
            return done;
    }
    
    
    public static void setIndex(int i)
    {
        index=i;
    }
    
    public static ManagerScreen getInstance()
    {
        if(INSTANCE == null)
            INSTANCE = new ManagerScreen();
        return INSTANCE;
    }
}
