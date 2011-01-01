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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Curves extends JPanel {
	private CurvesPaint paintPanel;
	private int [] yValues = {2, 32, 64, 96, 128, 160, 192, 224, 255};
	private ChangeListener spinnerChangeListener;
	private JSpinner [] spinners;

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
		paintPanel.yValues = yValues;
		paintPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				updateYvalues();
			}
		});
		paintPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				updateYvalues();
			}
		});
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
		
		spinners = new CSpinner[9];
		for (int i = 0; i < 9; i++ ) {
			spinners[i] = new CSpinner(i);
			spinners[i].setModel(new SpinnerNumberModel(yValues[i], 2, 255, 1));
			spinners[i].addChangeListener(spinnerChangeListener);
			panelControls.add(spinners[i], ((Integer)(i+1)).toString() + ", 1");
		}
		
	}    

	private void updateYvalues() {
		yValues = paintPanel.yValues;
		for (int i = 0; i < 9; i++) {
			spinners[i].setValue(yValues[i]);
		}
	}
}
