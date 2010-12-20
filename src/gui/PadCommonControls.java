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
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("12dlu"),},
			new RowSpec[] {
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
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button, "5, 1, fill, fill");
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblNote, "1, 2, right, center");
		
		NoteSpinControl noteSpinControl = new NoteSpinControl();
		add(noteSpinControl, "3, 2, left, center");
		
		JButton button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_1, "5, 2, fill, fill");
		
		JLabel lblAltNote = new JLabel("Alt Note");
		lblAltNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblAltNote, "1, 3, right, center");
		
		NoteSpinControl noteSpinControl_1 = new NoteSpinControl();
		add(noteSpinControl_1, "3, 3, left, fill");
		
		JButton button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_2, "5, 3, default, fill");
		
		JLabel lblPressrollNote = new JLabel("Pressroll Note");
		lblPressrollNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblPressrollNote, "1, 4, right, center");
		
		NoteSpinControl noteSpinControl_2 = new NoteSpinControl();
		add(noteSpinControl_2, "3, 4, left, fill");
		
		JButton button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_3, "5, 4, default, fill");
		
		JLabel lblCurve = new JLabel("Channel");
		lblCurve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCurve, "1, 5, right, center");
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(10, 1, 16, 1));
		add(spinner, "3, 5, left, center");
		
		JButton button_4 = new JButton("");
		button_4.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_4, "5, 5, fill, fill");
		
		JLabel lblCurve_1 = new JLabel("Curve");
		lblCurve_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCurve_1, "1, 6, right, center");
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_1, "3, 6, fill, center");
		
		JButton button_5 = new JButton("");
		button_5.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_5, "5, 6, fill, fill");
		
		JLabel lblCompression = new JLabel("Compression");
		lblCompression.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCompression, "1, 7, right, center");
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_2, "3, 7, fill, center");
		
		JButton button_6 = new JButton("");
		button_6.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_6, "5, 7, fill, fill");
		
		JLabel lblShift = new JLabel("Level Shift");
		lblShift.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblShift, "1, 8, right, center");
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_3, "3, 8, fill, center");
		
		JButton button_7 = new JButton("");
		button_7.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_7, "5, 8, fill, fill");
		
		JLabel lblXtalkLevel = new JLabel("XTalk Level");
		lblXtalkLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblXtalkLevel, "1, 9, right, center");
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_4, "3, 9, fill, center");
		
		JButton button_8 = new JButton("");
		button_8.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_8, "5, 9, fill, fill");
		
		JLabel lblXtalkGroup = new JLabel("XTalk Group");
		lblXtalkGroup.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblXtalkGroup, "1, 10, right, center");
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_5, "3, 10, fill, center");
		
		JButton button_9 = new JButton("");
		button_9.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_9, "5, 10, fill, fill");
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblThreshold, "1, 11, right, center");
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(30, null, 127, 1));
		add(spinner_1, "3, 11, left, center");
		
		JButton button_10 = new JButton("");
		button_10.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_10, "5, 11, fill, fill");
		
		JLabel lblGain = new JLabel("Gain");
		lblGain.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblGain, "1, 12, right, center");
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_6, "3, 12, fill, center");
		
		JButton button_11 = new JButton("");
		button_11.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_11, "5, 12, fill, fill");
		
		JLabel lblHighlevelAuto = new JLabel("HighLevel Auto");
		lblHighlevelAuto.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblHighlevelAuto, "1, 13, right, center");
		
		JCheckBox checkBox = new JCheckBox("");
		add(checkBox, "3, 13, left, center");
		
		JButton button_12 = new JButton("");
		button_12.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_12, "5, 13, fill, fill");
		
		JLabel lblHighlevel = new JLabel("HighLevel");
		lblHighlevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblHighlevel, "1, 14, right, center");
		
		JPanel panel = new JPanel();
		add(panel, "3, 14, left, top");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("58px"),},
			new RowSpec[] {
				RowSpec.decode("20px"),}));
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(64, 64, 1023, 1));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner_2,"#");
		spinner_2.setEditor(editor);
		panel.add(spinner_2, "1, 1, fill, top");
		
		JButton button_13 = new JButton("");
		button_13.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_13, "5, 14, fill, fill");
		
		JLabel lblRetriggerMask = new JLabel("Retrigger Mask");
		lblRetriggerMask.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblRetriggerMask, "1, 15, right, center");
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setModel(new SpinnerNumberModel(0, 0, 127, 1));
		add(spinner_3, "3, 15, left, center");
		
		JButton button_14 = new JButton("");
		button_14.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_14, "5, 15, fill, fill");
		
		JLabel lblDyn = new JLabel(" DynLevel");
		lblDyn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblDyn.setToolTipText("Dynamic Threshold Level");
		add(lblDyn, "1, 16, right, center");
		
		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_7, "3, 16, fill, center");
		
		JButton button_15 = new JButton("");
		button_15.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_15, "5, 16, fill, fill");
		
		JLabel lblDyntime = new JLabel("DynTime");
		lblDyntime.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblDyntime.setToolTipText("Dynamic Threshold decay time");
		add(lblDyntime, "1, 17, right, center");
		
		JComboBox comboBox_8 = new JComboBox();
		comboBox_8.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_8, "3, 17, fill, center");
		
		JButton button_16 = new JButton("");
		button_16.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_16, "5, 17, fill, fill");
		
		JLabel lblMinscan = new JLabel("MinScan");
		lblMinscan.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMinscan, "1, 18, right, center");
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerNumberModel(20, 10, 100, 1));
		add(spinner_4, "3, 18, left, center");
		
		JButton button_17 = new JButton("");
		button_17.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_17, "5, 18, fill, fill");
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblType, "1, 19, right, center");
		
		JComboBox comboBox_9 = new JComboBox();
		comboBox_9.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_9, "3, 19, fill, center");
		
		JButton button_18 = new JButton("");
		button_18.setIcon(new ImageIcon(PadCommonControls.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		add(button_18, "5, 19, fill, fill");
		

	}

}
