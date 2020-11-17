package com.prog.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.prog.evilian.Evilian;
import com.prog.world.Livello;

public class Cura extends Magia{
    private final static Animation<TextureAtlas.AtlasRegion> moving=new Animation<>(1/20f,Livello.getAtlas().findRegions("heal"),Animation.PlayMode.LOOP_PINGPONG);
    private long durata;
    private static long UI_CD;
    
    @Override
    public void init(Vector2 position,Vector2 impulso) {
        Rectangle r=getPos();
        COOLDOWN=5000;
        UI_CD=COOLDOWN;
        //position e' la posizione del personaggio in metri da convertire in pixel
        setPos((position.x) - r.width/2, (position.y), 25, 18);
        setPosWidth(r.width/Evilian.PPM);
        setPosHeight(r.height/Evilian.PPM);
        this.potenza=0.1f;
        durata=3000;
        this.anim=moving;
        time=TimeUtils.millis();
        //solo per i buff
        lastLaunch=time;
        alive=true;
    }

    @Override
    public void update(float delta) {
        Rectangle r=getPos();
        
        animationTime+=delta;
        time=TimeUtils.millis();
        float playerHP=Player.getHP();
        float playerMaxHP=Player.getMaxHp();
        
        if(playerHP >= playerMaxHP)
        {
            Player.setHP(playerMaxHP);
            //System.out.println("hp gia' al massimo, non uso la cura");
            alive = false;
        }
            
        if(alive && playerHP < playerMaxHP)
        {
            Player.setHP(playerHP+potenza*delta);
            //System.out.println("HP:\t" + pg.getHp() + "/" + pg.getHp()Max);
        }
        
        if(time-lastLaunch>durata)
        {
            alive=false;
        }
        
        
        setPosX(Player.getHealPosX() - r.width/2 + 10/Evilian.PPM);
        setPosY( Player.getHealPosY() );
    }
    
    public static long getCD()
    {
        return UI_CD;
    }
}
