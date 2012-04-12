package gui;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;

public class CSpinner extends JSpinner {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3742566974360755672L;
	private int index;
	
	/**
	 * Create the panel.
	 */
	public CSpinner(int i) {
		index = i;
		setFont(new Font("Segoe UI", Font.PLAIN, 10));
	}
	
	public int getIndex() {
		return index;
	}

}
