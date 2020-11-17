package com.prog.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.prog.evilian.Evilian;
import com.prog.world.Livello;

public class PallaDiGhiaccio extends Magia{
    
    private final static Animation<TextureAtlas.AtlasRegion> moving=new Animation<>(1/20f,Livello.getAtlas().findRegions("ice_ball"),Animation.PlayMode.LOOP);
    private static long UI_CD;
    
    @Override
    public void init(Vector2 position, Vector2 impulso)
    {
        Rectangle r=getPos();
        
        UI_CD=500;
        setCoolDown(UI_CD);
        //position e' la posizione del personaggio in metri da convertire in pixel
        setPos(position.x, position.y, 47, 20);
        setPosWidth(r.width/Evilian.PPM);
        setPosHeight(r.height/Evilian.PPM);
        setPower(0.1f);
        
        //System.out.println("sto spawnando il cerchio in "+pos);
        createBody(r.x*Evilian.PPM, r.y*Evilian.PPM, 10, 1, "magia", 0.6f, 0, 1,(short)16,(short)(32|8));
        
        getBody().applyLinearImpulse(impulso.scl(getPower()),new Vector2(r.x,r.y),true);
        
        setAnim(moving);
        setAlive(true);
    }

    @Override
    public void update(float delta)
    {
        Rectangle r=getPos();
        Body b=getBody();
        
        //System.out.println(alive);
        if(isAlive())
        {
            addToAnimationTime(delta);
            //dare impulso con parametro tmp
            r.x = b.getPosition().x - (r.width / 2);
            r.y = b.getPosition().y - (r.height / 2);

            //inclinazione proiettile(utilizzo arcotangente)
            Vector2 vel=b.getLinearVelocity();

            float rad =(float)Math.atan2(vel.y, vel.x);
            //conversione radianti-angolo
            setAngle((float) (rad*(180/Math.PI)));
        }
    }
    
    public static long getCD()
    {
        return UI_CD;
    }
}
