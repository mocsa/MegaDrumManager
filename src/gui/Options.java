package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Font;

public class Options extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JCheckBox chckbx_samePort;
	public JComboBox comboBox_MIDI_In;
	public JComboBox comboBox_MIDI_Out;
	private JCheckBox checkBox_Thru;
	public JComboBox comboBox_MIDI_Thru;
	private JCheckBox checkBox_saveOnClose;
	private ConfigOptions configOptions;
	JCheckBox checkBox_autoOpen;
	public int midi_port_out;
	public int midi_port_in;
	public boolean config_applied; 

	/**
	 * Create the dialog.
	 */
	public Options() {
		
		configOptions = new ConfigOptions();
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setName("dialog_options");
		setTitle("Options");
		setBounds(100, 100, 495, 317);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("261px"),},
			new RowSpec[] {
				RowSpec.decode("173px"),}));
		
		JPanel panel_midi = new JPanel();
		panel_midi.setBorder(new TitledBorder(null, "MIDI Ports", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panel_midi, "1, 1, fill, fill");
		
		comboBox_MIDI_Out = new JComboBox();
		comboBox_MIDI_Out.setFont(new Font("Tahoma", Font.PLAIN, 10));
		comboBox_MIDI_Out.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				midi_port_out = comboBox_MIDI_Out.getSelectedIndex();
			}
		});
		panel_midi.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:101px"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("left:144px"),},
			new RowSpec[] {
				RowSpec.decode("fill:12dlu"),
				RowSpec.decode("fill:12dlu"),
				RowSpec.decode("fill:12dlu"),
				RowSpec.decode("fill:12dlu"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("fill:12dlu"),}));
		
		JLabel lblUseSameInout = new JLabel("Use same In/Out");
		lblUseSameInout.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblUseSameInout, "1, 1");
		
		chckbx_samePort = new JCheckBox("");
		chckbx_samePort.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				comboBox_MIDI_Out.setEnabled(!chckbx_samePort.isSelected());
			}
		});
		panel_midi.add(chckbx_samePort, "3, 1, default, fill");
		chckbx_samePort.setHorizontalTextPosition(SwingConstants.LEADING);
		chckbx_samePort.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panel_midi.add(comboBox_MIDI_Out, "3, 3, fill, fill");
		
		comboBox_MIDI_In = new JComboBox();
		comboBox_MIDI_In.setFont(new Font("Tahoma", Font.PLAIN, 10));
		comboBox_MIDI_In.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				midi_port_in = comboBox_MIDI_In.getSelectedIndex();
			}
		});
		panel_midi.add(comboBox_MIDI_In, "3, 2, fill, fill");
		
		JLabel lblMidiIn = new JLabel("Midi In");
		lblMidiIn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblMidiIn, "1, 2, default, center");
		
		JLabel lblMidiOut = new JLabel("Midi Out");
		lblMidiOut.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblMidiOut, "1, 3, default, center");
		
		JLabel lblEnableMidiThru = new JLabel("Enable MIDI Thru");
		lblEnableMidiThru.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblEnableMidiThru, "1, 4");
		
		checkBox_Thru = new JCheckBox("");
		checkBox_Thru.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				comboBox_MIDI_Thru.setEnabled(checkBox_Thru.isSelected());
			}
		});
		checkBox_Thru.setHorizontalTextPosition(SwingConstants.LEADING);
		panel_midi.add(checkBox_Thru, "3, 4, default, fill");
		
		JLabel lblMidiThru = new JLabel("Midi Thru");
		lblMidiThru.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblMidiThru, "1, 5, right, default");
		
		comboBox_MIDI_Thru = new JComboBox();
		comboBox_MIDI_Thru.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_midi.add(comboBox_MIDI_Thru, "3, 5, fill, fill");
		
		JLabel lblSaveOptionsOn = new JLabel("Save Options on Exit");
		lblSaveOptionsOn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblSaveOptionsOn, "1, 6");
		
		checkBox_saveOnClose = new JCheckBox("");
		checkBox_saveOnClose.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				checkBox_autoOpen.setEnabled(checkBox_saveOnClose.isSelected());
			}
		});
		checkBox_saveOnClose.setHorizontalTextPosition(SwingConstants.LEADING);
		panel_midi.add(checkBox_saveOnClose, "3, 6");
		
		JLabel lblOpenOnStartup = new JLabel("Init Ports on Startup");
		lblOpenOnStartup.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblOpenOnStartup, "1, 7");
		
		checkBox_autoOpen = new JCheckBox("");
		checkBox_autoOpen.setHorizontalTextPosition(SwingConstants.LEADING);
		panel_midi.add(checkBox_autoOpen, "3, 7");
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						config_applied = true;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void updateConfig() {
		configOptions.useSamePort = chckbx_samePort.isSelected();
		configOptions.MidiInName = (String)comboBox_MIDI_In.getSelectedItem();
		configOptions.MidiOutName = (String)comboBox_MIDI_Out.getSelectedItem();
		configOptions.MidiThruName = (String)comboBox_MIDI_Thru.getSelectedItem();
		configOptions.useThruPort = checkBox_Thru.isSelected();
		configOptions.saveOnExit = checkBox_saveOnClose.isSelected();
		configOptions.autoOpenPorts = checkBox_autoOpen.isSelected();
	}
	
	private void updateControls() {
		chckbx_samePort.setSelected(configOptions.useSamePort);
		comboBox_MIDI_In.setSelectedItem(configOptions.MidiInName);
		comboBox_MIDI_Out.setSelectedItem(configOptions.MidiOutName);
		comboBox_MIDI_Thru.setSelectedItem(configOptions.MidiThruName);
		comboBox_MIDI_Out.setEnabled(!configOptions.useSamePort);
		comboBox_MIDI_Thru.setEnabled(configOptions.useThruPort);
		checkBox_Thru.setSelected(configOptions.useThruPort);
		checkBox_saveOnClose.setSelected(configOptions.saveOnExit);
		checkBox_autoOpen.setSelected(configOptions.autoOpenPorts);
	}
	
	public void loadOptionsFrom(ConfigOptions options) {
		configOptions.autoOpenPorts = options.autoOpenPorts;
		configOptions.saveOnExit = options.saveOnExit;
		configOptions.useSamePort = options.useSamePort;
		configOptions.useThruPort = options.useThruPort;
		configOptions.MidiInName = options.MidiInName;
		configOptions.MidiOutName = options.MidiOutName;
		configOptions.MidiThruName = options.MidiThruName;
		updateControls();
	}
	
	public void saveOptionsTo(ConfigOptions options) {
		updateConfig();
		options.autoOpenPorts = configOptions.autoOpenPorts;
		options.saveOnExit = configOptions.saveOnExit;
		options.useSamePort = configOptions.useSamePort;		
		options.useThruPort = configOptions.useThruPort;
		options.MidiInName = configOptions.MidiInName;
		options.MidiOutName = configOptions.MidiOutName;
		options.MidiThruName = configOptions.MidiThruName;
	}
	
	public void fillInPorts(String[] list) {
		comboBox_MIDI_In.removeAllItems();
		for (String string : list) {
			comboBox_MIDI_In.addItem(string);
		}		
	}

	public void fillOutPorts(String[] list) {
		comboBox_MIDI_Out.removeAllItems();	
		for (String string : list) {
			comboBox_MIDI_Out.addItem(string);
		}		
	}

	public void fillThruPorts(String[] list) {
		comboBox_MIDI_Thru.removeAllItems();
		for (String string : list) {
			comboBox_MIDI_Thru.addItem(string);
		}		
	}

}
