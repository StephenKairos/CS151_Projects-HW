import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DisplayImageFrame extends JFrame {
	
   /**
    * Creates a JFrame of type DisplayImageFrame
    * @param  image   BufferedImage that will be shown
    *                 in the frame.
    */
	private DisplayImageFrame(){};
	public DisplayImageFrame(BufferedImage image) {
		JLabel display = new JLabel();
		display.setIcon(new ImageIcon(image));
		add(display);
	}
}
