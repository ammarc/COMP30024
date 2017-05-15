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

import java.util.*;


public class Athena implements SliderPlayer
{
    private Board board;

    private static int numNodes = 0;
    private static double currentBoardScore;

    @Override
    public void init(int dimension, String board, char player)
    {
        this.board = new Board(board, dimension, player);
        currentBoardScore = boardUtility(this.board);
    }

    @Override
    public void update(Move move)
    {
        if (move != null)
        {
            //System.err.println("The other player made :" + move);
            this.board.updateBoard(move);
            currentBoardScore = boardUtility(board);
            //System.err.println("FROM THE OTHER GUY\n" + board);
        }
    }

    @Override
    public Move move()
    {
        // Setting different depths for different board sizes
        int depth;
        if (board.getN() < 6)
            depth = 10;
        else if (board.getN() == 6)
            depth = 7;
        else if (board.getN() == 7)
            depth = 7;
        else
            depth = 6;

        Move mv = alphaBeta(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (mv != null)
        {
            this.board.updateBoard(mv);
            currentBoardScore = boardUtility(board);
        }

        //TODO: Remove these:
        System.out.println("Number of nodes made: " + numNodes);
        System.out.println("Lateral moves made: " + board.getPlayer().getNumLateralMoves());
        board.getPlayer().setNumLateralMoves(0);
        return mv;
    }


    /**
     * Here Alpha-Beta pruning is applied to get the next move
     *
     * @return the next move the agent should make
     */
    protected Move alphaBeta(Board board, int depth, double alpha, double beta)
    {
        double v;
        double maxValue = Integer.MIN_VALUE;
        Move maxMove = null;
        ArrayList<Move> legalMoves = new ArrayList<>();
        legalMoves.addAll(board.getPlayer().getLegalMoves());

        // Decreasing the depth if we are near the end of the game
        if (board.getVerticalPieces().size() < 3 || board.getHorizontalPieces().size() < 3)
            depth /= 2;

        Iterator<Move> iterator = legalMoves.iterator();

        while (iterator.hasNext())
        {
            Move move = iterator.next();
            numNodes += 1;

            board.updateBoard(move);

            v = minValue(board, alpha, beta, depth - 1);
            if (v > maxValue)
            {
                maxValue = v;
                maxMove = new Move(move.i, move.j, move.d);
            }
            board.undoMove(move, board.getPlayer().getType());
            alpha = Math.max(alpha, v);
            if (beta <= alpha)
                break;
        }
        return maxMove;
    }

    protected double maxValue(Board board, double alpha, double beta, int depth)
    {
        if (board.isTerminal() || depth == 0)
            return calculateUtilityDiff(board);

        double v = Integer.MIN_VALUE;

        ArrayList<Move> legalMoves = new ArrayList<>();
        legalMoves.addAll(board.getPlayer().getLegalMoves());
        //Board newBoard;
        Iterator<Move> iterator = legalMoves.iterator();

        while (iterator.hasNext())
        {
            numNodes += 1;
            Move move = iterator.next();
            board.updateBoard(move);
            v = Math.max(v, minValue(board, alpha, beta, depth - 1));
            alpha = Math.max(alpha, v);
            board.undoMove(move, board.getPlayer().getType());
            if (beta <= alpha)
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

        while (iterator.hasNext())
        {
            numNodes += 1;
            Move move = iterator.next();
            board.updateBoard(move);
            v = Math.min(v, maxValue(board, alpha, beta, depth - 1));
            beta = Math.min(beta, v);
            board.undoMove(move, board.getOtherPlayer().getType());
            if (beta <= alpha)
                break;
        }
        return v;
    }

    public double boardUtility(Board board)
    {
        double score = 1;
        if (board.getPlayer().getType() == 'H')
        {
            for (Horizontal h : board.getHorizontalPieces())
            {
                score += h.getXPos() * 10;
            }
            score += (board.getN() - board.getHorizontalPieces().size() - 1) * 10 * board.getN() - 1;
            for (int i = 0; i < (board.getN() - board.getHorizontalPieces().size() - 1); i++)
                score += score * 200;
        }
        else if (board.getPlayer().getType() == 'V')
        {
            for (Vertical v : board.getVerticalPieces())
            {
                score += v.getYPos() * 10;
            }
            score += (board.getN() - board.getVerticalPieces().size() - 1) * 10 * board.getN() - 1;
            for (int i = 0; i < (board.getN() - board.getVerticalPieces().size() - 1); i++)
                score += score * 200;
        }

        // Taxing lateral moves
        score += board.getPlayer().getNumLateralMoves() * (-5000);
        // Reward states where the other player is being blocked
        score += board.getNumBlocked() * 500;

        return score;
    }

    public double calculateUtilityDiff(Board newBoard) { return boardUtility(newBoard) - currentBoardScore; }
}
