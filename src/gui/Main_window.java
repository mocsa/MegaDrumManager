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

public class Main_window {

	private JFrame frmMegadrummanager;
	private Options dialog_options;
	private Upgrade upgradeDialog;
	private Midi_handler midi_handler;
	private Timer timer_midi;
	private TimerTask midi_in_task;
	private MiscControls miscControls;
	private PedalControls pedalControls;
	private PadsControls padsControls;
	private ConfigFull configFull;
	private ConfigOptions configOptions;
	private FileManager fileManager;
	private int chainId;
	private JCheckBoxMenuItem chckbxmntmShowMisc;
	private JCheckBoxMenuItem chckbxmntmShowHihatPedal;
	private JCheckBoxMenuItem chckbxmntmShowPads;
	private JMenuItem menuItem;
	private JMenu mnView;
	private JProgressBar progressBar;
	private JComboBox comboBox_inputsCount;

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
		frmMegadrummanager.setBounds(100, 100, 991, 705);
		frmMegadrummanager.setLocation(10, 10);
		frmMegadrummanager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		chainId = 0;
		configFull = new ConfigFull();
		fileManager = new FileManager(frmMegadrummanager);
		dialog_options = new Options();
		midi_handler = new Midi_handler();
		upgradeDialog = new Upgrade(midi_handler, fileManager);
		midi_handler.config_chain_id = chainId;
		timer_midi = new Timer();
		midi_in_task = new TimerTask() {
			public void run() {
				midi_handler.get_midi();
				if (midi_handler.config_misc.changed) {
					midi_handler.config_misc.changed = false;
					miscControls.setConfig(midi_handler.config_misc);
				}
				if (midi_handler.config_pedal.changed) {
					midi_handler.config_pedal.changed = false;
					pedalControls.setConfig(midi_handler.config_pedal);
				}
				if (midi_handler.config_pad.changed_pad > 0) {
					padsControls.setConfig(midi_handler.config_pad, midi_handler.config_pad.changed_pad - 1);
					midi_handler.config_pad.changed_pad = 0;
				}
				if (midi_handler.config_3rd.changed_3rd > 0) {
					padsControls.setConfig3rd(midi_handler.config_3rd, midi_handler.config_3rd.changed_3rd - 1);
					midi_handler.config_3rd.changed_3rd = 0;
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
				getPad(padsControls.getPadPointer());
			}
		});
		mnSelectedPadSettings.add(mntmLoadFromMd_4);
		
		JMenuItem mntmSendToMd_4 = new JMenuItem("Send to MD");
		mntmSendToMd_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendPad(padsControls.getPadPointer());
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
		
		chckbxmntmShowMisc = new JCheckBoxMenuItem("Show Misc");
		chckbxmntmShowMisc.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (miscControls != null) {
					miscControls.setVisible(chckbxmntmShowMisc.isSelected());
				}
			}
		});
		chckbxmntmShowMisc.setSelected(true);
		mnView.add(chckbxmntmShowMisc);
		
		chckbxmntmShowHihatPedal = new JCheckBoxMenuItem("Show HiHat Pedal");
		chckbxmntmShowHihatPedal.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (pedalControls != null) {
					pedalControls.setVisible(chckbxmntmShowHihatPedal.isSelected());
				}
			}
		});
		chckbxmntmShowHihatPedal.setSelected(true);
		mnView.add(chckbxmntmShowHihatPedal);
		
		chckbxmntmShowPads = new JCheckBoxMenuItem("Show Pads");
		chckbxmntmShowPads.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (padsControls != null) {
					padsControls.setVisible(chckbxmntmShowPads.isSelected());
				}
			}
		});
		chckbxmntmShowPads.setSelected(true);
		mnView.add(chckbxmntmShowPads);
		
		JPanel panel_main = new JPanel();
		panel_main.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("fill:498px:grow"),}));
		
		miscControls = new MiscControls();
		miscControls.setBorder(new TitledBorder(null, "Misc", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_main.add(miscControls, "1, 2, default, top");
		miscControls.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getMisc();
			}
		});
		miscControls.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMisc();
			}
		});
		
				pedalControls = new PedalControls();
				pedalControls.setBorder(new TitledBorder(null, "HiHat Pedal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_main.add(pedalControls, "2, 2, default, top");
				pedalControls.getBtnGet().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						getPedal();
					}
				});
				pedalControls.getBtnSend().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						sendPedal();
					}
				});
		
		padsControls = new PadsControls();
		padsControls.getBtnSendall().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendAllPads();
			}
		});
		padsControls.getBtnGetall().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAllPads();
			}
		});
		padsControls.setBorder(new TitledBorder(null, "Pads", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_main.add(padsControls, "3, 2, default, top");
		padsControls.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getPad(padsControls.getPadPointer());
			}
		});
		padsControls.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendPad(padsControls.getPadPointer());
			}
		});
		frmMegadrummanager.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("757px:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("1dlu"),
				RowSpec.decode("417px:grow"),}));
		
		JPanel panel = new JPanel();
		frmMegadrummanager.getContentPane().add(panel, "1, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
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
				RowSpec.decode("12dlu"),}));
		
		JButton btnGetAll = new JButton("Get All");
		btnGetAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAll();
			}
		});
		panel.add(btnGetAll, "2, 1");
		
		JButton btnSendAll = new JButton("Send All");
		btnSendAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendAll();
			}
		});
		panel.add(btnSendAll, "4, 1");
		
		JButton btnLoadAll = new JButton("Load All");
		btnLoadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load_all();
			}
		});
		panel.add(btnLoadAll, "6, 1");
		
		JButton btnSaveAll = new JButton("Save All");
		btnSaveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save_all();
			}
		});
		panel.add(btnSaveAll, "8, 1");
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		
		JLabel lblInputs = new JLabel("Inputs:");
		panel.add(lblInputs, "10, 1, right, default");
		
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
		panel.add(comboBox_inputsCount, "12, 1, left, fill");
		
		progressBar.setStringPainted(true);
		panel.add(progressBar, "14, 1");
		frmMegadrummanager.getContentPane().add(panel_main, "1, 3, left, fill");
		
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
		delayMs(30);
	}
	
	private void sendPedal() {
		midi_handler.config_pedal = pedalControls.getConfig();
		midi_handler.send_config_pedal();
		delayMs(30);
	}
	
	private void getMisc() {
		midi_handler.clear_midi_input();
		midi_handler.request_config_misc();					
		delayMs(30);
	}
	
	private void sendMisc() {
		midi_handler.config_misc = miscControls.getConfig();
		midi_handler.send_config_misc();	
		delayMs(30);
	}
	
	private void getPad(int pad_id) {
		midi_handler.clear_midi_input();
		if ( pad_id > 0 ) {
			midi_handler.request_config_pad(pad_id + 1);
			delayMs(30);
			midi_handler.request_config_pad(pad_id + 2);
			delayMs(30);
			midi_handler.request_config_3rd((pad_id - 1)/2);
			delayMs(30);
		} else {
			midi_handler.request_config_pad(1);
			delayMs(30);
		}
	}
	
	private void sendPad(int pad_id) {
		if (pad_id > 0 ) {
			midi_handler.config_pad.copyVarsFrom(padsControls.getConfig(pad_id));
			midi_handler.send_config_pad(pad_id + 1);
			delayMs(30);
			midi_handler.config_pad .copyVarsFrom(padsControls.getConfig(pad_id+1));
			midi_handler.send_config_pad(pad_id + 2);
			delayMs(30);
			pad_id = (pad_id - 1)/2;
			midi_handler.config_3rd .copyVarsFrom(padsControls.getConfig3rd(pad_id));
			midi_handler.send_config_3rd(pad_id);
			delayMs(30);
		} else {
			midi_handler.config_pad.copyVarsFrom(padsControls.getConfig(0));
			midi_handler.send_config_pad(1);
			delayMs(30);
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

	private void getAll() {
		getMisc();
		getPedal();
		getAllPads();
	}
	
	private void sendAll() {
		sendMisc();
		sendPedal();
		sendAllPads();
	}

	private void load_all() {
		configFull = fileManager.load_all(configFull, configOptions);
		miscControls.loadFromConfigFull(configFull);
		pedalControls.loadFromConfigFull(configFull);
		padsControls.loadFromConfigFull(configFull);		
	}
	
	private void save_all() {
		miscControls.copyToConfigFull(configFull, configOptions.chainId);
		pedalControls.copyToConfigFull(configFull, configOptions.chainId);
		padsControls.copyToConfigFull(configFull, configOptions.chainId);
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
		miscControls.copyToConfigFull(configFull, configOptions.chainId);
		pedalControls.copyToConfigFull(configFull, configOptions.chainId);
		padsControls.copyToConfigFull(configFull, configOptions.chainId);
		if (!configOptions.lastFullPathConfig.equals("")) {
			configFull = fileManager.loadAllSilent(configFull, configOptions);
			miscControls.loadFromConfigFull(configFull);
			pedalControls.loadFromConfigFull(configFull);
			padsControls.loadFromConfigFull(configFull);		
		}
	}
	
	private void saveAndExit() {
		midi_handler.closeAllPorts();
		dialog_options.saveOptionsTo(configOptions);
		if (configOptions.saveOnExit) {
			fileManager.saveLastOptions(configOptions);
		}
		System.exit(0);
	}
	
	private void updateInputsCountControls() {
		pedalControls.updateInputCountsControls(configOptions.inputsCount);
		padsControls.updateInputCountsControls(configOptions.inputsCount);
	}
	
}

