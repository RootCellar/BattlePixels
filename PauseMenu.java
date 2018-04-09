import java.awt.*;
public class PauseMenu extends Menu
{
    int choice = 0;
    
    String[] choices = {"Resume",
                        "Start Over",
                        //"About"
                        };
                        
    boolean firstTick = true;
    
    public PauseMenu() {
    }
                        
    public void tick() {
        
        if(firstTick) {
            game.input.space.wasDown();
            firstTick = false;
        }
        
        if(game.input.space.wasDown()) {
            
            if(choice == 0) game.clearMenu();
            
            if(choice == 1) {
                game.setup();
                game.clearMenu();
            }
            
            //if(choice == 2) game.setMenu( new AboutMenu() );
        }  
        
        if(game.input.down.wasDown()) choice++;
        if(game.input.up.wasDown()) choice--;
        
        if(choice<0) choice = 0;
        if(choice>choices.length-1) choice = choices.length - 1;
        
        game.input.u.wasDown();
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for(int i=0; i<choices.length; i++) {
            if( i == choice) g.drawString("> "+choices[i], 50, (i*20)+60);
            else g.drawString(choices[i], 50, (i*20)+60);
        }
    }
}