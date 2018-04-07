public class Soldier extends Mob
{
    int tx = 0;
    int ty = 500;
    int reload = 0;
    
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
        if(reload>=100) reload = 100;
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
    
    public void render() {
        for(int i=-2; i<3; i++) {
            for(int k=-2; k<3; k++) {
                game.drawPixel(x+i, y+k, 127, 127, 127);
            }
        }
    }
}   