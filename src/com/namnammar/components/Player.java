package com.namnammar.components;

import aiproj.slider.Move;

import java.util.ArrayList;
import java.util.Iterator;

public class Player
{
    // pieces that the player && opponentPieces controls
    private ArrayList<Move> legalMoves;
    private char type;

    public Player(char player)
    {
        this.type = player;
        legalMoves = new ArrayList<>();
    }

    public char getType() { return type; }

    public ArrayList<Move> getLegalMoves()
    {
        return this.legalMoves;
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