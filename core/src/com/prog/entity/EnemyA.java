package com.prog.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.prog.evilian.Evilian;
import com.prog.world.AI.Node;
import static com.prog.world.Livello1.sd;

public class EnemyA extends Enemy{
    boolean reached;
    Node currentPos,nextPos;
    Vector2 curr,next;
    
    @Override
    public void update(float delta) {
        time=TimeUtils.millis();
        if(time - lastCall > 1000)
        {
            Node start=mapGraph.findNodeAtPos(this.body.getWorldCenter().scl(Evilian.PPM));
            Node goal=mapGraph.findNodeAtPos(EnemyFactory.getPlayerPos());
            if(start!=null && goal != null)
            {
                pathToEnemy=mapGraph.findPath(start, goal);
            }
            if(pathToEnemy!=null)
            {
                pathQueue.clear();
                for(Node n:pathToEnemy)
                {
                    pathQueue.addLast(n);
                }
            }
            lastCall=time;
            
            //test
            if(pathToEnemy!= null && pathToEnemy.getCount()>=2)
            {
                float force=0;
                Node curr=pathToEnemy.get(0);
                Node next=pathToEnemy.get(1);
                System.out.println(curr.x+"\t"+curr.y+"\n"+next.x+"\t"+next.y);
                Vector2 vcurr=new Vector2(curr.x,curr.y).scl(1/Evilian.PPM);
                Vector2 vnext=new Vector2(next.x,next.y).scl(1/Evilian.PPM);
                
                Vector2 dist=vnext.sub(vcurr);
                System.out.println(dist);
                
                if(dist.y > 0)
                {
                    float forza=body.getMass() * 5f;
                    body.applyLinearImpulse(new Vector2(0,forza),body.getWorldCenter(),true);
                }
                force=dist.x;
                
                body.setLinearVelocity(force,this.body.getLinearVelocity().y);
            }
        }
        
        pos.x=(body.getPosition().x)-(pos.width/2);
        pos.y=(body.getPosition().y)-(pos.height/2);
        
        
        
        //se maggiore di 0 allora esiste il path
        if(pathQueue.size >= 2)
        {
            //currentPos=pathQueue.removeFirst();
            //nextPos=pathQueue.removeFirst();
            
        }
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void draw() {
        //da fare
        for(int i=0;i<pathQueue.size;i++)
        {
            sd.setColor(1f, 1f, 1f, 1f);
            if(i != 0)
                sd.line((pathToEnemy.get(i-1).x/Evilian.PPM)+0.16f, (pathToEnemy.get(i-1).y/Evilian.PPM)+0.32f, (pathToEnemy.get(i).x/Evilian.PPM)+0.16f, (pathToEnemy.get(i).y/Evilian.PPM)+0.32f,0.05f);
            else
                sd.setColor(0f, 1f, 0f, 1f);
            sd.filledCircle((pathToEnemy.get(i).x/Evilian.PPM)+0.16f,(pathToEnemy.get(i).y/Evilian.PPM)+0.32f,0.1f);
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void reset() {
        this.alive=true;
        this.life=1;
    }

    @Override
    public void init() {
        this.alive=true;
        this.life=1;
        this.pos=new Rectangle(MathUtils.random(100f,300f),MathUtils.random(100f, 300f),16f,28f);
        this.body=createBody(pos.x,pos.y,pos.width,pos.height,1,"enemyA",1f,0f,1f,(short)32,(short)(4|8|16|64));
        
        this.pos.width/=Evilian.PPM;
        this.pos.height/=Evilian.PPM;
        
        this.time=TimeUtils.millis();
        lastCall=time;
        Node start=mapGraph.findNodeAtPos(this.body.getWorldCenter().scl(Evilian.PPM));
        Node goal=mapGraph.findNodeAtPos(EnemyFactory.getPlayerPos());
        if(start!=null && goal != null)
            pathToEnemy=mapGraph.findPath(start, goal);
        
        curr=new Vector2();
        
    }
    
    private Vector2 moveToNextNode()
    {
        Node currNode=null;
        Node nextNode=null;
        if(pathQueue.size>=2)
        {
            currNode=pathQueue.removeFirst();
            nextNode=pathQueue.removeFirst();
        }
        if(currNode != null && nextNode != null)
        {
            /*
            float angle = MathUtils.atan2(nextNode.y - currNode.y, nextNode.x - currNode.x);
            return new Vector2(MathUtils.cos(angle),MathUtils.sin(angle));
            */
            Vector2 curr=new Vector2(currNode.x,currNode.y);
            Vector2 next=new Vector2(nextNode.x,nextNode.y);
            return curr.lerp(next, 0.1f);
        }
        return null;
    }
}
