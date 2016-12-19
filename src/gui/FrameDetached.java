package gui;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

public class FrameDetached extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7320746619808327651L;
	public int controlsId;
	public boolean isDetached;
	
	/**
	 * Create the panel.
	 */
	public FrameDetached(int id) {
		setResizable(false);
		controlsId = id;
		isDetached = false;
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.PREF_COLSPEC,},
			new RowSpec[] {
				FormSpecs.PREF_ROWSPEC,}));
		//setAlwaysOnTop(true);
		
	}	

}
