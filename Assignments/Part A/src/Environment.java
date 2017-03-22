/**
 * Created by ammar on 3/15/17.
 */

import java.util.Scanner;

public class Environment
{
    private int n;
    private int[][] board;

    public void setN (int n ) { this.n = n; }

    public int getN ()
    {
        return this.n;
    }

    public int[][] getBoard ()
    {
        return this.board;
    }

    public void setBoard(int[][] inBoard)
    {
        Scanner in = new Scanner (System.in);

        board = new int[this.n][this.n];

        for (int i = 0; i < this.n; i++)
        {
            for (int j = 0; j < this.n; j++)
            {
                board[i][j] = inBoard[i][j];
            }
        }
    }


    public Environment ()
    {
        Scanner in = new Scanner(System.in);

        setN (in.nextInt());

        int[][] inBoard = new int[n][n];



        setBoard(inBoard);
    }
}
