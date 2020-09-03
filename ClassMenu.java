import java.awt.*;
public class ClassMenu extends Menu
{
    int choice = 0;
    
    String[] choices = {"Tank",
                        "Rampage",
                        "Summoner",
                        "Charger",
                        "Back"
                        };
                        
    int backNum = 4;
    boolean didBack = false;
    
    public ClassMenu() {
        
    }
    
    public void tick() {
        if(game.input.aup.wasDown()) {
            if(choice == backNum) {
                didBack = true;
            }
        }
        
        if(game.input.space.wasDown()) {
            
            if(choice == backNum) {
                game.setMenu( new StartMenu() );
            }
            
            if(choice == 0) {
                game.p = new TankPlayer( game.input );
                game.clearMenu();
                game.setup();
            }
            
            if(choice == 1) {
                game.p = new RampagePlayer( game.input );
                game.clearMenu();
                game.setup();
            }
            
            if(choice == 2) {
                game.p = new SummonerPlayer( game.input );
                game.clearMenu();
                game.setup();
            }
            
            if(choice == 3) {
                game.p = new ChargerPlayer( game.input );
                game.clearMenu();
                game.setup();
            }
            
        }
        
        if(game.input.down.wasDown()) choice++;
        if(game.input.up.wasDown()) choice--;
        
        if(choice<0) choice = 0;
        if(choice>choices.length-1) choice = choices.length - 1;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.drawString("Class Selection", 50, 50);
        g.drawString("Pick your class", 50, 65);
        g.drawString("(W = Up, S = Down, Space = Select)", 50, 80);
        
        for(int i=0; i<choices.length; i++) {
            if( i == choice) g.drawString("> " + choices[i], 50, (i*20)+100);
            else g.drawString("  " + choices[i], 50, (i*20)+100);
        }
        
        if(didBack) {
            g.setColor(Color.GREEN);
            g.drawString("You did it, you tried to see if Back is a", 180, 50);
            g.drawString("secret class. Sorry, but it really isn't!", 180, 65);
            g.drawString("Now you've done it, the class descriptions are green!", 180, 145);
        }
        
        if(choice == backNum) {
            g.drawString("Go back to the start menu", 180, 100);
            g.drawString("Did you expect this to be a class?", 180, 115);
            g.drawString("\"Back\" sure would make a great class, wouldn't it?", 180, 130);
        }
        
        if(choice == 0) {
            g.drawString("Very tough", 180, 100);
            g.drawString("High HP, and a shield to absorb damage", 180, 115);
            g.drawString("Moves slowly, but the minigun packs a punch", 180, 130);
            
            g.drawString("No special abilities. Just set up and use", 180, 160);
            g.drawString("that minigun with SPACE", 180, 175);
        }
        
        if(choice == 1) {
            g.drawString("Very fast", 180, 100);
            g.drawString("Deals tons of damage with a fast firing shotgun", 180, 115);
            g.drawString("Not very tough, slow to regenerate", 180, 130);
            
            g.drawString("No special abilities. Speed around and use", 180, 160);
            g.drawString("that epic shotgun with SPACE", 180, 175);
        }
        
        if(choice == 2) {
            g.drawString("Summons some drones", 180, 100);
            g.drawString("Drones can be controlled", 180, 115);
            g.drawString("Very weak in terms of damage and defense", 180, 130);
            
            g.drawString("Finally, a class with special abilities!", 180, 160);
            
            g.drawString("Arrow keys to move the drone's target location", 180, 175);
            g.drawString("E to set drone's target location to your location", 180, 190);
            g.drawString("K to use the best drone mode ever!", 180, 205);
            g.drawString("L to shoot the drone's gun", 180, 220);
        }
        
        if(choice == 3) {
            g.drawString("Weapons and stats charge up", 180, 100);
            g.drawString("Requires some devotion and skill", 180, 115);
            g.drawString("Weak, but gets tougher with devotion", 180, 130);
            
            g.drawString("Sadly, no special Skills", 180, 160);
            
            g.drawString("Fire rate starts slow, but speeds up", 180, 175);
            g.drawString("over time", 180, 190);
            //g.drawString("K to use the best drone mode ever!", 180, 205);
            //g.drawString("L to shoot the drone's gun", 180, 220);
        }
    }
    
}