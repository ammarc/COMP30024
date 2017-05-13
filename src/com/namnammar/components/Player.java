package com.namnammar.components;

import aiproj.slider.Move;

import java.util.ArrayList;

public class Player
{
    // pieces that the player && opponentPieces controls
    private ArrayList<Move> legalMoves;
    private char type;
    private int numLateralMoves;

    public Player(char player)
    {
        this.type = player;
        legalMoves = new ArrayList<>();
    }

    public void setNumLateralMoves(int numLateralMoves) {
        this.numLateralMoves = numLateralMoves;
    }

    public int getNumLateralMoves() {
        return numLateralMoves;
    }

    public char getType() { return type; }

    public ArrayList<Move> getLegalMoves()
    {
        /*ArrayList<Move> toReturn = new ArrayList<>();
        for (Move move : legalMoves)
            if (move.d == Move.Direction.RIGHT && type == 'H')
                toReturn.add(move);
            else if (move.d == Move.Direction.UP && type == 'V')
                toReturn.add(move);
        if (!toReturn.isEmpty())
            return toReturn;*/
        return legalMoves;
    }

    public void addMoves(ArrayList<Move> moves)
    {
        for (Move move : moves)
            legalMoves.add(move);
    }


    public void removeMoves(ArrayList<Move> removals)
    {
        for(Move move : removals)
        {
            legalMoves.remove(move);
        }
    }
}