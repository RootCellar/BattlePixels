import java.util.ArrayList;
public class Boss extends Mob
{
    ArrayList<Tank> minions = new ArrayList<Tank>();
    double tx = 500;
    double ty = 500;
    
    double spacing = 50;
    
    int waitTime = 200;
    int attackTime = 0;
    int explodeArea = 200;
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
        level.add( new Explosion(this, x, y, 200, 0) );
    }

    public void tick() {
        super.tick();

        bar.x = x - 20;
        bar.y = y + 15;
        bar.height = 10;
        bar.width = 40;
        
        if(hp < maxHp / 2) {
            speed = 3;
            if(waitTime>100) waitTime = 100;
            tx = 600;
        }
        
        if(hp < maxHp / 4) {
            speed = 4;
            if(waitTime>50) waitTime = 50;
            tx = 700;
        }
        
        if(hp < maxHp / 8) {
            speed = 5;
            if(waitTime>25) waitTime = 25;
            tx = 800;
        }

        if(Math.abs(ty - y) < speed && Math.abs(tx - x) < speed) {
            ty = (int) ( 200+ ( Math.random() * 600 ) );
            waitTime = 100;
        }
        
        if(Math.abs(tx - x) < speed) x = tx;

        doMinions();

        if(attackTime < 1 && hp < maxHp / 2) {
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

        bar.x = x - 20;
        bar.y = y + 15;
        bar.height = 10;
        bar.width = 40;
    }

    public void doMinions() {
        for(int i=0; i<minions.size(); i++) {
            if(!level.has( minions.get(i) ) ) minions.remove(i);
        }

        while(minions.size()<4) {
            Tank t = new Tank();
            t.team = this.team;
            t.x = x;
            t.y = y;
            t.speed = 3;
            level.add(t);
            minions.add(t);
        }

        minions.get(0).tx = x - spacing;
        minions.get(0).ty = y;

        minions.get(1).tx = x + spacing;
        minions.get(1).ty = y;

        minions.get(2).tx = x;
        minions.get(2).ty = y + spacing;

        minions.get(3).tx = x;
        minions.get(3).ty = y - spacing;

        minions.get(0).reload = 0;
        minions.get(1).reload = 0;
        minions.get(2).reload = 0;
        minions.get(3).reload = 0;
    }

    public void render() {
        for(int i=-5; i<6; i++) {
            for(int k=-5; k<6; k++) {
                game.drawPixel(x+i, y+k, 255, 0, 0);
            }
        }

        for(int i=-7; i<=7; i++) {
            game.drawPixel(x-i, y+i, 255, 255, 255);
        }

        for(int i=-7; i<=7; i++) {
            game.drawPixel(x-i, y-i, 255, 255, 255);
        }

        game.drawCircle(x, y, 255, 255, 255, size);
    }
}