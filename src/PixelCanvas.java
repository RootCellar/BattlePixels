import java.awt.*;
import java.awt.image.*;
import javax.swing.JFrame;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;
public class PixelCanvas extends Canvas implements Runnable
{
    int WIDTH = 700;
    int HEIGHT = 700;
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    JFrame frame;
    BufferStrategy bs = getBufferStrategy();
    int targetfps=30;
    boolean going = false;
    double Pfps = 0;
    long timeSinceLast = System.nanoTime();
    int frames = 0;
    int fps = 0;
    PixelCanvasUser user;
    boolean drawFPS = false;
    long timeSinceCheck = System.nanoTime();

    public void resize() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public void setSize(int n) {
        frame.setMinimumSize(new Dimension(n,n));
    }

    public void setTargetFps(int f) {
        targetfps=f;
    }

    public void start() {
        new Thread(this).start();
        going=true;
    }

    public void stop() {
        going=false;
    }

    public void run() {
        going = true;
        long time = System.nanoTime();
        while(going) {
            try{
                Thread.sleep(1);
            }catch(Exception e) {

            }

            try{
                if(System.nanoTime() - time >= 1000000000/targetfps) {
                    draw();
                    time = System.nanoTime();
                }
            }catch(Exception e) {

            }
        }
        going=false;
    }

    public void draw() {
        long time1 = System.nanoTime();
        render();
        long time2 = System.nanoTime();
        if(System.nanoTime() - timeSinceLast > 1000000000) {
            Pfps = 1000000000.0 / ( (double)( time2 - time1 ) );
            fps = frames;
            frames = 0;
            timeSinceLast = System.nanoTime();
        }
        //fps = Math.random();
    }   

    public void drawFps(Graphics g) {
        g.setColor(Color.RED);
        //g.drawString("PFPS: "+Pfps,10,10);
        if(drawFPS) g.drawString("FPS: "+fps,10,20);
    }

    public void render() {
        
        if(System.nanoTime() - timeSinceCheck >= 500000000) {
            if(WIDTH != getWidth() || HEIGHT != getHeight()) {
                WIDTH = getWidth();
                HEIGHT = getHeight();
                resize();
                timeSinceCheck = System.nanoTime();
            }
        }

        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLUE);
        //g.fillRect(0,0,getWidth(),getHeight());
        
        g.drawImage(image,0,0,WIDTH,HEIGHT,null);
        
        if(user!=null) user.draw(g);

        drawFps(g);

        g.dispose();
        bs.show();
        frames++;
    }

    public void clear() {
        for(int y=0; y< HEIGHT; y++) {
            for(int x=0; x < WIDTH; x++) {
                pixels[x+y*WIDTH]=0;
            }
        }
    }

    public int getByPos(int x, int y, int w) {
        return x + y * w;
    }

    public void setPixel(int x, int y, int c) {
        try{
            if(x<0 || y<0) return;
            if(x>=WIDTH || y>=HEIGHT) return;
            int i = x + y * WIDTH;
            pixels[i]=c;
        }catch(Exception e) {

        }
    }

    public void setPixel(int x, int y, int r, int g, int b) {
        try{
            if(x<0 || y<0) return;
            if(x>=WIDTH || y>=HEIGHT) return;
            int i = x + y * WIDTH;
            pixels[i]=getColor(r,g,b);
        }catch(Exception e) {

        }
    }
    
    //Test Method, possibly faster
    ///*
    public static int getColor(int r, int g, int b) {
        //int r2=r<<16;
        //int g2=g<<8;
        //int b2=b;
        
        try{
            return (r << 16)+(g << 8)+(b);
        }catch(Exception e) {
            return 0;
        }
    }
    //*/
    
    /* Original Method
     * May be slower
    public static int getColor(int r, int g, int b) {
        int r2=r<<16;
        int g2=g<<8;
        int b2=b;

        try{
            return r2+g2+b2;
        }catch(Exception e) {
            return 0;
        }
    }
    */

    public void randomize() {
        for(int y=0; y< HEIGHT; y++) {
            for(int x=0; x < WIDTH; x++) {
                pixels[x+y*WIDTH]=(int)(Math.random()*16777215.0);
                //pixels[x+y*WIDTH]=0;
            }
        }
    }

    public PixelCanvas() {
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        //frame.setResizable(false);
        frame.setMinimumSize(new Dimension(500,500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        PixelCanvas p = new PixelCanvas();
        int frames=0;
        long start=System.nanoTime();
        Terminal term = new Terminal();
        while(true) {
            try{
                p.clear();
                p.randomize();
                p.render();
                //Thread.sleep(1000 / 10);
                frames++;
            }catch(Exception e) {
                e.printStackTrace();
            }
            if(System.nanoTime()-start >= 1000000000) {
                term.write(frames+"");
                frames=0;
                start=System.nanoTime();
            }
        }
    }
}