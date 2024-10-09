package assignment;
import java.awt.Point;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a Tetris board -- essentially a 2D grid of piece types (or nulls). Supports
 * tetris pieces and row clearing.  Does not do any drawing or have any idea of
 * pixels. Instead, just represents the abstract 2D board.
 */
public final class TetrisBoard implements Board {

    // JTetris will use this constructor
    public Piece.PieceType[][] board;
    public Piece piece;
    public int refX;
    public int refY;
    public int rowsCleared;
    public Result lastResult;
    public Action lastAction;
    public TetrisBoard(int width, int height) {
        this.board = new Piece.PieceType[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                this.board[row][col] = null;
            }
        }
    }


    public boolean moveValid(int move, boolean down) {
        Point[] absolutePoints = getAbsolutePoints(this.piece);
        if (!down) {
            for (int i = 0; i < absolutePoints.length; i++) {
                if (absolutePoints[i].x+move < 0 || absolutePoints[i].x+move >= this.getWidth() || this.board[absolutePoints[i].y][absolutePoints[i].x+move] != null) {
                    return false;
                }
            }
            return true;
        }
        else {
            for (int i = 0; i < absolutePoints.length; i++) {
                if (absolutePoints[i].y-1 < 0|| this.board[absolutePoints[i].y-1][absolutePoints[i].x] != null) {
                    return false;
                }
            }
            return true;
        }
    }
    public int getX(int x) {
        return this.refX + x;
    }
    public int getY(int y) {

        return this.refY + y;
    }

    public boolean rotateValid(boolean clockwise, boolean isIShape) {
        Point[] kickArray;
        Piece rotatedPiece;

        if (clockwise == true) {
            rotatedPiece =this.piece.clockwisePiece();
            if (isIShape == true) {
                kickArray = Piece.I_CLOCKWISE_WALL_KICKS[rotatedPiece.getRotationIndex()];
            }
            else {
                kickArray = Piece.NORMAL_CLOCKWISE_WALL_KICKS[rotatedPiece.getRotationIndex()];
            }
        }
        else {
            rotatedPiece =this.piece.counterclockwisePiece();
            if (isIShape == true) {
                kickArray = Piece.I_COUNTERCLOCKWISE_WALL_KICKS[rotatedPiece.getRotationIndex()];
            }
            else {
                kickArray = Piece.NORMAL_COUNTERCLOCKWISE_WALL_KICKS[rotatedPiece.getRotationIndex()];
            }
        }
        Point kicker;
        int pointsChecked;
        Point[] absolutePoints = getAbsolutePoints(rotatedPiece);
        for (int i = 0; i < kickArray.length; i++) {
            kicker = kickArray[i];
            pointsChecked = 0;
            for (int j = 0; j < absolutePoints.length; j++) {
                //we have to check for out of bounds or piece already there
                if (absolutePoints[j].x+kicker.x < 0 || absolutePoints[j].x+kicker.x >= this.getWidth() || absolutePoints[j].y+kicker.y < 0 || absolutePoints[j].y+kicker.y >= this.getHeight() || this.board[absolutePoints[j].y+kicker.y][absolutePoints[j].x+kicker.x] != null) {
                    break;
                }
                pointsChecked++;
            }
            if (pointsChecked == absolutePoints.length) {
                this.refX += kicker.x;
                this.refY += kicker.y;
                return true;
            }
        }

        return false;
    }

    public Point[] getAbsolutePoints(Piece piecer) {
        Point[] points = new Point[4];
        for (int i = 0; i < 4; i++) {
            points[i] = new Point(getX(piecer.getBody()[i].x), getY(piecer.getBody()[i].y));
        }
        return points;
    }
    public int[] getAbsoluteSkirt(Point[] absolutePoints) {
        int[] skirt = new int[this.piece.getWidth()];
        for (int i = 0; i < this.piece.getWidth(); i++) {
            skirt[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < absolutePoints.length; i++) {
            skirt[absolutePoints[i].x-this.refX] = Math.min(skirt[absolutePoints[i].x-this.refX], absolutePoints[i].y);
        }
        return skirt;
    }
    public void setPiece() {
        Point[] absolutePoints = getAbsolutePoints(this.piece);
        for (int i = 0; i < absolutePoints.length; i++) {
            this.board[absolutePoints[i].y][absolutePoints[i].x] = this.piece.getType();
        }
    }

    public int drop(){
        Point[] absolutePoints = getAbsolutePoints(this.piece);
        int[] skirt = this.getAbsoluteSkirt(absolutePoints);
        int finalDiff = this.getHeight();
        int currHeight;
        for (int i = 0; i < skirt.length; i++) {
            if (skirt[i] == Integer.MAX_VALUE) {
                continue;
            }
            currHeight = skirt[i]-1;
            while (currHeight >= 0 && this.board[currHeight][this.refX+i] == null) {
                currHeight--;
            }
            currHeight++;
            finalDiff = Math.min(skirt[i]-currHeight, finalDiff);
        }
        return finalDiff;
    }

    public void clearRows() {
        ArrayList<Integer> clearRows = new ArrayList<>();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] == null) {
                    break;
                }
                if (j == this.board[i].length-1) {
                    clearRows.add(i);
                }
            }
        }
        rowsCleared = clearRows.size();
        if (clearRows.size() == 0) {
            return;
        }
        int clearRowsPointer = 0;
        for (int i = clearRows.get(clearRowsPointer); i < this.board.length; i++) {
            if (clearRowsPointer < clearRows.size() && i == clearRows.get(clearRowsPointer)) {
                for (int j = 0; j < this.board[i].length; j++) {
                    this.board[i][j] = null;
                }
                clearRowsPointer++;
            }
            else{
                for (int j = 0; j < this.board[i].length; j++) {
                    this.board[i-clearRowsPointer][j] = this.board[i][j];
                    this.board[i][j] = null;
                }
            }
        }
    }

    @Override
    public Result move(Action act) {
        if (act == Board.Action.LEFT) {
            lastAction = Board.Action.LEFT;
            if (moveValid(-1, false)) {
                this.refX -= 1;
                lastResult = Board.Result.SUCCESS;
                return Board.Result.SUCCESS;
            }
            lastResult = Board.Result.OUT_BOUNDS;
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.RIGHT) {
            lastAction = Board.Action.RIGHT;
            if (moveValid(1, false)) {
                this.refX += 1;
                lastResult = Board.Result.SUCCESS;
                return Board.Result.SUCCESS;
            }
            lastResult = Board.Result.OUT_BOUNDS;
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.DOWN) {
            lastAction = Board.Action.DOWN;
            if (moveValid(-1, true)) {
                this.refY -= 1;
                lastResult = Board.Result.SUCCESS;
                return Board.Result.SUCCESS;
            }
            setPiece();
            clearRows();
            lastResult = Board.Result.PLACE;
            return Board.Result.PLACE;
        }
        //IMPLEMENT ROTATIONS
        else if (act == Board.Action.CLOCKWISE) {
            lastAction = Board.Action.CLOCKWISE;
            if (rotateValid(true, piece.getType() == Piece.PieceType.STICK)) {
                this.piece = this.piece.clockwisePiece();
                lastResult = Board.Result.SUCCESS;
                return Board.Result.SUCCESS;
            }
            lastResult = Board.Result.OUT_BOUNDS;
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.COUNTERCLOCKWISE) {
            lastAction = Board.Action.COUNTERCLOCKWISE;

            if (rotateValid(false, piece.getType() == Piece.PieceType.STICK)) {
                this.piece = this.piece.counterclockwisePiece();
                lastResult = Board.Result.SUCCESS;
                return Board.Result.SUCCESS;
            }
            lastResult = Board.Result.OUT_BOUNDS;
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.DROP) {
            lastAction = Board.Action.DROP;
            int dropAmount = drop();
            this.refY -= dropAmount;
            lastResult = Board.Result.SUCCESS;
            return Board.Result.SUCCESS;
        }
        else if (act == Board.Action.NOTHING){
            lastAction = Board.Action.NOTHING;
            lastResult = Board.Result.SUCCESS;
            return Board.Result.SUCCESS;
        }
        lastAction = null;
        lastResult = Board.Result.NO_PIECE;
        return Board.Result.NO_PIECE;
    }

    @Override
    public Board testMove(Action act) {
        TetrisBoard testBoard = new TetrisBoard(this.getWidth(), this.getHeight());
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = 0; col < this.getWidth(); col++) {
                testBoard.board[row][col] = this.board[row][col];
            }
        }
        testBoard.move(act);
        return testBoard;
    }

    @Override
    public Piece getCurrentPiece() { return this.piece; }

    @Override
    public Point getCurrentPiecePosition() { return new Point(this.refX, this.refY); }

    @Override
    public void nextPiece(Piece p, Point spawnPosition) {
        this.refX = spawnPosition.x;
        this.refY = spawnPosition.y;
        for (int i = 0; i < p.getBody().length; i++) {
            if (getX(p.getBody()[i].x) >= this.getWidth() || p.getBody()[i].x < 0 || getY(p.getBody()[i].y) >= this.board.length || getY(p.getBody()[i].y) < 0 || this.board[getY(p.getBody()[i].y)][getX(p.getBody()[i].x)] != null) {
                throw new IllegalArgumentException();
            }
        }
        this.piece = p;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Board)) {
            return false;
        }
        Board newBoard = (Board) other;
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = 0; col < this.getWidth(); col++) {
                if (this.board[row][col] != newBoard.getGrid(col,row)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Result getLastResult() { return lastResult; }

    @Override
    public Action getLastAction() { return lastAction;}

    @Override
    public int getRowsCleared() {return this.rowsCleared;}

    @Override
    public int getWidth() { return this.board[0].length; }

    @Override
    public int getHeight() { return this.board.length; }

    @Override
    public int getMaxHeight() {
        int maxHeight = 0;
        for (int row = 0; row < this.getWidth(); row++) {
            maxHeight = Math.max(this.getColumnHeight(row), maxHeight);
        }
        return maxHeight;
    }

    @Override
    public int dropHeight(Piece piece, int x) {
        Point[] absolutePoints = getAbsolutePoints(this.piece);
        int[] skirt = this.getAbsoluteSkirt(absolutePoints);
        int finalDiff = this.getHeight();
        int currHeight;
        for (int i = 0; i < skirt.length; i++) {
            if (skirt[i] == Integer.MAX_VALUE) {
                continue;
            }
            currHeight = skirt[i]-1;
            while (currHeight >= 0 && this.board[currHeight][x] == null) {
                currHeight--;
            }
            currHeight++;
            finalDiff = Math.min(skirt[i]-currHeight, finalDiff);
        }
        return skirt[x-this.refX]-finalDiff;
    }

    @Override
    public int getColumnHeight(int x) {
        for (int i = 0; i < this.getHeight(); i++) {
            if (this.board[i][x] == null) {
                return i;
            }
        }
        return this.getHeight();
    }

    @Override
    public int getRowWidth(int y) {
        int takenUp = 0;
        for (int i = 0; i < this.getWidth(); i++) {
            if (this.board[y][i] != null) {
                takenUp++;
            }
        }
        return takenUp;
    }

    @Override
    public Piece.PieceType getGrid(int x, int y) { return this.board[y][x]; }
}
