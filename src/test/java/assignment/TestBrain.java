package assignment;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class TestBrain {
    @Test
    public void brainTester(){
        TetrisBoard board = new TetrisBoard(10,20);
        TetrisPiece piece = new TetrisPiece(Piece.PieceType.SQUARE);
        BumbleBeeBrain ourBrain = new BumbleBeeBrain();

        board.nextPiece(piece, new Point(0, 0));
        board.setPiece();
        board.nextPiece(piece, new Point(2, 0));
        board.setPiece();
        board.nextPiece(piece, new Point(4, 0));
        board.setPiece();
        board.nextPiece(piece, new Point(6, 0));
        board.setPiece();

        board.nextPiece(piece, new Point(7, 18));

        Assert.assertEquals(ourBrain.nextMove(board), Board.Action.RIGHT);

        board.move(ourBrain.nextMove(board));

        Assert.assertEquals(ourBrain.nextMove(board), Board.Action.DROP);


        TetrisBoard board2 = new TetrisBoard(10,20);
        TetrisPiece piece2 = new TetrisPiece(Piece.PieceType.SQUARE);
        TetrisPiece piece3 = new TetrisPiece(Piece.PieceType.STICK);

        board2.nextPiece(piece2, new Point(1, 0));
        board2.setPiece();
        board2.nextPiece(piece2, new Point(3, 0));
        board2.setPiece();
        board2.nextPiece(piece2, new Point(5, 0));
        board2.setPiece();
        board2.nextPiece(piece2, new Point(7, 0));
        board2.setPiece();
        board2.nextPiece(piece2, new Point(1, 2));
        board2.setPiece();
        board2.nextPiece(piece2, new Point(3, 2));
        board2.setPiece();
        board2.nextPiece(piece2, new Point(5, 2));
        board2.setPiece();
        board2.nextPiece(piece2, new Point(7, 2));
        board2.setPiece();

        board2.nextPiece(piece3, new Point(5, 16));

        Assert.assertEquals(ourBrain.nextMove(board2), Board.Action.RIGHT);

        board2.move(ourBrain.nextMove(board2));

        Assert.assertEquals(ourBrain.nextMove(board2), Board.Action.CLOCKWISE);

        board2.move(ourBrain.nextMove(board2));

        Assert.assertEquals(ourBrain.nextMove(board2), Board.Action.RIGHT);

        board2.move(ourBrain.nextMove(board2));

        Assert.assertEquals(ourBrain.nextMove(board2), Board.Action.DROP);


        board2.move(ourBrain.nextMove(board2));
        board2.setPiece();

        board2.nextPiece(piece3, new Point(1, 16));

        Assert.assertEquals(ourBrain.nextMove(board2), Board.Action.LEFT);

        board2.move(ourBrain.nextMove(board2));

        Assert.assertEquals(ourBrain.nextMove(board2), Board.Action.CLOCKWISE);

        board2.move(ourBrain.nextMove(board2));

        Assert.assertEquals(ourBrain.nextMove(board2), Board.Action.LEFT);

        board2.move(ourBrain.nextMove(board2));

        Assert.assertEquals(ourBrain.nextMove(board2), Board.Action.LEFT);

        board2.move(ourBrain.nextMove(board2));

        Assert.assertEquals(ourBrain.nextMove(board2), Board.Action.DROP);

        board2.move(ourBrain.nextMove(board2));

    }
}
