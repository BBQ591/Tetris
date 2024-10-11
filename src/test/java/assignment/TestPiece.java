package assignment;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.Arrays;

public class TestPiece{

    @Test
    public void testWallKicks() {
        TetrisBoard tetrisBoard = new TetrisBoard(10,20);
        Piece testStick = new TetrisPiece(Piece.PieceType.STICK);

        testStick = testStick.counterclockwisePiece();

        tetrisBoard.nextPiece(testStick.clockwisePiece(), new Point(0, 5));
        tetrisBoard.move(Board.Action.LEFT);
        tetrisBoard.setPiece();

        System.out.println(tetrisBoard.getGrid(0,5));

        tetrisBoard.nextPiece(testStick.clockwisePiece(), new Point(5, 5));





    }

    @Test
    public void testGetType(){
        Piece testT = new TetrisPiece(Piece.PieceType.T);
        Piece testLeftL = new TetrisPiece(Piece.PieceType.LEFT_L);
        Piece testRightL = new TetrisPiece(Piece.PieceType.RIGHT_L);
        Piece testLeftDog = new TetrisPiece(Piece.PieceType.LEFT_DOG);
        Piece testRightDog = new TetrisPiece(Piece.PieceType.RIGHT_DOG);
        Piece testStick = new TetrisPiece(Piece.PieceType.STICK);
        Piece testSquare = new TetrisPiece(Piece.PieceType.SQUARE);

        Assert.assertEquals(testT.getType(), Piece.PieceType.T);
        Assert.assertEquals(testLeftL.getType(), Piece.PieceType.LEFT_L);
        Assert.assertEquals(testRightL.getType(), Piece.PieceType.RIGHT_L);
        Assert.assertEquals(testLeftDog.getType(), Piece.PieceType.LEFT_DOG);
        Assert.assertEquals(testRightDog.getType(), Piece.PieceType.RIGHT_DOG);
        Assert.assertEquals(testStick.getType(), Piece.PieceType.STICK);
        Assert.assertEquals(testSquare.getType(), Piece.PieceType.SQUARE);
    }

    @Test
    public void testRotationIndex(){
        Piece testRightDog = new TetrisPiece(Piece.PieceType.RIGHT_DOG);

        Assert.assertEquals(testRightDog.getRotationIndex(), 0);

        testRightDog = testRightDog.clockwisePiece();

        Assert.assertEquals(testRightDog.getRotationIndex(), 1);

        testRightDog = testRightDog.clockwisePiece();

        Assert.assertEquals(testRightDog.getRotationIndex(), 2);

        testRightDog = testRightDog.clockwisePiece();

        Assert.assertEquals(testRightDog.getRotationIndex(), 3);

        testRightDog = testRightDog.clockwisePiece();

        Assert.assertEquals(testRightDog.getRotationIndex(), 0);
    }


    @Test
    public void TestGetWidth() {
        Piece testT = new TetrisPiece(Piece.PieceType.T);
        Piece testLeftL = new TetrisPiece(Piece.PieceType.LEFT_L);
        Piece testRightL = new TetrisPiece(Piece.PieceType.RIGHT_L);
        Piece testLeftDog = new TetrisPiece(Piece.PieceType.LEFT_DOG);
        Piece testRightDog = new TetrisPiece(Piece.PieceType.RIGHT_DOG);
        Piece testStick = new TetrisPiece(Piece.PieceType.STICK);
        Piece testSquare = new TetrisPiece(Piece.PieceType.SQUARE);

        Assert.assertEquals(testT.getWidth(), 3);
        Assert.assertEquals(testLeftL.getWidth(), 3);
        Assert.assertEquals(testRightL.getWidth(), 3);
        Assert.assertEquals(testLeftDog.getWidth(), 3);
        Assert.assertEquals(testRightDog.getWidth(), 3);
        Assert.assertEquals(testStick.getWidth(),4);
        Assert.assertEquals(testSquare.getWidth(), 2);
    }

    @Test
    public void TestGetHeight() {
        Piece testT = new TetrisPiece(Piece.PieceType.T);
        Piece testLeftL = new TetrisPiece(Piece.PieceType.LEFT_L);
        Piece testRightL = new TetrisPiece(Piece.PieceType.RIGHT_L);
        Piece testLeftDog = new TetrisPiece(Piece.PieceType.LEFT_DOG);
        Piece testRightDog = new TetrisPiece(Piece.PieceType.RIGHT_DOG);
        Piece testStick = new TetrisPiece(Piece.PieceType.STICK);
        Piece testSquare = new TetrisPiece(Piece.PieceType.SQUARE);

        Assert.assertEquals(testT.getHeight(), 3);
        Assert.assertEquals(testLeftL.getHeight(), 3);
        Assert.assertEquals(testRightL.getHeight(), 3);
        Assert.assertEquals(testLeftDog.getHeight(), 3);
        Assert.assertEquals(testRightDog.getHeight(), 3);
        Assert.assertEquals(testStick.getHeight(),4);
        Assert.assertEquals(testSquare.getHeight(), 2);
    }

    @Test
    public void TestGetBody(){
        Piece testT = new TetrisPiece(Piece.PieceType.T);
        Piece testSquare = new TetrisPiece(Piece.PieceType.SQUARE);

        Assert.assertEquals(testT.getBody(), new Point[] { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) });
        Assert.assertEquals(testSquare.getBody(), new Point[] { new Point(0, 0), new Point(1, 1), new Point(0, 1), new Point(1, 0) });
    }

    @Test
    public void TestRotations(){
        Piece testStick = new TetrisPiece(Piece.PieceType.STICK);
        Piece testT = new TetrisPiece(Piece.PieceType.T);

        Assert.assertEquals(testT.getBody(), new Point[] { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) });

        testT = testT.counterclockwisePiece();
        Assert.assertEquals(testT.getBody(), new Point[] { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 1) });

        testT = testT.counterclockwisePiece();
        Assert.assertEquals(testT.getBody(), new Point[] { new Point(1, 0), new Point(1, 1), new Point(0, 1), new Point(2, 1) });

        testT = testT.counterclockwisePiece();
        Assert.assertEquals(testT.getBody(),  new Point[] { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 1) });


        Assert.assertEquals(testStick.getBody(), new Point[] { new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2) });

        testStick = testStick.clockwisePiece();
        Assert.assertEquals(testStick.getBody(), new Point[] { new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(2, 3) });

        testStick = testStick.clockwisePiece();
        Assert.assertEquals(testStick.getBody(), new Point[] { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) });

        testStick = testStick.clockwisePiece();
        Assert.assertEquals(testStick.getBody(), new Point[] { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) });

    }

    @Test
    public void TestSkirt(){
        Piece testRightDog = new TetrisPiece(Piece.PieceType.RIGHT_DOG);
        Piece testStick = new TetrisPiece(Piece.PieceType.STICK);
        Piece testSquare = new TetrisPiece(Piece.PieceType.SQUARE);

        int[] stickSkirt = {2,2,2,2};
        Assert.assertTrue(Arrays.equals(testStick.getSkirt(), stickSkirt));

        testStick = testStick.clockwisePiece();
        int[] rotatedStickSkirt = {Integer.MAX_VALUE, Integer.MAX_VALUE, 0, Integer.MAX_VALUE};
        Assert.assertTrue(Arrays.equals(testStick.getSkirt(), rotatedStickSkirt));

        int[] squareSkirt = {0,0};
        Assert.assertTrue(Arrays.equals(testSquare.getSkirt(), squareSkirt));

        testSquare = testSquare.clockwisePiece();
        Assert.assertTrue(Arrays.equals(testSquare.getSkirt(), squareSkirt));

        int[] rightDogSkirt = {1,1,2};
        Assert.assertTrue(Arrays.equals(testRightDog.getSkirt(), rightDogSkirt));

        testRightDog = testRightDog.clockwisePiece();
        int[] rotatedRightDogSkirt = {Integer.MAX_VALUE,1,0};
        Assert.assertTrue(Arrays.equals(testRightDog.getSkirt(), rotatedRightDogSkirt));

    }

    @Test
    public void TestEquals(){
        Piece testT = new TetrisPiece(Piece.PieceType.T);
        Piece testLeftDog = new TetrisPiece(Piece.PieceType.LEFT_DOG);
        Piece testRightDog = new TetrisPiece(Piece.PieceType.RIGHT_DOG);
        Piece testSquare = new TetrisPiece(Piece.PieceType.SQUARE);
        Piece rotatedT = new TetrisPiece(Piece.PieceType.T);

        Assert.assertTrue(testT.equals(rotatedT));

        rotatedT = rotatedT.clockwisePiece();
        Assert.assertFalse(testT.equals(rotatedT));

        Piece rotatedSquare = new TetrisPiece(Piece.PieceType.SQUARE);
        Assert.assertTrue(testSquare.equals(rotatedSquare));

        rotatedSquare = rotatedSquare.clockwisePiece();
        Assert.assertFalse(testSquare.equals(rotatedSquare));

        Assert.assertFalse(testRightDog.equals(testLeftDog));
    }

}

