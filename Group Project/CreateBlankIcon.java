import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;


public class CreateBlankIcon implements IconCreator
{
   /**
    * Provides a concrete strategy method for creating a blank tile
    * icon.
    * @param  image   a BufferedImage subimage that would have been
    *                 used to draw the icon. Instead it will be
    *                 used to create a blank icon of the same
    *                 dimensions
    * @param  color   the color to be used to fill in the blank icon
    * @return         an icon with the dimensions of image
    * @see            IconCreator
    */
   @Override
   public Icon createIcon(BufferedImage image, Color color)
   {
      BufferedImage blankImage = new BufferedImage(image.getWidth(),
               image.getHeight(),
               BufferedImage.TYPE_INT_RGB);
      Graphics2D g = blankImage.createGraphics();
      g.setColor(color);
      g.fillRect(0, 0, blankImage.getWidth(), blankImage.getHeight());
      g.dispose();
      
      return new ImageIcon(blankImage);
   }

   @Override
   public Icon createIcon(BufferedImage image)
   {
      return createIcon(image, Color.GRAY);
   }

}
