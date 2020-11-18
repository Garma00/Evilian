package com.prog.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;
import com.prog.world.ManagerSound;

public final class SpellFactory{

    private static SpellFactory INSTANCE=null;
    private final Mouse mouse=Mouse.getInstance();
    private long time;
    private int spellSelector;
    private final Array<Magia> activeSpells;
    private final long[] lastLaunch;
    private boolean selectorPressed;

    private SpellFactory()
    {
        spellSelector=0;
        activeSpells=new Array<Magia>();
        time=TimeUtils.millis();
        lastLaunch=new long[4];
        selectorPressed=false;
    }

    //chiamala quando vuoi creare una spell
    private Magia createSpell(int spell){
       switch(spell)
       {
         case 0:
            return Pools.obtain(PallaDiFuoco.class);
         case 1:
            return Pools.obtain(PallaDiGhiaccio.class);
         case 2:
             return Pools.obtain(Cura.class);
         case 3:
             return Pools.obtain(Meteora.class);
       }
       //altrimenti ritorno null
        return null;
    }

    //chiamala quando vuoi distruggere una spell
    private void destroySpell(Magia spell)
    {
       Pools.free(spell);
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
        
        if(spellSelector == 3)
            res = launchMeteor();
        m=createSpell(spellSelector);
        
        m.init(pg_pos, res);
        //logica cooldown magie
        if(time-lastLaunch[spellSelector]>m.getCoolDown())
        {
            activeSpells.add(m);
            lastLaunch[spellSelector]=time;
            ManagerSound.getInstance().selectSound(spellSelector);
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
            if(!item.isAlive())
            {
                activeSpells.removeIndex(i);
                destroySpell(item);
            }
        }
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
    

    
    public void clearActiveSpells()
    {
        activeSpells.clear();
        spellSelector=0;
    }
    
    public static SpellFactory getInstance()
    {
        if(INSTANCE==null)
            INSTANCE=new SpellFactory();
        return INSTANCE;
    }
    
    public long[] getLastLaunch()               {return lastLaunch;}
    public boolean getSelectorPressed()         {return selectorPressed;}
    public int getSpellSelector()               {return spellSelector;}
    public long getTime()                       {return time;}
}
