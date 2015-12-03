import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ImageTile extends Tile {
   private BufferedImage image;

   public ImageTile(BufferedImage image, int solvedRow, int solvedColumn)
   {
      super(solvedRow, solvedColumn);
      this.image = image;
   }
   @Override
   public void displayImage() {
      ImageIcon icon = new ImageIcon(image);
      this.setIcon(icon);
   }

}
