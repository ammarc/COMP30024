/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - amamra, Nam - namn1
 * For COMP30024 Part A
 */


import com.namnammar.components.Board;

import java.util.Scanner;
import java.util.ArrayList;


public class Game
{

    /**
     *  This is the main method used to take the raw input and set up the board
     */
    public static void main (String args[])
    {
        Scanner in = new Scanner (System.in);
        int n = in.nextInt();
        in.nextLine();

        /** An array of strings to store the board */
        ArrayList<String[]> boardArray = new ArrayList<>(n);
        initializeArrayList(boardArray, n);

        /** Removing the spaces and adding to our board */
        for (int i = n - 1; i >= 0; i--)
        {
            String nextString = in.nextLine();
            String[] boardRow = nextString.split("\\s+");
            boardArray.set(i, boardRow);
        }

        in.close();

        // Board board = new Board(boardArray);

        // System.out.println(board.numLegalHMoves());
        // System.out.println(board.numLegalVMoves());
    }

    /**
     * All the entries of the board are set to null first so that they can be set later
     * without errors as the input begins with the (n - 1)th row
     * @param arrayList the list to initialize
     * @param size the size of the uninitialized array list
     */
    public static void initializeArrayList (ArrayList<String[]> arrayList, int size)
    {
        for (int i = 0; i < size; i++)
        {
            arrayList.add (i, null);
        }
    }
}
