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
    Player pg;
    public static long UI_CD;

    public Cura(Player pg)
    {
        this.pg = pg;
    }
    
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
        
        //System.out.println("sto spawnando il cerchio in "+pos);
        //this.body = createBody(pos.x*Evilian.PPM, pos.y*Evilian.PPM, 10, 1, "magia", 0.6f, 0, 1,(short)16,(short)32);
        
        
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
        if(pg.hp >= pg.hpMax)
        {
            pg.hp = pg.hpMax;
            //System.out.println("hp gia' al massimo non uso la cura");
            alive = false;
        }
            
        if(alive && pg.hp < pg.hpMax)
        {
            pg.hp+= potenza*delta;
            //System.out.println("HP:\t" + pg.hp + "/" + pg.hpMax);
        }
        
        if(time-lastLaunch>durata)
        {
            alive=false;
        }
        
        pos.x = pg.pos.x - pos.width/2 + 10/Evilian.PPM;
        pos.y = pg.pos.y;
        this.flipX = pg.flipX;
    }
    
}
