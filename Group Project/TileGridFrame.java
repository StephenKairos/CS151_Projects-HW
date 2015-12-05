import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

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

	private JMenuItem OPEN, NEW;
	private JRadioButtonMenuItem EASY, REGULAR, HARD;
	private ButtonGroup group;
	
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
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		add(log, BorderLayout.CENTER);
	}

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
					int column = (e.getX() * columns) / getWidth();
					int row = ((e.getY()) * rows) / getHeight();
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

				public void mouseReleased(MouseEvent e) {
				}
			});
			setLayout(new GridLayout(rows, columns));
		}
		update(observable);
	}

	public void update(Observable o) {
		if (o instanceof TileGrid) {
			if (tileGrid.isSolved() == true) {
				Frame showImage = new DisplayImageFrame(
						tileGrid.getPuzzleImage());
				showImage.setSize(tileGrid.getPuzzleImageWidth(),
						tileGrid.getPuzzleImageHeight());
				showImage.setTitle("Good Job!!!");
				super.dispose();
				showImage.setVisible(true);
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

	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		DisplayImageFrame displayImageFrame;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
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

		group = new ButtonGroup();
		EASY = new JRadioButtonMenuItem("Easy(3x3)");
		EASY.setSelected(true);
		EASY.setMnemonic(KeyEvent.VK_E);
		group.add(EASY);
		diffMenu.add(EASY);

		REGULAR = new JRadioButtonMenuItem("Normal(4x4)");
		REGULAR.setMnemonic(KeyEvent.VK_N);
		group.add(REGULAR);
		diffMenu.add(REGULAR);

		HARD = new JRadioButtonMenuItem("Hard(5x5)");
		HARD.setMnemonic(KeyEvent.VK_H);
		group.add(HARD);
		diffMenu.add(HARD);

		menu.add(diffMenu);

		return menuBar;
	}
	
	

	class OpenListener implements ActionListener {

		private TileGridFrame thisFrame;
		private DisplayImageFrame displayImageFrame;

		public OpenListener(TileGridFrame f) {
			super();
			thisFrame = f;
		}

		public void drawFrame(BufferedImage image, int difficulty) {
		   
		   class TileGridDrawListener implements ActionListener {
		      
		      private DisplayImageFrame displayImageFrame;
		      private TileGridFrame tileGridFrame;
		      
		      public TileGridDrawListener (DisplayImageFrame displayImageFrame, TileGridFrame tileGridFrame) {
		         this.displayImageFrame = displayImageFrame;
		         this.tileGridFrame = tileGridFrame;
		      }
		      
		      public void actionPerformed(ActionEvent e)
		      {
		         displayImageFrame.dispose();
		         tileGridFrame.setVisible(true);
		      }
		   }
		   
	      DisplayImageFrame displayImageFrame = new DisplayImageFrame(image);
         displayImageFrame.setSize(image.getWidth(), image.getHeight());
         displayImageFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
         displayImageFrame.setVisible(true);
         TileGrid tileGrid = new TileGrid(image, difficulty, difficulty);
         tileGrid.shuffle(100);
         TileGridFrame tileGridFrame = new TileGridFrame(tileGrid, image);
         TileGridDrawListener tileGridDrawListener = new TileGridDrawListener(displayImageFrame, tileGridFrame);
         Timer timer = new Timer(3000, tileGridDrawListener);
         timer.setRepeats(false);
         timer.start();
         tileGridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         tileGridFrame.setJMenuBar(tileGridFrame.createMenuBar());
         tileGridFrame.setSize(tileGrid.getPuzzleImageWidth(),
               tileGrid.getPuzzleImageHeight());
         tileGridFrame.setResizable(false);
		}
		
		public void actionPerformed(ActionEvent e) {
			int difficulty = 0;
			if(EASY.isSelected()) {
				difficulty = 3;
			} else if(REGULAR.isSelected()) {
				difficulty = 4;
			} else if(HARD.isSelected()) {
				difficulty = 5;
			}
			
			if(e.getSource() == OPEN) {
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
			} else if(e.getSource() == NEW && image != null) {
				drawFrame(image, difficulty);
			}
		}

		public void createFrame() throws IOException, InterruptedException {

		}
	}
}
