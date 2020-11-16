package com.prog.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.prog.world.StateContainer;

public class EnemyFactory {
    //l'array va reso publico perchè serve conoscere il numero di elementi e iterarli per salvare lo stato
    private Array<Enemy> activeEnemies=new Array<Enemy>();
    private static EnemyFactory INSTANCE=null;
    
    private EnemyFactory()
    {
    }
    
    //da aggiungere altri tipi
    public enum EnemyType {
        A,
        B
    };
    
    //chiamala quando vuoi creare una spell
    public Enemy createEnemy(EnemyType t){
       switch(t){
         case A:
            return Pools.obtain(EnemyA.class);
         case B:
             return Pools.obtain(EnemyB.class);
        }
        //altrimenti ritorno null
        return null;
    }
    
     //chiamala quando vuoi distruggere una spell
    public void destroyEnemy(Enemy e)
    {
       Pools.free(e);
    }
    
    public void update(float delta)
    {
        for(Enemy e : activeEnemies)
            if(!e.alive)
            {
                destroyEnemy(e);
                activeEnemies.removeValue(e, true);
            }else
            {
                e.update(delta);
            }
    }
    
    public void draw()
    {
        for(Enemy e : activeEnemies)
            e.draw();
    }
    
    public void addEnemy(float x,float y,float hp,EnemyType type)
    {
        Vector2 tmp=new Vector2();
        Enemy e=null;
        switch (type)
        {
            case A:
                e = createEnemy(EnemyType.A);
                break;
            case B:
                e = createEnemy(EnemyType.B);
                break;
        }
        e.init(tmp.set(x,y),hp);
        activeEnemies.add(e);
    }
    
    /*carico i nemici con i dati dal file*/
    public void addEnemies(Array<StateContainer> arr)
    {
        for(StateContainer s: arr)
        {
            switch(s.getType())
            {
                case "E":
                    Enemy e = createEnemy(EnemyType.A);
                    e.init(s.getPos(), s.getHp());
                    activeEnemies.add(e);
                    break;
                case "E2":
                    Enemy e2 = createEnemy(EnemyType.B);
                    e2.init(s.getPos(),s.getHp());
                    activeEnemies.add(e2);
                    break;
            }
        }
    }
    
    public int getSize()
    {
        return activeEnemies.size;
    }
    
    public Array<Enemy> getActiveEnemies()
    {
        return activeEnemies;
    }
    
    public static EnemyFactory getInstance()
    {
        if(INSTANCE==null)
            INSTANCE=new EnemyFactory();
        return INSTANCE;
    }
}
