package assignment;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Point;

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

    public class Node {
        //        public Node(PieceType type) {
//            Node next =
//        }
        Node next;
        Point[] val;
        int index;
        public Node(Point[] val, int index) {
            this.index = index;
            this.val = val;
            this.next = null;
        }
    }
    PieceType pieceType;
    /**
     * Construct a tetris piece of the given type. The piece should be in its spawn orientation,
     * i.e., a rotation index of 0.
     * 
     * You may freely add additional constructors, but please leave this one - it is used both in
     * the runner code and testing code.
     */
    public Node orientation;

    public TetrisPiece(PieceType type) {
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
            arr.add(new Point[]{new Point(1,0), new Point(1,1), new Point(2,1), new Point(2,2)});
            arr.add(new Point[]{new Point(0,1), new Point(1,1), new Point(2,1), new Point(2,0)});
            arr.add(new Point[]{new Point(0,0), new Point(1,0), new Point(1,1), new Point(1,2)});
        }
        else if (type == Piece.PieceType.RIGHT_L) {
            arr.add(new Point[]{new Point(0, 1), new Point(0,1), new Point(2,1), new Point(2,2)});
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
        /**
         * Critter x = new eachCritter();
         * Piece x = new TetrisPiece;
         */

//        orientation.setLinkedList(type);
    }

    @Override
    public PieceType getType() {
        // TODO: Implement me.
        //sets = {[point1, point2, ...], orientation2, orientation3 ...}
        return this.pieceType;
    }

    @Override
    public int getRotationIndex() {
        // TODO: Implement me.
        //linkedlist with each node self.prev, self.next, self.points, self.index
        return this.orientation.index;
    }

    @Override
    public Piece clockwisePiece() {
        // TODO: Implement me.
        //.next on linked list
        /**
         * Piece x = new TetrisPiece(this.getType())
         * x.setIndex(this.getRotationIndex()+1 % 4)
         */
        TetrisPiece newPiece = new TetrisPiece(this.getType());
        newPiece.setIndex((this.getRotationIndex()+1)%4);
        return newPiece;
    }

    public void setIndex(int rotationIndex) {
        while (this.orientation.index != rotationIndex) {
            this.orientation = this.orientation.next;
        }
    }

    @Override
    public Piece counterclockwisePiece() {
        // TODO: Implement me.
        TetrisPiece newPiece = new TetrisPiece(this.getType());
        newPiece.setIndex((this.getRotationIndex()-1)%4);
        return newPiece;
    }

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

    @Override
    public Point[] getBody() {
        // TODO: Implement me.
        // return whatever is in current linked list node
        return this.orientation.val;
    }

    @Override
    public int[] getSkirt() {
        // TODO: Implement me.
        // returns the lowest value at each x axis
        int[] skirt = new int[this.getHeight()];
        for (int i = 0; i < this.orientation.val.length; i++) {
            skirt[this.orientation.val[i].x] = Math.min(skirt[this.orientation.val[i].x], this.orientation.val[i].y);
        }
        return skirt;
    }

    @Override
    public boolean equals(Object other) {
        // Ignore objects which aren't also tetris pieces.
        if(!(other instanceof TetrisPiece)) return false;
        TetrisPiece otherPiece = (TetrisPiece) other;

        // TODO: Implement me.
        for (int i = 0; i < this.orientation.val.length; i++) {
            if (this.orientation.val[i].x != otherPiece.orientation.val[i].x || this.orientation.val[i].y != otherPiece.orientation.val[i].y) return false;
        }
        return true;
        //verify that the points are all the same
    }
}
