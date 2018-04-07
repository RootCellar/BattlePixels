import java.util.ArrayList;
public class Team
{
    String name = "NOT  SET";
    int r = 255;
    int g = 255;
    int b = 255;
    Level level;
    Flag flag = new Flag( 0, 0, 50, this);
    
    
    public void setLevel(Level l) {
        level=l;
        level.add(flag);
    }
    
    public ArrayList<Mob> getMobsOnTeam() {
        ArrayList<Mob> mobs = new ArrayList<Mob>();
        for(int i=0; i<level.entities.size(); i++) {
            Entity e = level.entities.get(i);
            
            if(e instanceof Mob) {
                Mob m = (Mob)e;
                
                if(m.team==null) continue;
                
                if(m.team.equals(this)) {
                    mobs.add(m);
                }
                
            }
            
        }
        
        return mobs;
    }
    
    public void tick() {
        
    }
}