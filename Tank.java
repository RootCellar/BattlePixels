public class Tank extends Mob
{
    int reload = 100;
    int maxReload = 100;
    int tx = x;
    int ty = y;
    public Tank() {
        maxHp=3000;
        hp=maxHp;
        regen=3;
        speed=2;
        team = new Team();
        dir=2;
        x=100;
        y=100;
    }
    
    public void tick() {
        super.tick();
        
        bar.width = 30;
        bar.height = 10;
        bar.x = x - 15;
        
        if(tx>x) addX(speed);
        if(tx<x) subX(speed);
        if(y<ty) addY(speed);
        if(y>ty) subY(speed);
        
        reload++;
        if(reload>maxReload) reload=maxReload;
        
        if(reload>=maxReload){
            shoot();
        }
    }
    
    public void shoot() {
        Projectile p = newProjectile();
        p.setByDir(dir, 5);
        p.damage = 500;
        p.damageRange = 25;
        p.size = 2;
        level.add(p);
        reload = 0;
    }
    
    public void render() {
        for(int i=-4; i<5; i++) {
            for(int k=-4; k<5; k++) {
                game.drawPixel(x+i, y+k, team.r, team.g, team.b);
            }
        }
    }
}