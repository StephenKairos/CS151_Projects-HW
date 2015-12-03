import javax.swing.JLabel;

public abstract class Tile extends JLabel {
	private int solvedRow;
	private int solvedColumn;
	private Tile(){};
	
	public Tile(int solvedRow, int solvedColumn) {
	   this.solvedRow = solvedRow;
	   this.solvedColumn = solvedColumn;
	}
	
	public boolean isSolved(int row, int column) {
	   if (row == solvedRow && column == solvedColumn)
	      return true;
	   return false;
	}
	
	public void displayImage() {
	   
	}

}
