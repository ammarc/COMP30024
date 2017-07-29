package com.namnammar.agents;
/**
 * Made by Ammar Ahmed and Nam Nguyen
 * For COMP30024 Part B
 */


import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;
import com.namnammar.components.Board;
import com.namnammar.components.Piece;

public class Simple implements SliderPlayer
{
    private Board board;

    /**
     * @param dimension The width and height of the board in cells
     * @param board A string representation of the initial state of the board,
     * as described in the part B specification
     * @param player 'H' or 'V', corresponding to which pieces the player will
     */
    public void init(int dimension, String board, char player)
    {
        this.board = new Board(board, dimension, player);
    }

    /**
     * @param move A Move object representing the previous move made by the
     * opponent, which may be null (indicating a pass). Also, before the first
     */
    public void update(Move move)
    {
        if (move != null)
        {
            int row = move.j;
            int col = move.i;
            this.board.updateBoard(move);
        }
    }

    /**
     * This simple agent tries the right/up moves first, before trying
     * the ones available to it
     * @return the next move to be made
     */
    public Move move() {

        if (board.getPlayer().getType() == 'H')
        {
            for (Piece p : board.getHorizontalPieces())
            {
                for (Boolean b : p.getDirection())
                {
                    System.err.println("Returning a move for horizontal "+b);
                    if (b && p.getDirection()[1])
                    {
                        Move m = new Move(p.getXPos(), p.getYPos(), Move.Direction.RIGHT);
                        this.update(m);
                        return m;
                    }
                    else if (b && p.getDirection()[2])
                    {
                        Move m = new Move(p.getXPos(), p.getYPos(), Move.Direction.UP);
                        this.update(m);
                        return m;
                    }
                    else if (b && p.getDirection()[3])
                    {
                        Move m = new Move(p.getXPos(), p.getYPos(), Move.Direction.DOWN);
                        this.update(m);
                        return m;
                    }
                }
            }
            return null;
        }

        else if (board.getPlayer().getType() == 'V')
        {
            for (Piece p : board.getVerticalPieces())
            {
                for (Boolean b : p.getDirection())
                {
                    if (b && p.getDirection()[2])
                    {
                        Move m = new Move(p.getXPos(), p.getYPos(), Move.Direction.UP);
                        this.update(m);
                        return m;
                    }
                    else if (b && p.getDirection()[1])
                    {
                        Move m = new Move(p.getXPos(), p.getYPos(), Move.Direction.RIGHT);
                        this.update(m);
                        return m;
                    }
                    else if (b && p.getDirection()[0])
                    {
                        Move m = new Move(p.getXPos(), p.getYPos(), Move.Direction.LEFT);
                        this.update(m);
                        return m;
                    }
                }
            }
        }
        return null;
    }
}
