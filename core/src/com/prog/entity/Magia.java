package com.prog.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;
import static com.prog.evilian.Evilian.batch;
import com.prog.world.Livello;

public abstract class Magia extends Entity implements Poolable{
    
    private float potenza;
    private float angle;
    private boolean alive;
    private long time;
    private long COOLDOWN;
    
    //lastlaunch dentro la magia e' solo per i buff
    protected long lastLaunch;
    
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
            if(getAnim()!=null)
            {
                TextureAtlas.AtlasRegion region = getAnim().getKeyFrame(getAnimationTime());
                //System.out.println("player in:"+body.getPosition());
                //System.out.println(pos.x+"\t"+pos.y);
                batch.draw(region,r.x,r.y,r.width/2,r.height/2,r.width,r.height,(getFlipX()?-1:1)*1,(getFlipY()?-1:1)*1,angle);
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
    
    protected float getPower()          {return this.potenza;}
    protected void setPower(float f)    {this.potenza=f;}
    protected void setAngle(float f)    {this.angle=f;}
    protected boolean isAlive()         {return this.alive;}
    public void setAlive(boolean b)     {this.alive=b;}
    protected long getTime()            {return this.time;}
    protected void setTime(long l)      {this.time=l;}
    protected void setCoolDown(long l)  {this.COOLDOWN=l;}
    protected long getCoolDown()        {return this.COOLDOWN;}
}
