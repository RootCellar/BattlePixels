public class Player extends Mob
{
    int reloadBig = 0;
    int reloadSmall = 0;
    int reloadShotgun = 0;
    int kills = 0;
    int energy = 0;
    int maxEnergy = 1000;
    int maxShield = 1000;
    int shield = 1000;

    public Player() {
        maxHp=2000;
        hp=maxHp;
        regen = 1;
        team = new Team();
        speed=2;
        shield = maxShield;
        y=500;
    }

    public void damage(int a) {
        //if(a<=100) return;
        shield -= a;
        if(shield<0) {
            hp+=shield;
            shield=0;
        }
        //super.damage(a);
        damageTime = 400;
    }

    public void revive() {
        isAlive=true;
        hp=maxHp;
        shield = maxShield;
    }

    public void killed(Mob m) {
        kills++;
    }

    public void render() {
        //game.drawPixel(x, y, 0, 0, 255);
        for(int i=-3; i<4; i++) {
            for(int k=-3; k<4; k++) {
                game.drawPixel(x+i, y+k, 0, 0, 255);
            }
        }
    }

    public void tick() {
        super.tick();

        regen = maxHp/500;

        energy++;
        if(energy>=maxEnergy) energy=maxEnergy;

        shield+= 1;
        if(shield>=maxShield) shield=maxShield;

        bar.x=x-20;
        bar.y=y+15;
        bar.width=40;
        bar.height=10;

        if(reloadBig<=1000) reloadBig++;
        reloadSmall++;
        reloadShotgun++;   
        if(reloadSmall>100) reloadSmall=100;
        if(reloadShotgun>100) reloadShotgun=100;
    }

    public void shootShotgun() {
        if(reloadShotgun>=100) {
            reloadSmall=5;
            for(int i=0; i<20; i++) {
                Projectile p = newProjectile();
                p.velocX=10;
                p.damage=20;
                p.setOffset(30);
                p.damageRange = 20;
                p.size = 1;
                shoot(p);
                reloadSmall=5;
            }
            reloadShotgun=0;
            reloadSmall=0;
        }
    }

    public void shootSmall() {
        if(reloadSmall>=5) {
            for(int i=0; i<1; i++) {
                Projectile p = newProjectile();
                //p.velocX=10;
                p.setByDir(1, 10);
                p.damage=40;
                p.setOffset(15);
                p.damageRange = 15;
                p.size = 2;
                shoot(p);
                reloadSmall=0;
            }
        }
    }

    public void shootBig() {
        if(reloadBig>=100) {
            Bomb b = new Bomb(this);
            b.x=x;
            b.y=y;
            b.velocX=3;
            b.damage=20;
            b.setOffset(20);
            b.maxTime=100;
            b.eRadius=1000;
            b.size = 5;
            shoot(b);
            reloadBig=0;
        }
    }

}