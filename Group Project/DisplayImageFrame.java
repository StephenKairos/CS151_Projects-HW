import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DisplayImageFrame extends JFrame {
	
	BufferedImage image;
	
	public DisplayImageFrame(){};
	public DisplayImageFrame(BufferedImage image) {
		this.image = image;
		setLayout(new GridLayout(1, 1));
		JLabel display = new JLabel();
		display.setIcon(new ImageIcon(this.image));
		this.add(display);
	}
}
