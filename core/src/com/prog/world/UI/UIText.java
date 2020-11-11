package com.prog.world.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.prog.world.Livello;
import static com.prog.evilian.Evilian.batch;
import com.prog.world.UI.UI.ElementType;


public class UIText
{
    private Rectangle pos;
    private BitmapFont font;
    ElementType type;
    
    public UIText(float x, float y, float width, float height, ElementType type)
    {
        //disegnamo il testo usando la posizione in pixel con una nuova telecamera 
        this.pos=new Rectangle(x,y,width,height);
        
        this.type=type;
        
        //this.original_pos=this.pos.x;
        font = new BitmapFont(Gdx.files.internal("fonts/heinzheinrich.fnt"));
    
    }
    
    public void draw()
    {
        font.draw(batch, String.valueOf((int)Livello.getGameplayTime()), pos.x, pos.y);
    }
    
    public void dispose()
    {
        font.dispose();
    }
}
