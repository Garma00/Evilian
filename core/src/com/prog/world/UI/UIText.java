package com.prog.world.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.prog.world.Livello;
import static com.prog.evilian.Evilian.batch;


public class UIText implements UIElement
{
    private final BitmapFont font;
    final Rectangle pos=new Rectangle();
    //ElementType type;
    
    public UIText(float x, float y, float width, float height)
    {
        //disegnamo il testo usando la posizione in pixel con una nuova telecamera 
        pos.set(x,y,width,height);
        font = new BitmapFont(Gdx.files.internal("fonts/heinzheinrich.fnt"));
    }
    
    @Override
    public void draw()
    {
        font.draw(batch, String.valueOf((int)Livello.getGameplayTime()), pos.x, pos.y);
    }
    
    @Override
    public void dispose()
    {
        font.dispose();
    }

    @Override
    public void update() 
    {
    }
}
