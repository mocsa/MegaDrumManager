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
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Curves extends JPanel {
	private CurvesPaint paintPanel;
	//private int [] yValues = {2, 32, 64, 96, 128, 160, 192, 224, 255};
	private ChangeListener spinnerChangeListener;
	private JSpinner [] spinners;
	private ConfigCurve [] configCurves;
	private int curvePointer;
	private int prevCurvePointer;
	private JComboBox comboBox_curveNumber;

	/**
	 * Create the panel.
	 */
	public Curves() {
		configCurves = new ConfigCurve[Constants.CURVES_COUNT];
        for(int i=0; i<Constants.CURVES_COUNT; i++){
        	configCurves[i] = new ConfigCurve();
        }
        curvePointer = 0;
        prevCurvePointer = -1;
        
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("left:281px:grow"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),
				RowSpec.decode("1dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("279px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("12dlu:grow"),}));
		
		paintPanel = new CurvesPaint();
		paintPanel.yValues = configCurves[curvePointer].yValues;
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
		
		JPanel panel = new JPanel();
		add(panel, "1, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("12dlu"),}));
		
		JButton button = new JButton("Get");
		button.setMargin(new Insets(1, 4, 1, 4));
		button.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel.add(button, "1, 1");
		
		JButton button_1 = new JButton("Send");
		button_1.setMargin(new Insets(1, 4, 1, 4));
		button_1.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel.add(button_1, "3, 1");
		
		JButton button_2 = new JButton("GetAll");
		button_2.setMargin(new Insets(1, 4, 1, 4));
		button_2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel.add(button_2, "5, 1");
		
		JButton button_3 = new JButton("SendAll");
		button_3.setMargin(new Insets(1, 4, 1, 4));
		button_3.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel.add(button_3, "7, 1");
		
		JPanel panel_1 = new JPanel();
		add(panel_1, "1, 3, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("20dlu"),},
			new RowSpec[] {
				RowSpec.decode("fill:12dlu"),}));
		
		JLabel lblCurve = new JLabel("Curve");
		lblCurve.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel_1.add(lblCurve, "2, 1, right, fill");
		
		comboBox_curveNumber = new JComboBox();
		comboBox_curveNumber.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
		        	prevCurvePointer = curvePointer;
		        	curvePointer = comboBox_curveNumber.getSelectedIndex();
		        	paintPanel.yValues = configCurves[curvePointer].yValues;
		        	updateYvalues();
		        	paintPanel.repaint();
		        }
			}
		});
		for (int i = 0; i < 4; i++) {
			comboBox_curveNumber.addItem(i+1);
		}
		comboBox_curveNumber.setSelectedIndex(0);
		comboBox_curveNumber.setMaximumRowCount(28);
		comboBox_curveNumber.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_1.add(comboBox_curveNumber, "4, 1, fill, fill");
		add(paintPanel, "1, 4, left, fill");
		
		JPanel panelControls = new JPanel();
		add(panelControls, "1, 6, fill, fill");
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
				configCurves[curvePointer].yValues[id] = ((Integer)((CSpinner)arg0.getSource()).getValue());
				//paintPanel.yValues = yValues;
				paintPanel.repaint();
			}
		};
		
		spinners = new CSpinner[9];
		for (int i = 0; i < 9; i++ ) {
			spinners[i] = new CSpinner(i);
			spinners[i].setModel(new SpinnerNumberModel(configCurves[curvePointer].yValues[i], 2, 255, 1));
			spinners[i].addChangeListener(spinnerChangeListener);
			panelControls.add(spinners[i], ((Integer)(i+1)).toString() + ", 1");
		}
		
	}    

	private void updateYvalues() {
		//yValues = paintPanel.yValues;
		if (spinners != null) {
			for (int i = 0; i < 9; i++) {
				spinners[i].setValue(configCurves[curvePointer].yValues[i]);
			}
		}
	}
}
