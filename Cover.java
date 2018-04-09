public class Cover extends Mob
{
    public Cover() {
        hp = maxHp = 20000;
    }
    
    public void tick() {
        checkHp();
        bar.x=x-10;
        bar.y=y+10;
        bar.width=20;
        bar.height=5;
        bar.has=hp;
        bar.outOf=maxHp;
        bar.calcPercent();
        
        
    }
    
    public void render() {
        for(int i=-5; i<5; i++) {
            for(int k=-5; k<6; k++) {
                game.drawPixel(x+i, y+k, team.r, team.g, team.b);
            }
        }
    }
}