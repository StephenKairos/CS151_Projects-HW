import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class PuzzleDisplay
{

   public static void main(String[] args) throws IOException, InterruptedException
   {
      TileGridFrame tileGridFrame = new TileGridFrame();
      tileGridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      tileGridFrame.setJMenuBar(tileGridFrame.createMenuBar());
      tileGridFrame.pack();
      tileGridFrame.setTitle("Tile Puzzle Game");
      tileGridFrame.setSize(430, 130);
      tileGridFrame.setVisible(true);
   }


}
