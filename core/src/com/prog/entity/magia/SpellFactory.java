package com.prog.entity.magia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.prog.entity.Mouse;
import com.prog.evilian.Evilian;


public class SpellFactory{

    private static SpellFactory INSTANCE=null;
    final Mouse mouse=Mouse.getInstance();
    private long time;
    private int spellSelector;
    private Array<Magia> activeSpells;
    private long[] lastLaunch;
    private boolean selectorPressed;
    
    public enum SpellType {
      PALLADIFUOCO,
      PALLADIGHIACCIO,
      CURA,
      METEORA
    };

    public SpellFactory()
    {
        spellSelector=0;
        activeSpells=new Array<Magia>();
        time=TimeUtils.millis();
        lastLaunch=new long[4];
        selectorPressed=false;
    }
    
    private final Pool<PallaDiFuoco> fireballPool = new Pool<PallaDiFuoco>() {
        @Override
        protected PallaDiFuoco newObject() {
            return new PallaDiFuoco();
        }
    };
    private final Pool<PallaDiGhiaccio> iceballPool = new Pool<PallaDiGhiaccio>() {
        @Override
        protected PallaDiGhiaccio newObject() {
            return new PallaDiGhiaccio();
        }
    };
    private final Pool<Cura> healPool = new Pool<Cura>() {
        @Override
        protected Cura newObject() {
            return new Cura();
        }
    };
    private final Pool<Meteora> meteorPool = new Pool<Meteora>() {
        @Override
        protected Meteora newObject() {
            return new Meteora();
        }
    };

    //chiamala quando vuoi creare una spell
    public Magia createSpell(SpellType spell){
       switch(spell){
         case PALLADIFUOCO:
            return fireballPool.obtain();
         case PALLADIGHIACCIO:
            return iceballPool.obtain();
         case CURA:
             return healPool.obtain();
         case METEORA:
             return meteorPool.obtain();
       }
       //altrimenti ritorno null
        return null;
    }

    //chiamala quando vuoi distruggere una spell
    public void destroySpell(Magia spell)
    {
       if(spell instanceof PallaDiFuoco){
         fireballPool.free((PallaDiFuoco)spell);
       }
       else if(spell instanceof PallaDiGhiaccio){
         iceballPool.free((PallaDiGhiaccio)spell);
       }else if(spell instanceof Cura){
         healPool.free((Cura)spell);
       }else if(spell instanceof Meteora){
         meteorPool.free((Meteora)spell);
       }
    }
    
    public void addToSpellSelector(int i)
    {
        spellSelector=(spellSelector+i)%4;
    }
    
    //tutta la logica per chiamare e lanciare le magie
    public Vector2 computeDistanceVector(Vector2 pg)
    {
        Vector3 mouse_pos = mouse.fixedPosition(Gdx.input.getX(), Gdx.input.getY(), mouse.getCam());
        Vector2 m = new Vector2(mouse_pos.x, mouse_pos.y);
        Vector2 tmp = m.cpy().sub(pg).nor();
        
        selectSpell(tmp,pg);
        
        //lo ritorno perche' il player dovra' girarsi in base a dove lancia la skill
        return tmp;
    }
    
    private void selectSpell(Vector2 res,Vector2 pg_pos)
    {
        Magia m = null;
        
        switch(spellSelector)
        {
            case 0:
                m=createSpell(SpellFactory.SpellType.PALLADIFUOCO);
                break;
            case 1:
                m=createSpell(SpellFactory.SpellType.PALLADIGHIACCIO);
                break;
            case 2:
                m=createSpell(SpellFactory.SpellType.CURA);
                break;
            case 3:
                res = launchMeteor();
                m=createSpell(SpellFactory.SpellType.METEORA);
                break;
        }

        
        m.init(pg_pos, res);
        //logica cooldown magie
        if(time-lastLaunch[spellSelector]>m.COOLDOWN)
        {
            activeSpells.add(m);
            lastLaunch[spellSelector]=time;
            Evilian.getManagerSound().selectSound(spellSelector);
        }else
            destroySpell(m);
    }
    
    private Vector2 launchMeteor()
    {
        Vector3 m_pos = mouse.fixedPosition(Gdx.input.getX(), Gdx.input.getY(), mouse.getCam());
        //meteora spawna a 300px sopra il personaggio(+3m ---> 3m * 100px/m = 300px)
        return new Vector2(m_pos.x, m_pos.y + 3f);
    }
    
    public void update(float d)
    {
        time=TimeUtils.millis();
        for(Magia m:activeSpells)
            m.update(d);
        
        for(int i=0;i<activeSpells.size;i++)
        {
            Magia item=activeSpells.get(i);
            if(!item.alive)
            {
                activeSpells.removeIndex(i);
                destroySpell(item);
            }
        }
    }
    
    public long getTime()
    {
        return time;
    }
    
    
    public void setSelectorPressed(boolean b) 
    {
        this.selectorPressed=b;
    }
    
    
    public void draw() 
    {
        for(Magia m:activeSpells)
            m.draw();
    }
    
    public long[] getLastLaunch()
    {
        return lastLaunch;
    }
    
    public boolean getSelectorPressed()
    {
        return selectorPressed;
    }
    
    public int getSpellSelector()
    {
        return spellSelector;
    }
    
    public void clearActiveSpells()
    {
        activeSpells.clear();
    }
    
    public static SpellFactory getInstance()
    {
        if(INSTANCE==null)
            INSTANCE=new SpellFactory();
        return INSTANCE;
    }
}
