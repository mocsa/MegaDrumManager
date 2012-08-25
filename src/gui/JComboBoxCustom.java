package gui;

import java.awt.Font;

import javax.swing.JComboBox;

public class JComboBoxCustom extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6545813982852258113L;
	public int selectEventsDisabled = 0;

	public JComboBoxCustom () {
		//this.setMaximumRowCount(128);
		this.setFont(new Font("Segoe UI", Font.PLAIN, 10));
	}
	
	public void setSelectedIndexWithoutEvent(int index) {
		if (index >= this.getItemCount()) {
			index = this.getItemCount() - 1;
		}
		if (index != getSelectedIndex()) {
			this.selectEventsDisabled = 1;
			this.setSelectedIndex(index);
		}
	}

}
