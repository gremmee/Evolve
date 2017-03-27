package nl.gremmee.evolve;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {
    private static final long serialVersionUID = 1522602361354624706L;

    public Window(int aWidth, int aHeight, String aTitle, Evolve aEvolve) {
        JFrame frame = new JFrame(aTitle);

        frame.setPreferredSize(new Dimension(aWidth, aHeight));
        frame.setMaximumSize(new Dimension(aWidth, aHeight));
        frame.setMinimumSize(new Dimension(aWidth, aHeight));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(aEvolve);
        frame.setVisible(true);
        aEvolve.start();
    }
}
