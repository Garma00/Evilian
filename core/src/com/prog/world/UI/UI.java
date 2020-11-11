package com.prog.world.UI;

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
        HEALTH_SHADE,
        FB_BAR,
        IB_BAR,
        H_BAR,
        M_BAR,
        SELECTOR,
        TIMER
    };
    
    private final OrthographicCamera textCamera;
    private final OrthographicCamera cam;
    private final Viewport camvp;
    float UI_WIDTH;
    float UI_HEIGHT;
    //utilizzo 2 array per differenziare tra elementi di sfondo ed elementi in primo piano
    //elementi in background
    Array<UIElement> bgElements;
    //elementi in foreground
    Array<UIElement> fgElements;
    //uitext viene trattata separatamente perche' ha bisogno di avere la telecamera
    //impostata in modo diverso(per evitare testo troppo piccolo e sfocato)
    UIText timer;
    
    public UI(float uiWidth,float uiHeight)
    {
        UI_WIDTH=uiWidth/Evilian.PPM;
        UI_HEIGHT=uiHeight/Evilian.PPM;
        this.cam=new OrthographicCamera();
        cam.setToOrtho(false,UI_WIDTH,UI_HEIGHT);
        this.camvp=new ExtendViewport(UI_WIDTH,UI_HEIGHT,cam);
        
        this.textCamera = new OrthographicCamera();
        textCamera.setToOrtho(false, uiWidth, uiHeight);
        
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
        
        batch.setProjectionMatrix(textCamera.combined);
        batch.begin();
        timer.draw();
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
        switch(type)
        {
            case BACKGROUND:
                bgElements.add(new BackGround(x,y,width,height,path,type));
                break;
            case FOREGROUND:
                fgElements.add(new BackGround(x,y,width,height,path,type));
                break;
            case HEALTH_BAR:
                fgElements.add(new HealthBar(x,y,width,height,path,type));
                break;
            case HEALTH_SHADE:
                fgElements.add(new HealthShade(x,y,width,height,path,type));
                break;
            case FB_BAR:
                fgElements.add(new FBBar(x,y,width,height,path,type));
                break;
            case IB_BAR:
                fgElements.add(new IBBar(x,y,width,height,path,type));
                break;
            case H_BAR:
                fgElements.add(new HBar(x,y,width,height,path,type));
                break;
            case M_BAR:
                fgElements.add(new MBar(x,y,width,height,path,type));
                break;
            case SELECTOR:
                fgElements.add(new Selector(x,y,width,height,path,type));
                break;
        }
    }
    
    public void add(float x,float y, float width,float height, ElementType type)
    {
        timer = new UIText(x, y, width, height, type);
    }
    
    public void dispose()
    {
        for(UIElement e:bgElements)
            e.dispose();
        for(UIElement e:fgElements)
            e.dispose();
        
        timer.dispose();
    }
}