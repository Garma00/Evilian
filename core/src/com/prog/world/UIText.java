package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import static com.prog.evilian.Evilian.batch;
import com.prog.world.UI.ElementType;


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
        //font.getData().setScale(1/3f, 1/6f);
    
    }
    
    public void draw()
    {
        //batch.draw(new Texture("images/ui/sword.png"), pos.x, pos.y, pos.width, pos.height);
        font.draw(batch, String.valueOf((int)Livello.gameplayTime), pos.x, pos.y);
    }
    
}
