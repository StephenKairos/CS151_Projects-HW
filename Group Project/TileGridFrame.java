import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
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
   
   public TileGridFrame(Observable observable) {
      observable.addObserver(this);
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
         setLayout(new GridLayout(rows, columns));
         for (JLabel label : tileGrid) {
            Tile tile = (Tile) label;
            tile.displayImage();
            add(label);
            // Debugging code below
            System.out.printf("Solved row value %d column value %d\n", tile.getSolvedRow(), tile.getSolvedColumn());
         }
      }
      revalidate();
      repaint();
   }

   @Override
   public void update(Observable o, Object arg)
   {
      update(o);
   }
}
