package gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.ChangeEvent;

public class PedalControls extends JPanel {
	private JButton btnGet;
	private JButton btnSend;
	private JComboBox comboBox_type;
	private JComboBox comboBox_curve;
	private JComboBox comboBox_input;
	private JCheckBox checkBox_altInput;
	private JCheckBox checkBox_reverseLevels;
	private JCheckBox checkBox_softChicks;
	private JCheckBox checkBox_autoLevels;
	private Spin127Control spin127Control_chickDelay;
	private Spin127Control spin127Control_cc;
	private Spin1023Control spin1023Control_lowLevel;
	private Spin1023Control spin1023Control_highLevel;
	private Spin127Control spin127Control_openLevel;
	private Spin127Control spin127Control_semiOpen;
	private Spin127Control spin127Control_halfOpen;
	private Spin127Control spin127Control_closed;
	private Spin127Control spin127Control_shortThres;
	private Spin127Control spin127Control_longThres;
	private NoteSpinControl noteSpinControl_bowSemiOpen;
	private NoteSpinControl noteSpinControl_edgeSemiOpen;
	private NoteSpinControl noteSpinControl_bellSemiOpen;
	private NoteSpinControl noteSpinControl_bowHalfOpen;
	private NoteSpinControl noteSpinControl_edgeHalfOpen;
	private NoteSpinControl noteSpinControl_bellHalfOpen;
	private NoteSpinControl noteSpinControl_bowSemiClosed;
	private NoteSpinControl noteSpinControl_edgeSemiClosed;
	private NoteSpinControl noteSpinControl_bellSemiClosed;
	private NoteSpinControl noteSpinControl_bowClosed;
	private NoteSpinControl noteSpinControl_edgeClosed;
	private NoteSpinControl noteSpinControl_bellClosed;
	private NoteSpinControl noteSpinControl_chick;
	private NoteSpinControl noteSpinControl_splash;
	
	
	
	private ConfigPedal configPedal;

	/**
	 * Create the panel.
	 */
	public PedalControls() {
		configPedal = new ConfigPedal();
		setLayout(new GridLayout(1, 0, 0, 0));		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("220px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("310px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("max(20dlu;default)"),}));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, "2, 2, left, top");
		
		JPanel panel_misc = new JPanel();
		tabbedPane.addTab("Misc", null, panel_misc, null);
		panel_misc.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:56dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblType, "1, 1, right, default");
		
		comboBox_type = new JComboBox();
		comboBox_type.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
			configPedal.type = (comboBox_type.getSelectedIndex() != 0);
			}
		});
		comboBox_type.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		comboBox_type.addItem("FootContr");
		comboBox_type.addItem("Pot");		
		panel_misc.add(comboBox_type, "3, 1, fill, default");
		
		JLabel lblCurve = new JLabel("Curve");
		lblCurve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblCurve, "1, 2, right, default");
		
		comboBox_curve = new JComboBox();
		comboBox_curve.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configPedal.curve = (short)comboBox_curve.getSelectedIndex();
			}
		});
		comboBox_curve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		for (String string : Constants.CURVES_LIST) {
			comboBox_curve.addItem(string);
			}
		panel_misc.add(comboBox_curve, "3, 2, fill, default");
		
		JLabel lblHihatInput = new JLabel("HiHat Input");
		lblHihatInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblHihatInput, "1, 3, right, default");
		
		comboBox_input = new JComboBox();
		comboBox_input.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configPedal.hhInput = (short)comboBox_input.getSelectedIndex();
			}
		});
		comboBox_input.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(comboBox_input, "3, 3, fill, default");
		
		JLabel lblAltInput = new JLabel("Alt Input");
		lblAltInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblAltInput, "1, 4");
		
		checkBox_altInput = new JCheckBox("");
		checkBox_altInput.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configPedal.altIn = checkBox_altInput.isSelected();
			}
		});
		checkBox_altInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_altInput, "3, 4");
		
		JLabel lblReverseLevels = new JLabel("Reverse Levels");
		lblReverseLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblReverseLevels, "1, 5");
		
		checkBox_reverseLevels = new JCheckBox("");
		checkBox_reverseLevels.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configPedal.reverseLevels = checkBox_reverseLevels.isSelected();
			}
		});
		checkBox_reverseLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_reverseLevels, "3, 5");
		
		JLabel lblSoftChicks = new JLabel("Soft Chicks");
		lblSoftChicks.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblSoftChicks, "1, 6");
		
		checkBox_softChicks = new JCheckBox("");
		checkBox_softChicks.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configPedal.softChicks = checkBox_softChicks.isSelected();
			}
		});
		checkBox_softChicks.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_softChicks, "3, 6");
		
		JLabel lblAutoLevels = new JLabel("Auto Levels");
		lblAutoLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblAutoLevels, "1, 7");
		
		checkBox_autoLevels = new JCheckBox("");
		checkBox_autoLevels.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configPedal.autoLevels = checkBox_autoLevels.isSelected();
			}
		});
		checkBox_autoLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_autoLevels, "3, 7");
		
		JLabel lblChickDelay = new JLabel("Chick Delay");
		lblChickDelay.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblChickDelay, "1, 8");
		
		spin127Control_chickDelay = new Spin127Control();
		spin127Control_chickDelay.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configPedal.chickDelay = ((Integer)spin127Control_chickDelay.getSpinner().getValue()).shortValue();
			}
		});
		panel_misc.add(spin127Control_chickDelay, "3, 8, fill, fill");
		
		JLabel lblCcNumber = new JLabel("CC Number");
		lblCcNumber.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblCcNumber, "1, 9");
		
		spin127Control_cc = new Spin127Control();
		spin127Control_cc.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configPedal.cc = ((Integer)spin127Control_cc.getSpinner().getValue()).shortValue();
			}
		});
		panel_misc.add(spin127Control_cc, "3, 9, fill, fill");
		
		JPanel panel_levels = new JPanel();
		tabbedPane.addTab("Levels", null, panel_levels, null);
		panel_levels.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:56dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblLow = new JLabel("Low");
		lblLow.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblLow, "1, 1");
		
		spin1023Control_lowLevel = new Spin1023Control();
		spin1023Control_lowLevel.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configPedal.lowLevel = ((Integer)spin1023Control_lowLevel.getSpinner().getValue()).shortValue();
			}
		});
		panel_levels.add(spin1023Control_lowLevel, "3, 1, fill, fill");
		
		JLabel lblHigh = new JLabel("High");
		lblHigh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblHigh, "1, 2");
		
		spin1023Control_highLevel = new Spin1023Control();
		spin1023Control_highLevel.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configPedal.highLevel = ((Integer)spin1023Control_highLevel.getSpinner().getValue()).shortValue();
			}
		});
		panel_levels.add(spin1023Control_highLevel, "3, 2, fill, fill");
		
		JLabel lblOpen = new JLabel("Open");
		lblOpen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblOpen, "1, 3");
		
		spin127Control_openLevel = new Spin127Control();
		panel_levels.add(spin127Control_openLevel, "3, 3, fill, fill");
		
		JLabel lblClosed = new JLabel("SemiOpen");
		lblClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblClosed, "1, 4");
		
		spin127Control_semiOpen = new Spin127Control();
		spin127Control_semiOpen.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
			}
		});
		panel_levels.add(spin127Control_semiOpen, "3, 4, fill, fill");
		
		JLabel lblHalfopen = new JLabel("HalfOpen");
		lblHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblHalfopen, "1, 5");
		
		spin127Control_halfOpen = new Spin127Control();
		panel_levels.add(spin127Control_halfOpen, "3, 5, fill, fill");
		
		JLabel lblClosed_1 = new JLabel("Closed");
		lblClosed_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblClosed_1, "1, 6");
		
		spin127Control_closed = new Spin127Control();
		panel_levels.add(spin127Control_closed, "3, 6, fill, fill");
		
		JLabel lblShortThresh = new JLabel("ShortChickThresh");
		lblShortThresh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblShortThresh, "1, 7");
		
		spin127Control_shortThres = new Spin127Control();
		panel_levels.add(spin127Control_shortThres, "3, 7, fill, fill");
		
		JLabel lblLongchickthresh = new JLabel("LongChickThresh");
		lblLongchickthresh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblLongchickthresh, "1, 8");
		
		spin127Control_longThres = new Spin127Control();
		panel_levels.add(spin127Control_longThres, "3, 8, fill, fill");
		
		JPanel panel_notes = new JPanel();
		tabbedPane.addTab("Notes", null, panel_notes, null);
		panel_notes.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:56dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblBowSemiopen = new JLabel("Bow SemiOpen");
		lblBowSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowSemiopen, "1, 1");
		
		noteSpinControl_bowSemiOpen = new NoteSpinControl();
		panel_notes.add(noteSpinControl_bowSemiOpen, "3, 1, left, center");
		
		JLabel lblEdgeSemiopen = new JLabel("Edge SemiOpen");
		lblEdgeSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeSemiopen, "1, 2");
		
		noteSpinControl_edgeSemiOpen = new NoteSpinControl();
		panel_notes.add(noteSpinControl_edgeSemiOpen, "3, 2, left, center");
		
		JLabel lblBellSemiopen = new JLabel("Bell SemiOpen");
		lblBellSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellSemiopen, "1, 3");
		
		noteSpinControl_bellSemiOpen = new NoteSpinControl();
		panel_notes.add(noteSpinControl_bellSemiOpen, "3, 3, left, center");
		
		JLabel lblBowHalfopen = new JLabel("Bow HalfOpen");
		lblBowHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowHalfopen, "1, 4");
		
		noteSpinControl_bowHalfOpen = new NoteSpinControl();
		panel_notes.add(noteSpinControl_bowHalfOpen, "3, 4, left, center");
		
		JLabel lblEdgeHalfopen = new JLabel("Edge HalfOpen");
		lblEdgeHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeHalfopen, "1, 5");
		
		noteSpinControl_edgeHalfOpen = new NoteSpinControl();
		panel_notes.add(noteSpinControl_edgeHalfOpen, "3, 5, left, center");
		
		JLabel lblBellHalfopen = new JLabel("Bell HalfOpen");
		lblBellHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellHalfopen, "1, 6");
		
		noteSpinControl_bellHalfOpen = new NoteSpinControl();
		panel_notes.add(noteSpinControl_bellHalfOpen, "3, 6, left, center");
		
		JLabel lblBowSemiclosed = new JLabel("Bow SemiClosed");
		lblBowSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowSemiclosed, "1, 7");
		
		noteSpinControl_bowSemiClosed = new NoteSpinControl();
		panel_notes.add(noteSpinControl_bowSemiClosed, "3, 7, left, center");
		
		JLabel lblEdgeSemiclosed = new JLabel("Edge SemiClosed");
		lblEdgeSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeSemiclosed, "1, 8");
		
		noteSpinControl_edgeSemiClosed = new NoteSpinControl();
		panel_notes.add(noteSpinControl_edgeSemiClosed, "3, 8, left, center");
		
		JLabel lblBellSemiclosed = new JLabel("Bell SemiClosed");
		lblBellSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellSemiclosed, "1, 9");
		
		noteSpinControl_bellSemiClosed = new NoteSpinControl();
		panel_notes.add(noteSpinControl_bellSemiClosed, "3, 9, left, center");
		
		JLabel lblBowClosed = new JLabel("Bow Closed");
		lblBowClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowClosed, "1, 10");
		
		noteSpinControl_bowClosed = new NoteSpinControl();
		panel_notes.add(noteSpinControl_bowClosed, "3, 10, left, center");
		
		JLabel lblEdgeClosed = new JLabel("Edge Closed");
		lblEdgeClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeClosed, "1, 11");
		
		noteSpinControl_edgeClosed = new NoteSpinControl();
		panel_notes.add(noteSpinControl_edgeClosed, "3, 11, left, center");
		
		JLabel lblBellClosed = new JLabel("Bell Closed");
		lblBellClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellClosed, "1, 12");
		
		noteSpinControl_bellClosed = new NoteSpinControl();
		panel_notes.add(noteSpinControl_bellClosed, "3, 12, left, center");
		
		JLabel lblChick = new JLabel("Chick");
		lblChick.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblChick, "1, 13");
		
		noteSpinControl_chick = new NoteSpinControl();
		panel_notes.add(noteSpinControl_chick, "3, 13, left, center");
		
		JLabel lblSplash = new JLabel("Splash");
		lblSplash.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblSplash, "1, 14");
		
		noteSpinControl_splash = new NoteSpinControl();
		panel_notes.add(noteSpinControl_splash, "3, 14, left, center");
		
		JPanel panel_buttons = new JPanel();
		panel.add(panel_buttons, "2, 4, fill, fill");
		
		btnGet = new JButton("Get");
		btnGet.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnGet);
		
		btnSend = new JButton("Send");
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSend);

	}

	public JButton getBtnGet() {
		return btnGet;
	}
	public JButton getBtnSend() {
		return btnSend;
	}
}
