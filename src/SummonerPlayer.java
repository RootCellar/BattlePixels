public class SummonerPlayer extends Player {
    
    int reloadGun = 0;
    
    double mx = 0;
    double my = 0;
    
    double mtx = 0;
    double mty = 0;
    
    double mspeed = 3;
    
    double basemrange = 400;
    double mrange = basemrange;
    
    boolean droneMode = false;
    
    Drone minion;
    
    public SummonerPlayer(InputListener in) {
        super(in);
        
        maxHp = 3000;
        hp = maxHp;
        regen = maxHp / 700;
        team = new Team();
        speed = 2;
        size = 0;
        y = 500;
        
        mtx = x;
        mty = y;
    }
    
    public void damage(double a, Projectile p, Mob m) {
        super.damage(a, p, m);
    }

    public void damage(double a, Mob m) {
        super.damage(a, m);
    }

    public void damage(double a) {
        hp-=a;
        damageTime = 300;
    }
    
    public void shootGun() {
        if(reloadGun>=30) {
            for(int i=0; i<5; i++) {
                Projectile p = newProjectile();
                /*
                Bomb p = new Bomb(this);
                p.x = x;
                p.y = y;
                */
                //p.velocX=10;
                p.setByDir(1, 15);
                p.damage=20;
                p.setOffset(15);
                p.damageRange = 15;
                p.size = 1;
                shoot(p);
                reloadGun=0;
            }
        }
    }
    
    public void doMinion() {
        if(minion == null || !level.has(minion) ) {
            minion = new Drone();
            
            minion.team = team;
            minion.x = x;
            minion.y = y;
            minion.speed = mspeed;
            minion.size = -20;
            level.add(minion);
        }
        
        if(input.aup.down) mty -= mspeed;
        if(input.adown.down) mty += mspeed;
        if(input.aleft.down) mtx -= mspeed;
        if(input.aright.down) mtx += mspeed;
        
        if(level.getDistance(x, y, mtx, mty) > mrange) {
            mtx = mx;
            mty = my;
        } else {
            mx = mtx;
            my = mty;
        }
        
        if(input.e.wasDown()) {
            mx = x;
            my = y;
            
            mtx = x;
            mty = y;
        }
        
        minion.dir = 1;
        
        if(input.l.down) minion.shoot();
        if(input.k.wasDown()) droneMode = !droneMode;
        
        minion.tx = mx;
        minion.ty = my;
    }
    
    public void tick() {
        super.tick();
        
        mrange = basemrange;
        
        if(droneMode) {
            dx = minion.x;
            dy = minion.y;
            
            mrange += 400;
        }
        
        if(input.space.down) shootGun();
        
        doMinion();
        
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

        game.drawCircle(mx, my, 255, 255, 255, 10);
        
        game.drawCircle(x, y, 127, 127, 127, mrange);
        
        game.drawLine(x, y, minion.x, minion.y, 255, 255, 255, 20);
        
        //shieldBar.render(game);
    }
}
