/**
 * Created by ammar on 3/15/17.
 */

import java.util.ArrayList;

public class Board
{
    private int n;
    private String[] board;
    private ArrayList<Piece> horizontalPieces = new ArrayList<>();
    private ArrayList<Piece> verticalPieces = new ArrayList<>();

    public void setN(int n)
    {
        this.n = n;
    }

    public int getN() {
        return this.n;
    }

    public String[] getBoard() {
        return this.board;
    }

    public Board (String[] boardArray)
    {
        this.board = boardArray;
    }

    public void setUpBoard ()
    {
        for (int i = 0; i < n; i++)
        {
            int j = 0;
            for (char c : board[i].toCharArray())
            {
                if (c == 'H')
                {
                    Horizontal horizontal = new Horizontal(i, j);
                    // Look up
                    if (i < n && board[i+1].toCharArray()[j] == '+') { horizontal.setUpTrue(); }
                    // Look right
                    if (i == n || board[i].toCharArray()[j+1] == '+') { horizontal.setRightTrue(); }
                    // Look down
                    if (i >= 0 && board[i-1].toCharArray()[j] == '+') { horizontal.setDownTrue(); }
                }

                if (c == 'V')
                {
                    Vertical vertical = new Vertical(i, j);
                    // Look up
                    if (i == n || board[i+1].toCharArray()[j] == '+') { vertical.setUpTrue(); }
                    // Look right
                    if (i < n && board[i].toCharArray()[j+1] == '+') { vertical.setRightTrue(); }
                    // Look left
                    if (i >= 0 && board[i].toCharArray()[j-1] == '+') { vertical.setLeftTrue(); }
                }
                j++;
            }
        }
    }

    public void printBoard ()
    {
        System.out.println();

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The board is:");

        System.out.println();
        for (int i = 0; i < this.n; i++)
        {
            for (char c : board[i].toCharArray())
            {
                System.out.print(c);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}

