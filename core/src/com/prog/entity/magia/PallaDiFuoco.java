package com.prog.entity.magia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.prog.evilian.Evilian;
import static com.prog.world.Livello.atlas;

public class PallaDiFuoco extends Magia{
    
    private final static Animation<TextureAtlas.AtlasRegion> moving=new Animation<>(1/20f,atlas.findRegions("fire_ball"),Animation.PlayMode.LOOP);
    public static long UI_CD;
    public PallaDiFuoco()
    {
    }
    
    @Override
    public void init(Vector2 position, Vector2 impulso)
    {
        COOLDOWN=300;
        UI_CD=COOLDOWN;
        //position e' la posizione del personaggio in metri da convertire in pixel
        pos = new Rectangle(0, 0, 47, 20);
        pos.x =(position.x);
        pos.y = (position.y);
        pos.width /= Evilian.PPM;
        pos.height /= Evilian.PPM;
        this.impulso = impulso;
        this.potenza=0.1f;
        
        System.out.println("sto spawnando il cerchio in "+pos);
        this.body = createBody(pos.x*Evilian.PPM, pos.y*Evilian.PPM, 10, 1, "magia", 0.6f, 0, 1,(short)16,(short)(32|8));
        
        body.applyLinearImpulse(impulso.scl(potenza),new Vector2(pos.x,pos.y),true);
        
        this.anim=moving;
        alive=true;
    }

    @Override
    public void update(float delta) {
        //System.out.println(alive);
        if(alive)
        {
            animationTime += delta;
            //dare impulso con parametro tmp
            pos.x = this.body.getPosition().x - (pos.width / 2);
            pos.y = this.body.getPosition().y - (pos.height / 2);
            //System.out.println(pos);
            //System.out.println(body.getWorldCenter());

            //inclinazione proiettile(utilizzo arcotangente)
            Vector2 vel=body.getLinearVelocity();

            float rad =(float)Math.atan2(vel.y, vel.x);
            //conversione radianti-angolo
            angle=(float) (rad*(180/Math.PI));
        }
    }
    
}
