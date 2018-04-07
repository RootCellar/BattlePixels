public class PixelBar
{
    double percent = 1;
    double outOf=0;
    double has=0;
    int width = 30;
    int height = 10;
    int x = -100;
    int y = -100;
    int rc = 0;
    int gc = 255;
    int bc = 0;
    public void render(Game g) {
        double howMuch = (percent/100) * ((double)width);
        int howMuch2 = (int)howMuch;
        for(int i=0; i<width; i++) {
            for(int k=0; k<height; k++) {
                //if(i>howMuch2) g.drawPixel(x+i, y+k, 255-rc, 255-gc, 255-bc);
                if(i>howMuch2) g.drawPixel(x+i, y+k, 255, 0, 0);
                else g.drawPixel(x+i, y+k, rc, gc, bc);
            }
        }
    }
    
    public void calcPercent() {
        try{  
            percent = ( has / outOf ) * 100;
        }catch(Exception e) {
            
        }
    }
}