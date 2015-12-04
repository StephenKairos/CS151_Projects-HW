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
	
	private JFileChooser fc;
	private int menuHeight;

	public TileGridFrame(Observable observable) {
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
					System.out.println(menuHeight);
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
			setLayout(new GridLayout(rows, columns));
			for (JLabel label : tileGrid) {
				Tile tile = (Tile) label;
				tile.displayImage();
				add(label);
				// Debugging code below
				System.out.printf("Solved row value %d column value %d\n",
						tile.getSolvedRow(), tile.getSolvedColumn());
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
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;
 
        //Create the menu bar.
        menuBar = new JMenuBar();
        
        //Build the first menu.
        menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);
 
        //a group of JMenuItems
        menuItem = new JMenuItem("Open",
                                 KeyEvent.VK_O);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.addActionListener(new openListener(this));
        menu.add(menuItem);
 
        return menuBar;
    }
	
	class openListener implements ActionListener {
		
		private TileGridFrame thisFrame;
		
		public openListener(TileGridFrame f) {
			super();
			thisFrame = f; 
		}
		
		public void actionPerformed(ActionEvent e) {
			int returnVal = fc.showOpenDialog(TileGridFrame.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				thisFrame.dispose();
				File imageFile = fc.getSelectedFile();
				// This is where a real application would open the file.
				try {
					BufferedImage image = ImageIO.read(imageFile);
				      DisplayImageFrame displayImageFrame = new DisplayImageFrame(image);
				      displayImageFrame.setSize(image.getWidth(), image.getHeight());
				      displayImageFrame.setVisible(true);
				      TimeUnit.SECONDS.sleep(5);
				      displayImageFrame.dispose();
					TileGrid tileGrid = new TileGrid(image, 3, 3);
					TileGridFrame tileGridFrame = new TileGridFrame(tileGrid);
					tileGridFrame
							.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				    tileGridFrame.setJMenuBar(tileGridFrame.createMenuBar());
					tileGridFrame.setSize(tileGrid.getPuzzleImageWidth(),
							tileGrid.getPuzzleImageHeight());
					tileGridFrame.setResizable(false);
					tileGridFrame.setVisible(true);
				} catch (IOException | InterruptedException exception) {
					System.err.println("FILE NOT FOUND");
				}
			}
		}
	}
}
