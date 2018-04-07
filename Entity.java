import java.util.ArrayList;
public class Entity
{
    public static Game game;
    int x;
    int y;
    Level level;
    Team team;
    
    public void remove() {
        level.remove(this);
    }
    
    public void tick() {
        
    }
    
    public void setLevel(Level l) {
        level=l;
    }   
    
    public void render() {
        game.drawPixel(x, y, 128, 128, 128);
    }
}