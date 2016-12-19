package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

class LabelWithState extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4271346265812705819L;

	public LabelWithState(String string) {
		super(string);
		//setForeground(Color.red);
		setBorder(BorderFactory.createCompoundBorder(null,BorderFactory.createEmptyBorder(2,6,2,6)));
	}
	
	public void setSyncState(int state) {
		switch (state) {
			case Constants.SYNC_STATE_UNKNOWN:
				setForeground(Color.BLUE);
				break;
			case Constants.SYNC_STATE_SYNCED:
				setForeground(Color.BLACK);
				break;
			case Constants.SYNC_STATE_NOT_SYNCED:
				setForeground(Color.RED);
				break;
			default:
				setForeground(Color.BLACK);
				break;
		}
	}
	
	public void setSyncNotSync(boolean synced) {
		if (synced) {
			setSyncState(Constants.SYNC_STATE_SYNCED);
		} else {
			setSyncState(Constants.SYNC_STATE_NOT_SYNCED);
		}
	}
}
