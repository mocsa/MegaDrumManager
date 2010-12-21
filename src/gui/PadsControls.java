package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class PadsControls extends JPanel {

	/**
	 * Create the panel.
	 */
	public PadsControls() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(62dlu;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),
				RowSpec.decode("max(215dlu;default):grow"),
				RowSpec.decode("max(43dlu;default):grow"),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JPanel panel_input_selection = new JPanel();
		add(panel_input_selection, "1, 1, fill, fill");
		panel_input_selection.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),}));
		
		JLabel label = new JLabel("Input");
		label.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel_input_selection.add(label, "2, 1, right, default");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_input_selection.add(comboBox, "4, 1, fill, default");
		
		JPanel panel_head_rim = new JPanel();
		add(panel_head_rim, "1, 2, fill, fill");
		panel_head_rim.setLayout(new GridLayout(1, 2, 0, 0));
		
		PadCommonControls panel_head = new PadCommonControls();
		panel_head_rim.add(panel_head);
		panel_head.setBorder(new TitledBorder(null, "Head/Bow", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		PadCommonControls panel_rim = new PadCommonControls();
		panel_head_rim.add(panel_rim);
		panel_rim.setBorder(new TitledBorder(null, "Rim/Edge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		ThirdZoneControls panel_3rd_zone = new ThirdZoneControls();
		add(panel_3rd_zone, "1, 3");
		panel_3rd_zone.setBorder(new TitledBorder(null, "3rd Zone", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_buttons = new JPanel();
		add(panel_buttons, "1, 4, fill, fill");
		
		JButton btnGet = new JButton("Get");
		btnGet.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnGet);
		
		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSend);

	}

}
