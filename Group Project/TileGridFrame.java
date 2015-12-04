import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TileGridFrame extends JFrame implements Observer
{
   /**
    * 
    */
   private static final long serialVersionUID = 4230145571524018736L;
   private TileGrid tileGrid;
   private int rows;
   private int columns;
   private boolean solved;
   
   public TileGridFrame(Observable observable) {
      observable.addObserver(this);
      this.solved = false;
      if (observable instanceof TileGrid) {
         tileGrid = (TileGrid)observable;
         this.rows = tileGrid.getRows();
         this.columns = tileGrid.getColumns();
         this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
               
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
               
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
               
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
               if (solved == true)
                  return;
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

            @Override
            public void mouseReleased(MouseEvent e)
            {
               
            }
         });
         setLayout(new GridLayout(rows, columns));
      }
      update(observable);
   }
   
   public void update(Observable o)
   {
      if (o instanceof TileGrid) {
         if (tileGrid.isSolved() == true) {
            Frame showImage = new DisplayImageFrame(tileGrid.getPuzzleImage());
            showImage.setSize(tileGrid.getPuzzleImageWidth(), tileGrid.getPuzzleImageHeight());
            showImage.setTitle("Good Job!!!");
            super.dispose();
            showImage.setVisible(true);        
         } else {
            setLayout(new GridLayout(rows, columns));
            for (JLabel label : tileGrid) {
               Tile tile = (Tile) label;
               tile.displayImage();
               add(label);
            }
         }
      }
      pack();
      validate();
      repaint();
   }

   @Override
   public void update(Observable o, Object arg)
   {
      update(o);
   }
}
