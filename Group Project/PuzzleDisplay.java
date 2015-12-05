import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

public class PuzzleDisplay {

	public static final int frameWidth = 430;
	public static final int frameHeight = 130;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		callPrompt();
	}

	/**
	 * Our main call, this creates the starter frame on which the player can
	 * choose a file and difficulty.
	 */
	public static void callPrompt() {

		TileGridFrame tileGridFrame = new TileGridFrame();
		tileGridFrame.setJMenuBar(tileGridFrame.createMenuBar());
		tileGridFrame.setTitle("Tile Puzzle Game");

		tileGridFrame.setSize(frameWidth, frameHeight);
		tileGridFrame.setResizable(false);
		tileGridFrame
				.setLocation(
						(Toolkit.getDefaultToolkit().getScreenSize().width - frameWidth) / 2,
						(Toolkit.getDefaultToolkit().getScreenSize().height - frameHeight) / 2);
		tileGridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tileGridFrame.setVisible(true);
	}
}
