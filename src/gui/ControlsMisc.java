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

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Insets;

public class ControlsMisc extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8689919340566654881L;

	private Boolean changeEventsAllowed = false;
	
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
	private JButton btnLoad;
	private JButton btnSave;
	private JLabel lblBigVuSplit;
	private JCheckBox checkBox_bigVuSplit;
	private JCheckBox checkBox_MidiThru;
	private JLabel lblMidiThruEnabled;
	private JLabel labelCustomNamesEn;
	private JCheckBox checkBox_customNamesEn;
	private JSpinner spinner_octaveShift;
	private JLabel lblOctaveShift;

	/**
	 * Create the panel.
	 */
	public ControlsMisc() {
		//configMisc = new ConfigMisc();
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				RowSpec.decode("1dlu"),}));
		
		JPanel panel_buttons = new JPanel();
		add(panel_buttons, "1, 1, fill, fill");
		panel_buttons.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		btnGet = new JButton("Get");
		btnGet.setToolTipText("<html>Get Miscellaneous settings from MegaDrum</html>");
		btnGet.setMargin(new Insets(1, 2, 1, 2));
		btnGet.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnGet, "1, 1, fill, fill");
		
		btnSend = new JButton("Send");
		btnSend.setToolTipText("<html>Send Miscellaneous settings to MegaDrum</html>");
		btnSend.setMargin(new Insets(1, 2, 1, 2));
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSend, "3, 1, fill, fill");
		
		btnLoad = new JButton("Load");
		btnLoad.setToolTipText("<html>Load Miscellaneous settings from a file</html>");
		btnLoad.setMargin(new Insets(1, 2, 1, 2));
		btnLoad.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnLoad, "5, 1");
		
		btnSave = new JButton("Save");
		btnSave.setToolTipText("<html>Save Miscellaneous settings to a file</html>");
		btnSave.setMargin(new Insets(1, 2, 1, 2));
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSave, "7, 1");
		
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNoteOffDelay = new JLabel("Note Off Delay");
		lblNoteOffDelay.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblNoteOffDelay, "1, 1");
		
		spinner_noteoff = new JSpinner();
		spinner_noteoff.setToolTipText("<html>Delay in milliseconds of a Note Off message<br>\r\nafter a Note On message</html>");
		spinner_noteoff.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short value = ((Short)spinner_noteoff.getValue()).shortValue();
				configMisc.note_off = (short)(value/10);
				if (configMisc.note_off < configMisc.pressroll) {
					configMisc.pressroll = configMisc.note_off; 
				}
				spinner_pressroll.setModel(new SpinnerNumberModel(new Short((short) (configMisc.pressroll*10)), new Short((short) 0), new Short((short) (configMisc.note_off*10)), new Short((short) 10)));
				valueChanged();
			}
		});
		spinner_noteoff.setModel(new SpinnerNumberModel(new Short((short) 200), new Short((short) 100), new Short((short) 2000), new Short((short) 10)));
		panel.add(spinner_noteoff, "3, 1");
		
		JLabel lblPressrollTimeout = new JLabel("Pressroll timeout");
		lblPressrollTimeout.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblPressrollTimeout, "1, 2");
		
		spinner_pressroll = new JSpinner();
		spinner_pressroll.setToolTipText("<html>Timeout in milliseconds for Pressroll detection.<br>\r\n<br>\r\nIf subsequent hits on a pad are within this timeout<br>\r\nMegaDrum will send Pressroll Notes  instead of<br>\r\nNotes or Alt Notes. \r\n</html>");
		spinner_pressroll.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short value = ((Short)spinner_pressroll.getValue()).shortValue();
				configMisc.pressroll = (short)(value/10);
				valueChanged();
			}
		});
		spinner_pressroll.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 200), new Short((short) 10)));
		panel.add(spinner_pressroll, "3, 2");
		
		JLabel lblLatency = new JLabel("Latency");
		lblLatency.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblLatency, "1, 3");
		
		spinner_latency = new JSpinner();
		spinner_latency.setToolTipText("<html>Internally MegaDrum scans all inputs every 10-30 microseconds for presence of signals.<br>\r\nThen, very simplified, every <Latency> period MegaDrum<br>\r\nscans input states to see if any of the inputs registered signals<br>\r\nand sends MIDI messages for triggered inputs. Lowering this setting<br>\r\nwill reduce latency and may degrade level detection precision.<br>\r\nRaising this setting may improve level detection precision and will increase latency.<br> Latency can be changed between 10 and 100 which corresponds to 1 and 10 milliseconds.<br>\r\nDefault is 40 - 4 millisecond.</html>");
		spinner_latency.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short value = ((Short)spinner_latency.getValue()).shortValue();
				configMisc.latency = (short)value;
				valueChanged();
			}
		});
		spinner_latency.setModel(new SpinnerNumberModel(new Short((short) 40), new Short((short) 10), new Short((short) 100), new Short((short) 1)));
		panel.add(spinner_latency, "3, 3");
		
		lblOctaveShift = new JLabel("Notes Names Shift");
		lblOctaveShift.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblOctaveShift, "1, 4");
		
		spinner_octaveShift = new JSpinner();
		spinner_octaveShift.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short value = ((Short)spinner_octaveShift.getValue()).shortValue();
				configMisc.octave_shift = (short)value;
				valueChanged();
			}
		});
		spinner_octaveShift.setModel(new SpinnerNumberModel(new Short((short) 2), new Short((short) 0), new Short((short) 2), new Short((short) 1)));
		panel.add(spinner_octaveShift, "3, 4");
		
		JLabel lblBigVuMeter = new JLabel("Big VU meter");
		lblBigVuMeter.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblBigVuMeter, "1, 5");
		
		checkBox_bigVuMeter = new JCheckBox("");
		checkBox_bigVuMeter.setToolTipText("<html>When disabled (default) each of 32 blocks on an LCD<br>\r\nis used as an individual VU meter for every input.<br>\r\nThe resolution of each of these small VU meters is 16,<br>\r\ni.e. one bar per 16 adjacent MIDI levels giving 8 bars for 128 MIDI levels.<br>\r\nThe resolution of the Big VU Meter is much better, approximately 100 bars<br>\r\nfor 128 MIDI levels. </html>");
		checkBox_bigVuMeter.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configMisc.big_vu_meter = checkBox_bigVuMeter.isSelected();
				valueChanged();
			}
		});
		panel.add(checkBox_bigVuMeter, "3, 5");
		
		lblBigVuSplit = new JLabel("Big VU split");
		lblBigVuSplit.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblBigVuSplit, "1, 6");
		
		checkBox_bigVuSplit = new JCheckBox("");
		checkBox_bigVuSplit.setToolTipText("<html>When disabled (and Big VU Meter is enabled)<br>\r\nthe whole top row of the LCD is dedicated to HiHat pedal position<br>\r\nand the whole bottom row of the LCD shows hit levels from all inputs<br>\r\non a big single VU meter.<br>\r\nWhen enabled (and Big VU Meter is enabled) the whole top row<br>\r\nshows hit levels from all even (Head/Bow) inputs and the whole<br>\r\nbottom row of the LCD shows hit levels on odd (Rim/Edge) inputs.<br>\r\nThe HiHat pedal position is not shown in this mode.</html>");
		checkBox_bigVuSplit.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configMisc.big_vu_split = checkBox_bigVuSplit.isSelected();
				valueChanged();
			}
		});
		panel.add(checkBox_bigVuSplit, "3, 6");
		
		JLabel lblQuickAccess = new JLabel("Quick Access");
		lblQuickAccess.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblQuickAccess, "1, 7");
		
		checkBox_quickAccess = new JCheckBox("");
		checkBox_quickAccess.setToolTipText("<html>When enabled you can quickly jump between inputs<br>\r\nin the MegaDrum menu rather than navigate to each input<br>\r\nusing LEFT/RIGHT keys.<br>\r\n<br>\r\nTo use it when enabled, first enter the Menu, then press<br>\r\nthe HiHat pedal and then hit a pad you want to configure.<br>\r\nWhenever you hit another pad while still holding the HiHat pedal pressed<br>\r\nand still in the Menu, it will jump to the last hit pad input.<br>\r\nWhen disabled you can quickly load next/previous drum map by<br>\r\npressing keys UP/DOWN while not in the Menu and holding<br>\r\nthe HiHat pedal pressed. May be useful for live performances<br>\r\nto quickly load different drum maps?</html>");
		checkBox_quickAccess.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configMisc.quick_access = checkBox_quickAccess.isSelected();
				valueChanged();
			}
		});
		panel.add(checkBox_quickAccess, "3, 7");
		
		JLabel lblAltFalsetrsuppression = new JLabel("AltFalseTrSupp");
		lblAltFalsetrsuppression.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblAltFalsetrsuppression, "1, 8");
		
		checkBox_altFalseTrSupp = new JCheckBox("");
		checkBox_altFalseTrSupp.setToolTipText("<html>When enabled, MegaDrum uses alternative algorithm<br>\r\nfor false triggering suppression. It has effect on<br>\r\nXTalk/Rettriger/DynLvl/DynTime settings. May be better<br>\r\nthan the original algorithm for certain types of pads/cymbals/kits.</html>");
		checkBox_altFalseTrSupp.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configMisc.alt_false_tr_supp = checkBox_altFalseTrSupp.isSelected();
				valueChanged();
			}
		});
		panel.add(checkBox_altFalseTrSupp, "3, 8");
		
		JLabel lblInputsPriority = new JLabel("Inputs Priority");
		lblInputsPriority.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblInputsPriority, "1, 9");
		
		checkBox_inputsPriority = new JCheckBox("");
		checkBox_inputsPriority.setToolTipText("<html>When enabled the first 3/4th of Head/Bow inputs are given<br>\r\nslightly higher priority (sampled more often) then the rest of inputs.</html>");
		checkBox_inputsPriority.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configMisc.inputs_priority = checkBox_inputsPriority.isSelected();
				valueChanged();
			}
		});
		panel.add(checkBox_inputsPriority, "3, 9");
		
		JLabel lblAllGainsLow = new JLabel("All Gains Low");
		lblAllGainsLow.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblAllGainsLow, "1, 10");
		
		checkBox_allGainsLow = new JCheckBox("");
		checkBox_allGainsLow.setToolTipText("<html>When enabled, it will disable all individual input Gain levels<br>\r\nand make Gain even lower than 'Gain Level' 0.<br>\r\nIt could be used if all your pads are 'hot' and to get<br>\r\na better dynamic range with such pads.</html>");
		checkBox_allGainsLow.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configMisc.all_gains_low = checkBox_allGainsLow.isSelected();
				valueChanged();
			}
		});
		panel.add(checkBox_allGainsLow, "3, 10");
		
		lblMidiThruEnabled = new JLabel("MIDI Thru");
		lblMidiThruEnabled.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblMidiThruEnabled, "1, 11");
		
		checkBox_MidiThru = new JCheckBox("");
		checkBox_MidiThru.setToolTipText("<html>When enabled MegaDrum will send out all MIDI messages<br>\r\nit receives over MIDI.<br>\r\nIf MIDI routing is not correct, it can create MIDI loops<br>\r\nand cause all MIDI devices in the loop, including MegaDrum,<br>\r\nto misbehave.</html>");
		checkBox_MidiThru.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configMisc.midi_thru = checkBox_MidiThru.isSelected();
				valueChanged();
			}
		});
		panel.add(checkBox_MidiThru, "3, 11");
		
		labelCustomNamesEn = new JLabel("CustomNamesEn");
		labelCustomNamesEn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(labelCustomNamesEn, "1, 12");
		
		checkBox_customNamesEn = new JCheckBox("");
		checkBox_customNamesEn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configMisc.custom_names_en = checkBox_customNamesEn.isSelected();
				valueChanged();
			}
		});
		checkBox_customNamesEn.setToolTipText("<html>Check this box to enable saving Custom Pads Names<br>\r\nin MegaDrum non-volatile memory.<br><br>\r\nWhen enabled, MegaDrum can store fewer Configs/Drum maps<br>\r\nin non-volatile memory.</html>");
		panel.add(checkBox_customNamesEn, "3, 12");
		changeEventsAllowed = true;

	}

	private void valueChanged() {
		if (changeEventsAllowed) {
			firePropertyChange("valueChanged", false, true);
		}
	}
	
	public void updateControls() {
		spinner_noteoff.setValue((short)(configMisc.note_off*10));
		spinner_pressroll.setModel(new SpinnerNumberModel(new Short((short) (configMisc.pressroll*10)),
				new Short((short) 0),
				new Short((short) (configMisc.note_off*10)),
				new Short((short) 10)));
		spinner_latency.setValue((short)(configMisc.latency));
		spinner_octaveShift.setValue((short)(configMisc.octave_shift));
		checkBox_bigVuMeter.setSelected(configMisc.big_vu_meter);
		checkBox_quickAccess.setSelected(configMisc.quick_access);
		checkBox_bigVuSplit.setSelected(configMisc.big_vu_split);
		checkBox_altFalseTrSupp.setSelected(configMisc.alt_false_tr_supp);
		checkBox_inputsPriority.setSelected(configMisc.inputs_priority);
		checkBox_allGainsLow.setSelected(configMisc.all_gains_low);		
		checkBox_MidiThru.setSelected(configMisc.midi_thru);		
		checkBox_customNamesEn.setSelected(configMisc.custom_names_en);		
	}

public void setConfig(ConfigMisc config)
{
	changeEventsAllowed = false;
	configMisc = config;
	updateControls();
	changeEventsAllowed = true;
}

	public JButton getBtnGet() {
		return btnGet;
	}
	public JButton getBtnSend() {
		return btnSend;
	}

	public JButton getBtnLoad() {
		return btnLoad;
	}
	public JButton getBtnSave() {
		return btnSave;
	}
}
