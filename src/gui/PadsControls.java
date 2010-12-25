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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.BitSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PadsControls extends JPanel {
	private JButton btnGet;
	private JButton btnSend;
	private JComboBox comboBox_padSelection;
	private PadCommonControls panel_head;
	private PadCommonControls panel_rim;
	private ThirdZoneControls panel_3rd_zone;
	
	private ConfigPad [] configPads;
	private Config3rd [] config3rds;
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

	//private boolean head_pad_type;
	private static final boolean head_pad = true;
	private static final boolean rim_pad = false;
	private JButton btnGetall;
	private JButton btnSendall;

	/**
	 * Create the panel.
	 */
	public PadsControls() {
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
				ColumnSpec.decode("max(62dlu;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),
				RowSpec.decode("1dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("max(256dlu;default)"),
				RowSpec.decode("max(3dlu;min):grow"),}));
		
		JPanel panel_buttons = new JPanel();
		add(panel_buttons, "1, 1, fill, fill");
		panel_buttons.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("42dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("42dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("42dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("42dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),}));
		
		btnGet = new JButton("Get");
		btnGet.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnGet, "1, 1, fill, fill");
		
		btnSend = new JButton("Send");
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnSend, "3, 1, fill, fill");
		
		btnGetall = new JButton("GetAll");
		btnGetall.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnGetall, "5, 1, fill, fill");
		
		btnSendall = new JButton("SendAll");
		btnSendall.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_buttons.add(btnSendall, "7, 1, fill, fill");
		
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
		comboBox_padSelection.addItem("");
		updatePadsSelection(0);
        for(int i=1; i<Constants.PADS_COUNT; i++){
    		comboBox_padSelection.addItem("");
    		updatePadsSelection(i);
    		i++;
        }
        comboBox_padSelection.setSelectedIndex(0); 
		comboBox_padSelection.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
					padSelection = comboBox_padSelection.getSelectedIndex();
					//if (padSelection != prevPadSelection) {
						prevPadSelection = padSelection;						
						if (padSelection > 0 ) {
							padSelection = ((padSelection - 1)*2) + 1;
							switch_to_pad(padSelection);
						} else {
							switch_to_pad(0);
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
		
		panel_head = new PadCommonControls();
		panel_head.getComboBox_type().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
					panel_3rd_zone.setVisible(panel_head.getComboBox_type().getSelectedIndex() > 0);
					panel_rim.getComboBox_type().setEnabled(panel_head.getComboBox_type().getSelectedIndex() > 0);
		        }
			}
		});
		panel_head_rim.add(panel_head);
		panel_head.setBorder(new TitledBorder(null, "Head/Bow", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel_rim = new PadCommonControls();
		panel_rim.getComboBox_type().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
					panel_3rd_zone.setAsSwitch(panel_rim.getComboBox_type().getSelectedIndex() > 0);
		        }
			}
		});
		panel_rim.setVisible(false);
		panel_head_rim.add(panel_rim);
		panel_rim.setBorder(new TitledBorder(null, "Rim/Edge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel_3rd_zone = new ThirdZoneControls();
		panel_3rd_zone.setVisible(false);
		add(panel_3rd_zone, "1, 5, fill, fill");
		panel_3rd_zone.setBorder(new TitledBorder(null, "3rd Zone", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panel_head.setConfig(configPads[0], head_pad, 0);
		switch_to_pad(0);
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
				configPads[pad_id].copyVarsFrom(panel_head.getConfig());
			}
			if (pad_id == (padPointer + 1)) {
				configPads[pad_id].copyVarsFrom(panel_rim.getConfig());
			}
		} else {
			if (padPointer == 0) {
				configPads[pad_id].copyVarsFrom(panel_head.getConfig());
			}
		}		
		return configPads[pad_id];
	}

	public void setConfig(ConfigPad config,int pad_id) {
		configPads[pad_id].copyVarsFrom(config);
		if (pad_id > 0) {
			if (pad_id == padPointer) {
				panel_head.setConfig(config, head_pad, pad_id);				
			}
			if (pad_id == (padPointer + 1)) {
				panel_rim.setConfig(config, rim_pad, pad_id);
			}
		} else {
			if (pad_id == padPointer) {
				panel_head.setConfig(config, head_pad, pad_id);
			}
		}
	}


	public Config3rd getConfig3rd(int third_id) {
		if (third_id == thirdPointer) {
			config3rds[thirdPointer].copyVarsFrom(panel_3rd_zone.getConfig());			
		}
		return config3rds[third_id];
	}

	public void setConfig3rd(Config3rd config, int third_id) {
		config3rds[third_id].copyVarsFrom(config);
		if (padPointer > 0 ) {
			if (third_id == thirdPointer) {
				panel_3rd_zone.setConfig(config);
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
		comboBox_padSelection.insertItemAt(padString, index);
		comboBox_padSelection.removeItemAt(index+1);
	}

	private void switch_to_pad(int pad_id) {
		int comboBox_pointer = 0;
		
		if (prevPadPointer >= 0) {
			configPads[prevPadPointer].copyVarsFrom(panel_head.getConfig());			
		}

		if (prevPadPointer > 0 ) {
			configPads[prevPadPointer+1].copyVarsFrom(panel_rim.getConfig());
			config3rds[prevThirdPointer].copyVarsFrom(panel_3rd_zone.getConfig());
		}
		if (pad_id > 0 ) {
			padPointer = ((pad_id - 1)&0xfffffe) + 1;
			thirdPointer = (pad_id - 1)/2;
			panel_rim.setVisible(true);
			panel_3rd_zone.setVisible(true);
			panel_rim.setConfig(configPads[padPointer+1], rim_pad, padPointer+1);
			panel_3rd_zone.setConfig(config3rds[thirdPointer]);
			panel_3rd_zone.setAsSwitch(configPads[padPointer+1].type);
			comboBox_pointer = ((pad_id - 1)>>1) + 1;
		} else {
			padPointer = 0;
			panel_rim.setVisible(false);
			panel_3rd_zone.setVisible(false);
		}
		if (prevPadPointer >= 0) {
			updatePadsSelection(prevPadPointer);
		}
		prevPadPointer = padPointer;
		prevThirdPointer = thirdPointer;
		panel_head.setConfig(configPads[padPointer], head_pad, padPointer);
		panel_head.getComboBox_type().setEnabled((padPointer != 0));
		panel_3rd_zone.setVisible(panel_head.getComboBox_type().getSelectedIndex() > 0);
		panel_rim.getComboBox_type().setEnabled(panel_head.getComboBox_type().getSelectedIndex() > 0);
		if (comboBox_pointer != prevPadSelection ) {
			//comboBox_padSelection.setSelectedIndex(comboBox_pointer);
		}
	}

	public void copyToConfigFull (ConfigFull config, int chain_id) {
		int i;
		switch_to_pad(padPointer);
		for (i = 0; i<Constants.PADS_COUNT; i++) {
			config.sysex_pads[i] = configPads[i].getSysex(chain_id, i);
			configPads[i].copyToConfigFull(config, i);
		}
		for (i = 0; i<((Constants.PADS_COUNT - 1)/2); i++) {
			config.sysex_3rds[i] = config3rds[i].getSysex(chain_id, i);
		}
	}
	
	public void loadFromConfigFull (ConfigFull config) {
		int i;
		for (i = 0; i<Constants.PADS_COUNT; i++) {
			configPads[i].setFromSysex(config.sysex_pads[i]);
			configPads[i].copyFromConfigFull(config, i);
		}
		for (i = 0; i<((Constants.PADS_COUNT - 1)/2); i++) {
			config3rds[i].setFromSysex(config.sysex_3rds[i]);
		}
		prevPadPointer = -1;
		switch_to_pad(padPointer);
	}

	public JButton getBtnGetall() {
		return btnGetall;
	}
	public JButton getBtnSendall() {
		return btnSendall;
	}
}
