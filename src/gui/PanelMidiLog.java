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
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.JSeparator;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class LogStore {
	public int note = 0;
	public int level = 0;
	public int timeDiff = 0;
	public Color color = Color.BLUE;
}

class NoWrapTextPane extends JTextPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4709909057357466382L;

	public boolean getScrollableTracksViewportWidth() {
		// should not allow text to be wrapped
		return false;
	}

	public void appendNaive(Color c, String s) { // naive implementation
		// bad: instiantiates a new AttributeSet object on each call
		SimpleAttributeSet aset = new SimpleAttributeSet();
		StyleConstants.setForeground(aset, c);

		int len = getText().length();
		setCaretPosition(len); // place caret at the end (with no selection)
		setCharacterAttributes(aset, false);
		replaceSelection(s); // there is no selection, so inserts at caret
	}

	public void append(Color c, String s) { // better implementation--uses
		// StyleContext
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
				StyleConstants.Foreground, c);

		int len = getDocument().getLength(); // same value as
		// getText().length();
		setCaretPosition(len); // place caret at the end (with no selection)
		setCharacterAttributes(aset, false);
		replaceSelection(s); // there is no selection, so inserts at caret
	}

//	public static void main(String argv[]) {
//
//		ColorPane pane = new ColorPane();
//		for (int n = 1; n <= 400; n += 1) {
//			if (isPrime(n)) {
//				pane.append(Color.red, String.valueOf(n) + ' ');
//			} else if (isPerfectSquare(n)) {
//				pane.append(Color.blue, String.valueOf(n) + ' ');
//			} else {
//				pane.append(Color.black, String.valueOf(n) + ' ');
//			}
//		}
//
//		JFrame f = new JFrame("ColorPane example");
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setContentPane(new JScrollPane(pane));
//		f.setSize(600, 400);
//		f.setVisible(true);
//	}

	public static boolean isPrime(int n) {
		if (n < 2)
			return false;
		double max = Math.sqrt(n);
		for (int j = 2; j <= max; j += 1)
			if (n % j == 0)
				return false; // j is a factor
		return true;
	}

	public static boolean isPerfectSquare(int n) {
		int j = 1;
		while (j * j < n && j * j > 0)
			j += 1;
		return (j * j == n);
	}

}


public class PanelMidiLog extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3519093945847340399L;
	private JPanel panelBars;
	private PanelMidiBar [] panelsMidiBar;  
	//private ColumnSpec [] columnSpecs;
	private LogStore [] logStore; 
	private int storePointer = 0;
	private long prevTime;
	//private long prevTime2;
	private Short rawLines = 0;
	private ArrayList<String> rawStrings;
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
	private JPanel panel;
	private JComboBox comboBoxBarsCount;
	private JLabel lblBarsNumber;
	
	public int barsCount;
	private JLabel lblHeadHit;
	private JLabel lblRimHit;
	private JLabel lblrdZoneHit;
	private JLabel lblChokeOn;
	private JLabel lblChokeOff;
	private JPanel panelHeadColor;
	private JPanel panelRimColor;
	private JPanel panel3rdColor;
	private JPanel panelChokeOnColor;
	private JPanel panelChokeOffColor;
	private JLabel lblUnknown;
	private JPanel panelUnknownColor;
	private JTabbedPane tabbedPaneMidi;
	private JPanel panelRawMidiTable;
	private JTable tableRawMidi;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModelMidiRaw;
	private JPanel panelRawMidiText;
	private JTextPane txtpnTimeChData;
	private JSeparator separator;
	private JScrollPane scrollPaneText;
	private NoWrapTextPane textPane;


	/**
	 * Create the panel.
	 */
	public PanelMidiLog(int bars) {
		barsCount = bars;
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("top:min"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		tabbedPaneMidi = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPaneMidi, "1, 1, fill, fill");
				panelCombined = new JPanel();
				panelCombined.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
	    		panelMidiScroll.reSetSize(new Dimension(panelBars.getPreferredSize().width, 132));
					}
				});
				tabbedPaneMidi.addTab("Visual MIDI", null, panelCombined, null);
				panelCombined.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.PREF_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("center:pref"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.PREF_COLSPEC,},
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
				//		panelBars.addMouseListener(new MouseAdapter() {
				//			@Override
				//			public void mousePressed(MouseEvent arg0) {
				//				showNewHit((int)(Math.random()*127),(int)(Math.random()*127), Color.BLUE);
				//			}
				//		});
						
						lblHihatPosition = new JLabel("HiHat");
						lblHihatPosition.setFont(new Font("Tahoma", Font.PLAIN, 10));
						panelCombined.add(lblHihatPosition, "3, 2");
						panelCombined.add(panelBars, "1, 4, fill, fill");
						
						panelHiHat = new JPanel();
						panelCombined.add(panelHiHat, "3, 4, fill, fill");
						panelHiHat.setLayout(new FormLayout(new ColumnSpec[] {
								FormFactory.PREF_COLSPEC,},
							new RowSpec[] {
								FormFactory.LINE_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.LINE_GAP_ROWSPEC,
								FormFactory.PREF_ROWSPEC,
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
						
						panel = new JPanel();
						panelCombined.add(panel, "5, 4, fill, fill");
						panel.setLayout(new FormLayout(new ColumnSpec[] {
								FormFactory.PREF_COLSPEC,
								ColumnSpec.decode("2dlu"),
								FormFactory.PREF_COLSPEC,},
							new RowSpec[] {
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.RELATED_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.RELATED_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.RELATED_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.RELATED_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.RELATED_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,
								FormFactory.RELATED_GAP_ROWSPEC,
								FormFactory.DEFAULT_ROWSPEC,}));
						
						comboBoxBarsCount = new JComboBox();
						comboBoxBarsCount.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent arg0) {
						        if (arg0.getStateChange() == ItemEvent.SELECTED) {
						        	barsCount = comboBoxBarsCount.getSelectedIndex()*4 + 16;
						        	reSetPanelBars();
						    		panelMidiScroll.reSetSize(new Dimension(panelBars.getPreferredSize().width, 132));
						    		firePropertyChange("resize", false, true);
						        }
							}
						});
						comboBoxBarsCount.setFont(new Font("Segoe UI", Font.PLAIN, 10));
						comboBoxBarsCount.setModel(new DefaultComboBoxModel(new String[] {"16", "20", "24", "28", "32"}));
						panel.add(comboBoxBarsCount, "1, 1, fill, default");
						
						lblBarsNumber = new JLabel("Bars number");
						lblBarsNumber.setFont(new Font("Segoe UI", Font.PLAIN, 10));
						panel.add(lblBarsNumber, "3, 1");
						
						panelHeadColor = new JPanel();
						panelHeadColor.setBackground(Constants.MD_HEAD_COLOR);
						panel.add(panelHeadColor, "1, 3, fill, fill");
						
						lblHeadHit = new JLabel("Head hit");
						lblHeadHit.setFont(new Font("Segoe UI", Font.PLAIN, 10));
						panel.add(lblHeadHit, "3, 3");
						
						panelRimColor = new JPanel();
						panelRimColor.setBackground(Constants.MD_RIM_COLOR);
						panel.add(panelRimColor, "1, 5, fill, fill");
						
						lblRimHit = new JLabel("Rim hit");
						lblRimHit.setFont(new Font("Segoe UI", Font.PLAIN, 10));
						panel.add(lblRimHit, "3, 5");
						
						panel3rdColor = new JPanel();
						panel3rdColor.setBackground(Constants.MD_3RD_COLOR);
						panel.add(panel3rdColor, "1, 7, fill, fill");
						
						lblrdZoneHit = new JLabel("3rd zone hit");
						lblrdZoneHit.setFont(new Font("Segoe UI", Font.PLAIN, 10));
						panel.add(lblrdZoneHit, "3, 7");
						
						panelChokeOnColor = new JPanel();
						panelChokeOnColor.setBackground(Constants.MD_AFTERTOUCH_ON_COLOR);
						panel.add(panelChokeOnColor, "1, 9, fill, fill");
						
						lblChokeOn = new JLabel("Choke on");
						lblChokeOn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
						panel.add(lblChokeOn, "3, 9");
						
						panelChokeOffColor = new JPanel();
						panelChokeOffColor.setBackground(Constants.MD_AFTERTOUCH_OFF_COLOR);
						panel.add(panelChokeOffColor, "1, 11, fill, fill");
						
						lblChokeOff = new JLabel("Choke off");
						lblChokeOff.setFont(new Font("Segoe UI", Font.PLAIN, 10));
						panel.add(lblChokeOff, "3, 11");
						
						panelUnknownColor = new JPanel();
						panelUnknownColor.setBackground(Constants.MD_UNKNOWN_COLOR);
						panel.add(panelUnknownColor, "1, 13, fill, fill");
						
						lblUnknown = new JLabel("Unknown");
						lblUnknown.setFont(new Font("Segoe UI", Font.PLAIN, 10));
						panel.add(lblUnknown, "3, 13");
						
						lblNotesNumbers = new JLabel("notes numbers");
						lblNotesNumbers.setFont(new Font("Tahoma", Font.PLAIN, 10));
						panelCombined.add(lblNotesNumbers, "1, 6");
						
				panelMidiScroll = new PanelMidiScroll(new Dimension(panelBars.getPreferredSize().width, 128));
				
				panelMidiScroll.reSetSize(new Dimension(panelBars.getPreferredSize().width, 132));
				
				
				tableModelMidiRaw = new DefaultTableModel(
						new Object[][] {
							{null, null, null, null, null, null, null },
						},
						new String[] {
							"Time delta", "Ch #", "Data1", "Data2", "Data3", "Note", "MIDI Message Name"
						}
					) {
						/**
						 * 
						 */
						private static final long serialVersionUID = -4891186049381935054L;
						Class[] columnTypes = new Class[] {
								String.class, String.class, String.class, String.class, String.class, String.class, String.class
						};
						public Class getColumnClass(int columnIndex) {
							return columnTypes[columnIndex];
						}
					};
				
				panelRawMidiText = new JPanel();
				tabbedPaneMidi.addTab("Raw MIDI", null, panelRawMidiText, null);
				panelRawMidiText.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),},
					new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						RowSpec.decode("min(2dlu;default)"),
						RowSpec.decode("top:default:grow"),}));
				
				txtpnTimeChData = new JTextPane();
				txtpnTimeChData.setEditable(false);
				txtpnTimeChData.setText("TimeDelta  Ch#  Data1 Data2 Data3 Note   Message Name ");
				txtpnTimeChData.setFont(new Font("Monospaced", Font.PLAIN, 14));
				panelRawMidiText.add(txtpnTimeChData, "2, 2, fill, fill");
				
				separator = new JSeparator();
				panelRawMidiText.add(separator, "2, 3");
				
				scrollPaneText = new JScrollPane();
				scrollPaneText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panelRawMidiText.add(scrollPaneText, "2, 4, fill, fill");
				
				textPane = new NoWrapTextPane();
				textPane.setBackground(new Color(255, 255, 255));
				textPane.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						textPane.setEditable(false);
					}
				});
				textPane.setFont(new Font("Monospaced", Font.PLAIN, 14));
				scrollPaneText.setViewportView(textPane);
				try {
					textPane.getDocument().remove(0, textPane.getDocument().getLength());
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				panelRawMidiTable = new JPanel();
				panelRawMidiTable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				//Table raw MIDI tab is disabled for now
				//tabbedPaneMidi.addTab("Raw MIDI", null, panelRawMidiTable, null);
				panelRawMidiTable.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.GROWING_BUTTON_COLSPEC,},
					new RowSpec[] {
						RowSpec.decode("fill:default"),}));
				
				scrollPane = new JScrollPane();
				panelRawMidiTable.add(scrollPane, "1, 1, left, top");
				
								tableRawMidi = new JTable();
								scrollPane.setViewportView(tableRawMidi);
								tableRawMidi.setModel(tableModelMidiRaw);
				tableRawMidi.getColumnModel().getColumn(0).setPreferredWidth(74);
				tableRawMidi.getColumnModel().getColumn(1).setPreferredWidth(40);
				tableRawMidi.getColumnModel().getColumn(2).setPreferredWidth(48);
				tableRawMidi.getColumnModel().getColumn(3).setPreferredWidth(48);
				tableRawMidi.getColumnModel().getColumn(4).setPreferredWidth(48);
				tableRawMidi.getColumnModel().getColumn(5).setPreferredWidth(52);
				tableRawMidi.getColumnModel().getColumn(6).setPreferredWidth(161);
				tableModelMidiRaw.removeRow(0);
		
		panelScroll = new JPanel();
		add(panelScroll, "1, 3, fill, fill");
		panelScroll.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
//		panelBars.setLayout(new FormLayout(columnSpecs,
//			new RowSpec[] {
//				FormFactory.RELATED_GAP_ROWSPEC,
//				FormFactory.DEFAULT_ROWSPEC,}));
		
		reSetPanelBars();
		panelMidiScroll.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		panelScroll.add(panelMidiScroll, "1, 1, fill, fill");
		
		panelScrollControls = new JPanel();
		panelScrollControls.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelScroll.add(panelScrollControls, "3, 1, fill, fill");
		panelScrollControls.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("right:pref"),
				ColumnSpec.decode("2dlu"),
				FormFactory.PREF_COLSPEC,},
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
		panelMidiScroll.reSetTimer((Integer)scrollBar.getValue());
		prevTime = System.nanoTime();
		//prevTime2 = System.nanoTime();
		rawStrings = new ArrayList<String>();
	}
	
	public void showNewHit(int note, int level, Color color) {
		int timeDiff;
		storePointer++;
		if (storePointer >= barsCount) {
			storePointer = 0;
		}
		timeDiff = (int)((System.nanoTime() - prevTime)/1000000);
		prevTime = System.nanoTime();
		logStore[storePointer].timeDiff = timeDiff;
		logStore[storePointer].note = note;
		logStore[storePointer].level = level;
		logStore[storePointer].color = color;
		int p = storePointer;
		for (int i=0;i<barsCount;i++) {
			panelsMidiBar[barsCount - i - 1].noteNumber = logStore[p].note; 
			panelsMidiBar[barsCount - i - 1].level = logStore[p].level;
			panelsMidiBar[barsCount - i - 1].color = logStore[p].color;
			panelsMidiBar[barsCount - i - 1].timeDiff = logStore[p].timeDiff;
			panelsMidiBar[barsCount - i - 1].updateToValues();
			p--;
			if (p<0) {
				p = barsCount - 1;
			}
		}
		if (panelMidiScroll != null) {
			panelMidiScroll.showHit(level, Color.BLACK);
		}
	}
	public void showHiHatLevel(int level, Color color) {
		panelHiHatBar.level = level;
		panelHiHatBar.fgColor = color;
		panelHiHatBar.repaint();
	}
	
	public void reSetPanelBars() {
		panelBars.removeAll();
		panelsMidiBar = new PanelMidiBar[barsCount];
		logStore = new LogStore[barsCount];
		for (int i=0; i<barsCount;i++) {
			panelsMidiBar[i] = new PanelMidiBar();
			panelBars.add(panelsMidiBar[i], ((Integer)((i+1)*2)).toString()+", 2");
			logStore[i] = new LogStore();
		}
	}
	
	public void pauseLiveScroll(boolean b) {
		panelMidiScroll.pauseScroll = b;
		tglbtnPause.setSelected(b);
	}
	
	public String getControlChangeType (Integer n) {
		String name = "";
		switch (n) {
		case 0:
			name += "Bank Select";
			break;
		case 1:
			name += "Modulation Wheel";
			break;
		case 2:
			name += "Breath Controller";
			break;
		case 4:
			name += "Foot Controller";
			break;
		case 5:
			name += "Partamento Time";
			break;
		case 6:
			name += "Data Entry MSB";
			break;
		case 7:
			name += "Channel Volume";
			break;
		case 8:
			name += "Balance";
			break;
		case 0xa:
			name += "Pan";
			break;
		case 0xb:
			name += "Expression Ctrl";
			break;
		case 0xc:
			name += "Effect Control 1";
			break;
		case 0xd:
			name += "Effect Control 2";
			break;
		case 0x10:
			name += "General Controller 1";
			break;
		case 0x11:
			name += "General Controller 2";
			break;
		case 0x12:
			name += "General Controller 3";
			break;
		case 0x13:
			name += "General Controller 4";
			break;
		case 0x20:
			name += "LSB Control 0";
			break;
		case 0x21:
			name += "LSB Control 1";
			break;
		case 0x22:
			name += "LSB Control 2";
			break;
		case 0x23:
			name += "LSB Control 3";
			break;
		case 0x24:
			name += "LSB Control 4";
			break;
		case 0x25:
			name += "LSB Control 5";
			break;
		case 0x26:
			name += "LSB Control 6";
			break;
		case 0x27:
			name += "LSB Control 7";
			break;
		case 0x28:
			name += "LSB Control 8";
			break;
		case 0x29:
			name += "LSB Control 9";
			break;
		case 0x2a:
			name += "LSB Control 10";
			break;
		case 0x2b:
			name += "LSB Control 11";
			break;
		case 0x2c:
			name += "LSB Control 12";
			break;
		case 0x2d:
			name += "LSB Control 13";
			break;
		case 0x2e:
			name += "LSB Control 14";
			break;
		case 0x2f:
			name += "LSB Control 15";
			break;
		case 0x30:
			name += "LSB Control 16";
			break;
		case 0x31:
			name += "LSB Control 17";
			break;
		case 0x32:
			name += "LSB Control 18";
			break;
		case 0x33:
			name += "LSB Control 19";
			break;
		case 0x34:
			name += "LSB Control 20";
			break;
		case 0x35:
			name += "LSB Control 21";
			break;
		case 0x36:
			name += "LSB Control 22";
			break;
		case 0x37:
			name += "LSB Control 23";
			break;
		case 0x38:
			name += "LSB Control 24";
			break;
		case 0x39:
			name += "LSB Control 25";
			break;
		case 0x3a:
			name += "LSB Control 26";
			break;
		case 0x3b:
			name += "LSB Control 27";
			break;
		case 0x3c:
			name += "LSB Control 28";
			break;
		case 0x3d:
			name += "LSB Control 29";
			break;
		case 0x3e:
			name += "LSB Control 30";
			break;
		case 0x3f:
			name += "LSB Control 31";
			break;
		case 0x40:
			name += "Damper Pedal";
			break;
		case 0x41:
			name += "Partamento";
			break;
		case 0x42:
			name += "Sostenuto";
			break;
		case 0x43:
			name += "Soft Pedal";
			break;
		case 0x44:
			name += "Legato Footswitch";
			break;
		case 0x45:
			name += "Hold 2";
			break;
		case 0x46:
			name += "Sound Controller 1";
			break;
		case 0x47:
			name += "Sound Controller 2";
			break;
		case 0x48:
			name += "Sound Controller 3";
			break;
		case 0x49:
			name += "Sound Controller 4";
			break;
		case 0x4a:
			name += "Sound Controller 5";
			break;
		case 0x4b:
			name += "Sound Controller 6";
			break;
		case 0x4c:
			name += "Sound Controller 7";
			break;
		case 0x4d:
			name += "Sound Controller 8";
			break;
		case 0x4e:
			name += "Sound Controller 9";
			break;
		case 0x4f:
			name += "Sound Controller 10";
			break;
		case 0x50:
			name += "General Controller 5";
			break;
		case 0x51:
			name += "General Controller 6";
			break;
		case 0x52:
			name += "General Controller 7";
			break;
		case 0x53:
			name += "General Controller 8";
			break;
		case 0x54:
			name += "Partamento Control";
			break;
		case 0x58:
			name += "Velocity Prefix";
			break;
		case 0x5b:
			name += "Effects 1 Depth";
			break;
		case 0x5c:
			name += "Effects 2 Depth";
			break;
		case 0x5d:
			name += "Effects 3 Depth";
			break;
		case 0x5e:
			name += "Effects 4 Depth";
			break;
		case 0x5f:
			name += "Effects 5 Depth";
			break;
		case 0x60:
			name += "Data Increment";
			break;
		case 0x61:
			name += "Data Decrement";
			break;
		case 0x62:
			name += "NRPN LSB";
			break;
		case 0x63:
			name += "NRPN MSB";
			break;
		case 0x64:
			name += "RPN LSB";
			break;
		case 0x65:
			name += "RPN MSB";
			break;
		case 0x78:
			name += "All Sound Off";
			break;
		case 0x79:
			name += "Reset All";
			break;
		case 0x7a:
			name += "Local Control";
			break;
		case 0x7b:
			name += "All Notes Off";
			break;
		case 0x7c:
			name += "Omni Mode Off";
			break;
		case 0x7d:
			name += "Omni Mode On";
			break;
		case 0x7e:
			name += "Mono Mode On";
			break;
		case 0x7f:
			name += "Poly Mode On";
			break;
		default:
			break;
		}
		return name;	
	}
	
	public void addRawMidi (byte [] buffer) {
		String time = "0";
		String ch = "1";
		String d1 = "0";
		String d2 = "";
		String d3 = "";
		String note = "";
		String name = "";
		String appendString = "";
		Color fontColor = Color.black;
		Float timeDiff;
		
		timeDiff = (Float)((float)(System.nanoTime() - prevTime)/1000000000);
		prevTime = System.nanoTime();
		time = String.format("%#9.3f", timeDiff);
				
		if (tableModelMidiRaw.getRowCount() > 200) {
			tableModelMidiRaw.removeRow(0);
		}
		switch (buffer.length) {
		case 1:
			//shortMessage.setMessage(buf[0]);
			break;
		case 2:
			//shortMessage.setMessage(buf[0], buf[1],0);
			switch (buffer[0]&0xf0) {			
			case 0xc0:
				fontColor = Constants.MIDI_PC_COLOR;
				name = "    Program Change";
				break;
			case 0xd0:
				fontColor = Constants.MIDI_CH_PR_COLOR;
				name = "    Channel Pressure";
				break;
			default:
				break;
			}
			note = String.format(" %s% d", Constants.NOTES_NAMES[(buffer[1]&0x7f) % 12], ((buffer[1]&0x7f) / 12) - 1);
			ch = String.format("   %2d", (buffer[0]&0x0f) + 1);
			d1 = String.format("  0x%02x", buffer[0]&0xff);
			d2 = String.format("  0x%02x", buffer[1]&0xff);
			d3 = String.format("      ");
			break;
		default:
			//shortMessage.setMessage(buf[0], buf[1],buf[2]);
			switch (buffer[0]&0xf0) {
			case 0x80:
				fontColor = Constants.MIDI_NOTE_OFF_COLOR;
				name = "    Note Off";
				break;
			case 0x90:
				if (buffer[2] == 0) {
					fontColor = Constants.MIDI_NOTE_OFF_COLOR;
					name = "    Note Off";
				} else {
					name = "    Note On";
					fontColor = Constants.MIDI_NOTE_ON_COLOR;
				}
				break;
			case 0xa0:
				fontColor = Constants.MIDI_AFTERTOUCH_COLOR;
				name = "    Aftertouch";
				break;
			case 0xb0:
				fontColor = Constants.MIDI_CC_COLOR;
				name = "    CC: ";
				name += getControlChangeType(buffer[1]&0x7f);
				break;
			case 0xc0:
				fontColor = Constants.MIDI_PC_COLOR;
				name = "    Program Change";
				break;
			case 0xd0:
				fontColor = Constants.MIDI_CH_PR_COLOR;
				name = "    Channel Pressure";
				break;
			case 0xe0:
				fontColor = Constants.MIDI_PITCH_COLOR;
				name = "    Pitch Wheel";
				break;
			default:
				break;
			}
			note = String.format(" %s% d", Constants.NOTES_NAMES[(buffer[1]&0x7f) % 12], ((buffer[1]&0x7f) / 12) - 1);
			ch = String.format("   %2d", (buffer[0]&0x0f) + 1);
			d1 = String.format("  0x%02x", buffer[0]&0xff);
			d2 = String.format("  0x%02x", buffer[1]&0xff);
			d3 = String.format("  0x%02x", buffer[2]&0xff);
			//note = "     ?";
			break;
		}
		
		appendString = time + ch + d1 + d2 + d3 + note + name;
		tableModelMidiRaw.addRow(new Object [] {time, ch, d1, d2, d3, note, name});//		Document doc = textPane.getDocument();
		JViewport viewport = (JViewport)tableRawMidi.getParent();
		Rectangle rect = tableRawMidi.getCellRect(tableModelMidiRaw.getRowCount(), 0, true);
		Point pt = viewport.getViewPosition();
		rect.setLocation(rect.x - pt.x, rect.y - pt.y);
		viewport.scrollRectToVisible(rect);

		if (rawStrings.size()>10) {
			rawStrings.remove(0);			
		}
		rawStrings.add(appendString);
		
		while (appendString.length() < 70) {
			appendString += " ";
		}
		appendString += "\n\r";
		Document doc = textPane.getDocument();
		textPane.setEditable(true);
		if (rawLines > 100) {
			try {
				doc.remove(0, 72);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//textPane.setCaretPosition(doc.getLength());
		//textPane.replaceSelection(appendString);
		textPane.append(fontColor, appendString);
		rawLines++;
	}
}
