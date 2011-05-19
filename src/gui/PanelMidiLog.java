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
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
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
	public boolean getScrollableTracksViewportWidth() {
		// should not allow text to be wrapped
		return false;
	}
}

public class PanelMidiLog extends JPanel {
	private JPanel panelBars;
	private PanelMidiBar [] panelsMidiBar;  
	private ColumnSpec [] columnSpecs;
	private LogStore [] logStore; 
	private int storePointer = 0;
	private long prevTime;
	private long prevTime2;
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
	private JPanel panelRawMidi;
	private JTable tableRawMidi;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModelMidiRaw;
	private JPanel panelRawMidi2;
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
				
				panelRawMidi = new JPanel();
				panelRawMidi.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				tabbedPaneMidi.addTab("Raw MIDI", null, panelRawMidi, null);
				panelRawMidi.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.GROWING_BUTTON_COLSPEC,},
					new RowSpec[] {
						RowSpec.decode("fill:default"),}));
				
				scrollPane = new JScrollPane();
				panelRawMidi.add(scrollPane, "1, 1, left, top");
				
				
				tableModelMidiRaw = new DefaultTableModel(
						new Object[][] {
							{null, null, null, null, null, null, null },
						},
						new String[] {
							"Time delta", "Ch #", "Data1", "Data2", "Data3", "Note", "MIDI Message Name"
						}
					) {
						Class[] columnTypes = new Class[] {
								String.class, String.class, String.class, String.class, String.class, String.class, String.class
						};
						public Class getColumnClass(int columnIndex) {
							return columnTypes[columnIndex];
						}
					};

				tableRawMidi = new JTable();
				scrollPane.setViewportView(tableRawMidi);
				tableRawMidi.setModel(tableModelMidiRaw);
				
				panelRawMidi2 = new JPanel();
				tabbedPaneMidi.addTab("Raw MIDI", null, panelRawMidi2, null);
				panelRawMidi2.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,},
					new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						RowSpec.decode("min(2dlu;default)"),
						RowSpec.decode("top:default"),}));
				
				txtpnTimeChData = new JTextPane();
				txtpnTimeChData.setEditable(false);
				txtpnTimeChData.setText("TimeDelta  Ch#  Data1 Data2 Data3 Note   Message Name ");
				txtpnTimeChData.setFont(new Font("Monospaced", Font.PLAIN, 11));
				panelRawMidi2.add(txtpnTimeChData, "2, 2, fill, fill");
				
				separator = new JSeparator();
				panelRawMidi2.add(separator, "2, 3");
				
				scrollPaneText = new JScrollPane();
				scrollPaneText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panelRawMidi2.add(scrollPaneText, "2, 4, fill, fill");
				
				textPane = new NoWrapTextPane();
				textPane.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						textPane.setEditable(false);
					}
				});
				textPane.setDisabledTextColor(Color.BLACK);
				//textPane.setContentType("text/html");
				textPane.setFont(new Font("Monospaced", Font.PLAIN, 11));
				scrollPaneText.setViewportView(textPane);
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
		prevTime2 = System.nanoTime();
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
	
	private Color msgToColor(byte [] buffer) {
		Color color = Color.black;
		
		switch (buffer.length) {
		case 1:
			//shortMessage.setMessage(buf[0]);
			break;
		case 2:
			//shortMessage.setMessage(buf[0], buf[1],0);
			break;
		default:
			switch (buffer[0]&0xf0) {
				case 0x80:
					color = Color.yellow;
					break;
				case 0x90:
					if (buffer[1] == 0) {
						color = Color.yellow;
					} else {
						color = Color.green;
					}
					break;
				default:
					break;
			}
		}
		
		return color;
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
		Color fontColor;
		Float timeDiff;
		
		timeDiff = (Float)((float)(System.nanoTime() - prevTime)/1000000000);
		prevTime = System.nanoTime();
		time = String.format("%#9.3f", timeDiff);
		
		fontColor = msgToColor(buffer);
		
		if (tableModelMidiRaw.getRowCount() > 200) {
			tableModelMidiRaw.removeRow(0);
		}
		switch (buffer.length) {
		case 1:
			//shortMessage.setMessage(buf[0]);
			break;
		case 2:
			//shortMessage.setMessage(buf[0], buf[1],0);
			break;
		default:
			//shortMessage.setMessage(buf[0], buf[1],buf[2]);
			ch = String.format("   %2d", (buffer[0]&0x0f) + 1);
			d1 = String.format("  0x%2x", buffer[0]&0xff);
			d2 = String.format("  0x%2x", buffer[1]&0xff);
			d3 = String.format("  0x%2x", buffer[2]&0xff);
			note = "     ?";
			name = "     Name";
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
		
		while (appendString.length() < 60) {
			appendString += " ";
		}
		appendString += "\n\r";
		Document doc = textPane.getDocument();
		textPane.setEditable(true);
		if (rawLines > 100) {
			try {
				doc.remove(0, 62);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		textPane.setCaretPosition(doc.getLength());
		textPane.replaceSelection(appendString);
		rawLines++;
	}
}
