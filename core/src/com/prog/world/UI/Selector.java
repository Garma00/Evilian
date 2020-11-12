
package com.prog.world.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class Selector implements UIElement
{
    private Texture tex;
    private float original_pos;
    final Rectangle pos=new Rectangle();
    
    public Selector(float x,float y,float width,float height,String path)
    {
        pos.set(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
        
        this.original_pos=pos.x;
    }
    
    @Override
    public void draw() {
        batch.draw(tex,pos.x,pos.y,pos.width,pos.height);
    }
    
    public void update()
    {
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
