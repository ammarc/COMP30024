/**
 * Created by ammar on 3/15/17.
 */
public abstract class Player
{
    private float x_pos;
	private float y_pos;
	
	public Player(float x, float y){
		x_pos = x;
		y_pos = y;
	}
	
	public abstract move();
}
