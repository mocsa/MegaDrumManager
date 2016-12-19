package gui;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerModel;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class Spin1023Control extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6939120061067438757L;
	private JSpinner spinner;
	private ValueChangedListener valueChangedListener;
	
	public boolean firstSet = true;
	public int stateChangeEventsDisabled = 0;

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
	
	public int getValue() {
		return (Integer)spinner.getValue();
	}
	
	public void addChangeListener(ChangeListener listener) {
		spinner.addChangeListener(listener);
	}
	
	public void setEventListener(ValueChangedListener vcl) {
		valueChangedListener = vcl;
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (stateChangeEventsDisabled > 0) {
					stateChangeEventsDisabled--;
				} else {
					valueChangedListener.valueChanged();
				}
			}
		});				

	}
	
	public void setValueWithoutEvents(int value) {
		if ((value >= 0 ) && (value < 1024)) {
			if (firstSet) {
				firstSet = false;
			} else {
				if (value != getValue()) {
					stateChangeEventsDisabled = 1;					
				}
			}
			spinner.setValue(value);
		}
	}

	public void setValue(int value) {
		if ((value >= 0 ) && (value < 1024)) {
			spinner.setValue(value);
		}
	}
	public JSpinner getSpinner() {
		return spinner;
	}
}
