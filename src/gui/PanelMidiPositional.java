package gui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PanelMidiPositional extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6662020825816232163L;
	private JSlider posIndicator1;
	private JSlider posIndicator2;
	private JSlider posIndicator3;
	private JSlider [] posIndicators;
	private static int posIndicatorsCount = 5;
	private int posPointer = 0;
	private int [] posValues;

	/**
	 * Create the panel.
	 */
	public PanelMidiPositional() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("100dlu:grow"),},
			new RowSpec[] {
				RowSpec.decode("16dlu"),
				RowSpec.decode("16px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("16px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("16px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("16px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("16px"),}));
		
		JPanel panel = new JPanel();
		add(panel, "1, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblR = new JLabel("Center");
		panel.add(lblR, "2, 2");
		
		JLabel lblLastPositional = new JLabel("Last Positional");
		lblLastPositional.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblLastPositional, "4, 2");
		
		JLabel lblCenter = new JLabel("Rim");
		panel.add(lblCenter, "6, 2");
		
		posIndicators = new JSlider[posIndicatorsCount];
		posValues = new int[posIndicatorsCount];
		for (int i = 0; i < posIndicatorsCount; i++) {
			posValues[i] = 0;
			posIndicators[i] = new JSlider();
			posIndicators[i].setEnabled(false);
			add(posIndicators[i], "1, " + Integer.toString(i*2 +2) + ", left, top");
		}
/*		
		posIndicator1 = new JSlider();
		posIndicator1.setEnabled(false);
		add(posIndicator1, "1, 2, left, top");
		
		posIndicator2 = new JSlider();
		posIndicator2.setEnabled(false);
		add(posIndicator2, "1, 4, left, top");
		
		posIndicator3 = new JSlider();
		posIndicator3.setEnabled(false);
		add(posIndicator3, "1, 6, left, top");
*/
		setPosIndicators();
	}

	private void setPosIndicators() {
		int p;
		p = posPointer;
		for (int i = 0; i < posIndicatorsCount; i++) {
			posIndicators[i].setValue(posValues[p]);
			posIndicators[i].putClientProperty("JSlider.isFilled", Boolean.FALSE);
			p--;
			if (p < 0) p = posIndicatorsCount - 1;
		}
	}
	public void setPosValue(int posValue) {
		posValue = (posValue*100)/127;
		posPointer++;
		if (posPointer >= posIndicatorsCount) posPointer = 0;
		posValues[posPointer] = posValue;
		setPosIndicators();
	}
}
