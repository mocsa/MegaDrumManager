package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PadButton extends JButton {
	private String name;

	/**
	 * Create the panel.
	 */
	public PadButton(String text) {
		name = text;
		setIcon(new ImageIcon(PadCommonControls.class.getResource("/com/sun/java/swing/plaf/motif/icons/ScrollRightArrowActive.gif")));
	}

	public String getName() {
		return name;
	}
}
