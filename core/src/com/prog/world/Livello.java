package com.prog.world;

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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
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
import com.prog.entity.Entity;
import com.prog.entity.Mouse;
import com.prog.evilian.Evilian;
import com.prog.world.AI.Node;
import com.prog.world.AI.Node.NodeType;
import com.prog.world.AI.NodeGraph;

public class Livello {
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
    
    
    
    public Livello(float gravity, boolean Sleep, String path, float cameraWidth, float cameraHeight,float uiWidth,float uiHeight,Evilian game)
    {
        
        //mi serve il riferimento alla classe root per poi cambiare screen (o livelli)
        root=game;
        debug=new Box2DDebugRenderer();
        
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
        
        mouse = new Mouse(cam);
        //NOTA: ogni frame nel render dovremo chiamare mapRenderer.setView(camera) e poi mapRenderer.render()
    
        //da fare un metodo che richiama dalle opzioni quali effetti sono attivi
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
                        break;
                    case "platform":
                        fdef.filter.categoryBits=(short)64;
                        fdef.filter.maskBits=(short)(4|32);
                        break;
                }
            }
            
            body.createFixture(fdef).setUserData("map_object");
            
            s.dispose();
        }
    }
    
    public NodeGraph parseNodes()
    {
        TiledMapTileLayer step1=(TiledMapTileLayer)map.getLayers().get("step1");
        TiledMapTileLayer step2=(TiledMapTileLayer)map.getLayers().get("step2");
        TiledMapTileLayer step3=(TiledMapTileLayer)map.getLayers().get("step3");
        //la mappa su tiled ha 100 tile di larghezza e 30 in altezza
        Node[][] arr=new Node[30][100];
        
        int width=step1.getWidth();
        int height=step1.getHeight();
        int x=0;
        int y=0;
        int tileDim=32;
        NodeGraph graph=new NodeGraph();

        //STEP1 corretto (aggiungo pavimento e basta)
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                Cell cell=step1.getCell(i,j);
                if(cell != null)
                {
                    Node n=new Node(x,y,NodeType.FLOOR);
                    //aggiungo pavimento alla matrice della mappa
                    arr[j][i]=n;
                    System.out.println("pavimento:"+x+"\t"+y);
                    
                    //collegamento con pavimento precedente
                    if( i-1 > 0 && arr[0][i-1] != null)
                    {
                        graph.connectNode(n, arr[0][i-1]);
                        graph.connectNode(arr[0][i-1], n);
                    }
                }
                y+=tileDim;
            }
            x+=tileDim;
            y=0;
        }
        
        
        //STEP2 corretto (aggiungo le cornici delle piattaforme)
        width=step2.getWidth();
        height=step2.getHeight();
        x=0;
        y=0;
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                Cell cell=step2.getCell(i,j);
                if(cell != null)
                {
                    Node n=new Node(x,y,NodeType.CORNER);
                    arr[j][i]=n;
                    
                    //collegamenti bidirezionali in cui il nemico potrà saltare
                    for(int k=0;k<3;k++)
                    {
                        //per il lato sinistro
                        if(i-1 > 0 && j-k >= 0)
                        {
                            Node tmp=arr[j-k][i-1];
                            if( tmp != null)
                            {
                                graph.connectNode(n, tmp);
                                graph.connectNode(tmp, n);
                            }
                        }
                        //per il lato destro
                        if(i+1 < width && j-k >= 0)
                        {
                            Node tmp=arr[j-k][i+1];
                            if( tmp != null)
                            {
                                graph.connectNode(n, tmp);
                                graph.connectNode(tmp, n);
                            }
                        }
                    }
                    
                    //collegamenti unidirezionali per scendere da qualsiasi cornice
                    for(int k=j;k>=0;k--)
                    {
                        Node tmp=arr[j-k][i-1];
                        Node tmp2=arr[j-k][i+1];
                        if(tmp != null)
                            graph.connectNode(n, tmp);
                        if(tmp2!=null)
                            graph.connectNode(n, tmp2);
                    }
                    
                    System.out.println("cornice:"+x+"\t"+y);
                }
                y+=tileDim;
            }
            x+=tileDim;
            y=0;
        }
        
        //STEP3 corretto (aggiungo la parte centrale delle piattaforme)
        width=step3.getWidth();
        height=step3.getHeight();
        x=0;
        y=0;
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                Cell cell=step3.getCell(i,j);
                if(cell != null)
                {
                    Node n=new Node(x,y,NodeType.MIDDLE);
                    arr[j][i]=n;
                    System.out.println("middle:"+x+"\t"+y);
                    
                    //controllo a destra fino a trovare la cornice
                    /*for(int k=0;k<15 && (x/tileDim)+k < 100;k++)
                    {
                        Node tmp=arr[y/tileDim][(x/tileDim)+k];
                        if(tmp!=null)
                        {
                            graph.connectNode(n, tmp);
                            graph.connectNode(tmp, n);
                        }      
                    }
                    //controllo a sinistra fino a trovare la cornice
                    for(int k=0;k<15 && (x/tileDim)-k >= 0;k++)
                    {
                        Node tmp=arr[y/tileDim][(x/tileDim)-k];
                        if(tmp!=null)
                        {
                            graph.connectNode(n, tmp);
                            graph.connectNode(tmp, n);
                        }      
                    }*/
                }
                y+=tileDim;
            }
            x+=tileDim;
            y=0;
        }
        
        //aggiunta tutti i nodi in array unidimensionale
        for(int i=0;i<height;i++)
            for(int j=0;j<width;j++)
                if(arr[i][j] != null)
                    graph.addNode(arr[i][j]);
        
        return graph;
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
    
    public void handleInput()
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            root.setScreen(new Opzioni(root.SCREEN_WIDTH, root.SCREEN_HEIGHT, root));
            
            //questo pezzo di codice fa crashare l'app appena si preme esc e si passa da livello1 a opzioni
            //dispose();
            //root.dispose();
            //Gdx.app.exit();
        }
    }
    
}
