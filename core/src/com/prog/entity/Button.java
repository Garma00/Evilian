package com.prog.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import com.prog.world.ManagerMusic;
import com.prog.world.ManagerSound;

public class Button extends Entity 
{
    Texture img;
    Texture img_off;
    private boolean isActive;
    private boolean hasDouble;
    public Button(float x, float y, float width, float height, String userData, String path, boolean isDouble)
    {
        super();
        setPos(x,y,width/Evilian.PPM,height/Evilian.PPM);
        Rectangle r=getPos();
        createBody(r.x,r.y, width, height, 0, userData, 0, 0, 0,(short)1,(short)2);
        this.img = new Texture (path);
        
        switch(((userDataContainer)getBody().getFixtureList().get(0).getUserData()).type)
        {
            case "sound":
                isActive=ManagerSound.getInstance().getVolume() == 0?false:true;
                break;
            case "music":
                isActive=ManagerMusic.getInstance().getVolume() == 0?false:true;
                break;
        }
        
        this.hasDouble=isDouble;
        if(isDouble)
            this.img_off = new Texture(path.replace(".png", "_off.png"));
    }
    
    
    
    @Override
    public void update(float delta) 
    {
        //chiamiamo getPos egetBody una sola volta per evitare di chiamarli piu' volte 
        //all'interno degli argomenti
        Body b=getBody();
        Rectangle r=getPos();
        super.setPos(b.getWorldCenter().x - (r.width/2),b.getWorldCenter().y - (r.height/2));  
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void draw()
    {
        //chiamiamo getPos una sola volta per evitare di chiamarlo piu' volte 
        //all'interno degli argomenti
        Rectangle r=getPos();
        if(hasDouble)
            batch.draw(isActive==true?img:img_off, r.x, r.y, r.width, r.height);
        else
            batch.draw(img, r.x, r.y, r.width, r.height);
    }

    @Override
    public void dispose() {
    }
    
    public void setActive(boolean f)
    {
        isActive=f;
    }
    
}
