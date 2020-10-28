package com.prog.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.prog.evilian.Evilian;

public class EnemyA extends Enemy{
    public boolean inAir;
    
    @Override
    public void update(float delta) {
        pos.x=(body.getPosition().x)-(pos.width/2);
        pos.y=(body.getPosition().y)-(pos.height/2);
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
        
        this.pos.width/=Evilian.PPM;
        this.pos.height/=Evilian.PPM;
        
    }
}
