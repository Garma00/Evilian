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
    private final OrthographicCamera textCamera;
    private final OrthographicCamera cam;
    private final Viewport camvp;
    private final float UI_WIDTH;
    private final float UI_HEIGHT;
    //utilizzo 2 array per differenziare tra elementi di sfondo ed elementi in primo piano
    //elementi in background
    private Array<UIElement> elements;
    //elementi in foreground
    //uitext viene trattata separatamente perche' ha bisogno di avere la telecamera
    //impostata in modo diverso(per evitare testo troppo piccolo e sfocato)
    private UIText timer;
    
    public UI(float uiWidth,float uiHeight)
    {
        UI_WIDTH=uiWidth/Evilian.PPM;
        UI_HEIGHT=uiHeight/Evilian.PPM;
        this.cam=new OrthographicCamera();
        cam.setToOrtho(false,UI_WIDTH,UI_HEIGHT);
        this.camvp=new ExtendViewport(UI_WIDTH,UI_HEIGHT,cam);
        
        this.textCamera = new OrthographicCamera();
        textCamera.setToOrtho(false, uiWidth, uiHeight);
        
        elements=new Array<UIElement>();
    }
    
    public void draw()
    {
        batch.setProjectionMatrix(cam.combined);
        Gdx.gl.glViewport(0,0,(int)(UI_WIDTH*Evilian.PPM),(int)(UI_HEIGHT*Evilian.PPM));
        batch.begin();
        for(UIElement e: elements)
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
        for(UIElement e:elements)
                e.update();
    }
    
    public void resize(int width,int height)
    {
        //da sistemare il resize
        camvp.update(width,height);
    }
    
    public void add(UIElement ue)
    {
        //il testo deve essere trattato diversamente
        if(ue instanceof UIText)
            timer=(UIText)ue;
        else
            elements.add(ue);
    }
    
    public void dispose()
    {
        for(UIElement e:elements)
            e.dispose();
        
        timer.dispose();
    }
}