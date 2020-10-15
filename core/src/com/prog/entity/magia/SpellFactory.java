package com.prog.entity.magia;

import com.badlogic.gdx.utils.Pool;


public class SpellFactory{
    public enum SpellType {
      PALLADIFUOCO,
      PALLADIGHIACCIO,
      CURA,
      METEORA
    };

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

    //chaimala quando vuoi creare una spell
    public Magia createSpell(SpellType monsterType){
       switch(monsterType){
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
    public void destroySpell(Magia spell){
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
}
