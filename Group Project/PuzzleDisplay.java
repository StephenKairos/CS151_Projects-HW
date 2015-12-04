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
      File imageFile = new File("C:/Users/StephenKairos/Desktop/CS151_Projects-HW/Flower.jpg");
      BufferedImage image = ImageIO.read(imageFile);
//      DisplayImageFrame displayImageFrame = new DisplayImageFrame(image);
//      displayImageFrame.setSize(image.getWidth(), image.getHeight());
//      displayImageFrame.setVisible(true);
//      TimeUnit.SECONDS.sleep(5);
//      displayImageFrame.dispose();
      TileGrid tileGrid = new TileGrid(image, 3, 3);
      TileGridFrame tileGridFrame = new TileGridFrame(tileGrid);
      tileGridFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      tileGridFrame.setSize(tileGrid.getPuzzleImageWidth(), tileGrid.getPuzzleImageHeight());
      tileGridFrame.setTitle("Tile Puzzle Game - " + imageFile.getName());
      tileGridFrame.setResizable(false);
      tileGridFrame.setVisible(true);
   }


}
