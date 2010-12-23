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

	//private boolean head_pad_type;
	private static final boolean head_pad = true;
	private static final boolean rim_pad = false;


	/**
	 * Create the panel.
	 */
	public PadsControls() {
		configPads = new ConfigPad[55];
        for(int i=0; i<55; i++){
        	configPads[i] = new ConfigPad();
        }
        padPointer = 0;
        prevPadPointer = 0;

		config3rds = new Config3rd[27];
        for(int i=0; i<27; i++){
        	config3rds[i] = new Config3rd();
        }
        thirdPointer = 0;
        prevThirdPointer = 0;
        
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(62dlu;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),
				RowSpec.decode("max(256dlu;default)"),
				RowSpec.decode("66dlu"),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JPanel panel_input_selection = new JPanel();
		add(panel_input_selection, "1, 1, fill, fill");
		panel_input_selection.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("12dlu"),}));
		
		JLabel label = new JLabel("Input");
		label.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel_input_selection.add(label, "2, 1, right, default");
		
		comboBox_padSelection = new JComboBox();
		comboBox_padSelection.addItem("1");
        for(int i=1; i<55; i++){
    		comboBox_padSelection.addItem(((Integer)(i + 1)).toString());
    		i++;
        }
 
		comboBox_padSelection.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
					int index = comboBox_padSelection.getSelectedIndex();
					
					if (index > 0 ) {
						index = ((index - 1)*2) + 1;
						switch_to_pad(index);
					} else {
						switch_to_pad(0);
					}
		        }
			}
		});
		comboBox_padSelection.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_input_selection.add(comboBox_padSelection, "4, 1, fill, default");
		
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
		add(panel_head_rim, "1, 2, fill, fill");
		panel_head_rim.setLayout(new GridLayout(1, 2, 0, 0));
		
		panel_head = new PadCommonControls();
		panel_head_rim.add(panel_head);
		panel_head.setBorder(new TitledBorder(null, "Head/Bow", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel_rim = new PadCommonControls();
		panel_rim.setVisible(false);
		panel_head_rim.add(panel_rim);
		panel_rim.setBorder(new TitledBorder(null, "Rim/Edge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel_3rd_zone = new ThirdZoneControls();
		panel_3rd_zone.setVisible(false);
		add(panel_3rd_zone, "1, 3");
		panel_3rd_zone.setBorder(new TitledBorder(null, "3rd Zone", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_buttons = new JPanel();
		add(panel_buttons, "1, 4, fill, fill");
		
		btnGet = new JButton("Get");
		btnGet.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnGet);
		
		btnSend = new JButton("Send");
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSend);
		
		JButton btnGetall = new JButton("GetAll");
		btnGetall.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnGetall);
		
		JButton btnSendall = new JButton("SendAll");
		btnSendall.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSendall);

		panel_head.setConfig(configPads[0], head_pad);
		switch_to_pad(0);

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
				panel_head.setConfig(config, head_pad);				
			}
			if (pad_id == (padPointer + 1)) {
				panel_rim.setConfig(config, rim_pad);				
			}
		} else {
			if (pad_id == padPointer) {
				panel_head.setConfig(config, head_pad);
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

	private void switch_to_pad(int pad_id) {
		int comboBox_pointer = 0;
		
		configPads[prevPadPointer].copyVarsFrom(panel_head.getConfig());

		if (prevPadPointer > 0 ) {
			configPads[prevPadPointer+1].copyVarsFrom(panel_rim.getConfig());
			config3rds[prevThirdPointer].copyVarsFrom(panel_3rd_zone.getConfig());
		}
		if (pad_id > 0 ) {
			padPointer = ((pad_id - 1)&0xfffffe) + 1;
			thirdPointer = (pad_id - 1)/2;
			panel_rim.setVisible(true);
			panel_3rd_zone.setVisible(true);
			panel_rim.setConfig(configPads[padPointer+1], rim_pad);
			panel_3rd_zone.setConfig(config3rds[thirdPointer]);
			comboBox_pointer = ((pad_id - 1)>>1) + 1;
		} else {
			padPointer = 0;
			panel_rim.setVisible(false);
			panel_3rd_zone.setVisible(false);
		}
		prevPadPointer = padPointer;
		prevThirdPointer = thirdPointer;
		panel_head.setConfig(configPads[padPointer], head_pad);
		panel_head.getComboBox_type().setEnabled((padPointer != 0));
		comboBox_padSelection.setSelectedIndex(comboBox_pointer);
	}
	
}
