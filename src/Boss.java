import java.util.ArrayList;
public class Boss extends Mob
{
    ArrayList<Cover> minions = new ArrayList<Cover>();
    double tx = 500;
    double ty = 500;
    
    double spacing = 50;
    
    int waitTime = 200;
    int attackTime = 0;
    int explodeArea = 200;
    
    double rotation = 0;
    double rotSpeed = 2.5;
    
    //double rotSpeed = 15;
    
    
    boolean doAi = false;
    
    public Boss() {
        maxHp = 200000;
        hp = maxHp;
        speed = 2;
        regen = 0;
        size = 100;
        spacing = size + 50;
    }

    public void die() {
        super.die();
        for(int i=0; i<minions.size(); i++) {
            minions.get(i).die();
        }
        level.add( new Explosion( this, x, y, 200, 0 ) );
    }
    
    public void hitInRange() {
        ArrayList<Mob> entities = level.getInRange( x, y, size);

        for(int i=0; i<entities.size(); i++) {
            Mob m = entities.get(i);
            if(!team.equals(m.team)) m.damage( 5, this );
        }

    }

    public void tick() {
        damageTime = 0;
        
        super.tick();
        
        hitInRange();
        
        rotation += rotSpeed;
        
        while(rotation >= 360) {
            rotation -= 360;
        }
        
        if(doAi && hp < maxHp / 2) {
            speed = 3;
            if(waitTime>100) waitTime = 100;
            tx = 600;
            rotSpeed = 5;
        }
        
        if(doAi && hp < maxHp / 4) {
            speed = 4;
            if(waitTime>50) waitTime = 50;
            tx = 700;
            rotSpeed = 10;
        }
        
        if(doAi && hp < maxHp / 8) {
            speed = 5;
            if(waitTime>25) waitTime = 25;
            tx = 800;
            rotSpeed = 15;
        }

        if(doAi && Math.abs(ty - y) < speed && Math.abs(tx - x) < speed) {
            ty = (int) ( 200+ ( Math.random() * 600 ) );
            waitTime = 100;
        }
        
        if(Math.abs(tx - x) < speed) x = tx;
        if(Math.abs(ty - y) < speed) y = ty;
        
        doMinions();

        if(doAi && attackTime < 1 && hp < maxHp / 2) {
            if(hp>maxHp / 4) attackTime = 100;
            else attackTime = 50;
            
            for(int i=0; i<10; i++) {
                int xOfs = ( -1 * explodeArea ) +  ( (int)( Math.random() * ( explodeArea * 2 ) ) );
                int yOfs = ( -1 * explodeArea ) +  ( (int)( Math.random() * ( explodeArea * 2 ) ) );
                
                Explosion e = new Explosion(this, x+xOfs, y+yOfs, 50, 30);
                e.speed = 5;
                level.add( e );
            }
            
        }
        
        if(!doAi) waitTime = 0;

        if(waitTime < 1) {
            if(y<ty) addY(speed);
            if(y>ty) subY(speed);
            
            if(x<tx) addX(speed);
            if(x>tx) subX(speed);
        }

        waitTime--;
        if(waitTime < 0) waitTime = 0;

        attackTime--;
        if(attackTime < 0) attackTime = 0;
        if(attackTime > 10000) attackTime = 10000;

        bar.x = x - 40;
        bar.y = y + 15;
        bar.height = 15;
        bar.width = 80;
    }

    public void doMinions() {
        for(int i=0; i<minions.size(); i++) {
            if(!level.has( minions.get(i) ) ) minions.remove(i);
        }

        while(minions.size()<4) {
            Cover t = new Cover();
            t.team = this.team;
            t.x = x;
            t.y = y;
            t.speed = 50;
            t.size = 50;
            level.add(t);
            minions.add(t);
        }

        double[] end = findPosByAngle(x, y, rotation, spacing);
        
        minions.get(0).x = x + end[0];
        minions.get(0).y = y + end[1];
        
        end = findPosByAngle(x, y, rotation + 90, spacing);

        minions.get(1).x = x + end[0];
        minions.get(1).y = y + end[1];
        
        end = findPosByAngle(x, y, rotation + 180, spacing);

        minions.get(2).x = x + end[0];
        minions.get(2).y = y + end[1];
        
        end = findPosByAngle(x, y, rotation + 270, spacing);

        minions.get(3).x = x + end[0];
        minions.get(3).y = y + end[1];
    }

    public void render() {
        for(int i=-5; i<6; i++) {
            for(int k=-5; k<6; k++) {
                game.drawPixel(x+i, y+k, team.r, team.g, team.b);
            }
        }

        for(int i=-7; i<=7; i++) {
            game.drawPixel(x-i, y+i, 255, 255, 255);
        }

        for(int i=-7; i<=7; i++) {
            game.drawPixel(x-i, y-i, 255, 255, 255);
        }

        game.drawCircle(x, y, 255, 255, 255, size);
        
        double[] end = findPosByAngle(x, y, rotation, 150);
        
        game.drawLine(x, y, x + end[0], y + end[1], 255, 0, 0, 100);
        
        ArrayList<Mob> entities = level.getInRange( x, y, size);

        for(int i=0; i<entities.size(); i++) {
            Mob m = entities.get(i);
            if(!team.equals(m.team)) game.drawLine(x, y, m.x, m.y, 255, 255, 255);
        }
    }
}