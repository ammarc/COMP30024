/**
 * Created by Nam Nguyen on 15/03/2017.
 */
public class Vertical extends Piece
{

    public final boolean DOWN = false;
    private boolean RIGHT = false;
    private boolean LEFT = false;
    private boolean UP = false;

    public Vertical(float x, float y)
    {
        super(x,y);
    }

    @Override
    public void move()
    {
        //do something
    }

    public void setRightFalse () { this.RIGHT = false; }
    public void setLeftFalse () { this.LEFT = false; }
    public void setUpFalse () { this.UP = false; }

    public void setRightTrue () { this.RIGHT = true; }
    public void setLeftTrue () { this.LEFT = true; }
    public void setUpTrue () { this.UP = true; }
}
