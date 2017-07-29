package com.namnammar.agents;
/**
 * Made by Ammar Ahmed and Nam Nguyen
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

    private static double currentBoardScore;

    /**
     *  Our implementation of the init method, where we keep track of the
     *  current score and create a new board for the agent
     * @param dimension The width and height of the board in cells
     * @param board A string representation of the initial state of the board,
     * as described in the part B specification
     * @param player 'H' or 'V', corresponding to which pieces the player will
     */
    @Override
    public void init(int dimension, String board, char player)
    {
        this.board = new Board(board, dimension, player);
        currentBoardScore = boardUtility(this.board);
    }

    /**
     * The board and the current score of the board is updated for our agent
     * @param move A Move object representing the previous move made by the
     * opponent, which may be null (indicating a pass). Also, before the first
     */
    @Override
    public void update(Move move)
    {
        if (move != null)
        {
            this.board.updateBoard(move);
            currentBoardScore = boardUtility(board);
        }
    }

    /**
     * We apply our alpha beta here and return the move accordingly
     * @return the next move to be made by our agent
     */
    @Override
    public Move move()
    {
        // Setting different depths for different board sizes
        int depth;
        if (board.getN() < 6)
            depth = 10;
        else if (board.getN() == 6)
            depth = 8;
        else if (board.getN() == 7)
            depth = 8;
        else
            depth = 6;

        // Determine next move by alpha beta algorithm
        Move mv = alphaBeta(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (mv != null)
        {
            // update board setups and utility score
            this.board.updateBoard(mv);
            currentBoardScore = boardUtility(board);
        }

        //reset number of lateral moves for this player
        board.getPlayer().setNumLateralMoves(0);
        return mv;
    }


    /**
     * Here Alpha-Beta pruning is applied to get the next move. We call
     * max directly from here so that we can keep track of the best move
     * we find and the board with the best utility during our search
     * @return the next move the agent should make
     */
    protected Move alphaBeta(Board board, int depth, double alpha, double beta)
    {
        double v;
        double maxValue = Integer.MIN_VALUE;
        Move maxMove = null;
        // get all legal moves from player
        ArrayList<Move> legalMoves = new ArrayList<>();
        legalMoves.addAll(board.getPlayer().getLegalMoves());

        // Decreasing the depth if we are near the end of the game
        if (board.getVerticalPieces().size() < 3 || board.getHorizontalPieces().size() < 3)
            depth /= 2;

        //iterate through all moves
        Iterator<Move> iterator = legalMoves.iterator();

        while (iterator.hasNext())
        {
            Move move = iterator.next();

            board.updateBoard(move);
            //start search down the tree nodes
            v = minValue(board, alpha, beta, depth - 1);
            if (v > maxValue)
            {
                // assign max utility value for this starting move
                maxValue = v;
                maxMove = new Move(move.i, move.j, move.d);
            }
            //undo this move to keep the starting board state
            board.undoMove(move, board.getPlayer().getType());
            alpha = Math.max(alpha, v);
            //if desired utility is achieved, exit loop
            if (beta <= alpha)
                break;
        }
        return maxMove;
    }

    /**
     * Recursive function to get max utility for this player
     * @return max utility
     */
    protected double maxValue(Board board, double alpha, double beta, int depth)
    {
        // base case, if board is terminal or has reached end of tree depth
        if (board.isTerminal() || depth == 0)
            return calculateUtilityDiff(board);

        double v = Integer.MIN_VALUE;

        // get all legal moves from player
        ArrayList<Move> legalMoves = new ArrayList<>();
        legalMoves.addAll(board.getPlayer().getLegalMoves());
        Iterator<Move> iterator = legalMoves.iterator();

        while (iterator.hasNext())
        {
            Move move = iterator.next();
            board.updateBoard(move);
            //get max utility at this node, by traverse down the tree
            v = Math.max(v, minValue(board, alpha, beta, depth - 1));
            //assign alpha value
            alpha = Math.max(alpha, v);
            board.undoMove(move, board.getPlayer().getType());
            if (beta <= alpha)
                break;
        }
        return v;
    }

    /**
     * Recursive function to get max utility for this player
     * @return max utility
     */
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
    /**
     * Calculate utility of board based on several conditions
     * @return utility of the input board
     */
    public double boardUtility(Board board)
    {
        //initialize score
        double score = 1;
        //loop through all of this player's pieces
        if (board.getPlayer().getType() == 'H')
        {
            for (Horizontal h : board.getHorizontalPieces())
            {
                // reward if pieces are closer to the finishing edge
                score += h.getXPos() * 10;
            }
            // reward for having the least number of pieces on the board
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

        // Taxing lateral moves heavily as they usually cost the player the game
        score += board.getPlayer().getNumLateralMoves() * (-5000);
        // Reward states where the other player is being blocked
        score += board.getNumBlocked() * 500;

        return score;
    }

    public double calculateUtilityDiff(Board newBoard) { return boardUtility(newBoard) - currentBoardScore; }
    
}
