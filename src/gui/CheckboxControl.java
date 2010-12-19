package gui;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSpinner;
import java.awt.Insets;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerModel;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JCheckBox;

public class CheckboxControl extends JPanel {
	private JLabel lblSpin;
	private JCheckBox checkbox;

	/**
	 * Create the panel.
	 */
	public CheckboxControl() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{80, 21, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblSpin = new JLabel("ChbxControl");
		lblSpin.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		GridBagConstraints gbc_lblSpin = new GridBagConstraints();
		gbc_lblSpin.insets = new Insets(0, 0, 0, 5);
		gbc_lblSpin.anchor = GridBagConstraints.EAST;
		gbc_lblSpin.gridx = 0;
		gbc_lblSpin.gridy = 0;
		add(lblSpin, gbc_lblSpin);
		
		checkbox = new JCheckBox();
		checkbox.setPreferredSize(new Dimension(30, 18));
		GridBagConstraints gbc_control = new GridBagConstraints();
		gbc_control.fill = GridBagConstraints.HORIZONTAL;
		gbc_control.gridx = 1;
		gbc_control.gridy = 0;
		add(checkbox, gbc_control);
	}

	public String getLabelText() {
		return lblSpin.getText();
	}
	public void setLabelText(String text) {
		lblSpin.setText(text);
	}
	
}
