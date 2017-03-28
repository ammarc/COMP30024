/**
 * Created by Nam Nguyen on 15/03/2017.
 */
public class Vertical extends Piece
{

    public Vertical(int x, int y)
    {
        super(x,y);
    }

    @Override
    public void move() {
        //do something
    }

    @Override
    public int numLegalMoves(Board board){
        int count = 0;
        String[] neighbors = board.getNeighborCells(this.getX_pos(), this.getY_pos());

        //doesn't check down direction
        for(int i = 0; i < neighbors.length - 1; i++){
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
}
