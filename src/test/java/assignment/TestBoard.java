package assignment;


import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.awt.*;
import java.util.Arrays;

public class TestBoard {
    @Test
    public void testMove(){
        TetrisBoard testBoard = new TetrisBoard(10,20);
        TetrisPiece testPiece = new TetrisPiece(Piece.PieceType.STICK);
        TetrisPiece testPiece2 = new TetrisPiece(Piece.PieceType.T);

        testBoard.nextPiece(testPiece, new Point(5,5));
        testBoard.move(Board.Action.LEFT);

        Assert.assertEquals(testBoard.getAbsolutePoints(testBoard.getCurrentPiece()), new Point[]{new Point(4,7), new Point(5,7), new Point(6,7), new Point(7,7)});

        testBoard.move(Board.Action.RIGHT);

        Assert.assertEquals(testBoard.getAbsolutePoints(testBoard.getCurrentPiece()), new Point[]{new Point(5,7), new Point(6,7), new Point(7,7), new Point(8,7)});

        testBoard.move(Board.Action.DOWN);

        Assert.assertEquals(testBoard.getAbsolutePoints(testBoard.getCurrentPiece()), new Point[]{new Point(5,6), new Point(6,6), new Point(7,6), new Point(8,6)});

        testBoard.move(Board.Action.DROP);

        Assert.assertEquals(testBoard.getAbsolutePoints(testBoard.getCurrentPiece()), new Point[]{new Point(5,0), new Point(6,0), new Point(7,0), new Point(8,0)});

        testBoard.nextPiece(testPiece2, new Point(5,5));

        testBoard.move(Board.Action.CLOCKWISE);

        Assert.assertEquals(testBoard.getAbsolutePoints(testBoard.getCurrentPiece()), new Point[]{new Point(6,5), new Point(6,6), new Point(6,7), new Point(7,6)});

        testBoard.move(Board.Action.COUNTERCLOCKWISE);

        Assert.assertEquals(testBoard.getAbsolutePoints(testBoard.getCurrentPiece()), new Point[]{new Point(5,6), new Point(6,6), new Point(7,6), new Point(6,7)});
    }
    @Test
    public void testWallKicks() {
        TetrisBoard tetrisBoard = new TetrisBoard(10,20);
        Piece testStick = new TetrisPiece(Piece.PieceType.STICK);
        Piece testSquare = new TetrisPiece(Piece.PieceType.SQUARE);
        Piece testT = new TetrisPiece(Piece.PieceType.T);

        tetrisBoard.nextPiece(testSquare, new Point(1, 0));
        tetrisBoard.setPiece();
        tetrisBoard.nextPiece(testSquare, new Point(1, 2));
        tetrisBoard.setPiece();
        tetrisBoard.nextPiece(testSquare, new Point(1, 4));
        tetrisBoard.setPiece();
        tetrisBoard.nextPiece(testSquare, new Point(1, 6));
        tetrisBoard.setPiece();

        tetrisBoard.nextPiece(testStick.counterclockwisePiece(), new Point(0,9));
        tetrisBoard.move(Board.Action.LEFT);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.CLOCKWISE);


        Assert.assertEquals(tetrisBoard.getAbsolutePoints(tetrisBoard.getCurrentPiece()), new Point[] {new Point(0,1), new Point(0,2), new Point(0,3), new Point(0,4)});

        tetrisBoard = new TetrisBoard(10,20);

        tetrisBoard.nextPiece(testSquare, new Point(1, 0));
        tetrisBoard.setPiece();
        tetrisBoard.nextPiece(testSquare, new Point(1, 2));
        tetrisBoard.setPiece();
        tetrisBoard.nextPiece(testSquare, new Point(1, 4));
        tetrisBoard.setPiece();

        tetrisBoard.nextPiece(testStick.counterclockwisePiece(), new Point(0,7));
        tetrisBoard.move(Board.Action.LEFT);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.CLOCKWISE);

        Assert.assertEquals(tetrisBoard.getAbsolutePoints(tetrisBoard.getCurrentPiece()), new Point[] {new Point(0,7), new Point(1,7), new Point(2,7), new Point(3,7)});

        tetrisBoard = new TetrisBoard(10,20);

        tetrisBoard.nextPiece(testSquare, new Point(1, 0));
        tetrisBoard.setPiece();
        tetrisBoard.nextPiece(testSquare, new Point(1, 2));
        tetrisBoard.setPiece();
        tetrisBoard.nextPiece(testSquare, new Point(1, 4));
        tetrisBoard.setPiece();

        tetrisBoard.nextPiece(testStick.counterclockwisePiece(), new Point(0,7));
        tetrisBoard.move(Board.Action.LEFT);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.DOWN);
        tetrisBoard.move(Board.Action.COUNTERCLOCKWISE);

        Assert.assertEquals(tetrisBoard.getAbsolutePoints(tetrisBoard.getCurrentPiece()), new Point[] {new Point(0,3), new Point(0,4), new Point(0,5), new Point(0,6)});

        tetrisBoard = new TetrisBoard(10,20);

        tetrisBoard.nextPiece(testT.counterclockwisePiece(), new Point(7,5));
        tetrisBoard.move(Board.Action.RIGHT);
        tetrisBoard.move(Board.Action.CLOCKWISE);

        Assert.assertEquals(tetrisBoard.getAbsolutePoints(tetrisBoard.getCurrentPiece()), new Point[] {new Point(7,6), new Point(8,6), new Point(9,6), new Point(8,7)});

        tetrisBoard = new TetrisBoard(10,20);

        tetrisBoard.nextPiece(testT.clockwisePiece(), new Point(0,5));
        tetrisBoard.move(Board.Action.LEFT);
        tetrisBoard.move(Board.Action.COUNTERCLOCKWISE);

        Assert.assertEquals(tetrisBoard.getAbsolutePoints(tetrisBoard.getCurrentPiece()), new Point[] {new Point(0,6), new Point(1,6), new Point(2,6), new Point(1,7)});
    }

    @Test
    public void testDropHeight(){
        TetrisBoard testBoard = new TetrisBoard(10,20);
        TetrisPiece testPiece = new TetrisPiece(Piece.PieceType.SQUARE);

        testBoard.nextPiece(testPiece, new Point(5,10));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.dropHeight(testPiece, 5), 0);

        testBoard.nextPiece(testPiece, new Point(4,15));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.dropHeight(testPiece, 4), 12);

        testBoard.nextPiece(testPiece, new Point(4,18));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.dropHeight(testPiece, 4), 17);

    }
    @Test
    public void testTestMove(){
        TetrisBoard testBoard = new TetrisBoard(10,20);
        TetrisBoard testBoard2 = new TetrisBoard(10,20);
        TetrisBoard testBoard3 = new TetrisBoard(10,20);
        TetrisPiece testPiece = new TetrisPiece(Piece.PieceType.SQUARE);

        testBoard.nextPiece(testPiece, new Point(5,5));
        testBoard.move(Board.Action.LEFT);
        testBoard.setPiece();

        testBoard2.nextPiece(testPiece, new Point(5,5));

        testBoard2 = (TetrisBoard) testBoard2.testMove(Board.Action.LEFT);
        testBoard2.setPiece();

        Assert.assertTrue(testBoard.equals(testBoard2));

        testBoard3.nextPiece(testPiece, new Point(5,5));
        testBoard3 = (TetrisBoard) testBoard3.testMove(Board.Action.RIGHT);
        testBoard3.setPiece();

        Assert.assertFalse(testBoard.equals(testBoard3));
    }


    @Test
    public void testGetCurrentPiece() {
        TetrisBoard testBoard = new TetrisBoard(10,20);
        Piece testPiece = new TetrisPiece(Piece.PieceType.STICK);
        Piece testPiece2 = new TetrisPiece(Piece.PieceType.SQUARE);

        testBoard.nextPiece(testPiece, new Point(0,0));
        testBoard.setPiece();

        Assert.assertTrue(testBoard.getCurrentPiece().equals(testPiece));

        testBoard.nextPiece(testPiece2, new Point(6,0));
        testBoard.setPiece();

        Assert.assertTrue(testBoard.getCurrentPiece().equals(testPiece2));

    }


    @Test
    public void testGetCurrentPiecePosition(){
        TetrisBoard testBoard = new TetrisBoard(10,20);
        Piece testPiece = new TetrisPiece(Piece.PieceType.STICK);
        Piece testPiece2 = new TetrisPiece(Piece.PieceType.SQUARE);

        testBoard.nextPiece(testPiece, new Point(0,0));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getCurrentPiecePosition(), new Point(0,0));

        testBoard.nextPiece(testPiece2, new Point(5,5));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getCurrentPiecePosition(), new Point(5,5));

    }

    @Test
    public void testNextPiece() {
        TetrisBoard testBoard = new TetrisBoard(10,20);
        Piece testPiece = new TetrisPiece(Piece.PieceType.STICK);
        Piece testPiece2 = new TetrisPiece(Piece.PieceType.SQUARE);

        testBoard.nextPiece(testPiece, new Point(5,5));
        testBoard.setPiece();

        Assert.assertTrue(testBoard.getCurrentPiece().equals(testPiece));
        Assert.assertEquals(testBoard.getGrid(5,7), Piece.PieceType.STICK);

        testBoard.nextPiece(testPiece2, new Point(0,0));
        testBoard.setPiece();

        Assert.assertTrue(testBoard.getCurrentPiece().equals(testPiece2));
        Assert.assertEquals(testBoard.getGrid(0,0), Piece.PieceType.SQUARE);
    }

    @Test
    public void testEquals() {
        TetrisBoard testBoard = new TetrisBoard(10,20);
        TetrisBoard testBoard2 = new TetrisBoard(10,20);
        TetrisBoard testBoard3 = new TetrisBoard(15,5);
        Piece testPiece = new TetrisPiece(Piece.PieceType.SQUARE);


        Assert.assertTrue(testBoard.equals(testBoard2));
        Assert.assertFalse(testBoard.equals(testBoard3));

        testBoard.nextPiece(testPiece, new Point(0,0));
        testBoard.setPiece();

        testBoard2.nextPiece(testPiece, new Point(0,0));
        testBoard2.setPiece();

        Assert.assertTrue(testBoard.equals(testBoard2));

        testBoard2.nextPiece(testPiece, new Point(5,0));
        testBoard2.setPiece();

        Assert.assertFalse(testBoard.equals(testBoard2));
    }

    @Test
    public void testEnums(){
        TetrisBoard testBoard = new TetrisBoard(10,20);
        Piece testPiece = new TetrisPiece(Piece.PieceType.SQUARE);

        testBoard.nextPiece(testPiece, new Point(0,0));
//        testBoard.setPiece();
        testBoard.move(Board.Action.LEFT);


        Assert.assertEquals(testBoard.getLastResult(), Board.Result.OUT_BOUNDS);
        Assert.assertEquals(testBoard.getLastAction(), Board.Action.LEFT);

        testBoard.nextPiece(testPiece, new Point(2,0));
//        testBoard.setPiece();
        testBoard.move(Board.Action.RIGHT);

        Assert.assertEquals(testBoard.getLastResult(), Board.Result.SUCCESS);
        Assert.assertEquals(testBoard.getLastAction(), Board.Action.RIGHT);

        testBoard.nextPiece(testPiece, new Point(0,0));
//        testBoard.setPiece();
        testBoard.move(Board.Action.DOWN);

        Assert.assertEquals(testBoard.getLastResult(), Board.Result.PLACE);
        Assert.assertEquals(testBoard.getLastAction(), Board.Action.DOWN);

        testBoard.nextPiece(testPiece, new Point(4,0));
//        testBoard.setPiece();
        testBoard.move(null);

        Assert.assertEquals(testBoard.getLastResult(), Board.Result.NO_PIECE);

        testBoard.nextPiece(testPiece, new Point(4,4));
//        testBoard.setPiece();
        testBoard.move(Board.Action.COUNTERCLOCKWISE);

        Assert.assertEquals(testBoard.getLastAction(), Board.Action.COUNTERCLOCKWISE);

        testBoard.move(Board.Action.CLOCKWISE);

        Assert.assertEquals(testBoard.getLastAction(), Board.Action.CLOCKWISE);

        testBoard.move(Board.Action.DROP);

        Assert.assertEquals(testBoard.getLastAction(), Board.Action.DROP);

    }

    @Test
    public void testGetRowsCleared() {
        TetrisBoard testBoard = new TetrisBoard(10,20);
        Piece testPiece = new TetrisPiece(Piece.PieceType.SQUARE);

        testBoard.nextPiece(testPiece, new Point(0,0));
        testBoard.setPiece();

        testBoard.clearRows();

        Assert.assertEquals(testBoard.getRowsCleared(), 0);

        testBoard.nextPiece(testPiece, new Point(2,1));
        testBoard.setPiece();
        testBoard.nextPiece(testPiece, new Point(4,1));
        testBoard.setPiece();
        testBoard.nextPiece(testPiece, new Point(6,1));
        testBoard.setPiece();
        testBoard.nextPiece(testPiece, new Point(8,1));
        testBoard.setPiece();

        testBoard.clearRows();

        Assert.assertEquals(testBoard.getRowsCleared(), 1);

        Assert.assertEquals(testBoard.getGrid(2,1), Piece.PieceType.SQUARE );
        Assert.assertNull(testBoard.getGrid(2, 0));



    }

    @Test
    public void testDimensions() {
        TetrisBoard testBoard = new TetrisBoard(10,20);
        TetrisBoard testBoard2 = new TetrisBoard(15,5);

        Assert.assertEquals(testBoard.getWidth(), 10);
        Assert.assertEquals(testBoard.getHeight(), 20);

        Assert.assertEquals(testBoard2.getWidth(), 15);
        Assert.assertEquals(testBoard2.getHeight(), 5);

    }

    @Test
    public void testGetMaxHeight() {
        TetrisBoard testBoard = new TetrisBoard(10,20);
        Piece testPiece = new TetrisPiece(Piece.PieceType.SQUARE);
        Piece testPieceStick = new TetrisPiece(Piece.PieceType.STICK);

        Assert.assertEquals(testBoard.getMaxHeight(), 0);

        testBoard.nextPiece(testPiece, new Point(0,0));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getMaxHeight(), 1);

        testBoard.nextPiece(testPiece, new Point(2,0));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getMaxHeight(), 1);

        testBoard.nextPiece(testPiece, new Point(2,2));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getMaxHeight(), 3);

        testBoard.nextPiece(testPiece, new Point(4,1));
        testBoard.setPiece();

        testBoard.nextPiece(testPiece, new Point(6,1));
        testBoard.setPiece();

        testBoard.nextPiece(testPiece, new Point(8,0));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getMaxHeight(), 3);

        testBoard.clearRows();

        Assert.assertEquals(testBoard.getMaxHeight(), 2);

    }

    @Test
    public void testGetColumnHeight(){
        TetrisBoard testBoard = new TetrisBoard(10,20);
        Piece testPiece = new TetrisPiece(Piece.PieceType.SQUARE);

        Assert.assertEquals(testBoard.getColumnHeight(1), 0);

        testBoard.nextPiece(testPiece, new Point(0,0));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getColumnHeight(1), 1);

        testBoard.nextPiece(testPiece, new Point(0,2));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getColumnHeight(1), 3);

        testBoard.nextPiece(testPiece, new Point(2,0));
        testBoard.setPiece();

        testBoard.nextPiece(testPiece, new Point(4,0));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getColumnHeight(5), 1);

        testBoard.nextPiece(testPiece, new Point(6,0));
        testBoard.setPiece();

        testBoard.nextPiece(testPiece, new Point(8,0));
        testBoard.setPiece();

        testBoard.clearRows();

        Assert.assertEquals(testBoard.getColumnHeight(1), 1);

    }

    @Test
    public void testGetRowWidth(){
        TetrisBoard testBoard = new TetrisBoard(10,20);
        Piece testPiece = new TetrisPiece(Piece.PieceType.SQUARE);

        Assert.assertEquals(testBoard.getRowWidth(1), 0);

        testBoard.nextPiece(testPiece, new Point(0,0));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getRowWidth(1), 2);

        testBoard.nextPiece(testPiece, new Point(2,0));
        testBoard.setPiece();

        testBoard.nextPiece(testPiece, new Point(4,0));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getRowWidth(1), 6);

        testBoard.nextPiece(testPiece, new Point(6,0));
        testBoard.setPiece();

        testBoard.nextPiece(testPiece, new Point(8,0));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getRowWidth(1), 10);

        testBoard.clearRows();

        Assert.assertEquals(testBoard.getRowWidth(1), 0);

    }

    @Test
    public void testGetGrid(){
        TetrisBoard testBoard = new TetrisBoard(10,20);
        Piece testPiece = new TetrisPiece(Piece.PieceType.SQUARE);


        Assert.assertNull(testBoard.getGrid(5, 15));

        testBoard.nextPiece(testPiece, new Point(5,15));
        testBoard.setPiece();

        Assert.assertEquals(testBoard.getGrid(5, 15), Piece.PieceType.SQUARE);
        Assert.assertEquals(testBoard.getGrid(6, 16), Piece.PieceType.SQUARE);
        Assert.assertNull(testBoard.getGrid(4, 15));

    }

}

