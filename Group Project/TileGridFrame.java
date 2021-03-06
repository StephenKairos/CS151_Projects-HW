import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TileGridFrame extends JFrame implements Observer {
	/**
    * 
    */
	private static final long serialVersionUID = 4230145571524018736L;
	private TileGrid tileGrid;
	private int rows;
	private int columns;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem OPEN, NEW;
	private JRadioButtonMenuItem EASY, REGULAR, HARD;
	private JRadioButtonMenuItem DEMO, TWENTY, FIFTY, HUNDRED;
	private ButtonGroup sizeGroup;
	private ButtonGroup shuffleGroup;

	private JFileChooser fc;

	private BufferedImage image;

	public TileGridFrame() {
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		JTextArea log;
		log = new JTextArea(
				"Slide Puzzle Instructions:\nYou can select a file turn into a puzzle with OPEN.\nTo restart the game with the same image, select NEW. To set the difficulty, select options, then DIFFICULTY.",
				5, 20);
		log.setLineWrap(true);
		// log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		add(log, BorderLayout.CENTER);
	}

	/**
	 * Constructor for TileGridFrame
	 * 
	 * @param observable
	 *            The Obervable object (TileGrid) that we should subscribe to
	 * @param newImage
	 *            The image to be used in the puzzle
	 */
	public TileGridFrame(Observable observable, BufferedImage newImage) {
		image = newImage;

		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		observable.addObserver(this);
		if (observable instanceof TileGrid) {
			tileGrid = (TileGrid) observable;
			this.rows = tileGrid.getRows();
			this.columns = tileGrid.getColumns();
			this.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
					if (e.getY() > 50) {
						int column = (e.getX() * columns) / getWidth();
						int row = ((e.getY() - 18) * rows) / getHeight();
						if (tileGrid.canMoveUp(row, column)) {
							tileGrid.moveUp(row, column);
						} else if (tileGrid.canMoveDown(row, column)) {
							tileGrid.moveDown(row, column);
						} else if (tileGrid.canMoveLeft(row, column)) {
							tileGrid.moveLeft(row, column);
						} else if (tileGrid.canMoveRight(row, column)) {
							tileGrid.moveRight(row, column);
						}
					}
				}

				public void mouseReleased(MouseEvent e) {
				}
			});
			setLayout(new GridLayout(rows, columns));
		}
		update(observable);
	}

	/**
	 * This is called whenever there is an update to the data in tileGrid
	 * 
	 * @param o
	 *            The tileGrid class
	 */
	public void update(Observable o) {
		if (o instanceof TileGrid) {
			if (tileGrid.isSolved() == true) {
				JFrame showImage = new DisplayImageFrame(
						tileGrid.getPuzzleImage());
				showImage.setSize(tileGrid.getPuzzleImageWidth(),
						tileGrid.getPuzzleImageHeight());
				showImage.setTitle("Good Job!!!");
				super.dispose();
				showImage.setVisible(true);
				showImage.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

				showImage.addWindowListener(new WindowListener() {
					
					@Override
					public void windowClosed(WindowEvent e) {
						PuzzleDisplay.callPrompt();
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
					}

					@Override
					public void windowClosing(WindowEvent e) {
					}

					@Override
					public void windowDeactivated(WindowEvent e) {
					}

					@Override
					public void windowDeiconified(WindowEvent e) {
					}

					@Override
					public void windowIconified(WindowEvent e) {
					}

					@Override
					public void windowOpened(WindowEvent e) {
					}
				});
			} else {
				setLayout(new GridLayout(rows, columns));
				for (JLabel label : tileGrid) {
					Tile tile = (Tile) label;
					tile.displayImage();
					add(label);
					// Debugging code below
					// System.out.printf("Solved row value %d column value %d\n",
					// tile.getSolvedRow(), tile.getSolvedColumn());
				}
			}
		}
		revalidate();
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		update(o);
	}

	/**
	 * Creates a menubar to be attached to the Frame.
	 * 
	 * @return
	 */
	public JMenuBar createMenuBar() {
		menuBar = new JMenuBar();

		menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_G);
		menuBar.add(menu);

		OPEN = new JMenuItem("Open", KeyEvent.VK_O);
		OPEN.addActionListener(new OpenListener(this));
		menu.add(OPEN);

		NEW = new JMenuItem("New", KeyEvent.VK_O);
		NEW.addActionListener(new OpenListener(this));
		menu.add(NEW);

		menu = new JMenu("Settings");
		menu.setMnemonic(KeyEvent.VK_S);
		menuBar.add(menu);

		JMenu diffMenu = new JMenu("Difficulty");
		diffMenu.setMnemonic(KeyEvent.VK_D);

		sizeGroup = new ButtonGroup();
		EASY = new JRadioButtonMenuItem("Easy(3x3)");
		EASY.setSelected(true);
		EASY.setMnemonic(KeyEvent.VK_E);
		sizeGroup.add(EASY);
		diffMenu.add(EASY);

		REGULAR = new JRadioButtonMenuItem("Normal(4x4)");
		REGULAR.setMnemonic(KeyEvent.VK_N);
		sizeGroup.add(REGULAR);
		diffMenu.add(REGULAR);

		HARD = new JRadioButtonMenuItem("Hard(5x5)");
		HARD.setMnemonic(KeyEvent.VK_H);
		sizeGroup.add(HARD);
		diffMenu.add(HARD);

		menu.add(diffMenu);
		
		JMenu shuffleMenu = new JMenu("Shuffles");
		shuffleMenu.setMnemonic(KeyEvent.VK_F);

		shuffleGroup = new ButtonGroup();
		DEMO = new JRadioButtonMenuItem("Demo (1 time)");
		DEMO.setSelected(true);
		shuffleGroup.add(DEMO);
		shuffleMenu.add(DEMO);

		TWENTY = new JRadioButtonMenuItem("20 Times");
		shuffleGroup.add(TWENTY);
		shuffleMenu.add(TWENTY);

		FIFTY = new JRadioButtonMenuItem("50 Times");
		shuffleGroup.add(FIFTY);
		shuffleMenu.add(FIFTY);
		
		HUNDRED = new JRadioButtonMenuItem("100 Times");
		shuffleGroup.add(HUNDRED);
		shuffleMenu.add(HUNDRED);

		menu.add(shuffleMenu);

		return menuBar;
	}

	class OpenListener implements ActionListener {

		private int shuffleIterations = 0;
		private int difficulty = 1;
		private static final int seconds = 3;
		private static final int milli = seconds * 1000;

		private TileGridFrame thisFrame;

		public OpenListener(TileGridFrame f) {
			super();
			thisFrame = f;
		}

		public void drawFrame(BufferedImage image, int difficulty) {

			/**
			 * This internal class is used so that we can set a timer to close
			 * the image frame and set the puzzle frame to visible
			 */
			class TileGridDrawListener implements ActionListener {

				private DisplayImageFrame displayImageFrame;
				private TileGridFrame tileGridFrame;

				public TileGridDrawListener(
						DisplayImageFrame displayImageFrame,
						TileGridFrame tileGridFrame) {
					this.displayImageFrame = displayImageFrame;
					this.tileGridFrame = tileGridFrame;
				}

				public void actionPerformed(ActionEvent e) {
					displayImageFrame.dispose();
					tileGridFrame.setVisible(true);
				}
			}

			DisplayImageFrame displayImageFrame = new DisplayImageFrame(image);
			displayImageFrame.setSize(image.getWidth(), image.getHeight());
			displayImageFrame
					.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			displayImageFrame.setVisible(true);

			TileGrid tileGrid = new TileGrid(image, difficulty, difficulty);
			tileGrid.shuffle(shuffleIterations);

			TileGridFrame tileGridFrame = new TileGridFrame(tileGrid, image);
			tileGridFrame.setLocation((Toolkit.getDefaultToolkit()
					.getScreenSize().width - image.getWidth()) / 2, (Toolkit
					.getDefaultToolkit().getScreenSize().height - image
					.getHeight()) / 2);

			TileGridDrawListener tileGridDrawListener = new TileGridDrawListener(
					displayImageFrame, tileGridFrame);
			Timer timer = new Timer(milli, tileGridDrawListener);
			timer.setRepeats(false);
			timer.start();
			tileGridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			tileGridFrame.setJMenuBar(tileGridFrame.createMenuBar());
			tileGridFrame.setSize(tileGrid.getPuzzleImageWidth(),
					tileGrid.getPuzzleImageHeight());
			tileGridFrame.setResizable(false);
		}

		public void actionPerformed(ActionEvent e) {
			if (EASY.isSelected()) {
				difficulty = 3;
			} else if (REGULAR.isSelected()) {
				difficulty = 4;
			} else if (HARD.isSelected()) {
				difficulty = 10;
			}
			
			if(DEMO.isSelected()) {
				shuffleIterations = 1;
			} else if(TWENTY.isSelected()) {
				shuffleIterations = 20;
			} else if(FIFTY.isSelected()) {
				shuffleIterations = 50;
			} else if(HUNDRED.isSelected()) {
				shuffleIterations = 100;
			}

			if (e.getSource() == OPEN) {
				int returnVal = fc.showOpenDialog(TileGridFrame.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					thisFrame.dispose();
					File imageFile = fc.getSelectedFile();
					image = null;
					try {
						image = ImageIO.read(imageFile);
					} catch (IOException e1) {
						System.err.println("IMAGE CORRUPTION ERROR");
					}
					drawFrame(image, difficulty);
				}
			} else if (e.getSource() == NEW && image != null) {
				drawFrame(image, difficulty);
				dispose();
			}
		}

		public void createFrame() throws IOException, InterruptedException {

		}
	}
}
