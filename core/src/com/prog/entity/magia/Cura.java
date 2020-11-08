package com.prog.entity.magia;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import static com.prog.world.Livello.atlas;

public class Cura extends Magia{
    private final static Animation<TextureAtlas.AtlasRegion> moving=new Animation<>(1/20f,atlas.findRegions("heal"),Animation.PlayMode.LOOP_PINGPONG);
    long durata;
    public static long UI_CD;
    
    @Override
    public void init(Vector2 position,Vector2 impulso) {
        COOLDOWN=5000;
        UI_CD=COOLDOWN;
        //position e' la posizione del personaggio in metri da convertire in pixel
        pos = new Rectangle(0, 0, 25, 18);
        pos.x =(position.x) - pos.width/2;
        pos.y = (position.y);
        pos.width /= Evilian.PPM;
        pos.height /= Evilian.PPM;
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
        animationTime+=delta;
        time=TimeUtils.millis();
        if(Player.hp >= Player.hpMax)
        {
            Player.hp = Player.hpMax;
            //System.out.println("hp gia' al massimo non uso la cura");
            alive = false;
        }
            
        if(alive && Player.hp < Player.hpMax)
        {
            Player.hp+= potenza*delta;
            //System.out.println("HP:\t" + pg.hp + "/" + pg.hpMax);
        }
        
        if(time-lastLaunch>durata)
        {
            alive=false;
        }
        
        
        pos.x = Player.healPosX - pos.width/2 + 10/Evilian.PPM;
        pos.y = Player.healPosY;
        //this.flipX = pg.flipX;
    }
    
}
