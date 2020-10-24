package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.prog.collision.LevelContactListener;
import com.prog.entity.Entity;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import static com.prog.evilian.Evilian.batch;
import static com.prog.evilian.Evilian.MANAGER_MUSIC;
import com.prog.world.AI.Node;
import com.prog.world.AI.NodeGraph;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Livello1 extends Livello implements Screen{
    Player p;
    float delta;
    LevelContactListener lcl;
    GraphPath<Node> res_path;
    int startIndex=0;
    int goalIndex=96;
    NodeGraph graph;
    
    ShapeDrawer sd=new ShapeDrawer(batch,new TextureRegion(new Texture("images/white_pixel.png")));

    
    public Livello1(float gravity, boolean Sleep, String path, float cameraWidth, float cameraHeight,float uiWidth,float uiHeight, Evilian game)
    {
        super(gravity, Sleep, path, cameraWidth, cameraHeight,uiWidth,uiHeight,game);
        lcl=new LevelContactListener();
        world.setContactListener(lcl);

        //prendo i poligoni della mappa e li inserisco nel mondo
        parseCollisions(world,map.getLayers().get("Collision_layer").getObjects());
        
        //ho bisgno di passare il listener come parametro per avere il flag inAir
        p=new Player(mouse);
        
        entities.add(p);
        
        //bisogna distruggere il mouse altrimenti il mouse nel livello1 avrebbe la gravit� applicata essendo un body
        world.destroyBody(mouse.body);
        
        MANAGER_MUSIC.selectMusic(2);
        
        //diamo un po' di zoom alla telecamera per un gameplay migliore
        cam.zoom-=0.5;
        
        //cam.translate(0f,-1f);
        //NOTA: METTI GLI ELEMENTI IN ORDINE
        level_ui.add(0,0,800,75,"images/ui/bg.png",UI.ElementType.BACKGROUND);
        //fb
        level_ui.add(300,15,40,40,"images/ui/fireball_2.png",UI.ElementType.FOREGROUND);
        level_ui.add(301,56,38,4,"images/ui/health_only.png", UI.ElementType.FB_BAR);
        level_ui.add(300, 55, 40, 6, "images/ui/skill_bar.png", UI.ElementType.FOREGROUND);
        
        //ib
        level_ui.add(400, 15, 40, 40, "images/ui/iceball.png", UI.ElementType.FOREGROUND);
        level_ui.add(401,56,38,4,"images/ui/health_only.png", UI.ElementType.IB_BAR);
        level_ui.add(400, 55, 40, 6, "images/ui/skill_bar.png", UI.ElementType.FOREGROUND);
        
        //heal
        level_ui.add(500, 15, 40, 40, "images/ui/heal.png", UI.ElementType.FOREGROUND);
        level_ui.add(501,56,38,4,"images/ui/health_only.png", UI.ElementType.H_BAR);
        level_ui.add(500, 55, 40, 6, "images/ui/skill_bar.png", UI.ElementType.FOREGROUND);
        
        
        //meteor
        level_ui.add(600, 15, 40, 40, "images/ui/meteor.png", UI.ElementType.FOREGROUND);
        level_ui.add(601,56,38,4,"images/ui/health_only.png", UI.ElementType.M_BAR);
        level_ui.add(600, 55, 40, 6, "images/ui/skill_bar.png", UI.ElementType.FOREGROUND);
        
        //health
        level_ui.add(56,31,50*3,4*3,"images/ui/health_only.png", UI.ElementType.HEALTH_BAR);
        level_ui.add(56,31,50*3,1*3,"images/ui/health_only_shade.png", UI.ElementType.HEALTH_SHADE);
        level_ui.add(20,25,64*3,8*3,"images/ui/health_bar_empty.png",UI.ElementType.FOREGROUND);
        
        //selector
        level_ui.add(257,25,40,16,"images/ui/sword.png", UI.ElementType.SELECTOR);
        
        graph=parseNodes();
        Node start=graph.nodeArray.get(startIndex);
        Node goal=graph.nodeArray.get(goalIndex);
        System.out.println("p "+start.x+"\t"+start.y+"\na "+goal.x+"\t"+goal.y);
        res_path=graph.findPath(start, goal);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        
                
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
        {
            goalIndex++;
            System.out.println(goalIndex);
            Node start=graph.nodeArray.get(startIndex);
            Node goal=graph.nodeArray.get(goalIndex);
            System.out.println(goal.x+"\t"+goal.y);
            res_path=graph.findPath(start, goal);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
        {
            goalIndex--;
            Node start=graph.nodeArray.get(startIndex);
            Node goal=graph.nodeArray.get(goalIndex);
            res_path=graph.findPath(start, goal);
        }

        cam.position.set(Math.max(p.pos.x+0.5f,2f), Math.max(p.pos.y+0.2f,1.4f),0f);
        cam.update();
        level_ui.update();
        batch.setProjectionMatrix(cam.combined);
        
        //handleinput entita'
        for(Entity e:entities)
            e.handleInput();
        
        
        //handleinput del livello
        handleInput();
        
        //guardo entities e faccio gli update
        for(Entity e:entities)
            e.update(f);
        
        //draw
        draw();
        level_ui.draw();
        
        world.step(1/60f,6,2);
    }

    @Override
    public void resize(int width, int height) {
        //da sistemare il discorso del resize(molto confusionario con le viewport)
        //mvfx.resize(width, height);
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
    public void dispose()
    {
        super.dispose();
    }
    
    public void draw()
    {   
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0,75,800, 525);
        //prima renderizzo la mappa e poi il player o altre cose
        mapRenderer.setView(cam);
        mapRenderer.render();
        //guardo entities e renderizzo cose
        batch.begin();
        for(Entity e:entities)
            e.draw();
        for(int i=0;i<res_path.getCount();i++)
        {
            sd.setColor(1f, 1f, 1f, 1f);
            if(i != 0)
                sd.line((res_path.get(i-1).x/Evilian.PPM)+0.16f, (res_path.get(i-1).y/Evilian.PPM)+0.32f, (res_path.get(i).x/Evilian.PPM)+0.16f, (res_path.get(i).y/Evilian.PPM)+0.32f,0.05f);
            else
                sd.setColor(0f, 1f, 0f, 1f);
            sd.filledCircle((res_path.get(i).x/Evilian.PPM)+0.16f,(res_path.get(i).y/Evilian.PPM)+0.32f,0.1f);
        }
        batch.end();
        debug.render(world, cam.combined);
    }

}
