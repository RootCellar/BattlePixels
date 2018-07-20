public class SuperHeavyTank extends Tank
{
    public SuperHeavyTank() {
        super();
        maxReload = 200;
        maxHp = 15000;
        hp = maxHp;
        speed = 0.5;
        regen = 10;
        dir = 1;
    }
    
    public void shoot() {
        Projectile p = newProjectile();
        p.setByDir(dir, 5);
        p.damage = 1000;
        p.damageRange = 25;
        p.size = 4;
        level.add(p);
        reload = 0;
    }
    
    public void render() {
        super.render();
        
        for(int i=-4; i<5; i++) {
            game.drawPixel(x-i, y+i, 0, 0, 0);
        }
        
        for(int i=-4; i<5; i++) {
            game.drawPixel(x-i, y-i, 0, 0, 0);
        }
    }
}