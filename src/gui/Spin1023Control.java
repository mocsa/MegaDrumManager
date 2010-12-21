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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class Spin1023Control extends JPanel {
	private JSpinner spinner;

	/**
	 * Create the panel.
	 */
	public Spin1023Control() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("46px"),},
			new RowSpec[] {
				RowSpec.decode("20px"),}));
		
		spinner = new JSpinner();
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner,"#");
		spinner.setEditor(editor);
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner.setModel(new SpinnerNumberModel(0, 0, 1023, 1));
		add(spinner, "1, 1, fill, top");
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
		if ((value >= 0 ) && (value < 1024)) {
			spinner.setValue(value);
		}
	}
	public JSpinner getSpinner() {
		return spinner;
	}
}
