/**
 * Created by ammar on 3/15/17.
 */

import java.util.ArrayList;

public class Board
{
    private int n;
    private ArrayList<String[]> board;
    private ArrayList<Piece> horizontalPieces = new ArrayList<Piece>();
    private ArrayList<Piece> verticalPieces = new ArrayList<Piece>();
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

    public void setN(int n)
    {
        this.n = n;
    }

    public int getN() {
        return this.n;
    }

    public ArrayList<String[]> getBoard() {
        return this.board;
    }

    public Board (ArrayList<String[]> boardArray)
    {
        this.board = boardArray;
    }

    public void setUpBoard ()
    {
        for (int i = 0; i < n; i++)
        {
            int j = 0;
            for (String c : board.get(i))
            {
                if (c.equals('H'))
                {
                    Horizontal horizontal = new Horizontal(i, j);
                    // Look down
                    if (i < n && board.get(i+1)[j].equals('+')) { horizontal.setUpTrue(); }
                    // Look right
                    if (j < n && board.get(i)[j+1].equals('+')) { horizontal.setRightTrue(); }
                    // Look up
                    if (i >= 0 && board.get(i-1)[j].equals('+')) { horizontal.setDownTrue(); }

                    horizontalPieces.add(horizontal);
                }

                if (c.equals('V'))
                {
                    Vertical vertical = new Vertical(i, j);
                    // Look down
                    if (i == n || board.get(i+1)[j].equals('+')) { vertical.setUpTrue(); }
                    // Look right
                    if (j < n && board.get(i)[j+1].equals('+')) { vertical.setRightTrue(); }
                    // Look left
                    if (j > 0 && board.get(i)[j-1].equals('+')) { vertical.setLeftTrue(); }

                    verticalPieces.add(vertical);
                }

                if (c.equals('B'))
                {
                    Obstacle obs = new Obstacle(i,j);
                    obstacles.add(obs);
                }
                j++;
            }
        }
    }

    public String[] getNeighborCells(int x, int y){
        //get neighbor cells, in following order: Left, Right, Up, Down
        String[] neighbors = new String[4];

        if (y>0){
            neighbors[0] = board.get(x)[y-1];
        }
        else {
            neighbors[0] = "";
        }

        if (y<n){
            neighbors[1] = board.get(x)[y+1];
        }
        else {
            neighbors[1] = "";
        }

        if (x>0){
            neighbors[2] = board.get(x-1)[y];
        }
        else {
            neighbors[2] = "";
        }

        if (x<n){
            neighbors[3] = board.get(x+1)[y];
        }
        else {
            neighbors[3] = "";
        }

        return neighbors;
    }

    public void printBoard ()
    {
        System.out.println();

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The board is:");

        System.out.println();
        for (int i = 0; i < this.n; i++)
        {
            for (String c : board.get(i))
            {
                System.out.print(c);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public int numLegalHMoves(){
        int count = 0;

        for( Piece p : horizontalPieces){
            count += p.numLegalMoves(this);
        }

        return count;
    }

    public int numLegalVMoves(){
        int count = 0;

        for( Piece p : verticalPieces){
            count += p.numLegalMoves(this);
        }

        return count;
    }
}

