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
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class PadCommonControls extends JPanel {
	private JComboBox comboBox_name;
	private NoteSpinControl noteSpinControl_note;
	private NoteSpinControl noteSpinControl_altNote;
	private JCheckBox checkBox_altLinked;
	private NoteSpinControl noteSpinControl_pressrollNote;
	private JCheckBox checkBox_pressrollLinked;
	private JSpinner spinner_channel;
	private JCheckBox checkBox_special;
	private JComboBox comboBox_curve;
	private JComboBox comboBox_compression;
	private JComboBox comboBox_shift;
	private JComboBox comboBox_xtalkLevel;
	private JComboBox comboBox_xtalkGroup;
	private JSpinner spinner_threshold;
	private JComboBox comboBox_gain;
	private JCheckBox checkBox_autoLevel;
	private JSpinner spinner_highLevel;
	private JSpinner spinner_retrigger;
	private JComboBox comboBox_dynLevel;
	private JComboBox comboBox_dynTime;
	private JSpinner spinner_minScan;
	private JComboBox comboBox_type;
	private boolean head_rim_pad;
	private static final boolean head_pad = true;
	private static final boolean rim_pad = false;
	
	private ConfigPad configPad;

	/**
	 * Create the panel.
	 */
	public PadCommonControls() {
		configPad = new ConfigPad();
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("78px"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("65dlu"),
				ColumnSpec.decode("12dlu"),},
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
		
		comboBox_name = new JComboBox();
		comboBox_name.setMaximumRowCount(30);
		comboBox_name.addItem("");
		for (String string : Constants.CUSTOM_PADS_NAMES_LIST) {
			comboBox_name.addItem(string);
			}
		comboBox_name.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_name, "3, 1, fill, center");
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblNote, "1, 2, right, center");
		
		noteSpinControl_note = new NoteSpinControl();
		add(noteSpinControl_note, "3, 2, left, center");
		
		JLabel lblAltNote = new JLabel("Alt Note");
		lblAltNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblAltNote, "1, 3, right, center");
		
		noteSpinControl_altNote = new NoteSpinControl();
		add(noteSpinControl_altNote, "3, 3, left, fill");
		
		checkBox_altLinked = new JCheckBox("");
		checkBox_altLinked.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noteSpinControl_altNote.getSpinner().setEnabled(!checkBox_altLinked.isSelected());
			}
		});
		checkBox_altLinked.setToolTipText("Linked to Note");
		add(checkBox_altLinked, "4, 3");
		
		JLabel lblPressrollNote = new JLabel("Pressroll Note");
		lblPressrollNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblPressrollNote, "1, 4, right, center");
		
		noteSpinControl_pressrollNote = new NoteSpinControl();
		add(noteSpinControl_pressrollNote, "3, 4, left, fill");
		
		checkBox_pressrollLinked = new JCheckBox("");
		checkBox_pressrollLinked.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noteSpinControl_pressrollNote.getSpinner().setEnabled(!checkBox_pressrollLinked.isSelected());
			}
		});
		checkBox_pressrollLinked.setToolTipText("Linked to Note");
		add(checkBox_pressrollLinked, "4, 4");
		
		JLabel lblCurve = new JLabel("Channel");
		lblCurve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCurve, "1, 5, right, center");
		
		spinner_channel = new JSpinner();
		spinner_channel.setModel(new SpinnerNumberModel(1, 1, 16, 1));
		add(spinner_channel, "3, 5, left, center");
		
		JLabel lblSpecialFunction = new JLabel("Special Function");
		lblSpecialFunction.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblSpecialFunction, "1, 6, right, default");
		
		checkBox_special = new JCheckBox("");
		add(checkBox_special, "3, 6");
		
		JLabel lblCurve_1 = new JLabel("Curve");
		lblCurve_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCurve_1, "1, 7, right, center");
		
		comboBox_curve = new JComboBox();
		comboBox_curve.setMaximumRowCount(16);
		for (String string : Constants.CURVES_LIST) {
			comboBox_curve.addItem(string);
			}
		comboBox_curve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_curve, "3, 7, fill, center");
		
		JLabel lblCompression = new JLabel("Compression");
		lblCompression.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCompression, "1, 8, right, center");
		
		comboBox_compression = new JComboBox();
        for(int i=0; i<8; i++){
    		comboBox_compression.addItem(((Integer)i).toString());
        }		
		comboBox_compression.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_compression, "3, 8, fill, center");
		
		JLabel lblShift = new JLabel("Level Shift");
		lblShift.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblShift, "1, 9, right, center");
		
		comboBox_shift = new JComboBox();
        for(int i=0; i<8; i++){
    		comboBox_shift.addItem(((Integer)(i*8)).toString());
        }		
		comboBox_shift.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_shift, "3, 9, fill, center");
		
		JLabel lblXtalkLevel = new JLabel("XTalk Level");
		lblXtalkLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblXtalkLevel, "1, 10, right, center");
		
		comboBox_xtalkLevel = new JComboBox();
        for(int i=0; i<8; i++){
    		comboBox_xtalkLevel.addItem(((Integer)i).toString());
        }		
		comboBox_xtalkLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_xtalkLevel, "3, 10, fill, center");
		
		JLabel lblXtalkGroup = new JLabel("XTalk Group");
		lblXtalkGroup.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblXtalkGroup, "1, 11, right, center");
		
		comboBox_xtalkGroup = new JComboBox();
        for(int i=0; i<8; i++){
    		comboBox_xtalkGroup.addItem(((Integer)i).toString());
        }		
		comboBox_xtalkGroup.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_xtalkGroup, "3, 11, fill, center");
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblThreshold, "1, 12, right, center");
		
		spinner_threshold = new JSpinner();
		spinner_threshold.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 127), new Short((short) 1)));
		add(spinner_threshold, "3, 12, left, center");
		
		JLabel lblGain = new JLabel("Gain");
		lblGain.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblGain, "1, 13, right, center");
		
		comboBox_gain = new JComboBox();
		comboBox_gain.setMaximumRowCount(10);
        for(int i=0; i<9; i++){
    		comboBox_gain.addItem(((Integer)i).toString());
        }		
		comboBox_gain.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_gain, "3, 13, fill, center");
		
		JLabel lblHighlevelAuto = new JLabel("HighLevel Auto");
		lblHighlevelAuto.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblHighlevelAuto, "1, 14, right, center");
		
		checkBox_autoLevel = new JCheckBox("");
		add(checkBox_autoLevel, "3, 14, left, center");
		
		JLabel lblHighlevel = new JLabel("HighLevel");
		lblHighlevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblHighlevel, "1, 15, right, center");
		
		JPanel panel = new JPanel();
		add(panel, "3, 15, left, top");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("58px"),},
			new RowSpec[] {
				RowSpec.decode("20px"),}));
		
		spinner_highLevel = new JSpinner();
		spinner_highLevel.setModel(new SpinnerNumberModel(new Short((short) 64), new Short((short) 64), new Short((short) 1023), new Short((short) 1)));
		JSpinner.NumberEditor jsne_spinner_highLevel = new JSpinner.NumberEditor(spinner_highLevel,"#");
		spinner_highLevel.setEditor(jsne_spinner_highLevel);
		panel.add(spinner_highLevel, "1, 1, fill, top");
		
		JLabel lblRetriggerMask = new JLabel("Retrigger Mask");
		lblRetriggerMask.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblRetriggerMask, "1, 16, right, center");
		
		spinner_retrigger = new JSpinner();
		spinner_retrigger.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 127), new Short((short) 1)));
		add(spinner_retrigger, "3, 16, left, center");
		
		JLabel lblDyn = new JLabel(" DynLevel");
		lblDyn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblDyn.setToolTipText("Dynamic Threshold Level");
		add(lblDyn, "1, 17, right, center");
		
		comboBox_dynLevel = new JComboBox();
		comboBox_dynLevel.setMaximumRowCount(16);
        for(int i=0; i<16; i++){
    		comboBox_dynLevel.addItem(((Integer)i).toString());
        }		
		comboBox_dynLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_dynLevel, "3, 17, fill, center");
		
		JLabel lblDyntime = new JLabel("DynTime");
		lblDyntime.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblDyntime.setToolTipText("Dynamic Threshold decay time");
		add(lblDyntime, "1, 18, right, center");
		
		comboBox_dynTime = new JComboBox();
		comboBox_dynTime.setMaximumRowCount(16);
        for(int i=0; i<16; i++){
    		comboBox_dynTime.addItem(((Integer)(i*4)).toString());
        }		
		comboBox_dynTime.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_dynTime, "3, 18, fill, center");
		
		JLabel lblMinscan = new JLabel("MinScan");
		lblMinscan.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMinscan, "1, 19, right, center");
		
		spinner_minScan = new JSpinner();
		spinner_minScan.setModel(new SpinnerNumberModel(new Short((short) 10), new Short((short) 10), new Short((short) 100), new Short((short) 1)));
		add(spinner_minScan, "3, 19, left, center");
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblType, "1, 20, right, center");
		
		comboBox_type = new JComboBox();
		comboBox_type.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_type, "3, 20, fill, center");
		

	}

	private void updateControls() {
		comboBox_name.setSelectedIndex(configPad.name);
		noteSpinControl_note.getSpinner().setValue(configPad.note);
		noteSpinControl_altNote.getSpinner().setValue(configPad.altNote);
		noteSpinControl_pressrollNote.getSpinner().setValue(configPad.pressrollNote);
		spinner_channel.setValue(configPad.channel + 1);
		checkBox_special.setSelected(configPad.special);
		comboBox_curve.setSelectedIndex(configPad.curve);
		comboBox_compression.setSelectedIndex(configPad.compression);
		comboBox_shift.setSelectedIndex(configPad.shift);
		comboBox_xtalkLevel.setSelectedIndex(configPad.xtalkLevel);
		comboBox_xtalkGroup.setSelectedIndex(configPad.xtalkGroup);
		spinner_threshold.setValue(configPad.threshold);
		comboBox_gain.setSelectedIndex(configPad.gain);
		checkBox_autoLevel.setSelected(configPad.autoLevel);
		spinner_highLevel.setValue(configPad.levelMax);
		spinner_retrigger.setValue(configPad.retrigger);
		comboBox_dynLevel.setSelectedIndex(configPad.dynLevel);
		comboBox_dynTime.setSelectedIndex(configPad.dynTime);
		spinner_minScan.setValue(configPad.minScan);
		//TO-DO piezo/switch, 3way type, dual and etc
		//comboBox_type.setSelectedIndex(configPad.type?1:0);
		comboBox_type.removeAllItems();
		if (head_rim_pad == head_pad) {
			comboBox_type.addItem("Single");
			comboBox_type.addItem("Dual or 3way Ymaha");
			comboBox_type.addItem("3way Roland");
			if (configPad.dual) {
				comboBox_type.setSelectedIndex(1);
			}
			if (configPad.threeWay) {
				comboBox_type.setSelectedIndex(2);
			}			
		} else {
			comboBox_type.addItem("Piezo");
			comboBox_type.addItem("Switch");
			if (configPad.type) {
				comboBox_type.setSelectedIndex(1);
			}
		}
	}
	
	private void updateConfig() {
		configPad.name = (short)comboBox_name.getSelectedIndex();
		configPad.note = ((Short)noteSpinControl_note.getSpinner().getValue()).shortValue();
		configPad.altNote = ((Short)noteSpinControl_altNote.getSpinner().getValue()).shortValue();
		configPad.pressrollNote = ((Short)noteSpinControl_pressrollNote.getSpinner().getValue()).shortValue();
		configPad.channel = (short)(((Integer)spinner_channel.getValue()).shortValue() - 1);
		configPad.special = checkBox_special.isSelected();
		configPad.curve = (short)comboBox_curve.getSelectedIndex();
		configPad.compression = (short)comboBox_compression.getSelectedIndex();
		configPad.shift = (short)comboBox_shift.getSelectedIndex();
		configPad.xtalkLevel = (short)comboBox_xtalkLevel.getSelectedIndex();
		configPad.xtalkGroup = (short)comboBox_xtalkGroup.getSelectedIndex();
		configPad.threshold = (Short)spinner_threshold.getValue();
		configPad.gain = (short)comboBox_gain.getSelectedIndex();
		configPad.autoLevel = checkBox_autoLevel.isSelected();
		configPad.levelMax = (Short)spinner_highLevel.getValue();
		configPad.retrigger = (Short)spinner_retrigger.getValue();
		configPad.dynLevel = (short)comboBox_dynLevel.getSelectedIndex();
		configPad.dynTime = (short)comboBox_dynTime.getSelectedIndex();
		configPad.minScan = (Short)spinner_minScan.getValue();
		//TO-DO piezo/switch, 3way type, dual and etc
		// configPad.type = (comboBox_type.getSelectedIndex() != 0);
		if (head_rim_pad == head_pad) {
			configPad.type = false;
			switch (comboBox_type.getSelectedIndex()) {
			case 1:
				configPad.dual = true;
				configPad.threeWay = false;
				break;
			case 2:
				configPad.dual = false;
				configPad.threeWay = true;
				break;
			default:
				configPad.dual = false;
				configPad.threeWay = false;
				break;
			}
		} else {
			if (comboBox_type.getSelectedIndex() == 0) {
				configPad.type = false;
			} else {
				configPad.type = true;
			}
		}
	}
	
	public void setConfig(ConfigPad config, boolean pad_type, int pad_id) {
		head_rim_pad = pad_type;
		configPad.copyVarsFrom(config);
		comboBox_name.removeItemAt(0);
		comboBox_name.insertItemAt(Constants.PADS_NAMES_LIST[pad_id], 0);
		updateControls();
	}
	
	public ConfigPad getConfig() {
		updateConfig();
		return configPad;
	}
	
	public JComboBox getComboBox_type() {
		return comboBox_type;
	}
	public JComboBox getComboBox_name() {
		return comboBox_name;
	}
}
