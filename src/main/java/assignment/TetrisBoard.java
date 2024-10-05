package assignment;
import java.awt.Point;

import java.awt.*;

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
                if (absolutePoints[i].x+move < 0 || absolutePoints[i].x+move >= this.getWidth()) {
                    return false;
                }
            }
            return true;
        }
        else {
            for (int i = 0; i < absolutePoints.length; i++) {
                if (absolutePoints[i].y-1 < 0) {
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

    public void erasePiece() {
        for (int i = 0; i < this.piece.getBody().length; i++) {
//            System.out.println(getX(this.piece.getBody()[i].x));
            this.board[getY(this.piece.getBody()[i].y)][getX(this.piece.getBody()[i].x)] = null;
        }
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
        System.out.println(rotatedPiece.getRotationIndex() + "THIS IS ROTATION INDEX");
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

    @Override
    public Result move(Action act) {
        if (act == Board.Action.LEFT) {
            if (moveValid(-1, false)) {
                this.refX -= 1;
                return Board.Result.SUCCESS;
            }
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.RIGHT) {
            if (moveValid(1, false)) {
//                erasePiece();
                this.refX += 1;
//                this.setPiece();
                return Board.Result.SUCCESS;
            }
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.DOWN) {

            if (moveValid(-1, true)) {
//                erasePiece();
                this.refY -= 1;
                return Board.Result.SUCCESS;
            }
            return Board.Result.OUT_BOUNDS;
        }
        //IMPLEMENT ROTATIONS
        else if (act == Board.Action.CLOCKWISE) {
            System.out.println("clockwise");
            if (rotateValid(true, piece.getType() == Piece.PieceType.STICK)) {
                System.out.println("this is valid");
//                erasePiece();
                this.piece = this.piece.clockwisePiece();
//                setPiece();
                return Board.Result.SUCCESS;
            }
            System.out.println("this is not valid");
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.COUNTERCLOCKWISE) {

            if (rotateValid(false, piece.getType() == Piece.PieceType.STICK)) {
//                erasePiece();
                this.piece = this.piece.counterclockwisePiece();
//                setPiece();
                return Board.Result.SUCCESS;
            }
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.DROP) {

            int dropAmount = drop();
            this.refY -= dropAmount;
//            erasePiece();
//            this.setPiece();
            return Board.Result.SUCCESS;
        }
        else if (act == Board.Action.NOTHING){
            return Board.Result.SUCCESS;
        }
        return Board.Result.NO_PIECE;
    }

    @Override
    public Board testMove(Action act) { return null; }

    @Override
    public Piece getCurrentPiece() { return this.piece; }

    @Override
    public Point getCurrentPiecePosition() { return new Point(this.refX, this.refY); }

    @Override
    public void nextPiece(Piece p, Point spawnPosition) {
        this.piece = p;
        this.refX = spawnPosition.x;
        this.refY = spawnPosition.y;
        for (int i = 0; i < p.getBody().length; i++) {
            //we dont have to check refX and refY < 0 here
//            System.out.println(""+(this.getHeight()-1 - refY)+" "+(this.getHeight()-1 - refY + (-p.getBody()[i].y)));
            if (getX(this.piece.getBody()[i].x) >= this.getWidth() || this.piece.getBody()[i].x < 0 || getY(this.piece.getBody()[i].y) >= this.board.length || this.getY(this.piece.getBody()[i].y) < 0 || this.board[getY(this.piece.getBody()[i].y)][getX(this.piece.getBody()[i].x)] != null) {
                throw new IllegalArgumentException();
            }
        }
//        for (int i = 0; i < this.piece.getBody().length; i++) {
//            //we dont have to check refX and refY < 0 here
////            System.out.println(""+(this.getHeight()-1 - refY)+" "+(this.getHeight()-1 - refY + (-p.getBody()[i].y)));
//            this.board[getY(this.piece.getBody()[i].y)][getX(this.piece.getBody()[i].x)] = this.piece.getType();
//        }
    }

    @Override
    public boolean equals(Object other) { return false; }

    @Override
    public Result getLastResult() { return Result.NO_PIECE; }

    @Override
    public Action getLastAction() { return Action.NOTHING; }

    @Override
    public int getRowsCleared() { return -1; }

    @Override
    public int getWidth() { return this.board[0].length; }

    @Override
    public int getHeight() { return this.board.length; }

    @Override
    public int getMaxHeight() { return -1; }

    @Override
    public int dropHeight(Piece piece, int x) { return -1; }

    @Override
    public int getColumnHeight(int x) { return -1; }

    @Override
    public int getRowWidth(int y) { return -1; }

    @Override
    public Piece.PieceType getGrid(int x, int y) { return null; }
}
