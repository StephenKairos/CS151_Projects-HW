import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class CreateBlankIcon implements IconCreator
{

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
