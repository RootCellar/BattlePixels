public class HeavyTank extends Tank
{
    public HeavyTank() {
        super();
        maxReload = 200;
        maxHp = 5000;
        hp = maxHp;
        speed = 1;
        regen = 5;
        dir = 1;
    }
    
    public void render() {
        super.render();
        for(int i=-4; i<5; i++) {
            game.drawPixel(x-i, y+i, 0, 0, 0);
        }
    }
}