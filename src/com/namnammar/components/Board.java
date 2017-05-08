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
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
                    Horizontal horizontal = new Horizontal(j, i);
                    /** Looking down, right, checking the case when the piece is
                     * on the right edge of the board and up respectively */
                    setHorizontalDir(horizontal);

                    horizontalPieces.add(horizontal);
                }

                else if (c.equals("V"))
                {
                    Vertical vertical = new Vertical(j, i);
                    /** Looking  up, right, checking the case when the piece is
                     * on the top edge of the board and left respectively */
                    setVerticalDir(vertical);

                    verticalPieces.add(vertical);
                }

                else if (c.equals("B"))
                {
                    Obstacle obs = new Obstacle(j, i);
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
        System.err.println("Moving "+col+" " +row+" to "+newCol+" "+newRow);
        board.get(newRow)[newCol] = toMove;

        // find the piece
        Piece moving = findPiece(col, row, toMove);
        moving.setCoordinates(newCol, newRow);

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

    private Piece findPiece(int column, int row, String type)
    {
        if (type.equals("H"))
        {
            for (Piece h : this.horizontalPieces)
                if (h.getXPos() == column && h.getYPos() == row)
                    return h;
        }
        else
        {
            for (Piece v : this.verticalPieces)
                if (v.getXPos() == column && v.getYPos() == row)
                    return v;
        }

        System.err.println("ERROR: no piece found at " + column + ", " + row);
        return null;
    }

    // TODO: Code duplication in both these methods
    private void setHorizontalDir(Piece horizontal)
    {
        int i = horizontal.getXPos();
        int j = horizontal.getYPos();

        // Looking down
        if (j > 0 && board.get(j-1)[i].equals("+")) horizontal.setDownTrue();

        else horizontal.setDownFalse();

        // Looking right
        if (i < n - 1 && board.get(j)[i+1].equals("+")) horizontal.setRightTrue();

        else if (i == n - 1) horizontal.setRightTrue();

        else horizontal.setRightFalse();

        // Looking up
        if (j < n - 1 && board.get(j+1)[i].equals("+")) horizontal.setUpTrue();

        else horizontal.setUpFalse();

    }

    // TODO: Code duplication in both these methods
    private void setVerticalDir(Piece vertical)
    {
        int i = vertical.getXPos();
        int j = vertical.getYPos();

        // Looking up
        if (j < n - 1 && board.get(j+1)[i].equals("+")) vertical.setUpTrue();

        else if (j == n - 1) vertical.setUpTrue();

        else vertical.setUpFalse();

        // Looking right
        if (i < n - 1 && board.get(j)[i+1].equals("+")) vertical.setRightTrue();

        else vertical.setRightFalse();

        // Looking left
        if (i > 0 && board.get(j)[i-1].equals("+")) vertical.setLeftTrue();

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

    public void printBoard()
    {
        System.out.println("Printing the input board");
        for (String s[] : board)
        {
            for (String c : s)
            {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println(board.get(0)[1]);
        System.out.println();
    }
}