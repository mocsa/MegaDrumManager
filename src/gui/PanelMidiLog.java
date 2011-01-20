package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class LogStore {
	public int note = 0;
	public int level = 0;
	public int time = 0;
}

public class PanelMidiLog extends JPanel {
	private JPanel panelBars;
	private PanelMidiBar [] panelsMidiBar;  
	private ColumnSpec [] columnSpecs;
	private LogStore [] logStore; 
	private int storePointer = 0;
	private JLabel label;
	private JLabel label_1;

	/**
	 * Create the panel.
	 */
	public PanelMidiLog() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		label = new JLabel("New label");
		add(label, "1, 2");
		
		panelBars = new JPanel();
		panelBars.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				showNewHit((int)(Math.random()*127),(int)(Math.random()*127));
			}
		});
		add(panelBars, "1, 4, fill, fill");
		columnSpecs = new ColumnSpec[Constants.MIDI_BARS_COUNT*2];
		for (int i=0; i<Constants.MIDI_BARS_COUNT;i++) {
			columnSpecs[i*2] = ColumnSpec.decode("1dlu");
			columnSpecs[i*2+1] = FormFactory.DEFAULT_COLSPEC;
		}
		panelBars.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,}));
		
		label_1 = new JLabel("New label");
		add(label_1, "1, 6");
//		panelBars.setLayout(new FormLayout(columnSpecs,
//			new RowSpec[] {
//				FormFactory.RELATED_GAP_ROWSPEC,
//				FormFactory.DEFAULT_ROWSPEC,}));
		
		panelsMidiBar = new PanelMidiBar[Constants.MIDI_BARS_COUNT];
		logStore = new LogStore[Constants.MIDI_BARS_COUNT];
		for (int i=0; i<Constants.MIDI_BARS_COUNT;i++) {
			panelsMidiBar[i] = new PanelMidiBar();
			panelBars.add(panelsMidiBar[i], ((Integer)((i+1)*2)).toString()+", 2");
			logStore[i] = new LogStore();
		}
		//panelBars.add(panelsMidiBar[0], "2, 2");

	}
	
	public void showNewHit(int note, int level) {
		storePointer++;
		if (storePointer >= Constants.MIDI_BARS_COUNT) {
			storePointer = 0;
		}
		logStore[storePointer].note = note;
		logStore[storePointer].level = level;
		int p = storePointer;
		for (int i=0;i<Constants.MIDI_BARS_COUNT;i++) {
			panelsMidiBar[Constants.MIDI_BARS_COUNT - i - 1].noteNumber = logStore[p].note; 
			panelsMidiBar[Constants.MIDI_BARS_COUNT - i - 1].level = logStore[p].level;
			panelsMidiBar[Constants.MIDI_BARS_COUNT - i - 1].updateToValues();
			p--;
			if (p<0) {
				p = Constants.MIDI_BARS_COUNT - 1;
			}
		}
	}

}
