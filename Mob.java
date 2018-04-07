public class Mob extends Entity
{
    int hp = 100;
    int maxHp = 100;
    int regen = 1;
    int damageTime = 0;
    int dir = 0;
    int speed = 1;
    PixelBar bar = new PixelBar();
    boolean isAlive=true;
    
    public void addX(int a) {
        x+=a;
        dir=1;
    }
    
    public void subX(int a) {
        x-=a;
        dir=3;
    }
    
    public void addY(int a) {
        y+=a;
        dir=2;
    }
    
    public void subY(int a) {
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
        isAlive=false;
        m.killed(this);
        level.remove(this);
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
    
    public void damage(int a) {
        hp-=a;
        damageTime = 400;
        //checkHp();
    }
    
    public void damage( int a, Projectile p, Mob m) {
        damage(a, m);
    }
    
    public void damage( int a, Mob m) {
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
        bar.x=x-10;
        bar.y=y+10;
        bar.width=20;
        bar.height=5;
        bar.has=hp;
        bar.outOf=maxHp;
        bar.calcPercent();
        
    }
}