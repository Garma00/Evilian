package com.prog.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import com.prog.world.Livello;

public class EnemyB extends Enemy{
    private final static Animation<TextureAtlas.AtlasRegion> walking=new Animation<>(1/10f,Livello.getAtlas().findRegions("king_pig_run"),Animation.PlayMode.LOOP);
    
    @Override
    public void update(float delta) {
        Body b=getBody();
        Rectangle r=getPos();
        //mando in avanti l'animazione
        addToAnimationTime(delta);
        
        setPos((b.getPosition().x)-(r.width/2),(b.getPosition().y)-(r.height/2));
        
        if(getSpeed() < 0.6f)
            setSpeed(getSpeed()+0.2f * delta);
        
        if(getWalk())
        {
            b.setLinearVelocity(-getSpeed(),b.getLinearVelocity().y);
            this.setFlipX(false);
        }
        else
        {
            b.setLinearVelocity(getSpeed(),b.getLinearVelocity().y);
            this.setFlipX(true);
        }
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void draw() {
        Rectangle r=getPos();
        if(getAnim()!=null)
        {
            TextureAtlas.AtlasRegion region = getAnim().getKeyFrame(getAnimationTime());
            batch.draw(region, r.x,r.y - 0.03f,r.width/2,r.height/2, r.width,r.height,(getFlipX()?-1:1)*1,(getFlipY()?-1:1)*1,0);
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void reset() {
        Body b=getBody();
        //setto la vita uguale a 1 e pronto per essere rispawnato
        //NOTA: il nemico non viene spawnato affinche' non verra' settato alive=true nella init successiva
        setLife(1f);
        if(!Livello.getWorld().isLocked())
            if(b != null)
            {
                Livello.getWorld().destroyBody(b);
            }
    }
    
    @Override
    void init(Vector2 loadingPosition, float hp)
    {
        Body b;
        Rectangle r=getPos();
        
        setAlive(true);
        setLife(hp);
        setPos(loadingPosition.x * Evilian.PPM, loadingPosition.y * Evilian.PPM, 19f,22f);
        createBody(r.x,r.y,r.width,r.height,1,"enemyB",1f,0f,0f,(short)32,(short)(4|8|16|64));
        b=getBody();
        this.attachFixture(b, new Vector2(-0.1f,-0.15f), true, "enemyLeftFoot", 5, 3, 0, 0, 0);
        this.attachFixture(b, new Vector2(0.1f,-0.15f),  true, "enemyRightFoot", 5, 3, 0, 0, 0);
        setPosWidth(r.width/Evilian.PPM);
        setPosHeight(r.height/Evilian.PPM);
        setWalkLeft(true);
        //nello sprite il nemico e' rivolto gia' verso sinistra
        this.setFlipX(false);
        setAnim(walking);
        setSpeed(0.6f);
    }
}
