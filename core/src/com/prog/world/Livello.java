package com.prog.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.prog.entity.Entity;
import com.prog.evilian.Evilian;

public class Livello {
    public static World world;
    //mappa tiled
    public TiledMap map;
    //renderer mappa ortogonale(esiste anche l'isometric ma non serve al nostro gioco)
    public OrthogonalTiledMapRenderer mapRenderer;
    public OrthographicCamera cam;
    public Array<Entity> entities;
    public Evilian root;
    public Box2DDebugRenderer debug;
    
    
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
        mapRenderer=new OrthogonalTiledMapRenderer(map);
        cam=new OrthographicCamera();
        
        cam.setToOrtho(false,cameraWidth,cameraHeight);
        
        //NOTA: ogni frame nel render dovremo chiamare mapRenderer.setView(camera) e poi mapRenderer.render()
    }
    
    public Livello(boolean Sleep, int cameraWidth, int cameraHeight, Evilian game)
    {
        root = game;
        debug = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), Sleep);
        entities = new Array<Entity>();
        cam = new OrthographicCamera();
        cam.setToOrtho(false,cameraWidth,cameraHeight);
    }
    
    public void dispose()
    {
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
    }
    
    public void parseCollisions(World w,MapObjects objects)
    {
        for(MapObject o: objects)
        {
            Shape s;
            if(o instanceof PolygonMapObject)
            {
                s=creaShape((PolygonMapObject) o);
            }
            else
                continue;
            
            Body body;
            BodyDef bdef= new BodyDef();
            bdef.type=BodyDef.BodyType.StaticBody;
            body=w.createBody(bdef);
            body.createFixture(s,1.0f);
            
            s.dispose();
        }
    }

    private Shape creaShape(PolygonMapObject poly) {
        float[] vertici= poly.getPolygon().getTransformedVertices();
        Vector2[] worldVertices= new Vector2[vertici.length/2];
        
        for(int i=0;i< worldVertices.length;i++)
            worldVertices[i]=new Vector2(vertici[i*2],vertici[i*2+1]);
        
        ChainShape cs=new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }
    
    
    
}
