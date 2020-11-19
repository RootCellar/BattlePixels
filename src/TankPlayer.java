public class TankPlayer extends Player {

    double maxShield = 4000;
    double shield = maxShield;

    PixelBar shieldBar = new PixelBar();

    int reloadGun = 0;

    public TankPlayer(InputListener in) {
        super(in);

        maxHp = 5000;
        hp = maxHp;
        regen = maxHp / 300;
        team = new Team();
        speed = 1.5;
        size = 30;
        y = 500;
    }

    public void damage(double a, Projectile p, Mob m) {
        super.damage(a, p, m);
    }

    public void damage(double a, Mob m) {
        super.damage(a, m);

        //m.damage(a, this); //Some kind of thorns effect
    }

    public void damage(double a) {
        shield -= a;
        if(shield<0) {
            hp+=shield;
            shield=0;
            damageTime = 400;
        }
    }

    public void shootGun() {
        if(reloadGun>=1) {
            for(int i=0; i<1; i++) {
                Projectile p = newProjectile();
                /*
                Bomb p = new Bomb(this);
                p.x = x;
                p.y = y;
                 */
                //p.velocX=10;
                p.setByDir(1, 10);
                p.damage=25;
                p.setOffset(15);
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

        shieldBar.x = x - 20;
        shieldBar.y = y + 30;
        shieldBar.width = 40;
        shieldBar.height = 10;
        shieldBar.gc = 0;
        shieldBar.bc = 255;
        shieldBar.has = shield;
        shieldBar.outOf = maxShield;
        shieldBar.calcPercent();

        shield += (maxShield / 600);
        if(shield >= maxShield) shield = maxShield;
        if(shield < 0) shield = 0;

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

        game.drawCircle(x, y, 255, 255, 255, size);

        shieldBar.render(game);
    }
}
