import java.awt.*;
public class HelpMenu extends Menu
{
    Menu previous;
    public HelpMenu(Menu m) {
        previous = m;
    }
    
    public void tick() {
        if(game.input.space.wasDown()) game.setMenu( previous );
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawString("Space to exit this help menu", 50, 10);
        g.drawString("T to pause the game", 50, 30);
        g.drawString("Use W, A, S, D to move", 50, 50);
        g.drawString("Use space to fire the main gun of your class", 50, 70);
        g.drawString("Each class has it's own specific abilities,", 50, 90);
        g.drawString("and their descriptions will include their controls", 50, 110);
        
    }
}