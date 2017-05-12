package com.namnammar.components;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - amamra, Nam - namn1
 * For COMP30024 Part A
 */


import aiproj.slider.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static aiproj.slider.Move.Direction.*;


public class Board
{
    private int n;
    private char[][] boardArray;
    private int utility;

    public ArrayList<Horizontal> getHorizontalPieces() {
        return horizontalPieces;
    }

    public ArrayList<Vertical> getVerticalPieces() {
        return verticalPieces;
    }

    private ArrayList<Horizontal> horizontalPieces = new ArrayList<>();
    private ArrayList<Vertical> verticalPieces = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    private Player player;
    private Player otherPlayer;

    public Player getPlayer() {
        return player;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public int getN() { return this.n; }

    public Board (String rawBoard, int dimension, char player)
    {
        Scanner in = new Scanner (rawBoard);
        /** An array of strings to store the rawBoard */

        boardArray = new char[dimension][];

        for(int i = 0; i < dimension; i++)
            boardArray[i] = new char[dimension];

        /** Removing the spaces and adding to our rawBoard */
        for (int i = dimension - 1; i >= 0; i--)
        {
            String nextString = in.nextLine();
            String s = nextString.replaceAll("\\s+", "");
            boardArray[i] = s.toCharArray();
        }

        in.close();

        if(player == 'H')
        {
            this.player = new Player('H');
            this.otherPlayer = new Player('V');
        }
        else
        {
            this.player = new Player('V');
            this.otherPlayer = new Player('H');
        }

        this.n = dimension;
        this.setUpPieces();
    }

    /**
     * This method sets up the pieces on the rawBoard by looking around them
     * and setting the boolean values of their directions correctly
     */
    public void setUpPieces ()
    {
        for (int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                char c = boardArray[i][j];
                if (c == 'H')
                {
                    Horizontal horizontal = new Horizontal(j, i);
                    /** Looking down, right, checking the case when the piece is
                     * on the right edge of the rawBoard and up respectively */
                    setHorizontalDir(horizontal);

                    horizontalPieces.add(horizontal);
                }

                else if (c == 'V')
                {
                    Vertical vertical = new Vertical(j, i);
                    /** Looking  up, right, checking the case when the piece is
                     * on the top edge of the rawBoard and left respectively */
                    setVerticalDir(vertical);

                    verticalPieces.add(vertical);
                }

                else if (c == 'B')
                {
                    Obstacle obs = new Obstacle(j, i);
                    obstacles.add(obs);
                }
            }
        }
    }

    public void updateBoard(Move move)
    {
        int col = move.i;
        int row = move.j;
        int newRow = row;
        int newCol = col;

        switch (move.d)
        {
            case UP:    newRow++; break;

            case DOWN:  newRow--; break;

            case LEFT:  newCol--; break;

            case RIGHT: newCol++; break;

            default:              break;
        }

        char toMove = boardArray[row][col];
        // swap places of two cells on rawBoard
        Piece moving = findPiece(col, row, toMove);
        boardArray[row][col] = '+';

        // Removing moves that have been made
        HashMap<Move, Double> toRemove = new HashMap<>();
        if(toMove == player.getType()) {
            for(Move mv : player.getLegalMoves().keySet()) {
                if(mv.i == col && mv.j == row) {
                    toRemove.put(mv, player.getLegalMoves().get(mv));
                }
            }
            player.removeMoves(toRemove);
        }
        else {
            for(Move mv : otherPlayer.getLegalMoves().keySet()) {
                if(mv.i == col && mv.j == row) {
                    toRemove.put(mv, otherPlayer.getLegalMoves().get(mv));
                }
            }
            otherPlayer.removeMoves(toRemove);
        }

        // and then remove it from the rawBoard
        if (newRow >= n || newCol >= n)
        {
            Piece toBeRemoved = null;
            if (moving instanceof Horizontal)
            {
                for (Piece h : horizontalPieces)
                    if (h.getXPos() == moving.getXPos() && h.getYPos() == moving.getYPos())
                        toBeRemoved = h;
                horizontalPieces.remove(toBeRemoved);
            }
            else
            {
                for (Piece v : verticalPieces)
                    if (v.getXPos() == moving.getXPos() && v.getYPos() == moving.getYPos())
                        toBeRemoved = v;
                verticalPieces.remove(toBeRemoved);
            }
        }
        else
        {
            boardArray[newRow][newCol] = toMove;
            System.err.println(this);
            System.err.println("--------------------------");
            System.err.println("Setting coords for: "+col+ " " + row+" to "+newCol+ " " +newRow);
            moving.setCoordinates(newCol, newRow);
            System.err.println("Vertical pieces are:");
            for (Piece p : verticalPieces)
                System.err.println(p);
            System.err.println("Horizontal pieces are:");
            for (Piece p : horizontalPieces)
                System.err.println(p);
            System.err.println("--------------------------");
        }

        // update the moved piece's direction and coordinates
        for (Piece h : horizontalPieces)
        {
            if (Math.abs(h.getXPos() - moving.getXPos()) <= 2 ||
                    Math.abs(h.getYPos() - moving.getYPos()) <= 2)
                setHorizontalDir(h);
        }

        for (Piece v : verticalPieces)
        {
            if (Math.abs(v.getXPos() - moving.getXPos()) <= 2 ||
                    Math.abs(v.getYPos() - moving.getYPos()) <= 2)
                setVerticalDir(v);
        }

    }

    private Piece findPiece(int column, int row, char type)
    {
        System.err.println("Now finding piece: "+type+" "+column+" "+row);
        System.err.println("Vertical pieces are:");
        for (Piece p : verticalPieces)
            System.err.println(p);
        System.err.println("Horizontal pieces are:");
        for (Piece p : horizontalPieces)
            System.err.println(p);
        if (type == 'H')
        {
            for (Piece h : this.horizontalPieces) {
                if (h.getXPos() == column && h.getYPos() == row) {
                    System.err.println("Found bitch!");
                    return h;

                }
            }
        }
        else if (type == 'V')
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
        ArrayList<Move> newMoves = new ArrayList<>();
        HashMap<Move, Double> toBeRemoved = new HashMap<>();

        // Looking down
        if (j > 0 && boardArray[j-1][i] == '+')
        {
            horizontal.setDownTrue();
            newMoves.add(new Move(i, j, DOWN));
        }

        else
        {
            horizontal.setDownFalse();
            toBeRemoved.put(new Move(i, j, DOWN), -1.0);
        }

        // Looking right
        if (i < n - 1 && boardArray[j][i+1] == '+')
        {
            horizontal.setRightTrue();
            newMoves.add(new Move(i, j, RIGHT));
        }

        else if (i == n - 1)
        {
            horizontal.setRightTrue();
            newMoves.add(new Move(i, j, RIGHT));
        }

        else
        {
            horizontal.setRightFalse();
            toBeRemoved.put(new Move(i, j, RIGHT), -1.0);
        }

        // Looking up
        if (j < n - 1 && boardArray[j+1][i] == '+')
        {
            horizontal.setUpTrue();
            newMoves.add(new Move(i, j, UP));
        }

        else
        {
            horizontal.setUpFalse();
            toBeRemoved.put(new Move(i, j, UP), -1.0);
        }

        if (player.getType() == 'H')
        {
            player.addMoves(newMoves);
            player.removeMoves(toBeRemoved);
        }
        else
        {
            otherPlayer.addMoves(newMoves);
            otherPlayer.removeMoves(toBeRemoved);
        }
    }

    // TODO: Code duplication in both these methods
    private void setVerticalDir(Piece vertical)
    {
        int i = vertical.getXPos();
        int j = vertical.getYPos();
        ArrayList<Move> newMoves = new ArrayList<>();
        // TODO: removing from a hash map like this is not right; need to update this
        HashMap<Move, Double> toBeRemoved = new HashMap<>();

        // Looking up
        if (j < n - 1 && boardArray[j+1][i] == '+')
        {
            vertical.setUpTrue();
            newMoves.add(new Move(i, j, UP));
        }

        else if (j == n - 1)
        {
            vertical.setUpTrue();
            newMoves.add(new Move(i, j, UP));
        }

        else
        {
            vertical.setUpFalse();
            toBeRemoved.put(new Move(i, j, UP), -1.0);
        }

        // Looking right
        if (i < n - 1 && boardArray[j][i+1] == '+')
        {
            vertical.setRightTrue();
            newMoves.add(new Move(i, j, RIGHT));
        }

        else
        {
            vertical.setRightFalse();
            toBeRemoved.put(new Move(i, j, RIGHT), -1.0);
        }


        // Looking left
        if (i > 0 && boardArray[j][i-1] == '+')
        {
            vertical.setLeftTrue();
            newMoves.add(new Move(i, j, LEFT));
        }

        else
        {
            vertical.setLeftFalse();
            toBeRemoved.put(new Move(i, j, LEFT), -1.0);
        }

        if (player.getType() == 'V')
        {
            player.addMoves(newMoves);
            player.removeMoves(toBeRemoved);
        }
        else
        {
            otherPlayer.addMoves(newMoves);
            otherPlayer.removeMoves(toBeRemoved);
        }
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


    // TODO: there are more terminal states of the rawBoard
    public boolean isTerminal()
    {
        if (horizontalPieces.size() == 0 || verticalPieces.size() == 0)
            return true;

        return false;
    }

    // TODO: this is just a rudimentary utility function, make a real one
    public int utility()  {
        int score = 0;
        if(player.getType() == 'H') {
            for(Horizontal h : horizontalPieces)
            {
                score += h.getXPos();
            }
        }
        else if(player.getType() == 'V') {
            for(Vertical v : verticalPieces) {
                score += v.getYPos();
            }
        }

        return score;
    }

    public int calculateUtilityDiff(Board oldBoard, Board newBoard)
    {
        return newBoard.utility() - oldBoard.utility();
    }

    /**
     * Copy constructor for the board
     * @param other the source to be copied
     */
    public Board (Board other)
    {
        this.boardArray = new char[other.getN()][other.getN()];
        this.verticalPieces = new ArrayList<>();
        this.horizontalPieces = new ArrayList<>();
        for (Horizontal p : other.getHorizontalPieces())
            this.horizontalPieces.add(new Horizontal(p));
        for (Vertical p : other.getVerticalPieces())
            this.verticalPieces.add(new Vertical(p));
        this.otherPlayer = new Player(other.otherPlayer.getType());
        this.player = new Player(other.player.getType());
        this.n = other.getN();
        for (int i = 0; i < other.getN(); i++)
            for (int j = 0; j < other.getN(); j++)
                this.boardArray[i][j] = other.boardArray[i][j];

        this.obstacles = (ArrayList<Obstacle>) other.obstacles.clone();
    }
    
    public String toString()
    {
        String str = "\n--------The Board--------\n";
        for (int i = this.n - 1; i >= 0; i--)
        {
            for(int j = 0; j < this.n; j++)
                str += boardArray[i][j] + " ";
            str += "\n";
        }
        return str;
    }
}