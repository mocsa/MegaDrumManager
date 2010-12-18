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
		frmMegadrummanager.setBounds(100, 100, 786, 656);
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
		
		JPanel panel_83 = new JPanel();
		panel_83.setBorder(new TitledBorder(null, "HiHat Pedal/Controller", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(frmMegadrummanager.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_misc, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_83, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(337, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_misc, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_83, GroupLayout.PREFERRED_SIZE, 382, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(225, Short.MAX_VALUE))
		);
		panel_83.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 23, 236, 318);
		panel_83.add(tabbedPane);
		
		JPanel panel_65 = new JPanel();
		tabbedPane.addTab("Misc", null, panel_65, null);
		panel_65.setLayout(null);
		
		JPanel panel_hh_type = new JPanel();
		panel_hh_type.setBounds(12, 0, 202, 18);
		panel_65.add(panel_hh_type);
		panel_hh_type.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 80, 18);
		panel_hh_type.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblType);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(92, 0, 110, 18);
		panel_hh_type.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JComboBox comboBoxHHtype = new JComboBox();
		panel_1.add(comboBoxHHtype, BorderLayout.CENTER);
		comboBoxHHtype.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		comboBoxHHtype.addItem("FootContr");
		comboBoxHHtype.addItem("Pot");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(12, 20, 202, 18);
		panel_65.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 80, 18);
		panel_2.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCurve = new JLabel("Curve");
		lblCurve.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_3.add(lblCurve, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(92, 0, 110, 18);
		panel_2.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JComboBox comboBoxHHcurve = new JComboBox();
		comboBoxHHcurve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		for (String string : Constants.CURVES_LIST) {
			   comboBoxHHcurve.addItem(string);
			}
		panel_4.add(comboBoxHHcurve, BorderLayout.CENTER);
		
		JPanel panel_35 = new JPanel();
		panel_35.setBounds(12, 40, 202, 18);
		panel_65.add(panel_35);
		panel_35.setLayout(null);
		
		JPanel panel_36 = new JPanel();
		panel_36.setBounds(0, 0, 80, 18);
		panel_35.add(panel_36);
		panel_36.setLayout(new BorderLayout(0, 0));
		
		JLabel lblHihatInput = new JLabel("HiHat Input");
		lblHihatInput.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHihatInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_36.add(lblHihatInput, BorderLayout.EAST);
		
		JPanel panel_37 = new JPanel();
		panel_37.setBounds(92, 0, 110, 18);
		panel_35.add(panel_37);
		panel_37.setLayout(new BorderLayout(0, 0));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_37.add(comboBox, BorderLayout.CENTER);
		
		JCheckBox checkBoxHHaltIn = new JCheckBox("Alt Input   ");
		checkBoxHHaltIn.setBounds(49, 60, 104, 18);
		panel_65.add(checkBoxHHaltIn);
		checkBoxHHaltIn.setHorizontalTextPosition(SwingConstants.LEADING);
		checkBoxHHaltIn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		
		JCheckBox checkBoxHHreverseLevels = new JCheckBox("Reverse Levels   ");
		checkBoxHHreverseLevels.setBounds(25, 80, 104, 18);
		panel_65.add(checkBoxHHreverseLevels);
		checkBoxHHreverseLevels.setHorizontalTextPosition(SwingConstants.LEADING);
		checkBoxHHreverseLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		
		JCheckBox chckbxSoftChicks = new JCheckBox("Soft Chicks   ");
		chckbxSoftChicks.setBounds(39, 100, 88, 18);
		panel_65.add(chckbxSoftChicks);
		chckbxSoftChicks.setHorizontalTextPosition(SwingConstants.LEADING);
		chckbxSoftChicks.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		
		JCheckBox chckbxAutoLevels = new JCheckBox("Auto Levels   ");
		chckbxAutoLevels.setBounds(37, 120, 92, 18);
		panel_65.add(chckbxAutoLevels);
		chckbxAutoLevels.setHorizontalTextPosition(SwingConstants.LEADING);
		chckbxAutoLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(12, 140, 202, 18);
		panel_65.add(panel_5);
		panel_5.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(0, 0, 80, 18);
		panel_5.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JLabel lblChickDelay = new JLabel("Chick delay");
		lblChickDelay.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_6.add(lblChickDelay, BorderLayout.EAST);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(92, 0, 110, 18);
		panel_5.add(panel_7);
		panel_7.setLayout(null);
		
		JSpinner spinnerHHchickDelay = new JSpinner();
		spinnerHHchickDelay.setBounds(0, 0, 44, 18);
		panel_7.add(spinnerHHchickDelay);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBounds(12, 160, 202, 18);
		panel_65.add(panel_11);
		panel_11.setLayout(null);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBounds(0, 0, 80, 18);
		panel_11.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCcValue = new JLabel("CC Value");
		lblCcValue.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_12.add(lblCcValue, BorderLayout.EAST);
		
		JPanel panel_13 = new JPanel();
		panel_13.setLayout(null);
		panel_13.setBounds(92, 0, 110, 18);
		panel_11.add(panel_13);
		
		JSpinner spinnerHHccValue = new JSpinner();
		spinnerHHccValue.setBounds(0, 0, 44, 18);
		panel_13.add(spinnerHHccValue);
		
		JPanel panel_66 = new JPanel();
		tabbedPane.addTab("Levels", null, panel_66, null);
		panel_66.setLayout(null);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(12, 0, 202, 18);
		panel_66.add(panel_8);
		panel_8.setLayout(null);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBounds(0, 0, 80, 18);
		panel_8.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JLabel lblLowLevel = new JLabel("Low");
		lblLowLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_9.add(lblLowLevel, BorderLayout.EAST);
		
		JPanel panel_10 = new JPanel();
		panel_10.setLayout(null);
		panel_10.setBounds(92, 0, 110, 18);
		panel_8.add(panel_10);
		
		JSpinner spinnerHHlowLEvel = new JSpinner();
		spinnerHHlowLEvel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinnerHHlowLEvel.setBounds(0, 0, 44, 18);
		panel_10.add(spinnerHHlowLEvel);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBounds(12, 20, 202, 18);
		panel_66.add(panel_14);
		panel_14.setLayout(null);
		
		JPanel panel_15 = new JPanel();
		panel_15.setBounds(0, 0, 80, 18);
		panel_14.add(panel_15);
		panel_15.setLayout(new BorderLayout(0, 0));
		
		JLabel lblHighLevel = new JLabel("High");
		lblHighLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_15.add(lblHighLevel, BorderLayout.EAST);
		
		JPanel panel_16 = new JPanel();
		panel_16.setLayout(null);
		panel_16.setBounds(92, 0, 110, 18);
		panel_14.add(panel_16);
		
		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner.setBounds(0, 0, 44, 18);
		panel_16.add(spinner);
		
		JPanel panel_17 = new JPanel();
		panel_17.setBounds(12, 40, 202, 18);
		panel_66.add(panel_17);
		panel_17.setLayout(null);
		
		JPanel panel_18 = new JPanel();
		panel_18.setBounds(0, 0, 80, 18);
		panel_17.add(panel_18);
		panel_18.setLayout(new BorderLayout(0, 0));
		
		JLabel lblOpenCcLevel = new JLabel("Open");
		lblOpenCcLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_18.add(lblOpenCcLevel, BorderLayout.EAST);
		
		JPanel panel_19 = new JPanel();
		panel_19.setLayout(null);
		panel_19.setBounds(92, 0, 110, 18);
		panel_17.add(panel_19);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_1.setBounds(0, 0, 44, 18);
		panel_19.add(spinner_1);
		
		JPanel panel_20 = new JPanel();
		panel_20.setBounds(12, 60, 202, 18);
		panel_66.add(panel_20);
		panel_20.setLayout(null);
		
		JPanel panel_21 = new JPanel();
		panel_21.setBounds(0, 0, 80, 18);
		panel_20.add(panel_21);
		panel_21.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSemiOpenLevel = new JLabel("Semi Open");
		lblSemiOpenLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_21.add(lblSemiOpenLevel, BorderLayout.EAST);
		
		JPanel panel_22 = new JPanel();
		panel_22.setLayout(null);
		panel_22.setBounds(92, 0, 110, 18);
		panel_20.add(panel_22);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_2.setBounds(0, 0, 44, 18);
		panel_22.add(spinner_2);
		
		JPanel panel_23 = new JPanel();
		panel_23.setBounds(12, 80, 202, 18);
		panel_66.add(panel_23);
		panel_23.setLayout(null);
		
		JPanel panel_24 = new JPanel();
		panel_24.setBounds(0, 0, 80, 18);
		panel_23.add(panel_24);
		panel_24.setLayout(new BorderLayout(0, 0));
		
		JLabel lblHopenLevel = new JLabel("Half Open");
		lblHopenLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_24.add(lblHopenLevel, BorderLayout.EAST);
		
		JPanel panel_25 = new JPanel();
		panel_25.setLayout(null);
		panel_25.setBounds(92, 0, 110, 18);
		panel_23.add(panel_25);
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_3.setBounds(0, 0, 44, 18);
		panel_25.add(spinner_3);
		
		JPanel panel_26 = new JPanel();
		panel_26.setBounds(12, 100, 202, 18);
		panel_66.add(panel_26);
		panel_26.setLayout(null);
		
		JPanel panel_27 = new JPanel();
		panel_27.setBounds(0, 0, 80, 18);
		panel_26.add(panel_27);
		panel_27.setLayout(new BorderLayout(0, 0));
		
		JLabel lblClosedLevel = new JLabel("Closed");
		lblClosedLevel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_27.add(lblClosedLevel, BorderLayout.EAST);
		
		JPanel panel_28 = new JPanel();
		panel_28.setLayout(null);
		panel_28.setBounds(92, 0, 110, 18);
		panel_26.add(panel_28);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_4.setBounds(0, 0, 44, 18);
		panel_28.add(spinner_4);
		
		JPanel panel_29 = new JPanel();
		panel_29.setBounds(12, 120, 202, 18);
		panel_66.add(panel_29);
		panel_29.setLayout(null);
		
		JPanel panel_30 = new JPanel();
		panel_30.setBounds(0, 0, 80, 18);
		panel_29.add(panel_30);
		panel_30.setLayout(new BorderLayout(0, 0));
		
		JLabel lblShrtchThres = new JLabel("Short Chick Thres");
		lblShrtchThres.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_30.add(lblShrtchThres, BorderLayout.EAST);
		
		JPanel panel_31 = new JPanel();
		panel_31.setLayout(null);
		panel_31.setBounds(92, 0, 110, 18);
		panel_29.add(panel_31);
		
		JSpinner spinner_5 = new JSpinner();
		spinner_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_5.setBounds(0, 0, 44, 18);
		panel_31.add(spinner_5);
		
		JPanel panel_32 = new JPanel();
		panel_32.setBounds(12, 140, 202, 18);
		panel_66.add(panel_32);
		panel_32.setLayout(null);
		
		JPanel panel_33 = new JPanel();
		panel_33.setBounds(0, 0, 80, 18);
		panel_32.add(panel_33);
		panel_33.setLayout(new BorderLayout(0, 0));
		
		JLabel lblLngchThres = new JLabel("Long Chick Thres");
		lblLngchThres.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_33.add(lblLngchThres, BorderLayout.EAST);
		
		JPanel panel_34 = new JPanel();
		panel_34.setLayout(null);
		panel_34.setBounds(92, 0, 110, 18);
		panel_32.add(panel_34);
		
		JSpinner spinner_6 = new JSpinner();
		spinner_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_6.setBounds(0, 0, 44, 18);
		panel_34.add(spinner_6);
		
		JPanel panel_67 = new JPanel();
		tabbedPane.addTab("Notes", null, panel_67, null);
		panel_67.setLayout(null);
		
		JPanel panel_38 = new JPanel();
		panel_38.setBounds(12, 0, 202, 18);
		panel_67.add(panel_38);
		panel_38.setLayout(null);
		
		JPanel panel_39 = new JPanel();
		panel_39.setBounds(0, 0, 80, 18);
		panel_38.add(panel_39);
		panel_39.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBowSopen = new JLabel("Bow SemiOpen ");
		lblBowSopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_39.add(lblBowSopen, BorderLayout.EAST);
		
		JPanel panel_40 = new JPanel();
		panel_40.setLayout(null);
		panel_40.setBounds(92, 0, 110, 18);
		panel_38.add(panel_40);
		
		JSpinner spinner_7 = new JSpinner();
		spinner_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_7.setBounds(0, 0, 44, 18);
		panel_40.add(spinner_7);
		
		JPanel panel_41 = new JPanel();
		panel_41.setBounds(12, 20, 202, 18);
		panel_67.add(panel_41);
		panel_41.setLayout(null);
		
		JPanel panel_42 = new JPanel();
		panel_42.setBounds(0, 0, 80, 18);
		panel_41.add(panel_42);
		panel_42.setLayout(new BorderLayout(0, 0));
		
		JLabel lblEdgeSemiopen = new JLabel("Edge SemiOpen ");
		lblEdgeSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_42.add(lblEdgeSemiopen, BorderLayout.EAST);
		
		JPanel panel_43 = new JPanel();
		panel_43.setLayout(null);
		panel_43.setBounds(92, 0, 110, 18);
		panel_41.add(panel_43);
		
		JSpinner spinner_8 = new JSpinner();
		spinner_8.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_8.setBounds(0, 0, 44, 18);
		panel_43.add(spinner_8);
		
		JPanel panel_44 = new JPanel();
		panel_44.setBounds(12, 40, 202, 18);
		panel_67.add(panel_44);
		panel_44.setLayout(null);
		
		JPanel panel_45 = new JPanel();
		panel_45.setBounds(0, 0, 80, 18);
		panel_44.add(panel_45);
		panel_45.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBellSemiopen = new JLabel("Bell SemiOpen ");
		lblBellSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_45.add(lblBellSemiopen, BorderLayout.EAST);
		
		JPanel panel_46 = new JPanel();
		panel_46.setLayout(null);
		panel_46.setBounds(92, 0, 110, 18);
		panel_44.add(panel_46);
		
		JSpinner spinner_9 = new JSpinner();
		spinner_9.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_9.setBounds(0, 0, 44, 18);
		panel_46.add(spinner_9);
		
		JPanel panel_47 = new JPanel();
		panel_47.setBounds(12, 60, 202, 18);
		panel_67.add(panel_47);
		panel_47.setLayout(null);
		
		JPanel panel_48 = new JPanel();
		panel_48.setBounds(0, 0, 80, 18);
		panel_47.add(panel_48);
		panel_48.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBowHalfopen = new JLabel("Bow HalfOpen ");
		lblBowHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_48.add(lblBowHalfopen, BorderLayout.EAST);
		
		JPanel panel_49 = new JPanel();
		panel_49.setLayout(null);
		panel_49.setBounds(92, 0, 110, 18);
		panel_47.add(panel_49);
		
		JSpinner spinner_10 = new JSpinner();
		spinner_10.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_10.setBounds(0, 0, 44, 18);
		panel_49.add(spinner_10);
		
		JPanel panel_50 = new JPanel();
		panel_50.setBounds(12, 80, 202, 18);
		panel_67.add(panel_50);
		panel_50.setLayout(null);
		
		JPanel panel_51 = new JPanel();
		panel_51.setBounds(0, 0, 80, 18);
		panel_50.add(panel_51);
		panel_51.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBowHalfopen_1 = new JLabel("Edge HalfOpen ");
		lblBowHalfopen_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_51.add(lblBowHalfopen_1, BorderLayout.EAST);
		
		JPanel panel_52 = new JPanel();
		panel_52.setLayout(null);
		panel_52.setBounds(92, 0, 110, 18);
		panel_50.add(panel_52);
		
		JSpinner spinner_11 = new JSpinner();
		spinner_11.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_11.setBounds(0, 0, 44, 18);
		panel_52.add(spinner_11);
		
		JPanel panel_53 = new JPanel();
		panel_53.setBounds(12, 100, 202, 18);
		panel_67.add(panel_53);
		panel_53.setLayout(null);
		
		JPanel panel_54 = new JPanel();
		panel_54.setBounds(0, 0, 80, 18);
		panel_53.add(panel_54);
		panel_54.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBowHalfopen_2 = new JLabel("Bell HalfOpen ");
		lblBowHalfopen_2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_54.add(lblBowHalfopen_2, BorderLayout.EAST);
		
		JPanel panel_55 = new JPanel();
		panel_55.setLayout(null);
		panel_55.setBounds(92, 0, 110, 18);
		panel_53.add(panel_55);
		
		JSpinner spinner_12 = new JSpinner();
		spinner_12.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_12.setBounds(0, 0, 44, 18);
		panel_55.add(spinner_12);
		
		JPanel panel_56 = new JPanel();
		panel_56.setBounds(12, 120, 202, 18);
		panel_67.add(panel_56);
		panel_56.setLayout(null);
		
		JPanel panel_57 = new JPanel();
		panel_57.setBounds(0, 0, 80, 18);
		panel_56.add(panel_57);
		panel_57.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBowSemiopen = new JLabel("Bow SemiClosed ");
		lblBowSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_57.add(lblBowSemiopen, BorderLayout.EAST);
		
		JPanel panel_58 = new JPanel();
		panel_58.setLayout(null);
		panel_58.setBounds(92, 0, 110, 18);
		panel_56.add(panel_58);
		
		JSpinner spinner_13 = new JSpinner();
		spinner_13.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_13.setBounds(0, 0, 44, 18);
		panel_58.add(spinner_13);
		
		JPanel panel_59 = new JPanel();
		panel_59.setBounds(12, 140, 202, 18);
		panel_67.add(panel_59);
		panel_59.setLayout(null);
		
		JPanel panel_60 = new JPanel();
		panel_60.setBounds(0, 0, 80, 18);
		panel_59.add(panel_60);
		panel_60.setLayout(new BorderLayout(0, 0));
		
		JLabel lblEdgeSemiclosed = new JLabel("Edge SemiClosed ");
		lblEdgeSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_60.add(lblEdgeSemiclosed, BorderLayout.EAST);
		
		JPanel panel_61 = new JPanel();
		panel_61.setLayout(null);
		panel_61.setBounds(92, 0, 110, 18);
		panel_59.add(panel_61);
		
		JSpinner spinner_14 = new JSpinner();
		spinner_14.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_14.setBounds(0, 0, 44, 18);
		panel_61.add(spinner_14);
		
		JPanel panel_62 = new JPanel();
		panel_62.setBounds(12, 160, 202, 18);
		panel_67.add(panel_62);
		panel_62.setLayout(null);
		
		JPanel panel_63 = new JPanel();
		panel_63.setBounds(0, 0, 80, 18);
		panel_62.add(panel_63);
		panel_63.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBellHalfopen = new JLabel("Bell SemiClosed ");
		lblBellHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_63.add(lblBellHalfopen, BorderLayout.EAST);
		
		JPanel panel_64 = new JPanel();
		panel_64.setLayout(null);
		panel_64.setBounds(92, 0, 110, 18);
		panel_62.add(panel_64);
		
		JSpinner spinner_15 = new JSpinner();
		spinner_15.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_15.setBounds(0, 0, 44, 18);
		panel_64.add(spinner_15);
		
		JPanel panel_68 = new JPanel();
		panel_68.setLayout(null);
		panel_68.setBounds(12, 180, 202, 18);
		panel_67.add(panel_68);
		
		JPanel panel_69 = new JPanel();
		panel_69.setBounds(0, 0, 80, 18);
		panel_68.add(panel_69);
		panel_69.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBowClosed = new JLabel("Bow Closed ");
		lblBowClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_69.add(lblBowClosed, BorderLayout.EAST);
		
		JPanel panel_70 = new JPanel();
		panel_70.setLayout(null);
		panel_70.setBounds(92, 0, 110, 18);
		panel_68.add(panel_70);
		
		JSpinner spinner_16 = new JSpinner();
		spinner_16.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_16.setBounds(0, 0, 44, 18);
		panel_70.add(spinner_16);
		
		JPanel panel_71 = new JPanel();
		panel_71.setLayout(null);
		panel_71.setBounds(12, 200, 202, 18);
		panel_67.add(panel_71);
		
		JPanel panel_72 = new JPanel();
		panel_72.setBounds(0, 0, 80, 18);
		panel_71.add(panel_72);
		panel_72.setLayout(new BorderLayout(0, 0));
		
		JLabel lblEdgeClosed = new JLabel("Edge Closed ");
		lblEdgeClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_72.add(lblEdgeClosed, BorderLayout.EAST);
		
		JPanel panel_73 = new JPanel();
		panel_73.setLayout(null);
		panel_73.setBounds(92, 0, 110, 18);
		panel_71.add(panel_73);
		
		JSpinner spinner_17 = new JSpinner();
		spinner_17.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_17.setBounds(0, 0, 44, 18);
		panel_73.add(spinner_17);
		
		JPanel panel_74 = new JPanel();
		panel_74.setLayout(null);
		panel_74.setBounds(12, 220, 202, 18);
		panel_67.add(panel_74);
		
		JPanel panel_75 = new JPanel();
		panel_75.setBounds(0, 0, 80, 18);
		panel_74.add(panel_75);
		panel_75.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBellClosed = new JLabel("Bell Closed ");
		lblBellClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_75.add(lblBellClosed, BorderLayout.EAST);
		
		JPanel panel_76 = new JPanel();
		panel_76.setLayout(null);
		panel_76.setBounds(92, 0, 110, 18);
		panel_74.add(panel_76);
		
		JSpinner spinner_18 = new JSpinner();
		spinner_18.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_18.setBounds(0, 0, 44, 18);
		panel_76.add(spinner_18);
		
		JPanel panel_77 = new JPanel();
		panel_77.setLayout(null);
		panel_77.setBounds(12, 240, 202, 18);
		panel_67.add(panel_77);
		
		JPanel panel_78 = new JPanel();
		panel_78.setBounds(0, 0, 80, 18);
		panel_77.add(panel_78);
		panel_78.setLayout(new BorderLayout(0, 0));
		
		JLabel lblChick = new JLabel("Chick ");
		lblChick.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_78.add(lblChick, BorderLayout.EAST);
		
		JPanel panel_79 = new JPanel();
		panel_79.setLayout(null);
		panel_79.setBounds(92, 0, 110, 18);
		panel_77.add(panel_79);
		
		JSpinner spinner_19 = new JSpinner();
		spinner_19.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_19.setBounds(0, 0, 44, 18);
		panel_79.add(spinner_19);
		
		JPanel panel_80 = new JPanel();
		panel_80.setLayout(null);
		panel_80.setBounds(12, 260, 202, 18);
		panel_67.add(panel_80);
		
		JPanel panel_81 = new JPanel();
		panel_81.setBounds(0, 0, 80, 18);
		panel_80.add(panel_81);
		panel_81.setLayout(new BorderLayout(0, 0));
		
		JLabel lblBlClosed = new JLabel("Splash ");
		lblBlClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_81.add(lblBlClosed, BorderLayout.EAST);
		
		JPanel panel_82 = new JPanel();
		panel_82.setLayout(null);
		panel_82.setBounds(92, 0, 110, 18);
		panel_80.add(panel_82);
		
		JSpinner spinner_20 = new JSpinner();
		spinner_20.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_20.setBounds(0, 0, 44, 18);
		panel_82.add(spinner_20);
		
		JButton button = new JButton("Get");
		button.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		button.setBounds(122, 354, 59, 25);
		panel_83.add(button);
		
		JButton button_1 = new JButton("Send");
		button_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		button_1.setBounds(184, 354, 59, 25);
		panel_83.add(button_1);
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

