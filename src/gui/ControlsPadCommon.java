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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import com.jgoodies.forms.layout.Sizes;

class PadButton extends JButton {
	private String name;

	/**
	 * Create the panel.
	 */
	public PadButton(String text, boolean pad_type) {
		name = text;
		setIcon(new ImageIcon(ControlsPadCommon.class.getResource("/icons12x12/46.png")));
		if (pad_type) {
			setToolTipText("Copy this input setting to All inputs");
		} else {
			setToolTipText("Copy head/rim setting to All pads");
		}
	}

	public String getName() {
		return name;
	}
}

class ComboBoxCustom extends JComboBox {

	public ComboBoxCustom () {
		this.setMaximumRowCount(30);
		this.setFont(new Font("Segoe UI", Font.PLAIN, 10));
	}
}

class LabelCustom extends JLabel {

	public LabelCustom (String name) {
		this.setText(name);
		this.setFont(new Font("Segoe UI", Font.PLAIN, 10));
	}
}

public class ControlsPadCommon extends JPanel {
	private Boolean changeEventsAllowed = false;
	
	private ComboBoxCustom comboBox_name;
	private NoteSpinControl noteSpinControl_note;
	private NoteSpinControl noteSpinControl_altNote;
	private NoteSpinControl noteSpinControl_pressrollNote;
	private JSpinner spinner_channel;
	private JCheckBox checkBox_special;
	private ComboBoxCustom comboBox_curve;
	private ComboBoxCustom comboBox_compression;
	private ComboBoxCustom comboBox_shift;
	private ComboBoxCustom comboBox_xtalkLevel;
	private ComboBoxCustom comboBox_xtalkGroup;
	private JSpinner spinner_threshold;
	private ComboBoxCustom comboBox_gain;
	private JCheckBox checkBox_autoLevel;
	private JSpinner spinner_highLevel;
	private JSpinner spinner_retrigger;
	private ComboBoxCustom comboBox_dynLevel;
	private ComboBoxCustom comboBox_dynTime;
	private JSpinner spinner_minScan;
	private ComboBoxCustom comboBox_type;
	private boolean head_rim_pad;
	private static final boolean head_pad = true;
	private static final boolean rim_pad = false;
	
	private ConfigFull configFull;
	private int	configIndex;
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
	//private String [] customNamesList;
	//private int customNamesCount;
	public String pressedPadButtonName;
	
	
	/**
	 * Create the panel.
	 */
	public ControlsPadCommon(boolean pad_type, ConfigFull config) {
		head_rim_pad = pad_type;
		configFull = config;
		if (pad_type == head_pad)
		{
			configIndex = 0;
		} else {
			configIndex = 1;
		}
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				new ColumnSpec(ColumnSpec.FILL, Sizes.bounded(Sizes.PREFERRED, Sizes.constant("12dlu", true), Sizes.constant("12dlu", true)), 0),},
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
		
		LabelCustom lblName = new LabelCustom("Name");
		lblName.setToolTipText("Input Name");
		add(lblName, "1, 1, right, center");
		
//		customNamesCount = Constants.CUSTOM_NAMES_MAX;
//		customNamesList = new String[Constants.CUSTOM_NAMES_MAX];
//        for(Integer i=0; i<Constants.CUSTOM_NAMES_MAX; i++){
//        	customNamesList[i] = "Custom" + i.toString();
//        }
		comboBox_name = new ComboBoxCustom();
		comboBox_name.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					firePropertyChange("nameChanged", false, true);
				}
			}
		});
		updatePadsNames();
		add(comboBox_name, "3, 1, fill, center");
		
		padButton__name = new PadButton("name",head_rim_pad);
		add(padButton__name, "5, 1");
		
		LabelCustom lblNote = new LabelCustom("Note");
		add(lblNote, "1, 2, right, center");
		
		noteSpinControl_note = new NoteSpinControl(configFull);
		noteSpinControl_note.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (noteSpinControl_altNote.getCheckBox().isSelected()) {
					noteSpinControl_altNote.setValue(noteSpinControl_note.getValue());
				}
				if (noteSpinControl_pressrollNote.getCheckBox().isSelected()) {
					noteSpinControl_pressrollNote.setValue(noteSpinControl_note.getValue());
				}
			}
		});
		add(noteSpinControl_note, "3, 2, left, center");
		
		padButton__note = new PadButton("note", head_rim_pad);
		add(padButton__note, "5, 2");
		
		
		LabelCustom lblAltNote = new LabelCustom("Alt Note");
		add(lblAltNote, "1, 3, right, center");
		
		noteSpinControl_altNote = new NoteSpinControl(configFull);
		add(noteSpinControl_altNote, "3, 3");
		noteSpinControl_altNote.getCheckBox().setVisible(true);
		noteSpinControl_altNote.getCheckBox().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noteSpinControl_altNote.getSpinner().setEnabled(!noteSpinControl_altNote.getCheckBox().isSelected());
				if (noteSpinControl_altNote.getCheckBox().isSelected()) {
					noteSpinControl_altNote.setValue(noteSpinControl_note.getValue());
				}
			}
		});
		noteSpinControl_altNote.getCheckBox().setToolTipText("Linked to Note");
		
		padButton_altNote = new PadButton("altNote", head_rim_pad);
		add(padButton_altNote, "5, 3");
		
		LabelCustom lblPressrollNote = new LabelCustom("Pressroll Note");
		add(lblPressrollNote, "1, 4, right, center");
		
		noteSpinControl_pressrollNote = new NoteSpinControl(configFull);
		add(noteSpinControl_pressrollNote, "3, 4");
		noteSpinControl_pressrollNote.getCheckBox().setVisible(true);
		noteSpinControl_pressrollNote.getCheckBox().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noteSpinControl_pressrollNote.getSpinner().setEnabled(!noteSpinControl_pressrollNote.getCheckBox().isSelected());
				if (noteSpinControl_pressrollNote.getCheckBox().isSelected()) {
					noteSpinControl_pressrollNote.setValue(noteSpinControl_note.getValue());
				}
			}
		});
		noteSpinControl_pressrollNote.getCheckBox().setToolTipText("Linked to Note");
		
		padButton_pressrollNote = new PadButton("pressrollNote", head_rim_pad);
		add(padButton_pressrollNote, "5, 4");
		
		LabelCustom lblCurve = new LabelCustom("Channel");
		add(lblCurve, "1, 5, right, center");
		
		spinner_channel = new JSpinner();
		spinner_channel.setModel(new SpinnerNumberModel(1, 1, 16, 1));
		add(spinner_channel, "3, 5, left, center");
		
		padButton_channel = new PadButton("channel", head_rim_pad);
		add(padButton_channel, "5, 5");
		
		LabelCustom lblSpecialFunction = new LabelCustom("Special Function");
		add(lblSpecialFunction, "1, 6, right, default");
		
		checkBox_special = new JCheckBox("");
		add(checkBox_special, "3, 6");
		
		padButton_special = new PadButton("special", head_rim_pad);
		add(padButton_special, "5, 6");
		
		LabelCustom lblCurve_1 = new LabelCustom("Curve");
		add(lblCurve_1, "1, 7, right, center");
		
		comboBox_curve = new ComboBoxCustom();
		for (String string : Constants.CURVES_LIST) {
			comboBox_curve.addItem(string);
			}
		add(comboBox_curve, "3, 7, fill, center");
		
		padButton_curve = new PadButton("curve", head_rim_pad);
		add(padButton_curve, "5, 7");
				
		LabelCustom lblCompression = new LabelCustom("Compression");
		add(lblCompression, "1, 8, right, center");
		
		comboBox_compression = new ComboBoxCustom();
        for(int i=0; i<8; i++){
    		comboBox_compression.addItem(((Integer)i).toString());
        }		
		add(comboBox_compression, "3, 8, fill, center");
		
		padButton_compression = new PadButton("compression", head_rim_pad);
		add(padButton_compression, "5, 8");
		
		LabelCustom lblShift = new LabelCustom("Level Shift");
		add(lblShift, "1, 9, right, center");
		
		comboBox_shift = new ComboBoxCustom();
        for(int i=0; i<8; i++){
    		comboBox_shift.addItem(((Integer)(i*8)).toString());
        }		
		add(comboBox_shift, "3, 9, fill, center");
		
		padButton_shift = new PadButton("shift", head_rim_pad);
		add(padButton_shift, "5, 9");
		
		LabelCustom lblXtalkLevel = new LabelCustom("XTalk Level");
		add(lblXtalkLevel, "1, 10, right, center");
		
		comboBox_xtalkLevel = new ComboBoxCustom();
        for(int i=0; i<8; i++){
    		comboBox_xtalkLevel.addItem(((Integer)i).toString());
        }		
		add(comboBox_xtalkLevel, "3, 10, fill, center");
		
		padButton_xtalkLevel = new PadButton("xtalkLevel", head_rim_pad);
		add(padButton_xtalkLevel, "5, 10");
		
		LabelCustom lblXtalkGroup = new LabelCustom("XTalk Group");
		add(lblXtalkGroup, "1, 11, right, center");
		
		comboBox_xtalkGroup = new ComboBoxCustom();
        for(int i=0; i<8; i++){
    		comboBox_xtalkGroup.addItem(((Integer)i).toString());
        }		
		add(comboBox_xtalkGroup, "3, 11, fill, center");
		
		padButton_xtalkGroup = new PadButton("xtalkGroup", head_rim_pad);
		add(padButton_xtalkGroup, "5, 11");
		
		LabelCustom lblThreshold = new LabelCustom("Threshold");
		add(lblThreshold, "1, 12, right, center");
		
		spinner_threshold = new JSpinner();
		spinner_threshold.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 127), new Short((short) 1)));
		add(spinner_threshold, "3, 12, left, center");
		
		padButton_threshold = new PadButton("threshold", head_rim_pad);
		add(padButton_threshold, "5, 12");
		
		LabelCustom lblGain = new LabelCustom("Gain");
		add(lblGain, "1, 13, right, center");
		
		comboBox_gain = new ComboBoxCustom();
        for(int i=0; i<9; i++){
    		comboBox_gain.addItem(((Integer)i).toString());
        }		
		add(comboBox_gain, "3, 13, fill, center");
		
		padButton_gain = new PadButton("gain", head_rim_pad);
		add(padButton_gain, "5, 13");
		
		LabelCustom lblHighlevelAuto = new LabelCustom("HighLevel Auto");
		add(lblHighlevelAuto, "1, 14, right, center");
		
		checkBox_autoLevel = new JCheckBox("");		
		add(checkBox_autoLevel, "3, 14, left, center");
		
		padButton_autoLevel = new PadButton("autoLevel", head_rim_pad);
		add(padButton_autoLevel, "5, 14");
		
		LabelCustom lblHighlevel = new LabelCustom("HighLevel");
		add(lblHighlevel, "1, 15, right, center");
				
		spinner_highLevel = new JSpinner();
		spinner_highLevel.setModel(new SpinnerNumberModel(new Short((short) 64), new Short((short) 64), new Short((short) 1023), new Short((short) 1)));
		JSpinner.NumberEditor jsne_spinner_highLevel = new JSpinner.NumberEditor(spinner_highLevel,"#");
		spinner_highLevel.setEditor(jsne_spinner_highLevel);
		add(spinner_highLevel, "3, 15, left, default");
		
		padButton_highLevel = new PadButton("highLevel", head_rim_pad);
		add(padButton_highLevel, "5, 15");
		
		LabelCustom lblRetriggerMask = new LabelCustom("Retrigger Mask");
		add(lblRetriggerMask, "1, 16, right, center");
		
		spinner_retrigger = new JSpinner();
		spinner_retrigger.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 127), new Short((short) 1)));
		add(spinner_retrigger, "3, 16, left, center");
		
		padButton_retrigger = new PadButton("retrigger", head_rim_pad);
		add(padButton_retrigger, "5, 16");
		
		LabelCustom lblDyn = new LabelCustom(" DynLevel");
		lblDyn.setToolTipText("Dynamic Threshold Level");
		add(lblDyn, "1, 17, right, center");
		
		comboBox_dynLevel = new ComboBoxCustom();
        for(int i=0; i<16; i++){
    		comboBox_dynLevel.addItem(((Integer)i).toString());
        }		
		add(comboBox_dynLevel, "3, 17, fill, center");
		
		padButton_dynLevel = new PadButton("dynLevel", head_rim_pad);
		add(padButton_dynLevel, "5, 17");
		
		LabelCustom lblDyntime = new LabelCustom("DynTime");
		lblDyntime.setToolTipText("Dynamic Threshold decay time");
		add(lblDyntime, "1, 18, right, center");
		
		comboBox_dynTime = new ComboBoxCustom();
        for(int i=0; i<16; i++){
    		comboBox_dynTime.addItem(((Integer)(i*4)).toString());
        }		
		add(comboBox_dynTime, "3, 18, fill, center");
		
		padButton_dynTime = new PadButton("dynTime", head_rim_pad);
		add(padButton_dynTime, "5, 18");
		
		LabelCustom lblMinscan = new LabelCustom("MinScan");
		add(lblMinscan, "1, 19, right, center");
		
		spinner_minScan = new JSpinner();
		spinner_minScan.setModel(new SpinnerNumberModel(new Short((short) 10), new Short((short) 10), new Short((short) 100), new Short((short) 1)));
		add(spinner_minScan, "3, 19, left, center");
		
		padButton_minScan = new PadButton("minScan", head_rim_pad);
		add(padButton_minScan, "5, 19");
		
		LabelCustom lblType = new LabelCustom("Type");
		add(lblType, "1, 20, right, center");
		
		comboBox_type = new ComboBoxCustom();
		comboBox_type.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					firePropertyChange("typeChanged", false, true);
				}
			}
		});
		add(comboBox_type, "3, 20, fill, center");
		
		padButton_type = new PadButton("type", head_rim_pad);
		add(padButton_type, "5, 20");
		
		for (Object control: this.getComponents()) {
			//System.out.printf("Component -> %s\n",control.getClass().toString());
			if (control.getClass().equals(PadButton.class)) {
				((PadButton)control).addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						pressedPadButtonName = ((PadButton)arg0.getSource()).getName();
						firePropertyChange("copyButton", false, true);
					}
				});
			}
			//if (control.getClass().equals (JComboBox.class)) {
			if (control instanceof JComboBox) {
				((JComboBox) control).addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						if (arg0.getStateChange() == ItemEvent.SELECTED) {
							valueChanged();
						}
					}
				});
				
			}
			if (control.getClass().equals(JCheckBox.class)) {
				((JCheckBox) control).addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent arg0) {
						valueChanged();
					}
				});
				
			}
			if (control.getClass().equals (NoteSpinControl.class)) {
				((NoteSpinControl) control).getSpinner().addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						valueChanged();
					}
				});				
			}
			if (control.getClass().equals(JSpinner.class)) {
				((JSpinner) control).setFont(new Font("Tahoma", Font.PLAIN, 11));
				((JSpinner) control).addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						valueChanged();
					}
				});				
			}
		}
				
	}
	
	private void valueChanged() {
		if (changeEventsAllowed) {
			updateConfig();
			firePropertyChange("valueChanged", false, true);
		}
	}

	public void updateControls() {
		changeEventsAllowed = false;

		comboBox_name.setSelectedIndex(configFull.configPads[configIndex].name);
		noteSpinControl_note.setValue(configFull.configPads[configIndex].note);
		noteSpinControl_altNote.setValue(configFull.configPads[configIndex].altNote);
		noteSpinControl_altNote.getCheckBox().setSelected(configFull.configPads[configIndex].altNote_linked);
		noteSpinControl_pressrollNote.setValue(configFull.configPads[configIndex].pressrollNote);
		noteSpinControl_pressrollNote.getCheckBox().setSelected(configFull.configPads[configIndex].pressrollNote_linked);
		spinner_channel.setValue(configFull.configPads[configIndex].channel + 1);
		checkBox_special.setSelected(configFull.configPads[configIndex].special);
		comboBox_curve.setSelectedIndex(configFull.configPads[configIndex].curve);
		comboBox_compression.setSelectedIndex(configFull.configPads[configIndex].compression);
		comboBox_shift.setSelectedIndex(configFull.configPads[configIndex].shift);
		comboBox_xtalkLevel.setSelectedIndex(configFull.configPads[configIndex].xtalkLevel);
		comboBox_xtalkGroup.setSelectedIndex(configFull.configPads[configIndex].xtalkGroup);
		spinner_threshold.setValue(configFull.configPads[configIndex].threshold);
		comboBox_gain.setSelectedIndex(configFull.configPads[configIndex].gain);
		checkBox_autoLevel.setSelected(configFull.configPads[configIndex].autoLevel);
		spinner_highLevel.setValue(configFull.configPads[configIndex].levelMax);
		spinner_retrigger.setValue(configFull.configPads[configIndex].retrigger);
		comboBox_dynLevel.setSelectedIndex(configFull.configPads[configIndex].dynLevel);
		comboBox_dynTime.setSelectedIndex(configFull.configPads[configIndex].dynTime);
		spinner_minScan.setValue(configFull.configPads[configIndex].minScan);
		comboBox_type.removeAllItems();
		if (head_rim_pad == head_pad) {
			comboBox_type.addItem("Single Piezo");
			comboBox_type.addItem("Dual or 3way Yamaha");
			comboBox_type.addItem("3way Roland");
			if (configFull.configPads[configIndex].dual) {
				comboBox_type.setSelectedIndex(1);
			}
			if (configFull.configPads[configIndex].threeWay) {
				comboBox_type.setSelectedIndex(2);
			}			
		} else {
			comboBox_type.addItem("Piezo");
			comboBox_type.addItem("Switch");
			if (configFull.configPads[configIndex].type) {
				comboBox_type.setSelectedIndex(1);
			}
		}
		changeEventsAllowed = true;
	}
	
	public void updateConfig() {
		configFull.configPads[configIndex].name = (short)comboBox_name.getSelectedIndex();
		configFull.configPads[configIndex].note = ((Short)noteSpinControl_note.getValue()).shortValue();
		configFull.configPads[configIndex].altNote = ((Short)noteSpinControl_altNote.getValue()).shortValue();
		configFull.configPads[configIndex].altNote_linked = noteSpinControl_altNote.getCheckBox().isSelected();
		configFull.configPads[configIndex].pressrollNote = ((Short)noteSpinControl_pressrollNote.getValue()).shortValue();
		configFull.configPads[configIndex].pressrollNote_linked = noteSpinControl_pressrollNote.getCheckBox().isSelected();
		configFull.configPads[configIndex].channel = (short)(((Integer)spinner_channel.getValue()).shortValue() - 1);
		configFull.configPads[configIndex].special = checkBox_special.isSelected();
		configFull.configPads[configIndex].curve = (short)comboBox_curve.getSelectedIndex();
		configFull.configPads[configIndex].compression = (short)comboBox_compression.getSelectedIndex();
		configFull.configPads[configIndex].shift = (short)comboBox_shift.getSelectedIndex();
		configFull.configPads[configIndex].xtalkLevel = (short)comboBox_xtalkLevel.getSelectedIndex();
		configFull.configPads[configIndex].xtalkGroup = (short)comboBox_xtalkGroup.getSelectedIndex();
		configFull.configPads[configIndex].threshold = (Short)spinner_threshold.getValue();
		configFull.configPads[configIndex].gain = (short)comboBox_gain.getSelectedIndex();
		configFull.configPads[configIndex].autoLevel = checkBox_autoLevel.isSelected();
		configFull.configPads[configIndex].levelMax = (Short)spinner_highLevel.getValue();
		configFull.configPads[configIndex].retrigger = (Short)spinner_retrigger.getValue();
		configFull.configPads[configIndex].dynLevel = (short)comboBox_dynLevel.getSelectedIndex();
		configFull.configPads[configIndex].dynTime = (short)comboBox_dynTime.getSelectedIndex();
		configFull.configPads[configIndex].minScan = (Short)spinner_minScan.getValue();
		if (head_rim_pad == head_pad) {
			configFull.configPads[configIndex].type = false;
			switch (comboBox_type.getSelectedIndex()) {
			case 1:
				configFull.configPads[configIndex].dual = true;
				configFull.configPads[configIndex].threeWay = false;
				break;
			case 2:
				configFull.configPads[configIndex].dual = false;
				configFull.configPads[configIndex].threeWay = true;
				break;
			default:
				configFull.configPads[configIndex].dual = false;
				configFull.configPads[configIndex].threeWay = false;
				break;
			}
		} else {
			if (comboBox_type.getSelectedIndex() == 0) {
				configFull.configPads[configIndex].type = false;
			} else {
				configFull.configPads[configIndex].type = true;
			}
		}
	}
	
	public void setConfigIndex(boolean pad_type, int pad_id) {
		int index;
		
		configIndex = pad_id;
		index = configFull.configPads[configIndex].name;
		head_rim_pad = pad_type;
		updateControls();
		changeEventsAllowed = false;
		updatePadsNames();
		//comboBox_name.insertItemAt(Constants.PADS_NAMES_LIST[configIndex], 0);
		//comboBox_name.removeItemAt(1);
		//comboBox_name.setSelectedIndex(index);
		changeEventsAllowed = true;
	}
	
	public void updatePadsNames() {
		comboBox_name.removeAllItems();
		//comboBox_name.addItem("");
		comboBox_name.addItem(Constants.PADS_NAMES_LIST[configIndex]);
		for (String string : Constants.CUSTOM_PADS_NAMES_LIST) {
			comboBox_name.addItem(string);
			}
		for (int i = 0; i < configFull.customNamesCount; i++) {
			comboBox_name.addItem(configFull.configCustomNames[i].name);
		}
		comboBox_name.setSelectedIndex(configFull.configPads[configIndex].name);			
	}
	
	public boolean getHeadRim() {
		return head_rim_pad;
	}
	
	public JComboBox getComboBox_type() {
		return comboBox_type;
	}

}
