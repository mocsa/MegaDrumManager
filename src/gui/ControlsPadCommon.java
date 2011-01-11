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
import javax.swing.SwingUtilities;

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
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ControlsPadCommon extends JPanel {
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
	private JPanel panel_1;
	private JPanel panel_2;
	private PadButton padButton__name;
	private PadButton padButton__note;
	private PadButton padButton_altNote;
	private PadButton padButton_pressrollNote;
	private PadButton padButton_channel;
	private PadButton padButton_special;
	private PadButton padButton_curve;
	private PadButton padButton_compression;
	private PadButton padButton_shift;
	private PadButton padButton_xtalkLevel;
	private PadButton padButton_xtalkGroup;
	private PadButton padButton_threshold;
	private PadButton padButton_gain;
	private PadButton padButton_autoLevel;
	private PadButton padButton_highLevel;
	private PadButton padButton_retrigger;
	private PadButton padButton_dynLevel;
	private PadButton padButton_dynTime;
	private PadButton padButton_minScan;
	private PadButton padButton_type;

	/**
	 * Create the panel.
	 */
	public ControlsPadCommon(boolean pad_type) {
		head_rim_pad = pad_type;
		configPad = new ConfigPad();
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("78px"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("53dlu"),
				ColumnSpec.decode("2dlu"),
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
		
		padButton__name = new PadButton("name",head_rim_pad);
		add(padButton__name, "5, 1, fill, center");
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblNote, "1, 2, right, center");
		
		noteSpinControl_note = new NoteSpinControl();
		noteSpinControl_note.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		noteSpinControl_note.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (checkBox_altLinked.isSelected()) {
					noteSpinControl_altNote.getSpinner().setValue(noteSpinControl_note.getSpinner().getValue());
				}
				if (checkBox_pressrollLinked.isSelected()) {
					noteSpinControl_pressrollNote.getSpinner().setValue(noteSpinControl_note.getSpinner().getValue());
				}
			}
		});
		add(noteSpinControl_note, "3, 2, left, center");
		
		padButton__note = new PadButton("note", head_rim_pad);
		add(padButton__note, "5, 2, default, fill");
		
		
		JLabel lblAltNote = new JLabel("Alt Note");
		lblAltNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblAltNote, "1, 3, right, center");
		
		panel_1 = new JPanel();
		add(panel_1, "3, 3, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("44dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		noteSpinControl_altNote = new NoteSpinControl();
		panel_1.add(noteSpinControl_altNote, "1, 1");
		
		checkBox_altLinked = new JCheckBox("");
		panel_1.add(checkBox_altLinked, "2, 1");
		checkBox_altLinked.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noteSpinControl_altNote.getSpinner().setEnabled(!checkBox_altLinked.isSelected());
			}
		});
		checkBox_altLinked.setToolTipText("Linked to Note");
		
		padButton_altNote = new PadButton("altNote", head_rim_pad);
		add(padButton_altNote, "5, 3");
		
		JLabel lblPressrollNote = new JLabel("Pressroll Note");
		lblPressrollNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblPressrollNote, "1, 4, right, center");
		
		panel_2 = new JPanel();
		add(panel_2, "3, 4, fill, fill");
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("44dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		noteSpinControl_pressrollNote = new NoteSpinControl();
		panel_2.add(noteSpinControl_pressrollNote, "1, 1");
		
		checkBox_pressrollLinked = new JCheckBox("");
		panel_2.add(checkBox_pressrollLinked, "2, 1");
		checkBox_pressrollLinked.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noteSpinControl_pressrollNote.getSpinner().setEnabled(!checkBox_pressrollLinked.isSelected());
			}
		});
		checkBox_pressrollLinked.setToolTipText("Linked to Note");
		
		padButton_pressrollNote = new PadButton("pressrollNote", head_rim_pad);
		add(padButton_pressrollNote, "5, 4");
		
		JLabel lblCurve = new JLabel("Channel");
		lblCurve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCurve, "1, 5, right, center");
		
		spinner_channel = new JSpinner();
		spinner_channel.setModel(new SpinnerNumberModel(1, 1, 16, 1));
		add(spinner_channel, "3, 5, left, center");
		
		padButton_channel = new PadButton("channel", head_rim_pad);
		add(padButton_channel, "5, 5");
		
		JLabel lblSpecialFunction = new JLabel("Special Function");
		lblSpecialFunction.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblSpecialFunction, "1, 6, right, default");
		
		checkBox_special = new JCheckBox("");
		add(checkBox_special, "3, 6");
		
		padButton_special = new PadButton("special", head_rim_pad);
		add(padButton_special, "5, 6");
		
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
		
		padButton_curve = new PadButton("curve", head_rim_pad);
		add(padButton_curve, "5, 7");
				
		JLabel lblCompression = new JLabel("Compression");
		lblCompression.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblCompression, "1, 8, right, center");
		
		comboBox_compression = new JComboBox();
        for(int i=0; i<8; i++){
    		comboBox_compression.addItem(((Integer)i).toString());
        }		
		comboBox_compression.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_compression, "3, 8, fill, center");
		
		padButton_compression = new PadButton("compression", head_rim_pad);
		add(padButton_compression, "5, 8");
		
		JLabel lblShift = new JLabel("Level Shift");
		lblShift.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblShift, "1, 9, right, center");
		
		comboBox_shift = new JComboBox();
        for(int i=0; i<8; i++){
    		comboBox_shift.addItem(((Integer)(i*8)).toString());
        }		
		comboBox_shift.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_shift, "3, 9, fill, center");
		
		padButton_shift = new PadButton("shift", head_rim_pad);
		add(padButton_shift, "5, 9");
		
		JLabel lblXtalkLevel = new JLabel("XTalk Level");
		lblXtalkLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblXtalkLevel, "1, 10, right, center");
		
		comboBox_xtalkLevel = new JComboBox();
        for(int i=0; i<8; i++){
    		comboBox_xtalkLevel.addItem(((Integer)i).toString());
        }		
		comboBox_xtalkLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_xtalkLevel, "3, 10, fill, center");
		
		padButton_xtalkLevel = new PadButton("xtalkLevel", head_rim_pad);
		add(padButton_xtalkLevel, "5, 10");
		
		JLabel lblXtalkGroup = new JLabel("XTalk Group");
		lblXtalkGroup.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblXtalkGroup, "1, 11, right, center");
		
		comboBox_xtalkGroup = new JComboBox();
        for(int i=0; i<8; i++){
    		comboBox_xtalkGroup.addItem(((Integer)i).toString());
        }		
		comboBox_xtalkGroup.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_xtalkGroup, "3, 11, fill, center");
		
		padButton_xtalkGroup = new PadButton("xtalkGroup", head_rim_pad);
		add(padButton_xtalkGroup, "5, 11");
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblThreshold, "1, 12, right, center");
		
		spinner_threshold = new JSpinner();
		spinner_threshold.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 127), new Short((short) 1)));
		add(spinner_threshold, "3, 12, left, center");
		
		padButton_threshold = new PadButton("threshold", head_rim_pad);
		add(padButton_threshold, "5, 12");
		
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
		
		padButton_gain = new PadButton("gain", head_rim_pad);
		add(padButton_gain, "5, 13");
		
		JLabel lblHighlevelAuto = new JLabel("HighLevel Auto");
		lblHighlevelAuto.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblHighlevelAuto, "1, 14, right, center");
		
		checkBox_autoLevel = new JCheckBox("");
		add(checkBox_autoLevel, "3, 14, left, center");
		
		padButton_autoLevel = new PadButton("autoLevel", head_rim_pad);
		add(padButton_autoLevel, "5, 14");
		
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
		
		padButton_highLevel = new PadButton("highLevel", head_rim_pad);
		add(padButton_highLevel, "5, 15");
		
		JLabel lblRetriggerMask = new JLabel("Retrigger Mask");
		lblRetriggerMask.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblRetriggerMask, "1, 16, right, center");
		
		spinner_retrigger = new JSpinner();
		spinner_retrigger.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 127), new Short((short) 1)));
		add(spinner_retrigger, "3, 16, left, center");
		
		padButton_retrigger = new PadButton("retrigger", head_rim_pad);
		add(padButton_retrigger, "5, 16");
		
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
		
		padButton_dynLevel = new PadButton("dynLevel", head_rim_pad);
		add(padButton_dynLevel, "5, 17");
		
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
		
		padButton_dynTime = new PadButton("dynTime", head_rim_pad);
		add(padButton_dynTime, "5, 18");
		
		JLabel lblMinscan = new JLabel("MinScan");
		lblMinscan.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMinscan, "1, 19, right, center");
		
		spinner_minScan = new JSpinner();
		spinner_minScan.setModel(new SpinnerNumberModel(new Short((short) 10), new Short((short) 10), new Short((short) 100), new Short((short) 1)));
		add(spinner_minScan, "3, 19, left, center");
		
		padButton_minScan = new PadButton("minScan", head_rim_pad);
		add(padButton_minScan, "5, 19");
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblType, "1, 20, right, center");
		
		comboBox_type = new JComboBox();
		comboBox_type.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(comboBox_type, "3, 20, fill, center");
		
		padButton_type = new PadButton("type", head_rim_pad);
		add(padButton_type, "5, 20");
				
	}

	public void updateControls() {
		comboBox_name.setSelectedIndex(configPad.name);
		noteSpinControl_note.getSpinner().setValue(configPad.note);
		System.out.printf("Setting Note Spinner to %d\n", configPad.note);
		noteSpinControl_altNote.getSpinner().setValue(configPad.altNote);
		checkBox_altLinked.setSelected(configPad.altNote_linked);
		noteSpinControl_pressrollNote.getSpinner().setValue(configPad.pressrollNote);
		checkBox_pressrollLinked.setSelected(configPad.pressrollNote_linked);
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
			comboBox_type.addItem("Single Piezo");
			comboBox_type.addItem("Dual or 3way Yamaha");
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
	
	public void updateConfig() {
		configPad.name = (short)comboBox_name.getSelectedIndex();
		configPad.note = ((Short)noteSpinControl_note.getSpinner().getValue()).shortValue();
		System.out.printf("Setting Note to %d\n", configPad.note);
		configPad.altNote = ((Short)noteSpinControl_altNote.getSpinner().getValue()).shortValue();
		configPad.altNote_linked = checkBox_altLinked.isSelected();
		configPad.pressrollNote = ((Short)noteSpinControl_pressrollNote.getSpinner().getValue()).shortValue();
		configPad.pressrollNote_linked = checkBox_pressrollLinked.isSelected();
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
		configPad = config;
		updateControls();
		comboBox_name.insertItemAt(Constants.PADS_NAMES_LIST[pad_id], 0);
		comboBox_name.removeItemAt(1);
	}
	
	public boolean getHeadRim() {
		return head_rim_pad;
	}
	
	public JComboBox getComboBox_type() {
		return comboBox_type;
	}
	public JComboBox getComboBox_name() {
		return comboBox_name;
	}
	public JButton getPadButton_name() {
		return padButton__name;
	}
	public JButton getPadButton_note() {
		return padButton__note;
	}
	public PadButton getPadButton_altNote() {
		return padButton_altNote;
	}
	public PadButton getPadButton_pressrollNote() {
		return padButton_pressrollNote;
	}
	public PadButton getPadButton_channel() {
		return padButton_channel;
	}
	public PadButton getPadButton_special() {
		return padButton_special;
	}
	public PadButton getPadButton_curve() {
		return padButton_curve;
	}
	public PadButton getPadButton_compression() {
		return padButton_compression;
	}
	public PadButton getPadButton_shift() {
		return padButton_shift;
	}
	public PadButton getPadButton_xtalkLevel() {
		return padButton_xtalkLevel;
	}
	public PadButton getPadButton_xtalkGroup() {
		return padButton_xtalkGroup;
	}
	public PadButton getPadButton_threshold() {
		return padButton_threshold;
	}
	public PadButton getPadButton_gain() {
		return padButton_gain;
	}
	public PadButton getPadButton_autoLevel() {
		return padButton_autoLevel;
	}
	public PadButton getPadButton_highLevel() {
		return padButton_highLevel;
	}
	public PadButton getPadButton_retrigger() {
		return padButton_retrigger;
	}
	public PadButton getPadButton_dynLevel() {
		return padButton_dynLevel;
	}
	public PadButton getPadButton_dynTime() {
		return padButton_dynTime;
	}
	public PadButton getPadButton_minScan() {
		return padButton_minScan;
	}
	public PadButton getPadButton_type() {
		return padButton_type;
	}
}
