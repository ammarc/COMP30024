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
        this.numLateralMoves = 0;
    }

    public void setNumLateralMoves(int numLateralMoves)
    {
        this.numLateralMoves = numLateralMoves;
        if (numLateralMoves < 0)
            this.numLateralMoves = 0;
    }

    public int getNumLateralMoves() {
        return numLateralMoves;
    }

    public char getType() { return type; }

    public ArrayList<Move> getLegalMoves()
    {
        return legalMoves;
    }

    public void addMoves(ArrayList<Move> moves)
    {
        for (Move move : moves)
            legalMoves.add(move);
    }
}