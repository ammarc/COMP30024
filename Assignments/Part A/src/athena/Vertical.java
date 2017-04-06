package athena;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam
 * User names: Ammar - amamra, Nam -
 * For COMP30024 Part A
 */

public class Vertical extends Piece
{
    /**
     * The constructor for this class
     * @param x the piece's x-coordinate
     * @param y the piece's y-coordinate
     */
    public Vertical(int x, int y)
    {
        super(x,y);
    }

    // @Override
    /*
    public int numLegalMoves(athena.Board board)
    {
        int count = 0;
        String[] neighbors = board.getNeighborCells(this.getxPos(), this.getyPos());

        //doesn't check down direction
        for(int i = 0; i < neighbors.length-1; i++)
        {
            if(neighbors[i].equals("+"))
            {
                count++;
                setDirection(i, true);
            }
            else
            {
                setDirection(i, false);
            }
        }

        // check whether the piece is one step away from the end
        if (this.getxPos() == 0) { count ++; }

        return count;
    }
    */
}