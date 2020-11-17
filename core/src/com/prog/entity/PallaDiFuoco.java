package com.prog.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.prog.evilian.Evilian;
import com.prog.world.Livello;

public class PallaDiFuoco extends Magia{
    
    private final static Animation<TextureAtlas.AtlasRegion> moving=new Animation<>(1/20f,Livello.getAtlas().findRegions("fire_ball"),Animation.PlayMode.LOOP);
    private static long UI_CD;
    
    
    @Override
    public void init(Vector2 position, Vector2 impulso)
    {
        Rectangle r=getPos();
        COOLDOWN=300;
        UI_CD=COOLDOWN;
        //position e' la posizione del personaggio in metri da convertire in pixel
        setPos(position.x, position.y, 47, 20);
        setPosWidth(r.width/Evilian.PPM);
        setPosHeight(r.height/Evilian.PPM);
        this.impulso = impulso;
        this.potenza=0.1f;
        
        //System.out.println("sto spawnando il cerchio in "+pos);
        createBody(r.x*Evilian.PPM, r.y*Evilian.PPM, 10, 1, "magia", 0.6f, 0, 1,(short)16,(short)(32|8));
        
        getBody().applyLinearImpulse(impulso.scl(potenza),new Vector2(r.x,r.y),true);
        
        this.anim=moving;
        alive=true;
    }

    @Override
    public void update(float delta) {
        Rectangle r=getPos();
        Body b=getBody();
        
        //System.out.println(alive);
        if(alive)
        {
            animationTime += delta;
            //dare impulso con parametro tmp
            r.x = b.getPosition().x - (r.width / 2);
            r.y = b.getPosition().y - (r.height / 2);
            //System.out.println(pos);
            //System.out.println(body.getWorldCenter());

            //inclinazione proiettile(utilizzo arcotangente)
            Vector2 vel=b.getLinearVelocity();

            float rad =(float)Math.atan2(vel.y, vel.x);
            //conversione radianti-angolo
            angle=(float) (rad*(180/Math.PI));
        }
    }
    
    public static long getCD()
    {
        return UI_CD;
    }
    
}
