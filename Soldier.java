public class Soldier extends Mob
{
    int tx = 0;
    int ty = 500;
    int reload = 0;
    int grenadeTime = 0;
    
    public boolean isAtSpot() {
        return (x==tx) && (y==ty);
    }

    public void tick() {
        super.tick();
        if(tx>x) addX(speed);
        if(tx<x) subX(speed);
        if(ty>y) addY(speed);
        if(ty<y) subY(speed);

        reload++;
        grenadeTime++;
        if(reload>=100) reload = 100;
        if(grenadeTime >= 1000) grenadeTime = 100;
    }

    public void shoot() {
        if(reload>=50) {
            Projectile p = newProjectile();
            p.damage = 35;
            p.setByDir(dir, 5);
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
                game.drawPixel(x+i, y+k, 127, 127, 127);
            }
        }
    }
}   