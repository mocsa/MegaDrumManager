package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.BitSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Insets;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Dimension;

public class ControlsPads extends JPanel {
	private JButton btnGet;
	private JButton btnSend;
	private JComboBox comboBox_padSelection;
	private ControlsPadCommon panel_head;
	private ControlsPadCommon panel_rim;
	private ThirdZoneControls panel_3rd_zone;
	
	public ConfigPad [] configPads;
	public Config3rd [] config3rds;
	private int padPointer;
	private int prevPadPointer;
	private int thirdPointer;
	private int prevThirdPointer;
	private JButton btnFirst;
	private JButton btnPrev;
	private JButton btnNext;
	private JButton btnLast;
	private int padSelection;
	private int prevPadSelection;
	private ActionListener padButtonActionListener;
	private boolean switchingPad = false;
	private boolean panel_3rd_zone_prevVisible = false;

	//private boolean head_pad_type;
	private static final boolean head_pad = true;
	private static final boolean rim_pad = false;
	private JButton btnGetall;
	private JButton btnSendall;
	private JButton btnCopyRim;
	private JButton btnCopyrd;
	private JButton btnLoad;
	private JButton btnSave;
	private JPanel panelCopyPad;
	private JMenu menu;
	private JMenuBar menuBar;
	private JMenu mnCopypadto;

	/**
	 * Create the panel.
	 */
	public ControlsPads() {
		configPads = new ConfigPad[Constants.PADS_COUNT];
        for(int i=0; i<Constants.PADS_COUNT; i++){
        	configPads[i] = new ConfigPad();
        }
        padPointer = 0;
        prevPadPointer = -1;

		config3rds = new Config3rd[(Constants.PADS_COUNT - 1)/2];
        for(int i=0; i<((Constants.PADS_COUNT - 1)/2); i++){
        	config3rds[i] = new Config3rd();
        }
        thirdPointer = 0;
        prevThirdPointer = -1;
        
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				RowSpec.decode("1dlu"),
				FormFactory.PREF_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.PREF_ROWSPEC,}));
		
		JPanel panel_buttons = new JPanel();
		add(panel_buttons, "1, 1, fill, fill");
		panel_buttons.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("18dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("18dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("18dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("18dlu"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("18dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("18dlu"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("25dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("25dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("25dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("25dlu"),
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("12dlu:grow"),}));
		
		btnGet = new JButton("Get");
		btnGet.setMargin(new Insets(1, 2, 1, 2));
		btnGet.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnGet, "1, 1, fill, fill");
		
		btnSend = new JButton("Send");
		btnSend.setMargin(new Insets(1, 2, 1, 2));
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnSend, "3, 1, fill, fill");
		
		btnGetall = new JButton("GetAll");
		btnGetall.setMargin(new Insets(1, 0, 1, 0));
		btnGetall.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnGetall, "5, 1, fill, fill");
		
		btnSendall = new JButton("SendAll");
		btnSendall.setMargin(new Insets(1, 0, 1, 0));
		btnSendall.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnSendall, "7, 1, fill, fill");
		
		btnLoad = new JButton("Load");
		btnLoad.setMargin(new Insets(1, 2, 1, 2));
		btnLoad.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnLoad, "9, 1");
		
		btnSave = new JButton("Save");
		btnSave.setMargin(new Insets(1, 2, 1, 2));
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnSave, "11, 1");
		
		panelCopyPad = new JPanel();
		panelCopyPad.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel_buttons.add(panelCopyPad, "13, 1, fill, fill");
		panelCopyPad.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("fill:pref"),}));
		
		menuBar = new JMenuBar();
		panelCopyPad.add(menuBar, "1, 1, left, top");
		
		mnCopypadto = new JMenu("CopyPad");
		mnCopypadto.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		menuBar.add(mnCopypadto);
		
		btnCopyRim = new JButton("CopyRim");
		btnCopyRim.setMargin(new Insets(1, 0, 1, 0));
		btnCopyRim.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnCopyRim, "17, 1");
		
		btnCopyrd = new JButton("Copy3rd");
		btnCopyrd.setMargin(new Insets(1, 0, 1, 0));
		btnCopyrd.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnCopyrd, "19, 1");
		
		JPanel panel_input_selection = new JPanel();
		add(panel_input_selection, "1, 3, fill, fill");
		panel_input_selection.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("86dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("12dlu"),}));
		
		JLabel label = new JLabel("Input");
		label.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel_input_selection.add(label, "2, 1, right, default");
		
		comboBox_padSelection = new JComboBox();
		updateInputCountsControls(Constants.PADS_COUNT);
        comboBox_padSelection.setSelectedIndex(0); 
		comboBox_padSelection.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
					padSelection = comboBox_padSelection.getSelectedIndex();
					//if (padSelection != prevPadSelection) {
					if (!switchingPad) {
						switchingPad = true;
						prevPadSelection = padSelection;						
						if (padSelection > 0 ) {
							padSelection = ((padSelection - 1)*2) + 1;
							switch_to_pad(padSelection);
						} else {
							switch_to_pad(0);
						}
						switchingPad = false;
					}
					//}
		        }
			}
		});
		comboBox_padSelection.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_input_selection.add(comboBox_padSelection, "4, 1, fill, fill");
		
		btnFirst = new JButton("first");
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBox_padSelection.setSelectedIndex(0);
			}
		});
		btnFirst.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_input_selection.add(btnFirst, "6, 1");
		
		btnPrev = new JButton("prev");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBox_padSelection.setSelectedIndex((comboBox_padSelection.getSelectedIndex()>0)?(comboBox_padSelection.getSelectedIndex() - 1):0);
			}
		});
		btnPrev.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_input_selection.add(btnPrev, "8, 1");
		
		btnNext = new JButton("next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBox_padSelection.setSelectedIndex((comboBox_padSelection.getSelectedIndex()<(comboBox_padSelection.getItemCount()-1))?(comboBox_padSelection.getSelectedIndex() + 1):(comboBox_padSelection.getItemCount() - 1));
			}
		});
		btnNext.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_input_selection.add(btnNext, "10, 1, default, top");
		
		btnLast = new JButton("last");
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBox_padSelection.setSelectedIndex(comboBox_padSelection.getItemCount() - 1);
			}
		});
		btnLast.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_input_selection.add(btnLast, "12, 1");
		
		JPanel panel_head_rim = new JPanel();
		add(panel_head_rim, "1, 4, fill, fill");
		panel_head_rim.setLayout(new GridLayout(1, 2, 0, 0));
		
		padButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String varName = ((PadButton)arg0.getSource()).getName();
				boolean head_rim = ((ControlsPadCommon)((PadButton)arg0.getSource()).getParent()).getHeadRim();
				copyPadVarToAll(varName, head_rim);
			}
		};
		panel_head = new ControlsPadCommon(head_pad);
		panel_head.addPropertyChangeListener( new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getPropertyName().equals("nameChanged")) {
					if (!switchingPad) {
						int index;
						index = comboBox_padSelection.getSelectedIndex();
						//System.out.printf("Renaming head at padPointer = %d\n", padPointer + 1);
						//configPads[padPointer].copyVarsFrom(panel_head.getConfig());
						panel_head.updateConfig();
						updatePadsSelection(padPointer);
						comboBox_padSelection.setSelectedIndex(index);
					}
				}
				if (arg0.getPropertyName().equals("typeChanged")) {
		        	if ((panel_head.getComboBox_type().getSelectedIndex() > 0) != panel_3rd_zone_prevVisible) {
		        		panel_3rd_zone_prevVisible = (panel_head.getComboBox_type().getSelectedIndex() > 0);
		        		panel_3rd_zone.setVisible(panel_3rd_zone_prevVisible);
		        		panel_rim.getComboBox_type().setEnabled(panel_3rd_zone_prevVisible);
		        		firePropertyChange("resize", false, true);
		        	}					
				}
				if (arg0.getPropertyName().equals("valueChanged")) {
					firePropertyChange("headValueChanged", false, true);
				}
			}
		});
		
		panel_head.getPadButton_name().addActionListener(padButtonActionListener);
		panel_head.getPadButton_note().addActionListener(padButtonActionListener);
		panel_head.getPadButton_altNote().addActionListener(padButtonActionListener);
		panel_head.getPadButton_pressrollNote().addActionListener(padButtonActionListener);
		panel_head.getPadButton_channel().addActionListener(padButtonActionListener);
		panel_head.getPadButton_special().addActionListener(padButtonActionListener);
		panel_head.getPadButton_curve().addActionListener(padButtonActionListener);
		panel_head.getPadButton_compression().addActionListener(padButtonActionListener);
		panel_head.getPadButton_shift().addActionListener(padButtonActionListener);
		panel_head.getPadButton_xtalkLevel().addActionListener(padButtonActionListener);
		panel_head.getPadButton_xtalkGroup().addActionListener(padButtonActionListener);
		panel_head.getPadButton_threshold().addActionListener(padButtonActionListener);
		panel_head.getPadButton_gain().addActionListener(padButtonActionListener);
		panel_head.getPadButton_autoLevel().addActionListener(padButtonActionListener);
		panel_head.getPadButton_highLevel().addActionListener(padButtonActionListener);
		panel_head.getPadButton_retrigger().addActionListener(padButtonActionListener);
		panel_head.getPadButton_dynLevel().addActionListener(padButtonActionListener);
		panel_head.getPadButton_dynTime().addActionListener(padButtonActionListener);
		panel_head.getPadButton_minScan().addActionListener(padButtonActionListener);
		panel_head.getPadButton_type().addActionListener(padButtonActionListener);

		panel_head_rim.add(panel_head);
		panel_head.setBorder(new TitledBorder(null, "Head/Bow", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel_rim = new ControlsPadCommon(rim_pad);
		panel_rim.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getPropertyName().equals("nameChanged")) {
					if (!switchingPad) {
						if (padPointer > 0 ) {
							int index;
							index = comboBox_padSelection.getSelectedIndex();
							//System.out.printf("Renaming rim at padPointer = %d\n", padPointer + 1);
							//configPads[padPointer+1].copyVarsFrom(panel_rim.getConfig());
							panel_rim.updateConfig();
							updatePadsSelection(padPointer + 1);
							comboBox_padSelection.setSelectedIndex(index);
						}
					}
				}
				if (arg0.getPropertyName().equals("typeChanged")) {
					panel_3rd_zone.setAsSwitch(panel_rim.getComboBox_type().getSelectedIndex() > 0);
				}
				if (arg0.getPropertyName().equals("valueChanged")) {
					firePropertyChange("rimValueChanged", false, true);
				}
			}
		});
		
		panel_rim.getPadButton_name().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_note().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_altNote().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_pressrollNote().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_channel().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_special().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_curve().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_compression().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_shift().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_xtalkLevel().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_xtalkGroup().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_threshold().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_gain().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_autoLevel().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_highLevel().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_retrigger().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_dynLevel().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_dynTime().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_minScan().addActionListener(padButtonActionListener);
		panel_rim.getPadButton_type().addActionListener(padButtonActionListener);
		
		panel_rim.setVisible(false);
		panel_head_rim.add(panel_rim);
		panel_rim.setBorder(new TitledBorder(null, "Rim/Edge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel_3rd_zone = new ThirdZoneControls();
		panel_3rd_zone.addPropertyChangeListener( new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getPropertyName().equals("valueChanged")) {
					firePropertyChange("thirdZoneValueChanged", false, true);
				}
			}
		});
		panel_3rd_zone.setVisible(false);
		add(panel_3rd_zone, "1, 5, fill, fill");
		panel_3rd_zone.setBorder(new TitledBorder(null, "3rd Zone", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panel_head.setConfig(configPads[0], head_pad, 0);
		comboBox_padSelection.setSelectedIndex(0);

		
	}
	
	public JButton getBtnGet() {
		return btnGet;
	}

	public JButton getBtnSend() {
		return btnSend;
	}
	
	public int getPadPointer() {
		return padPointer;
	}
	
	public void setPadPointer(int pointer) {
		padPointer = pointer;
	}
	
	public ConfigPad getConfig(int pad_id) {
		if (pad_id > 0) {
			if (pad_id == padPointer) {
				//configPads[pad_id].copyVarsFrom(panel_head.getConfig());
				panel_head.updateConfig();
			}
			if (pad_id == (padPointer + 1)) {
				//configPads[pad_id].copyVarsFrom(panel_rim.getConfig());
				panel_rim.updateConfig();
			}
		} else {
			if (padPointer == 0) {
				//configPads[pad_id].copyVarsFrom(panel_head.getConfig());
				panel_head.updateConfig();
			}
		}		
		return configPads[pad_id];
	}

	public ConfigPad [] getConfigs() {
		return configPads;
	}
	
	public void setConfig(byte[] buffer, int pad_id) {
		Utils.copySysexToConfigPad(buffer, configPads[pad_id]);
		if (pad_id > 0) {
			if (pad_id == padPointer) {
				panel_head.setConfig(configPads[pad_id], head_pad, pad_id);				
			}
			if (pad_id == (padPointer + 1)) {
				panel_rim.setConfig(configPads[pad_id], rim_pad, pad_id);
			}
		} else {
			if (pad_id == padPointer) {
				panel_head.setConfig(configPads[pad_id], head_pad, pad_id);
			}
		}
	}


	public Config3rd getConfig3rd(int third_id) {
		if (third_id == thirdPointer) {
			panel_3rd_zone.updateConfig();
		}
		return config3rds[third_id];
	}

	public void setConfig3rd(byte [] buffer, int third_id) {
		Utils.copySysexToConfig3rd(buffer, config3rds[third_id]);
		if (padPointer > 0 ) {
			if (third_id == thirdPointer) {
				panel_3rd_zone.setConfig(config3rds[third_id]);
			}
		}
	}
	
	private String getPadName(int pad_id) {
		String result;
		String head_rim = ((pad_id&0x01) > 0)?"H":"R";
		if (configPads[pad_id].name > 0) {
			result = Constants.CUSTOM_PADS_NAMES_LIST[configPads[pad_id].name - 1] + head_rim;
		} else {
			result = Constants.PADS_NAMES_LIST[pad_id];
		}
		
		return result;
	}
	
	private void updatePadsSelection(int pad_id) {
		int index;
		String head_str;
		String rim_str;
		String padString;
		
		if (pad_id > 0) {
			index = ((pad_id - 1)>>1) + 1;
			head_str = getPadName(pad_id);
			rim_str = getPadName(pad_id + 1);
			padString = ((Integer)(pad_id + 1)).toString() + " " +  head_str + "/" + ((Integer)(pad_id + 2)).toString() + " " + rim_str;
		} else {
			index = 0;
			head_str = getPadName(0);
			padString = ((Integer)(1)).toString() + "(" + head_str + ")";			
		}

		ActionListener copyPadAction =  new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.printf("Index = %d\n", Integer.parseInt(((JMenuItem)arg0.getSource()).getName()));
			}
		};
		if (mnCopypadto.getMenuComponentCount() == 0) {
			mnCopypadto.insert("All", 0);
			((JMenuItem)mnCopypadto.getMenuComponent(0)).setName("0");
			((JMenuItem)mnCopypadto.getMenuComponent(0)).addActionListener(copyPadAction);
		}
		if ((index+1) < mnCopypadto.getMenuComponentCount()) {
			mnCopypadto.remove(index+1);
		}
		mnCopypadto.insert(padString, index+1);
		((JMenuItem)mnCopypadto.getMenuComponent(index+1)).setName(((Integer)(index+1)).toString());
		((JMenuItem)mnCopypadto.getMenuComponent(index+1)).addActionListener(copyPadAction);
		
		comboBox_padSelection.insertItemAt(padString, index);
		comboBox_padSelection.removeItemAt(index+1);
		//populateCopyPad();
	}

	private void populateCopyPad() {
		mnCopypadto.removeAll();
		for (int i = 0;i<comboBox_padSelection.getItemCount();i++) {
			mnCopypadto.add(comboBox_padSelection.getItemAt(i).toString());
		}
	}

	private void switch_to_pad(int pad_id) {
		int comboBox_pointer = 0;
		
		if (prevPadPointer >= 0) {
			panel_head.updateConfig();
		}

		if (prevPadPointer > 0 ) {
			panel_rim.updateConfig();
			panel_3rd_zone.updateConfig();
		}
		if (pad_id > 0 ) {
			padPointer = ((pad_id - 1)&0xfffffe) + 1;
			thirdPointer = (pad_id - 1)/2;
			panel_rim.setVisible(true);
			//panel_3rd_zone.setVisible(true);
			panel_rim.setConfig(configPads[padPointer+1], rim_pad, padPointer+1);
			panel_3rd_zone.setConfig(config3rds[thirdPointer]);
			panel_3rd_zone.setAsSwitch(configPads[padPointer+1].type);
			comboBox_pointer = ((pad_id - 1)>>1) + 1;
		} else {
			padPointer = 0;
			panel_rim.setVisible(false);
			//panel_3rd_zone.setVisible(false);
		}
		if (prevPadPointer >= 0) {
			updatePadsSelection(prevPadPointer);
		}
		prevPadPointer = padPointer;
		prevThirdPointer = thirdPointer;
		panel_head.setConfig(configPads[padPointer], head_pad, padPointer);
		panel_head.getComboBox_type().setEnabled((padPointer != 0));
		//panel_3rd_zone.setVisible(panel_head.getComboBox_type().getSelectedIndex() > 0);
		panel_rim.getComboBox_type().setEnabled(panel_head.getComboBox_type().getSelectedIndex() > 0);
		//switchingPad = true;
		comboBox_padSelection.setSelectedIndex(comboBox_pointer);
		//switchingPad = false;
		//if (comboBox_pointer != prevPadSelection ) {
			//comboBox_padSelection.setSelectedIndex(comboBox_pointer);
		//}
	}
	
	public void loadFromConfigFull (ConfigFull config) {
		configPads = config.configPads;
		config3rds = config.config3rds;
		prevPadPointer = -1;
		switch_to_pad(padPointer);
	}
	
	private void copyPadVar(String varName, int pad_id, ConfigPad config) {
		if (varName.equals("name")) {
			configPads[pad_id].name = config.name;	
		}
		if (varName.equals("note")) {
			configPads[pad_id].note = config.note;	
		}
		if (varName.equals("altNote")) {
			configPads[pad_id].altNote = config.altNote;	
		}
		if (varName.equals("pressrollNote")) {
			configPads[pad_id].pressrollNote = config.pressrollNote;	
		}
		if (varName.equals("channel")) {
			configPads[pad_id].channel = config.channel;	
		}
		if (varName.equals("curve")) {
			configPads[pad_id].curve = config.curve;	
		}
		if (varName.equals("threshold")) {
			configPads[pad_id].threshold = config.threshold;	
		}
		if (varName.equals("retrigger")) {
			configPads[pad_id].retrigger = config.retrigger;	
		}
		if (varName.equals("highLevel")) {
			configPads[pad_id].levelMax = config.levelMax;	
		}
		if (varName.equals("minScan")) {
			configPads[pad_id].minScan = config.minScan;	
		}
		if (varName.equals("type")) {
			configPads[pad_id].type = config.type;	
			configPads[pad_id].dual = config.dual;	
			configPads[pad_id].threeWay = config.threeWay;	
		}
		if (varName.equals("autoLevel")) {
			configPads[pad_id].autoLevel = config.autoLevel;	
		}
		if (varName.equals("special")) {
			configPads[pad_id].special = config.special;	
		}
		if (varName.equals("gain")) {
			configPads[pad_id].gain = config.gain;	
		}
		if (varName.equals("xtalkLevel")) {
			configPads[pad_id].xtalkLevel = config.xtalkLevel;	
		}
		if (varName.equals("xtalkGroup")) {
			configPads[pad_id].xtalkGroup = config.xtalkGroup;	
		}
		if (varName.equals("dynTime")) {
			configPads[pad_id].dynTime = config.dynTime;	
		}
		if (varName.equals("dynLevel")) {
			configPads[pad_id].dynLevel = config.dynLevel;	
		}
		if (varName.equals("compression")) {
			configPads[pad_id].compression = config.compression;	
		}
		if (varName.equals("shift")) {
			configPads[pad_id].shift = config.shift;	
		}
	}
	
	private void copyPadVarToAll(String varName, boolean head_rim) {
		int input = padPointer;
		panel_head.updateConfig();
		if (head_rim == head_pad) {
			for (int i = 0; i<Constants.PADS_COUNT;i++) {
				if (i != input) {
					copyPadVar(varName, i, configPads[input]);
				}
		
			}
		} else {
			panel_rim.updateConfig();
			for (int i = 1; i<Constants.PADS_COUNT - 1;i++) {
				if (i != input) {
					copyPadVar(varName, i, configPads[input]);
					copyPadVar(varName, i+1, configPads[input+1]);
				}
				i++;
			}
		}

		if ((padPointer > 0) && (head_rim = head_pad)) {
			panel_rim.updateControls();
		}
	}
	
	public void updateInputCountsControls(int count) {
		if (panel_head != null) {
			switch_to_pad(0);
		}
		mnCopypadto.removeAll();
		comboBox_padSelection.setMaximumRowCount((count+1)/2);
		comboBox_padSelection.removeAllItems();
		comboBox_padSelection.addItem("");
		updatePadsSelection(0);
        for(int i=1; i<count; i++){
    		comboBox_padSelection.addItem("");
    		updatePadsSelection(i);
    		i++;
        }
	}

	public JButton getBtnGetall() {
		return btnGetall;
	}
	public JButton getBtnSendall() {
		return btnSendall;
	}
	public JButton getBtnLoad() {
		return btnLoad;
	}
	public JButton getBtnSave() {
		return btnSave;
	}
//	private static void addPopup(Component component, final JPopupMenu popup) {
//		component.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					showMenu(e);
//				}
//			}
//			public void mouseReleased(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					showMenu(e);
//				}
//			}
//			private void showMenu(MouseEvent e) {
//				popup.show(e.getComponent(), e.getX(), e.getY());
//			}
//		});
//	}
}
