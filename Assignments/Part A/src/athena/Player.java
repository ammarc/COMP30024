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
        if (player == 'H') {
            myPieces = board.getHorizontalPieces();
        }
        else {
            myPieces = board.getVerticalPieces();
        }
        int index = ran.nextInt(myPieces.size());

        Piece to_move = myPieces.get(index);

        ArrayList<Move.Direction> possibleMoves = board.possibleMoves(to_move);
        int num_possible = possibleMoves.size();
        ran = new Random(30006);
        index = ran.nextInt(num_possible);

        Move.Direction d = possibleMoves.get(index);

        System.out.println(possibleMoves);
        //System.out.println("dir: " + d + "\nPlayer: " + this.player);

        Move next = new Move(to_move.getXPos(), to_move.getYPos(), d);

        return next;
    }
}
