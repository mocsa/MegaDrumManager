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

public class ByteControl extends JPanel {
	private JLabel label;
	private JLabel lblNoteName;
	private JSpinner spinner;

	private int octave;
	private int note_pointer;
	private static final String [] note_names = {"C ", "C#", "D ", "D#", "E ", "F ", "F#", "G ", "G#", "A ", "A#", "B "};
	/**
	 * Create the panel.
	 */
	public ByteControl() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{112, 45, 57, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		label = new JLabel("New label");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		add(label, gbc_label);
		
		spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short note_number;
				short base;
				String note_text;
				note_number = ((Short)spinner.getValue()).shortValue();
				if (note_number > 0) {
					octave = note_number/12;
					base = (short)(octave*12);
					note_pointer = note_number - base;
					note_text = note_names[note_pointer] + " " + Integer.toString(octave - 1);
					lblNoteName.setText(note_text);
				} else {
					lblNoteName.setText("Disabled");
				}
			}
		});
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 127), new Short((short) 1)));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 0, 5);
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 0;
		add(spinner, gbc_spinner);
		
		lblNoteName = new JLabel("Disabled");
		lblNoteName.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		GridBagConstraints gbc_lblNoteName = new GridBagConstraints();
		gbc_lblNoteName.anchor = GridBagConstraints.WEST;
		gbc_lblNoteName.gridx = 2;
		gbc_lblNoteName.gridy = 0;
		add(lblNoteName, gbc_lblNoteName);
	}

	public String getLabelText() {
		return label.getText();
	}
	public void setLabelText(String text) {
		label.setText(text);
	}
	public SpinnerModel getSpinnerModel() {
		return spinner.getModel();
	}
	public void setSpinnerModel(SpinnerModel model) {
		spinner.setModel(model);
	}
	
	public short getValue() {
		return ((Short)spinner.getValue()).shortValue();
	}
	
	public void setValue(short value) {
		if ((value >= 0 ) && (value < 128)) {
			spinner.setValue(value);
		}
	}
}
