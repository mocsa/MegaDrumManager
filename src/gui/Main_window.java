package gui;

import java.awt.EventQueue;

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
	private FrameMisc frameMisc;
	private ControlsPedal controlsPedal;
	private ControlsPads controlsPads;
	private ControlsCurves controlsCurves;
	private ConfigFull configFull;
	private ConfigOptions configOptions;
	private FileManager fileManager;
	private int chainId;
	private JMenuItem menuItem;
	private JMenu mnView;
	private JProgressBar progressBar;
	private JComboBox comboBox_inputsCount;
	private ViewMenu viewMenuMisc;
	private ViewMenu viewMenuPedal;
	private ViewMenu viewMenuPads;
	private ViewMenu viewMenuCurves;
	

	
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
		midi_handler.config_chain_id = chainId;
		timer_midi = new Timer();
		midi_in_task = new TimerTask() {
			public void run() {
				midi_handler.get_midi();
				if (midi_handler.config_misc.changed) {
					midi_handler.config_misc.changed = false;
					controlsMisc.setConfig(midi_handler.config_misc);
				}
				if (midi_handler.config_pedal.changed) {
					midi_handler.config_pedal.changed = false;
					controlsPedal.setConfig(midi_handler.config_pedal);
				}
				if (midi_handler.config_pad.changed_pad > 0) {
					controlsPads.setConfig(midi_handler.config_pad, midi_handler.config_pad.changed_pad - 1);
					midi_handler.config_pad.changed_pad = 0;
				}
				if (midi_handler.config_3rd.changed_3rd > 0) {
					controlsPads.setConfig3rd(midi_handler.config_3rd, midi_handler.config_3rd.changed_3rd - 1);
					midi_handler.config_3rd.changed_3rd = 0;
				}
				if (midi_handler.config_curve.changed_curve > -1) {
					controlsCurves.setConfig(midi_handler.config_curve, midi_handler.config_curve.changed_curve);
					midi_handler.config_curve.changed_curve = -1;
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
		mnCustomCurves.add(mntmLoadFromMd_5);
		
		JMenuItem mntmSendToMd_5 = new JMenuItem("Send to MD");
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
		
		// Show panels. 0 - Misc, 1 - Pedal, 2 - Pads, 3 - Curves
		viewMenuMisc = new ViewMenu("Misc", 0);
		viewMenuMisc.addPropertyChangeListener("resize", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				resizeMainWindow();
			}
		});
		mnView.add(viewMenuMisc);

		viewMenuPedal = new ViewMenu("Pedal", 1);
		viewMenuPedal.addPropertyChangeListener("resize", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				resizeMainWindow();
			}
		});
		mnView.add(viewMenuPedal);

		viewMenuPads = new ViewMenu("Pads", 2);
		viewMenuPads.addPropertyChangeListener("resize", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				resizeMainWindow();
			}
		});
		mnView.add(viewMenuPads);
		
		viewMenuCurves = new ViewMenu("Curves", 3);
		viewMenuCurves.addPropertyChangeListener("resize", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				resizeMainWindow();
			}
		});
		mnView.add(viewMenuCurves);
		
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
		
		frameMisc = new FrameMisc();
		
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
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
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
				ColumnSpec.decode("36dlu"),
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
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		
		JLabel lblInputs = new JLabel("Inputs:");
		panel_top.add(lblInputs, "10, 1, right, default");
		
		comboBox_inputsCount = new JComboBox();
		comboBox_inputsCount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_inputsCount.removeAllItems();
		comboBox_inputsCount.addItem("22");
		comboBox_inputsCount.addItem("32");
		comboBox_inputsCount.addItem("40");
		comboBox_inputsCount.addItem("48");
		comboBox_inputsCount.addItem("56");
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
		panel_top.add(comboBox_inputsCount, "12, 1, left, fill");
		
		progressBar.setStringPainted(true);
		panel_top.add(progressBar, "14, 1");
		frmMegadrummanager.getContentPane().add(panel_main, "1, 3, left, fill");
		
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
		midi_handler.request_config_pedal();
		delayMs(configOptions.sysexDelay);
	}
	
	private void sendPedal() {
		midi_handler.config_pedal = controlsPedal.getConfig();
		midi_handler.send_config_pedal();
		delayMs(configOptions.sysexDelay);
	}
	
	private void getMisc() {
		midi_handler.clear_midi_input();
		midi_handler.request_config_misc();					
		delayMs(configOptions.sysexDelay);
	}
	
	private void sendMisc() {
		midi_handler.config_misc = controlsMisc.getConfig();
		midi_handler.send_config_misc();	
		delayMs(configOptions.sysexDelay);
	}
	
	private void getPad(int pad_id) {
		midi_handler.clear_midi_input();
		if ( pad_id > 0 ) {
			midi_handler.request_config_pad(pad_id + 1);
			delayMs(configOptions.sysexDelay);
			midi_handler.request_config_pad(pad_id + 2);
			delayMs(configOptions.sysexDelay);
			midi_handler.request_config_3rd((pad_id - 1)/2);
			delayMs(configOptions.sysexDelay);
		} else {
			midi_handler.request_config_pad(1);
			delayMs(configOptions.sysexDelay);
		}
	}
	
	private void sendPad(int pad_id) {
		if (pad_id > 0 ) {
			midi_handler.config_pad.copyVarsFrom(controlsPads.getConfig(pad_id));
			midi_handler.send_config_pad(pad_id + 1);
			delayMs(configOptions.sysexDelay);
			midi_handler.config_pad .copyVarsFrom(controlsPads.getConfig(pad_id+1));
			midi_handler.send_config_pad(pad_id + 2);
			delayMs(configOptions.sysexDelay);
			pad_id = (pad_id - 1)/2;
			midi_handler.config_3rd .copyVarsFrom(controlsPads.getConfig3rd(pad_id));
			midi_handler.send_config_3rd(pad_id);
			delayMs(configOptions.sysexDelay);
		} else {
			midi_handler.config_pad.copyVarsFrom(controlsPads.getConfig(0));
			midi_handler.send_config_pad(1);
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
                		progressBar.setMinimum(0);
                		progressBar.setMaximum(configOptions.inputsCount - 2);
                		for (i = 0; i<(configOptions.inputsCount - 1); i++) {
                			progressBar.setValue(i);
                			Rectangle progressRect = progressBar.getBounds();
                			progressRect.x = 0;
                			progressRect.y = 0;
                			progressBar.paintImmediately( progressRect );
                			getPad(i);
                		}
                		progressBar.setVisible(false);
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
                		progressBar.setMinimum(0);
                		progressBar.setMaximum(configOptions.inputsCount - 2);
                		for (i = 0; i<(configOptions.inputsCount - 1); i++) {
                			progressBar.setValue(i);
                			Rectangle progressRect = progressBar.getBounds();
                			progressRect.x = 0;
                			progressRect.y = 0;
                			progressBar.paintImmediately( progressRect );
                			sendPad(i);
                		}
                		progressBar.setVisible(false);
                   }
                });
            }

		});
        t.setPriority( Thread.NORM_PRIORITY );
        t.run();
	}

	private void getCurve(int curve_id) {
		midi_handler.clear_midi_input();
		midi_handler.request_config_curve(curve_id);
		delayMs(configOptions.sysexDelay);
	}
	
	private void sendCurve(int curve_id) {
		midi_handler.config_curve.copyVarsFrom(controlsCurves.getConfig(curve_id));
		midi_handler.send_config_curve(curve_id);
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

		viewMenuMisc.setConfigOptions(configOptions);
		viewMenuPedal.setConfigOptions(configOptions);
		viewMenuPads.setConfigOptions(configOptions);
		viewMenuCurves.setConfigOptions(configOptions);
		
	}
	
	private void saveAndExit() {
		midi_handler.closeAllPorts();
		configOptions.mainWindowPosition = frmMegadrummanager.getLocation();
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
		controlsMisc.setVisible(configOptions.showPanels[0] == Constants.PANEL_SHOW);
		if (configOptions.showPanels[0] == Constants.PANEL_DETATCH) {
			//frameMisc = new FrameMisc();
			//frameMisc.getContentPane().add(controlsMisc, "1, 1, fill, top");
			frameMisc.setVisible(configOptions.showPanels[0] == Constants.PANEL_DETATCH);
		} else {
			frameMisc.getContentPane().remove(controlsMisc);
			frameMisc.setVisible(configOptions.showPanels[0] == Constants.PANEL_DETATCH);
			//frameMisc = null;
		}
		controlsPedal.setVisible(configOptions.showPanels[1] == Constants.PANEL_SHOW);
		controlsPads.setVisible(configOptions.showPanels[2] == Constants.PANEL_SHOW);
		controlsCurves.setVisible(configOptions.showPanels[3] == Constants.PANEL_SHOW);
		frmMegadrummanager.pack();
	}
}

