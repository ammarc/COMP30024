package athena;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - amamra, Nam - namn1
 * For COMP30024 Part A
 */
import java.awt.*;
import java.util.ArrayList;

public abstract class Piece
{
    private Point coordinate;
    public enum Direction { UP, DOWN, LEFT, RIGHT };

    public ArrayList<Direction> getDirection() {
        return direction;
    }

    /** directions corresponding to Left, Right, Up, Down respectively */
    private ArrayList<Direction> direction;

    private int numLegalMoves;

    public Piece(int x, int y)
    {
        this.coordinate = new Point(x, y);
    }

    public int getXPos() { return (int)coordinate.getX(); }

    public int getYPos() { return (int)coordinate.getY(); }

    public void setCoordinates(int x, int y){
        this.coordinate = new Point(x,y);
    }

    /**
     * Getters and setters for the different directions
     */
    public void resetDir(){
        this.direction = new ArrayList<>();
    }

    public void addDir(Direction d){
        this.direction.add(d);
    }

    public int getNumLegalMoves () { return this.numLegalMoves; }

    /**
     * Sets the legal moves for the piece by checking the four
     * directions around the piece and incrementing count accordingly
     */
    public void setLegalMoves ()
    {
        this.numLegalMoves = this.direction.size();
    }
}