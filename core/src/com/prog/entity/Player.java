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
import com.prog.entity.magia.Magia;
import com.prog.entity.magia.SpellFactory;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.world.Livello.atlas;
import java.io.FileWriter;
import java.io.IOException;


public class Player extends Entity{
    private final static Animation<TextureAtlas.AtlasRegion> stand=new Animation<>(1/7f,atlas.findRegions("knight_m_idle_anim"),Animation.PlayMode.LOOP);
    private final static Animation<TextureAtlas.AtlasRegion> walking=new Animation<>(1/10f,atlas.findRegions("knight_m_run_anim"),Animation.PlayMode.LOOP);
    Mouse mouse;
    Array<Magia> activeSpells;
    SpellFactory spellFactory;
    public static int spellSelector;
    public static long time;
    public static long[] lastLaunch;
    public static float hp, hpMax;
    public static boolean selectorPressed;
    public boolean inAir, alive;
    private boolean invincibile;
    private float dmgTimer;
    
    public Player(Mouse mouse, float spawnX, float spawnY, float hp)
    {
        this.pos=new Rectangle(spawnX,spawnY,atlas.findRegion("knight_m_idle_anim", 0).getRegionWidth(),atlas.findRegion("knight_m_idle_anim", 0).getRegionHeight());
        this.anim=stand;
        //true perche' il player starta in aria
        inAir=true;
        this.body = createBody(pos.x, pos.y, pos.width, pos.height, 1, "player", 1f,  0, 1f,(short)4,(short)(8|32|64));
        this.mouse = mouse;
        //imposto width e height al valore corretto
        this.pos.width=this.pos.width/Evilian.PPM;
        this.pos.height=this.pos.height/Evilian.PPM;
        //attacco una fixture di tipo sensor come piede
        attachFixture(body,new Vector2(0,-0.15f), true,"player_foot", 12f, 5f, 0, 0, 0);
        this.flipX=false;
        this.flipY=false;
        activeSpells = new Array<Magia>();
        
        spellFactory=new SpellFactory(this);
        spellSelector=0;
        lastLaunch=new long[4];
        time=TimeUtils.millis();
        this.hp = hp;
        this.hpMax = 1.0f;
        selectorPressed=false;
        alive = true;
        invincibile = false;
        dmgTimer = 1f;
        
    }

    @Override
    public void update(float delta) {
        time=TimeUtils.millis();
        animationTime+=delta;
        //NOTA: getPosition di body mi ritorna il centro del corpo
        pos.x=(body.getPosition().x)-(pos.width/2);
        pos.y=(body.getPosition().y)-(pos.height/2);
        
        if(this.invincibile)
        {
            dmgTimer -= delta;
            if(dmgTimer <= 0)
            {
                invincibile = false;
                dmgTimer =  1f;
            }
                
        }
        
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

        //setto la vita a 0.1(inserito solo per debugging)
        if(Gdx.input.isKeyJustPressed(Keys.X))
            hp = 0.1f;
        
        //se il mouse viene clickato spara la magia, instanzio il proiettile e passo l'impulso
        if(Gdx.input.justTouched())
        {
            //calcolo vettore distanza tra player e mouse
            Vector2 res=lanciaMagia();
            spellSelect(res);

            //giriamo il personaggio in base al lancio della magia
            flipX=res.x<0?true:false;
        }
        
        if (Gdx.input.isKeyPressed(Keys.A)) {
            anim=walking;
            forza-=1.5;
            flipX=true;
        }
        else if (Gdx.input.isKeyPressed(Keys.D)) {
            anim=walking;
            forza+=1.5;
            flipX=false;
        }else
            anim=stand;

        //logica salto
        if(Gdx.input.isKeyPressed(Keys.W)){
            if(!inAir)
            {
                float force = body.getMass() * 1.5f;
                body.applyLinearImpulse(new Vector2(0,force), body.getWorldCenter(), false);
            }
        }
        this.body.setLinearVelocity(forza,this.body.getLinearVelocity().y);
        
        //selezionamento abilita'
        if(Gdx.input.isKeyJustPressed(Keys.Z))
        {
            spellSelector=(spellSelector+1)%4;
            selectorPressed = true;
        }
    }
    
    public Vector2 lanciaMagia()
    {
        Vector3 mouse_pos = mouse.fixedPosition(Gdx.input.getX(), Gdx.input.getY(), mouse.cam);
        System.out.println("mouse unproject:"+mouse_pos);
        Vector2 m = new Vector2(mouse_pos.x, mouse_pos.y);
        Vector2 pg = new Vector2(body.getWorldCenter());
        Vector2 tmp = m.cpy();
        tmp.sub(pg);
        //tmp = (mouse - player) normalizzato
        tmp.nor();
        return tmp;
    }

    @Override
    public void draw() {
        if(anim!=null)
        {
            TextureAtlas.AtlasRegion region = anim.getKeyFrame(animationTime);
            batch.draw(region,pos.x,pos.y,pos.width/2,pos.height/2,pos.width,pos.height,(flipX?-1:1)*1,(flipY?-1:1)*1,0);
        }
        draw_fireball();
    }
    
    public void draw_fireball()
    {
        for(Magia m:activeSpells)
            m.draw();
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
        //logica cooldown magie
        if(time-lastLaunch[spellSelector]>m.COOLDOWN)
        {
            activeSpells.add(m);
            lastLaunch[spellSelector]=time;
            Evilian.getManagerSound().selectSound(spellSelector);
        }else
            spellFactory.destroySpell(m);
    }
    
    public Vector2 lanciaMeteora()
    {
        Vector3 m_pos = mouse.fixedPosition(Gdx.input.getX(), Gdx.input.getY(), mouse.cam);
        //meteora spawna a 300px sopra il personaggio(+3m ---> 3m * 100px/m = 300px)
        return new Vector2(m_pos.x, m_pos.y + 3f);
    }

    public void saveState() throws IOException
    {
        FileWriter wr = new FileWriter("save_state.txt");
        String toWrite = "P " + pos.x + " " + pos.y + " " + hp + "\n";
        
        System.out.println(toWrite);
        wr.write(toWrite);
        wr.close();
    }

    public void applyDmg(float dmg)
    {
        if(!invincibile)
        {
            this.hp -= dmg;
            if(this.hp <= 0)
                this.alive = false;
            this.invincibile = true;
        }
    }

}
