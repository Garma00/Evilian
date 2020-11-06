package com.prog.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.prog.evilian.Evilian;
import com.prog.world.StateContainer;

public class EnemyFactory {
    static Player p;
    long time,lastCall;
    //l'array va reso publico perchè serve conoscere il numero di elementi e iterarli per salvare lo stato
    public Array<Enemy> activeEnemies=new Array<Enemy>();
    //da aggiungere altri tipi
    public enum EnemyType {
        A,
        B
    };
    public EnemyFactory(Player p)
    {
        this.p=p;
        time=TimeUtils.millis();
    }
    
    private final Pool<Enemy> EnemyAPool = new Pool<Enemy>() {
        @Override
        protected Enemy newObject() {
            return new EnemyA();
        }
    };
    
    
    private final Pool<EnemyB> EnemyBPool = new Pool<EnemyB>() {
        @Override
        protected EnemyB newObject() {
            return new EnemyB();
        }
    };
    
    //chiamala quando vuoi creare una spell
    public Enemy createEnemy(EnemyType t){
       switch(t){
         case A:
            return EnemyAPool.obtain();
         case B:
             return EnemyBPool.obtain();
        }
        //altrimenti ritorno null
        return null;
    }
    
     //chiamala quando vuoi distruggere una spell
    public void destroyEnemy(Enemy e){
       if(e instanceof EnemyA){
         EnemyAPool.free((EnemyA)e);
       }
       else if(e instanceof EnemyB){
         EnemyBPool.free((EnemyB)e);
       }
    }
    
    public static Vector2 getPlayerPos()
    {
        return p.body.getWorldCenter().scl(Evilian.PPM);
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
    
    public void addEnemy()
    {
        Enemy e = createEnemy(EnemyType.A);
        e.init();
        activeEnemies.add(e);
    }
    
    public void addEnemy(float x,float y,float hp)
    {
        Vector2 tmp=new Vector2();
        Enemy e = createEnemy(EnemyType.A);
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
}
