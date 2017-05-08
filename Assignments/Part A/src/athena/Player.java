package athena;

import aiproj.slider.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nam Nguyen on 5/05/2017.
 */
public class Player {

    private char player;

    public Player(char player){
        this.player = player;
    }

    // random strategy
    public Move random_next(Board board){
        Random ran = new Random(0);
        ArrayList<Piece> myPieces;
        ArrayList<Piece> opps;

        if (player == 'H') {
            myPieces = board.getHorizontalPieces();
            opps = board.getVerticalPieces();
        }
        else {
            myPieces = board.getVerticalPieces();
            opps = board.getHorizontalPieces();
        }

//        System.out.println("My Pieces:");
//        for (Piece p : myPieces){
//            System.out.println(p.getXPos() + " " +p.getYPos());
//        }
//        System.out.println("Opponents:");
//        for (Piece p : opps){
//            System.out.println(p.getXPos() + " " +p.getYPos());
//        }

        int index = ran.nextInt(myPieces.size());

        Piece to_move = myPieces.get(index);

        ArrayList<Move.Direction> possibleMoves = board.possibleMoves(to_move);
        int num_possible = possibleMoves.size();

        if (num_possible > 0){

            return random_move(to_move, possibleMoves, num_possible);
        }
        else {
            while (myPieces.size() > 0){
                myPieces.remove(to_move);
                index = ran.nextInt(myPieces.size());
                to_move = myPieces.get(index);
                possibleMoves = board.possibleMoves(to_move);
                num_possible = possibleMoves.size();
                if (num_possible > 0){
                    return random_move(to_move, possibleMoves, num_possible);
                }
            }

            return null;
        }
    }

    private Move random_move(Piece to_move, ArrayList<Move.Direction> possibleMoves, int num_possible){

        Random ran = new Random(30006);
        int index = ran.nextInt(num_possible);

        Move.Direction d = getDir(possibleMoves, index, ran);

        Move next = new Move(to_move.getXPos(), to_move.getYPos(), d);

        return next;
    }

    private Move.Direction getDir(ArrayList<Move.Direction> possibleMoves, int index, Random ran){
        Move.Direction d;

        if(player=='V'){
            System.out.println("V moves:");
            System.out.println(possibleMoves);
        }

        if (possibleMoves.contains(Move.Direction.RIGHT)&& player=='H'){
            d = Move.Direction.RIGHT;
        }
        else {
            if(possibleMoves.contains(Move.Direction.UP)&& player=='V'){
                d = Move.Direction.UP;
            }
            else {
                d = possibleMoves.get(index);
            }
        }

        return d;
    }
}
