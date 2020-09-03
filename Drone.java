public class Drone extends Mob
{
    double tx = 0;
    double ty = 500;
    int reload = 0;
    int grenadeTime = 0;
    
    public Drone() {
        speed = 1;
    }
    
    public boolean isAtSpot() {
        return (x==tx) && (y==ty);
    }

    public void tick() {
        super.tick();
        
        if(tx>x) addX(speed);
        if(tx<x) subX(speed);
        if(ty>y) addY(speed);
        if(ty<y) subY(speed);
        
        if( Math.abs(tx - x) < speed ) x = tx;
        if( Math.abs(ty - y) < speed ) y = ty;
        
        doLifeBar();

        reload++;
        grenadeTime++;
        
        if(reload>=1000) reload = 1000;
        if(grenadeTime >= 1000) grenadeTime = 1000;
    }

    public void shoot() {
        if(reload>=5) {
            Projectile p = newProjectile();
            p.damage = 35;
            p.setByDir(dir, 15);
            p.size = 1;
            level.add(p);
            reload = 0;
        }
    }
    
    public void grenade() {
        if(grenadeTime>=200) {
            Bomb b = new Bomb(this);
            b.x=x;
            b.y=y;
            b.setByDir(dir, 3);
            b.damage=20;
            b.setOffset(20);
            b.maxTime=100;
            b.eRadius=50;
            b.size = 2;
            shoot(b);
            grenadeTime = 0;
        }
    }
    
    public void render() {
        for(int i=-2; i<3; i++) {
            for(int k=-2; k<3; k++) {
                game.drawPixel(x+i, y+k, team.r, team.g, team.b);
            }
        }
    }
}   