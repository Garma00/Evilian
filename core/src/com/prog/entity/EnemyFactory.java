package com.prog.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.prog.evilian.Evilian;

public class EnemyFactory {
    static Player p;
    long time,lastCall;
    static Array<Enemy> activeEnemies=new Array<Enemy>();
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
    
    /*
    private final Pool<EnemyB> EnemyBPool = new Pool<EnemyB>() {
        @Override
        protected EnemyB newObject() {
            return new EnemyB();
        }
    };*/
    
    //chiamala quando vuoi creare una spell
    public Enemy createEnemy(EnemyType t){
       switch(t){
         case A:
            return EnemyAPool.obtain();
         /*case B:
             return EnemyBPool.obtain();
         */
        }
        //altrimenti ritorno null
        return null;
    }
    
     //chiamala quando vuoi distruggere una spell
    public void destroyEnemy(Enemy e){
       if(e instanceof EnemyA){
         EnemyAPool.free((EnemyA)e);
       }
       /*else if(e instanceof EnemyB){
         EnemyBPool.free((EnemyB)e);
       }*/
    }
    
    public static Vector2 getPlayerPos()
    {
        return p.body.getWorldCenter().scl(Evilian.PPM);
    }
    
    public void update(float delta)
    {
        //time=TimeUtils.millis();
        
        if(time - lastCall > 10000)
        {
            lastCall=time;
            Enemy e=createEnemy(EnemyType.A);
            e.init();
            activeEnemies.add(e);
        }
        
        for(Enemy e : activeEnemies)
            if(!e.alive)
            {
                destroyEnemy(e);
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
    
}
