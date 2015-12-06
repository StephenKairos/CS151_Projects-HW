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

   private Tile() {};

   /**
    * Constructor for the Tile class
    * @param image         BufferedImage subimage to be used for 
    *                      the tile
    * @param solvedRow     The row this tile must be in to be 
    *                      solved
    * @param solvedColumn  The column this tile must be in to be
    *                      solved.
    */
   public Tile(BufferedImage image, int solvedRow, int solvedColumn)
   {
      this.solvedRow = solvedRow;
      this.solvedColumn = solvedColumn;
      this.image = image;
   }

   /**
    * Returns the status of whether the tile is in the
    * correct position or not.
    * @param row     The row the tile must be in to be
    *                solved.
    * @param column  The column the tile must be in to
    *                be solved.
    * @return        true if the tile is in the correct
    *                position, false if not.
    */
   public boolean isSolved(int row, int column)
   {
      if (row == solvedRow && column == solvedColumn)
         return true;
      return false;
   }

   /**
    * Sets up the icon image for the tile from the
    * local BufferedImage upon instantiation.
    */
   public void displayImage()
   {
      if (null == icon)
         getIcon();
      this.setIcon(icon);
   }

   /**
    * Returns which row the tile must be in to be solved
    * @return row the tile is solved in
    */
   public int getSolvedRow()
   {
      return solvedRow;
   }

   /**
    * Returns which column the tile must be in to be solved
    * @return column the tile is solved in
    */
   public int getSolvedColumn()
   {
      return solvedColumn;
   }
   
   /**
    * This is our strategy method. There are currently
    * two strategies that can be set here:
    * CreateBlankIcon() and CreateTileIcon()
    */
   public void callCreateIcon()
   {
      if (null == iconSetter)
         iconSetter = new CreateTileIcon();
      icon = iconSetter.createIcon(image);
   }

   /**
    * Strategy method to change the iconSetter during runtime
    * @param iconSetter a concrete implementation of the 
    * iconCreator class.
    */
   public void setIconType(IconCreator iconSetter)
   {
      this.iconSetter = iconSetter;
   }

}
