import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class TileGridFrame extends JFrame implements Observer {
	/**
    * 
    */
	private static final long serialVersionUID = 4230145571524018736L;
	private TileGrid tileGrid;
	private int rows;
	private int columns;

	public TileGridFrame(Observable observable) {
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
					int row = (e.getY() * rows) / getHeight();
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
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);
 
        //a group of JMenuItems
        menuItem = new JMenuItem("A text-only menu item",
                                 KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);
 
        //a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
 
        rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);
 
        rbMenuItem = new JRadioButtonMenuItem("Another one");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);
 
        //a group of check box menu items
        menu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        menu.add(cbMenuItem);
 
        cbMenuItem = new JCheckBoxMenuItem("Another one");
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        menu.add(cbMenuItem);
 
        //a submenu
        menu.addSeparator();
        submenu = new JMenu("A submenu");
        submenu.setMnemonic(KeyEvent.VK_S);
 
        menuItem = new JMenuItem("An item in the submenu");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        submenu.add(menuItem);
 
        menuItem = new JMenuItem("Another item");
        submenu.add(menuItem);
        menu.add(submenu);
 
        //Build second menu in the menu bar.
        menu = new JMenu("Another Menu");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(menu);
 
        return menuBar;
    }
}
