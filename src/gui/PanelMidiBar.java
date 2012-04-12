package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

public class PanelMidiBar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6935942765123326328L;
	private PanelMidiLevelBar panelMidiLevelBar;
	private JLabel lblTime;
	private JLabel lblNote;
	public int level = 0;
	public int timeDiff = 0;
	public int noteNumber = 0;
	public Color color = Color.BLUE;

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
		
		panelMidiLevelBar = new PanelMidiLevelBar();
//		panelHitBar.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//				panelHitBar.level = (int)(Math.random()*127);
//				panelHitBar.repaint();
//				lblNote.setText(((Integer)(int)(Math.random()*127)).toString());
//			}
//		});
		add(panelMidiLevelBar, "1, 3, fill, fill");
		
		lblNote = new JLabel("?");
		lblNote.setFont(new Font("Tahoma", Font.PLAIN, 9));
		add(lblNote, "1, 5");

	}
	
	public void updateToValues() {
		panelMidiLevelBar.level = level;
		panelMidiLevelBar.fgColor = color;
		panelMidiLevelBar.repaint();
		lblNote.setText(((Integer)noteNumber).toString());
		if (timeDiff > 999) {
			lblTime.setText(">1s");
		} else {
			lblTime.setText(((Integer)timeDiff).toString());
		}
	}

}
