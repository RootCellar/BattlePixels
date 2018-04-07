public class EnemySoldier extends Mob
{
    int xt = 0;
    int yt = 0;
    int gotime = 0;
    int waittime = 10;
    int reload = 100;

    public void findDir() {
        xt=(int)Math.round( Math.random()*2 )-1;
        yt=(int)Math.round( Math.random()*2 )-1;
        //if(xt==0) xt=-1;
        //if(yt==0) yt=-1;
        gotime = (int)Math.round( Math.random()*100 );

    }
    
    public void shoot() {
        Projectile p = newProjectile();
        p.velocX=-10;
        p.damage = 35;
        p.size = 1;
        level.add(p);
        reload=25;
    }

    public void tick() {
        super.tick();
        regen();
        checkHp();
        if(gotime>0) {
            if(xt>0) addX(speed);
            if(yt>0) addY(speed);
            if(xt<0) subX(speed);
            if(yt<0) subY(speed);
            gotime--;
        }
        if(gotime==0) waittime--;
        if(gotime==0 && waittime==0) findDir();
        if(waittime==0) waittime=(int)Math.round( Math.random()*200 );
        reload--;
        if(reload<=0) {
            shoot();
        }

    }

    public void render() {
        /**
        game.drawPixel(x, y, 255, 0, 0);
        game.drawPixel(x+1, y, 255, 0, 0);
        game.drawPixel(x, y+1, 255, 0, 0);
        game.drawPixel(x+1, y+1, 255, 0, 0);
        */
        for(int i=-2; i<3; i++) {
            for(int k=-2; k<3; k++) {
                game.drawPixel(x+i, y+k, 255, 0, 0);
            }
        }
    }
}