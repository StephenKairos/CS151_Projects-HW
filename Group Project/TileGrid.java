import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class TileGrid extends Observable
         implements Iterable<JLabel>, Iterator<JLabel>
{
   private BufferedImage              puzzleImage;
   private int                        puzzleImageWidth;
   private int                        puzzleImageHeight;
   private int                        rows;
   private int                        columns;
   private ArrayList<ArrayList<Tile>> tileGrid;
   private Tile                       blankTile;
   
   private TileGrid()
   {
   };

   private int iterRow;
   private int iterColumn;

   public TileGrid(File imageFile, int rows, int columns) throws IOException
   {
      try
      {
         puzzleImage = ImageIO.read(imageFile);
      }
      catch (IOException e)
      {
         System.err.println("Error reading from file "
                  + imageFile.getAbsolutePath() + imageFile.getName());
         throw e;
      }
      puzzleImageWidth = puzzleImage.getWidth();
      puzzleImageHeight = puzzleImage.getHeight();
      this.rows = rows;
      this.columns = columns;
      tileGrid = new ArrayList();
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0; j < columns; j++)
         {
            BufferedImage tileImage = puzzleImage.getSubimage(
                     j * puzzleImageWidth / columns,
                     i * puzzleImageHeight / rows,
                     puzzleImageWidth / columns,
                     puzzleImageHeight / rows);
            ArrayList columnList;
            if (tileGrid.size() > i)
            {
               columnList = tileGrid.get(i);
            }
            else
            {
               columnList = new ArrayList<Tile>();
               tileGrid.add(i, columnList);
            }
            Tile newTile = new Tile(tileImage, i, j);
            if (null == blankTile) {
               blankTile = newTile;
               newTile.setIconType(new CreateBlankIcon());
            } else {
               newTile.setIconType(new CreateTileIcon());
            }
            newTile.callCreateIcon();
            columnList.add(j, newTile);
         }
      }
   }

   public boolean canMoveUp(int row, int column)
   {
      if ((row - 1) >= 0 && tileGrid.get(row - 1).get(column) == blankTile)
         return true;
      return false;
   }

   public boolean canMoveDown(int row, int column)
   {
      if ((row + 1) < rows && tileGrid.get(row + 1).get(column) == blankTile)
         return true;
      return false;
   }

   public boolean canMoveLeft(int row, int column)
   {
      if ((column - 1) >= 0 && tileGrid.get(row).get(column - 1) == blankTile)
         return true;
      return false;
   }

   public boolean canMoveRight(int row, int column)
   {
      if ((column + 1) < columns && tileGrid.get(row).get(column + 1) == blankTile)
         return true;
      return false;
   }
   
   public void moveUp(int row, int column)
   {
      if (!canMoveUp(row, column))
         return;
      int newRow = row - 1;
      int newColumn = column;
      swapTiles(row, column, newRow, newColumn);
   }
   
   public void moveDown(int row, int column)
   {
      if (!canMoveDown(row, column))
         return;
      int newRow = row + 1;
      int newColumn = column;
      swapTiles(row, column, newRow, newColumn);
   }
   
   public void moveLeft(int row, int column)
   {
      if (!canMoveLeft(row, column))
         return;
      int newRow = row;
      int newColumn = column - 1;
      swapTiles(row, column, newRow, newColumn);
   }
   
   public void moveRight(int row, int column)
   {
      if (!canMoveRight(row, column))
         return;
      int newRow = row;
      int newColumn = column + 1;
      swapTiles(row, column, newRow, newColumn);
   }
   
   public void swapTiles(int rowFrom, int columnFrom, int rowTo, int columnTo) {
      Tile tileFrom = tileGrid.get(rowFrom).get(columnFrom);
      Tile tileTo = tileGrid.get(rowTo).get(columnTo);
      tileGrid.get(rowFrom).set(columnFrom, tileTo);
      tileGrid.get(rowTo).set(columnTo, tileFrom);
      dataChanged();
   }

   public int getRows()
   {
      return rows;
   }

   public int getColumns()
   {
      return columns;
   }

   @Override
   public boolean hasNext()
   {
      if (iterColumn < columns || iterRow < rows - 1)
         return true;
      return false;
   }

   @Override
   public JLabel next()
   {
      JLabel returnLabel;
      if (iterColumn < columns)
      {
         returnLabel = tileGrid.get(iterRow).get(iterColumn);
         iterColumn++;
         return returnLabel;
      }
      else
         if (iterRow < rows - 1)
         {
            iterRow++;
            iterColumn = 0;
            returnLabel = tileGrid.get(iterRow).get(iterColumn);
            iterColumn++;
            return returnLabel;
         }
      throw new NoSuchElementException();
   }

   @Override
   public Iterator<JLabel> iterator()
   {
      this.iterRow = 0;
      this.iterColumn = 0;
      return this;
   }

   public int getPuzzleImageWidth()
   {
      return puzzleImageWidth;
   }

   public int getPuzzleImageHeight()
   {
      return puzzleImageHeight;
   }
   public void dataChanged() 
   {
      setChanged();
      notifyObservers();
   }
}