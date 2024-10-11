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
        currEmpty = 0;
        for (int i = 0; i < currentBoard.getMaxHeight(); i++) {
            currEmpty += (currentBoard.getWidth()-currentBoard.getRowWidth(i))*i;
        }
        currHoles = numIslands(currentBoard);
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
    public static int currHoles;
    private int scoreBoard(Board newBoard) {
        int empty = 0;
        for (int i = 0; i < newBoard.getMaxHeight(); i++) {
            empty += (newBoard.getWidth()-newBoard.getRowWidth(i))*i;
        }
        System.out.println(totalUmbrellas);
        return newBoard.getRowsCleared()-75*((int)Math.pow(getMaxPieceHeight(newBoard), 2))-10*(numIslands(newBoard)-currHoles)-1000*(getUmbrella(newBoard) - totalUmbrellas);
    }

    // Dimensions of the grid
    private static final int[] dx = {-1, 1, 0, 0}; // row movements (up, down)
    private static final int[] dy = {0, 0, -1, 1}; // column movements (left, right)
    private static Set<Point> visited;
    // Function to count the number of islands
    public static int numIslands(Board grid) {
        if (grid == null || grid.getWidth() == 0) {
            return 0;
        }
        visited = new HashSet<>();
        int numIslands = 0;
        int rows = grid.getHeight();
        int cols = grid.getWidth();

        // Traverse each cell in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // If we encounter land (i.e., '1'), we start a DFS to mark the entire island
                if (grid.getGrid(j,i) == null && !visited.contains(new Point(i, j))) {
                    numIslands++; // New island found
                    floodFill(grid, i, j); // Mark the entire island
                }
            }
        }

        return numIslands;
    }

    // DFS-based Flood Fill to mark all parts of the current island
    private static void floodFill(Board grid, int x, int y) {
        // Check boundaries and if current cell is not land ('1'), return
        if (x < 0 || x >= grid.getHeight() || y < 0 || y >= grid.getWidth() || visited.contains(new Point(x, y)) || grid.getGrid(y,x) != null) {
            return;
        }

        // Mark the current cell as visited by setting it to '0' (water)
        visited.add(new Point(x, y));

        // Explore all 4 possible directions (up, down, left, right)
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            floodFill(grid, newX, newY);
        }
    }

    // Example usage
    public int getNumIslands(Board board) {
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        return numIslands(board); // Output: 3
    }

}
