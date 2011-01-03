package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;

public class ControlsMisc extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8689919340566654881L;

	private ConfigMisc configMisc;
	private JSpinner spinner_noteoff;
	private JSpinner spinner_pressroll;
	private JSpinner spinner_latency;
	private JCheckBox checkBox_bigVuMeter;
	private JCheckBox checkBox_quickAccess;
	private JCheckBox checkBox_altFalseTrSupp;
	private JCheckBox checkBox_inputsPriority;
	private JCheckBox checkBox_allGainsLow;
	private JButton btnGet;
	private JButton btnSend;

	/**
	 * Create the panel.
	 */
	public ControlsMisc() {
		configMisc = new ConfigMisc();
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				RowSpec.decode("1dlu"),}));
		
		JPanel panel_buttons = new JPanel();
		add(panel_buttons, "1, 1, fill, fill");
		panel_buttons.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("24dlu"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),}));
		
		btnGet = new JButton("Get");
		btnGet.setMargin(new Insets(1, 4, 1, 4));
		btnGet.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnGet, "1, 1, fill, fill");
		
		btnSend = new JButton("Send");
		btnSend.setMargin(new Insets(1, 4, 1, 4));
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSend, "3, 1, fill, fill");
		
		JPanel panel = new JPanel();
		add(panel, "1, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:56dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("32dlu"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNoteOffDelay = new JLabel("Note Off Delay");
		lblNoteOffDelay.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblNoteOffDelay, "1, 1");
		
		spinner_noteoff = new JSpinner();
		spinner_noteoff.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short value = ((Short)spinner_noteoff.getValue()).shortValue();
				configMisc.note_off = (short)(value/10);
				if (configMisc.note_off < configMisc.pressroll) {
					configMisc.pressroll = configMisc.note_off; 
				}
				spinner_pressroll.setModel(new SpinnerNumberModel(new Short((short) (configMisc.pressroll*10)), new Short((short) 0), new Short((short) (configMisc.note_off*10)), new Short((short) 10)));
			}
		});
		spinner_noteoff.setModel(new SpinnerNumberModel(new Short((short) 200), new Short((short) 100), new Short((short) 2000), new Short((short) 10)));
		panel.add(spinner_noteoff, "3, 1");
		
		JLabel lblPressrollTimeout = new JLabel("Pressroll timeout");
		lblPressrollTimeout.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblPressrollTimeout, "1, 2");
		
		spinner_pressroll = new JSpinner();
		spinner_pressroll.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short value = ((Short)spinner_pressroll.getValue()).shortValue();
				configMisc.pressroll = (short)(value/10);
			}
		});
		spinner_pressroll.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 200), new Short((short) 10)));
		panel.add(spinner_pressroll, "3, 2");
		
		JLabel lblLatency = new JLabel("Latency");
		lblLatency.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblLatency, "1, 3");
		
		spinner_latency = new JSpinner();
		spinner_latency.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short value = ((Short)spinner_latency.getValue()).shortValue();
				configMisc.latency = (short)value;
			}
		});
		spinner_latency.setModel(new SpinnerNumberModel(new Short((short) 40), new Short((short) 10), new Short((short) 100), new Short((short) 1)));
		panel.add(spinner_latency, "3, 3");
		
		JLabel lblBigVuMeter = new JLabel("Big VU meter");
		lblBigVuMeter.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblBigVuMeter, "1, 4");
		
		checkBox_bigVuMeter = new JCheckBox("");
		checkBox_bigVuMeter.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configMisc.big_vu_meter = checkBox_bigVuMeter.isSelected();
			}
		});
		panel.add(checkBox_bigVuMeter, "3, 4");
		
		JLabel lblQuickAccess = new JLabel("Quick Access");
		lblQuickAccess.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblQuickAccess, "1, 5");
		
		checkBox_quickAccess = new JCheckBox("");
		checkBox_quickAccess.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configMisc.quick_access = checkBox_quickAccess.isSelected();
			}
		});
		panel.add(checkBox_quickAccess, "3, 5");
		
		JLabel lblAltFalsetrsuppression = new JLabel("AltFalseTrSupp");
		lblAltFalsetrsuppression.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblAltFalsetrsuppression, "1, 6");
		
		checkBox_altFalseTrSupp = new JCheckBox("");
		checkBox_altFalseTrSupp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configMisc.alt_false_tr_supp = checkBox_altFalseTrSupp.isSelected();
			}
		});
		panel.add(checkBox_altFalseTrSupp, "3, 6");
		
		JLabel lblInputsPriority = new JLabel("Inputs Priority");
		lblInputsPriority.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblInputsPriority, "1, 7");
		
		checkBox_inputsPriority = new JCheckBox("");
		checkBox_inputsPriority.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configMisc.inputs_priority = checkBox_inputsPriority.isSelected();			}
		});
		panel.add(checkBox_inputsPriority, "3, 7");
		
		JLabel lblAllGainsLow = new JLabel("All Gains Low");
		lblAllGainsLow.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblAllGainsLow, "1, 8");
		
		checkBox_allGainsLow = new JCheckBox("");
		checkBox_allGainsLow.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configMisc.all_gains_low = checkBox_allGainsLow.isSelected();			}
		});
		panel.add(checkBox_allGainsLow, "3, 8");

	}
	
	private void updateControls() {
		spinner_noteoff.setValue((short)(configMisc.note_off*10));
		spinner_pressroll.setModel(new SpinnerNumberModel(new Short((short) (configMisc.pressroll*10)),
				new Short((short) 0),
				new Short((short) (configMisc.note_off*10)),
				new Short((short) 10)));
		spinner_latency.setValue((short)(configMisc.latency));
		checkBox_bigVuMeter.setSelected(configMisc.big_vu_meter);
		checkBox_quickAccess.setSelected(configMisc.quick_access);
		checkBox_altFalseTrSupp.setSelected(configMisc.alt_false_tr_supp);
		checkBox_inputsPriority.setSelected(configMisc.inputs_priority);
		checkBox_allGainsLow.setSelected(configMisc.all_gains_low);		
	}
	
	public void setConfig(ConfigMisc config) {
		configMisc = config;
		updateControls();
	}
	
	public ConfigMisc getConfig() {
		return configMisc;
	}

	public JButton getBtnGet() {
		return btnGet;
	}
	public JButton getBtnSend() {
		return btnSend;
	}

	public void copyToConfigFull (ConfigFull config, int chain_id) {
		config.sysex_misc = configMisc.getSysex(chain_id);
	}
	
	public void loadFromConfigFull (ConfigFull config) {
		configMisc.setFromSysex(config.sysex_misc);
		updateControls();
	}
}
