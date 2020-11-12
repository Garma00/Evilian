package com.prog.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.ChromaticAberrationEffect;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;
import com.crashinvaders.vfx.effects.MotionBlurEffect;
import com.crashinvaders.vfx.effects.OldTvEffect;
import com.crashinvaders.vfx.effects.util.MixEffect;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;

public class ManagerVfx {
    public static final int BLOOM_EFFECT=1;
    public static final int GBLUR_EFFECT=2;
    public static final int MBLUR_EFFECT=4;
    public static final int CHROME_EFFECT=8;
    public static final int OLDTV_EFFECT=16;
    
    private final VfxManager manager=new VfxManager(Pixmap.Format.RGBA8888);
    private final OldTvEffect oldtv=new OldTvEffect();
    private final BloomEffect bloom= new BloomEffect();
    private final GaussianBlurEffect gblur=new GaussianBlurEffect();
    private final MotionBlurEffect mblur=new MotionBlurEffect(Pixmap.Format.RGBA8888,MixEffect.Method.MAX,0.9f);
    private final ChromaticAberrationEffect chrome=new ChromaticAberrationEffect(5);
    
    private static ManagerVfx INSTANCE=null;
    
    int active;
    
    public ManagerVfx()
    {
    }
    
    public void addEffect(int wanted)
    {
        switch(wanted)
        {
            case BLOOM_EFFECT:
                //System.out.println("aggiunta bloom");
                manager.addEffect(bloom);
                break;
            case GBLUR_EFFECT:
                //System.out.println("aggiunta gblur");
                manager.addEffect(gblur);
                break;
            case MBLUR_EFFECT:
                //System.out.println("aggiunta mblur");
                manager.addEffect(mblur);
                break;
            case CHROME_EFFECT:
                //System.out.println("aggiunta chrome");
                manager.addEffect(chrome);
                break;
            case OLDTV_EFFECT:
                //System.out.println("aggiunta oldtv");
                manager.addEffect(oldtv);
                break;
            default:
                System.out.println("Effect not recognized");
        }
    }
    
    public void removeEffect(int wanted)
    {
        switch(wanted)
        {
            case BLOOM_EFFECT:
                manager.removeEffect(bloom);
                break;
            case GBLUR_EFFECT:
                manager.removeEffect(gblur);
                break;
            case MBLUR_EFFECT:
                manager.removeEffect(mblur);
                break;
            case CHROME_EFFECT:
                manager.removeEffect(chrome);
                break;
            case OLDTV_EFFECT:
                manager.removeEffect(oldtv);
                break;
            default:
                System.out.println("Effect not recognized");
        }
    }
    
    public void initCapture()
    {
        manager.cleanUpBuffers(new Color(0,0,0,0));
        manager.beginInputCapture();
    }
    
    public void endCapture()
    {
        manager.endInputCapture();
        
    }
    
    public void enableBlend(boolean flag)
    {
        manager.setBlendingEnabled(flag);
    }
    
    public void applyEffects()
    {
        //applico effetti
        manager.applyEffects();
    }
    
    public void render()
    {
        //applico effetti
        manager.applyEffects();
        //renderizzo su schermo
        manager.renderToScreen();
    }
    
    public void render(int x,int y,int width,int height)
    {
        //applico effetti
        manager.applyEffects();
        //renderizzo su schermo
        manager.renderToScreen(x, y, width, height);
    }
    
    public void resize(int width,int height)
    {
        manager.resize(width, height);
    }
    
    public void removeAllEffects()
    {
        manager.removeAllEffects();
    }
    
    public void editBloom(float intensity,float saturation,float threshold,int bloom_passes)
    {
        bloom.setBloomIntensity(intensity);
        bloom.setBloomSaturation(saturation);
        bloom.setThreshold(threshold);
        bloom.setBlurPasses(bloom_passes);
    }
    
    public Texture renderToTexture(int width,int height)
    {
        VfxFrameBuffer output=new VfxFrameBuffer(Pixmap.Format.RGBA8888);
        output.initialize(width,height);
        manager.renderToFbo(output);
        return output.getTexture();
    }
    
    public void dispose()
    {
        manager.removeAllEffects();
        manager.dispose();
    }
    
    public static ManagerVfx getInstance()
    {
        if(INSTANCE==null)
            INSTANCE=new ManagerVfx();
        return INSTANCE;
    }
}
