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
					.addContainerGap(130, Short.MAX_VALUE))
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
		panel_67.setLayout(new GridLayout(15, 1, 0, 0));
		
		NoteControl noteControl = new NoteControl();
		GridBagLayout gridBagLayout = (GridBagLayout) noteControl.getLayout();
		gridBagLayout.rowWeights = new double[]{0.0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.columnWidths = new int[]{112, 45, 84};
		noteControl.setLabelText("Bow SemiOpen");
		panel_67.add(noteControl);
		
		NoteControl noteControl_1 = new NoteControl();
		GridBagLayout gridBagLayout_1 = (GridBagLayout) noteControl_1.getLayout();
		gridBagLayout_1.rowWeights = new double[]{0.0};
		gridBagLayout_1.rowHeights = new int[]{0};
		gridBagLayout_1.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_1.columnWidths = new int[]{112, 45, 84};
		noteControl_1.setLabelText("Edge SemiOpen");
		panel_67.add(noteControl_1);
		
		NoteControl noteControl_2 = new NoteControl();
		GridBagLayout gridBagLayout_2 = (GridBagLayout) noteControl_2.getLayout();
		gridBagLayout_2.rowWeights = new double[]{0.0};
		gridBagLayout_2.rowHeights = new int[]{0};
		gridBagLayout_2.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_2.columnWidths = new int[]{112, 45, 84};
		noteControl_2.setLabelText("Bell SemiOpen");
		panel_67.add(noteControl_2);
		
		NoteControl noteControl_3 = new NoteControl();
		GridBagLayout gridBagLayout_3 = (GridBagLayout) noteControl_3.getLayout();
		gridBagLayout_3.rowWeights = new double[]{0.0};
		gridBagLayout_3.rowHeights = new int[]{0};
		gridBagLayout_3.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_3.columnWidths = new int[]{112, 45, 84};
		noteControl_3.setLabelText("Bow HalfOpen");
		panel_67.add(noteControl_3);
		
		NoteControl noteControl_4 = new NoteControl();
		GridBagLayout gridBagLayout_4 = (GridBagLayout) noteControl_4.getLayout();
		gridBagLayout_4.rowWeights = new double[]{0.0};
		gridBagLayout_4.rowHeights = new int[]{0};
		gridBagLayout_4.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_4.columnWidths = new int[]{112, 45, 84};
		noteControl_4.setLabelText("Edge HalfOpen");
		panel_67.add(noteControl_4);
		
		NoteControl noteControl_5 = new NoteControl();
		GridBagLayout gridBagLayout_5 = (GridBagLayout) noteControl_5.getLayout();
		gridBagLayout_5.rowWeights = new double[]{0.0};
		gridBagLayout_5.rowHeights = new int[]{0};
		gridBagLayout_5.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_5.columnWidths = new int[]{112, 45, 84};
		noteControl_5.setLabelText("Bell HalfOpen");
		panel_67.add(noteControl_5);
		
		NoteControl noteControl_6 = new NoteControl();
		GridBagLayout gridBagLayout_6 = (GridBagLayout) noteControl_6.getLayout();
		gridBagLayout_6.rowWeights = new double[]{0.0};
		gridBagLayout_6.rowHeights = new int[]{0};
		gridBagLayout_6.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_6.columnWidths = new int[]{112, 45, 84};
		noteControl_6.setLabelText("Bow SemiClosed");
		panel_67.add(noteControl_6);
		
		NoteControl noteControl_7 = new NoteControl();
		GridBagLayout gridBagLayout_7 = (GridBagLayout) noteControl_7.getLayout();
		gridBagLayout_7.rowWeights = new double[]{0.0};
		gridBagLayout_7.rowHeights = new int[]{0};
		gridBagLayout_7.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_7.columnWidths = new int[]{112, 45, 84};
		noteControl_7.setLabelText("Edge SemiClosed");
		panel_67.add(noteControl_7);
		
		NoteControl noteControl_8 = new NoteControl();
		GridBagLayout gridBagLayout_8 = (GridBagLayout) noteControl_8.getLayout();
		gridBagLayout_8.rowWeights = new double[]{0.0};
		gridBagLayout_8.rowHeights = new int[]{0};
		gridBagLayout_8.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_8.columnWidths = new int[]{112, 45, 84};
		noteControl_8.setLabelText("Bell SemiClosed");
		panel_67.add(noteControl_8);
		
		NoteControl noteControl_9 = new NoteControl();
		GridBagLayout gridBagLayout_9 = (GridBagLayout) noteControl_9.getLayout();
		gridBagLayout_9.rowWeights = new double[]{0.0};
		gridBagLayout_9.rowHeights = new int[]{0};
		gridBagLayout_9.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_9.columnWidths = new int[]{112, 45, 84};
		noteControl_9.setLabelText("Bow Closed");
		panel_67.add(noteControl_9);
		
		NoteControl noteControl_10 = new NoteControl();
		GridBagLayout gridBagLayout_10 = (GridBagLayout) noteControl_10.getLayout();
		gridBagLayout_10.rowWeights = new double[]{0.0};
		gridBagLayout_10.rowHeights = new int[]{0};
		gridBagLayout_10.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_10.columnWidths = new int[]{112, 45, 84};
		noteControl_10.setLabelText("Edge Closed");
		panel_67.add(noteControl_10);
		
		NoteControl noteControl_11 = new NoteControl();
		GridBagLayout gridBagLayout_11 = (GridBagLayout) noteControl_11.getLayout();
		gridBagLayout_11.rowWeights = new double[]{0.0};
		gridBagLayout_11.rowHeights = new int[]{0};
		gridBagLayout_11.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_11.columnWidths = new int[]{112, 45, 84};
		noteControl_11.setLabelText("Bell Closed");
		panel_67.add(noteControl_11);
		
		NoteControl noteControl_12 = new NoteControl();
		GridBagLayout gridBagLayout_12 = (GridBagLayout) noteControl_12.getLayout();
		gridBagLayout_12.rowWeights = new double[]{0.0};
		gridBagLayout_12.rowHeights = new int[]{0};
		gridBagLayout_12.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_12.columnWidths = new int[]{112, 45, 84};
		noteControl_12.setLabelText("Chick");
		panel_67.add(noteControl_12);
		
		NoteControl noteControl_13 = new NoteControl();
		GridBagLayout gridBagLayout_13 = (GridBagLayout) noteControl_13.getLayout();
		gridBagLayout_13.rowWeights = new double[]{0.0};
		gridBagLayout_13.rowHeights = new int[]{0};
		gridBagLayout_13.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout_13.columnWidths = new int[]{112, 45, 84};
		noteControl_13.setLabelText("Splash");
		panel_67.add(noteControl_13);
		
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

