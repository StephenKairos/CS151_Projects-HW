import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public class PuzzleDisplay
{

   public static void main(String[] args) throws IOException
   {
      File imageFile = new File("Flower.jpg");
      TileGrid tileGrid = new TileGrid(imageFile, 3, 3);
      TileGridFrame tileGridFrame = new TileGridFrame(tileGrid);
      tileGridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      tileGridFrame.setSize(tileGrid.getPuzzleImageWidth(), tileGrid.getPuzzleImageHeight());
      tileGridFrame.setTitle("Tile Puzzle Game");
      tileGridFrame.setVisible(true);
   }

}
