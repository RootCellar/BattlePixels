public class Bomb extends Projectile
{
    int eRadius = 50;
    public Bomb(Mob m) {
        super(m);
        
        damageRange = 30;
    }
    
    public void hit(Mob target) {
        explode();
    }
    
    public void tick() {
        x+=velocX;
        y+=velocY;
        time++;
        
        hitInRange();
        
        if(time>=maxTime) {
            explode();
        }
    }
    
    public void explode() {
        level.add( new Explosion( shooter, x, y, eRadius, damage ) );
        remove();
    }
}