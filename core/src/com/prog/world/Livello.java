package com.prog.world;

import com.prog.world.UI.UI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.prog.entity.Enemy;
import com.prog.entity.EnemyFactory;
import com.prog.entity.EnemyFactory.EnemyType;
import com.prog.entity.Entity;
import com.prog.entity.Mouse;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import com.prog.world.UI.BackGround;
import com.prog.world.UI.FBBar;
import com.prog.world.UI.HBar;
import com.prog.world.UI.HealthBar;
import com.prog.world.UI.HealthShade;
import com.prog.world.UI.IBBar;
import com.prog.world.UI.MBar;
import com.prog.world.UI.Selector;
import com.prog.world.UI.UIText;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Livello {
    Player p;
    FileWriter wr;
    protected static World world;
    protected static Box2DDebugRenderer debug;
    //mappa tiled
    TiledMap map;
    //renderer mappa ortogonale(esiste anche l'isometric ma non serve al nostro gioco)
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera cam;
    Viewport camvp;
    Array<Entity> entities;
    Evilian root;
    private static TextureAtlas atlas;
    Mouse mouse;
    protected static final ManagerVfx mvfx=ManagerVfx.getInstance();
    UI level_ui;
    boolean resume;
    File file;//file per il caricamento dello stato
    EnemyFactory ef;
    private static int points;
    protected static float gameplayTime;

    public Livello(float gravity, boolean Sleep, String path, float cameraWidth, float cameraHeight,float uiWidth,float uiHeight,Evilian game) throws IOException
    {
        
        //mi serve il riferimento alla classe root per poi cambiare screen (o livelli)
        root=game;
        debug=new Box2DDebugRenderer();
        Player p = null;
        //creiamo il mondo
        //NOTA:inserire gravita' negativa da parametro
        world=new World(new Vector2(0,gravity),Sleep);
        mouse=Mouse.getInstance();
        entities=new Array<Entity>();
        //TmxMapLoader e' il loader del file che descrive la tiled map
        //il file dovra' avere estensione .tmx
        map= new TmxMapLoader().load(path);
        //si puo' inserire uno scale come secondo parametro
        mapRenderer=new OrthogonalTiledMapRenderer(map,1/Evilian.PPM);
        cam=new OrthographicCamera();
        
        cam.setToOrtho(false,cameraWidth/Evilian.PPM,cameraHeight/Evilian.PPM);
        
        //inizializzo la viewport come fit (non importa la grandezza della finestra, vedremo sempre la stessa regione(con barre nere se neceessarie ai lati))
        camvp=new ExtendViewport(cameraWidth/Evilian.PPM,cameraHeight/Evilian.PPM,cam);
        level_ui=new UI(uiWidth,uiHeight);
        
        atlas=new TextureAtlas("atlas/game.atlas");
        
        file = new File("save_state.txt");
        mouse.addCamToMouse(cam);
        mouse.addToWorld();
        //NOTA: ogni frame nel render dovremo chiamare mapRenderer.setView(camera) e poi mapRenderer.render()
    
        //da fare un metodo che richiama dalle opzioni quali effetti sono attivi
        ef = new EnemyFactory();
        gameplayTime = 0;
        points = 0;

        
    }
    
    public Livello(boolean Sleep, int cameraWidth, int cameraHeight, Evilian game)
    {
        root = game;
        debug = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), Sleep);
        mouse=Mouse.getInstance();
        entities = new Array<Entity>();
        cam = new OrthographicCamera();
        cam.setToOrtho(false,cameraWidth/Evilian.PPM,cameraHeight/Evilian.PPM);
        
        camvp=new FitViewport(game.getScreenWidth()/Evilian.PPM,game.getScreenHeight()/Evilian.PPM,cam);
        atlas=new TextureAtlas("atlas/game.atlas");

        mouse.addCamToMouse(cam);
        mouse.addToWorld();
    }
    
    public void dispose()
    {
        //i livelli tipo menu' non hanno map istanziata
        if(map != null)
        {
            map.dispose();
            mapRenderer.dispose();
        }
        atlas.dispose();
        mvfx.removeAllEffects();
        if(level_ui != null)
            level_ui.dispose();
    }
    
    public void parseCollisions(MapObjects objects)
    {
        for(MapObject o: objects)
        {
            Shape s;
            if (o instanceof RectangleMapObject) {
                s = getRectangle((RectangleMapObject)o);
            }
            else if (o instanceof PolygonMapObject) {
                s = getPolygon((PolygonMapObject)o);
            }
            else if (o instanceof CircleMapObject) {
                s = getCircle((CircleMapObject)o);
            }
            else {
                continue;
            }
            
            Body body;
            BodyDef bdef= new BodyDef();
            bdef.type=BodyDef.BodyType.StaticBody;
            body=world.createBody(bdef);
            FixtureDef fdef=new FixtureDef();
            fdef.friction=0f;
            fdef.shape=s;
            fdef.density=1f;
            MapProperties mp=o.getProperties();
            if(mp.containsKey("type"))
            {
                String type=(String)mp.get("type");
                
                switch(type)
                {
                    case "platform_solid":
                        fdef.filter.categoryBits=(short)8;
                        fdef.filter.maskBits=(short)(4|32|16);
                        body.createFixture(fdef).setUserData("map_object");
                        break;
                    case "platform":
                        fdef.filter.categoryBits=(short)64;
                        fdef.filter.maskBits=(short)(4|32);
                        body.createFixture(fdef).setUserData("map_object");
                        break;
                    case "platform_wall":
                        //stessi bit per le platform_solid
                        fdef.filter.categoryBits=(short)8;
                        fdef.filter.maskBits=(short)(4|32|16);
                        body.createFixture(fdef).setUserData("map_wall");
                        break;
                    case "end_level":
                        fdef.filter.categoryBits=(short)128;
                        fdef.filter.maskBits=(short)(4);
                        body.createFixture(fdef).setUserData("end_level");
                        break;
                }
            }
            
            s.dispose();
        }
    }
    
    private PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / Evilian.PPM,
                                   (rectangle.y + rectangle.height * 0.5f ) / Evilian.PPM);
        polygon.setAsBox(rectangle.width * 0.5f / Evilian.PPM,
                         rectangle.height * 0.5f / Evilian.PPM,
                         size,
                         0.0f);
        return polygon;
    }

    private CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / Evilian.PPM);
        circleShape.setPosition(new Vector2(circle.x / Evilian.PPM, circle.y / Evilian.PPM));
        return circleShape;
    }

    private PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            //System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / Evilian.PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }
    
    public void parseEnemiesSpawnPoints(MapObjects objects)
    {
        //mi faccio passare come parametro la lista dei blocchi in cui ci staranno i nemici
        for(MapObject o:objects)
        {
            Rectangle rec;
            if (o instanceof RectangleMapObject) 
            {
                MapProperties mp=o.getProperties();
                rec =((RectangleMapObject) o).getRectangle();
                if(mp.containsKey("type"))
                {
                    String type=(String)mp.get("type");

                    switch(type)
                    {
                        case "A":
                            this.ef.addEnemy(rec.x/Evilian.PPM, rec.y/Evilian.PPM, 1f,EnemyType.A);
                            break;
                        case "B":
                            this.ef.addEnemy(rec.x/Evilian.PPM, rec.y/Evilian.PPM, 1f,EnemyType.B);
                            break;
                    }
                }
            }
        }
    }
    
    public void handleInput() throws IOException
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            //salvo lo stato del player
            p.saveState();
            //salvo lo stato dei nemici
            this.saveEnemyState();
            
            this.changeScreenTo(2);
        }
    }
    
    
    //legge dal file del player posizione e vita e le ritorna dentro uno state container
    public StateContainer caricaStatoPlayer() throws FileNotFoundException
    {
        try
        {
            float x = 50, y = 100, hp = 1;
            Scanner scan = new Scanner(file);
            //scan del timer
            String line = scan.nextLine();
            this.gameplayTime=Float.parseFloat(line);
            //scan riga del player
            line = scan.nextLine();
            String[] words = line.split(" ");
            x = Float.parseFloat(words[0]);
            y = Float.parseFloat(words[1]);
            hp = Float.parseFloat(words[2]);  
            scan.close();
            return new StateContainer(new Vector2(x, y), hp);
        }
        catch(FileNotFoundException e)
        {
                System.out.println("no file found");
                return null;
        }
            
    }
    
    public Array<StateContainer> caricaStatoNemico() throws FileNotFoundException
    {
        Array<StateContainer> arr;
        File f = new File("enemy_state.txt");
        try (Scanner scan = new Scanner(f)){
            int n = scan.nextInt();
            scan.nextLine();
            arr = new Array<>();
            for(int i = 0; i < n; i++)
            {
                String line = scan.nextLine();
                String[] words = line.split(" ");
                arr.add(new StateContainer(new Vector2(Float.parseFloat(words[1]), Float.parseFloat(words[2])), Float.parseFloat(words[3]), words[0]));
            }
        }
        return arr;
    }
    
    //salva lo stato per ogni nemico ed il numero di nemici come primo valore 
    public void saveEnemyState() throws IOException
    {
        Array <Enemy> list = ef.getActiveEnemies();
        int size = ef.getSize();
        FileWriter wr = new FileWriter("enemy_state.txt");
        String toWrite = "" + size + "\n";
        wr.write(toWrite);
        for(int i = 0; i < size; i ++)
            list.get(i).salvStato(wr);
        wr.close();
    }
    
    //questa funzione verra' chiamata alla fine del livello
    protected void endLevel() throws FileNotFoundException, IOException
    {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //creo uno score con i punti totalizzati
        Score score = new Score(points, java.time.LocalDate.now().format(dateFormatter), java.time.LocalTime.now().format(timeFormatter));
        
        FileWriter wr = new FileWriter("score.txt");
        String toWrite = "" + score.getPoints() + " " + score.getDate() + " " +  score.getTime();
        wr.write(toWrite);
        wr.close();
        
        //reset file salvataggio dopo aver completato il livello
        wr=new FileWriter("enemy_state.txt");
        wr.close();
        
        wr=new FileWriter("save_state.txt");
        wr.close();
        
        this.changeScreenTo(5);
    }
    
    void loadUI()
    {
        //NOTA: METTI GLI ELEMENTI IN ORDINE
        level_ui.add(new BackGround(0,0,800,75,"images/ui/bg.png"));
        //fb
        level_ui.add(new BackGround(300,15,40,40,"images/ui/fireball_2.png"));
        level_ui.add(new FBBar(301,56,38,4,"images/ui/health_only.png"));
        level_ui.add(new BackGround(300, 55, 40, 6, "images/ui/skill_bar.png"));
        
        //ib
        level_ui.add(new BackGround(400, 15, 40, 40, "images/ui/iceball.png"));
        level_ui.add(new IBBar(401,56,38,4,"images/ui/health_only.png"));
        level_ui.add(new BackGround(400, 55, 40, 6, "images/ui/skill_bar.png"));
        
        //heal
        level_ui.add(new BackGround(500, 15, 40, 40, "images/ui/heal.png"));
        level_ui.add(new HBar(501,56,38,4,"images/ui/health_only.png"));
        level_ui.add(new BackGround(500, 55, 40, 6, "images/ui/skill_bar.png"));
        
        
        //meteor
        level_ui.add(new BackGround(600, 15, 40, 40, "images/ui/meteor.png"));
        level_ui.add(new MBar(601,56,38,4,"images/ui/health_only.png"));
        level_ui.add(new BackGround(600, 55, 40, 6, "images/ui/skill_bar.png"));
        
        //health
        level_ui.add(new HealthBar(56,31,50*3,4*3,"images/ui/health_only.png"));
        level_ui.add(new HealthShade(56,31,50*3,1*3,"images/ui/health_only_shade.png"));
        level_ui.add(new BackGround(20,25,64*3,8*3,"images/ui/health_bar_empty.png"));
        
        //selector
        level_ui.add(new Selector(257,25,40,16,"images/ui/sword.png"));
        
        //timer
        level_ui.add(new UIText(727,50,40,16));
    }

    void adjustCameraToPlayer() 
    {
        Rectangle r=p.getPos();
        cam.position.set(Math.max(r.x+0.5f,2f), Math.max(r.y+0.2f,1.4f),0f);
    }

    void changeScreenTo(int i) 
    {
        ManagerScreen.setIndex(i);
        try {
            ManagerScreen.getManagerScreen().changeScreen(entities, root);
        } catch (IOException ex) {
            Logger.getLogger(Livello1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void changeScreenTo() 
    {
        try {
            ManagerScreen.getManagerScreen().changeScreen(entities, root);
        } catch (IOException ex) {
            Logger.getLogger(Livello1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void addPoints(int toAdd)
    {
        points+=toAdd;
    }
    
    public static World getWorld()
    {
        return world;
    }
    
    public static Box2DDebugRenderer getDebug()
    {
        return debug;
    }
    
    public static TextureAtlas getAtlas()
    {
        return atlas;
    }
    
    public static float getGameplayTime()
    {
        return gameplayTime;
    }
}
