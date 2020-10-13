package com.prog.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.ChromaticAberrationEffect;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;
import com.crashinvaders.vfx.effects.MotionBlurEffect;
import com.crashinvaders.vfx.effects.OldTvEffect;
import com.crashinvaders.vfx.effects.util.MixEffect;

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
    private final ChromaticAberrationEffect chrome=new ChromaticAberrationEffect(15);
    
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
                bloom.dispose();
                break;
            case GBLUR_EFFECT:
                gblur.dispose();
                break;
            case MBLUR_EFFECT:
                mblur.dispose();
                break;
            case CHROME_EFFECT:
                chrome.dispose();
                break;
            case OLDTV_EFFECT:
                oldtv.dispose();
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
    
    public void dispose()
    {
        manager.removeAllEffects();
        manager.dispose();
    }
}
