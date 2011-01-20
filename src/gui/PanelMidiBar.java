package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class PanelMidiBar extends JPanel {
	private PanelHitBar panelHitBar;
	private JLabel lblTime;
	private JLabel lblNote;
	public int level = 0;
	public int timeDiff = 1;
	public int noteNumber = 1;

	/**
	 * Create the panel.
	 */
	public PanelMidiBar() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("center:pref"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblTime = new JLabel("?");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 9));
		add(lblTime, "1, 1");
		
		panelHitBar = new PanelHitBar();
//		panelHitBar.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//				panelHitBar.level = (int)(Math.random()*127);
//				panelHitBar.repaint();
//				lblNote.setText(((Integer)(int)(Math.random()*127)).toString());
//			}
//		});
		add(panelHitBar, "1, 3, fill, fill");
		
		lblNote = new JLabel("?");
		lblNote.setFont(new Font("Tahoma", Font.PLAIN, 9));
		add(lblNote, "1, 5");

	}
	
	public void updateToValues() {
		panelHitBar.level = level;
		panelHitBar.repaint();
		lblNote.setText(((Integer)noteNumber).toString());		
		lblTime.setText(((Integer)timeDiff).toString());		
	}

}
