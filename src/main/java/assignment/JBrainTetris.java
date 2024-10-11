package assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class JBrainTetris extends JTetris{

    public static void main(String[] args) {
        createGUI(new JBrainTetris());
    }
    JBrainTetris() {
//        setPreferredSize(new Dimension(WIDTH*PIXELS+2, (HEIGHT+TOP_SPACE)*PIXELS+2));
//        gameOn = false;
//        board = new TetrisBoard(WIDTH, HEIGHT + TOP_SPACE);
//        System.out.println(board);
//        timer = new javax.swing.Timer(DELAY, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                tick(new LameBrain().nextMove(board));
//            }
//        });
        timer = new javax.swing.Timer(DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (board.getCurrentPiece() != null) {

                    tick(new BumbleBeeBrain().nextMove(board));
//                    tick(Board.Action.DOWN);
                }
            }
        });
    }
}
