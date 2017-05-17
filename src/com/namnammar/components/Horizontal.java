package com.namnammar.components;

/**
 * Made by Ammar Ahmed and Nam Nguyen
 * Student IDs: Ammar 728926, Nam 718604
 * User names: Ammar - amamra, Nam - namn1
 * For COMP30024 Part B
 */

public class Horizontal extends Piece
{
    /**
     * The constructor for this class
     * @param x the piece's x-coordinate
     * @param y the piece's y-coordinate
     */
    public Horizontal(int x, int y)
    {
        super(x,y);
    }

    public Horizontal(Horizontal other) {
        super(other);
    }
}