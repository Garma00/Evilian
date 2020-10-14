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
        pos = new Rectangle(0, 0, 50, 50);
        pos.x =position.x * Evilian.PPM;
        pos.y = position.y * Evilian.PPM;
        pos.width /= Evilian.PPM;
        pos.height /= Evilian.PPM;
        this.potenza = potenza;
        this.impulso = impulso;
        this.body = createBody(pos.x, pos.y, 10, 1, "magia", 1, 0, 1);
        this.body.applyLinearImpulse(impulso, body.getWorldCenter(), true);
    }
    
    

    @Override
    public Body createBody(float x, float y, float radius, int bodyType, String userData, float density, float restitution, float friction) {
        return super.createBody(x, y, radius, bodyType, userData, density, restitution, friction); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public void update(float delta) 
    {
        animationTime += delta;
        //dare impulso con parametro tmp
        pos.x = this.body.getPosition().x - pos.width / 2;
        pos.y = this.body.getPosition().y - pos.height / 2;
        
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
