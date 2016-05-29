package wormgame.domain;

import javax.swing.SwingUtilities;

import wormgame.game.WormGame;
import wormgame.gui.UserInterface;

public class Main {
	public static void main(String[] args) {
		WormGame game = new WormGame(20, 20, false);
		UserInterface ui = new UserInterface(game, 20);
		SwingUtilities.invokeLater(ui);
		
        while (ui.getUpdatable() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("The drawing board hasn't been created yet.");
            }
        }
		
		game.setComponent(ui.getUpdatable());
		game.start();

	}
}
