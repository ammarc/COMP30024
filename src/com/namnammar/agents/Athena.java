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
        int depth = 8;
        Move mv = alphaBeta(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (mv != null)
            this.board.updateBoard(mv);
        board.getPlayer().setNumLateralMoves(0);
        return mv;
    }


    /**
     * Here Alpha-Beta pruning is applied to get the next move
     * @return the next move the agent should make
     */
    protected Move alphaBeta (Board board, int depth, double alpha, double beta)
    {
        System.err.println("Starting alpha beta");
        double v;
        double maxValue = Integer.MIN_VALUE;
        Move maxMove = null;
        ArrayList<Move> legalMoves = new ArrayList<>();

        legalMoves.addAll(board.getPlayer().getLegalMoves());
        Board newBoard;

        for (Move move : legalMoves)
        {
            System.err.println("Going through moves in alpha beta");
            newBoard = new Board(board);
            newBoard.updateBoard(move);
            //board.updateBoard(move);
            v = minValue(newBoard, alpha, beta, depth - 1);
            //v = minValue(board, alpha, beta, depth - 1);
            if (v > maxValue)
            {
                maxValue = v;
                maxMove = new Move(move.i, move.j, move.d);
            }
            //board.undoMove(move, board.getPlayer().getType());
            alpha = Math.max(alpha, v);
            if (beta <= alpha)
                break;
        }
        System.err.println("-------Ending Alpha Beta---------");

        return maxMove;
    }

    protected double maxValue(Board board, double alpha, double beta, int depth)
    {
        if (board.isTerminal() || depth == 0)
            return calculateUtilityDiff(board);

        double v = Integer.MIN_VALUE;

        ArrayList<Move> legalMoves = new ArrayList<>();
        legalMoves.addAll(board.getPlayer().getLegalMoves());

        Iterator<Move> iterator = legalMoves.iterator();

        Board newBoard;

        while (iterator.hasNext())
        {
            newBoard = new Board(board);
            Move move = iterator.next();

            newBoard.updateBoard(move);
            //board.updateBoard(move);
            v = Math.max(v, minValue(newBoard, alpha, beta, depth - 1));
            //v = Math.max(v, minValue(board, alpha, beta, depth - 1));
            alpha = Math.max(alpha, v);
            //board.undoMove(move, board.getPlayer().getType());
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
        legalMoves.addAll(board.getOtherPlayer().getLegalMoves());

        Iterator<Move> iterator = legalMoves.iterator();
        Board newBoard;

        while (iterator.hasNext())
        {
            newBoard = new Board(board);
            Move move = iterator.next();
            newBoard.updateBoard(move);
            //board.updateBoard(move);
            v = Math.min(v, maxValue(newBoard, alpha, beta, depth - 1));
            //v = Math.min(v, maxValue(board, alpha, beta, depth - 1));
            beta = Math.min(beta, v);
            //board.undoMove(move, board.getOtherPlayer().getType());
            if(beta <= alpha)
                break;
        }
        return v;
    }

   // TODO: this is just a rudimentary utility function, make a real one
    public double boardUtility(Board board) {
        double score = 1;
        if(board.getPlayer().getType() == 'H') {
            for(Horizontal h : board.getHorizontalPieces())
            {
                score += h.getXPos() * 10;
            }
            score += (board.getN() - board.getHorizontalPieces().size() - 1) * 10 * board.getN() - 1;
            for (int i = 0; i < (board.getN() - board.getHorizontalPieces().size() - 1); i++)
                score += score * 200;
        }
        else if(board.getPlayer().getType() == 'V') {
            for(Vertical v : board.getVerticalPieces()) {
                score += v.getYPos() * 10;
            }
            score += (board.getN() - board.getVerticalPieces().size() - 1) * 10 * board.getN() - 1;
            for (int i = 0; i < (board.getN() - board.getVerticalPieces().size() - 1); i++)
                score += score * 200;
        }

        // Taxing lateral moves
        score += board.getPlayer().getNumLateralMoves() * (-300);
        // Reward states where the other player is being blocked
        score += board.getNumBlocked() * 50;
        return score;
    }

    public double calculateUtilityDiff(Board newBoard)
    {
        return boardUtility(newBoard) - boardUtility(this.board);
    }

}