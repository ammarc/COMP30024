/**
 * Created by Nam Nguyen on 15/03/2017.
 */
public class Horizontal extends Piece
{
    public Horizontal(int x, int y)
    {
        super(x,y);
    }

    @Override
    public int numLegalMoves(Board board){
        int count = 0;
        String[] neighbors = board.getNeighborCells(this.getX_pos(), this.getY_pos());

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

    @Override
    public void move()
    {
        //do something
    }
}
