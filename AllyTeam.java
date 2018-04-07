import java.util.ArrayList;
public class AllyTeam extends Team
{
    int xTarget = 500;
    int yTarget = 500;
    int spread = 50;
    int time = 0;
    public AllyTeam() {
        b=255;
        r=0;
        g=0;
    }
    
    public void tick() {
        flag.x = 200;
        flag.y = 500;
        flag.radius = 200;
        
        time++;
        if(getMobsOnTeam().size()<50) {
            Soldier m = new Soldier();
            m.x=0;
            m.y=500;
            m.team=this;
            level.add(m);
        }
        
        if(time>=500) {
            yTarget = 100+(int)(Math.random()*800);
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
            m.dir = 1;
            m.shoot();
        }
    }
}