package assignment;

import java.awt.*;
import java.util.*;

/**
 * A Lame Brain implementation for JTetris; tries all possible places to put the
 * piece (but ignoring rotations, because we're lame), trying to minimize the
 * total height of pieces on the board.
 */
public class BumbleBeeBrain implements Brain {

    private ArrayList<Board> options;
    private ArrayList<Board.Action> firstMoves;

    /**
     * Decide what the next move should be based on the state of the board.
     */
    public Board.Action nextMove(Board currentBoard) {
        return enumerateOptions(currentBoard);
    }

    /**
     * Test all of the places we can put the current Piece.
     * Since this is just a Lame Brain, we aren't going to do smart
     * things like rotating pieces.
     */
    private Board.Action enumerateOptions(Board currentBoard) {
        totalUmbrellas = getUmbrella(currentBoard);
        currHeight = currentBoard.getMaxHeight();
        int maxLeft = -Integer.MAX_VALUE;
        int maxRight = -Integer.MAX_VALUE;
        int currScore;
        Board left = currentBoard.testMove(Board.Action.LEFT);
        for (int i = 0; i < 4; i++) {
            while (left.getLastResult() == Board.Result.SUCCESS) {
                currScore = scoreBoard(left.testMove(Board.Action.DROP));
                if (currScore > maxLeft) {
                    maxLeft = currScore;
                }
                left.move(Board.Action.LEFT);
            }
            left = currentBoard.testMove(Board.Action.LEFT);
            for (int j = 0; j < i; j++) {
                left.move(Board.Action.CLOCKWISE);
            }
        }
        Board right = currentBoard.testMove(Board.Action.RIGHT);
        for (int i = 0; i < 4; i++) {
            while (right.getLastResult() == Board.Result.SUCCESS) {
                currScore = scoreBoard(right.testMove(Board.Action.DROP));
                if (currScore > maxRight) {
                    maxRight = currScore;
                }
                right = right.testMove(Board.Action.RIGHT);
            }
            right = right.testMove(Board.Action.RIGHT);
            for (int j = 0; j < i; j++) {
                right.move(Board.Action.CLOCKWISE);
            }
        }

        //starting on calculating current score
        boolean currPosIsBestPlace = false;
        Board currPos = currentBoard.testMove(Board.Action.NOTHING);
        for (int i = 0; i < 4; i++) {
            if (scoreBoard(currPos.testMove(Board.Action.DROP)) >= maxRight && scoreBoard(currPos.testMove(Board.Action.DROP)) >= maxLeft) {
                currPosIsBestPlace = true;
                break;
            }
            currPos.move(Board.Action.CLOCKWISE);
        }
        if (!currPosIsBestPlace) {
            if (maxLeft > maxRight) {
                if (currentBoard.move(Board.Action.LEFT) == Board.Result.OUT_BOUNDS) {
                    return Board.Action.CLOCKWISE;
                }
                if (currentBoard.move(Board.Action.RIGHT) == Board.Result.OUT_BOUNDS) {
                    return Board.Action.CLOCKWISE;
                }
                return Board.Action.LEFT;
            }
            else {
                if (currentBoard.move(Board.Action.RIGHT) == Board.Result.OUT_BOUNDS) {
                    return Board.Action.CLOCKWISE;
                }
                if (currentBoard.move(Board.Action.LEFT) == Board.Result.OUT_BOUNDS) {
                    return Board.Action.CLOCKWISE;
                }
                return Board.Action.RIGHT;
            }
        }
        else {
            currPos = currentBoard.testMove(Board.Action.NOTHING);
            int rot1Score = scoreBoard(currPos.testMove(Board.Action.DROP));
            boolean isntRot1Max = false;
            for (int i = 0; i < 4; i++) {
                if (scoreBoard(currPos.testMove(Board.Action.DROP)) > rot1Score) {
                    isntRot1Max = true;
                    break;
                }
                currPos.move(Board.Action.CLOCKWISE);
            }
            if (isntRot1Max) {
                return Board.Action.CLOCKWISE;
            }
            else {
                return Board.Action.DROP;
            }
        }
    }


    /**
     * Since we're trying to avoid building too high,
     * we're going to give higher scores to Boards with
     * MaxHeights close to 0.
     */
    public static int currHeight;
    public static int totalUmbrellas;
    public int getUmbrella(Board newBoard) {
        int umbrellas = 0;
        int index;
        int add;
        for (int i = 0; i < newBoard.getWidth(); i++) {
            for (int j = 0; j < newBoard.getHeight(); j++) {
                if (newBoard.getGrid(i,j) == null && j + 1 < newBoard.getHeight() && newBoard.getGrid(i,j+1) != null) {
                    index = j;
                    add = 1;
                    while (index >= 0 && newBoard.getGrid(i,index) == null) {
                        umbrellas += add;
                        add*=2;
                        index --;
                    }
                }
            }
        }
        return umbrellas;
    }
    public int getMaxPieceHeight(Board newBoard) {
        Point[] absolutePoints = ((TetrisBoard) newBoard).getAbsolutePoints(newBoard.getCurrentPiece());
        int maxPoint = 0;
        for (int i = 0; i < absolutePoints.length; i++) {
            maxPoint = Math.max(maxPoint, absolutePoints[i].y);
        }
        return maxPoint;
    }
    public static int currEmpty;
    private int scoreBoard(Board newBoard) {
        if (newBoard.getMaxHeight() >= newBoard.getHeight()-4) {
            return -Integer.MAX_VALUE;
        }
        return 10*newBoard.getRowsCleared()-50000*(getUmbrella(newBoard) - totalUmbrellas)- 1000*(int)averageDiffHeights(newBoard);
    }

    public static double averageDiffHeights(Board board) {
        double sum = 0;
        for (int i = 0; i < board.getWidth()-1; i++) {
            sum += Math.pow(Math.abs(board.getColumnHeight(i)-board.getColumnHeight(i+1)),4);
        }
        sum /= board.getWidth();
        return sum;
    }



}
