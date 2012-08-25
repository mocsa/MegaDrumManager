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
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.SpinnerNumberModel;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class ControlsPedal extends JPanel implements ValueChangedListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9188782839444156764L;

	//private Boolean changeEventsAllowed = false;
	private Boolean controlsInited = false;

	private JButton btnGet;
	private JButton btnSend;
	private JComboBoxCustom comboBox_type;
	private JComboBoxCustom comboBox_curve;
	private JComboBoxCustom comboBox_input;
	private JCheckBoxCustom checkBox_altInput;
	private JCheckBoxCustom checkBox_reverseLevels;
	private JCheckBoxCustom checkBox_softChicks;
	private JCheckBoxCustom checkBox_autoLevels;
	private Spin127Control spin127Control_chickDelay;
	private Spin127Control spin127Control_cc;
	private Spin127Control spin127Control_ccRdcLvl;
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
	
	private ConfigFull configFull;
	private JButton btnLoad;
	private JButton btnSave;

	/**
	 * Create the panel.
	 */
	public ControlsPedal(ConfigFull config) {
		configFull = config;
		setLayout(new GridLayout(1, 0, 0, 0));		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("310px"),}));
		
		JPanel panel_buttons = new JPanel();
		panel.add(panel_buttons, "1, 1, fill, fill");
		panel_buttons.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("fill:default"),}));
		
		btnGet = new JButton("Get");
		btnGet.setMargin(new Insets(1, 4, 1, 4));
		btnGet.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnGet, "1, 1, fill, fill");
		
		btnSend = new JButton("Send");
		btnSend.setMargin(new Insets(1, 4, 1, 4));
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSend, "3, 1, fill, fill");
		
		btnLoad = new JButton("Load");
		btnLoad.setMargin(new Insets(1, 4, 1, 4));
		btnLoad.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnLoad, "5, 1");
		
		btnSave = new JButton("Save");
		btnSave.setMargin(new Insets(1, 4, 1, 4));
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSave, "7, 1");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, "1, 2, left, top");
		
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblType, "1, 1, right, default");
		
		comboBox_type = new JComboBoxCustom();
		comboBox_type.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox_type.selectEventsDisabled > 0) {
						comboBox_type.selectEventsDisabled--;
					} else {
						if (controlsInited) {
							valueChanged();
						}
					}
				}
			}
		});
		comboBox_type.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		comboBox_type.addItem("Pot");		
		comboBox_type.addItem("FootContr");
		panel_misc.add(comboBox_type, "3, 1, fill, default");
		
		JLabel lblCurve = new JLabel("Curve");
		lblCurve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblCurve, "1, 2, right, default");
		
		comboBox_curve = new JComboBoxCustom();
		comboBox_curve.setMaximumRowCount(16);
		comboBox_curve.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox_curve.selectEventsDisabled > 0) {
						comboBox_curve.selectEventsDisabled--;
					} else {
						if (controlsInited) {
							valueChanged();
						}
					}
				}
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
		
		comboBox_input = new JComboBoxCustom();
		comboBox_input.setMaximumRowCount(20);
		comboBox_input.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox_input.selectEventsDisabled > 0) {
						comboBox_input.selectEventsDisabled--;
					} else {
						if (controlsInited) {
							valueChanged();
						}
					}
				}
			}
		});
		updateInputCountsControls(Constants.PADS_COUNT);
		comboBox_input.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(comboBox_input, "3, 3, fill, default");
		
		JLabel lblAltInput = new JLabel("Alt Input");
		lblAltInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblAltInput, "1, 4");
		
		checkBox_altInput = new JCheckBoxCustom("");
		checkBox_altInput.setValueChangedListener(this);
		checkBox_altInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_altInput, "3, 4");
		
		JLabel lblReverseLevels = new JLabel("Reverse Levels");
		lblReverseLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblReverseLevels, "1, 5");
		
		checkBox_reverseLevels = new JCheckBoxCustom("");
		checkBox_reverseLevels.setValueChangedListener(this);
		checkBox_reverseLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_reverseLevels, "3, 5");
		
		JLabel lblSoftChicks = new JLabel("Soft Chicks");
		lblSoftChicks.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblSoftChicks, "1, 6");
		
		checkBox_softChicks = new JCheckBoxCustom("");
		checkBox_softChicks.setValueChangedListener(this);
		checkBox_softChicks.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_softChicks, "3, 6");
		
		JLabel lblAutoLevels = new JLabel("Auto Levels");
		lblAutoLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblAutoLevels, "1, 7");
		
		checkBox_autoLevels = new JCheckBoxCustom("");
		checkBox_autoLevels.setValueChangedListener(this);
		checkBox_autoLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_autoLevels, "3, 7");
		
		JLabel lblChickDelay = new JLabel("Chick Delay");
		lblChickDelay.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblChickDelay, "1, 8");
		
		spin127Control_chickDelay = new Spin127Control();
		spin127Control_chickDelay.getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 12));
		spin127Control_chickDelay.setEventListener(this);
		panel_misc.add(spin127Control_chickDelay, "3, 8, fill, fill");
		
		JLabel lblCcNumber = new JLabel("CC Number");
		lblCcNumber.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblCcNumber, "1, 9");
		
		spin127Control_cc = new Spin127Control();
		spin127Control_cc.getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 12));
		spin127Control_cc.setEventListener(this);
		panel_misc.add(spin127Control_cc, "3, 9, fill, fill");
		
		JLabel lblCcRdcLvl = new JLabel("CC Reduction Lvl");
		lblCcRdcLvl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblCcRdcLvl, "1, 10");
		
		spin127Control_ccRdcLvl = new Spin127Control();
		spin127Control_ccRdcLvl.getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 12));
		spin127Control_ccRdcLvl.setEventListener(this);
		spin127Control_ccRdcLvl.getSpinner().setModel(new SpinnerNumberModel(0, 0, 3, 1));
		panel_misc.add(spin127Control_ccRdcLvl, "3, 10, fill, fill");
		
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
		spin1023Control_lowLevel.setEventListener(this);
		panel_levels.add(spin1023Control_lowLevel, "3, 1, fill, fill");
		
		JLabel lblHigh = new JLabel("High");
		lblHigh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblHigh, "1, 2");
		
		spin1023Control_highLevel = new Spin1023Control();
		spin1023Control_highLevel.setEventListener(this);
		panel_levels.add(spin1023Control_highLevel, "3, 2, fill, fill");
		
		JLabel lblOpen = new JLabel("Open");
		lblOpen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblOpen, "1, 3");
		
		spin127Control_openLevel = new Spin127Control();
		spin127Control_openLevel.setEventListener(this);
		panel_levels.add(spin127Control_openLevel, "3, 3, fill, fill");
		
		JLabel lblClosed = new JLabel("SemiOpen");
		lblClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblClosed, "1, 4");
		
		spin127Control_semiOpen = new Spin127Control();
		spin127Control_semiOpen.setEventListener(this);
		panel_levels.add(spin127Control_semiOpen, "3, 4, fill, fill");
		
		JLabel lblHalfopen = new JLabel("HalfOpen");
		lblHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblHalfopen, "1, 5");
		
		spin127Control_halfOpen = new Spin127Control();
		spin127Control_halfOpen.setEventListener(this);
		panel_levels.add(spin127Control_halfOpen, "3, 5, fill, fill");
		
		JLabel lblClosed_1 = new JLabel("Closed");
		lblClosed_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblClosed_1, "1, 6");
		
		spin127Control_closed = new Spin127Control();
		spin127Control_closed.setEventListener(this);
		panel_levels.add(spin127Control_closed, "3, 6, fill, fill");
		
		JLabel lblShortThresh = new JLabel("ShortChickThresh");
		lblShortThresh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblShortThresh, "1, 7");
		
		spin127Control_shortThres = new Spin127Control();
		spin127Control_shortThres.setEventListener(this);
		panel_levels.add(spin127Control_shortThres, "3, 7, fill, fill");
		
		JLabel lblLongchickthresh = new JLabel("LongChickThresh");
		lblLongchickthresh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblLongchickthresh, "1, 8");
		
		spin127Control_longThres = new Spin127Control();
		spin127Control_longThres.setEventListener(this);
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
		
		noteSpinControl_bowSemiOpen = new NoteSpinControl(configFull);
		noteSpinControl_bowSemiOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_bowSemiOpen, "3, 1, left, center");
		
		JLabel lblEdgeSemiopen = new JLabel("Edge SemiOpen");
		lblEdgeSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeSemiopen, "1, 2");
		
		noteSpinControl_edgeSemiOpen = new NoteSpinControl(configFull);
		noteSpinControl_edgeSemiOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_edgeSemiOpen, "3, 2, left, center");
		
		JLabel lblBellSemiopen = new JLabel("Bell SemiOpen");
		lblBellSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellSemiopen, "1, 3");
		
		noteSpinControl_bellSemiOpen = new NoteSpinControl(configFull);
		noteSpinControl_bellSemiOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_bellSemiOpen, "3, 3, left, center");
		
		JLabel lblBowHalfopen = new JLabel("Bow HalfOpen");
		lblBowHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowHalfopen, "1, 4");
		
		noteSpinControl_bowHalfOpen = new NoteSpinControl(configFull);
		noteSpinControl_bowHalfOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_bowHalfOpen, "3, 4, left, center");
		
		JLabel lblEdgeHalfopen = new JLabel("Edge HalfOpen");
		lblEdgeHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeHalfopen, "1, 5");
		
		noteSpinControl_edgeHalfOpen = new NoteSpinControl(configFull);
		noteSpinControl_edgeHalfOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_edgeHalfOpen, "3, 5, left, center");
		
		JLabel lblBellHalfopen = new JLabel("Bell HalfOpen");
		lblBellHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellHalfopen, "1, 6");
		
		noteSpinControl_bellHalfOpen = new NoteSpinControl(configFull);
		noteSpinControl_bellHalfOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_bellHalfOpen, "3, 6, left, center");
		
		JLabel lblBowSemiclosed = new JLabel("Bow SemiClosed");
		lblBowSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowSemiclosed, "1, 7");
		
		noteSpinControl_bowSemiClosed = new NoteSpinControl(configFull);
		noteSpinControl_bowSemiClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_bowSemiClosed, "3, 7, left, center");
		
		JLabel lblEdgeSemiclosed = new JLabel("Edge SemiClosed");
		lblEdgeSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeSemiclosed, "1, 8");
		
		noteSpinControl_edgeSemiClosed = new NoteSpinControl(configFull);
		noteSpinControl_edgeSemiClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_edgeSemiClosed, "3, 8, left, center");
		
		JLabel lblBellSemiclosed = new JLabel("Bell SemiClosed");
		lblBellSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellSemiclosed, "1, 9");
		
		noteSpinControl_bellSemiClosed = new NoteSpinControl(configFull);
		noteSpinControl_bellSemiClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_bellSemiClosed, "3, 9, left, center");
		
		JLabel lblBowClosed = new JLabel("Bow Closed");
		lblBowClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowClosed, "1, 10");
		
		noteSpinControl_bowClosed = new NoteSpinControl(configFull);
		noteSpinControl_bowClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_bowClosed, "3, 10, left, center");
		
		JLabel lblEdgeClosed = new JLabel("Edge Closed");
		lblEdgeClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeClosed, "1, 11");
		
		noteSpinControl_edgeClosed = new NoteSpinControl(configFull);
		noteSpinControl_edgeClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_edgeClosed, "3, 11, left, center");
		
		JLabel lblBellClosed = new JLabel("Bell Closed");
		lblBellClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellClosed, "1, 12");
		
		noteSpinControl_bellClosed = new NoteSpinControl(configFull);
		noteSpinControl_bellClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_bellClosed, "3, 12, left, center");
		
		JLabel lblChick = new JLabel("Chick");
		lblChick.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblChick, "1, 13");
		
		noteSpinControl_chick = new NoteSpinControl(configFull);
		noteSpinControl_chick.setEventListener(this);
		panel_notes.add(noteSpinControl_chick, "3, 13, left, center");
		
		JLabel lblSplash = new JLabel("Splash");
		lblSplash.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblSplash, "1, 14");
		
		noteSpinControl_splash = new NoteSpinControl(configFull);
		noteSpinControl_splash.setEventListener(this);
		panel_notes.add(noteSpinControl_splash, "3, 14, left, center");

		controlsInited = true;
	}
	
	public void valueChanged() {
		if (controlsInited) {
			updateConfig();
			firePropertyChange("valueChanged", false, true);
		}
	}

	public void updateControls() {
		comboBox_type.setSelectedIndexWithoutEvent(configFull.configPedal.type?1:0);
		comboBox_curve.setSelectedIndexWithoutEvent(configFull.configPedal.curve);
		comboBox_input.setSelectedIndexWithoutEvent((configFull.configPedal.hhInput-2)/2);
		checkBox_altInput.setValueWithoutEvent(configFull.configPedal.altIn);
		checkBox_reverseLevels.setValueWithoutEvent(configFull.configPedal.reverseLevels);
		checkBox_softChicks.setValueWithoutEvent(configFull.configPedal.softChicks);
		checkBox_autoLevels.setValueWithoutEvent(configFull.configPedal.autoLevels);
		spin127Control_chickDelay.setValueWithoutEvents(configFull.configPedal.chickDelay);
		spin127Control_cc.setValueWithoutEvents(configFull.configPedal.cc);
		spin127Control_ccRdcLvl.setValueWithoutEvents(configFull.configPedal.ccRdcLvl);
		spin1023Control_lowLevel.setValueWithoutEvents(configFull.configPedal.lowLevel);
		spin1023Control_highLevel.setValueWithoutEvents(configFull.configPedal.highLevel);
		spin127Control_openLevel.setValueWithoutEvents(configFull.configPedal.openLevel);
		spin127Control_semiOpen.setValueWithoutEvents(configFull.configPedal.semiOpenLevel);
		spin127Control_halfOpen.setValueWithoutEvents(configFull.configPedal.halfOpenLevel);
		spin127Control_closed.setValueWithoutEvents(configFull.configPedal.closedLevel);
		spin127Control_shortThres.setValueWithoutEvents(configFull.configPedal.shortThres);
		spin127Control_longThres.setValueWithoutEvents(configFull.configPedal.longThres);
		noteSpinControl_bowSemiOpen.setValueWithoutEvents(configFull.configPedal.bowSemiOpenNote);
		noteSpinControl_edgeSemiOpen.setValueWithoutEvents(configFull.configPedal.edgeSemiOpenNote);
		noteSpinControl_bellSemiOpen.setValueWithoutEvents(configFull.configPedal.bellSemiOpenNote);
		noteSpinControl_bowHalfOpen.setValueWithoutEvents(configFull.configPedal.bowHalfOpenNote);
		noteSpinControl_edgeHalfOpen.setValueWithoutEvents(configFull.configPedal.edgeHalfOpenNote);
		noteSpinControl_bellHalfOpen.setValueWithoutEvents(configFull.configPedal.bellHalfOpenNote);
		noteSpinControl_bowSemiClosed.setValueWithoutEvents(configFull.configPedal.bowSemiClosedNote);
		noteSpinControl_edgeSemiClosed.setValueWithoutEvents(configFull.configPedal.edgeSemiClosedNote);
		noteSpinControl_bellSemiClosed.setValueWithoutEvents(configFull.configPedal.bellSemiClosedNote);
		noteSpinControl_bowClosed.setValueWithoutEvents(configFull.configPedal.bowClosedNote);
		noteSpinControl_edgeClosed.setValueWithoutEvents(configFull.configPedal.edgeClosedNote);
		noteSpinControl_bellClosed.setValueWithoutEvents(configFull.configPedal.bellClosedNote);
		noteSpinControl_chick.setValueWithoutEvents(configFull.configPedal.chickNote);
		noteSpinControl_splash.setValueWithoutEvents(configFull.configPedal.splashNote);
	}
	
	public void updateConfig() {
		if (controlsInited) {
			configFull.configPedal.type = (comboBox_type.getSelectedIndex() != 0);
			configFull.configPedal.curve = comboBox_curve.getSelectedIndex();
			configFull.configPedal.hhInput = (comboBox_input.getSelectedIndex()*2 + 2);
			configFull.configPedal.altIn = checkBox_altInput.isSelected();
			configFull.configPedal.reverseLevels = checkBox_reverseLevels.isSelected();
			configFull.configPedal.softChicks = checkBox_softChicks.isSelected();
			configFull.configPedal.autoLevels = checkBox_autoLevels.isSelected();
			configFull.configPedal.chickDelay = (Integer)spin127Control_chickDelay.getSpinner().getValue();
			configFull.configPedal.cc = (Integer)spin127Control_cc.getSpinner().getValue();
			configFull.configPedal.ccRdcLvl = (Integer)spin127Control_ccRdcLvl.getSpinner().getValue();
			configFull.configPedal.openLevel = (Integer)spin127Control_openLevel.getSpinner().getValue();
			configFull.configPedal.semiOpenLevel = (Integer)spin127Control_semiOpen.getSpinner().getValue();
			configFull.configPedal.halfOpenLevel = (Integer)spin127Control_halfOpen.getSpinner().getValue();
			configFull.configPedal.closedLevel = (Integer)spin127Control_closed.getSpinner().getValue();
			configFull.configPedal.shortThres = (Integer)spin127Control_shortThres.getSpinner().getValue();
			configFull.configPedal.longThres = (Integer)spin127Control_longThres.getSpinner().getValue();
			configFull.configPedal.lowLevel = (Integer)spin1023Control_lowLevel.getSpinner().getValue();
			configFull.configPedal.highLevel = (Integer)spin1023Control_highLevel.getSpinner().getValue();
		
			configFull.configPedal.bowSemiOpenNote = (Integer)noteSpinControl_bowSemiOpen.getValue();		
			configFull.configPedal.edgeSemiOpenNote = (Integer)noteSpinControl_edgeSemiOpen.getValue();
			configFull.configPedal.bellSemiOpenNote = (Integer)noteSpinControl_bellSemiOpen.getValue();
			configFull.configPedal.bowHalfOpenNote = (Integer)noteSpinControl_bowHalfOpen.getValue();
			configFull.configPedal.edgeHalfOpenNote = (Integer)noteSpinControl_edgeHalfOpen.getValue();
			configFull.configPedal.bellHalfOpenNote = (Integer)noteSpinControl_bellHalfOpen.getValue();
			configFull.configPedal.bowSemiClosedNote = (Integer)noteSpinControl_bowSemiClosed.getValue();
			configFull.configPedal.edgeSemiClosedNote = (Integer)noteSpinControl_edgeSemiClosed.getValue();
			configFull.configPedal.bellSemiClosedNote = (Integer)noteSpinControl_bellSemiClosed.getValue();
			configFull.configPedal.bowClosedNote = (Integer)noteSpinControl_bowClosed.getValue();
			configFull.configPedal.edgeClosedNote = (Integer)noteSpinControl_edgeClosed.getValue();
			configFull.configPedal.bellClosedNote = (Integer)noteSpinControl_bellClosed.getValue();
			configFull.configPedal.chickNote = (Integer)noteSpinControl_chick.getValue();
			configFull.configPedal.splashNote = (Integer)noteSpinControl_splash.getValue();			
		}
	}
	
	public JButton getBtnGet() {
		return btnGet;
	}
	public JButton getBtnSend() {
		return btnSend;
	}
	
	public void updateInputCountsControls(int count) {
		comboBox_input.selectEventsDisabled = 1;
		comboBox_input.removeAllItems();
        for(int i=2; i<(count - 1); i++){
        	comboBox_input.addItem(((Integer)i).toString());
        	i++;
        }
		comboBox_input.setSelectedIndexWithoutEvent((configFull.configPedal.hhInput-2)/2);
	}

	public JButton getBtnLoad() {
		return btnLoad;
	}
	public JButton getBtnSave() {
		return btnSave;
	}
}
