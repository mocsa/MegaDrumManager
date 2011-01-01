package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Curves extends JPanel {
	private CurvesPaint paintPanel;
	private int [] yValues = {2, 32, 64, 96, 128, 160, 192, 224, 255};
	private ChangeListener spinnerChangeListener;

	/**
	 * Create the panel.
	 */
	public Curves() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("left:281px:grow"),},
			new RowSpec[] {
				RowSpec.decode("279px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("12dlu:grow"),}));
		
		paintPanel = new CurvesPaint();
		add(paintPanel, "1, 1, left, fill");
		
		JPanel panelControls = new JPanel();
		add(panelControls, "1, 3, fill, fill");
		panelControls.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),}));
		
		spinnerChangeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int id;
				id = ((CSpinner)arg0.getSource()).getIndex();
				yValues[id] = ((Integer)((CSpinner)arg0.getSource()).getValue());
				paintPanel.yValues = yValues;
				paintPanel.repaint();
			}
		};
		CSpinner spinner_0 = new CSpinner(0);
		spinner_0.addChangeListener(spinnerChangeListener);
		panelControls.add(spinner_0, "1, 1");
		
		CSpinner spinner_1 = new CSpinner(1);
		spinner_1.addChangeListener(spinnerChangeListener);
		panelControls.add(spinner_1, "2, 1");

		CSpinner spinner_2 = new CSpinner(2);
		spinner_2.addChangeListener(spinnerChangeListener);
		panelControls.add(spinner_2, "3, 1");
		
		CSpinner spinner_3 = new CSpinner(3);
		panelControls.add(spinner_3, "4, 1");

		CSpinner spinner_4 = new CSpinner(4);
		panelControls.add(spinner_4, "5, 1");

		CSpinner spinner_5 = new CSpinner(5);
		panelControls.add(spinner_5, "6, 1");
		
		CSpinner spinner_6 = new CSpinner(6);
		panelControls.add(spinner_6, "7, 1");

		CSpinner spinner_7 = new CSpinner(7);
		panelControls.add(spinner_7, "8, 1");
		
		CSpinner spinner_8 = new CSpinner(8);
		panelControls.add(spinner_8, "9, 1");
		
	}    
	
}
