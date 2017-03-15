/**
 * Created by Nam Nguyen on 15/03/2017.
 */
public class Vertical extends Player{

    private boolean LEFT = true;
    private boolean RIGHT = true;
    private boolean DOWN = false;
    public static final boolean UP = true;

    public Vertical(){
        super();
    }

    public Vertical(float x, float y){
        super(x,y);
    }

    @Override
    public void move(){
        //do something
    }
}
