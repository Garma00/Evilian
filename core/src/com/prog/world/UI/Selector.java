
package com.prog.world.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class Selector extends UIElement
{
    private final Rectangle r;
    private float original_pos;
    
    public Selector(float x,float y,float width,float height,String path,UI.ElementType type)
    {
        super();
        r=getPos();
        setPos(x/Evilian.PPM,y/Evilian.PPM,width/Evilian.PPM,height/Evilian.PPM);
        this.tex=new Texture(path);
        this.type=type;
        
        this.original_pos=r.x;
    }
    
    @Override
    public void draw() {
        batch.draw(tex,r.x,r.y,r.width,r.height);
    }
    
    public void update()
    {
        if(sp.getSelectorPressed())
        {
            setPosX(sp.getSpellSelector() == 0?original_pos:r.x+1);
            sp.setSelectorPressed(false);
        }
    }
    
}
