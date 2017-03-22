/**
 * Created by Nam Nguyen on 15/03/2017.
 */
public class Obstacle {
    private float x_pos;
    private float y_pos;

    public Obstacle(){
        this.x_pos = 0;
        this.y_pos = 0;
    }

    public Obstacle(float x, float y){
        this.x_pos = x;
        this.y_pos = y;
    }

    public float getX_pos() {
        return x_pos;
    }

    public void setX_pos(float x_pos) {
        this.x_pos = x_pos;
    }

    public float getY_pos() {
        return y_pos;
    }

    public void setY_pos(float y_pos) {
        this.y_pos = y_pos;
    }

}
