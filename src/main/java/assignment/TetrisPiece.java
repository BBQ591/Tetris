package assignment;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import java.awt.*;

/**
 * An immutable representation of a tetris piece in a particular rotation.
 * 
 * All operations on a TetrisPiece should be constant time, except for its
 * initial construction. This means that rotations should also be fast - calling
 * clockwisePiece() and counterclockwisePiece() should be constant time! You may
 * need to do pre-computation in the constructor to make this possible.
 */


public final class TetrisPiece implements Piece {
    //initializes linked list
    public class Node {
        //points to the next node
        Node next;
        //has the points of the current orientation
        Point[] val;
        //has the rotationIndex
        int index;
        public Node(Point[] val, int index) {
            this.index = index;
            this.val = val;
            this.next = null;
        }
    }
    public PieceType pieceType;
    public Node orientation;

    public TetrisPiece(PieceType type) {
        //determines the set of points to initialize the piece with depending on its type
        this.pieceType = type;
        ArrayList<Point[]> arr = new ArrayList<>();
        if (type == Piece.PieceType.T) {
            arr.add(new Point[]{new Point(0, 1), new Point(1,1), new Point(2,1), new Point(1,2)});
            arr.add(new Point[]{new Point(1, 0), new Point(1,1), new Point(1,2), new Point(2,1)});
            arr.add(new Point[]{new Point(1, 0), new Point(1,1), new Point(0,1), new Point(2,1)});
            arr.add(new Point[]{new Point(1,0), new Point(1,1), new Point(1,2), new Point(0,1)});
        }
        else if (type == Piece.PieceType.SQUARE) {
            arr.add(new Point[]{new Point(0, 0), new Point(1,1), new Point(0,1), new Point(1,0)});
            arr.add(new Point[]{new Point(0, 0), new Point(1,1), new Point(0,1), new Point(1,0)});
            arr.add(new Point[]{new Point(0, 0), new Point(1,1), new Point(0,1), new Point(1,0)});
            arr.add(new Point[]{new Point(0, 0), new Point(1,1), new Point(0,1), new Point(1,0)});
        }
        else if (type == Piece.PieceType.STICK) {
            arr.add(new Point[]{new Point(0, 2), new Point(1,2), new Point(2,2), new Point(3,2)});
            arr.add(new Point[]{new Point(2,0), new Point(2,1), new Point(2,2), new Point(2,3)});
            arr.add(new Point[]{new Point(0,1), new Point(1,1), new Point(2,1), new Point(3,1)});
            arr.add(new Point[]{new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3)});
        }
        else if (type == Piece.PieceType.LEFT_L) {
            arr.add(new Point[]{new Point(0, 1), new Point(0,2), new Point(1,1), new Point(2,1)});
            arr.add(new Point[]{new Point(1,0), new Point(1,1), new Point(1,2), new Point(2,2)});
            arr.add(new Point[]{new Point(0,1), new Point(1,1), new Point(2,1), new Point(2,0)});
            arr.add(new Point[]{new Point(0,0), new Point(1,0), new Point(1,1), new Point(1,2)});
        }
        else if (type == Piece.PieceType.RIGHT_L) {
            arr.add(new Point[]{new Point(0, 1), new Point(1,1), new Point(2,1), new Point(2,2)});
            arr.add(new Point[]{new Point(1,0), new Point(1,1), new Point(1,2), new Point(2,0)});
            arr.add(new Point[]{new Point(0,0), new Point(0,1), new Point(1,1), new Point(2,1)});
            arr.add(new Point[]{new Point(1,0), new Point(1,1), new Point(1,2), new Point(0,2)});
        }
        else if (type == Piece.PieceType.LEFT_DOG) {
            arr.add(new Point[]{new Point(0, 2), new Point(1,2), new Point(1,1), new Point(2,1)});
            arr.add(new Point[]{new Point(1,0), new Point(1,1), new Point(2,1), new Point(2,2)});
            arr.add(new Point[]{new Point(0,1), new Point(1,1), new Point(1,0), new Point(2,0)});
            arr.add(new Point[]{new Point(0,0), new Point(0,1), new Point(1,1), new Point(1,2)});
        }
        else if (type == Piece.PieceType.RIGHT_DOG) {
            arr.add(new Point[]{new Point(0,1), new Point(1,1), new Point(1,2), new Point(2,2)});
            arr.add(new Point[]{new Point(2,0), new Point(2,1), new Point(1,1), new Point(1,2)});
            arr.add(new Point[]{new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1)});
            arr.add(new Point[]{new Point(1,0), new Point(1,1), new Point(0,1), new Point(0,2)});
        }

        //creating circularly linked list
        orientation = new Node(null, 0);
        Node pointer = orientation;
        int index = 0;
        while (index < 4) {
            pointer.val = arr.get(index);
            pointer.index = index;
            if (index != 3) {
                pointer.next = new Node(null, -1);
            }
            else {
                pointer.next = orientation;
            }

            pointer = pointer.next;
            index++;

        }
    }

    //returns the type of the current piece
    @Override
    public PieceType getType() {
        // TODO: Implement me.
        return this.pieceType;
    }
    //gets the rotation index of the current piece. this is stored in each node
    @Override
    public int getRotationIndex() {
        // TODO: Implement me.
        return this.orientation.index;
    }
    //clockwise means 1 rotation to the next node
    @Override
    public Piece clockwisePiece() {
        // TODO: Implement me.
        TetrisPiece newPiece = new TetrisPiece(this.getType());
        newPiece.orientation = this.orientation.next;
        return newPiece;
    }
    //1 move counterclockwise is the same as 3 moves clockwise. Since 3 moves counterclockwise is still constant time, then this is valid
    //we didnt create a prev pointer because it would have complicated the code more
    @Override
    public Piece counterclockwisePiece() {
        // TODO: Implement me.
        TetrisPiece newPiece = new TetrisPiece(this.getType());
        newPiece.orientation = this.orientation.next.next.next;
        return newPiece;
    }
    //returns the width of the bounding box based on the current piece's type
    @Override
    public int getWidth() {
        // TODO: Implement me.
        if (this.pieceType == Piece.PieceType.T) {
            return 3;
        }
        else if (this.pieceType == Piece.PieceType.SQUARE) {
            return 2;
        }
        else if (this.pieceType == Piece.PieceType.STICK) {
            return 4;
        }
        else if (this.pieceType == Piece.PieceType.LEFT_L) {
            return 3;
        }
        else if (this.pieceType == Piece.PieceType.RIGHT_L) {
            return 3;
        }
        else if (this.pieceType == Piece.PieceType.LEFT_DOG) {
            return 3;
        }
        else if (this.pieceType == Piece.PieceType.RIGHT_DOG) {
            return 3;
        }
        return -1;
    }
    //returns the height of the bounding box based on the current piece's type
    @Override
    public int getHeight() {
        // TODO: Implement me.
        if (this.pieceType == Piece.PieceType.T) {
            return 3;
        }
        else if (this.pieceType == Piece.PieceType.SQUARE) {
            return 2;
        }
        else if (this.pieceType == Piece.PieceType.STICK) {
            return 4;
        }
        else if (this.pieceType == Piece.PieceType.LEFT_L) {
            return 3;
        }
        else if (this.pieceType == Piece.PieceType.RIGHT_L) {
            return 3;
        }
        else if (this.pieceType == Piece.PieceType.LEFT_DOG) {
            return 3;
        }
        else if (this.pieceType == Piece.PieceType.RIGHT_DOG) {
            return 3;
        }
        return -1;
    }
    //gets the current set of points relative to the bounding box
    @Override
    public Point[] getBody() {
        // TODO: Implement me.
        return this.orientation.val;
    }
    //gets the lowest value at each column. If there is no point at a certain column, then skirt just saves it as Integer.MAX_Value
    @Override
    public int[] getSkirt() {
        // TODO: Implement me.
        int[] skirt = new int[this.getWidth()];
        //initialize skirt
        for (int i = 0; i < this.getWidth(); i++) {
            skirt[i] = Integer.MAX_VALUE;
        }
        //changing skirt based on piece's Point values
        for (int i = 0; i < this.orientation.val.length; i++) {
            skirt[this.orientation.val[i].x] = Math.min(skirt[this.orientation.val[i].x], this.orientation.val[i].y);
        }
        return skirt;
    }
    //checks if the type and rotationIndex are the same. if they are, then they are the same piece. Otherwise, they arent the same piece
    @Override
    public boolean equals(Object other) {
        if(!(other instanceof TetrisPiece)) return false;
        TetrisPiece otherPiece = (TetrisPiece) other;
        return this.pieceType == otherPiece.pieceType && this.orientation.index == otherPiece.orientation.index;
    }
}
