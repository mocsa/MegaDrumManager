package gui;

import java.awt.EventQueue;

import javax.sound.midi.MidiDevice;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.sound.midi.Transmitter;
import javax.sound.midi.Receiver;
import	javax.sound.midi.Sequence;
import	javax.sound.midi.Sequencer;
//import	javax.sound.midi.Track;
//import javax.sound.midi.Synthesizer;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.SysexMessage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main_window {

	private JFrame frmMegadrummanager;
	private Options dialog_options;

	private int nPorts;
	private int port;
	private int port_in;
	private int port_out;

	private MidiDevice midiout;
	private MidiDevice midiin;
	private MidiDevice.Info[]	aInfos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_window window = new Main_window();
					window.frmMegadrummanager.setVisible(true);
					window.dialog_options.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main_window() {
		midiin = null;
		midiout = null;
		port_in = 0;
		port_out = 0;
		
		initialize();
	}
	
	private void open_options_dialog() {
		aInfos = MidiSystem.getMidiDeviceInfo(); 
		nPorts = aInfos.length;
		int[] table_in = new int[nPorts];
		int[] table_out = new int[nPorts];

		dialog_options.comboBox_MIDI_In.removeAllItems();
		dialog_options.comboBox_MIDI_Out.removeAllItems();

 		port = 0;
		for (int i = 0; i < nPorts; i++)
		{
			try
			{
				midiin = MidiSystem.getMidiDevice(aInfos[i]);
				if (midiin.getMaxTransmitters() != 0)
				{
					table_in[port] = i;
					dialog_options.comboBox_MIDI_In.addItem(aInfos[i].getName());
					port++;
				}
			}
			catch (MidiUnavailableException e)
			{
				show_error("Error trying to list MIDI In devices");
			}
		}
		
		port = 0;		
		for (int i = 0; i < nPorts; i++)
		{
			try
			{
				midiout = MidiSystem.getMidiDevice(aInfos[i]);
				if (midiout.getMaxReceivers() != 0)
				{
					table_out[port] = i;
					dialog_options.comboBox_MIDI_Out.addItem(aInfos[i].getName());
					port++;
				}
			}
			catch (MidiUnavailableException e)
			{
				show_error("Error trying to list MIDI Out devices");
			}
		}
		
		dialog_options.comboBox_MIDI_In.setSelectedIndex(port_in);
		dialog_options.comboBox_MIDI_Out.setSelectedIndex(port_out);
		dialog_options.config_applied = false;

		dialog_options.setVisible(true);

		if (dialog_options.config_applied) {
			port_in = dialog_options.midi_port_in;
			port_out = dialog_options.midi_port_out;
			midiout.close();
			midiin.close();
		    try {
		    	midiin = MidiSystem.getMidiDevice(aInfos[table_in[port_in]]);
		    	midiin.open();
				//System.out.printf("\nOpened MIDI In port %d (%d) - %s.\n", port_in,table_in[port_in],aInfos[table_in[port_in]].getName());
		    } catch (MidiUnavailableException e) {
		    	show_error("Cannot open MIDI In port");
		    }		    		    
		    try {
		    	midiout = MidiSystem.getMidiDevice(aInfos[table_out[port_out]]);
		    	midiout.open();
				//System.out.printf("\nOpened MIDI Out port %d (%d) - %s.\n", port_out,table_out[port_out],aInfos[table_out[port_out]].getName());
		    } catch (MidiUnavailableException e) {
		    	show_error("Cannot open MIDI Out port");
		    }
		}
		
	}
	
	private void show_error(String msg) {
		JOptionPane.showMessageDialog(null,
			    msg,
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMegadrummanager = new JFrame();
		frmMegadrummanager.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (midiout.isOpen()) {
					midiout.close();
				}
				if (midiin.isOpen()) {
					midiin.close();
				}
			}
		});
		frmMegadrummanager.setResizable(false);
		frmMegadrummanager.setTitle("MegaDrumManager");
		frmMegadrummanager.setBounds(100, 100, 450, 312);
		frmMegadrummanager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		dialog_options = new Options();
		
		JMenuBar menuBar = new JMenuBar();
		frmMegadrummanager.setJMenuBar(menuBar);
		
		JMenu mnMain = new JMenu("Main");
		menuBar.add(mnMain);
		
		JMenu mnLoad = new JMenu("All settings");
		mnMain.add(mnLoad);
		
		JMenuItem mntmLoadFromMd = new JMenuItem("Load from MD");
		mnLoad.add(mntmLoadFromMd);
		
		JMenuItem mntmSendToMd = new JMenuItem("Send to MD");
		mnLoad.add(mntmSendToMd);
		
		JMenuItem mntmLoadFromFile = new JMenuItem("Load from file");
		mnLoad.add(mntmLoadFromFile);
		
		JMenuItem mntmSaveToFile = new JMenuItem("Save to file");
		mnLoad.add(mntmSaveToFile);
		
		JMenuItem mntmSaveToMd = new JMenuItem("Save to MD slot 1");
		mnLoad.add(mntmSaveToMd);
		
		JMenu mnMiscSettings = new JMenu("Misc settings");
		mnMain.add(mnMiscSettings);
		
		JMenuItem mntmLoadFromMd_1 = new JMenuItem("Load from MD");
		mnMiscSettings.add(mntmLoadFromMd_1);
		
		JMenuItem mntmSendToMd_1 = new JMenuItem("Send to MD");
		mnMiscSettings.add(mntmSendToMd_1);
		
		JMenu mnHihatPedalSettings = new JMenu("HiHat pedal settings");
		mnHihatPedalSettings.setToolTipText("");
		mnMain.add(mnHihatPedalSettings);
		
		JMenuItem mntmLoadFromMd_2 = new JMenuItem("Load from MD");
		mnHihatPedalSettings.add(mntmLoadFromMd_2);
		
		JMenuItem mntmSendToMd_2 = new JMenuItem("Send to MD");
		mnHihatPedalSettings.add(mntmSendToMd_2);
		
		JMenu mnAllPadsSettings = new JMenu("All pads settings");
		mnMain.add(mnAllPadsSettings);
		
		JMenuItem mntmLoadFromMd_3 = new JMenuItem("Load from MD");
		mnAllPadsSettings.add(mntmLoadFromMd_3);
		
		JMenuItem mntmSendToMd_3 = new JMenuItem("Send to MD");
		mnAllPadsSettings.add(mntmSendToMd_3);
		
		JMenu mnSelectedPadSettings = new JMenu("Selected pad settings");
		mnMain.add(mnSelectedPadSettings);
		
		JMenuItem mntmLoadFromMd_4 = new JMenuItem("Load from MD");
		mnSelectedPadSettings.add(mntmLoadFromMd_4);
		
		JMenuItem mntmSendToMd_4 = new JMenuItem("Send to MD");
		mnSelectedPadSettings.add(mntmSendToMd_4);
		
		JMenu mnCustomCurves = new JMenu("Custom curves");
		mnMain.add(mnCustomCurves);
		
		JMenuItem mntmLoadFromMd_5 = new JMenuItem("Load from MD");
		mnCustomCurves.add(mntmLoadFromMd_5);
		
		JMenuItem mntmSendToMd_5 = new JMenuItem("Send to MD");
		mnCustomCurves.add(mntmSendToMd_5);
		
		JSeparator separator_1 = new JSeparator();
		mnMain.add(separator_1);
		
		JMenuItem mntmOptions = new JMenuItem("Options");
		mntmOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				open_options_dialog();
			}
		});
		mnMain.add(mntmOptions);
		
		JSeparator separator = new JSeparator();
		mnMain.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnMain.add(mntmExit);
		
		JPanel panel_misc = new JPanel();
		
		panel_misc.setBorder(new TitledBorder(null, "Misc", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(frmMegadrummanager.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_misc, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(263, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_misc, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
		);
		panel_misc.setLayout(null);
		
		JPanel panel_misc_noteoff = new JPanel();
		panel_misc_noteoff.setBounds(6, 22, 168, 30);
		panel_misc.add(panel_misc_noteoff);
		panel_misc_noteoff.setLayout(null);
		
		JLabel lblNoteoffDelay = new JLabel("NoteOff delay");
		lblNoteoffDelay.setBounds(13, 7, 74, 16);
		panel_misc_noteoff.add(lblNoteoffDelay);
		
		JSpinner spinner_noteoff = new JSpinner();
		spinner_noteoff.setBounds(92, 5, 52, 20);
		panel_misc_noteoff.add(spinner_noteoff);
		spinner_noteoff.setModel(new SpinnerNumberModel(new Short((short) 200), new Short((short) 100), new Short((short) 2000), new Short((short) 10)));
		
		JPanel panel_misc_pressroll = new JPanel();
		panel_misc_pressroll.setLayout(null);
		panel_misc_pressroll.setBounds(6, 51, 168, 30);
		panel_misc.add(panel_misc_pressroll);
		
		JLabel lblPressrollTime = new JLabel("PressRoll");
		lblPressrollTime.setBounds(13, 7, 74, 16);
		panel_misc_pressroll.add(lblPressrollTime);
		
		JSpinner spinner_pressroll = new JSpinner();
		spinner_pressroll.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 2000), new Short((short) 1)));
		spinner_pressroll.setBounds(92, 5, 52, 20);
		panel_misc_pressroll.add(spinner_pressroll);
		
		JPanel panel_misc_latency = new JPanel();
		panel_misc_latency.setLayout(null);
		panel_misc_latency.setBounds(6, 79, 168, 30);
		panel_misc.add(panel_misc_latency);
		
		JLabel lblLatency = new JLabel("Latency");
		lblLatency.setBounds(13, 7, 74, 16);
		panel_misc_latency.add(lblLatency);
		
		JSpinner spinner_latency = new JSpinner();
		spinner_latency.setModel(new SpinnerNumberModel(new Short((short) 40), new Short((short) 10), new Short((short) 100), new Short((short) 1)));
		spinner_latency.setBounds(92, 5, 52, 20);
		panel_misc_latency.add(spinner_latency);
		
		JPanel panel_misc_flags = new JPanel();
		panel_misc_flags.setBounds(6, 109, 105, 90);
		panel_misc.add(panel_misc_flags);
		panel_misc_flags.setLayout(null);
		
		JCheckBox chckbxBigVuMeter = new JCheckBox("Big VU meter");
		chckbxBigVuMeter.setBounds(0, 0, 105, 25);
		panel_misc_flags.add(chckbxBigVuMeter);
		
		JCheckBox chckbxQuickAccess = new JCheckBox("Quick Access");
		chckbxQuickAccess.setBounds(0, 21, 105, 25);
		panel_misc_flags.add(chckbxQuickAccess);
		
		JCheckBox chckbxAltFalseTrig = new JCheckBox("Alt False Trig");
		chckbxAltFalseTrig.setBounds(0, 43, 105, 25);
		panel_misc_flags.add(chckbxAltFalseTrig);
		
		JCheckBox chckbxInputsPriority = new JCheckBox("Inputs Priority");
		chckbxInputsPriority.setBounds(0, 65, 105, 25);
		panel_misc_flags.add(chckbxInputsPriority);
		frmMegadrummanager.getContentPane().setLayout(groupLayout);
	}
}

