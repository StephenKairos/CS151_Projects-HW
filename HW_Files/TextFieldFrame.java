package HW_Files;

import java.awt.*;

import javax.swing.*;

public class TextFieldFrame extends JFrame {
	public TextFieldFrame(String name) {
		super(name);
	}
	
	public void addToPane(Container pane) {
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 1;
		
        c.gridy = 0;
	    pane.add(new JLabel("Constraint 1"), c);
	    
        c.gridy = 1;
	    pane.add(new JTextField(20), c);
	    
	    c.gridy = 2;
	    pane.add(new JLabel(" "), c);
	    
	    c.gridy = 3;
	    pane.add(new JLabel("Constraint 2"), c);
	    
	    c.gridy = 4;
	    pane.add(new JTextField(), c);
	    
	    c.gridy = 5;
	    pane.add(new JLabel(" "), c);
	    
	    c.gridy = 6;
	    pane.add(new JLabel("Constraint 3"), c);
	    
	    c.gridy = 7;
	    pane.add(new JTextField(), c);
	}
}
