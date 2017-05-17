package com.namnammar.components;

import aiproj.slider.Move;

import java.util.ArrayList;

public class Player
{
    // all legal moves of this player
    private ArrayList<Move> legalMoves;
    // player type, either H or V
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

    public int getNumMoves(){ return legalMoves.size();};

    //add moves to internal memory structure
    public void addMoves(ArrayList<Move> moves)
    {
        for (Move move : moves)
            legalMoves.add(move);
    }

}