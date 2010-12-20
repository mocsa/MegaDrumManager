package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Font;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;

public class PadCommonControls extends JPanel {

	/**
	 * Create the panel.
	 */
	public PadCommonControls() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("82px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("97px"),
				ColumnSpec.decode("1dlu"),},
			new RowSpec[] {
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px"),}));
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblName.setToolTipText("Input Name");
		add(lblName, "1, 1, right, center");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox, "3, 1, fill, center");
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblNote, "1, 2, right, center");
		
		NoteSpinControl noteSpinControl = new NoteSpinControl();
		add(noteSpinControl, "3, 2, left, center");
		
		JLabel lblAltNote = new JLabel("Alt Note");
		lblAltNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblAltNote, "1, 3, right, center");
		
		NoteSpinControl noteSpinControl_1 = new NoteSpinControl();
		add(noteSpinControl_1, "3, 3, left, fill");
		
		JLabel lblPressrollNote = new JLabel("Pressroll Note");
		lblPressrollNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblPressrollNote, "1, 4, right, center");
		
		NoteSpinControl noteSpinControl_2 = new NoteSpinControl();
		add(noteSpinControl_2, "3, 4, left, fill");
		
		JLabel lblCurve = new JLabel("Channel");
		lblCurve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCurve, "1, 5, right, center");
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(10, 1, 16, 1));
		add(spinner, "3, 5, left, center");
		
		JLabel lblSpecialFunction = new JLabel("Special Function");
		lblSpecialFunction.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblSpecialFunction, "1, 6, right, default");
		
		JCheckBox checkBox_1 = new JCheckBox("");
		add(checkBox_1, "3, 6");
		
		JLabel lblCurve_1 = new JLabel("Curve");
		lblCurve_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCurve_1, "1, 7, right, center");
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_1, "3, 7, fill, center");
		
		JLabel lblCompression = new JLabel("Compression");
		lblCompression.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCompression, "1, 8, right, center");
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_2, "3, 8, fill, center");
		
		JLabel lblShift = new JLabel("Level Shift");
		lblShift.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblShift, "1, 9, right, center");
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_3, "3, 9, fill, center");
		
		JLabel lblXtalkLevel = new JLabel("XTalk Level");
		lblXtalkLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblXtalkLevel, "1, 10, right, center");
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_4, "3, 10, fill, center");
		
		JLabel lblXtalkGroup = new JLabel("XTalk Group");
		lblXtalkGroup.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblXtalkGroup, "1, 11, right, center");
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_5, "3, 11, fill, center");
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblThreshold, "1, 12, right, center");
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(30, null, 127, 1));
		add(spinner_1, "3, 12, left, center");
		
		JLabel lblGain = new JLabel("Gain");
		lblGain.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblGain, "1, 13, right, center");
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_6, "3, 13, fill, center");
		
		JLabel lblHighlevelAuto = new JLabel("HighLevel Auto");
		lblHighlevelAuto.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblHighlevelAuto, "1, 14, right, center");
		
		JCheckBox checkBox = new JCheckBox("");
		add(checkBox, "3, 14, left, center");
		
		JLabel lblHighlevel = new JLabel("HighLevel");
		lblHighlevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblHighlevel, "1, 15, right, center");
		
		JPanel panel = new JPanel();
		add(panel, "3, 15, left, top");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("58px"),},
			new RowSpec[] {
				RowSpec.decode("20px"),}));
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(64, 64, 1023, 1));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner_2,"#");
		spinner_2.setEditor(editor);
		panel.add(spinner_2, "1, 1, fill, top");
		
		JLabel lblRetriggerMask = new JLabel("Retrigger Mask");
		lblRetriggerMask.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblRetriggerMask, "1, 16, right, center");
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setModel(new SpinnerNumberModel(0, 0, 127, 1));
		add(spinner_3, "3, 16, left, center");
		
		JLabel lblDyn = new JLabel(" DynLevel");
		lblDyn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblDyn.setToolTipText("Dynamic Threshold Level");
		add(lblDyn, "1, 17, right, center");
		
		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_7, "3, 17, fill, center");
		
		JLabel lblDyntime = new JLabel("DynTime");
		lblDyntime.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblDyntime.setToolTipText("Dynamic Threshold decay time");
		add(lblDyntime, "1, 18, right, center");
		
		JComboBox comboBox_8 = new JComboBox();
		comboBox_8.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_8, "3, 18, fill, center");
		
		JLabel lblMinscan = new JLabel("MinScan");
		lblMinscan.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMinscan, "1, 19, right, center");
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerNumberModel(20, 10, 100, 1));
		add(spinner_4, "3, 19, left, center");
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblType, "1, 20, right, center");
		
		JComboBox comboBox_9 = new JComboBox();
		comboBox_9.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_9, "3, 20, fill, center");
		

	}

}
