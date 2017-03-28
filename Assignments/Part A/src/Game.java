/**
 * Created by ammar on 3/15/17.
 */
import java.util.Scanner;
import java.util.ArrayList;


public class Game
{
    public static void main (String args[])
    {
        Scanner in = new Scanner (System.in);
        //debug
        System.out.println("Hello");
        int n = in.nextInt();
        //debug
        System.out.println("N is: " + n);
        in.nextLine();

        ArrayList<String[]> boardArray = new ArrayList<String[]>();
        for (int i = 0; i < n; i++)
        {
            String nextString = in.nextLine();
            //debug
            System.out.println("This line: " + nextString);
            String[] boardRow = nextString.split("\\s+");
            boardArray.add(i, boardRow);
        }
        in.close();

        //debug
        System.out.println("Board read");

        Board board = new Board(boardArray);
        board.setN(n);
        //debug
        System.out.println("Board created");
        board.setUpBoard();
        board.printBoard();

        System.out.println("Number Legal H Moves: " + board.numLegalHMoves());
        System.out.println("Number Legal V Moves: " + board.numLegalVMoves());
    }
}

