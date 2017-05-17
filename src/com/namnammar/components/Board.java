package com.namnammar.components;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - amamra, Nam - namn1
 * For COMP30024 Part B
 */


import aiproj.slider.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

import static aiproj.slider.Move.Direction.*;


public class Board
{
    private int n;
    private char[][] boardArray;

    public ArrayList<Horizontal> getHorizontalPieces() {
        return horizontalPieces;
    }

    public ArrayList<Vertical> getVerticalPieces() {
        return verticalPieces;
    }

    private ArrayList<Horizontal> horizontalPieces = new ArrayList<>();
    private ArrayList<Vertical> verticalPieces = new ArrayList<>();

    // store both players
    private Player player;
    private Player otherPlayer;

    public Player getPlayer() {
        return player;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public int getN() { return this.n; }

    /**
     * Constructor for Board
     * @param rawBoard String representation of board, provided by the Referee
     * @param dimension Dimension of board
     * @param player character representation of my player (either H or V)
     */
    public Board (String rawBoard, int dimension, char player)
    {
        Scanner in = new Scanner (rawBoard);
        /** An array of strings to store the rawBoard */

        boardArray = new char[dimension][];

        for(int i = 0; i < dimension; i++)
            boardArray[i] = new char[dimension];

        /** Removing the spaces and adding to our rawBoard
         * reading from last row of string as board start from bottom left
         * */
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
        // set direction of all Pieces
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
            }
        }
    }

    /**
     * Undo the provided move
     * @param move Move to undo
     * @param type which player made the move
     */
    public void undoMove(Move move, char type)
    {

        // If the row was 0 and column was 1, and went up
        // newRow was 1 and column was 1.
        // Now when you want to undo 0, 1 up. You find newRow,
        // newColumn into a +

        int col = move.i;
        int row = move.j;

        //get new coordinates set based on move direction
        Point new_coor = findCoordinates(move.d, row, col);
        int newCol = new_coor.x;
        int newRow = new_coor.y;

        Piece moving;
        //assign Piece to old board cell
        boardArray[row][col] = type;
        //make the Board cell available again if Piece didn't move off the board
        if(newRow < n && newCol < n)
        {
            boardArray[newRow][newCol] = '+';
            moving = findPiece(newCol, newRow, type);
            //reset coordinates of piece
            moving.setCoordinates(col, row);
        }
        //otherwise add Piece back to array
        else
        {
            if(type == 'H')
                horizontalPieces.add(new Horizontal(col, row));
            else
                verticalPieces.add(new Vertical(col, row));
        }

        // update the moved piece's direction and coordinates
        player.getLegalMoves().clear();
        otherPlayer.getLegalMoves().clear();
        for (Piece h : horizontalPieces)
            setHorizontalDir(h);

        for (Piece v : verticalPieces)
            setVerticalDir(v);

        // Undoing the lateral move count if a lateral move was made
        if (this.getPlayer().getType() == 'H')
            if (move.d == Move.Direction.UP || move.d == Move.Direction.DOWN)
                this.getPlayer().setNumLateralMoves(this.getPlayer().getNumLateralMoves()-1);
        else if (this.getPlayer().getType() == 'V')
            if (move.d == Move.Direction.LEFT || move.d == Move.Direction.RIGHT)
                this.getPlayer().setNumLateralMoves(this.getPlayer().getNumLateralMoves()-1);
    }

    /**
     * Update board given move
     * @param move Move made by a player
     */
    public void updateBoard(Move move)
    {
        if (move == null)
            return;

        int col = move.i;
        int row = move.j;

        Point new_coor = findCoordinates(move.d, row, col);
        int newCol = new_coor.x;
        int newRow = new_coor.y;

        char toMove = boardArray[row][col];
        // swap places of two cells on rawBoard
        Piece moving = findPiece(col, row, toMove);
        boardArray[row][col] = '+';

        // Checks if the moves made were out of the board
        if (newRow >= n || newCol >= n)
        {
            Piece pieceToBeRemoved = null;
            // remove Piece that just moved off from array
            if (toMove == 'H')
            {
                for (Piece h : horizontalPieces)
                    if (h.getXPos() == moving.getXPos() && h.getYPos() == moving.getYPos())
                        pieceToBeRemoved = h;
                horizontalPieces.remove(pieceToBeRemoved);
            }
            else if (toMove == 'V')
            {
                for (Piece v : verticalPieces)
                    if (v.getXPos() == moving.getXPos() && v.getYPos() == moving.getYPos())
                        pieceToBeRemoved = v;
                verticalPieces.remove(pieceToBeRemoved);
            }
        }
        else
        {
            boardArray[newRow][newCol] = toMove;
            moving.setCoordinates(newCol, newRow);
        }

        // update the moved piece's direction and coordinates
        player.getLegalMoves().clear();
        otherPlayer.getLegalMoves().clear();
        for (Piece h : horizontalPieces)
        {
            setHorizontalDir(h);
        }

        for (Piece v : verticalPieces)
        {
            setVerticalDir(v);
        }

        // Updating the number of lateral moves of the player
        if (this.getPlayer().getType() == 'H')
            if (move.d == Move.Direction.UP || move.d == Move.Direction.DOWN)
                this.getPlayer().setNumLateralMoves(this.getPlayer().getNumLateralMoves()+1);
        else if (this.getPlayer().getType() == 'V')
            if (move.d == Move.Direction.LEFT || move.d == Move.Direction.RIGHT)
                this.getPlayer().setNumLateralMoves(this.getPlayer().getNumLateralMoves()+1);
    }

    /**
     * Find the appropriate piece from arrays according to coordinates
     * @param column col index
     * @param row row index
     * @param type type of player
     * @return
     */
    private Piece findPiece(int column, int row, char type)
    {
        if (type == 'H')
        {
            for (Piece h : this.horizontalPieces) {
                if (h.getXPos() == column && h.getYPos() == row) {
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

        System.err.println("ERROR: no piece found at " + column + ", " + row+"\nThe board is:");
        System.err.println(this);
        System.err.println("----------------------");
        return null;
    }

    /**
     * Look in all possible direction and set value accordingly
     * @param horizontal piece to set direction
     */
    private void setHorizontalDir(Piece horizontal)
    {
        int i = horizontal.getXPos();
        int j = horizontal.getYPos();
        ArrayList<Move> newMoves = new ArrayList<>();

        // Looking down
        if (j > 0 && boardArray[j-1][i] == '+')
        {
            horizontal.setDownTrue();
            newMoves.add(new Move(i, j, DOWN));
        }

        else
            horizontal.setDownFalse();

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
            horizontal.setRightFalse();

        // Looking up
        if (j < n - 1 && boardArray[j+1][i] == '+')
        {
            horizontal.setUpTrue();
            newMoves.add(new Move(i, j, UP));
        }

        else
            horizontal.setUpFalse();

        // Adding all the moves possible to the player
        if (player.getType() == 'H')
        {
            player.addMoves(newMoves);
            newMoves.clear();
        }
        else
        {
            otherPlayer.addMoves(newMoves);
            newMoves.clear();
        }
    }

    /**
     * Look in all possible direction and set value accordingly
     * @param vertical piece to set direction
     */
    private void setVerticalDir(Piece vertical)
    {
        int i = vertical.getXPos();
        int j = vertical.getYPos();
        ArrayList<Move> newMoves = new ArrayList<>();

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
            vertical.setUpFalse();

        // Looking right
        if (i < n - 1 && boardArray[j][i+1] == '+')
        {
            vertical.setRightTrue();
            newMoves.add(new Move(i, j, RIGHT));
        }

        else
            vertical.setRightFalse();


        // Looking left
        if (i > 0 && boardArray[j][i-1] == '+')
        {
            vertical.setLeftTrue();
            newMoves.add(new Move(i, j, LEFT));
        }

        else
            vertical.setLeftFalse();

        // Adding all the moves possible for the vertical player
        if (player.getType() == 'V')
        {
            player.addMoves(newMoves);
        }
        else
        {
            otherPlayer.addMoves(newMoves);
        }
    }

    /**
     * check if game has ended
     * @return true if the board is at a terminal state, false otherwise
     */
    public boolean isTerminal()
    {
        //if either player has moved all pieces off the board
        if (horizontalPieces.size() == 0 || verticalPieces.size() == 0){
            return true;
        }
        //if neither player has any moves
        if (this.player.getNumMoves() == 0 && this.otherPlayer.getNumMoves() == 0){
            return true;
        }

        return false;
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

        // Copying all the data from the previous players of the other board
        Player otherPlayer = new Player(other.getOtherPlayer().getType());
        otherPlayer.setNumLateralMoves(other.getOtherPlayer().getNumLateralMoves());
        this.otherPlayer = otherPlayer;

        Player player = new Player(other.getPlayer().getType());
        player.setNumLateralMoves(other.getPlayer().getNumLateralMoves());
        this.player = player;

        this.n = other.getN();
        for (int i = 0; i < other.getN(); i++)
            for (int j = 0; j < other.getN(); j++)
                this.boardArray[i][j] = other.boardArray[i][j];
    }

    /**
     * @return number of opponent's pieces blocked
     */
    public int getNumBlocked ()
    {
        int numBlocked = 0;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (player.getType() == 'H' && i > 0)
                    if (boardArray[i-1][j] == 'V' && boardArray[i][j] == 'H')
                        numBlocked += 1;
                else if (player.getType() == 'V' && j > 0)
                    if (boardArray[i][j-1] == 'H' && boardArray[i][j] == 'V')
                        numBlocked += 1;
            }
        }
        return numBlocked;
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

    /**
     * @return Point object that consists of column and row coordinates of where the piece to moving to
     */
    private Point findCoordinates(Move.Direction d, int newRow, int newCol){
        switch (d)
        {
            case UP:    newRow++; break;

            case DOWN:  newRow--; break;

            case LEFT:  newCol--; break;

            case RIGHT: newCol++; break;

            default:              break;
        }

        return new Point(newCol, newRow);
    }
}