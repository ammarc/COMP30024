/**
 * Nam Nguyen
 * 15/03/2017
 *
 * Player abstract class
 *
 */
public abstract class Piece
{
    private float x_pos;
    private float y_pos;

    private int numLegalMoves = 0;

    public Piece(float x, float y)
    {
        this.x_pos = x;
        this.y_pos = y;
    }

    public float getX_pos() {
        return x_pos;
    }

    public void setX_pos(float x_pos) {
        this.x_pos = x_pos;
    }

    public float getY_pos()
    {
        return y_pos;
    }

    public void setY_pos(float y_pos) {
        this.y_pos = y_pos;
    }

    public abstract void move();

    public void setDir (Board curState)
    {
        //change booleans
    }

    public void updateNumLegalMoves ()
    {

    }
}
