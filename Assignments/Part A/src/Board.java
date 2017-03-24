/**
 * Created by ammar on 3/15/17.
 */

import jdk.internal.dynalink.ChainedCallSite;

import java.util.Scanner;

public class Board
{
    private int n;
    private Object[][] board;

    public void setN(int n)
    {
        this.n = n;
    }

    public int getN() {
        return this.n;
    }

    public Object[][] getBoard() {
        return this.board;
    }

    public Board ()
    {

        Scanner in = new Scanner(System.in);

        this.n = in.nextInt();

        in.nextLine();

        this.board = new Object[this.n][this.n];

        for (int i = this.n - 1; i >= 0; i--)
        {


            // rawInput = rawInput.replaceAll("\\s", "");

            for (int j = 0; j < this.n; j++)
            {
                String rawInput = in.next();

                switch (rawInput)
                {
                    case "H":
                        board[i][j] = new Horizontal(i, j);
                        break;
                    case "V":
                        board[i][j] = new Vertical(i, j);
                        break;
                    case "+":
                        board[i][j] = new String (rawInput);
                        break;
                    case "B":
                        board[i][j] = new Obstacle(i, j);
                        break;
                }
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
            for (int j = 0; j < this.n; j++)
            {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}

