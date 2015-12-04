import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.swing.Icon;

public interface IconCreator
{
   public Icon createIcon(BufferedImage image);
   public Icon createIcon(BufferedImage image, Color color);
}
