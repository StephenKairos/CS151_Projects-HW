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
      File imageFile = new File("Flower.jpg");
      BufferedImage image = ImageIO.read(imageFile);
      DisplayImageFrame displayImageFrame = new DisplayImageFrame(image);
      displayImageFrame.setSize(image.getWidth(), image.getHeight());
      displayImageFrame.setVisible(true);
      TimeUnit.SECONDS.sleep(2);
      displayImageFrame.dispose();
      TileGrid tileGrid = new TileGrid(image, 3, 3);
      tileGrid.shuffle(1);
      TileGridFrame tileGridFrame = new TileGridFrame(tileGrid);
      tileGridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      tileGridFrame.setSize(tileGrid.getPuzzleImageWidth(), tileGrid.getPuzzleImageHeight());
      tileGridFrame.setTitle("Tile Puzzle Game - " + imageFile.getName());
      tileGridFrame.setVisible(true);
   }


}
