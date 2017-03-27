/**
 * Created by ammar on 3/15/17.
 */
import java.util.Scanner;


public class Game
{
    public static void main (String args[])
    {
        Scanner in = new Scanner (System.in);

        int n = in.nextInt();
        in.nextLine();

        String[] boardArray = new String[n];
        for (int i = 0; i < n; i++)
        {
            String boardRow = in.nextLine();
            boardRow = boardRow.replaceAll("\\s", "");
            boardArray[i] = boardRow;
        }
        in.close();

        Board board = new Board(boardArray);
        board.setN(n);
        board.setUpBoard();
        board.printBoard();
    }
}

