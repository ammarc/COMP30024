package athena;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam
 * For COMP30024 Part A
 */

public class Horizontal extends Piece
{
    /**
     * The constructor for this class
     * @param x the piece's x-coordinate
     * @param y the piece's y-coordinate
     */
    public Horizontal(int x, int y)
    {
        super(x,y);
    }

    /*@Override
    public int numLegalMoves(athena.Board board){
        int count = 0;
        String[] neighbors = board.getNeighborCells(this.getxPos(), this.getyPos());

        //doesn't check left direction
        for(int i = 1; i < neighbors.length; i++){
            if(neighbors[i].equals("+")){
                count++;
                setDirection(i, true);
            }
            else {
                setDirection(i, false);
            }
        }

        return count;
    }
    */
}