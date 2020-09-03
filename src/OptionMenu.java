import java.awt.*;
public class OptionMenu extends Menu
{
    int choice = 0;
    
    String[] choices = {
                        "TPS",
                        };
                        
    int tpsCount = 50;
    
    Menu previous;
    
    boolean obtained = false;
    
    public OptionMenu(Menu m) {
        previous = m;
    }
    
    public void tick() {
        if(!obtained) {
            tpsCount = game.tps;
            obtained = true;
            
            game.input.left.wasDown();
            game.input.aright.wasDown();
            game.input.aleft.wasDown();
        }
        
        if(choice == 0) {
            if(game.input.aright.wasDown()) {
                tpsCount += 5;
            }
            
            if(game.input.aleft.wasDown()) {
                tpsCount -= 5;
            }
            
            if(tpsCount < 5) tpsCount = 5;
            if(tpsCount > 1000) tpsCount = 1000;
        }
        
        if(game.input.space.wasDown()) {
            game.tps = tpsCount;
            
            game.setMenu( previous );
            //if(choice == 0) game.clearMenu();
            //if(choice == 0) game.setMenu( new ClassMenu() );
            //if(choice == 1) game.setMenu( new HelpMenu( this ) );
        }
        
        if(game.input.left.wasDown()) {
            tpsCount = 50;
        }
        
        if(game.input.down.wasDown()) choice++;
        if(game.input.up.wasDown()) choice--;
        
        if(choice<0) choice = 0;
        if(choice>choices.length-1) choice = choices.length - 1;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.drawString("Hello!", 50, 50);
        g.drawString("Welcome to the Game", 50, 65);
        g.drawString("W = Up, S = Down, Space = exit", 50, 80);
        g.drawString("A = reset", 50, 95);
        
        for(int i=0; i<choices.length; i++) {
            if( i == choice) g.drawString("> "+choices[i], 50, (i*20)+120);
            else g.drawString(choices[i], 50, (i*20)+120);
        }
        
        g.drawString("TARGET TPS: " + tpsCount, 180, 65);
    }
    
}