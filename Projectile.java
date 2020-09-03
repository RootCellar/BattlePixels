import java.util.ArrayList;
public class Projectile extends Entity
{
    int velocX = 0;
    int velocY = 0;
    int maxTime = 1000;
    int time = 0;
    int damage = 0;
    int damageRange = 10;
    int size = 0;
    Mob shooter;
    Team team;
    public Projectile(Mob m) {
        shooter = m;
        team = shooter.team;
    }

    public int setOffset(int acc) {
        int offset = (int)(Math.round( Math.random() * ( acc * 2 ) ) - acc );
        if(velocX!=0) y+=offset;
        if(velocY!=0) x+=offset;
        return offset;
    }
    
    public void setByDir(int dir, int speed) {
        velocX=0;
        velocY=0;
        if(dir==0) velocY=speed*-1;
        if(dir==1) velocX=speed;
        if(dir==2) velocY=speed;
        if(dir==3) velocX=speed*-1;
    }

    public void hit(Mob target) {

        //if(team==null || target.team==null) target.damage( damage, this, shooter);
        if(!target.team.equals(team)) {
            target.damage( damage, this, shooter );
            remove();
        }

    }

    public void remove() {
        level.remove(this);
    }

    public void tick() {
        time++;
        if(time>=maxTime) {
            remove();
        }
        x+=velocX;
        y+=velocY;

        hitInRange();
    }
    
    public void hitInRange() {
        ArrayList<Mob> entities = level.getInRange( x, y, damageRange);

        for(int i=0; i<entities.size(); i++) {
            Mob m = entities.get(i);
            if(!team.equals(m.team)) hit(m);
        }

    }

    public void render() {
        for(int i=0-size; i<=size; i++) {
            for(int k=0-size; k<=size; k++) {
                game.drawPixel(x+i, y+k, 0, 255, 0);
            }
        }
        //game.drawPixel(x, y, 0, 255, 0);
    }   
}