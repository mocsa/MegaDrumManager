package gui;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSpinnerCustom extends JSpinner {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3677120642138018481L;
	
	private ValueChangedListener valueChangedListener;
	private int changeEventsDisabled = 0;

	public JSpinnerCustom () {
	}
	
	public JSpinnerCustom (ValueChangedListener vcl) {
		setValueChangedListener(vcl);
	}

	public void setValueWithoutEvent(int value) {
		if (value != (Integer)getValue()) {
			changeEventsDisabled = 1;			
		}
		setValue(value);
	}
	
	public void setValueChangedListener(ValueChangedListener vcl) {
		valueChangedListener = vcl;
		
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (changeEventsDisabled > 0) {
					changeEventsDisabled--;
				} else {
					valueChangedListener.valueChanged();
				}
			}
		});				
	}
}
