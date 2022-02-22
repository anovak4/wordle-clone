import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Wordle implements KeyListener {
    private JLabel[][] words;
    private int wordIndex = 0;
    private int letterIndex = 0;

    public Wordle() {
        JFrame frame = new JFrame("Wordle");

        JPanel wordPanel = new JPanel();
        wordPanel.setLayout(new GridLayout(6,5,10,13)); // rows, cols, hgap, vgap
        
        words = new JLabel[6][5];
        for (int w = 0; w < words.length; w++) {
            for (int l = 0; l < words[w].length; l++) {
                JLabel letter = new JLabel();
                letter.setOpaque(true); //needed to be able to set the background
                letter.setBackground(new Color(194, 194, 194));
                letter.setForeground(Color.black);
                // letter.setText(w + "-" + l);
                letter.setPreferredSize(new Dimension(60,60));
                letter.setFont(new Font("Arial", Font.BOLD, 35));
                wordPanel.add(letter);
                letter.addKeyListener(this);
                words[w][l] = letter;
            }
        }

        // final setup to make things visible
        frame.add(wordPanel, BorderLayout.NORTH);
        frame.setSize(375, 455);
        frame.setVisible(true);    
    }

    // not working
    public void keyTyped(KeyEvent e) {
        words[wordIndex][letterIndex].setText(String.valueOf(e.getKeyChar()));
    }

    // only need these two because we're implementing KeyListener
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    
}