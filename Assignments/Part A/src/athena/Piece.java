package athena;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - amamra, Nam - namn1
 * For COMP30024 Part A
 */

public abstract class Piece
{
    private int xPos;
    private int yPos;

    /** directions corresponding to Left, Right, Up, Down respectively */
    private boolean[] direction = {false, false, false, false};

    private int numLegalMoves;

    public Piece(int x, int y)
    {
        this.xPos = x;
        this.yPos = y;
    }

    public int getXPos() { return xPos; }

    public int getYPos() { return yPos; }

    public void setCoordinates(int x, int y){
        this.xPos = x;
        this.yPos = y;
    }

    /**
     * Getters and setters for the different directions
     */
    public void setLeftFalse () { direction[0] = false; }
    public void setRightFalse () { direction[1] = false; }
    public void setDownFalse () { direction[3] = false; }
    public void setUpFalse () { direction[2] = false; }

    public void setLeftTrue () { direction[0] = true; }
    public void setRightTrue () { direction[1] = true; }
    public void setDownTrue () { direction[3] = true; }
    public void setUpTrue () { direction[2] = true; }

    public int getNumLegalMoves () { return this.numLegalMoves; }

    public boolean[] getDirection() {
        return direction;
    }

    /**
     * Sets the legal moves for the piece by checking the four
     * directions around the piece and incrementing count accordingly
     */
    public void setLegalMoves ()
    {
        int count = 0;
        for (int i = 0; i < 4; i++)
        {
            if (this.direction[i])
            {
                count++;
            }
        }
        this.numLegalMoves = count;
    }
}