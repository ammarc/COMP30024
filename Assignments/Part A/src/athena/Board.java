package athena;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - amamra, Nam - namn1
 * For COMP30024 Part A
 */

import aiproj.slider.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Board
{
    private int n;
    public static enum Type {BLANK, HSLIDER, VSLIDER, OBSTACLE};

    private HashMap<Point, Type> board;

    private HashMap<Point, Piece> horizontalPieces;
    private HashMap<Point, Piece> verticalPieces;

    public ArrayList<Piece> getHorizontalPieces() {
        return new ArrayList<>(horizontalPieces.values());
    }

    public ArrayList<Piece> getVerticalPieces() {
        return new ArrayList<>(verticalPieces.values());
    }

    /**
     * Constructor for this class
     * @param boardArray 2-D board array
     */
    public Board (ArrayList<String[]> boardArray, int n)
    {
        this.n = n;
        horizontalPieces = new HashMap<>(n);
        verticalPieces = new HashMap<>(n);

        board = readBoard(boardArray, horizontalPieces, verticalPieces);

        for(Piece p : horizontalPieces.values()){
            setHorizontalDir(p);
        }
        for(Piece p : verticalPieces.values()){
            setVerticalDir(p);
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
