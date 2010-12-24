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

public class Main_window {

	private JFrame frmMegadrummanager;
	private Options dialog_options;
	private Midi_handler midi_handler;
	private Timer timer_midi;
	private MiscControls miscControls;
	private PedalControls pedalControls;
	private PadsControls padsControls;
	private ConfigFull configFull;
	private FileManager fileManager;

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

		configFull = new ConfigFull();
		fileManager = new FileManager(frmMegadrummanager);
		dialog_options = new Options();
		midi_handler = new Midi_handler();
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
		mnLoad.add(mntmLoadFromMd);
		
		JMenuItem mntmSendToMd = new JMenuItem("Send to MD");
		mnLoad.add(mntmSendToMd);
		
		JMenuItem mntmLoadFromFile = new JMenuItem("Load from file");
		mntmLoadFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				configFull = fileManager.load_all();
			}
		});
		mnLoad.add(mntmLoadFromFile);
		
		JMenuItem mntmSaveToFile = new JMenuItem("Save to file");
		mntmSaveToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileManager.save_all(configFull);
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
				midi_handler.clear_midi_input();
				midi_handler.request_config_misc();
			}
		});
		mnMiscSettings.add(mntmLoadFromMd_1);
		
		JMenuItem mntmSendToMd_1 = new JMenuItem("Send to MD");
		mntmSendToMd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.config_misc = miscControls.getConfig();
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
		
		JPanel panel_pedal = new JPanel();
		panel_pedal.setBorder(new TitledBorder(null, "HiHat Pedal/Controller", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_pads = new JPanel();
		panel_pads.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Pads", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panel_misc = new JPanel();
		panel_misc.setBorder(new TitledBorder(null, "Misc", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(frmMegadrummanager.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(7)
					.addComponent(panel_misc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_pedal, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel_pads, GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_pedal, GroupLayout.PREFERRED_SIZE, 382, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_pads, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_misc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_misc.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		miscControls = new MiscControls();
		panel_misc.add(miscControls, "1, 2");
		miscControls.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.clear_midi_input();
				midi_handler.request_config_misc();				
			}
		});
		miscControls.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.config_misc = miscControls.getConfig();
				midi_handler.send_config_misc();
			}
		});
		
		panel_pads.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		padsControls = new PadsControls();
		panel_pads.add(padsControls, "1, 1");
		panel_pedal.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		padsControls.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index;
				midi_handler.clear_midi_input();
				index = padsControls.getPadPointer();
				if (index > 0 ) {
					midi_handler.request_config_pad(index + 1);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					midi_handler.request_config_pad(index + 2);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					midi_handler.request_config_3rd((index - 1)/2);
				} else {
					midi_handler.request_config_pad(1);
				}
			}
		});
		padsControls.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index;
				midi_handler.clear_midi_input();
				index = padsControls.getPadPointer();
				if (index > 0 ) {
					midi_handler.config_pad.copyVarsFrom(padsControls.getConfig(index));
					midi_handler.send_config_pad(index + 1);
					midi_handler.config_pad .copyVarsFrom(padsControls.getConfig(index+1));
					midi_handler.send_config_pad(index + 2);
					index = (index - 1)/2;
					midi_handler.config_3rd .copyVarsFrom(padsControls.getConfig3rd(index));
					midi_handler.send_config_3rd(index);
				} else {
					midi_handler.config_pad.copyVarsFrom(padsControls.getConfig(0));
					midi_handler.send_config_pad(1);
				}
			}
		});

		pedalControls = new PedalControls();
		panel_pedal.add(pedalControls, "1, 1");
		pedalControls.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.clear_midi_input();
				midi_handler.request_config_pedal();				
			}
		});
		pedalControls.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				midi_handler.config_pedal = pedalControls.getConfig();
				midi_handler.send_config_pedal();
			}
		});

		frmMegadrummanager.getContentPane().setLayout(groupLayout);
		
	}
	
}

