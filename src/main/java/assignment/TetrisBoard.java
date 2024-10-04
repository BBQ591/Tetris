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
    public Point[] piece;
    public Piece.PieceType pieceType;
    public TetrisBoard(int width, int height) {
        this.board = new Piece.PieceType[height][width];
        System.out.println(height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                this.board[row][col] = null;
            }
        }
    }
    public boolean moveValid(int move, boolean down) {
        if (down == true) {
            for (int i = 0; i < this.piece.length; i++) {
                if (this.board[this.piece[i].y-1][this.piece[i].x] != null) {
                    return false;
                }
            }
            return true;
        }
        else {
            for (int i = 0; i < this.piece.length; i++) {
                if (this.board[this.piece[i].y][this.piece[i].x+move] != null) {
                    return false;
                }
                return true;
            }
        }
    }
    public void erasePiece() {
        for (int i = 0; i < this.piece.length; i++) {
            this.board[this.piece[i].y][this.piece[i].x] = null;
        }
    }
    public void movePiece(int move, boolean down) {
        if (down == true) {
            for (int i = 0; i < this.piece.length; i++) {
                this.piece[i].y -= 1;
                this.board[this.piece[i].y][this.piece[i].x] = this.pieceType;
            }
        }
        else {
            for (int i = 0; i < this.piece.length; i++) {
                this.piece[i].x += move;
                this.board[this.piece[i].y][this.piece[i].x] = this.pieceType;
            }
        }
    }
    @Override
    public Result move(Action act) {
        if (act = Board.Action.LEFT) {
            if (moveValid(-1, false)) {
                erasePiece();
                movePiece(-1, false);
                return Board.RESULT.SUCCESS;
            }
            return Board.RESULT.OUT_BOUNDS;
        }
        else if (act = Board.Action.RIGHT) {
            if (moveValid(1, false)) {
                erasePiece();
                movePiece(1, false);
                return Board.RESULT.SUCCESS;
            }
            return Board.RESULT.OUT_BOUNDS;
        }
        else if (act = Board.Action.DOWN) {
            if (moveValid(-1, true)) {
                erasePiece();
                movePiece(-1, true);
                return Board.RESULT.SUCCESS;
            }
            return Board.RESULT.OUT_BOUNDS;
        }
        //IMPLEMENT ROTATIONS
        return Result.NO_PIECE;
    }

    @Override
    public Board testMove(Action act) { return null; }

    @Override
    public Piece getCurrentPiece() { return null; }

    @Override
    public Point getCurrentPiecePosition() { return null; }

    @Override
    public void nextPiece(Piece p, Point spawnPosition) {
        System.out.println(p.getType() == Piece.PieceType.SQUARE);
        System.out.println(spawnPosition);
        this.pieceType = p.getType();
        int refX = spawnPosition.x;
        int refY = spawnPosition.y;
        for (int i = 0; i < p.getBody().length; i++) {
            //we dont have to check refX and refY < 0 here
            System.out.println(""+(this.getHeight()-1 - refY)+" "+(this.getHeight()-1 - refY + (-p.getBody()[i].y)));
            if (refX + p.getBody()[i].x >= this.board[0].length || refX < 0 || this.getHeight()-1 - refY >= this.board.length || this.getHeight()-1 - refY + (-p.getBody()[i].y) < 0 || this.board[refY+p.getBody()[i].y][refX + p.getBody()[i].x] != null) {
                throw new IllegalArgumentException();
            }
        }
        this.piece = new Point[4];
        for (int i = 0; i < p.getBody().length; i++) {
            this.piece[i] = new Point(this.getHeight()-1 - refY + (-p.getBody()[i].y), refX + p.getBody()[i].x);
            this.board[this.getHeight()-1 - refY + (-p.getBody()[i].y)][refX + p.getBody()[i].x] = p.getType();
        }
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
