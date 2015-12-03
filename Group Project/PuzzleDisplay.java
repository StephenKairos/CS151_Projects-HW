import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public class PuzzleDisplay
{

   public static void main(String[] args) throws IOException
   {
      File imageFile = new File("Flower.jpg");
      TileGrid tileGrid = new TileGrid(imageFile, 10, 10);
      TileGridFrame tileGridFrame = new TileGridFrame(tileGrid, 10, 10);
      tileGridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      tileGridFrame.setSize(1000, 1000);
      tileGridFrame.setVisible(true);
   }

}
