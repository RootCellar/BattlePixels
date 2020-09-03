import java.util.ArrayList;
public class AllyTeam extends Team
{
    int xTarget = 500;
    int yTarget = 500;
    int spread = 100;
    int time = 0;
    public AllyTeam() {
        b=255;
        r=0;
        g=0;
    }
    
    public void setup() {
        /*
        for(int i=0; i<6; i++) {
            Cover c = new Cover();
            c.team=this;
            c.x=100;
            c.y=450 + (i*20);
            level.add(c);
        }
        */
       
       flag.x = 200;
       flag.y = 500;
       flag.radius = 200;
       //flag.visible = false;
       
       Cover c = new Cover();
       c.team = this;
       c.x = flag.x;
       c.y = flag.y;
       c.size = 100;
       level.add(c);
    }
    
    public void tick() {
        flag.x = 200;
        flag.y = 500;
        flag.radius = 200;
        
        time++;
        
        if(getMobsOnTeam().size()<100) {
            Soldier m = new Soldier();
            m.x=0;
            m.y=500;
            m.team=this;
            level.add(m);
        }
        
        if(time>=500) {
            yTarget = 200+(int)(Math.random()*600);
            time=0;
        }
        
        ArrayList<Mob> mobs = getMobsOnTeam();
        for(int i=0; i<mobs.size(); i++) {
            Mob m = mobs.get(i);
            if(!(m instanceof Soldier)) continue;
            Soldier s = (Soldier)m;
            if(s.isAtSpot()) {
                s.ty = (int)( yTarget + ( ( Math.random() * spread * 2 ) - spread ) );
                s.tx = (int)( xTarget + ( ( Math.random() * spread * 2 ) - spread ) );
            }
            s.dir = 1;
            s.shoot();
            //s.grenade();
        }
    }
}