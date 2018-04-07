import java.util.ArrayList;
import java.awt.event.*;
public class InputListener implements KeyListener
{
    public class Key
    {
        public boolean down=false;
        public boolean wasDown=false;
        public void Key()
        {
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if(pressed && !down) wasDown=true;
            down = pressed;
        }

        public boolean wasDown() {
            if(wasDown) {
                wasDown=false;
                return true;
            }
            else return false;
        }
    }

    public ArrayList<Key> keys = new ArrayList<Key>();

    Key up = new Key();
    Key down = new Key();
    Key left = new Key();
    Key right = new Key();
    Key space = new Key();
    Key j = new Key();
    Key k = new Key();
    Key l = new Key();
    Key p = new Key();
    Key t = new Key();
    Key c = new Key();
    Key v = new Key();
    Key y = new Key();
    Key u = new Key();
    Key i = new Key();
    Key z = new Key();
    Key x = new Key();
    Key e = new Key();
    Key b = new Key();
    Key n = new Key();
    Key m = new Key();

    public InputListener(Game game) {
        game.screen.addKeyListener(this);
    }

    public void toggle(KeyEvent ke, boolean pressed) {
        //System.out.println(ke);
        if(ke.getKeyCode() == KeyEvent.VK_W) up.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_A) left.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_SPACE) space.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_J) j.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_K) k.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_L) l.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_P) p.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_T) t.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_C) c.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_V) v.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_I) i.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_U) u.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_Y) y.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_Z) z.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_X) x.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_E) e.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_B) b.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_N) n.toggle(pressed);
        if(ke.getKeyCode() == KeyEvent.VK_M) m.toggle(pressed);
    }

    public void keyPressed(KeyEvent ke) {
        toggle(ke,true);
    }

    public void keyReleased(KeyEvent ke) {
        toggle(ke,false);
    }

    public void keyTyped(KeyEvent ke) {

    }
}