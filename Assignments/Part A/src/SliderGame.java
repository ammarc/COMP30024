/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - amamra, Nam - namn1
 * For COMP30024 Part B
 */

import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;

import athena.Board;
import athena.Player;
import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;

public class SliderGame implements SliderPlayer {
    private Board board;
    private int dim;
    private Player myPlayer;

    @Override
    public void init(int dimension, String board, char player) {
        this.dim = dimension;
        loadBoard(board);
        this.myPlayer = new Player(player);
    }

    @Override
    public void update(Move move) {
        if(move == null){
            //do nothing
        }
        else{
            int row = move.j;
            int col = move.i;
            int new_row = row , new_col = col;
            if (move.d == Move.Direction.UP){
                new_row -= 1;
            }
            if (move.d == Move.Direction.DOWN){
                new_row += 1;
            }
            if (move.d == Move.Direction.LEFT){
                new_col -= 1;
            }
            if (move.d == Move.Direction.RIGHT){
                new_col += 1;
            }

            board.updateBoard(col, row, new_col, new_row);
//
//            System.out.println("Current Board:");
//            board.printBoard();
        }
    }

    @Override
    public Move move() {
        return myPlayer.random_next(this.board);
    }

    private void loadBoard(String board){

        Scanner in = new Scanner (board);
        /** An array of strings to store the board */
        ArrayList<String[]> boardArray = new ArrayList<>(dim);
        initializeArrayList(boardArray, dim);

        /** Removing the spaces and adding to our board */
        for (int i = 0; i < dim; i++)
        {
            String nextString = in.nextLine();
            String[] boardRow = nextString.split("\\s+");
            boardArray.set(i, boardRow);
        }

        in.close();
//
//        System.out.println("initial board:");
//        for(int i = 0; i < dim; i++){
//            for (int j = 0; j < dim; j++){
//                String s = boardArray.get(i)[j];
//                System.out.print(s);
//            }
//            System.out.println();
//        }

        this.board = new Board(boardArray, dim);
    }

    /**
     * All the entries of the board are set to null first so that they can be set later
     * without errors as the input begins with the (n - 1)th row
     * @param arrayList the list to initialize
     * @param size the size of the uninitialized array list
     */
    private static void initializeArrayList (ArrayList<String[]> arrayList, int size)
    {
        for (int i = 0; i < size; i++)
        {
            arrayList.add (i, null);
        }
    }
}
