import java.awt.*;
public class AboutMenu extends Menu 
{
    public void tick() {
        if(game.input.space.wasDown()) game.setMenu( new StartMenu() );
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawString("Space to exit this menu", 30, 30);
        g.drawString("Game created by Darian Marvel", 30, 50);
        g.drawString("Game development started March 9th, 2018", 30, 70);
    }
}