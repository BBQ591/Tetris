//package assignment;
//
//import java.util.ArrayList;
//
//public class Individual {
//    public double maxHeightWeight;
//    public double linesClearedWeight;
//    TetrisBoard board;
//    public Individual(double maxHeightWeight, double linesClearedWeight) {
//        this.maxHeightWeight = maxHeightWeight;
//        this.linesClearedWeight = linesClearedWeight;
//    }
//    public int getFitness() {
//        int counter = 0;
//        TetrisBoard board = new TetrisBoard(10,20);
//
//    }
//
//    public void tick(Board.Action verb) {
//        Board.Result result = this.board.move(verb);
//        switch (result) {
//            case SUCCESS:
//            case OUT_BOUNDS:
//                // The board is responsible for staying in a good state
//                counter += 1;
//                break;
//            case PLACE:
//                counter += 1;
//                if (this.board.getMaxHeight() > HEIGHT) {
//                    return counter;
//                }
//            case NO_PIECE:
//                addNewPiece();
//                break;
//        }
//    }
//
//    private ArrayList<Board> options;
//    private ArrayList<Board.Action> firstMoves;
//
//    /**
//     * Decide what the next move should be based on the state of the board.
//     */
//    public Board.Action nextMove(Board currentBoard) {
//        // Fill the our options array with versions of the new Board
//        options = new ArrayList<>();
//        firstMoves = new ArrayList<>();
//        enumerateOptions(currentBoard);
//
//        int best = 0;
//        int bestIndex = 0;
//
//        // Check all of the options and get the one with the highest score
//        for (int i = 0; i < options.size(); i++) {
//            int score = scoreBoard(options.get(i));
//            if (score > best) {
//                best = score;
//                bestIndex = i;
//            }
//        }
//        // We want to return the first move on the way to the best Board
//        return firstMoves.get(bestIndex);
//    }
//
//    /**
//     * Test all of the places we can put the current Piece.
//     * Since this is just a Lame Brain, we aren't going to do smart
//     * things like rotating pieces.
//     */
//    private void enumerateOptions(Board currentBoard) {
//        // We can always drop our current Piece
//        options.add(currentBoard.testMove(Board.Action.DROP));
//        firstMoves.add(Board.Action.DROP);
//
//        currentBoard = currentBoard.testMove(Board.Action.LEFT);
//
//        for(int rotationIndex = 0; rotationIndex < 4; rotationIndex++) {
//
//            while (currentBoard.getLastResult() == Board.Result.SUCCESS){
//                currentBoard = currentBoard.testMove(Board.Action.LEFT);
//            }
//            options.add(currentBoard.testMove(Board.Action.DROP));
//            firstMoves.add(Board.Action.DROP);
//            currentBoard = currentBoard.testMove(Board.Action.RIGHT);
//            options.add(currentBoard.testMove(Board.Action.DROP));
//            firstMoves.add(Board.Action.DROP);
//            while (currentBoard.getLastResult() == Board.Result.SUCCESS){
//                currentBoard = currentBoard.testMove(Board.Action.RIGHT);
//
//            }
//
//        }
//
//
//
//        // Now we'll add all the places to the left we can DROP
//        Board left = currentBoard.testMove(Board.Action.LEFT);
//        while (left.getLastResult() == Board.Result.SUCCESS) {
//            options.add(left.testMove(Board.Action.DROP));
//            firstMoves.add(Board.Action.LEFT);
//            left.move(Board.Action.LEFT);
//        }
//
//        // And then the same thing to the right
//        Board right = currentBoard.testMove(Board.Action.RIGHT);
//        while (right.getLastResult() == Board.Result.SUCCESS) {
//            options.add(right.testMove(Board.Action.DROP));
//            firstMoves.add(Board.Action.RIGHT);
//            right.move(Board.Action.RIGHT);
//        }
//
//    }
//
//    /**
//     * Since we're trying to avoid building too high,
//     * we're going to give higher scores to Boards with
//     * MaxHeights close to 0.
//     */
//    private int scoreBoard(Board newBoard) {
//        return 100 - (newBoard.getMaxHeight() * 5);
//    }
//}