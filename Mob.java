public class Mob extends Entity
{
    double hp = 100;
    double maxHp = 100;
    double regen = 1;
    int damageTime = 0;
    int dir = 0;
    
    double rot = 0;
    
    double speed = 1;
    PixelBar bar = new PixelBar();
    boolean isAlive=true;
    
    public double[] findPosByAngle(double x1, double y1, double r, double d) {
        double[] toRet = new double[2];
        
        while(r >= 360) {
            r-=360;
        }
        
        while(r < 0) {
            r+=360;
        }
        
        if(r % 90 == 0) {
            
            if(r==90) {
                toRet[0] = d;
                toRet[1] = 0;
                return toRet;
            }
            
            if(r==180) {
                toRet[0] = 0;
                toRet[1] = d;
                return toRet;
            }
            
            if(r==270) {
                toRet[0] = d * -1;
                toRet[1] = 0;
                return toRet;
            }
            
            if(r==360) {
                toRet[0] = 0;
                toRet[1] = d * -1;
                return toRet;
            }
            
        }
        
        int c = 0;
        while(r >= 90 ) {
            r-=90;
            c++;
        }
        
        if(c==1 || c==3) r= 90 - r;
        
        double x2 = d * Math.sin( Math.toRadians(r) );
        double y2 = d * Math.cos( Math.toRadians(r) );
        
        if(c==0) {
            y2*=-1;
        }
        
        if(c==1) {
            //y2*=-1;
            
        }
        
        if(c==2) {
            //y2*=-1;
            x2*=-1;
        }
        
        if(c==3) {
            x2*=-1;
            y2*=-1;
        }
        
        toRet[0] = x2;
        toRet[1] = y2;
        
        return toRet;
    }

    public void addX(double a) {
        x+=a;
        dir=1;
    }

    public void subX(double a) {
        x-=a;
        dir=3;
    }

    public void addY(double a) {
        y+=a;
        dir=2;
    }

    public void subY(double a) {
        y-=a;
        dir=0;
    }

    public Projectile newProjectile() {
        Projectile p = new Projectile(this);
        p.x=x;
        p.y=y;
        p.velocX=10;
        p.damage=15;
        return p;
    }

    public void regen() {
        if(damageTime>0) {
            damageTime--;
        }
        else hp+=regen;
    }

    public void killed(Mob m) {

    }

    public void die(Mob m) {
        m.killed(this);
        if(isAlive) {
            die();
        }
    }

    public void die() {
        isAlive=false;
        level.remove(this);
    }

    public void checkHp() {
        if(hp<=0) {
            die();
            hp=0;
        }

        if(hp>=maxHp) hp=maxHp;
    }

    public void damage(double a) {
        hp-=a;
        damageTime = 400;
        //checkHp();
    }

    public void damage(double a, Projectile p, Mob m) {
        damage(a, m);
    }

    public void damage(double a, Mob m) {
        damage(a);
        if(hp<=0) die(m);
    }

    public void collision() {
        if(x<10) x=10;
        if(x>500) x=500;
        if(y<10) y=10;
        if(y>500) y=500;
    }

    public void shoot(Projectile p) {
        level.add(p);
    }

    public void shoot() {
        Projectile p = new Projectile(this);
        p.x=x;
        p.y=y;
        p.damage=10;
        p.velocY=-10;
        level.add(p);
    }

    public void tick() {
        regen();
        checkHp();

        doLifeBar();

    }

    public void doLifeBar() {

        bar.x= x - 10;
        bar.y= y + 10;
        bar.width=20;
        bar.height=5;
        bar.has=hp;
        bar.outOf=maxHp;
        bar.calcPercent();

    }
}