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
import java.awt.Dimension;
import java.awt.Font;
import java.util.Timer;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;

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
	private JPanel panelHiHat;
	private JLabel lblL;
	private JLabel lblL_1;
	private JPanel panelCombined;
	private JPanel panelScroll;
	private PanelMidiScroll panelMidiScroll;
	private JPanel panelScrollControls;
	private JLabel lblScrollSpeed;
	private JCheckBox checkBoxAutoPause;
	private JLabel lblAutoPause;
	private JToggleButton tglbtnPause;
	private JScrollBar scrollBar;

	/**
	 * Create the panel.
	 */
	public PanelMidiLog() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		panelCombined = new JPanel();
		add(panelCombined, "1, 1, left, top");
		panelCombined.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("center:pref:grow"),
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
		panelCombined.add(lblHitsIntervalsmilliseconds, "1, 2");
		
		panelBars = new JPanel();
		panelBars.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				showNewHit((int)(Math.random()*127),(int)(Math.random()*127), Color.BLUE);
			}
		});
		
		lblHihatPosition = new JLabel("HiHat");
		lblHihatPosition.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelCombined.add(lblHihatPosition, "3, 2");
		panelCombined.add(panelBars, "1, 4, fill, fill");
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
		
		panelHiHat = new JPanel();
		panelCombined.add(panelHiHat, "3, 4, fill, fill");
		panelHiHat.setLayout(new FormLayout(new ColumnSpec[] {
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
		panelHiHat.add(lblL, "1, 2, center, default");
		
		panelHiHatBar = new PanelMidiLevelBar();
		panelHiHat.add(panelHiHatBar, "1, 4, center, top");
		
		lblL_1 = new JLabel("Closed");
		lblL_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panelHiHat.add(lblL_1, "1, 6, center, default");
		
		lblNotesNumbers = new JLabel("notes numbers");
		lblNotesNumbers.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelCombined.add(lblNotesNumbers, "1, 6");
		
		panelScroll = new JPanel();
		add(panelScroll, "1, 3, fill, fill");
		panelScroll.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("pref:grow"),}));
		
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

		panelMidiScroll = new PanelMidiScroll(new Dimension(panelBars.getPreferredSize().width, 128));
		
		panelScroll.add(panelMidiScroll, "1, 1, fill, fill");
		
		panelScrollControls = new JPanel();
		panelScroll.add(panelScrollControls, "3, 1, fill, fill");
		panelScrollControls.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("right:pref"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("left:default"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		scrollBar = new JScrollBar();
		scrollBar.setValue(50);
		scrollBar.setPreferredSize(new Dimension(80, 14));
		scrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				panelMidiScroll.reSetTimer((Integer)scrollBar.getValue());

			}
		});
		scrollBar.setMinimum(10);
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		panelScrollControls.add(scrollBar, "2, 2");
		
		lblScrollSpeed = new JLabel("Scroll speed");
		lblScrollSpeed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelScrollControls.add(lblScrollSpeed, "4, 2");
		
		checkBoxAutoPause = new JCheckBox("");
		checkBoxAutoPause.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				panelMidiScroll.autoPause = checkBoxAutoPause.isSelected();
			}
		});
		
		tglbtnPause = new JToggleButton("Pause");
		tglbtnPause.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				panelMidiScroll.pauseScroll = tglbtnPause.isSelected();
			}
		});
		tglbtnPause.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelScrollControls.add(tglbtnPause, "2, 4");
		panelScrollControls.add(checkBoxAutoPause, "2, 6");
		
		lblAutoPause = new JLabel("Auto pause");
		lblAutoPause.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelScrollControls.add(lblAutoPause, "4, 6");
		panelMidiScroll.autoPause = checkBoxAutoPause.isSelected();
		
		panelMidiScroll.reSetSize(new Dimension(panelBars.getPreferredSize().width, 128));
		panelMidiScroll.reSetTimer((Integer)scrollBar.getValue());
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
		panelMidiScroll.showHit(level, Color.BLACK);
	}
	public void showHiHatLevel(int level, Color color) {
		panelHiHatBar.level = level;
		panelHiHatBar.fgColor = color;
		panelHiHatBar.repaint();
	}
}
