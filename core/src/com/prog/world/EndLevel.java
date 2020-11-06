package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.prog.evilian.Evilian;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static com.prog.evilian.Evilian.batch;

public class EndLevel extends Livello implements Screen {
    
    private float score;
    private BitmapFont font;
    private OrthographicCamera endCamera;
    
    
    public EndLevel(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian game) throws FileNotFoundException
    { 
        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, game);
        endCamera = new OrthographicCamera();
        endCamera.setToOrtho(resume, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        
        //leggo lo score dal file
        score = readScore();
        font = new BitmapFont();
        
        
    }
    
    @Override
    public void show() {
    }

    @Override
    public void render(float f)
    {
        endCamera.update();
        batch.setProjectionMatrix(endCamera.combined);
        
        
        batch.begin();
        draw();
        batch.end();
        
    }

    @Override
    public void resize(int i, int i1) {
        
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
    public void draw()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        font.draw(batch, "hai totalizzato " + score , root.SCREEN_WIDTH /  2, root.SCREEN_HEIGHT / 2);
        
        
        
    }
    
    private float readScore() throws FileNotFoundException
    {
        File file = new File("score.txt");
        Scanner scan = new Scanner(file);
        float s = Float.parseFloat(scan.nextLine());
        scan.close();
        return s;
    }
    
}
