public class RampagePlayer extends Player {
    
    int reloadGun = 0;
    
    public RampagePlayer(InputListener in) {
        super(in);
        
        maxHp = 2000;
        hp = maxHp;
        regen = maxHp / 1600;
        team = new Team();
        speed = 3;
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
        if(reloadGun>=10) {
            for(int i=0; i<10; i++) {
                Projectile p = newProjectile();
                /*
                Bomb p = new Bomb(this);
                p.x = x;
                p.y = y;
                */
                //p.velocX=10;
                p.setByDir(1, 15);
                p.damage=50;
                p.setOffset(30);
                p.damageRange = 15;
                p.size = 1;
                shoot(p);
                reloadGun=0;
            }
        }
    }
    
    public void tick() {
        super.tick();
        
        if(input.space.down) shootGun();
        
        reloadGun++;
        if(reloadGun > 1000) reloadGun = 1000;
        
        if(reloadGun < 0) reloadGun = 0;
    }
    
    public void render() {
        //game.drawPixel(x, y, 0, 0, 255);
        for(int i=-3; i<4; i++) {
            for(int k=-3; k<4; k++) {
                game.drawPixel(x+i, y+k, 0, 0, 255);
            }
        }

        //game.drawCircle(x, y, 255, 255, 255, size);
        
        //shieldBar.render(game);
    }
}
