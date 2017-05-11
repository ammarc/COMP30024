package com.namnammar.components;

import aiproj.slider.Move;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Player
{
    // pieces that the player && opponentPieces controls
    private HashMap<Move, Integer> legalMoves;
    private char type;

    public Player(char player)
    {
        this.type = player;
        legalMoves = new HashMap<>();
    }

    public char getType() { return type; }

    public HashMap<Move, Integer> getLegalMoves()
    {
        return this.legalMoves;
    }

    public void addMoves(ArrayList<Move> moves)
    {
        for (Move move : moves)
            legalMoves.put(move, -1);
    }

    public void removeMove(Move move) {
        if (legalMoves.containsKey(move))
            legalMoves.remove(move);
        else
        {
            ArrayList<Move> toBeRemoved = new ArrayList<>();
            for (Move nextMove : legalMoves.keySet())
            {
                if (nextMove.i == move.i && nextMove.j == move.j && nextMove.d == move.d)
                {
                    toBeRemoved.add(nextMove);
                }
            }
            for (Move mv : toBeRemoved)
                legalMoves.remove(mv);
            toBeRemoved.clear();
        }
    }

    public void removeMoves(HashMap<Move, Integer> removals) {
        for(Move move : removals.keySet()) {
            removeMove(move);
        }
    }

    public void setMoveValue(Move move, int value)
    {
        legalMoves.put(move, value);
    }
}