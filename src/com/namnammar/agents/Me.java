package com.namnammar.agents;

import aiproj.slider.Move;
import aiproj.slider.SliderPlayer;
import com.namnammar.components.Board;

import java.util.Scanner;

public class Me implements SliderPlayer {
    private Board board;

    public void init(int dimension, String board, char player)
    {
        this.board = new Board(board, dimension, player);
    }

    public void update(Move move)
    {
        if (move != null)
        {
            this.board.updateBoard(move);
        }
    }

    /**
     * Human-controlled player, reads in input and move accordingly
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
