package com.namnammar.agents; /**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - ammara, Nam - namn1
 * For COMP30024 Part B
 */

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;
import com.namnammar.components.Board;
import com.namnammar.components.Horizontal;
import com.namnammar.components.Vertical;

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
            this.board.updateBoard(move);
            System.err.println("FROM THE OTHER GUY\n" + board);
        }
    }

    @Override
    public Move move()
    {
        int depth = 7;
        Move mv = alphaBeta(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.board.updateBoard(mv);
        System.err.println(board);
        return mv;
    }


    /**
     * Here Alpha-Beta pruning is applied to get the next move
     * @return the next move the agent should make
     */
    protected Move alphaBeta (Board board, int depth, int alpha, int beta)
    {
        double v = maxValue(board, alpha, beta, depth);

        // finding the move with the given value

        for (Move m : board.getPlayer().getLegalMoves().keySet()) {

            if (board.getPlayer().getLegalMoves().get(m) == v)
                return m;
        }

        System.err.println("Error: cannot find the next move to make");
        return null;
    }

    protected double maxValue(Board board, double alpha, double beta, int depth)
    {
        if (board.isTerminal() || depth == 0)
            return calculateUtilityDiff(board);

        double v = Integer.MIN_VALUE;

        ArrayList<Move> legalMoves = new ArrayList<>();

        for (Move m : board.getPlayer().getLegalMoves().keySet())
            legalMoves.add(m);

        Iterator<Move> iterator = legalMoves.iterator();

        while (iterator.hasNext())
        {
            Move move = iterator.next();
            Board newBoard = new Board(board);
            newBoard.updateBoard(move);
            v = Math.max(v, minValue(newBoard, alpha, beta, depth - 1));
            board.getPlayer().setMoveValue(move, v);
            alpha = Math.max(alpha, v);

            if(beta <= alpha)
                break;
        }
        return v;
    }

    protected double minValue(Board board, double alpha, double beta, int depth)
    {
        if (board.isTerminal() || depth == 0)
            return calculateUtilityDiff(board);
        double v = Integer.MAX_VALUE;
        ArrayList<Move> legalMoves = new ArrayList<>();

        for (Move m : board.getOtherPlayer().getLegalMoves().keySet())
            legalMoves.add(m);

        Iterator<Move> iterator = legalMoves.iterator();

        while (iterator.hasNext())
        {
            Move move = iterator.next();
            Board newBoard = new Board(board);
            newBoard.updateBoard(move);
            v = Math.min(v, maxValue(newBoard, alpha, beta, depth - 1));
            beta = Math.min(beta, v);

            if(beta <= alpha)
                break;
        }
        return v;
    }

   // TODO: this is just a rudimentary utility function, make a real one
    public double boardUtility(Board board)  {
        int score = 0;
        if(board.getPlayer().getType() == 'H') {
            for(Horizontal h : board.getHorizontalPieces())
            {
                score += h.getXPos();
            }
            score *= board.getN() - board.getHorizontalPieces().size();
        }
        else if(board.getPlayer().getType() == 'V') {
            for(Vertical v : board.getVerticalPieces()) {
                score += v.getYPos();
            }
            score *= board.getN() - board.getVerticalPieces().size();
        }

        return score;
    }

    public double calculateUtilityDiff(Board newBoard)
    {
        return boardUtility(newBoard) - boardUtility(this.board);
    }

}