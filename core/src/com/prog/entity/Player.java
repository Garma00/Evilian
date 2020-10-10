package com.prog.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.prog.collision.LevelContactListener;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.world.Livello.atlas;

public class Player extends Entity{
    private final static Animation<TextureAtlas.AtlasRegion> stand=new Animation<>(1/7f,atlas.findRegions("knight_m_idle_anim"),Animation.PlayMode.LOOP);
    private final static Animation<TextureAtlas.AtlasRegion> walking=new Animation<>(1/10f,atlas.findRegions("knight_m_run_anim"),Animation.PlayMode.LOOP);
    LevelContactListener lcl;
    
    public Player(LevelContactListener lcl)
    {
        this.pos=new Rectangle(200,1000,atlas.findRegion("knight_m_idle_anim", 0).getRegionWidth(),atlas.findRegion("knight_m_idle_anim", 0).getRegionHeight());
        this.anim=stand;
        this.lcl=lcl;
        this.body = createBody(pos.x, pos.y, pos.width, pos.height, 1, "player", 0.1f,  0, 1f);
        //attacco una fixture di tipo sensor come piede
        attachFixture(body,new Vector2(0,-0.15f), true, 10f, 10f, "player_foot", 0, 0, 0);
        this.flipX=false;
        this.flipY=false;
    }

    @Override
    public void update(float delta) {
        animationTime+=delta;
        //NOTA: getPosition di body mi ritorna il centro del corpo
        pos.x=(body.getPosition().x * Evilian.PPM)-(pos.width/2);
        pos.y=(body.getPosition().y * Evilian.PPM)-(pos.height/2);
    }

    @Override
    public void handleInput() {
        Vector2 pos = this.body.getPosition();
        float forza=0;


        // apply left impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyPressed(Keys.A)) {
            anim=walking;
            forza-=1.5;
            flipX=true;
        }
        // apply right impulse, but only if max velocity is not reached yet
        else if (Gdx.input.isKeyPressed(Keys.D)) {
            anim=walking;
            forza+=1.5;
            flipX=false;
        }else
            anim=stand;

        if(Gdx.input.isKeyPressed(Keys.W)){
            if(!lcl.inAir)
            {
                float force = body.getMass() * 1.5f;
                System.out.println("massa:"+body.getMass()+"\tforza:"+force);
                body.applyLinearImpulse(new Vector2(0,force), body.getWorldCenter(), false);
            }
        }
        
        this.body.setLinearVelocity(forza,this.body.getLinearVelocity().y);
    }

    @Override
    public void draw() {
        //batch importato staticamente
        if(anim!=null)
        {
            TextureAtlas.AtlasRegion region = anim.getKeyFrame(animationTime);
            System.out.println("player:\t"+(body.getPosition().x)+"\t"+(body.getPosition().y));
            System.out.println("draw:\t"+pos.x+"\t"+pos.y);
            System.out.println(pos.width/Evilian.PPM+"\t"+pos.height/Evilian.PPM);
            //batch.draw(region, 0.16f, 0.16f, (pos.width/2)/Evilian.PPM,(pos.height/2)/Evilian.PPM,pos.width/Evilian.PPM, pos.height/Evilian.PPM,(flipX?-1:1)*1,(flipY?-1:1)*1,0);
            batch.draw(region,pos.x,pos.y,body.getWorldCenter().x,body.getWorldCenter().y,pos.width,pos.height,(flipX?-1:1)*1,(flipY?-1:1)*1,0);
        }
    }

    @Override
    public void dispose() {
    }
    
}
