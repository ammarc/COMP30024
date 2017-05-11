package com.namnammar.agents; /**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - ammara, Nam - namn1
 * For COMP30024 Part B
 */

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;
import com.namnammar.components.Board;

import java.util.ArrayList;
import java.util.Iterator;


public class Athena implements SliderPlayer {
    private Board board;

    @Override
    public void init(int dimension, String board, char player)
    {
        this.board = new Board(board, dimension, player);
    }

    @Override
    public void update(Move move)
    {
        if (move != null)
        {
            System.err.println("The other player made :" + move);
            int row = move.j;
            int col = move.i;
            this.board.updateBoard(row, col, move);
            System.err.println("FROM THE OTHER GUY\n" + board);
        }
    }

    @Override
    public Move move()
    {
        /*
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
                }
            }
        }
        return null;
        */
        int depth = 1;
        Move mv = alphaBeta(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.board.updateBoard(mv.j, mv.i, mv);
        System.err.println(board);
        return mv;
    }


    /**
     * Here Alpha-Beta pruning is applied to get the next move
     * @return the next move the agent should make
     */
    protected static Move alphaBeta (Board board, int depth, int alpha, int beta)
    {
        int v = maxValue(board, alpha, beta, depth);

        // finding the move with the given value

        for (Move m : board.getPlayer().getLegalMoves().keySet()) {

            if (board.getPlayer().getLegalMoves().get(m) == v)
                return m;
        }

        System.err.println("Error: cannot find the next move to make");
        return null;
    }

    protected static int maxValue(Board board, int alpha, int beta, int depth)
    {
        if (board.isTerminal() || depth == 0)
            return board.utility();

        int v = Integer.MIN_VALUE;

        int count = 0;

        ArrayList<Move> legalMoves = new ArrayList<>();

        for (Move m : board.getPlayer().getLegalMoves().keySet())
            legalMoves.add(m);

        Iterator<Move> iterator = legalMoves.iterator();

        while (iterator.hasNext())
        {
            Move move = iterator.next();
            System.err.println("Count is "+count);
            Board newBoard = new Board(board);
            newBoard.updateBoard(move.j, move.i, move);
            v = Integer.max(v, minValue(newBoard, alpha, beta, depth - 1));
            board.getPlayer().setMoveValue(move, v);
            alpha = Integer.max(alpha, v);

            count++;

            if(beta <= alpha)
                break;
        }
        return v;
    }

    protected static int minValue(Board board, int alpha, int beta, int depth)
    {
        if (board.isTerminal() || depth == 0)
            return board.utility();
        System.err.println("Fuck off ");
        int v = Integer.MAX_VALUE;
        ArrayList<Move> legalMoves = new ArrayList<>();

        for (Move m : board.getOtherPlayer().getLegalMoves().keySet())
            legalMoves.add(m);

        Iterator<Move> iterator = legalMoves.iterator();

        while (iterator.hasNext())
        {
            Move move = iterator.next();
            Board newBoard = new Board(board);
            newBoard.updateBoard(move.j, move.i, move);
            v = Integer.min(v, maxValue(newBoard, alpha, beta, depth - 1));
            beta = Integer.max(beta, v);

            if(beta <= alpha)
                break;
        }
        return v;
    }
}