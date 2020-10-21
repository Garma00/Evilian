package com.prog.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.prog.collision.LevelContactListener;
import com.prog.entity.magia.Magia;
import com.prog.entity.magia.SpellFactory;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.world.Livello.atlas;
import static com.prog.evilian.Evilian.MANAGER_SOUND;


public class Player extends Entity{
    private final static Animation<TextureAtlas.AtlasRegion> stand=new Animation<>(1/7f,atlas.findRegions("knight_m_idle_anim"),Animation.PlayMode.LOOP);
    private final static Animation<TextureAtlas.AtlasRegion> walking=new Animation<>(1/10f,atlas.findRegions("knight_m_run_anim"),Animation.PlayMode.LOOP);
    Mouse mouse;
    Array<Magia> activeSpells;
    SpellFactory spellFactory;
    public static int spellSelector, sound;//sound è l'indice per selezionare l'effetto sonoro corretto
    public static long time;
    public static long[] lastLaunch;
    public static float hp, hpMax;
    public static boolean selectorPressed;
    public static boolean inAir;
    
    public Player(Mouse mouse)
    {
        this.pos=new Rectangle(50,100,atlas.findRegion("knight_m_idle_anim", 0).getRegionWidth(),atlas.findRegion("knight_m_idle_anim", 0).getRegionHeight());
        this.anim=stand;
        //true perche' il player starta in aria
        inAir=true;
        this.body = createBody(pos.x, pos.y, pos.width, pos.height, 1, "player", 1f,  0, 1f,(short)4,(short)(8|32|64));
        //imposto width e height al valore corretto
        this.pos.width=this.pos.width/Evilian.PPM;
        this.pos.height=this.pos.height/Evilian.PPM;
        //attacco una fixture di tipo sensor come piede
        attachFixture(body,new Vector2(0,-0.15f), true,"player_foot", 12f, 5f, 0, 0, 0);
        this.flipX=false;
        this.flipY=false;
        this.mouse = mouse;
        activeSpells = new Array<Magia>();
        
        spellFactory=new SpellFactory(this);
        spellSelector=0;
        lastLaunch=new long[4];
        time=TimeUtils.millis();
        this.hp = 0.1f;
        this.hpMax = 1.0f;
        this.sound = 0;
        selectorPressed=false;
    }

    @Override
    public void update(float delta) {
        time=TimeUtils.millis();
        animationTime+=delta;
        //NOTA: getPosition di body mi ritorna il centro del corpo
        pos.x=(body.getPosition().x)-(pos.width/2);
        pos.y=(body.getPosition().y)-(pos.height/2);
        
        for(Magia m:activeSpells)
            m.update(delta);
        
         
        for(int i=0;i<activeSpells.size;i++)
        {
            Magia item=activeSpells.get(i);
            if(!item.alive)
            {
                activeSpells.removeIndex(i);
                spellFactory.destroySpell(item);
            }
        }
    }

    @Override
    public void handleInput() {
        float forza=0;

        //setto la vita a 0.1
        if(Gdx.input.isKeyJustPressed(Keys.X))
            hp = 0.1f;
        
        //se il mouse viene clickato spara la magia, instanzio il proiettile e passo l'inpulso
        if(Gdx.input.justTouched())
        {
            Vector2 res=lanciaMagia();
            spellSelect(res);

            //logica player dopo il lancio
            if(res.x < 0)
                flipX=true;
            else
                flipX=false;
        }
        
        // apply left impulse, but only if max velocity is not reached yet
        if (Gdx.input.isKeyPressed(Keys.A)) {
            anim=walking;
            forza-=1.5;
            flipX=true;
        }
        // apply right impulse, but only if max velocity is not reached yet
        else if (Gdx.input.isKeyPressed(Keys.D)) {
            anim=walking;
            forza+=1.5;
            flipX=false;
        }else
            anim=stand;

        if(Gdx.input.isKeyPressed(Keys.W)){
            if(!inAir)
            {
                float force = body.getMass() * 1.5f;
                //System.out.println("massa:"+body.getMass()+"\tforza:"+force);
                body.applyLinearImpulse(new Vector2(0,force), body.getWorldCenter(), false);
            }
        }
        
        this.body.setLinearVelocity(forza,this.body.getLinearVelocity().y);
        
        if(Gdx.input.isKeyJustPressed(Keys.Z))
        {
            spellSelector=(spellSelector+1)%4;
            selectorPressed = true;
            sound = spellSelector;//l'indice sound corrisponde sempre al valore di Z
        }
    }
    
    public Vector2 lanciaMagia()
    {
        Vector3 mouse_pos = mouse.fixedPosition(Gdx.input.getX(), Gdx.input.getY(), mouse.cam);
        //System.out.println("mouse unproject:"+mouse_pos);
        Vector2 m = new Vector2(mouse_pos.x, mouse_pos.y);
        Vector2 pg = new Vector2(body.getWorldCenter());
        //System.out.println("player:"+pg);
        Vector2 tmp = m.cpy();
        tmp.sub(pg);
        //System.out.println("vettore distanza:"+tmp);
        //tmp = (mouse - player) normalizzato
        tmp.nor();
        //System.out.println("vettore normalizzato:"+tmp);
        return tmp;
    }

    @Override
    public void draw() {
        //batch importato staticamente
        if(anim!=null)
        {
            TextureAtlas.AtlasRegion region = anim.getKeyFrame(animationTime);
            //System.out.println("player in:"+body.getPosition());
            //System.out.println(pos.x+"\t"+pos.y);
            batch.draw(region,pos.x,pos.y,pos.width/2,pos.height/2,pos.width,pos.height,(flipX?-1:1)*1,(flipY?-1:1)*1,0);
        }
        draw_fireball();
    }
    
    public void draw_fireball()
    {
        for(Magia m:activeSpells)
        {
            m.draw();
            
            
        }
            
    }

    @Override
    public void dispose() {
    }

    private void spellSelect(Vector2 res) {
        Magia m = null;
        
        switch(spellSelector)
        {
            case 0:
                m=spellFactory.createSpell(SpellFactory.SpellType.PALLADIFUOCO);
                break;
            case 1:
                m=spellFactory.createSpell(SpellFactory.SpellType.PALLADIGHIACCIO);
                break;
            case 2:
                m=spellFactory.createSpell(SpellFactory.SpellType.CURA);
                break;
            case 3:
                res = lanciaMeteora();
                m=spellFactory.createSpell(SpellFactory.SpellType.METEORA);
                break;
        }

        
        m.init(this.body.getWorldCenter(), res);
        if(time-lastLaunch[spellSelector]>m.COOLDOWN)
        {
            activeSpells.add(m);
            lastLaunch[spellSelector]=time;
            MANAGER_SOUND.selectSound(sound);
        }else
            spellFactory.destroySpell(m);
    }
    
    public Vector2 lanciaMeteora()
    {
        Vector3 m_pos = mouse.fixedPosition(Gdx.input.getX(), Gdx.input.getY(), mouse.cam);
        //+3f = +300px
        return new Vector2(m_pos.x, m_pos.y + 3f);
    }
}
