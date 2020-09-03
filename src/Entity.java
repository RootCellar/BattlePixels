import java.util.ArrayList;
public class Entity
{
    public static Game game;
    double x;
    double y;
    double size = 0;
    Level level;
    Team team;
    
    boolean visible = true;
    
    public void remove() {
        level.remove(this);
    }
    
    public void tick() {
        
    }
    
    public void setLevel(Level l) {
        level=l;
    }   
    
    public void render() {
        if(!visible) return;
        
        game.drawPixel(x, y, 128, 128, 128);
    }
}