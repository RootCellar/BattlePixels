public class Player extends Mob
{
    //int reloadBig = 0;
    //int reloadSmall = 0;
    //int reloadShotgun = 0;
    int kills = 0;
    
    double dx = 0;
    double dy = 0;

    InputListener input;

    public Player(InputListener in) {
        input = in;

        maxHp = 2000;
        hp = maxHp;
        regen = 1;
        team = new Team();
        speed = 2;
        size = 15;
        y = 500;
    }

    public void revive() {
        isAlive=true;
        hp=maxHp;
    }

    public void killed(Mob m) {
        kills++;
    }

    public void render() {
        for(int i=-3; i<4; i++) {
            for(int k=-3; k<4; k++) {
                game.drawPixel(x+i, y+k, 0, 0, 255);
            }
        }

        game.drawCircle(x, y, 255, 255, 255, size);
    }
    
    public void doMovementControls() {
        if(input.down.down) addY( speed );
        if(input.up.down) subY( speed );
        if(input.left.down) subX( speed );
        if(input.right.down) addX( speed );
    }

    public void tick() {
        super.tick();

        doMovementControls();
        
        bar.x=x-20;
        bar.y=y+15;
        bar.width=40;
        bar.height=10;
        
        dx = x;
        dy = y;
        
        /**
        if(input.space.down) shootSmall();
        if(input.l.down) shootBig();
        if(input.k.down) shootShotgun();

        regen = maxHp/500;

        energy++;
        if(energy>=maxEnergy) energy=maxEnergy;

        shield+= 1;
        if(shield>=maxShield) shield=maxShield;

        bar.x=x-20;
        bar.y=y+15;
        bar.width=40;
        bar.height=10;

        if(reloadBig<=1000) reloadBig++;
        reloadSmall++;
        reloadShotgun++;   
        if(reloadSmall>100) reloadSmall=100;
        if(reloadShotgun>100) reloadShotgun=100;
        */
    }
}