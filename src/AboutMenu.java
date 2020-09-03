import java.awt.*;
public class AboutMenu extends Menu 
{
    Menu previous;
    public AboutMenu(Menu m) {
        previous = m;
    }
    
    public void tick() {
        if(game.input.space.wasDown()) game.setMenu( previous );
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawString("Space to exit this menu", 30, 30);
        g.drawString("Game created by Darian Marvel", 30, 50);
        g.drawString("Game development started March 9th, 2018", 30, 70);
        g.drawString("This game is very experimental, and is meant", 30, 90);
        g.drawString("to be expanded upon in other projects.", 30, 110);
    }
}