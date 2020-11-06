package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.prog.evilian.Evilian;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static com.prog.evilian.Evilian.batch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EndLevel extends Livello implements Screen {
    
    private float score;
    private BitmapFont font;
    
    public EndLevel(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian game) throws FileNotFoundException
    { 
        
        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, game);
        cam.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        //leggo lo score dal file
        score = readScore();
        font = new BitmapFont();
    
    }
    
    @Override
    public void show(){
    }

    @Override
    public void render(float f)
    {
        //resetto la viewport
        
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        
        Gdx.gl.glViewport(0,0,800,600);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        //try cathc perchè dentro draw viene chiamata punteggi() che legge da file 
        try {
            draw();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EndLevel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    //punteggi() causa throws FileNotFoundException
    public void draw() throws FileNotFoundException
    {
        font.draw(batch, "hai totalizzato "+score , root.SCREEN_WIDTH / 2, root.SCREEN_HEIGHT / 2);
        font.draw(batch, "i tuoi record", root.SCREEN_WIDTH / 2,  (root.SCREEN_HEIGHT / 2) - 25);
        punteggi();
    }
    
    //legge lo score effettuato durante la partita corrente dal file score
    private float readScore() throws FileNotFoundException
    {
        File file = new File("score.txt");
        Scanner scan = new Scanner(file);
        float s = Float.parseFloat(scan.nextLine());
        scan.close();
        return s;
    }
    
    //legge i record dal file e li stampa
    private void punteggi() throws FileNotFoundException
    {
        File f = new File("general_info.txt");
        Scanner scan = new Scanner(f);
        int i = 2;
        while(scan.hasNextLine())
        {
            font.draw(batch, scan.nextLine(), root.SCREEN_WIDTH / 2, (root.SCREEN_HEIGHT / 2) - 25 * i);
            i ++;
        }
            
        scan.close();
    }
}
