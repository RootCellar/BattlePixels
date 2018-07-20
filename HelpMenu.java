import java.awt.*;
public class HelpMenu extends Menu
{
    public HelpMenu() {
        
    }
    
    public void tick() {
        if(game.input.space.wasDown()) game.setMenu( new StartMenu() );
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawString("Space to exit this help menu", 50, 10);
        g.drawString("T to pause the game", 50, 30);
        g.drawString("Use W, A, S, D to move", 50, 50);
        g.drawString("Use space to fire the machine gun", 50, 70);
        g.drawString("Use K to fire the shotgun", 50, 90);
        g.drawString("Use L to fire the bomb", 50, 110);
        
    }
}