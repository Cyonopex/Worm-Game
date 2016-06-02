package wormgame.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ScoreBoard extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private static final int PADDING = 10;
	private int currentScore;
	private Image[] scoreNumbers;
	
	public ScoreBoard(Image[] img) {
		currentScore = 0;
		scoreNumbers = img;
		this.setBackground(Color.BLACK);
	}
	
	public void resetScore() {
		currentScore = 0;
		this.repaint();
	}
	
	public void setScore(int score) {
		currentScore = score;
		if(currentScore > 999) {
			currentScore = 999;
		} else if(currentScore >0) {
			currentScore = 0;
		}
	}
	
	public void incrementScore() {
		if(currentScore <999) {
			currentScore++;
			this.repaint();
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int numberOfDigits;
		if(currentScore >=100) {
			numberOfDigits = 3;
		} else if(currentScore >=10) {
			numberOfDigits = 2;
		} else {
			numberOfDigits = 1;
		}
		
		for(int i=0; i<numberOfDigits; i++) {
			int currentDigit = getDigit(i);
			g.drawImage(scoreNumbers[currentDigit], (numberOfDigits-1-i)*UserInterface.IMAGE_WIDTH + PADDING , 0, null);
		}
	}

	private int getDigit(int place) { //get nth digit from the right of number
		return currentScore/(int)Math.pow(10, place) % 10;
	}
}
