package gui;

import javax.swing.JDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

public class FrameDetatched extends JDialog {
	public int controlsId;
	
	/**
	 * Create the panel.
	 */
	public FrameDetatched(int id) {
		controlsId = id;
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,}));
		//setAlwaysOnTop(true);
		
	}	

}
