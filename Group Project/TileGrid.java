import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Random;

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
   
   private TileGrid() {};

   private int iterRow;
   private int iterColumn;

   /**
    * TileGrid constructor
    * @param image   BufferedImage to be broken into tiles
    * @param rows    number of rows
    * @param columns number of columns
    */
   public TileGrid(BufferedImage image, int rows, int columns)
   {
	  puzzleImage = image;
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

   /**
    * Shows the ability of a tile to move in the given direction
    * @param row     the row the tile is in
    * @param column  the column the tile is in
    * @return        true if the tile can be moved in the given
    *                direction. False if not.
    */
   public boolean canMoveUp(int row, int column)
   {
      if ((row - 1) >= 0 && tileGrid.get(row - 1).get(column) == blankTile)
         return true;
      return false;
   }

   /**
    * Shows the ability of a tile to move in the given direction
    * @param row     the row the tile is in
    * @param column  the column the tile is in
    * @return        true if the tile can be moved in the given
    *                direction. False if not.
    */
   public boolean canMoveDown(int row, int column)
   {
      if ((row + 1) < rows && tileGrid.get(row + 1).get(column) == blankTile)
         return true;
      return false;
   }

   /**
    * Shows the ability of a tile to move in the given direction
    * @param row     the row the tile is in
    * @param column  the column the tile is in
    * @return        true if the tile can be moved in the given
    *                direction. False if not.
    */
   public boolean canMoveLeft(int row, int column)
   {
      if ((column - 1) >= 0 && tileGrid.get(row).get(column - 1) == blankTile)
         return true;
      return false;
   }

   /**
    * Shows the ability of a tile to move in the given direction
    * @param row     the row the tile is in
    * @param column  the column the tile is in
    * @return        true if the tile can be moved in the given
    *                direction. False if not.
    */
   public boolean canMoveRight(int row, int column)
   {
      if ((column + 1) < columns && tileGrid.get(row).get(column + 1) == blankTile)
         return true;
      return false;
   }
   
   /**
    * Attempts to move a tile in the given direction
    * @param row     the row the tile is in
    * @param column  the column the tile is in
    */
   public void moveUp(int row, int column)
   {
      if (!canMoveUp(row, column))
         return;
      int newRow = row - 1;
      int newColumn = column;
      swapTiles(row, column, newRow, newColumn);
   }
   
   /**
    * Attempts to move a tile in the given direction
    * @param row     the row the tile is in
    * @param column  the column the tile is in
    */
   public void moveDown(int row, int column)
   {
      if (!canMoveDown(row, column))
         return;
      int newRow = row + 1;
      int newColumn = column;
      swapTiles(row, column, newRow, newColumn);
   }
   
   /**
    * Attempts to move a tile in the given direction
    * @param row     the row the tile is in
    * @param column  the column the tile is in
    */
   public void moveLeft(int row, int column)
   {
      if (!canMoveLeft(row, column))
         return;
      int newRow = row;
      int newColumn = column - 1;
      swapTiles(row, column, newRow, newColumn);
   }
   
   /**
    * Attempts to move a tile in the given direction
    * @param row     the row the tile is in
    * @param column  the column the tile is in
    */
   public void moveRight(int row, int column)
   {
      if (!canMoveRight(row, column))
         return;
      int newRow = row;
      int newColumn = column + 1;
      swapTiles(row, column, newRow, newColumn);
   }
   
   /**
    * Swaps tile locations in tileGrid
    * @param rowFrom       the row to be moved from
    * @param columnFrom    the column to be moved from
    * @param rowTo         the row to be moved to
    * @param columnTo      the column to be moved to
    */
   public void swapTiles(int rowFrom, int columnFrom, int rowTo, int columnTo) {
      Tile tileFrom = tileGrid.get(rowFrom).get(columnFrom);
      Tile tileTo = tileGrid.get(rowTo).get(columnTo);
      tileGrid.get(rowFrom).set(columnFrom, tileTo);
      tileGrid.get(rowTo).set(columnTo, tileFrom);
      dataChanged();
   }

   /**
    * Shows the number of rows in this tileGrid
    * @return number of rows in tileGrid
    */
   public int getRows()
   {
      return rows;
   }

   /**
    * Shows the number of columns in this tileGrid
    * @return number of columns in tileGrid
    */
   public int getColumns()
   {
      return columns;
   }
   
   /**
    * Implementation of iterator hasNext()
    */
   @Override
   public boolean hasNext()
   {
      if (iterColumn < columns || iterRow < rows - 1)
         return true;
      return false;
   }
   
   /**
    * Implementation of iterator next()
    */
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

   /**
    * Our iterator override. This allows for tiles to be returned
    * in the order they should be drawn in for GridLayout.
    */
   @Override
   public Iterator<JLabel> iterator()
   {
      this.iterRow = 0;
      this.iterColumn = 0;
      return this;
   }

   /**
    * Gives the width in pixels of the puzzleImage
    * @return width in pixels of the puzzleImage
    */
   public int getPuzzleImageWidth()
   {
      return puzzleImageWidth;
   }

   /**
    * Gives the height in pixels of the puzzleImage
    * @return height in pixels of the puzzleImage
    */
   public int getPuzzleImageHeight()
   {
      return puzzleImageHeight;
   }
   
   /**
    * Method to be called whenever data in tileGrid is modified.
    * This will notify observers that data has changed.
    */
   public void dataChanged() 
   {
      setChanged();
      notifyObservers();
   }
   
   /**
    * Loops through tileGrid and checks isSolved for every tile
    * @return true if the entire puzzle is solved, false if not.
    */
   public boolean isSolved() {
	   boolean solved = true;
	   for (int i = 0; i < rows; i++) {
         for (int j = 0; j < columns; j++) {
			   solved = tileGrid.get(i).get(j).isSolved(i, j);
			   if (solved == false)
				   return solved;
		   }
	   }
	   return solved;
   }
   
   /**
    * Shuffles the puzzle
    * @param iterations how many times the blank tile should be moved
    */
   public void shuffle(int iterations) {
	   int blankRow = 0;
	   int blankColumn = 0;
	   int lastDirection = 0;
	   Random generator = new Random(System.currentTimeMillis());
	   while (iterations > 0) {
	      int direction = generator.nextInt(4);
	      if (direction == lastDirection) {
	         continue;
	      } else {
	         lastDirection = direction;
	      }
	      if (direction == 0 && blankRow -1 >= 0) // Up
	      {
	         swapTiles(blankRow, blankColumn, blankRow -1, blankColumn);
	         blankRow--;
	         iterations--;
	      } else if (direction == 1 && blankRow + 1 < rows) // Down
	      {
	         swapTiles(blankRow, blankColumn, blankRow + 1, blankColumn);
	         blankRow++;
	         iterations--;
	      } else if (direction == 2 && blankColumn - 1 >= 0) // Left
	      {
	         swapTiles(blankRow, blankColumn, blankRow, blankColumn - 1);
	         blankColumn--;
	         iterations--;
	      } else if (direction == 3 && blankColumn + 1 < columns) // Right
	      {
	         swapTiles(blankRow, blankColumn, blankRow, blankColumn + 1);
	         blankColumn++;
	         iterations--;
	      }
	   }
	   
   }
   
   /**
    * Gives the image used in the puzzle
    * @return BufferedImage object of the image
    */
   public BufferedImage getPuzzleImage() {
      return puzzleImage;
   }
}