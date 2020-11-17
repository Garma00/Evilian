
package com.prog.world.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.entity.SpellFactory;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class Selector implements UIElement
{
    private final Texture tex;
    private final float original_pos;
    private final Rectangle pos;
    private final static SpellFactory sp=SpellFactory.getInstance();
    
    public Selector(float x,float y,float width,float height,String path)
    {
        this.pos = new Rectangle();
        pos.set(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
        
        this.original_pos=pos.x;
    }
    
    @Override
    public void draw() {
        batch.draw(tex,pos.x,pos.y,pos.width,pos.height);
    }
    
    @Override
    public void update()
    {
        //logica per il posizionamento della spadina
        if(sp.getSelectorPressed())
        {
            pos.setX(sp.getSpellSelector() == 0?original_pos:pos.x+1);
            sp.setSelectorPressed(false);
        }
    }

    @Override
    public void dispose() 
    {
        tex.dispose();
    }
}
