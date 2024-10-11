package assignment;

import java.awt.*;
import java.util.*;

/**
 * A Lame Brain implementation for JTetris; tries all possible places to put the
 * piece (but ignoring rotations, because we're lame), trying to minimize the
 * total height of pieces on the board.
 */
public class BumbleBeeBrain implements Brain {
    //function that is a part of the interface
    public Board.Action nextMove(Board currentBoard) {
        return enumerateOptions(currentBoard);
    }


    private Board.Action enumerateOptions(Board currentBoard) {
        //global variables below are used to calculate the quality of each possible move on the board
        //represents the current board's umbrella weight
        totalUmbrellas = getUmbrella(currentBoard);
        //represents the current board's max height
        currHeight = currentBoard.getMaxHeight();
        //maxLeft represents the highest score that the piece can create by moving left
        double maxLeft = -Integer.MAX_VALUE;
        //same thing as maxLeft but moving right
        double maxRight = -Integer.MAX_VALUE;
        //currScore stores the board's score after an action
        double currScore;

        //generating all possible resulting boards by the piece moving left
        //searches for hte maximum score that can be obtained by moving the piece left
        //includes rotations
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

        //generating all possible resulting boards by the piece moving right
        //searches for hte maximum score that can be obtained by moving the piece right
        //includes rotations
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

        //here, we are determining if the piece's current position is in the optimal spot
        //currPosIsBestPlace stores the boolean of whether the current position is the best position
        boolean currPosIsBestPlace = false;
        Board currPos = currentBoard.testMove(Board.Action.NOTHING);
        for (int i = 0; i < 4; i++) {
            if (scoreBoard(currPos.testMove(Board.Action.DROP)) >= maxRight && scoreBoard(currPos.testMove(Board.Action.DROP)) >= maxLeft) {
                currPosIsBestPlace = true;
                break;
            }
            currPos.move(Board.Action.CLOCKWISE);
        }

        //this means that we need to move hte piece left or right
        if (!currPosIsBestPlace) {
            if (maxLeft > maxRight) {
                //making sure that the move is valid. if the move is not valid, then we try to rotate
                //if the piece wants to move left but it is invalid, that means it needs to rotate to get to the correct spot
                if (currentBoard.move(Board.Action.LEFT) == Board.Result.OUT_BOUNDS) {
                    return Board.Action.CLOCKWISE;
                }
                if (currentBoard.move(Board.Action.RIGHT) == Board.Result.OUT_BOUNDS) {
                    return Board.Action.CLOCKWISE;
                }
                return Board.Action.LEFT;
            }
            else {
                //making sure that the move is valid. if the move is not valid, then we try to rotate
                //if the piece wants to move left but it is invalid, that means it needs to rotate to get to the correct spot
                if (currentBoard.move(Board.Action.RIGHT) == Board.Result.OUT_BOUNDS) {
                    return Board.Action.CLOCKWISE;
                }
                if (currentBoard.move(Board.Action.LEFT) == Board.Result.OUT_BOUNDS) {
                    return Board.Action.CLOCKWISE;
                }
                return Board.Action.RIGHT;
            }
        }
        //this means that the piece's current x-y position is optimal. However, we don't know if the rotation is optimal
        else {
            //looking for the rotationIndex that is optimal
            currPos = currentBoard.testMove(Board.Action.NOTHING);
            double rot1Score = scoreBoard(currPos.testMove(Board.Action.DROP));
            //isntRot1Max stores whether the current rotation is optimal
            boolean isntRot1Max = false;
            for (int i = 0; i < 4; i++) {
                if (scoreBoard(currPos.testMove(Board.Action.DROP)) > rot1Score) {
                    isntRot1Max = true;
                    break;
                }
                currPos.move(Board.Action.CLOCKWISE);
            }
            if (isntRot1Max) {
                //the current rotation is not optimal
                return Board.Action.CLOCKWISE;
            }
            else {
                //the current rotation is optimal
                return Board.Action.DROP;
            }
        }
    }


    //these global variables store the state of the input board to help calculate score
    public static int currHeight;
    public static double totalUmbrellas;

    //calculating the number of umbrellas given a board
    public double getUmbrella(Board newBoard) {
        double umbrellas = 0;
        int index;
        double add;
        for (int i = 0; i < newBoard.getWidth(); i++) {
            for (int j = 0; j < newBoard.getHeight(); j++) {
                //the current point is an umbrella point. It's value is null and the piece above it is another piece
                if (newBoard.getGrid(i,j) == null && j + 1 < newBoard.getHeight() && newBoard.getGrid(i,j+1) != null) {
                    index = j;
                    add = 32;
                    //looping down to find the total space that is a part of this umbrella
                    while (index >= 0 && newBoard.getGrid(i,index) == null) {
                        umbrellas += add;
                        //we multiply to heavily punish a piece if it covers a big, deep opening
                        add*=4;
                        index --;
                    }
                }
            }
        }
        return umbrellas;
    }
    private double scoreBoard(Board newBoard) {
        //if the placement of hte new piece ends the game, then we don't want to place the piece there
        if (newBoard.getMaxHeight() >= newBoard.getHeight()-4) {
            return -Integer.MAX_VALUE;
        }
        //weights/score calculation
        return -(getUmbrella(newBoard) - totalUmbrellas)- 0.25*averageDiffHeights(newBoard);
    }

    public static double averageDiffHeights(Board board) {
        double sum = 0;
        for (int i = 0; i < board.getWidth()-1; i++) {
            //calculating difference between adjacent column heights
            sum += Math.pow(Math.abs(board.getColumnHeight(i)-board.getColumnHeight(i+1)),5);
        }
        sum /= board.getWidth();
        return sum;
    }



}
