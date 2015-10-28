import java.awt.*;
import javax.swing.*;

public class BarOneTester {
	public static void main(String[] args) {
		BarGraphFrame bFrame = new BarGraphFrame("Graph of Infinite Knowledge");
		TextFieldFrame tFrame = new TextFieldFrame("Data");
		
		tFrame.addToPane(tFrame.getContentPane());
		JTextField tField1 = new JTextField("");
	    
	    tFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    bFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    bFrame.setSize(400, 400);
	    bFrame.setVisible(true);
	    bFrame.setLocationRelativeTo(tFrame.getComponent(0));
	    
	    tFrame.setSize(250, 300);
	    tFrame.setVisible(true);
	    tFrame.setLocationRelativeTo(bFrame.getComponent(0));
	}
}
