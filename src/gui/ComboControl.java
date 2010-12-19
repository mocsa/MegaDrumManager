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
import javax.swing.JComboBox;

public class ComboControl extends JPanel {
	private JLabel lblSpin;
	private JComboBox combobox;

	/**
	 * Create the panel.
	 */
	public ComboControl() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{80, 80, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblSpin = new JLabel("ComboControl");
		lblSpin.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		GridBagConstraints gbc_lblSpin = new GridBagConstraints();
		gbc_lblSpin.insets = new Insets(0, 0, 0, 5);
		gbc_lblSpin.anchor = GridBagConstraints.EAST;
		gbc_lblSpin.gridx = 0;
		gbc_lblSpin.gridy = 0;
		add(lblSpin, gbc_lblSpin);
		
		combobox = new JComboBox();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 0;
		add(combobox, gbc_spinner);
	}

	public String getLabelText() {
		return lblSpin.getText();
	}
	public void setLabelText(String text) {
		lblSpin.setText(text);
	}

	public void addItem(Object object) {
		combobox.addItem(object);
	}

	public void removeAllItems() {
		combobox.removeAllItems();
	}
	
}
