package com.namnammar.agents; /**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - ammara, Nam - namn1
 * For COMP30024 Part B
 */

import java.util.Scanner;
import java.util.ArrayList;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;
import com.namnammar.components.Board;
import com.namnammar.components.Piece;
import com.namnammar.components.Player;

public class Prometheus implements SliderPlayer {
    private Board board;
    private int dim;
    private Player myPlayer;

    @Override
    public void init(int dimension, String board, char player) {
        this.dim = dimension;
        loadBoard(board);
        this.myPlayer = new Player(this.board.getHorizontalPieces(),
                this.board.getVerticalPieces(), player);
    }

    @Override
    public void update(Move move)
    {
        if (move != null)
        {
            int newRow = 0;
            int newCol = 0;
            int row = move.j;
            int col = move.i;

            switch (move.d)
            {
                case UP:
                    newRow = row - 1;

                case DOWN:
                    newRow = row + 1;

                case LEFT:
                    newCol = col - 1;

                case RIGHT:
                    newCol = col + 1;
            }

            board.updateBoard(row, col, newRow, newCol);
        }
    }

    @Override
    public Move move()
    {
        if (this.myPlayer.getType() == 'H')
        {
            for (Piece p : board.getHorizontalPieces())
                for (Boolean b : p.getDirection())
                    if (b && p.getDirection()[1])
                        return new Move(p.getYPos(), p.getXPos(), Move.Direction.RIGHT);
        }

        for (Piece p : board.getVerticalPieces())
            for (Boolean b : p.getDirection())
                if (b && p.getDirection()[2])
                    return new Move(p.getYPos(), p.getXPos(), Move.Direction.UP);

        return null;
    }

    private void loadBoard(String board)
    {

        Scanner in = new Scanner (board);
        /** An array of strings to store the board */
        ArrayList<String[]> boardArray = new ArrayList<>(dim);
        initializeArrayList(boardArray, dim);

        /** Removing the spaces and adding to our board */
        for (int i = dim - 1; i >= 0; i--)
        {
            String nextString = in.nextLine();
            String[] boardRow = nextString.split("\\s+");
            boardArray.set(i, boardRow);
        }

        in.close();
        this.board = new Board(boardArray);
        this.board.setN(dim);
        this.board.setUpPieces();
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