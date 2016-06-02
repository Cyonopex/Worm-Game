package wormgame.domain;

import javax.swing.SwingUtilities;

import wormgame.game.WormGame;
import wormgame.gui.UserInterface;

public class Main {
	public static void main(String[] args) {
		WormGame game = new WormGame(20, 20, false);
		UserInterface ui = new UserInterface(game, 20);
		SwingUtilities.invokeLater(ui);
		
        while (ui.getDrawingBoard() == null || ui.getScoreBoard() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("The drawing board or score board hasn't been created yet.");
            }
        }
		
		game.setDrawingBoard(ui.getDrawingBoard());
		game.setScoreBoard(ui.getScoreBoard());
	}
}
