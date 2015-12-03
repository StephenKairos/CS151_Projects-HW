import java.awt.GridLayout;
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
   
   public TileGridFrame(Observable observable, int rows, int columns) {
      observable.addObserver(this);
      if (observable instanceof TileGrid) {
         tileGrid = (TileGrid)observable;
         this.rows = tileGrid.getRows();
         this.columns = tileGrid.getColumns();
         setLayout(new GridLayout(rows, columns));
      }
      update(observable);
   }
   
   public void update(Observable o)
   {
      if (o instanceof TileGrid) {
         setLayout(new GridLayout(rows, columns));
         for (JLabel label : tileGrid) {
            add(label);
         }
         pack();
      }
      
   }

   @Override
   public void update(Observable o, Object arg)
   {
      update(o);
   }

}
