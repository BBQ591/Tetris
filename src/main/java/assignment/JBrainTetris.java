package assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class JBrainTetris extends JTetris{

    public static void main(String[] args) {
        //the only thing that runs in main is launching the gui. nothing else is needed
        createGUI(new JBrainTetris());
    }
    JBrainTetris() {
        // At every increment of time, the BumbleBeeBrain runs a command based on its next best move
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
