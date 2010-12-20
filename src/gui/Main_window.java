package gui;

import java.awt.EventQueue;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SpringLayout;

public class Main_window {

	private JFrame frmMegadrummanager;
	private Options dialog_options;
	private Midi_handler midi_handler;
	private Timer timer_midi;
	
	private JCheckBox chckbxAllGainsLow;
	private JSpinner spinner_noteoff;
	private JSpinner spinner_pressroll;
	private JSpinner spinner_latency;
	private JCheckBox chckbxBigVuMeter;
	private JCheckBox chckbxQuickAccess;
	private JCheckBox chckbxAltFalseTrig;
	private JCheckBox chckbxInputsPriority;



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
		
		initialize();
	}
	
	private void open_options_dialog() {
		
		midi_handler.Init_options(dialog_options);
	}
	
	public static void show_error(String msg) {
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
				midi_handler.Close_all_ports();
			}
		});
		frmMegadrummanager.setResizable(false);
		frmMegadrummanager.setTitle("MegaDrumManager");
		frmMegadrummanager.setBounds(100, 100, 971, 656);
		frmMegadrummanager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		dialog_options = new Options();
		midi_handler = new Midi_handler();
		timer_midi = new Timer();
		timer_midi.scheduleAtFixedRate(
				new TimerTask() {
					public void run() {
						midi_handler.get_midi();
						if (midi_handler.config_misc.changed) {
							update_misc_config_controls();
							midi_handler.config_misc.changed = false;
						}
					}
				}
				, 1000, 1);
		
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
		mntmLoadFromMd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.clear_midi_input();
				midi_handler.request_config_misc();
			}
		});
		mnMiscSettings.add(mntmLoadFromMd_1);
		
		JMenuItem mntmSendToMd_1 = new JMenuItem("Send to MD");
		mntmSendToMd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.send_config_misc();
			}
		});
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
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnMain.add(mntmExit);
		
		JPanel panel_misc = new JPanel();
		panel_misc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		panel_misc.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Misc", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panel_pedal = new JPanel();
		panel_pedal.setBorder(new TitledBorder(null, "HiHat Pedal/Controller", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_pads = new JPanel();
		panel_pads.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Pads", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout groupLayout = new GroupLayout(frmMegadrummanager.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_misc, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_pedal, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_pads, GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(13, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_pedal, GroupLayout.PREFERRED_SIZE, 382, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_misc, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_pads, GroupLayout.PREFERRED_SIZE, 594, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(235, Short.MAX_VALUE))
		);
		panel_pedal.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		PedalControls pedalControls = new PedalControls();
		panel_pedal.add(pedalControls, "1, 1");
		
		JPanel panel = new JPanel();
		panel_pedal.add(panel, "1, 3, fill, fill");
		
				JButton button = new JButton("Get");
				panel.add(button);
				button.setFont(new Font("Segoe UI", Font.PLAIN, 10));
				
				JButton button_1 = new JButton("Send");
				panel.add(button_1);
				button_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_pads.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("12dlu:grow"),
				RowSpec.decode("1dlu"),
				RowSpec.decode("max(267dlu;default)"),
				RowSpec.decode("57dlu"),
				RowSpec.decode("max(22dlu;default)"),}));
		
		JPanel panel_input_selection = new JPanel();
		panel_pads.add(panel_input_selection, "1, 1, fill, fill");
		panel_input_selection.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),}));
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel_input_selection.add(lblInput, "2, 1, right, default");
		
		JComboBox comboBox = new JComboBox();
		panel_input_selection.add(comboBox, "4, 1, fill, default");
		
		JPanel panel_pads_head_rim = new JPanel();
		panel_pads.add(panel_pads_head_rim, "1, 3, fill, fill");
		panel_pads_head_rim.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_pads_head = new JPanel();
		panel_pads_head.setBorder(new TitledBorder(null, "Head/Bow", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pads_head_rim.add(panel_pads_head);
		panel_pads_head.setLayout(new GridLayout(0, 1, 0, 0));
		PadCommonControls padHeadControl = new PadCommonControls();
		panel_pads_head.add(padHeadControl);
		
		JPanel panel_pads_rim = new JPanel();
		panel_pads_rim.setBorder(new TitledBorder(null, "Rim/Edge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pads_head_rim.add(panel_pads_rim);
		panel_pads_rim.setLayout(new GridLayout(1, 0, 0, 0));
		
		PadCommonControls padCommonControls = new PadCommonControls();
		panel_pads_rim.add(padCommonControls);
		
		JPanel panel_3rd_zone = new JPanel();
		panel_3rd_zone.setBorder(new TitledBorder(null, "3rd Zone", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pads.add(panel_3rd_zone, "1, 4, fill, fill");
		panel_3rd_zone.setLayout(new BoxLayout(panel_3rd_zone, BoxLayout.X_AXIS));
		
		ThirdZoneControls thirdZoneControls = new ThirdZoneControls();
		panel_3rd_zone.add(thirdZoneControls);
		panel_misc.setLayout(null);
		
		JPanel panel_misc_noteoff = new JPanel();
		panel_misc_noteoff.setBounds(6, 22, 168, 30);
		panel_misc.add(panel_misc_noteoff);
		panel_misc_noteoff.setLayout(null);
		
		JLabel lblNoteoffDelay = new JLabel("NoteOff delay");
		lblNoteoffDelay.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblNoteoffDelay.setBounds(13, 7, 74, 16);
		panel_misc_noteoff.add(lblNoteoffDelay);
		
		spinner_noteoff = new JSpinner();
		spinner_noteoff.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_noteoff.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short value = ((Short)spinner_noteoff.getValue()).shortValue();
				midi_handler.config_misc.note_off = (short)(value/10);
				if (midi_handler.config_misc.note_off < midi_handler.config_misc.pressroll) {
					midi_handler.config_misc.pressroll = midi_handler.config_misc.note_off; 
				}
				spinner_pressroll.setModel(new SpinnerNumberModel(new Short((short) (midi_handler.config_misc.pressroll*10)), new Short((short) 0), new Short((short) (midi_handler.config_misc.note_off*10)), new Short((short) 10)));
			}
		});
		spinner_noteoff.setBounds(92, 5, 52, 20);
		spinner_noteoff.setModel(new SpinnerNumberModel(new Short((short) 200), new Short((short) 100), new Short((short) 2000), new Short((short) 10)));
		panel_misc_noteoff.add(spinner_noteoff);
		
		JPanel panel_misc_pressroll = new JPanel();
		panel_misc_pressroll.setLayout(null);
		panel_misc_pressroll.setBounds(6, 51, 168, 30);
		panel_misc.add(panel_misc_pressroll);
		
		JLabel lblPressrollTime = new JLabel("PressRoll");
		lblPressrollTime.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblPressrollTime.setBounds(13, 7, 74, 16);
		panel_misc_pressroll.add(lblPressrollTime);
		
		spinner_pressroll = new JSpinner();
		spinner_pressroll.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_pressroll.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short value = ((Short)spinner_pressroll.getValue()).shortValue();
				midi_handler.config_misc.pressroll = (short)(value/10);
			}
		});
		spinner_pressroll.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 2000), new Short((short) 10)));
		spinner_pressroll.setBounds(92, 5, 52, 20);
		panel_misc_pressroll.add(spinner_pressroll);
		
		JPanel panel_misc_latency = new JPanel();
		panel_misc_latency.setLayout(null);
		panel_misc_latency.setBounds(6, 79, 168, 30);
		panel_misc.add(panel_misc_latency);
		
		JLabel lblLatency = new JLabel("Latency");
		lblLatency.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblLatency.setBounds(13, 7, 74, 16);
		panel_misc_latency.add(lblLatency);
		
		spinner_latency = new JSpinner();
		spinner_latency.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_latency.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short value = ((Short)spinner_latency.getValue()).shortValue();
				midi_handler.config_misc.latency = (short)value;
			}
		});
		spinner_latency.setModel(new SpinnerNumberModel(new Short((short) 40), new Short((short) 10), new Short((short) 100), new Short((short) 1)));
		spinner_latency.setBounds(92, 5, 52, 20);
		panel_misc_latency.add(spinner_latency);
		
		JPanel panel_misc_flags = new JPanel();
		panel_misc_flags.setBounds(6, 109, 105, 115);
		panel_misc.add(panel_misc_flags);
		panel_misc_flags.setLayout(null);
		
		chckbxBigVuMeter = new JCheckBox("Big VU meter");
		chckbxBigVuMeter.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		chckbxBigVuMeter.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				midi_handler.config_misc.big_vu_meter = chckbxBigVuMeter.isSelected();
			}
		});
		chckbxBigVuMeter.setBounds(0, 0, 105, 25);
		panel_misc_flags.add(chckbxBigVuMeter);
		
		chckbxQuickAccess = new JCheckBox("Quick Access");
		chckbxQuickAccess.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		chckbxQuickAccess.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				midi_handler.config_misc.quick_access = chckbxQuickAccess.isSelected();
			}
		});
		chckbxQuickAccess.setBounds(0, 21, 105, 25);
		panel_misc_flags.add(chckbxQuickAccess);
		
		chckbxAltFalseTrig = new JCheckBox("Alt False Trig");
		chckbxAltFalseTrig.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		chckbxAltFalseTrig.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				midi_handler.config_misc.alt_false_tr_supp = chckbxAltFalseTrig.isSelected();
			}
		});
		chckbxAltFalseTrig.setBounds(0, 43, 105, 25);
		panel_misc_flags.add(chckbxAltFalseTrig);
		
		chckbxInputsPriority = new JCheckBox("Inputs Priority");
		chckbxInputsPriority.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		chckbxInputsPriority.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				midi_handler.config_misc.inputs_priority = chckbxInputsPriority.isSelected();
			}
		});
		chckbxInputsPriority.setBounds(0, 65, 105, 25);
		panel_misc_flags.add(chckbxInputsPriority);
		
		chckbxAllGainsLow = new JCheckBox("All Gains Low");
		chckbxAllGainsLow.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		chckbxAllGainsLow.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				midi_handler.config_misc.all_gains_low = chckbxAllGainsLow.isSelected();
			}
		});
		chckbxAllGainsLow.setBounds(0, 87, 105, 25);
		panel_misc_flags.add(chckbxAllGainsLow);
		
		JButton btnGet_misc = new JButton("Get");
		btnGet_misc.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		btnGet_misc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.clear_midi_input();
				midi_handler.request_config_misc();
			}
		});
		btnGet_misc.setBounds(52, 243, 59, 25);
		panel_misc.add(btnGet_misc);
		
		JButton btnSend_misc = new JButton("Send");
		btnSend_misc.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		btnSend_misc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.send_config_misc();
			}
		});
		btnSend_misc.setBounds(115, 243, 59, 25);
		panel_misc.add(btnSend_misc);

		frmMegadrummanager.getContentPane().setLayout(groupLayout);
		
		update_misc_config_controls();
	}
	
	public void update_misc_config_controls() {
		spinner_noteoff.setValue((short)(midi_handler.config_misc.note_off*10));
		spinner_pressroll.setModel(new SpinnerNumberModel(new Short((short) (midi_handler.config_misc.pressroll*10)), new Short((short) 0), new Short((short) (midi_handler.config_misc.note_off*10)), new Short((short) 10)));
		spinner_latency.setValue((short)(midi_handler.config_misc.latency));
		chckbxBigVuMeter.setSelected(midi_handler.config_misc.big_vu_meter);
		chckbxQuickAccess.setSelected(midi_handler.config_misc.quick_access);
		chckbxAltFalseTrig.setSelected(midi_handler.config_misc.alt_false_tr_supp);
		chckbxInputsPriority.setSelected(midi_handler.config_misc.inputs_priority);
		chckbxAllGainsLow.setSelected(midi_handler.config_misc.all_gains_low);
	}
}

