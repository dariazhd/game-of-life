package de.hawhamburg.inf.gol;

import javax.swing.JFrame;

/**
 * Application frame showing the PlaygroundComponent with the Playground graphics.
 * 
 * @author Christian Lins
 */
public class ApplicationFrame extends JFrame {
    
    public ApplicationFrame() {
        setTitle("Game of Life");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
