package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Options extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	public JComboBox comboBox_MIDI_In;
	public JComboBox comboBox_MIDI_Out;
	public int midi_port_out;
	public int midi_port_in;
	public boolean config_applied; 

	/**
	 * Create the dialog.
	 */
	public Options() {
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setName("dialog_options");
		setTitle("Options");
		setBounds(100, 100, 495, 317);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel_midi = new JPanel();
		panel_midi.setBorder(new TitledBorder(null, "MIDI Ports", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_midi.setBounds(0, 0, 269, 144);
		contentPanel.add(panel_midi);
		panel_midi.setLayout(null);
		
		comboBox_MIDI_Out = new JComboBox();
		comboBox_MIDI_Out.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				midi_port_out = comboBox_MIDI_Out.getSelectedIndex();
			}
		});
		comboBox_MIDI_Out.setBounds(62, 73, 197, 20);
		panel_midi.add(comboBox_MIDI_Out);
		
		comboBox_MIDI_In = new JComboBox();
		comboBox_MIDI_In.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				midi_port_in = comboBox_MIDI_In.getSelectedIndex();
			}
		});
		comboBox_MIDI_In.setBounds(62, 48, 197, 20);
		panel_midi.add(comboBox_MIDI_In);
		
		JCheckBox chckbxUseSameInout = new JCheckBox("Use same In/Out port");
		chckbxUseSameInout.setBounds(6, 18, 145, 23);
		panel_midi.add(chckbxUseSameInout);
		chckbxUseSameInout.setHorizontalTextPosition(SwingConstants.LEADING);
		chckbxUseSameInout.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		JLabel lblMidiIn = new JLabel("Midi In");
		lblMidiIn.setBounds(6, 51, 46, 14);
		panel_midi.add(lblMidiIn);
		
		JLabel lblMidiOut = new JLabel("Midi Out");
		lblMidiOut.setBounds(6, 76, 46, 14);
		panel_midi.add(lblMidiOut);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						config_applied = true;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
