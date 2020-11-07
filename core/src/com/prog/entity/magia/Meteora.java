package com.prog.entity.magia;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.prog.evilian.Evilian;
import static com.prog.world.Livello.atlas;

public class Meteora extends Magia{
    
    private final static Animation<TextureAtlas.AtlasRegion> moving=new Animation<>(1/20f,atlas.findRegions("meteor"),Animation.PlayMode.LOOP);
    public static long UI_CD;
    @Override
    public void init(Vector2 position, Vector2 impulso)
    {
        COOLDOWN=10000;
        UI_CD=COOLDOWN;
        //position e' la posizione del personaggio in metri da convertire in pixel
        pos = new Rectangle(0, 0, 100, 20);
        pos.x = impulso.x;
        pos.y = impulso.y;
        pos.width /= Evilian.PPM;
        pos.height /= Evilian.PPM;
        this.impulso = impulso;
        this.potenza=0.05f;
        
        //System.out.println("sto spawnando il cerchio in "+pos);
        this.body = createBody(pos.x*Evilian.PPM, pos.y*Evilian.PPM, 20, 100, 1, "magia", 0.6f, 0, 1,(short)16,(short)(32|8));
        
        //body.applyLinearImpulse(impulso.scl(potenza),new Vector2(pos.x,pos.y),true);
        
        this.anim=moving;
        alive=true;
        
        angle = 269;
    }

    @Override
    public void update(float delta)
    {
        //System.out.println(alive);
        if(alive)
        {
            animationTime += delta;
            //dare impulso con parametro tmp
            pos.x = this.body.getPosition().x - (pos.width / 2);
            pos.y = this.body.getPosition().y - (pos.height / 2);
            //System.out.println(pos);
            //System.out.println(body.getWorldCenter());

            
        }
    }
    
}
