import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class CreateTileIcon implements IconCreator
{

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
