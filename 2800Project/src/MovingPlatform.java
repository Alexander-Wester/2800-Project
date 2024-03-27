import java.awt.*;

public class MovingPlatform extends Enemy{
    private int lower;
    private int upper;
    private int speed;
    private boolean movingLower;
    private boolean movingUpper;
    private boolean upDown;
    public MovingPlatform(int x, int y, int width, int height, int lower, int upper, int speed, boolean upDown){
        super(x, y, width, height,0);
        hitBox = new Rectangle(x,y,width,height);
        isInvincible = true;
        isAlive = false;
        this.lower = lower;
        this.upper = upper;
        movingLower = false;
        movingUpper = true;
        this.speed = speed;
        this.upDown = upDown;
    }
    public void tick(GameManager gm){
        if(!upDown) {// If upDown is false the platform is moving on x-Axis
            if (movingUpper) {
                if (hitBox.getX() <= upper) {
                    int x = (int) (hitBox.getX() + speed);
                    hitBox.setLocation(x, (int) hitBox.getY());
                } else if (hitBox.getX() >= upper) {
                    movingUpper = false;
                    movingLower = true;
                }
            } else if (movingLower) {
                if (hitBox.getX() >= lower) {
                    int x = (int) (hitBox.getX() - speed);
                    hitBox.setLocation(x, (int) hitBox.getY());
                } else if (hitBox.getX() <= lower) {
                    movingLower = false;
                    movingUpper = true;
                }
            }
        } else{// Moves on y-axis if upDown is true
            if(movingUpper){ // Upper lower is kind of reversed for up down since a higher number Y is lower on the screen
                if(hitBox.getY() <= upper){
                    int y = (int)(hitBox.getY() + speed);
                    hitBox.setLocation((int)hitBox.getX(), y);
                } else if(hitBox.getY() >= lower){
                    movingUpper = false;
                    movingLower = true;
                }
            } else if (movingLower){
                if(hitBox.getY() >= lower){
                    int y = (int)(hitBox.getY() - speed);
                    hitBox.setLocation((int)hitBox.getX(), y);
                } else if (hitBox.getY() <= lower){
                    movingUpper = true;
                    movingLower = false;
                }
            }

        }
    }
}
