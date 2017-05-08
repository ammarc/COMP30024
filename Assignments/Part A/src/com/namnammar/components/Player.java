package com.namnammar.components;

import java.util.ArrayList;

/**
 * Created by Nam Nguyen on 5/05/2017.
 */
public class Player {
    // pieces that the player && opponent controls
    private ArrayList<Piece> myPieces;
    private ArrayList<Piece> opponent;
    private char type;

    public Player(ArrayList<Piece> hPieces, ArrayList<Piece> vPieces, char player){
        if(player == 'H'){
            this.myPieces = hPieces;
            this.opponent = vPieces;
        }
        else {
            this.myPieces = vPieces;
            this.opponent = hPieces;
        }
        this.type = player;
    }

    public char getType() {
        return type;
    }

    // strategy
    public void next(){}
}