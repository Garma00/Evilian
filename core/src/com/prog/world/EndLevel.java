package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.prog.evilian.Evilian;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static com.prog.evilian.Evilian.batch;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.prog.world.ManagerScreen.index;
import static com.prog.world.ManagerScreen.MANAGER_SCREEN;

public class EndLevel extends Livello implements Screen {
    
    private Score score;
    private BitmapFont font;
    Array<Score> bestScores;
    
    public EndLevel(int SCREEN_WIDTH, int SCREEN_HEIGHT, Evilian game) throws FileNotFoundException, IOException
    { 
        
        super(false, SCREEN_WIDTH, SCREEN_HEIGHT, game);
        
        bestScores = new Array<Score>();
        cam.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.score = readScore();
        font = new BitmapFont();
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
        //resetto la viewport
        
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        try {
            handleInput();
        } catch (IOException ex) {
            Logger.getLogger(EndLevel.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gdx.gl.glViewport(0,0,800,600);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        
        batch.begin();
        //try catch perchè dentro draw viene chiamata punteggi() che legge da file 
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
        font.draw(batch, "hai totalizzato " + score.getPoints(), 10, 580);
        font.draw(batch, "i tuoi record", (root.SCREEN_WIDTH / 2) - 50,  (root.SCREEN_HEIGHT / 2) + 25);
        for(int i = 0; i < bestScores.size; i ++)
            font.draw(batch, ""+bestScores.get(i).getPoints() + " " + bestScores.get(i).getDate() + " " + bestScores.get(i).getTime(), (root.SCREEN_WIDTH / 2) - 50, (root.SCREEN_HEIGHT / 2) - (50  * i + 2));
        
        font.draw(batch, "Premi ESC per tornare al menù", 10, 20);
            
        
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.ITALIAN);
        while(scan.hasNextLine())
        {
            words = scan.nextLine().split(" ");
            s = new Score(Integer.parseInt(words[0]), LocalDate.parse(words[1], formatter), LocalTime.parse(words[2]));
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
            index = -1;
            MANAGER_SCREEN.changeScreen(null, root);
            
        }
    }
        
}
