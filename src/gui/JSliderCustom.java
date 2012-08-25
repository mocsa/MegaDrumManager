package gui;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSliderCustom extends JSlider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8192228359671641246L;
	
	private ValueChangedListener valueChangedListener;
	private int changeEventsDisabled = 0;

	public JSliderCustom () {
	}
	
	public JSliderCustom (ValueChangedListener vcl) {
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
