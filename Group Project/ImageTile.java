import java.awt.image.BufferedImage;

public class ImageTile extends Tile {
   private BufferedImage image;

   public ImageTile(BufferedImage image, int solvedRow, int solvedColumn)
   {
      super(solvedRow, solvedColumn);
      this.image = image;
   }

}
