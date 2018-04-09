import java.util.ArrayList;
public class Flag extends Entity
{    
    int radius = 50;
    int capturePoints = 0;
    PixelBar bar = new PixelBar();
    boolean captured = false;
    
    public Flag(int xPos, int yPos, int r, Team t) {
        x=xPos;
        y=yPos;
        radius=r;
        team = t;
    }
    
    public void tick() {
        ArrayList<Mob> mobs = level.getInRange( x, y, radius);
        
        for(int i=0; i<mobs.size(); i++) {
            Mob m = mobs.get(i);
            
            if(!m.team.equals(team)) capturePoints++;
            //capturePoints++;
        }
        
        bar.width = 60;
        bar.height = 10;
        bar.x = x - (bar.width / 2);
        bar.y = y + 25;
        bar.has = capturePoints;
        bar.outOf=500;
        bar.calcPercent();
        
        if(capturePoints>=500) {
            //capturePoints = 0;
            captured = true;
        }
    }
    
    public void render() {
        /**
        game.drawPixel(x-radius, y, team.r, team.g, team.b);
        game.drawPixel(x+radius, y, team.r, team.g, team.b);
        game.drawPixel(x, y-radius, team.r, team.g, team.b);
        game.drawPixel(x, y+radius, team.r, team.g, team.b);
        */
        game.drawCircle( x, y, team.r, team.g, team.b, radius);
       
        game.drawPixel(x, y, team.r, team.g, team.b);
        
        bar.render(game);
    }
}