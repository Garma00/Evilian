package com.prog.entity.magia;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.prog.entity.Entity;
import static com.prog.evilian.Evilian.batch;
import static com.prog.world.Livello.world;



public abstract class Magia extends Entity implements Poolable{
    
    float potenza;
    Vector2 impulso;
    Rectangle pos;
    float angle;
    public long COOLDOWN;
    public boolean alive;
    long time;
    public long lastLaunch;
    
    public abstract void init(Vector2 position, float potenza, Vector2 impulso);
    

    @Override
    public abstract void update(float delta);

    @Override
    public void handleInput() {
    }

    @Override
    public void draw()
    {
        if(alive)
            if(anim!=null)
            {
                TextureAtlas.AtlasRegion region = anim.getKeyFrame(animationTime);
                //System.out.println("player in:"+body.getPosition());
                //System.out.println(pos.x+"\t"+pos.y);
                batch.draw(region,pos.x,pos.y,pos.width/2,pos.height/2,pos.width,pos.height,(flipX?-1:1)*1,(flipY?-1:1)*1,angle);
            }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void reset() {
        alive=true;
        //da rivedere in futuro
        if(!world.isLocked())
            world.destroyBody(this.body);
        lastLaunch=0;
        time=0;
    }
    
}
