package com.prog.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.prog.evilian.Evilian;
import com.prog.world.Livello;


public class Meteora extends Magia{
    
    public Meteora()
    {
        super();
    }
    
    private final static Animation<TextureAtlas.AtlasRegion> moving=new Animation<>(1/20f,Livello.getAtlas().findRegions("meteor"),Animation.PlayMode.LOOP);
    private static long UI_CD;
    @Override
    public void init(Vector2 position, Vector2 impulso)
    {
        Rectangle r=getPos();
        COOLDOWN=10000;
        UI_CD=COOLDOWN;
        //position e' la posizione del personaggio in metri da convertire in pixel
        setPos(impulso.x, impulso.y, 100, 20);
        setPosWidth(r.width/Evilian.PPM);
        setPosHeight(r.height/Evilian.PPM);
        this.impulso = impulso;
        this.potenza=0.05f;
        
        //System.out.println("sto spawnando il cerchio in "+pos);
        createBody(r.x*Evilian.PPM, r.y*Evilian.PPM, 20, 100, 1, "magia", 0.6f, 0, 1,(short)16,(short)(32|8));
        
        //body.applyLinearImpulse(impulso.scl(potenza),new Vector2(pos.x,pos.y),true);
        
        this.anim=moving;
        alive=true;
        
        angle = 269;
    }

    @Override
    public void update(float delta)
    {
        Body b=getBody();
        Rectangle r=getPos();
        if(alive)
        {
            animationTime += delta;
            //dare impulso con parametro tmp
            setPosX( b.getPosition().x - (r.width / 2));
            setPosY(b.getPosition().y - (r.height / 2));
        }
    }
    
    public static long getCD()
    {
        return UI_CD;
    }
    
}
