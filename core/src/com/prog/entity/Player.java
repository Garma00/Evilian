package com.prog.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.prog.entity.magia.SpellFactory;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.world.Livello.atlas;
import java.io.FileWriter;
import java.io.IOException;


public class Player extends Entity{
    private final static Animation<TextureAtlas.AtlasRegion> stand=new Animation<>(1/7f,atlas.findRegions("knight_m_idle_anim"),Animation.PlayMode.LOOP);
    private final static Animation<TextureAtlas.AtlasRegion> walking=new Animation<>(1/10f,atlas.findRegions("knight_m_run_anim"),Animation.PlayMode.LOOP);
    final static SpellFactory spellFactory=SpellFactory.INSTANCE;
    
    public static float hp, hpMax;
    public static float healPosX,healPosY;

    public boolean inAir, alive;
    private boolean invincibile;
    private float dmgTimer;
    
    public Player(float spawnX, float spawnY, float hp)
    {
        this.pos=new Rectangle(spawnX,spawnY,atlas.findRegion("knight_m_idle_anim", 0).getRegionWidth(),atlas.findRegion("knight_m_idle_anim", 0).getRegionHeight());
        this.anim=stand;
        //true perche' il player starta in aria
        inAir=true;
        createBody(pos.x, pos.y, pos.width, pos.height, 1, "player", 1f,  0, 1f,(short)4,(short)(8|32|64));
        //imposto width e height al valore corretto
        this.pos.width/=Evilian.PPM;
        this.pos.height/=Evilian.PPM;
        //attacco una fixture di tipo sensor come piede
        attachFixture(body,new Vector2(0,-0.15f), true,"player_foot", 12f, 5f, 0, 0, 0);
        this.flipX=this.flipY=false;
        
        this.hp = hp;
        this.hpMax = 1.0f;
        alive = true;
        invincibile = false;
        //1 secondo
        dmgTimer = 1f;
    }

    @Override
    public void update(float delta) {
        animationTime+=delta;
        //NOTA: getPosition di body mi ritorna il centro del corpo
        pos.x=(body.getPosition().x)-(pos.width/2);
        pos.y=(body.getPosition().y)-(pos.height/2);
        healPosX=pos.x;
        healPosY=pos.y;
        
        if(this.invincibile)
        {
            dmgTimer -= delta;
            if(dmgTimer <= 0)
            {
                invincibile = false;
                dmgTimer =  1f;
            }
        }
        
        spellFactory.update(delta);
    }

    @Override
    public void handleInput() {
        float forza=0;
        Vector2 res;

        //setto la vita a 0.1(inserito solo per debugging)
        if(Gdx.input.isKeyJustPressed(Keys.X))
            hp = 0.1f;
        
        //se il mouse viene clickato spara la magia, instanzio il proiettile e passo l'impulso
        if(Gdx.input.justTouched())
        {
            //calcolo vettore distanza tra player e mouse
            res=spellFactory.computeDistanceVector(this.body.getWorldCenter());

            //giriamo il personaggio in base al lancio della magia
            flipX=res.x<0;
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
            spellFactory.addToSpellSelector(1);
            spellFactory.setSelectorPressed(true);
        }
    }

    @Override
    public void draw() {
        if(anim!=null)
        {
            TextureAtlas.AtlasRegion region = anim.getKeyFrame(animationTime);
            batch.draw(region,pos.x,pos.y,pos.width/2,pos.height/2,pos.width,pos.height,(flipX?-1:1)*1,(flipY?-1:1)*1,0);
        }
        
        spellFactory.draw();
    }

    @Override
    public void dispose() {
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
