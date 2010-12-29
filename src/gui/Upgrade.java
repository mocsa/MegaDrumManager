package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.Dialog.ModalityType;

public class Upgrade extends JDialog {
	private Midi_handler midi_handler;
	private JTextField textField_fileName;
	private FileManager fileManager;
	private File file;
	private ConfigOptions configOptions;
	private JButton btnStart;
	private JButton btnCancel;
	private JButton btnClose;
	
	/**
	 * Create the panel.
	 */
	public Upgrade(Midi_handler mh, FileManager fm) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		midi_handler = mh;
		fileManager = fm;
		file = null;
		setResizable(false);
		setName("upgradeDialog");
		setTitle("MegaDrum Firmware Upgrade");
		setBounds(100, 100, 495, 254);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JTextPane txtpnSelectThe = new JTextPane();
		txtpnSelectThe.setText("1. Select the MegaDrum firmware file.\r\n2. Disconnect (power off) MegaDrum.\r\n3. While holding MegaDrum's button LEFT connect (power on) MegaDrum. \r\n4. By pressing MegaDrum's button UP select a correct MegaDrum crystal frequency.\r\n5. Press MegaDrum's button DOWN. MegaDrum LCD will show 'StartUpdateOnPC'.\r\n6. Click button Start.\r\n7. Wait for the upgrade to finish.\r\n8. Click button Close.");
		getContentPane().add(txtpnSelectThe, "2, 2, fill, fill");
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "2, 4, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblMegadrumFirmware = new JLabel("MegaDrum Firmware:");
		panel_1.add(lblMegadrumFirmware, "2, 2, right, default");
		
		textField_fileName = new JTextField();
		panel_1.add(textField_fileName, "4, 2, fill, default");
		textField_fileName.setColumns(10);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				file = fileManager.selectFirmwareFile(configOptions);
				if (file != null) {
					textField_fileName.setText(file.getAbsolutePath());
					btnStart.setEnabled(true);
				}
			}
		});
		panel_1.add(btnOpen, "6, 2");
		
		JProgressBar progressBar = new JProgressBar();
		getContentPane().add(progressBar, "2, 6");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "2, 8, fill, fill");
		
		btnStart = new JButton("Start");
		btnStart.setEnabled(false);
		panel.add(btnStart);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setEnabled(false);
		panel.add(btnCancel);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStart.setEnabled(false);
				btnCancel.setEnabled(false);
				setVisible(false);
			}
		});
		panel.add(btnClose);
	}
	
	public void setConfigOptions(ConfigOptions config) {
		configOptions = config;
	}

}
