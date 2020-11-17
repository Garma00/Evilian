package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.prog.evilian.Evilian;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static com.prog.evilian.Evilian.batch;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EndLevel extends Livello implements Screen {
    
    private Score score;
    private BitmapFont font;
    private Array<Score> bestScores;
    
    public EndLevel(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian game) throws FileNotFoundException, IOException
    { 
        
        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, game);
        
        bestScores = new Array<Score>();
        getCam().setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        //leggo lo score dal file
        score = readScore();
        font = new BitmapFont(Gdx.files.internal("fonts/heinzheinrich.fnt"));
        this.score = readScore();
        //leggo gli score migliori
        loadBestScores();
        updateScores();
        writeBestScores();
    }
    
    @Override
    public void show(){
    }

    @Override
    public void render(float f)
    {   
        getCam().update();
        batch.setProjectionMatrix(getCam().combined);
        try {
            handleInput();
        } catch (IOException ex) {
            Logger.getLogger(EndLevel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //resetto la viewport di OpenGL
        Gdx.gl.glViewport(0,0,800,600);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        
        batch.begin();
        //try catch perche' dentro draw viene chiamata punteggi() che legge da file 
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
    
    public void draw() throws FileNotFoundException
    {
        font.setColor(Color.RED);
        font.draw(batch, "Hai totalizzato " + score.getPoints(), 10, 580);
        font.setColor(Color.WHITE);
        font.draw(batch, "I tuoi record:", (root.getScreenWidth() / 2) - 50,  (root.getScreenHeight() / 2) + 25);
        for(int i = 0; i < bestScores.size; i ++)
            font.draw(batch, (i+1)+". "+bestScores.get(i).getPoints() + " punti, " + bestScores.get(i).getDate() + " " + bestScores.get(i).getTime(), (root.getScreenWidth() / 2) - 50, (root.getScreenHeight() / 2) - (50  * i + 2));
        
        font.draw(batch, "Premi ESC per tornare al menu'", 10, 50);
            
        
    }
    
    //legge lo score effettuato durante la partita corrente dal file score
    private Score readScore() throws FileNotFoundException
    {
        
        File file = new File("score.txt");
        Scanner scan = new Scanner(file);
        String line = scan.nextLine();
        String[] words = line.split(" ");
        Score s = new Score(Integer.parseInt(words[0]));
        
        scan.close();
        return s;
    }
    
    
    
    //legge i record dal file
    private void loadBestScores() throws FileNotFoundException, IOException
    {
        File f = new File("general_info.txt");
        if(!f.exists())
        {
            FileWriter wr = new FileWriter("general_info.txt");
            wr.close();
        }
        Scanner scan = new Scanner(f);
        String[] words;
        Score s;
        while(scan.hasNextLine())
        {
            words = scan.nextLine().split(" ");
            s = new Score(Integer.parseInt(words[0]),words[1], words[2]);
            bestScores.add(s);
            
        }
        scan.close();
            
    }

    private void updateScores()
    {
        bestScores.add(score);
        if(bestScores.size <= 3)    
            sort(bestScores);
           
        
        else
        {
            sort(bestScores);
            bestScores.removeIndex(bestScores.size - 1);
        }
            
    }
    
    private void sort(Array<Score> bestScores)
    {
        bestScores.sort(new Comparator()
            {
                @Override
                public int compare(Object a, Object b) 
                {
                    //comparazione in maniera decrescente
                    return Integer.compare(((Score)b).getPoints(), ((Score)a).getPoints());
                }
            }

            );
    }
    
    private void writeBestScores() throws IOException
    {
        String toWrite;
        FileWriter wr = new FileWriter("general_info.txt");
        for(Score s: bestScores)
        {
            toWrite = "" + s.getPoints() + " " + s.getDate() + " " + s.getTime() + "\n";
            wr.write(toWrite);
        }
        
        wr.close();
    }
    
    public void handleInput() throws IOException
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            ManagerScreen.setIndex(-1);
            ManagerScreen.getInstance().changeScreen(null, root);
        }
    }
        
}
