import java.util.ArrayList;
public class EnemyTeam extends Team
{
    int soldierCount = 0;
    int heavyTankCount = 0;
    int tankCount = 0;
    int bossCount = 0;
    
    public EnemyTeam() {
        r=255;
        b=0;
        g=0;
    }
    
    public void setup() {
        for(int i=0; i<8; i++) {
            Cover c = new Cover();
            c.team=this;
            c.x=800;
            c.y=430 + (i*20);
            level.add(c);
        }

    }
    
    public void tick() {
        
        flag.x = 800;
        flag.y = 500;
        flag.radius = 200;
        
        soldierCount = 0;
        heavyTankCount = 0;
        tankCount = 0;
        bossCount = 0;
        
        ArrayList<Mob> mobs = getMobsOnTeam();
        for(int i=0; i<mobs.size(); i++) {
            Mob m = mobs.get(i);
            if(m instanceof EnemySoldier) soldierCount++;
            if(m instanceof SuperHeavyTank) heavyTankCount++;
            if(m instanceof HeavyTank) tankCount++;
            if(m instanceof Boss) bossCount++;
        }
        
        if(bossCount<1) {
            Boss b = new Boss();
            b.doAi = true;
            b.team = this;
            b.x = 1000;
            b.y = 500;
            level.add(b);
        }
        
        if(soldierCount<50) {
            EnemySoldier m = new EnemySoldier();
            m.x=900;
            m.y=(int)(Math.random()*level.yBound);
            m.team=this;
            level.add(m);
        }
        
        if(heavyTankCount < 5) {
            SuperHeavyTank m = new SuperHeavyTank();
            m.x=900;
            m.y=(int)(Math.random()*level.yBound);
            m.team=this;
            level.add(m);
        }
        
        if(tankCount < 10) {
            HeavyTank m = new HeavyTank();
            m.x=900;
            m.y=(int)(Math.random()*level.yBound);
            m.team=this;
            level.add(m);
        }
        
        mobs = getMobsOnTeam();
        for(int i=0; i<mobs.size(); i++) {
            Mob m = mobs.get(i);
            if(m instanceof Tank) {
                Tank t = (Tank)m;
                t.tx = t.x;
                t.ty = t.y;
                t.dir = 3;
                
            }
            
        }
    }
}