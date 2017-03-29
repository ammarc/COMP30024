/**
 * Created by ammar on 3/15/17.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class Game
{
    public static void main (String args[]) throws FileNotFoundException
    {
        //Switch to System.in
        Scanner in = new Scanner (new File("src/input1.txt"));
        int n = in.nextInt();
        in.nextLine();

        ArrayList<String[]> boardArray = new ArrayList<String[]>();
        for (int i = 0; i < n; i++)
        {
            String nextString = in.nextLine();
            String[] boardRow = nextString.split("\\s+");
            boardArray.add(i, boardRow);
        }
        in.close();

        Board board = new Board(boardArray);
        board.setN(n);
        board.setUpBoard();
//        board.printBoard();

        System.out.println("Number Legal H Moves: " + board.numLegalHMoves());
        System.out.println("Number Legal V Moves: " + board.numLegalVMoves());
    }
}

