package com.prog.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import com.prog.world.Livello;

public class EnemyA extends Enemy{
    private final static Animation<TextureAtlas.AtlasRegion> walking=new Animation<>(1/10f,Livello.getAtlas().findRegions("pig_run"),Animation.PlayMode.LOOP);
    
    public EnemyA()
    {
        super();
    }
    
    @Override
    public void update(float delta) {
        //mando in avanti l'animazione
        animationTime+=delta;
        Rectangle r=getPos();
        Body b=getBody();
        
        setPos((b.getPosition().x)-(r.width/2),(b.getPosition().y)-(r.height/2));
        
        if(SPEED < 0.6f)
            SPEED += 0.2f * delta;
        
        if(walkLeft)
        {
            b.setLinearVelocity(-SPEED,b.getLinearVelocity().y);
            flipX=false;
        }
        else
        {
            b.setLinearVelocity(SPEED,b.getLinearVelocity().y);
            this.flipX=true;
        }
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void draw() {
        Rectangle r=getPos();
        if(anim!=null)
        {
            TextureAtlas.AtlasRegion region = anim.getKeyFrame(animationTime);
            batch.draw(region,r.x,r.y,r.width/2,r.height/2,r.width,r.height,(flipX?-1:1)*1,(flipY?-1:1)*1,0);
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
        this.life=1f;
        if(!Livello.getWorld().isLocked())
            if(b != null)
            {
                Livello.getWorld().destroyBody(b);
            }
    }
    
    public void init(Vector2 loadingPosition, float hp)
    {
        Body b;
        Rectangle r=getPos();
        
        this.alive=true;
        this.life=hp;
        setPos(loadingPosition.x * Evilian.PPM, loadingPosition.y * Evilian.PPM, 18f,19f);
        createBody(r.x,r.y,r.width,r.height,1,"enemyA",1f,0f,0f,(short)32,(short)(4|8|16|64));
        b=getBody();
        this.attachFixture(b, new Vector2(-0.1f,-0.15f), true, "enemyLeftFoot", 5, 3, 0, 0, 0);
        this.attachFixture(b, new Vector2(0.1f,-0.15f),  true, "enemyRightFoot", 5, 3, 0, 0, 0);
        setPosWidth(r.width/Evilian.PPM);
        setPosHeight(r.height/Evilian.PPM);
        walkLeft=true;
        //nello sprite il nemico e' rivolto gia' verso sinistra
        this.flipX=false;
        this.anim=walking;
        SPEED=0.6f;
    }
}
