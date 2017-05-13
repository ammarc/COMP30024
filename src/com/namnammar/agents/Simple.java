package com.namnammar.agents;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;
import com.namnammar.components.Board;
import com.namnammar.components.Piece;

public class Simple implements SliderPlayer
{
    private Board board;

    public void init(int dimension, String board, char player)
    {
        this.board = new Board(board, dimension, player);
    }

    public void update(Move move)
    {
        if (move != null)
        {
            int row = move.j;
            int col = move.i;
            this.board.updateBoard(move);
        }
    }

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
