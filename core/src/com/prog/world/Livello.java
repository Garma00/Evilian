package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.prog.entity.Entity;
import com.prog.entity.Mouse;
import com.prog.entity.Player;
import com.prog.evilian.Evilian;
import com.prog.world.ManagerVfx;
import java.io.File;
import java.io.FileNotFoundException;
//per la funzione salva stato
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import static com.prog.world.ManagerScreen.index;
import static com.prog.world.ManagerScreen.MANAGER_SCREEN;

public class Livello {
    Player p;
    FileWriter wr;
    public static World world;
    //mappa tiled
    public TiledMap map;
    //renderer mappa ortogonale(esiste anche l'isometric ma non serve al nostro gioco)
    public OrthogonalTiledMapRenderer mapRenderer;
    public OrthographicCamera cam;
    public Viewport camvp;
    public Array<Entity> entities;
    public Evilian root;
    public Box2DDebugRenderer debug;
    public static TextureAtlas atlas;
    public Mouse mouse;
    public static final ManagerVfx mvfx=new ManagerVfx();
    public UI level_ui;
    public boolean resume;
    File file;//file per il caricamento dello stato
    EnemyFactory ef;
    public static float score;
    protected static float timeEmployed;
    private Array<Float> scores;
    
   
    
    public Livello(float gravity, boolean Sleep, String path, float cameraWidth, float cameraHeight,float uiWidth,float uiHeight,Evilian game) throws IOException
    {
        
        //mi serve il riferimento alla classe root per poi cambiare screen (o livelli)
        root=game;
        debug=new Box2DDebugRenderer();
        Player p = null;
        //creiamo il mondo
        //NOTA:inserire gravita' negativa da parametro
        world=new World(new Vector2(0,gravity),Sleep);
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
        mouse = new Mouse(cam);
        //NOTA: ogni frame nel render dovremo chiamare mapRenderer.setView(camera) e poi mapRenderer.render()
    
        //da fare un metodo che richiama dalle opzioni quali effetti sono attivi
        ef = new EnemyFactory(p);
        timeEmployed = 0;
        score = 0f;
        scores = new Array<>();

        
    }
    
    public Livello(boolean Sleep, int cameraWidth, int cameraHeight, Evilian game)
    {
        root = game;
        debug = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), Sleep);
        entities = new Array<Entity>();
        cam = new OrthographicCamera();
        cam.setToOrtho(false,cameraWidth/Evilian.PPM,cameraHeight/Evilian.PPM);
        
        camvp=new FitViewport(game.SCREEN_WIDTH/Evilian.PPM,game.SCREEN_HEIGHT/Evilian.PPM,cam);
        atlas=new TextureAtlas("atlas/game.atlas");

        mouse = new Mouse(cam);
        
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
        //mvfx.dispose();
        //forse non conviene fare il dispose del mondo visto che ne abbiamo solo uno istanziato
        //world.dispose();
    }
    
    public void parseCollisions(World w,MapObjects objects)
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
            body=w.createBody(bdef);
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
                rec =((RectangleMapObject) o).getRectangle();
                this.ef.addEnemy(rec.x/Evilian.PPM, rec.y/Evilian.PPM, 1f);
            }
        }
    }
    
    public void handleInput() throws IOException
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.E))
            endLevel();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            //salvo lo stato del player
            p.salvaStato();
            //salvo lo stato dei nemici
            salvaStato();
            
            root.setScreen(new Opzioni(root.SCREEN_WIDTH, root.SCREEN_HEIGHT, root));
            
            //questo pezzo di codice fa crashare l'app appena si preme esc e si passa da livello1 a opzioni
            //dispose();
            //root.dispose();
            //Gdx.app.exit();
        }
    }
    
    
    //legge dal file del player posizione e vita e le ritorna dentro uno state container
    public StateContainer caricaStatoPlayer() throws FileNotFoundException
    {
        try
        {
            float x = 50, y = 100, hp = 1;
            System.out.println("entro per leggere");
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine())
            {
                
                String line = scan.nextLine();
                String[] words = line.split(" ");
                if(words[0].equals("P"))
                {
                    x = Float.parseFloat(words[1]);
                    y = Float.parseFloat(words[2]);
                    hp = Float.parseFloat(words[3]);
                }    
            }
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
    public void salvaStato() throws IOException
    {
        Array <Enemy> list = ef.getActiveEnemies();
        int size = ef.getSize();
        FileWriter wr = new FileWriter("enemy_state.txt");
        String toWrite = "" + size + "\n";
        System.out.println("ci sono " + size + " nemici");
        wr.write(toWrite);
        for(int i = 0; i < size; i ++)
            list.get(i).salvStato(wr);
        wr.close();
    }
    
    //questa funzione verrà chiamata alla fine del livello
    protected void endLevel() throws FileNotFoundException, IOException
    {
        
        //calcolo lo score effettivo e lo scrivo sul file
        score -= timeEmployed;
        index = 5;
        FileWriter wr = new FileWriter("score.txt");
        String toWrite = "" + score;
        wr.write(toWrite);
        wr.close();
        
        //inserisco lo score attuale nell'array
        scores.add(score);
        
        writeScores();
        MANAGER_SCREEN.changeScreen(entities, root);
        
    }
    
    //carico i migliori score
    protected void loadScore() throws FileNotFoundException
    {
        File f = new File("general_info.txt");
        Scanner scan = new Scanner(f);
        while(scan.hasNextLine())
            insertScore(scan.nextFloat());
        scan.close();
    }
    
    //scrivo gli score sul file
    protected void writeScores() throws IOException
    {
        FileWriter wr = new FileWriter("general_info.txt");
        
        for(float n: scores)
            wr.append(String.valueOf(n));
        
        wr.close();
        
    }
        
    private void insertScore(float s)
    {
        //carico in ordine dal più grande al più piccolo gli score
        for(float i: scores)
        {
            if(s > i)
                i = s;
        }
    }
    
}
    

