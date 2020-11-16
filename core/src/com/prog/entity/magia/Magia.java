package com.prog.entity.magia;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.prog.entity.Entity;
import static com.prog.evilian.Evilian.batch;
import com.prog.world.Livello;

public abstract class Magia extends Entity implements Poolable{
    
    float potenza;
    Vector2 impulso;
    float angle;
    long COOLDOWN;
    boolean alive;
    long time;
    //lastlaunch dentro la magia e' solo per i buff
    long lastLaunch;
    
    public Magia()
    {
        super();
    }
    
    public abstract void init(Vector2 position, Vector2 impulso);
    
    @Override
    public abstract void update(float delta);

    @Override
    public void handleInput() {
    }

    @Override
    public void draw()
    {
        Rectangle r=getPos();
        if(alive)
            if(anim!=null)
            {
                TextureAtlas.AtlasRegion region = anim.getKeyFrame(animationTime);
                //System.out.println("player in:"+body.getPosition());
                //System.out.println(pos.x+"\t"+pos.y);
                batch.draw(region,r.x,r.y,r.width/2,r.height/2,r.width,r.height,(flipX?-1:1)*1,(flipY?-1:1)*1,angle);
            }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void reset() {
        Body b=getBody();
        
        alive=true;
        //da rivedere in futuro
        if(!Livello.getWorld().isLocked())
            if(b != null)
                Livello.getWorld().destroyBody(b);
        lastLaunch=0;
        time=0;
    }
    
    public void setAlive(boolean f)
    {
        alive=f;
    }
}
