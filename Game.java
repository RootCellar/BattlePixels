import java.net.*;
import java.util.ArrayList;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
public class Game implements Runnable, PixelCanvasUser
{
    public PixelCanvas screen = new PixelCanvas();
    public InputListener input = new InputListener(this);
    Renderer renderer = new Renderer(this);
    //public int x = 0;
    //public int y = 0;
    public Player p = new Player();
    public int xd = screen.WIDTH/2;
    public int yd = screen.HEIGHT/2;
    public int xo = 0;
    public int yo = 0;
    public Socket server;
    public boolean going=false;

    public Menu menu;

    public Level level = new Level();

    public Level[] levels = new Level[1]; // Multi-Level Support Test
    int currentLevel = 0;

    public PixelBar bar = new PixelBar();
    public PixelBar bar2 = new PixelBar();
    public PixelBar bar3 = new PixelBar();

    long start = System.nanoTime();
    int renderDistance = 1500;
    boolean paused = false;
    boolean lifeBars = true;
    boolean debug = false;
    
    boolean MULTI_RENDERING = false; //Set to true to enable multi-threaded rendering. WIP, glitchy
    
    int ticks = 0;
    int frames = 0;
    int tps = 50;
    int fps = 100;
    int pfps = 100;
    int waitTime = 1;
    int ticks2 = 0;
    int frames2 = 0;
    boolean spectate = false;
    boolean clearScreen = true;
    boolean ticking = false;
    RAMChecker ram = new RAMChecker();
    //public Mob[] mobs = new Mob[10000];
    //public ArrayList<Mob> mobs = new ArrayList<Mob>();

    public void setMenu(Menu m) {
        m.game = this;
        menu = m;
        //screen.user = m;
    }

    public void clearMenu() {
        menu = null;
        //screen.user = this;
    }

    public void takeScreenshot() {
        System.out.print("Attempting to take a screenshot...");
        try{
            boolean success = ImageIO.write(screen.image, "png", new File("Screenshot.png"));
            System.out.println(success);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new Game();
    }

    public Game() {
        //new Thread(this).start();
        setMenu( new StartMenu() );
        start();
        //screen.setSize(800);
    }

    public void setServer(Socket s) {
        server=s;
    }

    public void start() {
        if(!going) {
            going=true;
            new Thread(this).start();
            //screen.setVisible(true);
        }
    }

    public void stop() {
        going = false;
        //screen.setVisible(false);
    }

    public void setup() {
        level = new Level();

        p = new Player();

        screen.user=this;
        Entity.game=this;

        p.setLevel(level);
        p.team = new AllyTeam();

        level.addTeam( p.team );
        level.addTeam( new EnemyTeam() );

        //level.add( new Flag( 800, 500, 200, level.teams.get(1) ) );

        HeavyTank t = new HeavyTank();
        t.team=p.team;
        t.x=0;
        t.y=500;
        t.tx = 0;
        t.ty = 500;
        t.dir = 1;
        level.add(t);

        if(!spectate) level.add(p);

        levels[0] = level;

        for(int i=1; i<levels.length; i++) {
            levels[i] = new Level();
            levels[i].addTeam( new EnemyTeam() );

        }
        //level.add( new Tank() );
    }

    public synchronized void run() {
        long last = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / tps;
        double nsPerFrame = 1000000000.0 / fps;
        long lastFrame = System.nanoTime();
        long checkTime = System.nanoTime();
        ticking = false;
        setup();
        
        if(MULTI_RENDERING) renderer.start();
        while(going) {
            try{
                nsPerTick = 1000000000.0 / tps;
                nsPerFrame = 1000000000.0 / fps;

                waitTime = 1;
                if(fps>100 && !MULTI_RENDERING) waitTime = 0;
                
                long now = System.nanoTime();
                unprocessed += (now-last) / nsPerTick;
                last=now;

                renderer.canRender(false);
                while(unprocessed>=1) {
                    tick();
                    ticks2++;
                    unprocessed-=1;
                }
                renderer.canRender(true);
                //render();

                ///*
                if( !MULTI_RENDERING && ( ( System.nanoTime() - lastFrame ) >= nsPerFrame  ) ) {
                    lastFrame = System.nanoTime();
                    render();
                    frames2++;
                    //lastFrame = System.nanoTime();
                }
                //*/

                Thread.sleep(waitTime);

                while(input.j.down) {
                    Thread.sleep(1);
                }

                if( System.nanoTime() - checkTime > 1000000000) {
                    ticks = ticks2;
                    frames = frames2;
                    ticks2=0;
                    frames2=0;
                    checkTime = System.nanoTime();
                }

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        going=false;
    }

    public void draw(Graphics g) {
        //g.drawString("HI",(int)(Math.random()*400), (int)(Math.random()*400)); //TEST
        if(menu != null) {
            menu.draw(g);
            return;
        }
        g.setColor(Color.GREEN);
        if(debug) {
            g.drawString("X: "+p.x, 10,30);
            g.drawString("Y: "+p.y, 10,40);
            //g.drawString("E+P: "+(level.entities.size()+level.projectiles.size()), 10, 50);
            g.drawString("T:"+((System.nanoTime()-start)/1000000000), 10,60); 
            g.drawString("HP: "+p.hp+"/"+p.maxHp, 10, 70);
            g.drawString("KILLS: " + p.kills, 10, 80);
            g.drawString("DIR: " + p.dir, 10, 90);
            
            g.drawString("E+P: "+(level.entities.size()+level.projectiles.size()), 10, 110);
            g.drawString("E: "+level.entities.size(), 10, 120);
            g.drawString("P: "+level.projectiles.size(), 10, 130);
            g.drawString("T: "+level.teams.size(), 10, 140);
            g.drawString("F: "+level.flags.size(), 10, 150);
            
            g.drawString("TPS: "+ticks, 150, 30);
            g.drawString("FPS: "+frames, 150, 40);
            g.drawString("RD: "+renderDistance, 150, 50);
            //g.drawString("RAM: "+ram.percentUsed+"%", 100, 60);
            g.drawString("LEVEL: "+currentLevel, 150, 70);
            g.drawString("PFPS: "+pfps, 150, 80);
            
            //LEVEL DATA
            g.drawString("LEVEL TICK: "+level.levelTickingTime, 250, 30);
            g.drawString("ENTITY TICK: "+level.entityTickingTime, 250, 40);
            g.drawString("PROJ TICK: "+level.projectileTickingTime, 250, 50);
            g.drawString("FLAG TICK: "+level.flagTickingTime, 250, 60);
            g.drawString("TEAM TICK: "+level.teamTickingTime, 250, 70);
        }

        if(paused) {
            g.drawString("PAUSED", 100, 100);
        }

        if(!screen.hasFocus()) g.drawString("Click to focus", xd, yd-100);
    }

    public void render() {

        long start = System.nanoTime();
        
        if(menu != null) {
            screen.draw();
            screen.clear();
            return;
        }

        xd = screen.WIDTH/2;
        yd = screen.HEIGHT/2;
        xo=(int)Math.round(p.x);
        yo=(int)Math.round(p.y);
        if(clearScreen) screen.clear();

        //screen.setPixel(xd,yd,255,255,255);
        //screen.setPixel(xd+1,yd,255,255,255);
        //screen.setPixel(xd,yd+1,255,255,255);
        //screen.setPixel(xd+1,yd+1,255,255,255);

        //ai();

        for(int i=0; i<level.xBound; i+=50) {
            for(int k=0; k<level.yBound; k+=50) {
                drawPixel(i, k, 255, 255, 255);
            }
        }   

        for(int i=0; i<level.xBound; i++) { //Top Border
            drawPixel(i, 0, 255, 255, 255);
        }

        for(int i=0; i<level.xBound; i++) { //Bottom Border
            drawPixel(i, level.yBound, 255, 255, 255);
        }

        for(int i=0; i<level.yBound; i++) { //Left Border
            drawPixel(0, i, 255, 255, 255);
        }

        for(int i=0; i<level.yBound; i++) { //Right Border
            drawPixel(level.xBound, i, 255, 255, 255);
        }

        renderEntities();

        bar.x = p.x - 120;
        bar.y = ( p.y + ( screen.HEIGHT / 2 ) ) - 60;
        bar.width=240;
        bar.height=20;
        //bar.percent = ((p.hp*100)/p.maxHp);
        bar.has = p.hp;
        bar.outOf = p.maxHp;
        bar.calcPercent();
        bar.render(this);

        bar2.x = p.x - 120;
        bar2.y = ( p.y + ( screen.HEIGHT / 2 ) ) - 30;
        bar2.width=240;
        bar2.height=20;
        bar2.has = p.energy;
        bar2.outOf = p.maxEnergy;
        bar2.calcPercent();
        bar2.gc=0;
        bar2.bc=255;
        bar2.render(this);

        bar3.x = p.x - 120;
        bar3.y = ( p.y + ( screen.HEIGHT / 2 ) ) - 90;
        bar3.width=240;
        bar3.height=20;
        bar3.has = p.shield;
        bar3.outOf = p.maxShield;
        bar3.calcPercent();
        bar3.gc=255;
        bar3.rc=255;
        bar3.render(this);
        
        drawLine(p.x, p.y, 500, 500, 0, 255, 0, 100);
        drawLine(p.x, p.y, 0, 500, 0, 0, 255, 100);
        drawLine(p.x, p.y, 1000, 500, 255, 0, 0, 100);

        screen.draw();
        
        long end = System.nanoTime();
        
        long toDraw = end - start;
        
        pfps = (int) (1000000000 / toDraw);
        /**
        
        if(toDraw > 15000000) {
            renderDistance--;
        }
        else {
            renderDistance++;
        }
        
        */
        
        if(renderDistance<200) renderDistance = 200;
    }

    public void tick() {
        ram.run();

        if(menu != null) {
            menu.tick();
            return;
        }

        //if(!screen.hasFocus()) paused = true;

        if(input.t.wasDown()) {
            paused=!paused;
        }

        if(paused) return;

        if( menu == null) tickGame();
    }

    public void tickGame() {

        if(p.isAlive) {

            if(input.down.down) p.addY( p.speed );
            if(input.up.down) p.subY( p.speed );
            if(input.left.down) p.subX( p.speed );
            if(input.right.down) p.addX( p.speed );
            if(input.space.down) p.shootSmall();
            if(input.l.down) p.shootBig();
            if(input.k.down) p.shootShotgun();

        }

        if(input.p.wasDown()) debug = !debug;

        ///**
        //if(input.c.wasDown()) takeScreenshot();
        //if(input.u.wasDown() && renderDistance>=1000) renderDistance -= 50;
        //if(input.i.wasDown() && renderDistance<=2000) renderDistance += 50;
        if(input.u.wasDown()) setMenu( new PauseMenu() );
        if(input.v.wasDown()) fps = 10000;
        if(input.y.wasDown()) fps = 100;
        if(input.z.wasDown()) clearScreen = !clearScreen;
        //*/

        if(input.n.wasDown()) selLevel(currentLevel-1);
        if(input.m.wasDown()) selLevel(currentLevel+1);

        if(!p.isAlive && !spectate) {
            p.x=0;
            p.y=500;
            p.revive();
            level.add(p);
        }

        //level.tick();

        for(int i=0; i<levels.length; i++) {
            levels[i].tick();
        }
    }

    public void selLevel(int n) {
        if(n < 0 || n > levels.length - 1) return;
        level.remove(p);
        level = levels[n];
        level.add(p);
        currentLevel = n;

    }

    public void renderEntities() {
        for(int i=0; i<level.entities.size(); i++) {
            Entity e = level.entities.get(i);
            if( Level.getDistance(p.x, p.y, e.x, e.y) < renderDistance ) {
                e.render();
                if(e instanceof Mob) {
                    Mob m = (Mob)e;
                    if(lifeBars) m.bar.render(this);
                }
            }
        }

        for(int i=0; i<level.projectiles.size(); i++) {
            Projectile e = level.projectiles.get(i);
            if( Level.getDistance(p.x, p.y, e.x, e.y) < renderDistance) e.render();
        }

        for(int i=0; i<level.flags.size(); i++) {
            level.flags.get(i).render();
        }
    }

    public int getXOffset() {
        return xd-xo;
    }

    public int getYOffset() {
        return yd-yo;
    }
    
    public void drawPixel(double x, double y, int r, int g, int b) {
        drawPixel( (int)Math.round(x), (int)Math.round(y), r, g, b);
        //screen.setPixel((x-xo)+xd, (y-yo)+yd, r, g, b);
        //if( Level.getDistance(xo, yo, x, y) < renderDistance) screen.setPixel((x-xo)+xd, (y-yo)+yd, r, g, b);
    }

    public void drawPixel(int x, int y, int r, int g, int b) {
        screen.setPixel((x-xo)+xd, (y-yo)+yd, r, g, b);
        //if( Level.getDistance(xo, yo, x, y) < renderDistance) screen.setPixel((x-xo)+xd, (y-yo)+yd, r, g, b);
    }

    public void drawPixel(int x, int y, int c) {
        screen.setPixel((x-xo)+xd, (y-yo)+yd, c);
    }

    public void drawCircle(double x2, double y2, int r, int g, int b, double radius2) {
        int x = (int)Math.round(x2);
        int y = (int)Math.round(y2);
        int radius = (int)Math.round(radius2);
        
        for(int i=0; i<=90; i+=1) {
            
            double angle = ( (double) i ) / 1;
            double mult = Math.sin( Math.toRadians(angle) );
            int yAdd = (int)(mult * radius);
            mult = Math.cos( Math.toRadians(angle) );
            int xAdd = (int)(mult * radius);

            drawPixel( x+xAdd, y+yAdd, r,g,b);
            drawPixel( x-xAdd, y+yAdd, r,g,b);
            drawPixel( x+xAdd, y-yAdd, r,g,b);
            drawPixel( x-xAdd, y-yAdd, r,g,b);
        }
    }
    
    public void drawSquare(int x1, int y1, int x2, int y2, int r, int g, int b) {
        if(x1>x2 || y1>y2) return;
        
        for(int i=x1; i<=x2; i++) {
            for(int k=y1; k<=y2; k++) {
                drawPixel( i, k, r, g, b);
            }
        }
    }
    
    public void drawLine(double x1, double y1, double x2, double y2, int r, int g, int b, int count, int size) {
        //boolean backwards = x1 > x2;
        x1*=-1;
        y1*=-1;
        x2*=-1;
        y2*=-1;
        
        double y = 0;
        
        double slope;
        //if(y1!=y2) slope =(double)Math.abs(x1-x2) / (double)Math.abs(y1-y2);
        if(y1!=y2) slope =(double)Math.abs(y1-y2) / (double)Math.abs(x1-x2);
        else slope = 0;
        
        if(y1>y2) slope *=-1;
        
        double difference = Math.abs(x1 - x2);
        
        double begin;
        double end;
        
        if(difference<0) {
            begin = difference;
            end = 0;
        }
        else {
            begin = 0;
            end = difference;
        }
        
        double toAdd = end - begin;
        
        if(count<1) count = (int) Math.round( Level.getDistance( x1, y1, x2, y2) );
        
        toAdd/=(double)count;
        
        
        for(double i = begin; i<end; i+=toAdd) {
            y = i * slope;
            y*=-1;
            
            if(x1>x2) drawPixel( (x1*-1)+i, (y1*-1)+y, r, g, b);
            else drawPixel( (x1*-1)-i, (y1*-1)+y, r, g, b);
        }
    }
    
    public void drawLine(double x1, double y1, double x2, double y2, int r, int g, int b) {
        drawLine(x1, y1, x2, y2, r, g, b, 100, 1);
    }
    
    public void drawLine(double x1, double y1, double x2, double y2, int r, int g, int b, int count) {
        drawLine(x1, y1, x2, y2, r, g, b, count, 1);
    }
}