package com.namnammar.agents; /**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - ammara, Nam - namn1
 * For COMP30024 Part B
 */

import java.sql.SQLSyntaxErrorException;
import java.util.Scanner;
import java.util.ArrayList;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;
import com.namnammar.components.Board;
import com.namnammar.components.Piece;
import com.namnammar.components.Player;

public class Athena implements SliderPlayer {
    private Board board;
    private int dimension;
    private Player myPlayer;

    @Override
    public void init(int dimension, String board, char player) {
        this.dimension = dimension;
        loadBoard(board);
        this.myPlayer = new Player(this.board.getHorizontalPieces(),
                this.board.getVerticalPieces(), player);
    }

    @Override
    public void update(Move move)
    {
        if (move != null)
        {
            System.err.println("Now updating board for: "+ myPlayer.getType());
            int newRow = move.j;
            int newCol = move.i;
            int row = move.j;
            int col = move.i;

            switch (move.d)
            {
                case UP:
                    newRow++;
                    break;

                case DOWN:
                    // System.err.println("DOWN");
                    newRow--;
                    break;

                case LEFT:
                    newCol--;
                    break;

                case RIGHT:
                    // System.err.println("RIGHT");
                    newCol++;
                    break;
            }

            board.updateBoard(row, col, newRow, newCol);
        }
    }

    @Override
    public Move move()
    {
        System.err.println("Selecting a move now");
        if (this.myPlayer.getType() == 'H')
        {
            for (Piece p : board.getHorizontalPieces())
                for (Boolean b : p.getDirection())
                    if (b && p.getDirection()[1])
                    {
                        Move m = new Move(p.getXPos(), p.getYPos(), Move.Direction.RIGHT);
                        this.update(m);
                        return m;
                    }
            return null;
        }

        for (Piece p : board.getVerticalPieces())
            for (Boolean b : p.getDirection())
                if (b && p.getDirection()[2])
                {
                    System.err.println("Move "+p.getXPos()+" "+p.getYPos());
                    Move m = new Move(p.getXPos(), p.getYPos(), Move.Direction.UP);
                    this.update(m);
                    return m;
                }

        return null;
    }

    private void loadBoard(String board)
    {

        Scanner in = new Scanner (board);
        /** An array of strings to store the board */
        ArrayList<String[]> boardArray = new ArrayList<>(dimension);
        initializeArrayList(boardArray, dimension);

        /** Removing the spaces and adding to our board */
        for (int i = dimension - 1; i >= 0; i--)
        {
            String nextString = in.nextLine();
            String[] boardRow = nextString.split("\\s+");
            boardArray.set(i, boardRow);
        }

        in.close();
        this.board = new Board(boardArray);
        this.board.setN(dimension);
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
            arrayList.add (i, null);
    }
}