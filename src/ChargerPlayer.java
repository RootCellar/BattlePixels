public class ChargerPlayer extends Player {
    
    double reloadGun = 0;
    double maxReloadGun = 30;
    
    public ChargerPlayer(InputListener in) {
        super(in);
        
        maxHp = 2000;
        hp = maxHp;
        regen = maxHp / 1600;
        team = new Team();
        speed = 2;
        size = 0;
        y = 500;
    }
    
    public void damage(double a, Projectile p, Mob m) {
        super.damage(a, p, m);
    }

    public void damage(double a, Mob m) {
        super.damage(a, m);
    }

    public void damage(double a) {
        hp-=a;
        damageTime = 0;
    }
    
    public void shootGun() {
        if(reloadGun>=maxReloadGun) {
            for(int i=0; i<10; i++) {
                Projectile p = newProjectile();
                p.setByDir(1, 15);
                p.damage=20;
                p.setOffset(50);
                p.damageRange = 15;
                p.size = 1;
                shoot(p);
                reloadGun=0;
            }
            
            maxReloadGun *= 0.9;
        }
    }
    
    public void tick() {
        super.tick();
        
        if(input.space.down) shootGun();
        else maxReloadGun = 50;
        
        if(maxReloadGun < 0) maxReloadGun = 0;
        if(maxReloadGun > 30) maxReloadGun = 30;
        
        reloadGun++;
        if(reloadGun > 1000) reloadGun = 1000;
        
        if(reloadGun < 0) reloadGun = 0;
    }
    
    public void render() {
        //game.drawPixel(x, y, 0, 0, 255);
        for(int i=-3; i<4; i++) {
            for(int k=-3; k<4; k++) {
                game.drawPixel(x+i, y+k, team.r, team.g, team.b);
            }
        }

        //game.drawCircle(x, y, 255, 255, 255, size);
        
        //shieldBar.render(game);
    }
}
