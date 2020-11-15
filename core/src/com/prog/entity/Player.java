package com.prog.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.prog.entity.magia.SpellFactory;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import com.prog.world.Livello;
import java.io.FileWriter;
import java.io.IOException;


public class Player extends Entity{
    private final static Animation<TextureAtlas.AtlasRegion> stand=new Animation<>(1/7f,Livello.getAtlas().findRegions("knight_m_idle_anim"),Animation.PlayMode.LOOP);
    private final static Animation<TextureAtlas.AtlasRegion> walking=new Animation<>(1/10f,Livello.getAtlas().findRegions("knight_m_run_anim"),Animation.PlayMode.LOOP);
    final static SpellFactory spellFactory=SpellFactory.getInstance();
    
    private static float hp, hpMax;
    private static float healPosX,healPosY;
    private boolean inAir, alive;
    private boolean invincibile;
    private float dmgTimer;
    private boolean levelCompleted;
    
    public Player(float spawnX, float spawnY, float hp)
    {
        super();
        Rectangle r=getPos();
        setPos(spawnX,spawnY,Livello.getAtlas().findRegion("knight_m_idle_anim", 0).getRegionWidth(),(Livello.getAtlas().findRegion("knight_m_idle_anim", 0).getRegionHeight()));
        this.anim=stand;
        //true perche' il player starta in aria
        inAir=true;
        createBody(r.x, r.y, r.width, r.height, 1, "player", 1f,  0, 1f,(short)4,(short)(8|32|64|128));
        //imposto width e height al valore corretto
        setPosWidth(r.width/Evilian.PPM);
        setPosHeight(r.height/Evilian.PPM);
        //attacco una fixture di tipo sensor come piede
        attachFixture(getBody(),new Vector2(0,-0.15f), true,"player_foot", 12f, 5f, 0, 0, 0);
        this.flipX=this.flipY=false;
        
        this.hp = hp;
        this.hpMax = 1.0f;
        alive = true;
        invincibile = false;
        //1 secondo
        dmgTimer = 1f;
        levelCompleted=false;
    }

    @Override
    public void update(float delta) {
        Body b=getBody();
        Rectangle r=getPos();
        
        animationTime+=delta;
        //NOTA: getPosition di body mi ritorna il centro del corpo
        setPosX((b.getPosition().x)-(r.width/2));
        setPosY((b.getPosition().y)-(r.height/2));
        healPosX=r.x;
        healPosY=r.y;
        
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
        Body b=getBody();

        //setto la vita a 0.1(inserito solo per debugging)
        if(Gdx.input.isKeyJustPressed(Keys.X))
            hp = 0.1f;
        
        //se il mouse viene clickato spara la magia, instanzio il proiettile e passo l'impulso
        if(Gdx.input.justTouched())
        {
            //calcolo vettore distanza tra player e mouse
            res=spellFactory.computeDistanceVector(b.getWorldCenter());

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
                float force = b.getMass() * 1.5f;
                b.applyLinearImpulse(new Vector2(0,force), b.getWorldCenter(), false);
            }
        }
        b.setLinearVelocity(forza,b.getLinearVelocity().y);
        
        //selezionamento abilita'
        if(Gdx.input.isKeyJustPressed(Keys.Z))
        {
            spellFactory.addToSpellSelector(1);
            spellFactory.setSelectorPressed(true);
        }
    }

    @Override
    public void draw() {
        Rectangle r=getPos();
        if(anim!=null)
        {
            TextureAtlas.AtlasRegion region = anim.getKeyFrame(animationTime);
            batch.draw(region,r.x,r.y - 0.03f,r.width/2,r.height/2,r.width,r.height,(flipX?-1:1)*1,(flipY?-1:1)*1,0);
        }
        
        spellFactory.draw();
    }

    @Override
    public void dispose() {
    }

    public void saveState() throws IOException
    {
        Rectangle r=getPos();
        FileWriter wr = new FileWriter("save_state.txt");
        String toWrite = ""+Livello.getGameplayTime()+"\n";
        toWrite+=r.x + " " + r.y + " " + hp + "\n";
        
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
    
    public void setLevelCompleted(boolean f)
    {
        levelCompleted=f;
    }
    
    public boolean isLevelCompleted()
    {
        return levelCompleted;
    }
    
    public boolean isInAir()
    {
        return inAir;
    }
    
    public boolean isAlive()
    {
        return alive;
    }
    
    public static float getMaxHp()
    {
        return hpMax;
    }
    
    public static float getHealPosX()
    {
        return healPosX;
    }
    
    public static float getHealPosY()
    {
        return healPosY;
    }

    public void setInAir(boolean f) 
    {
        inAir=f;
    }
    
    public static float getHP() 
    {
        return hp;
    }
    
    
    public static void setHP(float newhp) 
    {
        hp=newhp;
    }
}
