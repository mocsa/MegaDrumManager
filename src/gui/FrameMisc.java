package gui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.event.WindowStateListener;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;

public class FrameMisc extends JDialog {
	private ControlsMisc controlsMisc;

	/**
	 * Create the panel.
	 */
	public FrameMisc() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				pack();
			}
		});
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,}));
		
		//getContentPane().add(controlsMisc, "1, 1, fill, top");
		setTitle("Misc MegaDrum settings");
	}	

}
