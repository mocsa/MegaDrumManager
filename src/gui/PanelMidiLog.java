package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.util.Timer;

class LogStore {
	public int note = 0;
	public int level = 0;
	public int timeDiff = 0;
	public Color color = Color.BLUE;
}

public class PanelMidiLog extends JPanel {
	private JPanel panelBars;
	private PanelMidiBar [] panelsMidiBar;  
	private ColumnSpec [] columnSpecs;
	private LogStore [] logStore; 
	private int storePointer = 0;
	private long prevTime;
	private JLabel lblHitsIntervalsmilliseconds;
	private JLabel lblNotesNumbers;
	private PanelMidiLevelBar panelHiHatBar;
	private JLabel lblHihatPosition;
	private JPanel panel;
	private JLabel lblL;
	private JLabel lblL_1;

	/**
	 * Create the panel.
	 */
	public PanelMidiLog() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("center:pref"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:pref:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblHitsIntervalsmilliseconds = new JLabel("hits intervals (milliseconds)");
		lblHitsIntervalsmilliseconds.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(lblHitsIntervalsmilliseconds, "1, 2");
		
		panelBars = new JPanel();
//		panelBars.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//				showNewHit((int)(Math.random()*127),(int)(Math.random()*127), Color.BLUE);
//			}
//		});
		
		lblHihatPosition = new JLabel("HiHat");
		lblHihatPosition.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(lblHihatPosition, "3, 2");
		add(panelBars, "1, 4, fill, fill");
		columnSpecs = new ColumnSpec[Constants.MIDI_BARS_COUNT*2];
		for (int i=0; i<Constants.MIDI_BARS_COUNT;i++) {
			columnSpecs[i*2] = ColumnSpec.decode("1dlu");
			columnSpecs[i*2+1] = FormFactory.DEFAULT_COLSPEC;
		}
//		panelBars.setLayout(new FormLayout(
//				columnSpecs,
//			new RowSpec[] {
//				FormFactory.RELATED_GAP_ROWSPEC,
//				FormFactory.PREF_ROWSPEC,}));
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
		
		panel = new JPanel();
		add(panel, "3, 4, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("center:pref"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("256px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblL = new JLabel("Open");
		lblL.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel.add(lblL, "1, 2, center, default");
		
		panelHiHatBar = new PanelMidiLevelBar();
		panel.add(panelHiHatBar, "1, 4, center, top");
		
		lblL_1 = new JLabel("Closed");
		lblL_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel.add(lblL_1, "1, 6, center, default");
		
		lblNotesNumbers = new JLabel("notes numbers");
		lblNotesNumbers.setFont(new Font("Tahoma", Font.PLAIN, 10));
		add(lblNotesNumbers, "1, 6");
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
		prevTime = System.nanoTime();

	}
	
	public void showNewHit(int note, int level, Color color) {
		int timeDiff;
		storePointer++;
		if (storePointer >= Constants.MIDI_BARS_COUNT) {
			storePointer = 0;
		}
		timeDiff = (int)((System.nanoTime() - prevTime)/1000000);
		prevTime = System.nanoTime();
		logStore[storePointer].timeDiff = timeDiff;
		logStore[storePointer].note = note;
		logStore[storePointer].level = level;
		logStore[storePointer].color = color;
		int p = storePointer;
		for (int i=0;i<Constants.MIDI_BARS_COUNT;i++) {
			panelsMidiBar[Constants.MIDI_BARS_COUNT - i - 1].noteNumber = logStore[p].note; 
			panelsMidiBar[Constants.MIDI_BARS_COUNT - i - 1].level = logStore[p].level;
			panelsMidiBar[Constants.MIDI_BARS_COUNT - i - 1].color = logStore[p].color;
			panelsMidiBar[Constants.MIDI_BARS_COUNT - i - 1].timeDiff = logStore[p].timeDiff;
			panelsMidiBar[Constants.MIDI_BARS_COUNT - i - 1].updateToValues();
			p--;
			if (p<0) {
				p = Constants.MIDI_BARS_COUNT - 1;
			}
		}
	}
	public void showHiHatLevel(int level, Color color) {
		panelHiHatBar.level = level;
		panelHiHatBar.fgColor = color;
		panelHiHatBar.repaint();
	}
}
