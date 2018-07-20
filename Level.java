import java.util.ArrayList;
public class Level
{
    ArrayList<Entity> entities = new ArrayList<Entity>();
    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    ArrayList<Team> teams = new ArrayList<Team>();
    ArrayList<Flag> flags = new ArrayList<Flag>();
    
    ArrayList<Entity> pendingMobSpawns = new ArrayList<Entity>();
    ArrayList<Projectile> pendingProjectileSpawns = new ArrayList<Projectile>();
    
    ArrayList<Entity> pendingMobDespawns = new ArrayList<Entity>();
    ArrayList<Projectile> pendingProjectileRemovals = new ArrayList<Projectile>();
    
    double xBound = 1000;
    double yBound = 1000;
    
    int entityTickingTime = 0;
    int projectileTickingTime = 0;
    int teamTickingTime = 0;
    int flagTickingTime = 0;
    int levelTickingTime = 0;
    
    public boolean has(Entity e) {
        for(int i=0; i<entities.size(); i++) {
            if( entities.get(i).equals(e) ) return true;
        }
        
        for(int i=0; i<pendingMobSpawns.size(); i++) {
            if( pendingMobSpawns.get(i).equals(e) ) return true;
        }
        
        return false;
    }

    public void remove(Entity e) {
        //entities.remove(e);
        pendingMobDespawns.add(e);
        
    }

    public void remove(Projectile p) {
        //projectiles.remove(p);
        pendingProjectileRemovals.add(p);
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
        long start, end;
        
        long start2 = System.nanoTime();
        
        /**
         * Bug fix
         * 
         */
        while(pendingMobSpawns.size()>0) {
            entities.add( pendingMobSpawns.remove(0) );
        }
        
        while(pendingProjectileSpawns.size()>0) {
            projectiles.add( pendingProjectileSpawns.remove(0) );
        }
        
        start = System.nanoTime();
        for(int i=0; i<teams.size(); i++) {
            teams.get(i).tick();
        }
        end = System.nanoTime();
        teamTickingTime = (int) (end-start);
        
        start = System.nanoTime();
        for(int i=0; i<projectiles.size(); i++) {
            
            Projectile p = projectiles.get(i);

            p.tick();
            if(p.x<0) remove(p);
            if(p.y<0) remove(p);
            if(p.x>xBound) remove(p);
            if(p.y>yBound) remove(p);
            
        }
        end = System.nanoTime();
        projectileTickingTime = (int) (end-start);
        
        start = System.nanoTime();
        for(int i=0; i<entities.size(); i++) {
            Entity e = entities.get(i);
            
            if(e.x<0) e.x=0;
            if(e.y<0) e.y=0;
            if(e.x>xBound) e.x=xBound;
            if(e.y>yBound) e.y=yBound;
            
            e.tick();
            
            if(e.x<0) e.x=0;
            if(e.y<0) e.y=0;
            if(e.x>xBound) e.x=xBound;
            if(e.y>yBound) e.y=yBound;

        }
        end = System.nanoTime();
        entityTickingTime = (int) (end-start);
        
        start = System.nanoTime();
        for(int i=0; i<flags.size(); i++) {
            flags.get(i).tick();
        }
        end = System.nanoTime();
        flagTickingTime = (int) (end-start);
        
        /**
         * Bug Fix
         * 
         */
        while(pendingMobDespawns.size()>0) {
            entities.remove( pendingMobDespawns.remove(0) );
        }
        
        while(pendingProjectileRemovals.size()>0) {
            projectiles.remove( pendingProjectileRemovals.remove(0) );
        }
        
        long end2 = System.nanoTime();
        levelTickingTime = (int) (end2-start2);
    }
    
    public void add(Flag f) {
        flags.add(f);
        f.setLevel(this);
    }

    public void add(Entity e) {
        //entities.add(e);
        pendingMobSpawns.add(e);
        e.setLevel(this);
    }

    public void add(Projectile p) {
        //projectiles.add(p);
        pendingProjectileSpawns.add(p);
        p.setLevel(this);
    }

    public void addTeam(Team t) {
        t.setLevel(this);
        teams.add(t);
        t.setup();
    }

    public ArrayList<Mob> getInRange( double x, double y, double r) {
        ArrayList<Mob> list = new ArrayList<Mob>();

        for(int i=0; i<entities.size(); i++) {
            Entity e = entities.get(i);
            
            if(!(e instanceof Mob)) continue;
            
            if( getDistance( x, y, e.x, e.y) <= r + (double)e.size) {
                list.add((Mob)e);
            }

        }

        return list;
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        double a = Math.abs( x1 - x2 );
        double b = Math.abs( y1 - y2 );
        a*=a;
        b*=b;
        return Math.sqrt( a + b );
    }
}