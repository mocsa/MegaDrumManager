package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;

public class ThirdZoneControls extends JPanel {

	/**
	 * Create the panel.
	 */
	public ThirdZoneControls() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("42dlu"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("60dlu"),
				ColumnSpec.decode("10dlu"),
				ColumnSpec.decode("42dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("60dlu"),},
			new RowSpec[] {
				RowSpec.decode("13dlu"),
				RowSpec.decode("13dlu"),
				RowSpec.decode("13dlu"),}));
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblNote, "1, 1, right, default");
		
		NoteSpinControl noteSpinControl = new NoteSpinControl();
		add(noteSpinControl, "3, 1, fill, fill");
		
		JLabel lblMidpoint = new JLabel("MidPoint");
		lblMidpoint.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMidpoint, "5, 1, right, default");
		
		JSlider slider = new JSlider();
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(1);
		slider.setValue(7);
		slider.setMaximum(15);
		add(slider, "7, 1, fill, fill");
		
		JLabel lblAltNote = new JLabel("Alt Note");
		lblAltNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblAltNote, "1, 2, right, default");
		
		NoteSpinControl noteSpinControl_1 = new NoteSpinControl();
		add(noteSpinControl_1, "3, 2, fill, fill");
		
		JLabel lblMidpointWidth = new JLabel("MidPoint Width");
		lblMidpointWidth.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMidpointWidth, "5, 2, right, default");
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 15, 1));
		add(spinner, "7, 2, left, fill");
		
		JLabel lblPressrollNote = new JLabel("Pressroll Note");
		lblPressrollNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblPressrollNote, "1, 3, right, default");
		
		NoteSpinControl noteSpinControl_2 = new NoteSpinControl();
		add(noteSpinControl_2, "3, 3, fill, fill");
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblThreshold, "5, 3, right, default");
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 127, 1));
		add(spinner_1, "7, 3, left, fill");

	}

}
