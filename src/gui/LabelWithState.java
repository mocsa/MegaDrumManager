package gui;

import java.awt.Color;
import javax.swing.JLabel;

class LabelWithState extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4271346265812705819L;

	public LabelWithState(String string) {
		super(string);
		//setForeground(Color.red);
	}
	
	public void setSyncState(int state) {
		switch (state) {
			case Constants.SYNC_STATE_UNKNOWN:
				setForeground(Color.BLUE);
				break;
			case Constants.SYNC_STATE_SYNCED:
				setForeground(Color.GREEN);
				break;
			case Constants.SYNC_STATE_NOT_SYNCED:
				setForeground(Color.RED);
				break;
			default:
				setForeground(Color.BLACK);
				break;
		}
	}
}
