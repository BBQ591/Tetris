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

    public class Node {
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

    @Override
    public PieceType getType() {
        // TODO: Implement me.
        return this.pieceType;
    }

    @Override
    public int getRotationIndex() {
        // TODO: Implement me.
        return this.orientation.index;
    }

    @Override
    public Piece clockwisePiece() {
        // TODO: Implement me.
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
        newPiece.setIndex(((this.getRotationIndex()-1)%4+4)%4);
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
        return this.orientation.val;
    }

    @Override
    public int[] getSkirt() {
        // TODO: Implement me.
        int[] skirt = new int[this.getWidth()];
        for (int i = 0; i < this.getWidth(); i++) {
            skirt[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < this.orientation.val.length; i++) {
            skirt[this.orientation.val[i].x] = Math.min(skirt[this.orientation.val[i].x], this.orientation.val[i].y);
        }
        return skirt;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof TetrisPiece)) return false;
        TetrisPiece otherPiece = (TetrisPiece) other;

        Set<Point> points = new HashSet<>();

        for (int i = 0; i < this.orientation.val.length; i++) {
            points.add(otherPiece.orientation.val[i]);
        }

        if(points.size() != this.orientation.val.length){
            return false;
        }

        // TODO: Implement me.
        for (int i = 0; i < this.orientation.val.length; i++) {
            if (!points.contains(this.orientation.val[i])) return false;
        }
        return true;
    }
}
