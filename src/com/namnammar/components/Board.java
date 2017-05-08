package com.namnammar.components;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - amamra, Nam - namn1
 * For COMP30024 Part A
 */

import com.namnammar.components.Horizontal;
import com.namnammar.components.Obstacle;
import com.namnammar.components.Piece;
import com.namnammar.components.Vertical;

import java.util.ArrayList;


public class Board
{
    private int n;
    private ArrayList<String[]> board;

    public ArrayList<Piece> getHorizontalPieces() {
        return horizontalPieces;
    }

    public ArrayList<Piece> getVerticalPieces() {
        return verticalPieces;
    }

    private ArrayList<Piece> horizontalPieces = new ArrayList<>();
    private ArrayList<Piece> verticalPieces = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    public void setN(int n) { this.n = n; }

    public int getN() { return this.n; }

    /**
     * Constructor for this class
     * @param boardArray 2-D board array
     */
    public Board (ArrayList<String[]> boardArray)
    {
        this.board = boardArray;
    }

    /**
     * This method sets up the pieces on the board by looking around them
     * and setting the boolean values of their directions correctly
     */
    public void setUpPieces ()
    {
        for (int i = 0; i < n; i++)
        {
            int j = 0;
            for (String c : board.get(i))
            {
                if (c.equals("H"))
                {
                    Horizontal horizontal = new Horizontal(i, j);
                    /** Looking down, right, checking the case when the piece is
                     * on the right edge of the board and up respectively */
                    setHorizontalDir(horizontal);

                    horizontalPieces.add(horizontal);
                }

                else if (c.equals("V"))
                {
                    Vertical vertical = new Vertical(i, j);
                    /** Looking  up, right, checking the case when the piece is
                     * on the top edge of the board and left respectively */
                    setVerticalDir(vertical);

                    verticalPieces.add(vertical);
                }

                else if (c.equals("B"))
                {
                    Obstacle obs = new Obstacle(i, j);
                    obstacles.add(obs);
                }
                j++;
            }
        }
    }

    public void updateBoard(int row, int col, int newRow, int newCol)
    {
        String toMove = board.get(row)[col];
        // swap places of two cells on board
        board.get(row)[col] = "+";
        board.get(newRow)[newCol] = toMove;

        // find the piece
        Piece moving = findPiece(row, col, toMove);
        moving.setCoordinates(newRow, newCol);

        // update the moved piece's direction and coordinates
        for (Piece h : horizontalPieces)
        {
            if (Math.abs(h.getXPos() - moving.getXPos()) < 2 ||
                    Math.abs(h.getYPos() - moving.getYPos()) < 2)
                setHorizontalDir(h);
        }

        for (Piece v : verticalPieces)
        {
            if (Math.abs(v.getXPos() - moving.getXPos()) < 2 ||
                    Math.abs(v.getYPos() - moving.getYPos()) < 2)
                setVerticalDir(v);
        }

    }

    private Piece findPiece(int row, int column, String type)
    {
        if (type.equals("H"))
        {
            for (Piece h : this.horizontalPieces)
                if (h.getXPos() == row && h.getYPos() == column)
                    return h;
        }
        else
        {
            for (Piece v : this.verticalPieces)
                if (v.getXPos() == row && v.getYPos() == column)
                    return v;
        }

        System.err.println("ERROR: no piece found at " + row + ", " + column);
        return null;
    }

    // TODO: Code duplication in both these methods
    private void setHorizontalDir(Piece horizontal)
    {
        int i = horizontal.getXPos();
        int j = horizontal.getYPos();

        // Looking up
        if (i > 0 && board.get(i-1)[j].equals("+")) horizontal.setUpTrue();

        else horizontal.setUpFalse();

        // Looking right
        if (j < n - 1 && board.get(i)[j+1].equals("+")) horizontal.setRightTrue();

        else if (j == n - 1) horizontal.setRightTrue();

        else horizontal.setRightFalse();

        // Looking down
        if (i < n - 1 && board.get(i+1)[j].equals("+")) horizontal.setDownTrue();

        else horizontal.setDownFalse();

    }

    // TODO: Code duplication in both these methods
    private void setVerticalDir(Piece vertical)
    {
        int i = vertical.getXPos();
        int j = vertical.getYPos();

        // Looking up
        if (i < n - 1 && board.get(i+1)[j].equals("+")) vertical.setUpTrue();

        else if (i == n - 1) vertical.setUpTrue();

        else vertical.setUpFalse();

        // Looking right
        if (j < n - 1 && board.get(i)[j+1].equals("+")) vertical.setRightTrue();

        else vertical.setRightFalse();

        // Looking left
        if (j > 0 && board.get(i)[j-1].equals("+")) vertical.setLeftTrue();

        else vertical.setLeftFalse();
    }

    /**
     * Calculates the number of total horizontal legal moves
     * by first setting them for the piece and adding it to total
     * @return number of legal horizontal moves
     */
    public int numLegalHMoves()
    {
        int count = 0;

        for( Piece p : horizontalPieces)
        {
            p.setLegalMoves();
            count += p.getNumLegalMoves();
        }
        return count;
    }

    /**
     * Calculates the number of total vertical legal moves
     * by first setting them for the piece and adding it to total
     * @return number of legal vertical moves
     */
    public int numLegalVMoves()
    {
        int count = 0;

        for (Piece p : verticalPieces)
        {
            p.setLegalMoves();
            count += p.getNumLegalMoves();
        }
        return count;
    }

    public void setFree (int row, int column)
    {
        board.get(row)[column] = "+";
    }
}