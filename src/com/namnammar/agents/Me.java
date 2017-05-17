package com.namnammar.agents;
/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - ammara, Nam - namn1
 * For COMP30024 Part B
 */

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;
import com.namnammar.components.Board;

import java.util.Scanner;

public class Me implements SliderPlayer {
    private Board board;

    /**
     * @param dimension The width and height of the board in cells
     * @param board A string representation of the initial state of the board,
     * as described in the part B specification
     * @param player 'H' or 'V', corresponding to which pieces the player will
     */
    public void init(int dimension, String board, char player)
    {
        this.board = new Board(board, dimension, player);
    }

    /**
     * @param move A Move object representing the previous move made by the
     * opponent, which may be null (indicating a pass). Also, before the first
     */
    public void update(Move move)
    {
        if (move != null)
        {
            this.board.updateBoard(move);
        }
    }

    /**
     * Human-controlled player, reads in input and move accordingly
     * @return Move made by the player read from STDIN
     */
    public Move move() {
        System.out.println("C   R   Direction");
        Scanner in = new Scanner(System.in);
        int col = in.nextInt();
        int row = in.nextInt();
        String dir = in.next();
        Move.Direction d = null;
        if(dir.equalsIgnoreCase("up"))
            d = Move.Direction.UP;
        if(dir.equalsIgnoreCase("down"))
            d = Move.Direction.DOWN;
        if(dir.equalsIgnoreCase("right"))
            d = Move.Direction.RIGHT;
        if(dir.equalsIgnoreCase("left"))
            d = Move.Direction.LEFT;
        return new Move (col, row, d);
    }
}
