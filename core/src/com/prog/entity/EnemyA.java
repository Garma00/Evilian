package com.prog.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.prog.evilian.Evilian;

public class EnemyA extends Enemy{
    public boolean walkLeft;
    float SPEED=0.6f;
    
    @Override
    public void update(float delta) {
        pos.x=(body.getPosition().x)-(pos.width/2);
        pos.y=(body.getPosition().y)-(pos.height/2);
        
        if(SPEED < 0.6f)
            SPEED += 0.2f * delta;
        
        if(walkLeft)
            body.setLinearVelocity(-SPEED,body.getLinearVelocity().y);
        else
            body.setLinearVelocity(SPEED,body.getLinearVelocity().y);
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void draw() {
        //da fare
    }

    @Override
    public void dispose() {
    }

    @Override
    public void reset() {
        this.alive=true;
        this.life=1;
    }

    @Override
    public void init() {
        this.alive=true;
        this.life=1;
        this.pos=new Rectangle(MathUtils.random(100f,300f),MathUtils.random(100f, 300f),16f,28f);
        this.body=createBody(pos.x,pos.y,pos.width,pos.height,1,"enemyA",1f,0f,1f,(short)32,(short)(4|8|16|64));
        this.attachFixture(this.body, new Vector2(-0.1f,-0.15f), true, "enemyLeftFoot", 5, 3, 0, 0, 0);
        this.attachFixture(this.body, new Vector2(0.1f,-0.15f),  true, "enemyRightFoot", 5, 3, 0, 0, 0);
        this.pos.width/=Evilian.PPM;
        this.pos.height/=Evilian.PPM;
        walkLeft=true;
    }
    
    public void init(Vector2 loadingPosition, float hp)
    {
        this.alive=true;
        this.life=hp;
        this.pos=new Rectangle(loadingPosition.x * Evilian.PPM, loadingPosition.y * Evilian.PPM, 16f,28f);
        this.body=createBody(pos.x,pos.y,pos.width,pos.height,1,"enemyA",1f,0f,1f,(short)32,(short)(4|8|16|64));
        this.attachFixture(this.body, new Vector2(-0.1f,-0.15f), true, "enemyLeftFoot", 5, 3, 0, 0, 0);
        this.attachFixture(this.body, new Vector2(0.1f,-0.15f),  true, "enemyRightFoot", 5, 3, 0, 0, 0);
        this.pos.width/=Evilian.PPM;
        this.pos.height/=Evilian.PPM;
        walkLeft=true;
    }

    public void debuffVelocita()
    {
        this.SPEED = 0.2f;
        System.out.println("velocità " + SPEED);
    }
}
