public class Cover extends Mob
{
    public Cover() {
        hp = maxHp = 20000;
        size = 30;
    }
    
    public void damage(double a) {
        //hp-=a;
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
    
    public void tick() {
        checkHp();
        
        bar.x= x - 10;
        bar.y= y + 10;
        bar.width=20;
        bar.height=8;
        bar.has=hp;
        bar.outOf=maxHp;
        bar.calcPercent();
        
        
    }
    
    public void render() {
        for(int i=-5; i<6; i++) {
            for(int k=-5; k<6; k++) {
                game.drawPixel(x+i, y+k, team.r, team.g, team.b);
            }
        }
        
        game.drawCircle(x, y, 255, 255, 255, size);
    }
}