package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;

public class UI 
{   
    public enum ElementType
    {
        BACKGROUND,
        FOREGROUND,
        HEALTH_BAR,
        HEALTH_SHADE
    };
    
    private OrthographicCamera cam;
    private Viewport camvp;
    public static float UI_WIDTH;
    public static float UI_HEIGHT;
    //utilizzo 2 array per differenziare tra elementi di sfondo ed elementi in primo piano
    //elementi in background
    Array<UIElement> bgElements;
    //elementi in foreground
    Array<UIElement> fgElements;
    
    public UI(float uiWidth,float uiHeight)
    {
        UI_WIDTH=uiWidth/Evilian.PPM;
        UI_HEIGHT=uiHeight/Evilian.PPM;
        this.cam=new OrthographicCamera();
        cam.setToOrtho(false,UI_WIDTH,UI_HEIGHT);
        this.camvp=new ExtendViewport(UI_WIDTH,UI_HEIGHT,cam);
        
        bgElements=new Array<UIElement>();
        fgElements=new Array<UIElement>();
    }
    
    public void draw()
    {
        batch.setProjectionMatrix(cam.combined);
        Gdx.gl.glViewport(0,0,(int)(UI_WIDTH*Evilian.PPM),(int)(UI_HEIGHT*Evilian.PPM));
        batch.begin();
        for(UIElement e: bgElements)
            e.draw();
        for(UIElement e: fgElements)
            e.draw();
        batch.end();
    }
    
    public void update()
    {
        cam.update();
        for(UIElement e:fgElements)
                e.update();
    }
    
    public void resize(int width,int height)
    {
        //da sistemare il resize
        camvp.update(width,height);
    }
    
    public void add(float x,float y, float width,float height, String path,ElementType type)
    {
        UIElement e=new UIElement(x,y,width,height,path,type);
        switch(type)
        {
            case BACKGROUND:
                bgElements.add(e);
                break;
            case FOREGROUND:
                fgElements.add(e);
                break;
            case HEALTH_BAR:
                fgElements.add(e);
                break;
            case HEALTH_SHADE:
                fgElements.add(e);
                break;
            default:
                e.dispose();
                e=null;
        }
    }
}