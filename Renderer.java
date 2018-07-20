public class Renderer implements Runnable
{
    Game game;
    boolean going = false;
    int waitTime = 1;
    boolean canRender = false;
    
    public Renderer(Game g) {
        game = g;
    }
    
    public synchronized void canRender(boolean can) {
        canRender = can;
    }

    public void start() {
        if(!going) {
            going = true;
            new Thread(this).start();
        }
    }

    public void stop() {
        going = false;
    }

    public void run() {
        double nsPerFrame = 1000000000.0 / game.fps;
        long lastFrame = System.nanoTime();

        while(going) {
            try{

                nsPerFrame = 1000000000.0 / game.fps;
                
                if(game.fps>100) {
                    waitTime = 0;
                }
                else {
                    waitTime = 1;
                }

                if( ( System.nanoTime() - lastFrame ) >= nsPerFrame && canRender) {
                    lastFrame = System.nanoTime();
                    game.render();
                    game.frames2++;
                    //lastFrame = System.nanoTime();
                }

                Thread.sleep(waitTime);
                
            }catch(Exception e) {

            }
        }
    }
}