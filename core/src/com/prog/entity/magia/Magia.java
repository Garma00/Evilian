package com.prog.entity.magia;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.prog.entity.Entity;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.world.Livello.atlas;



public class Magia extends Entity{
    
    public int potenza;
    Vector2 impulso;
    Rectangle pos;
    private final static Animation<TextureAtlas.AtlasRegion> moving=new Animation<>(1/7f,atlas.findRegions("knight_m_idle_anim"),Animation.PlayMode.LOOP);
    
    public Magia(Vector2 position, int potenza, Vector2 impulso)
    {
        //position e' la posizione del personaggio in metri da convertire in pixel
        pos = new Rectangle(0, 0, 20, 20);
        pos.x =(position.x);
        pos.y = (position.y);
        pos.width /= Evilian.PPM;
        pos.height /= Evilian.PPM;
        this.potenza = potenza;
        this.impulso = impulso;
        
        pos.x+=impulso.x/5;
        pos.y+=impulso.y/5;
        
        System.out.println("sto spawnando il cerchio in "+pos);
        this.body = createBody(pos.x*Evilian.PPM, pos.y*Evilian.PPM, 10, 1, "magia", 0.6f, 0, 1,(short)16,(short)32);
        
        body.applyLinearImpulse(impulso.scl(1/10f),new Vector2(pos.x,pos.y),true);
        
        this.anim=moving;
    }
    

    @Override
    public void update(float delta) 
    {
        //animationTime += delta;
        //dare impulso con parametro tmp
        pos.x = this.body.getPosition().x - (pos.width / 2);
        pos.y = this.body.getPosition().y - (pos.height / 2);
        //System.out.println(pos);
        //System.out.println(body.getWorldCenter());
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void draw()
    {
        if(anim!=null)
        {
            TextureAtlas.AtlasRegion region = anim.getKeyFrame(animationTime);
            //System.out.println("player in:"+body.getPosition());
            //System.out.println(pos.x+"\t"+pos.y);
            batch.draw(region,pos.x,pos.y,pos.width/2,pos.height/2,pos.width,pos.height,(flipX?-1:1)*1,(flipY?-1:1)*1,0);
        }
    }

    @Override
    public void dispose() {
    }
    
}
