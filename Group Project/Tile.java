import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Tile extends JLabel
{
   IconCreator    iconSetter;
   BufferedImage image;
   Icon          icon;
   private int   solvedRow;
   private int   solvedColumn;

   private Tile()
   {
   };

   public Tile(BufferedImage image, int solvedRow, int solvedColumn)
   {
      this.solvedRow = solvedRow;
      this.solvedColumn = solvedColumn;
      this.image = image;
   }

   public boolean isSolved(int row, int column)
   {
      if (row == solvedRow && column == solvedColumn)
         return true;
      return false;
   }

   public void displayImage()
   {
      if (null == icon)
         getIcon();
      this.setIcon(icon);
   }

   public int getSolvedRow()
   {
      return solvedRow;
   }

   public int getSolvedColumn()
   {
      return solvedColumn;
   }
   
   public void callCreateIcon()
   {
      if (null == iconSetter)
         iconSetter = new CreateTileIcon();
      icon = iconSetter.createIcon(image);
   }

   public void setIconType(IconCreator iconSetter)
   {
      this.iconSetter = iconSetter;
   }

}
