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

    public Piece.PieceType[][] board;
    public Piece piece;
    //refX and refY store the piece's bottom left corner of the border box as coordinates on the this.board
    public int refX;
    public int refY;
    //rows cleared last
    public int rowsCleared;
    //previous result
    public Result lastResult;
    //previous action
    public Action lastAction;
    public TetrisBoard(int width, int height) {
        //error checking when creating tetrisboard
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        //initializing board
        this.board = new Piece.PieceType[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                this.board[row][col] = null;
            }
        }
    }

    //this function checks if down, left, or right move is valid. This means checking if the move is in bounds
    //the variable move is set to +1 when the command is right and -1 when the command is left
    //boolean down determines if the command is down
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
    //helper function to convert a given point of the piece.getBody() and convert it to the board's coordinates rather than the relative position in the bounding box
    public int getX(int x) {
        return this.refX + x;
    }
    public int getY(int y) {

        return this.refY + y;
    }
    //checks if the rotation is valid. this includes wall kicks
    public boolean rotateValid(boolean clockwise, boolean isIShape) {
        Point[] kickArray;
        Piece rotatedPiece;
        //initializing kickArray and rotatedPiece based on where the rotation is clockwise and if it is the I shape
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
        //kicker holds the current value of index i in kickArray. This is how much the kick is changing the points of the piece
        Point kicker;
        //we need to get the absolute points here because we need to see if the piece's points are valid on the board, not relative to the bounding box
        Point[] absolutePoints = getAbsolutePoints(rotatedPiece);
        for (int i = 0; i < kickArray.length; i++) {
            kicker = kickArray[i];
            for (int j = 0; j < absolutePoints.length; j++) {
                //we have to check for out of bounds or piece already there
                if (absolutePoints[j].x+kicker.x < 0 || absolutePoints[j].x+kicker.x >= this.getWidth() || absolutePoints[j].y+kicker.y < 0 || absolutePoints[j].y+kicker.y >= this.getHeight() || this.board[absolutePoints[j].y+kicker.y][absolutePoints[j].x+kicker.x] != null) {
                    break;
                }
                //if j reaches the end of the loop without any out of bounds or existing piece trouble, then that means the current piece kick values is valid
                //return a value
                if (j == 3) {
                    this.refX += kicker.x;
                    this.refY += kicker.y;
                    return true;
                }
            }
        }

        return false;
    }
    //returns the piece's points relative to the board's coordinates
    public Point[] getAbsolutePoints(Piece piece) {
        Point[] points = new Point[4];
        for (int i = 0; i < 4; i++) {
            points[i] = new Point(getX(piece.getBody()[i].x), getY(piece.getBody()[i].y));
        }
        return points;
    }
    //this gets the skirt of the absolute points of piece
    //we don't use getSkirt in TetrisPiece because it only returns the skirt relative to the bounding box
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

    //setPiece is called when the piece needs to be placed. Hence, we put it on the board in this function
    public void setPiece() {
        Point[] absolutePoints = getAbsolutePoints(this.piece);
        for (int i = 0; i < absolutePoints.length; i++) {
            this.board[absolutePoints[i].y][absolutePoints[i].x] = this.piece.getType();
        }
    }

    //this function calculates the the number of rows that a piece would drop
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
            while (currHeight >= 0 && this.board[currHeight][getX(i)] == null) {
                currHeight--;
            }
            currHeight++;
            finalDiff = Math.min(skirt[i]-currHeight, finalDiff);
        }
        return finalDiff;
    }

    //clears all full rows
    //only called when a piece is placed
    public void clearRows() {
        //clearRows arraylist contains all the indexes of rows that are full
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
        this.rowsCleared = clearRows.size();
        //no rows to clear. exit
        if (clearRows.size() == 0) {
            return;
        }

        //adjust board based on removed rows
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

    //interface function. Implements and executes all actions that are called
    @Override
    public Result move(Action act) {
        //resetting the number of rows cleared in the previous move. this is always 0
        this.rowsCleared = 0;


        if (act == Board.Action.LEFT) {
            this.lastAction = Board.Action.LEFT;
            if (moveValid(-1, false)) {
                this.refX -= 1;
                this.lastResult = Board.Result.SUCCESS;
                return Board.Result.SUCCESS;
            }
            this.lastResult = Board.Result.OUT_BOUNDS;
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.RIGHT) {
            this.lastAction = Board.Action.RIGHT;
            if (moveValid(1, false)) {
                this.refX += 1;
                this.lastResult = Board.Result.SUCCESS;
                return Board.Result.SUCCESS;
            }
            this.lastResult = Board.Result.OUT_BOUNDS;
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.DOWN) {
            this.lastAction = Board.Action.DOWN;
            if (moveValid(-1, true)) {
                this.refY -= 1;
                this.lastResult = Board.Result.SUCCESS;
                return Board.Result.SUCCESS;
            }
            //if the down action is out of bounds, then the piece is being placed
            setPiece();
            clearRows();
            this.lastResult = Board.Result.PLACE;
            return Board.Result.PLACE;
        }
        //IMPLEMENT ROTATIONS
        else if (act == Board.Action.CLOCKWISE) {
            this.lastAction = Board.Action.CLOCKWISE;
            if (rotateValid(true, piece.getType() == Piece.PieceType.STICK)) {
                this.piece = this.piece.clockwisePiece();
                this.lastResult = Board.Result.SUCCESS;
                return Board.Result.SUCCESS;
            }
            this.lastResult = Board.Result.OUT_BOUNDS;
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.COUNTERCLOCKWISE) {
            this.lastAction = Board.Action.COUNTERCLOCKWISE;

            if (rotateValid(false, piece.getType() == Piece.PieceType.STICK)) {
                this.piece = this.piece.counterclockwisePiece();
                this.lastResult = Board.Result.SUCCESS;
                return Board.Result.SUCCESS;
            }
            this.lastResult = Board.Result.OUT_BOUNDS;
            return Board.Result.OUT_BOUNDS;
        }
        else if (act == Board.Action.DROP) {
            this.lastAction = Board.Action.DROP;
            int dropAmount = drop();
            this.refY -= dropAmount;
            this.lastResult = Board.Result.PLACE;
            //DROP always places a piece
            setPiece();
            clearRows();
            return Board.Result.PLACE;
        }
        else if (act == Board.Action.NOTHING){
            this.lastAction = Board.Action.NOTHING;
            this.lastResult = Board.Result.SUCCESS;
            return Board.Result.SUCCESS;
        }
        this.lastAction = null;
        this.lastResult = Board.Result.NO_PIECE;
        return Board.Result.NO_PIECE;
    }
    //this function is used to test a move given the current board state. it returns a new board
    //mainly used for brain
    @Override
    public Board testMove(Action act) {
        //copying the dimensions and class variables of the current tetris board
        TetrisBoard testBoard = new TetrisBoard(this.getWidth(), this.getHeight());
        for (int row = 0; row < this.getHeight(); row++) {
            for (int col = 0; col < this.getWidth(); col++) {
                testBoard.board[row][col] = this.getGrid(col,row);
            }
        }
        testBoard.piece = this.piece;
        testBoard.refX = this.refX;
        testBoard.refY = this.refY;

        testBoard.rowsCleared = this.rowsCleared;
        testBoard.move(act);
        return testBoard;
    }

    @Override
    public Piece getCurrentPiece() { return this.piece; }

    //returns refX and refY because that is the location of the lower left corner of the bounding box on the board
    @Override
    public Point getCurrentPiecePosition() { return new Point(this.refX, this.refY); }

    //called after a piece is placed or the game is started
    @Override
    public void nextPiece(Piece p, Point spawnPosition) {
        this.refX = spawnPosition.x;
        this.refY = spawnPosition.y;
        Point[] absolutePoints = getAbsolutePoints(p);
        for (int i = 0; i < absolutePoints.length; i++) {
            //error checking to make sure that each given point plus spawnpoint is valid
            if (absolutePoints[i].x >= this.getWidth() || absolutePoints[i].x < 0 || absolutePoints[i].y >= this.getHeight() || absolutePoints[i].y < 0 || this.board[absolutePoints[i].y][absolutePoints[i].x] != null) {
                throw new IllegalArgumentException();
            }
        }
        this.piece = p;
    }

    //checks if two boards are equal
    @Override
    public boolean equals(Object other) {
        //error checking. Object other must be a Board type
        if (!(other instanceof Board)) {
            return false;
        }

        Board newBoard = (Board) other;
        if (newBoard.getWidth() != this.getWidth() || newBoard.getHeight() != this.getHeight()) {
            return false;
        }
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
    public Result getLastResult() { return this.lastResult; }

    @Override
    public Action getLastAction() { return this.lastAction;}

    @Override
    public int getRowsCleared() {return this.rowsCleared;}

    @Override
    public int getWidth() { return this.board[0].length; }

    @Override
    public int getHeight() { return this.board.length; }
    //mainly used for the brain to obtain the height of the tallest tower on the board
    @Override
    public int getMaxHeight() {
        //finds the maximum height of each column of the grid
        int maxHeight = 0;
        for (int row = 0; row < this.getWidth(); row++) {
            maxHeight = Math.max(this.getColumnHeight(row), maxHeight);
        }
        return maxHeight;
    }
    //finds the point in column x of piece that it would drop to if it were dropped
    @Override
    public int dropHeight(Piece piece, int x) {
        Point[] absolutePoints = getAbsolutePoints(this.piece);
        int[] skirt = this.getAbsoluteSkirt(absolutePoints);
        //we can use the drop function here to calculate how much the piece would drop if it were dropped
        //hence, we can find the final y position
        return skirt[x-this.refX]-drop();
    }

    //gets the height of the column at x
    @Override
    public int getColumnHeight(int x) {
        for (int i = this.getHeight()-1; i >=0; i--) {
            if (this.board[i][x] != null) {
                return i;
            }
        }
        return 0;
    }

    //gets the number of squares taken up in row y
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

    //gets either a piecetype or null at the board at position (x,y)
    @Override
    public Piece.PieceType getGrid(int x, int y) { return this.board[y][x]; }
}
