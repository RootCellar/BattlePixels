import java.awt.*;
public class StartMenu extends Menu
{
    int choice = 0;
    
    String[] choices = {"Start the Game",
                        "How to play",
                        "About",
                        "OPTIONS"
                        };
    
    public StartMenu() {
        
    }
    
    public void tick() {
        if(game.input.space.wasDown()) {
            
            //if(choice == 0) game.clearMenu();
            if(choice == 0) game.setMenu( new ClassMenu() );
            if(choice == 1) game.setMenu( new HelpMenu( this ) );
            if(choice == 2) game.setMenu( new AboutMenu( this ) );
            if(choice == 3) game.setMenu( new OptionMenu( this ) );
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
        g.drawString("(W = Up, S = Down, Space = Select)", 50, 80);
        
        for(int i=0; i<choices.length; i++) {
            if( i == choice) g.drawString("> "+choices[i], 50, (i*20)+100);
            else g.drawString(choices[i], 50, (i*20)+100);
        }
    }
    
}