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

        board = readBoard(n, boardArray, horizontalPieces, verticalPieces);

        for(Piece p : horizontalPieces.values()){
            setHorizontalDir(p);
        }
        for(Piece p : verticalPieces.values()){
            setVerticalDir(p);
        }
    }

//    /**
//     * This method sets up the pieces on the board by looking around them
//     * and setting the boolean values of their directions correctly
//     */
//    public void setUpPieces ()
//    {
//        for (int i = 0; i < n; i++)
//        {
//            int j = 0;
//            for (String c : board.get(i))
//            {
//                if (c.equals("H"))
//                {
//                    Horizontal horizontal = new Horizontal(i, j);
//                    /** Looking down, right, checking the case when the piece is
//                     * on the right edge of the board and up respectively */
//                    setHorizontalDir(horizontal);
//
//                    horizontalPieces.add(horizontal);
//                }
//
//                else if (c.equals("V"))
//                {
//                    Vertical vertical = new Vertical(i, j);
//                    /** Looking  up, right, checking the case when the piece is
//                     * on the top edge of the board and left respectively */
//                    setVerticalDir(vertical);
//
//                    verticalPieces.add(vertical);
//                }
//
//                else if (c.equals("B"))
//                {
//                    Obstacle obs = new Obstacle(i, j);
//                    obstacles.add(obs);
//                }
//                j++;
//            }
//        }
//    }

    public void updateBoard(int col, int row, int new_col, int new_row){
        Point cur = new Point(col, row);
        Point next = new Point(new_col, new_row);
        Type to_move = this.board.get(cur);
        // swap places of two cells on board
        this.board.replace(cur, Type.BLANK);
        this.board.replace(next, to_move);

        //find the piece
        Piece moving = findPiece(row, col, to_move);
        moving.setCoordinates(new_col, new_row);

        //update all pieces' direction
        for (Piece p : horizontalPieces.values()){
            setHorizontalDir(p);
        }
        for (Piece p : verticalPieces.values()){
            setVerticalDir(p);
        }

    }

    private Piece findPiece(int i, int j, Type type){
        if (type == Type.HSLIDER){
            return this.horizontalPieces.get(new Point(j,i));
        }
        else {
            return this.verticalPieces.get(new Point(j,i));
        }
    }

    private void setHorizontalDir(Piece horizontal){
        int i = horizontal.getXPos();
        int j = horizontal.getYPos();

        horizontal.resetDir();

        if (i > 0 && board.get(new Point(j,i-1))==Type.BLANK) { horizontal.addDir(Piece.Direction.UP); }

        if (j < n - 1 && board.get(new Point(j+1,i))==Type.BLANK) { horizontal.addDir(Piece.Direction.RIGHT); }

        else if (j == n - 1) { horizontal.addDir(Piece.Direction.RIGHT); }

        if (i < n - 1 && board.get(new Point(j,i+1))==Type.BLANK) { horizontal.addDir(Piece.Direction.DOWN);; }

    }

    private void setVerticalDir(Piece vertical){
        int j = vertical.getXPos();
        int i = vertical.getYPos();

        vertical.resetDir();

        if (i > 0 && board.get(new Point(j,i-1))==Type.BLANK) { vertical.addDir(Piece.Direction.UP); }

        else if (i == 0) { vertical.addDir(Piece.Direction.UP); }

        if (j < n - 1 && board.get(new Point(j+1,i))==Type.BLANK) { vertical.addDir(Piece.Direction.RIGHT); }

        if (j > 0 && board.get(new Point(j-1,i))==Type.BLANK) { vertical.addDir(Piece.Direction.LEFT); }
    }

    /**
     * Calculates the number of total horizontal legal moves
     * by first setting them for the piece and adding it to total
     * @return number of legal horizontal moves
     */
    public int numLegalHMoves()
    {
        int count = 0;

        for( Piece p : horizontalPieces.values())
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

        for (Piece p : verticalPieces.values())
        {
            p.setLegalMoves();
            count += p.getNumLegalMoves();
        }
        return count;
    }

    public ArrayList<Move.Direction> possibleMoves(Piece p){
        ArrayList<Move.Direction> possibleCells = new ArrayList<>();

        ArrayList<Piece.Direction> dir = p.getDirection();

        if (dir.contains(Piece.Direction.LEFT)){
            possibleCells.add(Move.Direction.LEFT);
        }
        if (dir.contains(Piece.Direction.RIGHT)){
            possibleCells.add(Move.Direction.RIGHT);
        }
        if (dir.contains(Piece.Direction.UP)){
            possibleCells.add(Move.Direction.UP);
        }
        if (dir.contains(Piece.Direction.DOWN)){
            possibleCells.add(Move.Direction.DOWN);
        }

        return possibleCells;
    }

    private HashMap<Point, Type> readBoard(ArrayList<String[]> boardArray, HashMap<Point, Piece> hPieces, HashMap<Point, Piece> vPieces) {
        HashMap<Point, Type> boardMap = new HashMap<>(n*n);
        int i,j;
        Point coor;
        for(i=0; i<n; i++){
            for(j=0; j<n; j++){
                coor = new Point(j,i);
                switch (boardArray.get(i)[j]){
                    case "+":
                        boardMap.put(coor, Type.BLANK);
                        break;
                    case "H":
                        Horizontal h = new Horizontal(j,i);
                        boardMap.put(coor, Type.HSLIDER);
                        hPieces.put(coor, h);
                        break;
                    case "V":
                        Vertical v = new Vertical(j,i);
                        boardMap.put(coor, Type.VSLIDER);
                        vPieces.put(coor, v);
                        break;
                    case "B":
                        boardMap.put(coor, Type.OBSTACLE);
                }


            }
        }

        return boardMap;
    }

    public void printBoard(){
        for(int i = 0; i<n; i++){
            for(int j = 0; j < n; j++){
                Type t = board.get(new Point(j,i));
                switch (t){
                    case BLANK:
                        System.out.print("+ ");
                        break;
                    case HSLIDER:
                        System.out.print("H ");
                        break;
                    case VSLIDER:
                        System.out.print("V ");
                        break;
                    case OBSTACLE:
                        System.out.print("B ");
                        break;
                }
            }
            System.out.println();
        }
    }
}