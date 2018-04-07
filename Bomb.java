public class Bomb extends Projectile
{
    int eRadius = 50;
    public Bomb(Mob m) {
        super(m);
    }
    
    public void tick() {
        x+=velocX;
        y+=velocY;
        time++;
        
        if(time>=maxTime) {
            explode();
        }
    }
    
    public void explode() {
        level.add( new Explosion( shooter, x, y, eRadius, damage ) );
        remove();
    }
}