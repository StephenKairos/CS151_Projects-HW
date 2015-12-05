import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DisplayImageFrame extends JFrame {

	private static final long serialVersionUID = 3188801463566847995L;

	/**
	 * Prevent calls to create with empty parameters
	 */
	private DisplayImageFrame() {}
	
	/**
	 * Creates a JFrame of type DisplayImageFrame
	 * 
	 * @param image
	 *            BufferedImage that will be shown in the frame.
	 */
	public DisplayImageFrame(BufferedImage image) {
		JLabel display = new JLabel();
		display.setIcon(new ImageIcon(image));
		setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width - image.getWidth()) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - image.getHeight()) / 2);
		setResizable(false);
		add(display);
	}
}
