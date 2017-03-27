/**
 * Created by Nam Nguyen on 15/03/2017.
 */
public class Horizontal extends Piece
{

    public final boolean LEFT = false;
    private boolean RIGHT = false;
    private boolean DOWN = false;
    private boolean UP = false;


    public Horizontal(float x, float y)
    {
        super(x,y);

    }

    @Override
    public void move()
    {
        //do something
    }

    public void setRightFalse () { this.RIGHT = false; }
    public void setDonwFalse () { this.DOWN = false; }
    public void setUpFalse () { this.UP = false; }

    public void setRightTrue () { this.RIGHT = true; }
    public void setDownTrue () { this.DOWN = true; }
    public void setUpTrue () { this.UP = true; }
}
