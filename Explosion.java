import java.util.*;
public class Explosion extends Entity
{
    int range = 50;
    int current = 0;
    Mob shooter;
    int damage=1;
    Team t;
    public Explosion(Mob s, int one, int two, int r, int d) {
        x=one;
        y=two;
        range=r;
        shooter=s;
        team=s.team;
        damage=d;
    }
    
    public void tick() {
        ArrayList<Mob> toDamage = level.getInRange(x, y, current);
        current++;
        
        for(int i=0; i<toDamage.size(); i++) {
            Mob m = toDamage.get(i);
            if(!team.equals(m.team) )m.damage( damage, shooter );
        }
        
        if(current>=range) remove();
    }
    
    public void render() {
        
        /**
        int sub = current/2;
        
        for(int i=0; i<current; i++) {
            //Strait lines from center
            game.drawPixel(x, y-i, 255, 0, 0);
            game.drawPixel(x, y+i, 255, 0, 0);
            game.drawPixel(x+i, y, 255, 0, 0);
            game.drawPixel(x-i, y, 255, 0, 0);
            
            //Diagonal Lines
            
            game.drawPixel(x-i, y-i, 255, 0, 0);
            game.drawPixel(x+i, y+i, 255, 0, 0);
            game.drawPixel(x+i, y-i, 255, 0, 0);
            game.drawPixel(x-i, y+i, 255, 0, 0);
            
        }
        */
        game.drawCircle(x, y, 255, 0, 0, current);
    }
}