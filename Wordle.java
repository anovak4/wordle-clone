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
                letter.setPreferredSize(new Dimension(40,60));
                letter.setFont(new Font("Arial", Font.BOLD, 40));
				letter.setHorizontalAlignment(SwingConstants.CENTER);
                wordPanel.add(letter);
                letter.addKeyListener(this);
                words[w][l] = letter;
            }
        }

        // final setup
		frame.pack();
        frame.add(wordPanel, BorderLayout.NORTH);
        frame.setSize(300, 450);
		words[wordIndex][letterIndex].requestFocusInWindow(); 
        frame.setVisible(true);    
    }

	private void checkAnswer(){
		System.out.println("Checking answer...");

		wordIndex++;
		letterIndex = 0;
	}

    public void keyTyped(KeyEvent e) {
        words[wordIndex][letterIndex].setText(String.valueOf(e.getKeyChar()).toUpperCase());
		letterIndex++;
		if(letterIndex >= 5) {
			checkAnswer();
		}
    }

    public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if(letterIndex > 0) {
				letterIndex--;
			}
			words[wordIndex][letterIndex].setText("");
		}
		// if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		// 	checkAnswer();
		// }
	}

	// only need this because I'm implementing KeyListener
    public void keyReleased(KeyEvent e) {}
    
}