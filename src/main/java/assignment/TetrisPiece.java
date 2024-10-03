package assignment;
import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<Point> val;
        Node prev;
        int index;
        public Node(ArrayList<Point> val, int index) {
            this.index = index;
            this.val = val;
            this.next = null;
            this.prev = null;
        }
//        public void setLinkedList(PieceType type) {
//            if (type == Piece.PieceType.T) {
//                next =
//            }
//            return;
//        }

    }
    /**
     * Construct a tetris piece of the given type. The piece should be in its spawn orientation,
     * i.e., a rotation index of 0.
     * 
     * You may freely add additional constructors, but please leave this one - it is used both in
     * the runner code and testing code.
     */
    public Node orientation;

    public TetrisPiece(PieceType type) {
        ArrayList<ArrayList<Point>> arr = new ArrayList<>();
        if (type == Piece.PieceType.T) {
            arr.add(new ArrayList<>(Arrays.asList(new Point(0, 1), new Point(1,1), new Point(2,1), new Point(1,2))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(1, 0), new Point(1,1), new Point(1,2), new Point(2,1))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(1, 0), new Point(1,1), new Point(0,1), new Point(2,1))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(1,0), new Point(1,1), new Point(1,2), new Point(0,1))));
        }
        else if (type == Piece.PieceType.SQUARE) {
            arr.add(new ArrayList<>(Arrays.asList(new Point(0, 0), new Point(1,1), new Point(0,1), new Point(1,0))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(0, 0), new Point(1,1), new Point(0,1), new Point(1,0))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(0, 0), new Point(1,1), new Point(0,1), new Point(1,0))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(0, 0), new Point(1,1), new Point(0,1), new Point(1,0))));
        }
        else if (type == Piece.PieceType.STICK) {
            arr.add(new ArrayList<>(Arrays.asList(new Point(0, 2), new Point(1,2), new Point(2,2), new Point(3,2))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(2,0), new Point(2,1), new Point(2,2), new Point(2,3))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(0,1), new Point(1,1), new Point(2,1), new Point(3,1))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3))));
        }
        else if (type == Piece.PieceType.LEFT_L) {
            arr.add(new ArrayList<>(Arrays.asList(new Point(0, 1), new Point(0,2), new Point(1,1), new Point(2,1))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(1,0), new Point(1,1), new Point(2,1), new Point(2,2))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(0,1), new Point(1,1), new Point(2,1), new Point(2,0))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(0,0), new Point(1,0), new Point(1,1), new Point(1,2))));
        }
        else if (type == Piece.PieceType.RIGHT_L) {
            arr.add(new ArrayList<>(Arrays.asList(new Point(0, 1), new Point(0,1), new Point(2,1), new Point(2,2))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(1,0), new Point(1,1), new Point(1,2), new Point(2,0))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(0,0), new Point(0,1), new Point(1,1), new Point(2,1))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(1,0), new Point(1,1), new Point(1,2), new Point(0,2))));
        }
        else if (type == Piece.PieceType.LEFT_DOG) {
            arr.add(new ArrayList<>(Arrays.asList(new Point(0, 2), new Point(1,2), new Point(1,1), new Point(2,1))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(1,0), new Point(1,1), new Point(2,1), new Point(2,2))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(0,1), new Point(1,1), new Point(1,0), new Point(2,0))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(0,0), new Point(0,1), new Point(1,1), new Point(1,2))));
        }
        else if (type == Piece.PieceType.RIGHT_DOG) {
            arr.add(new ArrayList<>(Arrays.asList(new Point(0,1), new Point(1,1), new Point(1,2), new Point(2,2))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(2,0), new Point(2,1), new Point(1,1), new Point(1,2))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1))));
            arr.add(new ArrayList<>(Arrays.asList(new Point(1,0), new Point(1,1), new Point(0,1), new Point(0,2))));
        }


        orientation = new Node(null, 0);
        Node pointer = orientation;
        Node prev = null;
        int index = 0;
        while (index < 4) {
            if (prev != null) {
                pointer.prev = prev;
            }
            pointer.val = arr.get(index);
            pointer.index = index;
            if (index != 3) {
                pointer.next = new Node(null, -1);
            }
            else {
                pointer.next = orientation;
            }
            prev = pointer;
            pointer = pointer.next;
            index++;
        }
        if (type == Piece.PieceType.T) {
            pointer.prev = prev;
            index = 0;
            while (index < 4) {
                System.out.println(pointer.val + "" + pointer.index);
                pointer = pointer.next;
                index += 1;
            }
        }





//        orientation.setLinkedList(type);
    }

    @Override
    public PieceType getType() {
        // TODO: Implement me.
        //sets = {[point1, point2, ...], orientation2, orientation3 ...}
        return null;
    }

    @Override
    public int getRotationIndex() {
        // TODO: Implement me.
        //linkedlist with each node self.prev, self.next, self.points, self.index
        return -1;
    }

    @Override
    public Piece clockwisePiece() {
        // TODO: Implement me.
        //.next on linked list
        return null;
    }

    @Override
    public Piece counterclockwisePiece() {
        // TODO: Implement me.
        //.prev on linked list
        return null;
    }

    @Override
    public int getWidth() {
        // TODO: Implement me.
        //returns the box, regardless of orientation
        return -1;
    }

    @Override
    public int getHeight() {
        // TODO: Implement me.
        //same as getWidth
        return -1;
    }

    @Override
    public Point[] getBody() {
        // TODO: Implement me.
        // return whatever is in current linked list node
        return null;
    }

    @Override
    public int[] getSkirt() {
        // TODO: Implement me.
        // returns the lowest value at each x axis
        return null;
    }

    @Override
    public boolean equals(Object other) {
        // Ignore objects which aren't also tetris pieces.
        if(!(other instanceof TetrisPiece)) return false;
        TetrisPiece otherPiece = (TetrisPiece) other;

        // TODO: Implement me.
        return false;
        //verify that the points are all the same
    }
}
