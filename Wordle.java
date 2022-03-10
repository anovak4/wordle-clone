import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Wordle implements KeyListener {
    private JLabel[][] words;
    private int wordIndex = 0;
    private int letterIndex = 0;
	private String target;
	private String[] targetArr;
	private String[] correctness = {"a", "a", "a", "a", "a"}; 

    public Wordle() {
		int ran = (int) (Math.random() * 997) + 1; // range of file is 1-997
		try(BufferedReader reader = new BufferedReader(new FileReader("words.txt"))) {
			for (int i = 0; i < ran; i++)
              reader.readLine();
          	target = reader.readLine();
		} catch(IOException e) {
        	System.out.println(e);
      	}
		targetArr = target.split("");
		System.out.println(target);
		
        JFrame frame = new JFrame("Wordle");

        JPanel wordPanel = new JPanel();
        wordPanel.setLayout(new GridLayout(6,5,10,13)); // rows, cols, hgap, vgap
        
        words = new JLabel[6][5];
        for(int w = 0; w < words.length; w++) {
            for(int l = 0; l < words[w].length; l++) {
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

		frame.pack();
        frame.add(wordPanel, BorderLayout.NORTH);
        frame.setSize(300, 450);
		words[wordIndex][letterIndex].requestFocusInWindow(); 
        frame.setVisible(true);    
    }

    public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == '\b') {
			// backspace
			if(letterIndex > 0) {
				letterIndex--;
			}
			words[wordIndex][letterIndex].setText("");
		} else if(e.getKeyChar() == '\n') {
			// enter
			if(letterIndex >= 5) {
				checkAnswer();
			}
		} else {
			// letters
			if(letterIndex < 5) {
				words[wordIndex][letterIndex].setText(String.valueOf(e.getKeyChar()).toUpperCase());
				letterIndex++;
			}
		}
    }

	// only need these because I'm implementing KeyListener
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

	private void checkAnswer(){
		Arrays.fill(correctness, "a");
		for(int i = 0; i < words[wordIndex].length; i++) {
			checkCorrect(i);
			checkWrong(i);
		}
		checkExtras();
		int correctCount = 0;
		for(int i = 0; i < correctness.length; i++) {
			if(correctness[i].equals("c")) {
				words[wordIndex][i].setBackground(new Color(3, 168, 64)); //green
				correctCount++;
			} else if(correctness[i].equals("w")) {
				words[wordIndex][i].setBackground(new Color(117, 117, 117)); //gray
			} else {
				words[wordIndex][i].setBackground(new Color(255, 223, 82)); //yellow
			}
		}
		wordIndex++;
		letterIndex = 0;
		if(correctCount == 5) {
			// TODO: dont accept any more input
		}
	}

	private void checkCorrect(int index) {
		if(words[wordIndex][index].getText().equalsIgnoreCase(targetArr[index])) {
			correctness[index] = "c";
		}
	}

	private void checkWrong(int index) {
		boolean wrong = true;
		for(String letter:targetArr) {
			if(words[wordIndex][index].getText().equalsIgnoreCase(letter)) {
				wrong = false;
			}
		}
		if(wrong) {
			correctness[index] = "w";
		}
	}

	private void checkExtras() {
		String input = combineWord(wordIndex);
		for(int i = 0; i < words[wordIndex].length; i++) {
			char letter = words[wordIndex][i].getText().charAt(0);
			if(containsDuplicate(input, letter)) {
				int index1 = input.indexOf(letter);
				int index2 = input.indexOf(letter, index1 + 1);
				if((correctness[index1].equals("w") && correctness[index2].equals("w")) || (correctness[index1].equals("c") && correctness[index2].equals("c"))) {}
				else if(correctness[index1].equals("c")) {
					correctness[index2] = "w";
				} else if(correctness[index2].equals("c")) {
					correctness[index1] = "w";
				} else {
					correctness[index1] = "a";
	                correctness[index2] = "w";
				}
			}
		}
	}

	private String combineWord(int wordIndex) {
		String str = "";
		for(int i = 0; i < words[wordIndex].length; i++) {
			str += words[wordIndex][i].getText();
		}
		return str;
	}

	private boolean containsDuplicate(String str, char letter) {
		return str.indexOf(letter) != str.lastIndexOf(letter);
	}
    
}