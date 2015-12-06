import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class CreateTileIcon implements IconCreator
{

   /**
    * Provides a concrete strategy method for creating an image tile
    * icon.
    * @param  image   a BufferedImage subimage that is used to draw
    *                 the icon.
    * @return         an icon with the dimensions of image
    * @see            IconCreator
    */
   @Override
   public Icon createIcon(BufferedImage image)
   {
      return new ImageIcon(image);
   }

   @Override
   public Icon createIcon(BufferedImage image, Color color)
   {
      return createIcon(image);
   }

}
