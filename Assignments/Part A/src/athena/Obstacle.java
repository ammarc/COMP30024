package athena;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - amamra, Nam - namn1
 * For COMP30024 Part A
 */

public class Obstacle
{
    private float xPos;
    private float yPos;

    /**
     * The constructor for this class
     * @param x the piece's x-coordinate
     * @param y the piece's y-coordinate
     */
    public Obstacle(float x, float y)
    {
        this.xPos = x;
        this.yPos = y;
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }
}
