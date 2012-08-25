package gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

public class JCheckBoxCustom extends JCheckBox {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5088921137363648696L;

	private ValueChangedListener valueChangedListener;
	private int changeEventsDisabled = 0;

	public JCheckBoxCustom(String string) {
		super(string);
	}

	public JCheckBoxCustom (ValueChangedListener vcl) {
		super("");
		setValueChangedListener(vcl);
	}

	public void setValueWithoutEvent(boolean selected) {
		if (selected != isSelected()) {
			changeEventsDisabled = 1;			
		}
		setSelected(selected);
	}
	
	public void setValueChangedListener(ValueChangedListener vcl) {
		valueChangedListener = vcl;
		addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (changeEventsDisabled > 0) {
					changeEventsDisabled--;
				} else {
					valueChangedListener.valueChanged();
				}
			}
		});
	}

}
