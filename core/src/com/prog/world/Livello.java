package com.prog.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.prog.entity.Entity;
import com.prog.entity.Mouse;
import com.prog.evilian.Evilian;
import com.prog.world.ManagerVfx;

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
    
    
    
    public Livello(float gravity, boolean Sleep, String path, int cameraWidth, int cameraHeight,Evilian game)
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
        camvp=new FitViewport(game.SCREEN_WIDTH/Evilian.PPM,game.SCREEN_HEIGHT/Evilian.PPM,cam);
        atlas=new TextureAtlas("atlas/osvaldo.atlas");
        
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
        atlas=new TextureAtlas("atlas/osvaldo.atlas");

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
            fdef.filter.categoryBits=(short)8;
            fdef.filter.maskBits=(short)(4|32);
            body.createFixture(fdef).setUserData("map_object");
            
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
                System.out.println(vertices[i]);
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
