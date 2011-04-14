package gui;

import java.awt.EventQueue;

import javax.swing.ComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;

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

import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;

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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JProgressBar;
import javax.swing.JCheckBoxMenuItem;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.WindowFocusListener;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class Main_window {

	private JFrame frmMegadrummanager;
	private JPanel panel_main;
	private JPanel panel_top;
	private Options dialog_options;
	private Upgrade upgradeDialog;
	private Midi_handler midi_handler;
	private ControlsMisc controlsMisc;
	private ControlsPedal controlsPedal;
	private ControlsPads controlsPads;
	private ControlsCurves controlsCurves;
	private PanelMidiLog panelMidiLog;
	private FrameDetached [] framesDetached;
	private JPanel [] controlsPanels;
	private ViewMenu [] viewMenus;
	//private ConfigFull configFull;
	private ConfigFull [] fullConfigs;
	private ConfigOptions configOptions;
	private FileManager fileManager;
	//private int chainId;
	private JMenuItem menuItem;
	private JMenu mnView;
	private JProgressBar progressBar;
	private JComboBox comboBox_inputsCount;
	private boolean resizeWindow = true;
	private JToggleButton tglbtnMidi;
	private JLabel lblVersion;
	private JToggleButton tglbtnLiveUpdates;
	private JComboBox comboBoxCfg;
	//private int configPointer = 0;
	//private String [] configsStrings;
	

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/*
			        System.out.println(System.getProperty("os.name")
			                + " " + System.getProperty("os.version")
			                + " " + System.getProperty("java.version"));
			            UIManager.LookAndFeelInfo[] lfa =
			                UIManager.getInstalledLookAndFeels();
			            for (UIManager.LookAndFeelInfo lf : lfa) {
			                UIManager.setLookAndFeel(lf.getClassName());
			                UIDefaults uid = UIManager.getLookAndFeelDefaults();
			                System.out.println("***"
			                    + " " + lf.getName()
			                    + " " + lf.getClassName()
			                    + " " + uid.size() + " entries");
			            }
			        */
					//UIDefaults defaults = UIManager.getDefaults();
					//defaults.put("JComponent.sizeVariant", "regular");
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
		loadConfig();
		resizeMainWindow();
	}
	
	private void open_options_dialog() {
		
		dialog_options.config_applied = false;
		dialog_options.setVisible(true);
		if (dialog_options.config_applied) {
			dialog_options.saveOptionsTo(configOptions);
			midi_handler.initPorts(configOptions);
		}
		toggleMidiOpenButton();
	}
	
//	public static void show_error(String msg) {
//		JOptionPane.showMessageDialog(null,
//			    msg,
//			    "Error",
//			    JOptionPane.ERROR_MESSAGE);
//	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMegadrummanager = new JFrame();
		frmMegadrummanager.setIconImage(Toolkit.getDefaultToolkit().getImage(Main_window.class.getResource("/icons/megadrum-manager128.png")));
		frmMegadrummanager.setResizable(false);
		frmMegadrummanager.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				saveAndExit();
			}
		});
		frmMegadrummanager.setTitle("MegaDrumManager");
		frmMegadrummanager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		configOptions = new ConfigOptions(); // default options loaded with new
		fullConfigs = new ConfigFull[Constants.CONFIGS_COUNT];
		for (Integer i = 1;i<=Constants.CONFIGS_COUNT;i++) {
			fullConfigs[i-1] = new ConfigFull();
		}
		fileManager = new FileManager(frmMegadrummanager);
		midi_handler = new Midi_handler();
		dialog_options = new Options(midi_handler);
		SwingUtilities.updateComponentTreeUI(dialog_options);
		dialog_options.pack();
		dialog_options.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getPropertyName().equals("UIchanged")) {
	        		try {
						UIManager.setLookAndFeel(dialog_options.lookAndFeel.getClassName());
					} catch (Exception e) {
						Utils.show_error("Error setting LookAndFeel. Exiting.\n" +
								"(" + e.getMessage() + ")");
						System.exit(1);
					}
					resizeMainWindow();
					SwingUtilities.updateComponentTreeUI(dialog_options);
					dialog_options.pack();
				}
			}
		});

		
		upgradeDialog = new Upgrade(midi_handler, fileManager);
		midi_handler.chainId = 0;
		midi_handler.addMidiEventListener(new MidiEventListener() {
			@Override
			public void midiEventOccurred(MidiEvent evt) {
				if (!upgradeDialog.isVisible()) {
					midi_handler.getMidi();
					if (midi_handler.sysexReceived) {
						midi_handler.sysexReceived = false;
						decodeSysex(midi_handler.bufferIn);						
					} else if (midi_handler.bufferIn != null) {
						decodeShortMidi(midi_handler.bufferIn);
					}
					midi_handler.bufferIn = null;
				}
			}
		});		
		
		JMenuBar menuBar = new JMenuBar();
		frmMegadrummanager.setJMenuBar(menuBar);
		
		JMenu mnMain = new JMenu("Main");
		menuBar.add(mnMain);
		
		JMenu mnLoad = new JMenu("All settings");
		mnMain.add(mnLoad);
		
		JMenuItem mntmLoadFromMd = new JMenuItem("Load from MD");
		mntmLoadFromMd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAll();
			}
		});
		mnLoad.add(mntmLoadFromMd);
		
		JMenuItem mntmSendToMd = new JMenuItem("Send to MD");
		mntmSendToMd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendAll();
			}
		});
		mnLoad.add(mntmSendToMd);
		
		JMenuItem mntmLoadFromFile = new JMenuItem("Load from file");
		mntmLoadFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				load_all();
			}
		});
		mnLoad.add(mntmLoadFromFile);
		
		JMenuItem mntmSaveToFile = new JMenuItem("Save to file");
		mntmSaveToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save_all();
			}
		});
		mnLoad.add(mntmSaveToFile);
		
		JMenuItem mntmSaveToMd = new JMenuItem("Save to MD slot 1");
		mntmSaveToMd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.requestSaveSlot1();
			}
		});
		mnLoad.add(mntmSaveToMd);
		
		JMenu mnMiscSettings = new JMenu("Misc settings");
		mnMain.add(mnMiscSettings);
		
		JMenuItem mntmLoadFromMd_1 = new JMenuItem("Load from MD");
		mntmLoadFromMd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getMisc();
			}
		});
		mnMiscSettings.add(mntmLoadFromMd_1);
		
		JMenuItem mntmSendToMd_1 = new JMenuItem("Send to MD");
		mntmSendToMd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMisc();
			}
		});
		mnMiscSettings.add(mntmSendToMd_1);
		
		JMenu mnHihatPedalSettings = new JMenu("HiHat pedal settings");
		mnHihatPedalSettings.setToolTipText("");
		mnMain.add(mnHihatPedalSettings);
		
		JMenuItem mntmLoadFromMd_2 = new JMenuItem("Load from MD");
		mntmLoadFromMd_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getPedal();
			}
		});
		mnHihatPedalSettings.add(mntmLoadFromMd_2);
		
		JMenuItem mntmSendToMd_2 = new JMenuItem("Send to MD");
		mntmSendToMd_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendPedal();
			}
		});
		mnHihatPedalSettings.add(mntmSendToMd_2);
		
		JMenu mnAllPadsSettings = new JMenu("All pads settings");
		mnMain.add(mnAllPadsSettings);
		
		JMenuItem mntmLoadFromMd_3 = new JMenuItem("Load from MD");
		mntmLoadFromMd_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAllPads();
			}
		});
		mnAllPadsSettings.add(mntmLoadFromMd_3);
		
		JMenuItem mntmSendToMd_3 = new JMenuItem("Send to MD");
		mntmSendToMd_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendAllPads();
			}
		});
		mnAllPadsSettings.add(mntmSendToMd_3);
		
		JMenu mnSelectedPadSettings = new JMenu("Selected pad settings");
		mnMain.add(mnSelectedPadSettings);
		
		JMenuItem mntmLoadFromMd_4 = new JMenuItem("Load from MD");
		mntmLoadFromMd_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getPad(controlsPads.getPadPointer());
			}
		});
		mnSelectedPadSettings.add(mntmLoadFromMd_4);
		
		JMenuItem mntmSendToMd_4 = new JMenuItem("Send to MD");
		mntmSendToMd_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendPad(controlsPads.getPadPointer());
			}
		});
		mnSelectedPadSettings.add(mntmSendToMd_4);
		
		JMenu mnCustomCurves = new JMenu("Custom curves");
		mnMain.add(mnCustomCurves);
		
		JMenuItem mntmLoadFromMd_5 = new JMenuItem("Load from MD");
		mntmLoadFromMd_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getAllCurves();
			}
		});
		mnCustomCurves.add(mntmLoadFromMd_5);
		
		JMenuItem mntmSendToMd_5 = new JMenuItem("Send to MD");
		mntmSendToMd_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendAllCurves();
			}
		});
		mnCustomCurves.add(mntmSendToMd_5);
		
		JSeparator separator_2 = new JSeparator();
		mnMain.add(separator_2);
		
		JMenuItem mntmFirmwareUpgrade = new JMenuItem("Firmware upgrade");
		mntmFirmwareUpgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				upgradeDialog.setConfigOptions(configOptions);
				midi_handler.getMidiBlocked = true;
				upgradeDialog.setVisible(true);
				midi_handler.getMidiBlocked = false;
			}
		});
		mnMain.add(mntmFirmwareUpgrade);
		
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
				saveAndExit();
			}
		});
		mnMain.add(mntmExit);
		
		mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane jp = JOptionPane();
				//jp.setFont(new Font("Tahoma", Font.PLAIN, 10));
				//jp.showMessageDialog(null,
				JOptionPane.showMessageDialog(null,
					    "<html><font size=5>"+Constants.HELP_ABOUT+"</font></html>",
					    "About",
					    JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnHelp.add(mntmAbout);
		
		
		panel_main = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_main.getLayout();
		flowLayout.setAlignOnBaseline(true);
		panel_main.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("pref:grow"),}));
		
		controlsMisc = new ControlsMisc();
		controlsMisc.getBtnSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_MISC_SIZE];
				Utils.copyConfigMiscToSysex(controlsMisc.getConfig(), sysex, configOptions.chainId);
				fileManager.saveSysex(sysex, configOptions);
			}
		});
		controlsMisc.getBtnLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_MISC_SIZE];
				Utils.copyConfigMiscToSysex(controlsMisc.getConfig(), sysex, configOptions.chainId);
				fileManager.loadSysex(sysex, configOptions);
				controlsMisc.setConfig(sysex);
			}
		});
		controlsMisc.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if ((configOptions != null) && configOptions.interactive) {
					if (arg0.getPropertyName().equals("valueChanged")) {
						sendMisc();
					}
				}
			}
		});
		controlsMisc.setBorder(new TitledBorder(null, "Misc", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_main.add(controlsMisc, "1, 2, default, top");
		controlsMisc.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getMisc();
			}
		});
		controlsMisc.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMisc();
			}
		});
		
		controlsPedal = new ControlsPedal();
		controlsPedal.getBtnSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
				Utils.copyConfigPedalToSysex(controlsPedal.getConfig(), sysex, configOptions.chainId);
				fileManager.saveSysex(sysex, configOptions);
			}
		});
		controlsPedal.getBtnLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
				Utils.copyConfigPedalToSysex(controlsPedal.getConfig(), sysex, configOptions.chainId);
				fileManager.loadSysex(sysex, configOptions);
				controlsPedal.setConfig(sysex);
			}
		});
		controlsPedal.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if ((configOptions != null) && configOptions.interactive) {
					if (arg0.getPropertyName().equals("valueChanged")) {
						sendPedal();
					}
				}
			}
		});
		controlsPedal.setBorder(new TitledBorder(null, "HiHat Pedal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_main.add(controlsPedal, "2, 2, default, top");
		controlsPedal.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getPedal();
			}
		});
		controlsPedal.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendPedal();
			}
		});
		
		controlsPads = new ControlsPads();
		controlsPads.getBtnSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PAD_SIZE];
				byte [] sysex3rd = new byte[Constants.MD_SYSEX_3RD_SIZE];
				byte [] sysexPad = new byte[Constants.MD_SYSEX_PAD_SIZE*2 + Constants.MD_SYSEX_3RD_SIZE];
				int padId = controlsPads.getPadPointer();
				if (padId > 0 ) {
					Utils.copyConfigPadToSysex(controlsPads.getConfig(padId), sysex, configOptions.chainId, padId);
					for (int i = 0; i<sysex.length;i++) {
						sysexPad[i] = sysex[i];
					}
					Utils.copyConfigPadToSysex(controlsPads.getConfig(padId+1), sysex, configOptions.chainId, padId+1);
					for (int i = 0; i<sysex.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE + i] = sysex[i];
					}
					Utils.copyConfig3rdToSysex(controlsPads.getConfig3rd((padId-1)/2), sysex3rd, configOptions.chainId, (padId-1)/2);
					for (int i = 0; i<sysex3rd.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + i] = sysex3rd[i];
					}
					fileManager.saveSysex(sysexPad, configOptions);
				} else {
					Utils.copyConfigPadToSysex(controlsPads.getConfig(0), sysex, configOptions.chainId, 0);
					for (int i = 0; i<sysex.length;i++) {
						sysexPad[i] = sysex[i];
					}					
					fileManager.saveSysex(sysex, configOptions);
				}
			}
		});
		controlsPads.getBtnLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PAD_SIZE];
				byte [] sysex3rd = new byte[Constants.MD_SYSEX_3RD_SIZE];
				byte [] sysexPad = new byte[Constants.MD_SYSEX_PAD_SIZE*2 + Constants.MD_SYSEX_3RD_SIZE];
				int padId = controlsPads.getPadPointer();
				if (padId > 0 ) {
					Utils.copyConfigPadToSysex(controlsPads.getConfig(padId), sysex, configOptions.chainId, padId);
					for (int i = 0; i<sysex.length;i++) {
						sysexPad[i] = sysex[i];
					}
					Utils.copyConfigPadToSysex(controlsPads.getConfig(padId+1), sysex, configOptions.chainId, padId+1);
					for (int i = 0; i<sysex.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE + i] = sysex[i];
					}
					Utils.copyConfig3rdToSysex(controlsPads.getConfig3rd((padId-1)/2), sysex3rd, configOptions.chainId, (padId-1)/2);
					for (int i = 0; i<sysex3rd.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + i] = sysex3rd[i];
					}
					fileManager.loadSysex(sysexPad, configOptions);
					for (int i = 0; i<sysex.length;i++) {
						sysex[i] = sysexPad[i];
					}
					controlsPads.setConfig(sysex, padId);
					for (int i = 0; i<sysex.length;i++) {
						sysex[i] = sysexPad[Constants.MD_SYSEX_PAD_SIZE + i];
					}
					controlsPads.setConfig(sysex, padId+1);
					for (int i = 0; i<sysex3rd.length;i++) {
						sysex3rd[i] = sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + i];
					}
					controlsPads.setConfig3rd(sysex3rd, (padId-1)/2);
				} else {
					Utils.copyConfigPadToSysex(controlsPads.getConfig(padId), sysex, configOptions.chainId, padId);
					fileManager.loadSysex(sysex, configOptions);					
					controlsPads.setConfig(sysex, 0);
				}
			}
		});
		controlsPads.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if ((configOptions != null) && configOptions.interactive) {
					if (arg0.getPropertyName().equals("headValueChanged")) {
						sendPadOneZone(controlsPads.getPadPointer());
					}
					if (arg0.getPropertyName().equals("rimValueChanged")) {
						sendPadOneZone(controlsPads.getPadPointer() + 1);
					}
					if (arg0.getPropertyName().equals("thirdZoneValueChanged")) {
						sendThirdZone(controlsPads.getPadPointer());
					}
				}
			}
		});
		
//		controlsPads.addPropertyChangeListener("resize", new PropertyChangeListener() {
//			public void propertyChange(PropertyChangeEvent arg0) {
//				resizeMainWindow();
//			}
//		});
		controlsPads.getBtnSendall().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendAllPads();
			}
		});
		controlsPads.getBtnGetall().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAllPads();
			}
		});
		controlsPads.setBorder(new TitledBorder(null, "Pads", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_main.add(controlsPads, "3, 2, default, top");
		controlsPads.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getPad(controlsPads.getPadPointer());
			}
		});
		controlsPads.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendPad(controlsPads.getPadPointer());
			}
		});

		frmMegadrummanager.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("pref:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				RowSpec.decode("1dlu"),
				RowSpec.decode("pref:grow"),
				RowSpec.decode("1dlu"),
				FormFactory.PREF_ROWSPEC,}));
		
		panel_top = new JPanel();
		frmMegadrummanager.getContentPane().add(panel_top, "1, 1, fill, fill");
		panel_top.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("40dlu"),
				ColumnSpec.decode("2dlu"),
				FormFactory.PREF_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnGetAll = new JButton("Get All");
		btnGetAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnGetAll.setMargin(new Insets(0, 1, 0, 1));
		btnGetAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAll();
			}
		});
		panel_top.add(btnGetAll, "2, 1");
		
		JButton btnSendAll = new JButton("Send All");
		btnSendAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSendAll.setMargin(new Insets(0, 1, 0, 1));
		btnSendAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendAll();
			}
		});
		panel_top.add(btnSendAll, "4, 1");
		
		JButton btnLoadAll = new JButton("Load All");
		btnLoadAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnLoadAll.setMargin(new Insets(0, 1, 0, 1));
		btnLoadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load_all();
			}
		});
		panel_top.add(btnLoadAll, "6, 1");
		
		JButton btnSaveAll = new JButton("Save All");
		btnSaveAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSaveAll.setMargin(new Insets(0, 1, 0, 1));
		btnSaveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save_all();
			}
		});
		panel_top.add(btnSaveAll, "8, 1");
		
		JButton btnSaveToSlot = new JButton("Save To Slot1");
		btnSaveToSlot.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSaveToSlot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.requestSaveSlot1();
			}
		});
		btnSaveToSlot.setMargin(new Insets(0, 1, 0, 1));
		panel_top.add(btnSaveToSlot, "10, 1");
		
		comboBoxCfg = new JComboBox();
		comboBoxCfg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = comboBoxCfg.getSelectedItem().toString();
				if (comboBoxCfg.getSelectedIndex()<0) {
					configOptions.configsNames[configOptions.lastConfig] = text;
					comboBoxCfg.setModel(new DefaultComboBoxModel(configOptions.configsNames));
					comboBoxCfg.setSelectedIndex(configOptions.lastConfig);
				}
			}			
		});
		comboBoxCfg.setModel(new DefaultComboBoxModel(configOptions.configsNames));
		comboBoxCfg.setSelectedIndex(configOptions.lastConfig);
		comboBoxCfg.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.DESELECTED) {
				}
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (comboBoxCfg.getSelectedIndex()>-1) {
						configOptions.lastConfig = comboBoxCfg.getSelectedIndex();
						loadAllFromConfigFull();
					}
				}
			}
		});
		comboBoxCfg.setEditable(true);
		comboBoxCfg.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel_top.add(comboBoxCfg, "12, 1, fill, default");
		
		JButton btnPrevcfg = new JButton("prevCfg");
		btnPrevcfg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (configOptions.lastConfig>0) {
					configOptions.lastConfig--;
					comboBoxCfg.setSelectedIndex(configOptions.lastConfig);
				}
			}
		});
		btnPrevcfg.setMargin(new Insets(0, 1, 0, 1));
		btnPrevcfg.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_top.add(btnPrevcfg, "14, 1");
		
		JButton btnNextcfg = new JButton("nextCfg");
		btnNextcfg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (configOptions.lastConfig<(Constants.CONFIGS_COUNT-1)) {
					configOptions.lastConfig++;
					comboBoxCfg.setSelectedIndex(configOptions.lastConfig);
				}
			}
		});
		btnNextcfg.setMargin(new Insets(0, 1, 0, 1));
		btnNextcfg.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_top.add(btnNextcfg, "16, 1");
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmMegadrummanager.getContentPane().add(panel, "1, 3, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblMidi = new JLabel("MIDI :");
		lblMidi.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(lblMidi, "2, 1");
		
		tglbtnMidi = new JToggleButton("Open MIDI");
		panel.add(tglbtnMidi, "4, 1");
		tglbtnMidi.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		tglbtnMidi.setMargin(new Insets(1, 4, 1, 4));
		
		JLabel lblFirmwareVer = new JLabel("Firmware ver:");
		lblFirmwareVer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(lblFirmwareVer, "6, 1");
		
		lblVersion = new JLabel("???????");
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVersion.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblVersion.setForeground(new Color(0, 0, 0));
		panel.add(lblVersion, "8, 1");
		
		JLabel lblInputs = new JLabel("Inputs:");
		lblInputs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(lblInputs, "10, 1");
		
		comboBox_inputsCount = new JComboBox();
		comboBox_inputsCount.setMaximumRowCount(20);
		panel.add(comboBox_inputsCount, "12, 1");
		comboBox_inputsCount.setFont(new Font("Tahoma", Font.PLAIN, 9));
		comboBox_inputsCount.removeAllItems();
		for (int i=0;i<((Constants.MAX_INPUTS-Constants.MIN_INPUTS)/2 + 1);i++) {
			comboBox_inputsCount.addItem((i*2) + Constants.MIN_INPUTS + 1);
		}		
		tglbtnLiveUpdates = new JToggleButton("Live updates");
		tglbtnLiveUpdates.setFont(new Font("Tahoma", Font.PLAIN, 9));
		tglbtnLiveUpdates.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configOptions.interactive = tglbtnLiveUpdates.isSelected(); 
			}
		});
		tglbtnLiveUpdates.setMargin(new Insets(2, 4, 2, 4));
		panel.add(tglbtnLiveUpdates, "14, 1");
		
		progressBar = new JProgressBar();
		panel.add(progressBar, "16, 1");
		progressBar.setVisible(false);
		
		progressBar.setStringPainted(true);
		comboBox_inputsCount.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
		        	configOptions.inputsCount = (comboBox_inputsCount.getSelectedIndex()*2) + Constants.MIN_INPUTS;
		        	updateInputsCountControls();
		        }
			}
		});
		tglbtnMidi.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					midi_handler.closeAllPorts();
					lblVersion.setText("????????");
				}
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					showMidiWarningIfNeeded();
					midi_handler.initPorts(configOptions);
				}
				toggleMidiOpenButton();
			}
		});
		frmMegadrummanager.getContentPane().add(panel_main, "1, 5, 3, 1, left, fill");
		
		controlsCurves = new ControlsCurves();
		controlsCurves.getBtnSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_CURVE_SIZE];
				Utils.copyConfigCurveToSysex(controlsCurves.getConfig(controlsCurves.getCurvePointer()), sysex, configOptions.chainId, controlsCurves.getCurvePointer());
				fileManager.saveSysex(sysex, configOptions);
			}
		});
		controlsCurves.getBtnLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_CURVE_SIZE];
				Utils.copyConfigCurveToSysex(controlsCurves.getConfig(controlsCurves.getCurvePointer()), sysex, configOptions.chainId, controlsCurves.getCurvePointer());
				fileManager.loadSysex(sysex, configOptions);					
				controlsCurves.setConfig(sysex, controlsCurves.getCurvePointer());
			}
		});
		controlsCurves.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if ((configOptions != null) && configOptions.interactive) {
					if (arg0.getPropertyName().equals("valueChanged")) {
						sendCurve(controlsCurves.getCurvePointer());
					}
				}
			}
		});
		controlsCurves.setBorder(new TitledBorder(null, "Curves", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		controlsCurves.getButton_get().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getCurve(controlsCurves.getCurvePointer());
			}
		});
		controlsCurves.getButton_send().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendCurve(controlsCurves.getCurvePointer());
			}
		});
		controlsCurves.getButton_getAll().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getAllCurves();
			}
		});
		controlsCurves.getButton_sendAll().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendAllCurves();
			}
		});
		panel_main.add(controlsCurves, "4, 2, fill, top");
		
		panelMidiLog = new PanelMidiLog(16);
		panelMidiLog.setBorder(new TitledBorder(null, "MIDI Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_main.add(panelMidiLog, "5, 2, fill, fill");

		// Show panels. 0 - Misc, 1 - Pedal, 2 - Pads, 3 - Curves
		framesDetached = new FrameDetached[Constants.PANELS_COUNT];
		controlsPanels = new JPanel[Constants.PANELS_COUNT];
		viewMenus = new ViewMenu[Constants.PANELS_COUNT];
		controlsPanels[0] = controlsMisc;
		controlsPanels[1] = controlsPedal;
		controlsPanels[2] = controlsPads;
		controlsPanels[3] = controlsCurves;
		controlsPanels[4] = panelMidiLog;
		
		for (int i=0;i<Constants.PANELS_COUNT;i++) {
			controlsPanels[i].addPropertyChangeListener("resize", new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					resizeMainWindow();
				}
			});			
			viewMenus[i] = new ViewMenu(Constants.PANELS_NAMES[i], i);
			viewMenus[i].addPropertyChangeListener("resize", new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					resizeMainWindow();
				}
			});
			mnView.add(viewMenus[i]);
			framesDetached[i] = new FrameDetached(i);
			framesDetached[i].setTitle(Constants.PANELS_NAMES[i]);
			framesDetached[i].setIconImage(Toolkit.getDefaultToolkit().getImage(Main_window.class.getResource("/icons/megadrum-manager128.png")));
			framesDetached[i].addWindowListener(new WindowAdapter() {
				@Override
				public void windowActivated(WindowEvent arg0) {
					((FrameDetached)arg0.getSource()).pack();
				}
				@Override
				public void windowDeactivated (WindowEvent arg0) {
					int id = ((FrameDetached)arg0.getSource()).controlsId;
					if (!framesDetached[id].isVisible()) {
						configOptions.showPanels[id] = Constants.PANEL_HIDE;
						viewMenus[id].updateControls();
						resizeMainWindow();
					}
				}
			});
		}
	}
	
	private void delayMs(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Utils.show_error("Unrecoverable delayMs (timer) error. Exiting.\n" +
					"(" + e.getMessage() + ")");
			System.exit(1);
		}
	}
	
	private void getPedal() {
		//midi_handler.clear_midi_input();
		midi_handler.requestConfigPedal();
		delayMs(configOptions.sysexDelay);
	}
	
	private void sendPedal() {
		byte [] sysexPedal = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
		Utils.copyConfigPedalToSysex(controlsPedal.getConfig(), sysexPedal, configOptions.chainId);
		midi_handler.sendSysex(sysexPedal);
		delayMs(configOptions.sysexDelay);
	}
	
	private void getMisc() {
		//midi_handler.clear_midi_input();
		midi_handler.requestConfigMisc();					
		delayMs(configOptions.sysexDelay);
	}
	
	private void sendMisc() {
		byte [] sysexMisc = new byte[Constants.MD_SYSEX_MISC_SIZE];
		Utils.copyConfigMiscToSysex(controlsMisc.getConfig(), sysexMisc, configOptions.chainId);
		midi_handler.sendSysex(sysexMisc);
		delayMs(configOptions.sysexDelay);
	}
	
	private void getPad(int pad_id) {
		//midi_handler.clear_midi_input();
		if ( pad_id > 0 ) {
			midi_handler.requestConfigPad(pad_id + 1);
			delayMs(configOptions.sysexDelay);
			midi_handler.requestConfigPad(pad_id + 2);
			delayMs(configOptions.sysexDelay);
			midi_handler.requestConfig3rd((pad_id - 1)/2);
			delayMs(configOptions.sysexDelay);
		} else {
			midi_handler.requestConfigPad(1);
			delayMs(configOptions.sysexDelay);
		}
	}
	
	private void sendPadOneZone(int pad_id) {
		byte [] sysexPad = new byte[Constants.MD_SYSEX_PAD_SIZE];
		
		Utils.copyConfigPadToSysex(controlsPads.getConfig(pad_id), sysexPad, configOptions.chainId, pad_id);
		midi_handler.sendSysex(sysexPad);
		delayMs(configOptions.sysexDelay);		
	}
	
	private void sendThirdZone(int pad_id) {
		byte [] sysex3rd = new byte[Constants.MD_SYSEX_3RD_SIZE];
		
		pad_id = (pad_id - 1)/2;
		Utils.copyConfig3rdToSysex(controlsPads.getConfig3rd(pad_id), sysex3rd, configOptions.chainId, pad_id);
		midi_handler.sendSysex(sysex3rd);
		delayMs(configOptions.sysexDelay);
	}

	private void sendPad(int pad_id) {

		sendPadOneZone(pad_id);
		if (pad_id > 0 ) {
			sendPadOneZone(pad_id + 1);
			sendThirdZone(pad_id);
		}
		
	}
	
	private void getAllPads() {
		progressBar.setVisible(true);
		Thread t = new Thread(new Runnable() {
            public void run() {
                // since we're not on the EDT,
                // let's put the setVisible-code
                // into the Event Dispatching Queue
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                		int i;
                        resizeWindow = false;
                		progressBar.setMinimum(0);
                		progressBar.setMaximum(configOptions.inputsCount - 2);
                		for (i = 0; i<(configOptions.inputsCount - 1); i++) {
                			progressBar.setValue(i);
                			Rectangle progressRect = progressBar.getBounds();
                			progressRect.x = 0;
                			progressRect.y = 0;
                			progressBar.paintImmediately( progressRect );
                			getPad(i);
                			if (i>0) {
                				i++;
                			}
                		}
                		progressBar.setVisible(false);
                        resizeWindow = true;
                        resizeMainWindow();
                   }
                });
            }

		});
        t.setPriority( Thread.NORM_PRIORITY );
        t.run();
	}
		
	private void sendAllPads() {
		progressBar.setVisible(true);
		Thread t = new Thread(new Runnable() {
            public void run() {
                // since we're not on the EDT,
                // let's put the setVisible-code
                // into the Event Dispatching Queue
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                		int i;
                        resizeWindow = false;
                		progressBar.setMinimum(0);
                		progressBar.setMaximum(configOptions.inputsCount - 2);
                		for (i = 0; i<(configOptions.inputsCount - 1); i++) {
                			progressBar.setValue(i);
                			Rectangle progressRect = progressBar.getBounds();
                			progressRect.x = 0;
                			progressRect.y = 0;
                			progressBar.paintImmediately( progressRect );
                			sendPad(i);
                			if (i>0) {
                				i++;
                			}
                		}
                		progressBar.setVisible(false);
                        resizeWindow = true;
                        resizeMainWindow();
                   }
                });
            }

		});
        t.setPriority( Thread.NORM_PRIORITY );
        t.run();
	}

	private void getCurve(int curve_id) {
		//midi_handler.clear_midi_input();
		midi_handler.requestConfigCurve(curve_id);
		delayMs(configOptions.sysexDelay);
	}
	
	private void sendCurve(int curve_id) {
		byte [] sysexCurve = new byte[Constants.MD_SYSEX_CURVE_SIZE];
		Utils.copyConfigCurveToSysex(controlsCurves.getConfig(curve_id), sysexCurve, configOptions.chainId, curve_id);
		midi_handler.sendSysex(sysexCurve);
		delayMs(configOptions.sysexDelay);
	}
	
	private void getAllCurves() {
		for (int i = 0; i<Constants.CURVES_COUNT; i++) {
			getCurve(i);
		}
	}
		
	private void sendAllCurves() {
		for (int i = 0; i<Constants.CURVES_COUNT; i++) {
			sendCurve(i);
		}
	}

	private void getAll() {
		getMisc();
		getPedal();
		getAllPads();
		getAllCurves();
	}
	
	private void sendAll() {
		sendMisc();
		sendPedal();
		sendAllPads();
		sendAllCurves();
	}

	private void loadAllFromConfigFull() {
		controlsMisc.loadFromConfigFull(fullConfigs[configOptions.lastConfig]);
		controlsPedal.loadFromConfigFull(fullConfigs[configOptions.lastConfig]);
		controlsPads.loadFromConfigFull(fullConfigs[configOptions.lastConfig]);		
		controlsCurves.loadFromConfigFull(fullConfigs[configOptions.lastConfig]);				
	}
	private void load_all() {
		fileManager.load_all(fullConfigs[configOptions.lastConfig], configOptions);
		loadAllFromConfigFull();
	}

	private void save_all() {
		fileManager.save_all(fullConfigs[configOptions.lastConfig], configOptions);
	}
	
	private void loadConfig() {
		//copyAllToConfigFull();
		configOptions  = fileManager.loadLastOptions(configOptions);
		comboBoxCfg.setModel(new DefaultComboBoxModel(configOptions.configsNames));
		comboBoxCfg.setSelectedIndex(configOptions.lastConfig);
		dialog_options.fillInPorts(midi_handler.getMidiInList());
		dialog_options.fillOutPorts(midi_handler.getMidiOutList());
		dialog_options.fillThruPorts(midi_handler.getMidiOutList());
		dialog_options.loadOptionsFrom(configOptions);
		showMidiWarningIfNeeded();
		if (configOptions.autoOpenPorts) {
			midi_handler.initPorts(configOptions);
			tglbtnMidi.setSelected(midi_handler.isMidiOpen());
		}
		midi_handler.chainId = configOptions.chainId;
		comboBox_inputsCount.setSelectedIndex((configOptions.inputsCount - Constants.MIN_INPUTS)/2);
		updateInputsCountControls();
		if (!configOptions.lastFullPathConfig.equals("")) {
			fileManager.loadAllSilent(fullConfigs[configOptions.lastConfig], configOptions);
			loadAllFromConfigFull();
		}
		frmMegadrummanager.setLocation(configOptions.mainWindowPosition);
		for (int i = 0;i<Constants.PANELS_COUNT;i++) {
			framesDetached[i].setLocation(configOptions.framesPositions[i]);
			viewMenus[i].setConfigOptions(configOptions);
		}
		tglbtnLiveUpdates.setSelected(configOptions.interactive);
		
	}
	
	private void saveAndExit() {
		midi_handler.closeAllPorts();
		configOptions.mainWindowPosition = frmMegadrummanager.getLocation();
		for (int i = 0;i<Constants.PANELS_COUNT;i++) {
			configOptions.framesPositions[i] = framesDetached[i].getLocation(); 
		}
		dialog_options.saveOptionsTo(configOptions);
		if (configOptions.saveOnExit) {
			fileManager.saveLastOptions(configOptions);
		}
		System.exit(0);
	}
	
	private void updateInputsCountControls() {
		controlsPedal.updateInputCountsControls(configOptions.inputsCount);
		controlsPads.updateInputCountsControls(configOptions.inputsCount);
	}
	
	private void resizeMainWindow() {
		// Show panels. 0 - Misc, 1 - Pedal, 2 - Pads, 3 - Curves, 4 - MIDI Log
		for (int i = 0; i< Constants.PANELS_COUNT; i++) {
			SwingUtilities.updateComponentTreeUI(framesDetached[i]);
			controlsPanels[i].setVisible(configOptions.showPanels[i] != Constants.PANEL_HIDE);
			if (i == 3) {
				// Pause, start live MIDI log
				panelMidiLog.pauseLiveScroll(!panelMidiLog.isVisible());
			}
			if (configOptions.showPanels[i] == Constants.PANEL_DETACH) {
				if (!framesDetached[i].isDetached) {
					framesDetached[i].isDetached = true;
					framesDetached[i].getContentPane().add(controlsPanels[i], "1, 1, fill, top");
					framesDetached[i].setVisible(true);
				}
			} else {
				if (framesDetached[i].isDetached) {
					framesDetached[i].isDetached = false;
					framesDetached[i].setVisible(false);
					panel_main.add(controlsPanels[i], ((Integer)(i+1)).toString() +", 2, default, top");
				}
			}
			framesDetached[i].pack();
		}
		SwingUtilities.updateComponentTreeUI(frmMegadrummanager);
		if (resizeWindow) {
			frmMegadrummanager.pack();
		}
	}
	
	private void toggleMidiOpenButton() {
		if (midi_handler.isMidiOpen()) {
			tglbtnMidi.setText("Close MIDI");
			tglbtnMidi.setSelected(true);
			midi_handler.requestVersion();
		} else {
			tglbtnMidi.setText("Open MIDI");
			tglbtnMidi.setSelected(false);
		}
	}
	
	private void decodeSysex(byte [] buffer) {
		if (buffer[1] == Constants.MD_SYSEX) {
			if (buffer[2] == (byte) configOptions.chainId) {
				switch (buffer[3]) {
					case Constants.MD_SYSEX_MISC:
						controlsMisc.setConfig(midi_handler.bufferIn);
						break;
					case Constants.MD_SYSEX_PEDAL:
						controlsPedal.setConfig(midi_handler.bufferIn);
						break;
					case Constants.MD_SYSEX_PAD:
						controlsPads.setConfig(midi_handler.bufferIn, buffer[4] - 1);
						break;
					case Constants.MD_SYSEX_3RD:
						controlsPads.setConfig3rd(midi_handler.bufferIn, buffer[4]);
						break;
					case Constants.MD_SYSEX_VERSION:
						int ver = 0;
						if (buffer.length >= Constants.MD_SYSEX_VERSION_SIZE) {
							int b;
							for (int i=0;i<4;i++) {
								b = (int)(buffer[i*2 + 4]<<4);
								b |= (int)buffer[i*2 + 5];
								ver += b<<(8*i);
							}
							lblVersion.setText(((Integer)ver).toString());
						}
						break;
					case Constants.MD_SYSEX_CURVE:
						controlsCurves.setConfig(midi_handler.bufferIn, buffer[4]);
						break;
					default:
						break;
				}
			}
		}		
	}
	
	public void decodeShortMidi (byte [] buffer) {
		Color color;
		switch (buffer.length) {
		case 1:
			//shortMessage.setMessage(buf[0]);
			break;
		case 2:
			//shortMessage.setMessage(buf[0], buf[1],0);
			break;
		default:
			//shortMessage.setMessage(buf[0], buf[1],buf[2]);
			if (((buffer[0]&0xf0) == 0x90) && (buffer[2] > 0)) {
				color = Constants.MD_UNKNOWN_COLOR;
				for (int i = 0; i< controlsPads.configPads.length;i++) {
					if ((buffer[1]==controlsPads.configPads[i].note) ||
							(buffer[1]==controlsPads.configPads[i].altNote) ||
							(buffer[1]==controlsPads.configPads[i].pressrollNote)) {
						if (i==0) {
							color = Constants.MD_HEAD_COLOR;
						} else {
							color = ((i&0x01)==1)?Constants.MD_HEAD_COLOR:Constants.MD_RIM_COLOR;
						}
					}
					if ((i&0x01) == 1) {
						if ((buffer[1]==controlsPads.config3rds[(i-1)/2].note) ||
								(buffer[1]==controlsPads.config3rds[(i-1)/2].altNote) ||
								(buffer[1]==controlsPads.config3rds[(i-1)/2].pressrollNote) ||
								(buffer[1]==controlsPads.config3rds[(i-1)/2].dampenedNote)) {
									color = Constants.MD_3RD_COLOR;
						}						
					}
				}
				panelMidiLog.showNewHit(buffer[1], buffer[2], color);
			}
			if ((buffer[0]&0xf0) == 0xa0) {
				panelMidiLog.showNewHit(buffer[1], 127,(buffer[2]>0)?Constants.MD_AFTERTOUCH_ON_COLOR:Constants.MD_AFTERTOUCH_OFF_COLOR);
			}
			if (((buffer[0]&0xf0) == 0xb0) && (buffer[1] == 0x04)) {
				panelMidiLog.showHiHatLevel(127 - buffer[2], Constants.MD_HIHAT_COLOR);
			}
			break;
		}
	
	}
	
	private void showMidiWarningIfNeeded() {
		if (configOptions.MidiInName.equals("") || configOptions.MidiOutName.equals("")) {
			JOptionPane.showMessageDialog(null,
				    Constants.MIDI_PORTS_WARNING,
				    "Warning",
				    JOptionPane.INFORMATION_MESSAGE);
		}		
	}
}

