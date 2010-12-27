package gui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileFilter;

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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Main_window {

	private JFrame frmMegadrummanager;
	private Options dialog_options;
	private Midi_handler midi_handler;
	private Timer timer_midi;
	private MiscControls miscControls;
	private PedalControls pedalControls;
	private PadsControls padsControls;
	private ConfigFull configFull;
	private ConfigOptions configOptions;
	private FileManager fileManager;
	private int chainId;
	private JCheckBox chckbxShowMisc;
	private JCheckBox chckbxShowHihatPedal;
	private JCheckBox chckbxShowPads;
	private JMenuItem menuItem;
	private JMenu mnView;

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
		System.out.printf("\nMIDI In ports:\n");
		for (String string : midi_handler.getMidiInList()) {
			System.out.printf("%s\n", string);
			}
		System.out.printf("\nMIDI Out ports:\n");
		for (String string : midi_handler.getMidiOutList()) {
			System.out.printf("%s\n", string);
			}
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
		frmMegadrummanager.setResizable(false);
		frmMegadrummanager.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				saveAndExit();
			}
		});
		frmMegadrummanager.setTitle("MegaDrumManager");
		frmMegadrummanager.setBounds(100, 100, 929, 705);
		frmMegadrummanager.setLocation(10, 10);
		frmMegadrummanager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		chainId = 0;
		configFull = new ConfigFull();
		fileManager = new FileManager(frmMegadrummanager);
		dialog_options = new Options();
		midi_handler = new Midi_handler();
		midi_handler.config_chain_id = chainId;
		timer_midi = new Timer();
		timer_midi.scheduleAtFixedRate(
				new TimerTask() {
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
				}
				, 1000, 1);
		
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
		
		chckbxShowMisc = new JCheckBox("Show Misc");
		chckbxShowMisc.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (miscControls != null) {
					miscControls.setVisible(chckbxShowMisc.isSelected());
				}
			}
		});
		chckbxShowMisc.setSelected(true);
		mnView.add(chckbxShowMisc);
		
		chckbxShowHihatPedal = new JCheckBox("Show HiHat Pedal");
		chckbxShowHihatPedal.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (pedalControls != null) {
					pedalControls.setVisible(chckbxShowHihatPedal.isSelected());
				}
			}
		});
		chckbxShowHihatPedal.setSelected(true);
		mnView.add(chckbxShowHihatPedal);
		
		chckbxShowPads = new JCheckBox("Show Pads");
		chckbxShowPads.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (padsControls != null) {
					padsControls.setVisible(chckbxShowPads.isSelected());
				}
			}
		});
		chckbxShowPads.setSelected(true);
		mnView.add(chckbxShowPads);
		
//		menuItem = new JMenuItem("New menu item");
//		menuItem.setSelected(true);
//		menuItem.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				if (miscControls != null) {
//					miscControls.setVisible(menuItem.isSelected());
//				}
//			}
//		});
//		mnView.add(menuItem);
		
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
		int i;
		for (i = 0; i<Constants.PADS_COUNT;i++) {
			getPad(i);
		}
	}
	
	private void sendAllPads() {
		int i;
		for (i = 0; i<Constants.PADS_COUNT;i++) {
			sendPad(i);
		}
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
		configFull = fileManager.load_all();
		miscControls.loadFromConfigFull(configFull);
		pedalControls.loadFromConfigFull(configFull);
		padsControls.loadFromConfigFull(configFull);		
	}
	
	private void save_all() {
		miscControls.copyToConfigFull(configFull, chainId);
		pedalControls.copyToConfigFull(configFull, chainId);
		padsControls.copyToConfigFull(configFull, chainId);
		fileManager.save_all(configFull);
	}
	
	private void loadConfig() {
		configOptions = new ConfigOptions(); // default options loaded with new
		configOptions  = fileManager.loadLastOptions(configOptions);
		dialog_options.loadOptionsFrom(configOptions);
	}
	
	private void saveAndExit() {
		midi_handler.Close_all_ports();
		dialog_options.saveOptionsTo(configOptions);
		if (configOptions.saveOnExit) {
			fileManager.saveLastOptions(configOptions);
		}
		System.exit(0);
	}
	
}

