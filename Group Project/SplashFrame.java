import java.io.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;

public class SplashFrame extends JPanel implements ActionListener {
	static private final String newline = "\n";
	JButton openButton;
	JTextArea log;
	JFileChooser fc;

	public SplashFrame() {
		super(new BorderLayout());

		// Create the log first, because the action listeners
		// need to refer to it.
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		// Create a file chooser
		fc = new JFileChooser();

		// Uncomment one of the following lines to try a different
		// file selection mode. The first allows just directories
		// to be selected (and, at least in the Java look and feel,
		// shown). The second allows both files and directories
		// to be selected. If you leave these lines commented out,
		// then the default mode (FILES_ONLY) will be used.
		//
		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Create the open button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).
		openButton = new JButton("Open");
		openButton.addActionListener(this);

		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(openButton);

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {

		// Handle open button action.
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(SplashFrame.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File imageFile = fc.getSelectedFile();
				System.out.println(imageFile.canRead());
				BufferedImage image = null;

				try {
					image = ImageIO.read(imageFile);
				} catch (IOException e1) {
					System.err.println("IMAGE CORRUPTION ERROR");
				}
				DisplayImageFrame displayImageFrame = new DisplayImageFrame(
						image);

				displayImageFrame.setSize(image.getWidth(), image.getHeight());
				displayImageFrame.setVisible(true);
				displayImageFrame
						.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//				while (displayImageFrame.isShowing()) {
//					// try {
//					// TimeUnit.SECONDS.sleep(2);
//					// } catch (InterruptedException e1) {
//					// }
//				}

				TileGrid tileGrid = new TileGrid(image, 3, 3);
				tileGrid.shuffle(100);
				TileGridFrame tileGridFrame = new TileGridFrame(tileGrid, image);
				tileGridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				tileGridFrame.setJMenuBar(tileGridFrame.createMenuBar());
				tileGridFrame.setSize(tileGrid.getPuzzleImageWidth(),
						tileGrid.getPuzzleImageHeight());
				tileGridFrame.setResizable(false);
				tileGridFrame.setVisible(true);
			} else {
				log.append("Open command cancelled by user." + newline);
			}
			log.setCaretPosition(log.getDocument().getLength());
		}
	}

	// /** Returns an ImageIcon, or null if the path was invalid. */
	// protected static ImageIcon createImageIcon(String path) {
	// java.net.URL imgURL = SplashFrame.class.getResource(path);
	// if (imgURL != null) {
	// return new ImageIcon(imgURL);
	// } else {
	// System.err.println("Couldn't find file: " + path);
	// return null;
	// }
	// }

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("SplashFrame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new SplashFrame());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		createAndShowGUI();
	}
}
