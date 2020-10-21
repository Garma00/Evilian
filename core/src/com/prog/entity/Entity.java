package com.prog.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.prog.evilian.Evilian;
import static com.prog.world.Livello.world;

public abstract class Entity {
    public Animation<TextureAtlas.AtlasRegion> anim;
    public Rectangle pos;
    public float animationTime;
    public boolean flipX;
    public boolean flipY;
    public Body body;
    public short CATEGORY_BIT;
    public short MASK_BIT;
    public String entity_type;
    
    public abstract void update(float delta);
    
    public abstract void handleInput();
    
    public abstract void draw();
    
    public abstract void dispose();
    
    //bodyType
    //0 static
    //1 dynamic
    //2 kinematic
    public Body createBody(float x,float y,float width,float height, int bodyType,String userData,float density, float restitution, float friction,short categoria,short mask)
    {
        BodyDef bdef= new BodyDef();
        CATEGORY_BIT=categoria;
        MASK_BIT=mask;
        this.entity_type=userData;
        
        switch(bodyType)
        {
            case 1:
                bdef.type=BodyDef.BodyType.DynamicBody;
                break;
            case 2:
                bdef.type=BodyDef.BodyType.KinematicBody;
                break;
            default:
                bdef.type=BodyDef.BodyType.StaticBody;
                break;
        }
        
        bdef.position.set(x/Evilian.PPM,y/Evilian.PPM);
        bdef.fixedRotation=true;

        body=world.createBody(bdef);
        
        attachFixture(body,new Vector2(0,0),false,userData,width,height,density,restitution,friction);
        
        return body;
    }
    
    //overloading
    public Body createBody(float x, float y,float radius,int bodyType, String userData,float density, float restitution, float friction,short categoria,short mask)
    {
        BodyDef bdef= new BodyDef();
        CATEGORY_BIT=categoria;
        MASK_BIT=mask;
        this.entity_type=userData;
        
        switch(bodyType)
        {
            case 1:
                bdef.type=BodyDef.BodyType.DynamicBody;
                break;
            case 2:
                bdef.type=BodyDef.BodyType.KinematicBody;
                break;
            default:
                bdef.type=BodyDef.BodyType.StaticBody;
                break;
        }
        
        bdef.position.set(x/Evilian.PPM,y/Evilian.PPM);
        
        body=world.createBody(bdef);
        
        attachFixture(body,new Vector2(0,0),false,userData,radius,density,restitution,friction);
        
        return body;
        
    }
    
    public void attachFixture(Body b,Vector2 relativePos, boolean isSensor,String userData, float width, float height,float density, float restitution, float friction)
    {
        PolygonShape shape=new PolygonShape();
        // diviso 2 perche' la setasbox parte dal centro
        shape.setAsBox((width/2)/Evilian.PPM, (height/2)/Evilian.PPM,relativePos,0);
        
        FixtureDef fdef=new FixtureDef();
        fdef.density=density;
        fdef.friction=friction;
        fdef.restitution=restitution;
        fdef.isSensor=isSensor;
        fdef.filter.categoryBits=CATEGORY_BIT;
        fdef.filter.maskBits=MASK_BIT;
        
        fdef.shape=shape;
        
        if(isSensor)
            b.createFixture(fdef).setUserData(userData);
        else
            b.createFixture(fdef).setUserData(this);
        
        shape.dispose();
    }
    
    //overloading
    public void attachFixture(Body b,Vector2 relativePos, boolean isSensor,String userData,float radius, float density, float restitution, float friction)
    {
        CircleShape shape=new CircleShape();
        // diviso 2 perche' la setasbox parte dal centro
        shape.setRadius(radius/Evilian.PPM);
        shape.setPosition(relativePos);
        
        FixtureDef fdef=new FixtureDef();
        fdef.density=density;
        fdef.friction=friction;
        fdef.restitution=restitution;
        fdef.isSensor=isSensor;
        fdef.filter.categoryBits=CATEGORY_BIT;
        fdef.filter.maskBits=MASK_BIT;
        
        fdef.shape=shape;
        
        b.createFixture(fdef).setUserData(this);
        
        shape.dispose();
    }
}
