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

public class PedalControls extends JPanel {

	/**
	 * Create the panel.
	 */
	public PedalControls() {
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		comboBox.addItem("FootContr");
		comboBox.addItem("Pot");		
		panel_misc.add(comboBox, "3, 1, fill, default");
		
		JLabel lblCurve = new JLabel("Curve");
		lblCurve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblCurve, "1, 2, right, default");
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		for (String string : Constants.CURVES_LIST) {
			comboBox_1.addItem(string);
			}
		panel_misc.add(comboBox_1, "3, 2, fill, default");
		
		JLabel lblHihatInput = new JLabel("HiHat Input");
		lblHihatInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblHihatInput, "1, 3, right, default");
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(comboBox_2, "3, 3, fill, default");
		
		JLabel lblAltInput = new JLabel("Alt Input");
		lblAltInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblAltInput, "1, 4");
		
		JCheckBox checkBox = new JCheckBox("");
		checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox, "3, 4");
		
		JLabel lblReverseLevels = new JLabel("Reverse Levels");
		lblReverseLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblReverseLevels, "1, 5");
		
		JCheckBox checkBox_1 = new JCheckBox("");
		checkBox_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_1, "3, 5");
		
		JLabel lblSoftChicks = new JLabel("Soft Chicks");
		lblSoftChicks.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblSoftChicks, "1, 6");
		
		JCheckBox checkBox_2 = new JCheckBox("");
		checkBox_2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_2, "3, 6");
		
		JLabel lblAutoLevels = new JLabel("Auto Levels");
		lblAutoLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblAutoLevels, "1, 7");
		
		JCheckBox checkBox_3 = new JCheckBox("");
		checkBox_3.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_3, "3, 7");
		
		JLabel lblChickDelay = new JLabel("Chick Delay");
		lblChickDelay.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblChickDelay, "1, 8");
		
		Spin127Control spin127Control_6 = new Spin127Control();
		panel_misc.add(spin127Control_6, "3, 8, fill, fill");
		
		JLabel lblCcNumber = new JLabel("CC Number");
		lblCcNumber.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblCcNumber, "1, 9");
		
		Spin127Control spin127Control_7 = new Spin127Control();
		panel_misc.add(spin127Control_7, "3, 9, fill, fill");
		
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
		
		Spin1023Control spin1023Control = new Spin1023Control();
		panel_levels.add(spin1023Control, "3, 1, fill, fill");
		
		JLabel lblHigh = new JLabel("High");
		lblHigh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblHigh, "1, 2");
		
		Spin1023Control spin1023Control_1 = new Spin1023Control();
		panel_levels.add(spin1023Control_1, "3, 2, fill, fill");
		
		JLabel lblOpen = new JLabel("Open");
		lblOpen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblOpen, "1, 3");
		
		Spin127Control spin127Control = new Spin127Control();
		panel_levels.add(spin127Control, "3, 3, fill, fill");
		
		JLabel lblClosed = new JLabel("SemiOpen");
		lblClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblClosed, "1, 4");
		
		Spin127Control spin127Control_1 = new Spin127Control();
		panel_levels.add(spin127Control_1, "3, 4, fill, fill");
		
		JLabel lblHalfopen = new JLabel("HalfOpen");
		lblHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblHalfopen, "1, 5");
		
		Spin127Control spin127Control_2 = new Spin127Control();
		panel_levels.add(spin127Control_2, "3, 5, fill, fill");
		
		JLabel lblClosed_1 = new JLabel("Closed");
		lblClosed_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblClosed_1, "1, 6");
		
		Spin127Control spin127Control_3 = new Spin127Control();
		panel_levels.add(spin127Control_3, "3, 6, fill, fill");
		
		JLabel lblShortThresh = new JLabel("ShortChickThresh");
		lblShortThresh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblShortThresh, "1, 7");
		
		Spin127Control spin127Control_4 = new Spin127Control();
		panel_levels.add(spin127Control_4, "3, 7, fill, fill");
		
		JLabel lblLongchickthresh = new JLabel("LongChickThresh");
		lblLongchickthresh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblLongchickthresh, "1, 8");
		
		Spin127Control spin127Control_5 = new Spin127Control();
		panel_levels.add(spin127Control_5, "3, 8, fill, fill");
		
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
		
		NoteSpinControl noteSpinControl = new NoteSpinControl();
		panel_notes.add(noteSpinControl, "3, 1, left, center");
		
		JLabel lblEdgeSemiopen = new JLabel("Edge SemiOpen");
		lblEdgeSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeSemiopen, "1, 2");
		
		NoteSpinControl noteSpinControl_1 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_1, "3, 2, left, center");
		
		JLabel lblBellSemiopen = new JLabel("Bell SemiOpen");
		lblBellSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellSemiopen, "1, 3");
		
		NoteSpinControl noteSpinControl_2 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_2, "3, 3, left, center");
		
		JLabel lblBowHalfopen = new JLabel("Bow HalfOpen");
		lblBowHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowHalfopen, "1, 4");
		
		NoteSpinControl noteSpinControl_3 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_3, "3, 4, left, center");
		
		JLabel lblEdgeHalfopen = new JLabel("Edge HalfOpen");
		lblEdgeHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeHalfopen, "1, 5");
		
		NoteSpinControl noteSpinControl_4 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_4, "3, 5, left, center");
		
		JLabel lblBellHalfopen = new JLabel("Bell HalfOpen");
		lblBellHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellHalfopen, "1, 6");
		
		NoteSpinControl noteSpinControl_5 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_5, "3, 6, left, center");
		
		JLabel lblBowSemiclosed = new JLabel("Bow SemiClosed");
		lblBowSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowSemiclosed, "1, 7");
		
		NoteSpinControl noteSpinControl_6 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_6, "3, 7, left, center");
		
		JLabel lblEdgeSemiclosed = new JLabel("Edge SemiClosed");
		lblEdgeSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeSemiclosed, "1, 8");
		
		NoteSpinControl noteSpinControl_7 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_7, "3, 8, left, center");
		
		JLabel lblBellSemiclosed = new JLabel("Bell SemiClosed");
		lblBellSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellSemiclosed, "1, 9");
		
		NoteSpinControl noteSpinControl_8 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_8, "3, 9, left, center");
		
		JLabel lblBowClosed = new JLabel("Bow Closed");
		lblBowClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowClosed, "1, 10");
		
		NoteSpinControl noteSpinControl_9 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_9, "3, 10, left, center");
		
		JLabel lblEdgeClosed = new JLabel("Edge Closed");
		lblEdgeClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeClosed, "1, 11");
		
		NoteSpinControl noteSpinControl_10 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_10, "3, 11, left, center");
		
		JLabel lblBellClosed = new JLabel("Bell Closed");
		lblBellClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellClosed, "1, 12");
		
		NoteSpinControl noteSpinControl_11 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_11, "3, 12, left, center");
		
		JLabel lblChick = new JLabel("Chick");
		lblChick.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblChick, "1, 13");
		
		NoteSpinControl noteSpinControl_12 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_12, "3, 13, left, center");
		
		JLabel lblSplash = new JLabel("Splash");
		lblSplash.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblSplash, "1, 14");
		
		NoteSpinControl noteSpinControl_13 = new NoteSpinControl();
		panel_notes.add(noteSpinControl_13, "3, 14, left, center");
		
		JPanel panel_buttons = new JPanel();
		panel.add(panel_buttons, "2, 4, fill, fill");
		
		JButton button = new JButton("Get");
		button.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(button);
		
		JButton button_1 = new JButton("Send");
		button_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(button_1);

	}

}
