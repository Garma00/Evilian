package com.prog.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.world.Livello.atlas;
import static com.prog.world.Livello.world;

public class EnemyA extends Enemy{
    private final static Animation<TextureAtlas.AtlasRegion> walking=new Animation<>(1/10f,atlas.findRegions("pig_run"),Animation.PlayMode.LOOP);
    
    @Override
    public void update(float delta) {
        //mando in avanti l'animazione
        this.animationTime+=delta;
        
        pos.x=(body.getPosition().x)-(pos.width/2);
        pos.y=(body.getPosition().y)-(pos.height/2);
        
        if(SPEED < 0.6f)
            SPEED += 0.2f * delta;
        
        if(walkLeft)
        {
            body.setLinearVelocity(-SPEED,body.getLinearVelocity().y);
            this.flipX=false;
        }
        else
        {
            body.setLinearVelocity(SPEED,body.getLinearVelocity().y);
            this.flipX=true;
        }
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void draw() {
        if(anim!=null)
        {
            TextureAtlas.AtlasRegion region = anim.getKeyFrame(animationTime);
            batch.draw(region,pos.x,pos.y,pos.width/2,pos.height/2,pos.width,pos.height,(flipX?-1:1)*1,(flipY?-1:1)*1,0);
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void reset() {
        //setto la vita uguale a 1 e pronto per essere rispawnato
        //NOTA: il nemico non viene spawnato affinche' non verra' settato alive=true nella init successiva
        this.life=1f;
        if(!world.isLocked())
            if(body != null)
            {
                world.destroyBody(body);
            }
    }

    @Override
    public void init() {
        this.alive=true;
        this.life=1;
        this.pos=new Rectangle(MathUtils.random(100f,300f),MathUtils.random(100f, 300f),18f,19f);
        createBody(pos.x,pos.y,pos.width,pos.height,1,"enemyA",1f,0f,0f,(short)32,(short)(4|8|16|64));
        this.attachFixture(this.body, new Vector2(-0.1f,-0.15f), true, "enemyLeftFoot", 5, 3, 0, 0, 0);
        this.attachFixture(this.body, new Vector2(0.1f,-0.15f),  true, "enemyRightFoot", 5, 3, 0, 0, 0);
        this.pos.width/=Evilian.PPM;
        this.pos.height/=Evilian.PPM;
        walkLeft=true;
        //nello sprite il nemico e' rivolto gia' verso sinistra
        this.flipX=false;
        this.anim=walking;
        SPEED=0.6f;
    }
    
    public void init(Vector2 loadingPosition, float hp)
    {
        this.alive=true;
        this.life=hp;
        this.pos=new Rectangle(loadingPosition.x * Evilian.PPM, loadingPosition.y * Evilian.PPM, 18f,19f);
        createBody(pos.x,pos.y,pos.width,pos.height,1,"enemyA",1f,0f,0f,(short)32,(short)(4|8|16|64));
        this.attachFixture(this.body, new Vector2(-0.1f,-0.15f), true, "enemyLeftFoot", 5, 3, 0, 0, 0);
        this.attachFixture(this.body, new Vector2(0.1f,-0.15f),  true, "enemyRightFoot", 5, 3, 0, 0, 0);
        this.pos.width/=Evilian.PPM;
        this.pos.height/=Evilian.PPM;
        walkLeft=true;
        //nello sprite il nemico e' rivolto gia' verso sinistra
        this.flipX=false;
        this.anim=walking;
        SPEED=0.6f;
    }
}
