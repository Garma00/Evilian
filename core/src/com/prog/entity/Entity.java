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
import com.prog.world.Livello;

public abstract class Entity {
    private Animation<TextureAtlas.AtlasRegion> anim;
    private final Rectangle pos;
    
    private float animationTime;
    private boolean flipX;
    private boolean flipY;
    private Body body;
    private short CATEGORY_BIT;
    private short MASK_BIT;
    
    public class userDataContainer
    {
        public String type;
        public Entity e;
        
        public userDataContainer(String u, Entity e)
        {
            this.type=u;
            this.e=e;
        }
    }
    
    public Entity()
    {
        this.pos=new Rectangle();
    }
    
    public abstract void update(float delta);
    
    public abstract void handleInput();
    
    public abstract void draw();
    
    public abstract void dispose();
    
    //bodyType
    //0 static
    //1 dynamic
    //2 kinematic
    public void createBody(float x,float y,float width,float height, int bodyType,String userData,float density, float restitution, float friction,short categoria,short mask)
    {
        BodyDef bdef= new BodyDef();
        CATEGORY_BIT=categoria;
        MASK_BIT=mask;
        
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

        this.body=Livello.getWorld().createBody(bdef);
        
        attachFixture(body,new Vector2(0,0),false,userData,width,height,density,restitution,friction);
    }
    
    public void createBody(float x, float y,float radius,int bodyType, String userData,float density, float restitution, float friction,short categoria,short mask)
    {
        BodyDef bdef= new BodyDef();
        CATEGORY_BIT=categoria;
        MASK_BIT=mask;
        
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
        
        body=Livello.getWorld().createBody(bdef);
        
        attachFixture(body,new Vector2(0,0),false,userData,radius,density,restitution,friction);
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
        
        b.createFixture(fdef).setUserData(new userDataContainer(userData, this));
        
        shape.dispose();
    }
    
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
        
        b.createFixture(fdef).setUserData(new userDataContainer(userData, this));
        
        shape.dispose();
    }
    
    public Body getBody()                                               {return body;}
    public Rectangle getPos()                                           {return pos;}
    protected void setPos(float x, float y, float width, float height)  {pos.set(x, y, width, height);}
    protected void setPosX(float x)                                     {pos.setX(x);}
    protected void setPosY(float y)                                     {pos.setY(y);}
    protected void setPosWidth(float w)                                 {pos.setWidth(w);}
    public void setPosHeight(float h)                                   {pos.setHeight(h);}
    protected void setPos(float x,float y)                              {pos.setPosition(x, y);}
    protected void setAnim(Animation<TextureAtlas.AtlasRegion> a)       {anim=a;}
    protected Animation<TextureAtlas.AtlasRegion> getAnim()             {return anim;}
    protected float getAnimationTime()                                  {return animationTime;}
    protected void addToAnimationTime(float f)                          {this.animationTime+=f;}
    protected boolean getFlipX()                                        {return this.flipX;}
    protected boolean getFlipY()                                        {return this.flipY;}
    protected void setFlipX(boolean b)                                  {this.flipX=b;}
    protected void setFlipY(boolean b)                                  {this.flipY=b;}
}
