package com.namnammar.components;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * For COMP30024 Part B
 */

public class Vertical extends Piece
{
    /**
     * The constructor for this class
     * @param x the piece's x-coordinate
     * @param y the piece's y-coordinate
     */
    public Vertical(int x, int y)
    {
        super(x,y);
    }

    public Vertical(Vertical other) {
        super(other);
    }
}
