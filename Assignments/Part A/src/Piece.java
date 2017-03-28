/**
 * Nam Nguyen
 * 15/03/2017
 *
 * Player abstract class
 *
 */
public abstract class Piece
{
    private int x_pos;
    private int y_pos;

    //direction corresponding to Left, Right, Up, Down
    private boolean[] direction = {false, false, false, false};

    private int numLegalMoves = 0;

    public Piece(int x, int y)
    {
        this.x_pos = x;
        this.y_pos = y;
    }

    public int getX_pos() {
        return x_pos;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public int getY_pos()
    {
        return y_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public void setLeftFalse() { direction[0] = false;}
    public void setRightFalse () { direction[1] = false; }
    public void setDownFalse () { direction[3] = false; }
    public void setUpFalse () { direction[2] = false; }

    public void setLeftTrue() { direction[0] = true;}
    public void setRightTrue () { direction[1] = true; }
    public void setDownTrue () { direction[3] = true; }
    public void setUpTrue () { direction[2] = true;}

    public void setDirection(int index, boolean bool){
        direction[index] = bool;
    }

    public abstract void move();

    public abstract int numLegalMoves (Board board);
}
