package gui;
import javax.swing.JPanel;
import java.awt.Component;

public final class JNoteControl {
	/**
	 * @wbp.factory
	 * @wbp.factory.parameter.source arg0 panel_1
	 * @wbp.factory.parameter.source arg0_1 panel_2
	 */
	public static JPanel createJPanel(Component arg0, Component arg0_1) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(arg0);
		panel.add(arg0_1);
		return panel;
	}
}