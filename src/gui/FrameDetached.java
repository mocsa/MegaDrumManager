package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

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
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,}));
		//setAlwaysOnTop(true);
		
	}	

}
