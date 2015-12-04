import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DisplayImageFrame extends JFrame 
{
	BufferedImage image;
	public DisplayImageFrame(){};
	public DisplayImageFrame(BufferedImage image) 
	{
		add(new JLabel(new ImageIcon(image)));
	}
}
