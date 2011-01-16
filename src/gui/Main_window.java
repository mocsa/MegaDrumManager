package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
import javax.swing.filechooser.FileFilter;

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

public class Main_window {

	private JFrame frmMegadrummanager;
	private JPanel panel_main;
	private JPanel panel_top;
	private Options dialog_options;
	private Upgrade upgradeDialog;
	private Midi_handler midi_handler;
	private Timer timer_midi;
	private TimerTask midi_in_task;
	private ControlsMisc controlsMisc;
	private ControlsPedal controlsPedal;
	private ControlsPads controlsPads;
	private ControlsCurves controlsCurves;
	private FrameDetached [] framesDetached;
	private JPanel [] controlsPanels;
	private ViewMenu [] viewMenus;
	private ConfigFull configFull;
	private ConfigOptions configOptions;
	private FileManager fileManager;
	private int chainId;
	private JMenuItem menuItem;
	private JMenu mnView;
	private JProgressBar progressBar;
	private JComboBox comboBox_inputsCount;
	private boolean resizeWindow = true;
	private JToggleButton tglbtnMidi;
	

	
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
		frmMegadrummanager.setResizable(false);
		frmMegadrummanager.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				saveAndExit();
			}
		});
		frmMegadrummanager.setTitle("MegaDrumManager");
		frmMegadrummanager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		chainId = 0;
		configFull = new ConfigFull();
		fileManager = new FileManager(frmMegadrummanager);
		midi_handler = new Midi_handler();
		dialog_options = new Options(midi_handler);
		upgradeDialog = new Upgrade(midi_handler, fileManager);
		midi_handler.chainId = chainId;
		timer_midi = new Timer();
		midi_in_task = new TimerTask() {
			public void run() {
				midi_handler.get_midi();
				if (midi_handler.sysexReceived) {
					decodeSysex(midi_handler.bufferIn);
					midi_handler.sysexReceived = false;
					midi_handler.bufferIn = null;
				}
			}
		};

		timer_midi.scheduleAtFixedRate(midi_in_task, 1000, 1);
		
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
		
		
		panel_main = new JPanel();
		panel_main.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,}));
		
		controlsMisc = new ControlsMisc();
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
		controlsPads.addPropertyChangeListener("resize", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				resizeMainWindow();
			}
		});
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
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("20dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("32dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("12dlu:grow"),}));
		
		JButton btnGetAll = new JButton("Get All");
		btnGetAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAll();
			}
		});
		panel_top.add(btnGetAll, "2, 1");
		
		JButton btnSendAll = new JButton("Send All");
		btnSendAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendAll();
			}
		});
		panel_top.add(btnSendAll, "4, 1");
		
		JButton btnLoadAll = new JButton("Load All");
		btnLoadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load_all();
			}
		});
		panel_top.add(btnLoadAll, "6, 1");
		
		JButton btnSaveAll = new JButton("Save All");
		btnSaveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save_all();
			}
		});
		panel_top.add(btnSaveAll, "8, 1");
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmMegadrummanager.getContentPane().add(panel, "1, 3, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("40dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblMidi = new JLabel("MIDI :");
		panel.add(lblMidi, "2, 1");
		
		tglbtnMidi = new JToggleButton("Open MIDI");
		panel.add(tglbtnMidi, "4, 1");
		tglbtnMidi.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		tglbtnMidi.setMargin(new Insets(1, 4, 1, 4));
		
		JLabel lblFirmwareVer = new JLabel("Firmware ver:");
		panel.add(lblFirmwareVer, "6, 1");
		
		JLabel lblJjjjjjjjjjjjjjj = new JLabel("jjjjjjjjjjjjjjj");
		lblJjjjjjjjjjjjjjj.setForeground(new Color(0, 0, 0));
		panel.add(lblJjjjjjjjjjjjjjj, "8, 1");
		
		JLabel lblInputs = new JLabel("Inputs:");
		panel.add(lblInputs, "10, 1");
		
		comboBox_inputsCount = new JComboBox();
		panel.add(comboBox_inputsCount, "12, 1");
		comboBox_inputsCount.setFont(new Font("Tahoma", Font.PLAIN, 9));
		comboBox_inputsCount.removeAllItems();
		comboBox_inputsCount.addItem("22");
		comboBox_inputsCount.addItem("32");
		comboBox_inputsCount.addItem("40");
		comboBox_inputsCount.addItem("48");
		comboBox_inputsCount.addItem("56");
		
		progressBar = new JProgressBar();
		panel.add(progressBar, "14, 1");
		progressBar.setVisible(false);
		
		progressBar.setStringPainted(true);
		comboBox_inputsCount.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
		        	switch (comboBox_inputsCount.getSelectedIndex()) {
		        	case 0:
		        		configOptions.inputsCount = 21;
		        		break;
		        	case 1:
		        		configOptions.inputsCount = 31;
		        		break;
		        	case 2:
		        		configOptions.inputsCount = 39;
		        		break;
		        	case 3:
		        		configOptions.inputsCount = 47;
		        		break;
		        	default:
		        		configOptions.inputsCount = 55;
		        		break;
		        	}
		        	updateInputsCountControls();
		        }
			}
		});
		tglbtnMidi.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					midi_handler.closeAllPorts();
				}
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					midi_handler.initPorts(configOptions);
				}
				toggleMidiOpenButton();
			}
		});
		frmMegadrummanager.getContentPane().add(panel_main, "1, 5, left, fill");
		
		controlsCurves = new ControlsCurves();
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
		

		// Show panels. 0 - Misc, 1 - Pedal, 2 - Pads, 3 - Curves
		framesDetached = new FrameDetached[Constants.PANELS_COUNT];
		controlsPanels = new JPanel[Constants.PANELS_COUNT];
		viewMenus = new ViewMenu[Constants.PANELS_COUNT];
		controlsPanels[0] = controlsMisc;
		controlsPanels[1] = controlsPedal;
		controlsPanels[2] = controlsPads;
		controlsPanels[3] = controlsCurves;
		for (int i=0;i<Constants.PANELS_COUNT;i++) {
			viewMenus[i] = new ViewMenu(Constants.PANELS_NAMES[i], i);
			viewMenus[i].addPropertyChangeListener("resize", new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					resizeMainWindow();
				}
			});
			mnView.add(viewMenus[i]);
			framesDetached[i] = new FrameDetached(i);
			framesDetached[i].setTitle(Constants.PANELS_NAMES[i]);
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
			e.printStackTrace();
		}
	}
	
	private void getPedal() {
		midi_handler.clear_midi_input();
		midi_handler.requestConfigPedal();
		delayMs(configOptions.sysexDelay);
	}
	
	private void sendPedal() {
		byte [] sysexPedal = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
		Utils.copyConfigPedalToSysex(controlsPedal.getConfig(), sysexPedal, chainId);
		midi_handler.sendSysex(sysexPedal);
		delayMs(configOptions.sysexDelay);
	}
	
	private void getMisc() {
		midi_handler.clear_midi_input();
		midi_handler.requestConfigMisc();					
		delayMs(configOptions.sysexDelay);
	}
	
	private void sendMisc() {
		byte [] sysexMisc = new byte[Constants.MD_SYSEX_MISC_SIZE];
		Utils.copyConfigMiscToSysex(controlsMisc.getConfig(), sysexMisc, chainId);
		midi_handler.sendSysex(sysexMisc);
		delayMs(configOptions.sysexDelay);
	}
	
	private void getPad(int pad_id) {
		midi_handler.clear_midi_input();
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
	
	private void sendPad(int pad_id) {
		byte [] sysexPad = new byte[Constants.MD_SYSEX_PAD_SIZE];
		byte [] sysex3rd = new byte[Constants.MD_SYSEX_3RD_SIZE];

		if (pad_id > 0 ) {
			Utils.copyConfigPadToSysex(controlsPads.getConfig(pad_id), sysexPad, chainId, pad_id);
			midi_handler.sendSysex(sysexPad);
			delayMs(configOptions.sysexDelay);
			Utils.copyConfigPadToSysex(controlsPads.getConfig(pad_id + 1), sysexPad, chainId, pad_id + 1);
			midi_handler.sendSysex(sysexPad);
			delayMs(configOptions.sysexDelay);
			pad_id = (pad_id - 1)/2;
			Utils.copyConfig3rdToSysex(controlsPads.getConfig3rd(pad_id), sysex3rd, chainId, pad_id);
			midi_handler.sendSysex(sysex3rd);
			delayMs(configOptions.sysexDelay);
		} else {
			Utils.copyConfigPadToSysex(controlsPads.getConfig(0), sysexPad, chainId, 0);
			midi_handler.sendSysex(sysexPad);
			delayMs(configOptions.sysexDelay);
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
		midi_handler.clear_midi_input();
		midi_handler.requestConfigCurve(curve_id);
		delayMs(configOptions.sysexDelay);
	}
	
	private void sendCurve(int curve_id) {
		byte [] sysexCurve = new byte[Constants.MD_SYSEX_CURVE_SIZE];
		Utils.copyConfigCurveToSysex(controlsCurves.getConfig(curve_id), sysexCurve, chainId, curve_id);
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

	private void load_all() {
		configFull = fileManager.load_all(configFull, configOptions);
		controlsMisc.loadFromConfigFull(configFull);
		controlsPedal.loadFromConfigFull(configFull);
		controlsPads.loadFromConfigFull(configFull);		
	}
	
	private void save_all() {
		controlsMisc.copyToConfigFull(configFull, configOptions.chainId);
		controlsPedal.copyToConfigFull(configFull, configOptions.chainId);
		controlsPads.copyToConfigFull(configFull, configOptions.chainId);
		fileManager.save_all(configFull, configOptions);
	}
	
	private void loadConfig() {
		configOptions = new ConfigOptions(); // default options loaded with new
		configOptions  = fileManager.loadLastOptions(configOptions);
		dialog_options.fillInPorts(midi_handler.getMidiInList());
		dialog_options.fillOutPorts(midi_handler.getMidiOutList());
		dialog_options.fillThruPorts(midi_handler.getMidiOutList());
		dialog_options.loadOptionsFrom(configOptions);
		if (configOptions.autoOpenPorts) {
			midi_handler.initPorts(configOptions);
			tglbtnMidi.setSelected(midi_handler.isMidiOpen());
		}
		switch (configOptions.inputsCount) {
			case 21:
				comboBox_inputsCount.setSelectedIndex(0);
				break;
			case 31:
				comboBox_inputsCount.setSelectedIndex(1);
				break;
			case 39:
				comboBox_inputsCount.setSelectedIndex(2);
				break;
			case 47:
				comboBox_inputsCount.setSelectedIndex(3);
				break;
			default:
				comboBox_inputsCount.setSelectedIndex(4);
				break;
		}
		updateInputsCountControls();
		controlsMisc.copyToConfigFull(configFull, configOptions.chainId);
		controlsPedal.copyToConfigFull(configFull, configOptions.chainId);
		controlsPads.copyToConfigFull(configFull, configOptions.chainId);
		if (!configOptions.lastFullPathConfig.equals("")) {
			configFull = fileManager.loadAllSilent(configFull, configOptions);
			controlsMisc.loadFromConfigFull(configFull);
			controlsPedal.loadFromConfigFull(configFull);
			controlsPads.loadFromConfigFull(configFull);		
		}
		frmMegadrummanager.setLocation(configOptions.mainWindowPosition);
		for (int i = 0;i<Constants.PANELS_COUNT;i++) {
			framesDetached[i].setLocation(configOptions.framesPositions[i]);
			viewMenus[i].setConfigOptions(configOptions);
		}
		
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
		// Show panels. 0 - Misc, 1 - Pedal, 2 - Pads, 3 - Curves
		for (int i = 0; i< Constants.PANELS_COUNT; i++) {
			controlsPanels[i].setVisible(configOptions.showPanels[i] != Constants.PANEL_HIDE);
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
		if (resizeWindow) {
			frmMegadrummanager.pack();
		}
	}
	
	private void toggleMidiOpenButton() {
		if (midi_handler.isMidiOpen()) {
			tglbtnMidi.setText("Close MIDI");
			tglbtnMidi.setSelected(true);
		} else {
			tglbtnMidi.setText("Open MIDI");
			tglbtnMidi.setSelected(false);
		}
	}
	
	private void decodeSysex(byte [] buffer) {
		if (buffer[1] == Constants.MD_SYSEX) {
			if (buffer[2] == (byte) chainId) {
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
					case Constants.MD_SYSEX_CURVE:
						controlsCurves.setConfig(midi_handler.bufferIn, buffer[4]);
						break;
					default:
						break;
				}
			}
		}		
	}
}

