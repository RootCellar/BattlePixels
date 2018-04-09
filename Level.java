import java.util.ArrayList;
public class Level
{
    ArrayList<Entity> entities = new ArrayList<Entity>();
    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    ArrayList<Team> teams = new ArrayList<Team>();
    ArrayList<Flag> flags = new ArrayList<Flag>();
    int xBound = 1000;
    int yBound = 1000;

    public void remove(Entity e) {
        entities.remove(e);
    }

    public void remove(Projectile p) {
        projectiles.remove(p);
    }

    public void trySpawn() {
        /**
        if(entities.size()<10000) {
        Monster m = new Monster();
        m.x=0;
        m.y=0;
        add(m);
        }
         */
    }

    public void tick() {
        for(int i=0; i<teams.size(); i++) {
            teams.get(i).tick();
        }

        for(int i=0; i<projectiles.size(); i++) {
            Projectile p = projectiles.get(i);

            p.tick();
            if(p.x<0) remove(p);
            if(p.y<0) remove(p);
            if(p.x>xBound) remove(p);
            if(p.y>yBound) remove(p);
        }

        for(int i=0; i<entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
            if(e.x<0) e.x=0;
            if(e.y<0) e.y=0;
            if(e.x>xBound) e.x=xBound;
            if(e.y>yBound) e.y=yBound;

        }
        
        for(int i=0; i<flags.size(); i++) {
            flags.get(i).tick();
        }

    }
    
    public void add(Flag f) {
        flags.add(f);
        f.setLevel(this);
    }

    public void add(Entity e) {
        entities.add(e);
        e.setLevel(this);
    }

    public void add(Projectile p) {
        projectiles.add(p);
        p.setLevel(this);
    }

    public void addTeam(Team t) {
        t.setLevel(this);
        teams.add(t);
        t.setup();
    }

    public ArrayList<Mob> getInRange( int x, int y, int r) {
        ArrayList<Mob> list = new ArrayList<Mob>();

        for(int i=0; i<entities.size(); i++) {
            Entity e = entities.get(i);
            
            if(!(e instanceof Mob)) continue;
            
            if( getDistance( x, y, e.x, e.y) <= r) {
                list.add((Mob)e);
            }

        }

        return list;
    }

    public static double getDistance(int x1, int y1, int x2, int y2) {
        double a = Math.abs( x1 - x2 );
        double b = Math.abs( y1 - y2 );
        a*=a;
        b*=b;
        return Math.sqrt( a + b );
    }
}