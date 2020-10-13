package com.prog.entity;

import static com.prog.evilian.Evilian.batch;
import com.prog.world.ManagerVfx;

public class GraphicButton extends Button{
    ManagerVfx mvfx;
    int effect;
    public GraphicButton(float x, float y, float width, float height, String userData, String path,int effect, ManagerVfx mvfx)
    {
        //i bottoni grafici hanno sempre il corrispettivo off
        super(x,y,width,height,userData,path,true);
        this.mvfx=mvfx;
        this.effect=effect;
        mvfx.addEffect(effect);
    }
    
    @Override
    public void draw()
    {
        if(isActive)
        {
            
            batch.draw(img, pos.x, pos.y, pos.width, pos.height);
        }
        else
        {
            batch.draw(img_off, pos.x, pos.y, pos.width, pos.height);
            mvfx.removeEffect(effect);
        }
    }
}
