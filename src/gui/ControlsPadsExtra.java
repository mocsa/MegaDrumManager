package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JComboBox;
//import javax.swing.JLabel;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class ComboBoxRendererWithState implements ListCellRenderer<Object> {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  //private final static Dimension preferredSize = new Dimension(0, 20);
	  private int length = 0;
	  int[]	syncStates;
	  
	  public Component getListCellRendererComponent(JList<?> list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
	    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
	        isSelected, cellHasFocus);
	    if ((syncStates != null) && (index < syncStates.length) && (index > -1)) {
		    switch (syncStates[index]) {
			    case Constants.SYNC_STATE_UNKNOWN:
				    renderer.setForeground(Color.BLUE);
			    	break;
			    case Constants.SYNC_STATE_NOT_SYNCED:
				    renderer.setForeground(Color.RED);
			    	break;
			    case Constants.SYNC_STATE_SYNCED:
				    renderer.setForeground(Color.BLACK);
			    	break;
			    default:
				    renderer.setForeground(Color.BLACK);
			    	break;	    	
		    }	    	
	    }
//	    if ((index == -1) && (length > 0)) {
//	    	renderer.setForeground(Color.YELLOW);
//	    }
	    //renderer.setPreferredSize(preferredSize);
	    return renderer;
	  }
	  
	  public void setLength(int l) {
		length = l;
		if (syncStates != null) {
			syncStates = null;
		}
		syncStates = new int[length];
	  }
	  
	  public void setSyncStateAtIndex(int state, int index) {
		  if ((index < length) && (index > -1)) {
			  syncStates[index] = state;
		  }
	  }
	  
	  public void setSyncNotSyncedAtIndex(boolean synced, int index) {
		  if ((index < length) && (index > -1)) {
			  if (synced) {
				  syncStates[index] = Constants.SYNC_STATE_SYNCED;				  
			  } else {
				  syncStates[index] = Constants.SYNC_STATE_NOT_SYNCED;
			  }
		  }
	  }
}

class ComboBoxEditorWithState extends BasicComboBoxEditor {
	private JLabel label = new JLabel();
	private JPanel panel = new JPanel();
	private Object selectedItem;
	
	public ComboBoxEditorWithState() {
		
		label.setOpaque(false);
		label.setFont(new Font("Arial", Font.BOLD, 24));
		label.setForeground(Color.BLACK);
		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
		panel.add(label);
		panel.setBackground(Color.GREEN);
	}
	
	public Component getEditorComponent() {
		return this.panel;
	}
	
	public Object getItem() {
		return "[" + this.selectedItem.toString() + "]";
	}
	
	public void setItem(Object item) {
		this.selectedItem = item;
		label.setText(item.toString());
	}
	
}

public class ControlsPadsExtra extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9179308222155600591L;

	private Boolean changeEventsAllowed = false;
	
	private CurvesPaint paintPanel;
	private ChangeListener spinnerChangeListener;
	private JSpinner [] spinners;
	private int curvePointer;
	//private int prevCurvePointer;
	private ConfigFull configFull;
	private ConfigFull moduleConfigFull;
	private int customNamePointer;
	//private int prevCustomNamePointer;
	private JComboBox<String> comboBox_curveNumber;
	private JButton button_first;
	private JButton button_prev;
	private JButton button_next;
	private JButton button_last;
	private JButton button_curveGet;
	private JButton button_curveSend;
	private JButton button_curveGetAll;
	private JButton button_curveSendAll;
	private JButton btnCurveLoad;
	private JButton btnCurveSave;
	private JTabbedPane tabbedPane;
	private JPanel panelCurves;
	private JPanel panelCustomNames;
	private JPanel panelNamesGetSend;
	private JPanel panelNamesEdit;
	private JTextField textFieldEditName;
	private JComboBox<String> comboBoxSelectName;
	private LabelWithState lblCustomNames, lblEditSelectedName, lblSelectNameTo, lblwhenModifiedCustom, lblCurve;
	private JComboBox<String> comboBoxCustomNamesCount;
	private JButton button_customNameGet;
	private JButton button_customNameSend;
	private JButton button_customNamesGetAll;
	private JButton button_customNamesSendAll;
	private JButton button_customNameLoad;
	private JButton button_customNameSave;
	private JPanel panel;
	private int selectedCustomName = 0;
	private int textFieldEditNameCaretPos = 0;

	private ComboBoxRendererWithState comboBoxSelectNameRenderer;
	
	/**
	 * Create the panel.
	 */
	public ControlsPadsExtra(ConfigFull config, ConfigFull module) {
		configFull = config;
		moduleConfigFull = module;
        curvePointer = 0;
        //prevCurvePointer = -1;
        
//        customNamesCount = Constants.CUSTOM_NAMES_MAX;
        configFull.customNamesCount = 32;
        customNamePointer = 0;
        //prevCustomNamePointer = -1;

        setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.PREF_COLSPEC,},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("1dlu"),}));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "1, 1");
		
		panelCurves = new JPanel();
		tabbedPane.addTab("Custom Curves", null, panelCurves, null);
		panelCurves.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JPanel panelCurvesGetSend = new JPanel();
		panelCurves.add(panelCurvesGetSend, "1, 1, left, default");
		panelCurvesGetSend.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		button_curveGet = new JButton("Get");
		button_curveGet.setMargin(new Insets(1, 4, 1, 4));
		button_curveGet.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(button_curveGet, "1, 1");
		
		button_curveSend = new JButton("Send");
		button_curveSend.setMargin(new Insets(1, 4, 1, 4));
		button_curveSend.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(button_curveSend, "3, 1");
		
		button_curveGetAll = new JButton("GetAll");
		button_curveGetAll.setMargin(new Insets(1, 0, 1, 0));
		button_curveGetAll.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(button_curveGetAll, "5, 1");
		
		button_curveSendAll = new JButton("SendAll");
		button_curveSendAll.setMargin(new Insets(1, 0, 1, 0));
		button_curveSendAll.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(button_curveSendAll, "7, 1");
		
		btnCurveLoad = new JButton("Load");
		btnCurveLoad.setMargin(new Insets(1, 2, 1, 2));
		btnCurveLoad.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(btnCurveLoad, "9, 1");
		
		btnCurveSave = new JButton("Save");
		btnCurveSave.setMargin(new Insets(1, 2, 1, 2));
		btnCurveSave.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(btnCurveSave, "11, 1");
		
		JPanel panelCurvesSelection = new JPanel();
		panelCurves.add(panelCurvesSelection, "1, 2, left, default");
		panelCurvesSelection.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.PREF_ROWSPEC,}));
		
		lblCurve = new LabelWithState("Curve");
		lblCurve.setFont(new Font("Segoe UI", Font.BOLD, 11));
		panelCurvesSelection.add(lblCurve, "2, 1");
		
		comboBox_curveNumber = new JComboBox<String>();
		comboBox_curveNumber.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
		        	//prevCurvePointer = curvePointer;
		        	curvePointer = comboBox_curveNumber.getSelectedIndex();
		        	updateCurveControls();
		        }
			}
		});
		//comboBox_curveNumber.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		comboBox_curveNumber.setModel(new DefaultComboBoxModel<String>(Constants.CURVES_LIST));
		comboBox_curveNumber.setSelectedIndex(0);
		comboBox_curveNumber.setMaximumRowCount(28);
		comboBox_curveNumber.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesSelection.add(comboBox_curveNumber, "4, 1");
		
		button_first = new JButton("first");
		button_first.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	//prevCurvePointer = curvePointer;
	        	curvePointer = 0;
	        	updateCurveControls();
			}
		});
		button_first.setMargin(new Insets(1, 8, 1, 8));
		button_first.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelCurvesSelection.add(button_first, "6, 1");
		
		button_prev = new JButton("prev");
		button_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	//prevCurvePointer = curvePointer;
	        	if (curvePointer > 0) {
	        		curvePointer--;
	        	}
	        	updateCurveControls();
			}
		});
		button_prev.setMargin(new Insets(1, 6, 1, 6));
		button_prev.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelCurvesSelection.add(button_prev, "8, 1");
		
		button_next = new JButton("next");
		button_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	//prevCurvePointer = curvePointer;
	        	if (curvePointer < (Constants.CURVES_COUNT - 1)) {
	        		curvePointer++;
	        	}
	        	updateCurveControls();
			}
		});
		button_next.setMargin(new Insets(1, 6, 1, 6));
		button_next.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelCurvesSelection.add(button_next, "10, 1");
		
		button_last = new JButton("last");
		button_last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	//prevCurvePointer = curvePointer;
	        	curvePointer = Constants.CURVES_COUNT - 1;
	        	updateCurveControls();
			}
		});
		button_last.setMargin(new Insets(1, 8, 1, 8));
		button_last.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelCurvesSelection.add(button_last, "12, 1");
		
		paintPanel = new CurvesPaint();
		panelCurves.add(paintPanel, "1, 3, left, default");
		paintPanel.yValues = configFull.configCurves[curvePointer].yValues;
		JPanel panelControls = new JPanel();
		panelCurves.add(panelControls, "1, 4, left, default");
		panelControls.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),},
			new RowSpec[] {
				RowSpec.decode("18px"),}));
		
		panelCustomNames = new JPanel();
		tabbedPane.addTab("Custom Names", null, panelCustomNames, null);
		panelCustomNames.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		panelNamesGetSend = new JPanel();
		panelCustomNames.add(panelNamesGetSend, "1, 1, fill, fill");
		panelNamesGetSend.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.PREF_ROWSPEC,}));
		
		lblCustomNames = new LabelWithState("Custom names:");
		lblCustomNames.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelNamesGetSend.add(lblCustomNames, "1, 1, right, default");
		
		comboBoxCustomNamesCount = new JComboBox<String>();
		comboBoxCustomNamesCount.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		comboBoxCustomNamesCount.setModel(new DefaultComboBoxModel<String>(new String[] {"2", "16", "32"}));
		comboBoxCustomNamesCount.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					switch (comboBoxCustomNamesCount.getSelectedIndex()) {
						case 0:
							configFull.customNamesCount = 2;
							break;
						case 1:
							configFull.customNamesCount = 16;
							break;
						default:
							configFull.customNamesCount = 32;
							break;
					}
					if (customNamePointer > configFull.customNamesCount)
					{
						customNamePointer = 0;
				        //prevCustomNamePointer = -1;
					}
					updateCustomNameControls();
				}
			}
		});
		comboBoxCustomNamesCount.setSelectedIndex(2);
		panelNamesGetSend.add(comboBoxCustomNamesCount, "2, 1, fill, default");
		
		button_customNameGet = new JButton("Get");
		button_customNameGet.setMargin(new Insets(1, 4, 1, 4));
		button_customNameGet.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_customNameGet, "4, 1");
		
		button_customNameSend = new JButton("Send");
		button_customNameSend.setMargin(new Insets(1, 4, 1, 4));
		button_customNameSend.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_customNameSend, "6, 1");
		
		button_customNamesGetAll = new JButton("GetAll");
		button_customNamesGetAll.setMargin(new Insets(1, 0, 1, 0));
		button_customNamesGetAll.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_customNamesGetAll, "8, 1");
		
		button_customNamesSendAll = new JButton("SendAll");
		button_customNamesSendAll.setMargin(new Insets(1, 0, 1, 0));
		button_customNamesSendAll.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_customNamesSendAll, "10, 1");
		
		button_customNameLoad = new JButton("Load");
		button_customNameLoad.setMargin(new Insets(1, 2, 1, 2));
		button_customNameLoad.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_customNameLoad, "12, 1");
		
		button_customNameSave = new JButton("Save");
		button_customNameSave.setMargin(new Insets(1, 2, 1, 2));
		button_customNameSave.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_customNameSave, "14, 1");
		
		panelNamesEdit = new JPanel();
		panelCustomNames.add(panelNamesEdit, "1, 3, fill, fill");
		panelNamesEdit.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(151dlu;default):grow"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lblEditSelectedName = new LabelWithState("Edit selected name:");
		panelNamesEdit.add(lblEditSelectedName, "1, 1, right, default");
		
		textFieldEditName = new JTextField();
		textFieldEditName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = textFieldEditName.getText();
				text = text.trim();
				text += "        ";
				text = text.substring(0, 8);
				text = text.trim();
				int ind = comboBoxSelectName.getSelectedIndex();
				configFull.configCustomNames[ind].name = text;
				textFieldEditNameCaretPos = textFieldEditName.getCaretPosition();
				updateCustomNameControls();
				comboBoxSelectName.setSelectedIndex(ind);
				valueCustomNameChanged();
			}
		});
		textFieldEditName.setColumns(10);
		panelNamesEdit.add(textFieldEditName, "3, 1, fill, default");
		
		lblSelectNameTo = new LabelWithState("Select name to edit:");
		panelNamesEdit.add(lblSelectNameTo, "1, 3, right, default");
		
		comboBoxSelectName = new JComboBox<String>();
		comboBoxSelectNameRenderer = new ComboBoxRendererWithState();
		comboBoxSelectNameRenderer.setLength(0);
		comboBoxSelectName.setRenderer(comboBoxSelectNameRenderer);
		//comboBoxSelectName.setEditor(new ComboBoxEditorWithState());
		comboBoxSelectName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					//prevCustomNamePointer = customNamePointer;
					customNamePointer = comboBoxSelectName.getSelectedIndex();
					textFieldEditName.setText(configFull.configCustomNames[comboBoxSelectName.getSelectedIndex()].name);
					selectedCustomName = comboBoxSelectName.getSelectedIndex();
					updateCustomNameSyncState();
				}
			}
		});
		updateCustomNameControls();
		
		panelNamesEdit.add(comboBoxSelectName, "3, 3, fill, default");
		
		panel = new JPanel();
		panelCustomNames.add(panel, "1, 5, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lblwhenModifiedCustom = new LabelWithState("<html>When modified Custom Names will be truncated<br>to 8 charachters maximum</html>");
		panel.add(lblwhenModifiedCustom, "2, 2");

		paintPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				updateYvalues();
			}
		});
		paintPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				updateYvalues();
			}
		});
		//for (int i = 0; i < 4; i++) {
		//	comboBox_curveNumber.addItem(i+1);
		//}
		
		spinnerChangeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int id;
				id = ((CSpinner)arg0.getSource()).getIndex();
				configFull.configCurves[curvePointer].yValues[id] = ((Integer)((CSpinner)arg0.getSource()).getValue());
				//paintPanel.yValues = yValues;
				paintPanel.repaint();
				valueCurveChanged();
			}
		};
		
		spinners = new CSpinner[9];
		for (int i = 0; i < 9; i++ ) {
			spinners[i] = new CSpinner(i);
			spinners[i].setModel(new SpinnerNumberModel(configFull.configCurves[curvePointer].yValues[i], 2, 255, 1));
			spinners[i].addChangeListener(spinnerChangeListener);
			panelControls.add(spinners[i], ((Integer)(i+1)).toString() + ", 1");
		}
		
		changeEventsAllowed = true;
	}    

	private void valueCurveChanged() {
		if (changeEventsAllowed) {
			firePropertyChange("valueCurveChanged", false, true);
		}
		updateCurveSyncState();
	}

	private void updateCurveSyncState() {
		if (configFull.configCurves[curvePointer].syncState == Constants.SYNC_STATE_UNKNOWN ) {
			lblCurve.setSyncState(Constants.SYNC_STATE_UNKNOWN);
		} else {
			lblCurve.setSyncNotSync(true);
			for (int i = 0; i < 9; i++) {
				if (configFull.configCurves[curvePointer].yValues[i] != moduleConfigFull.configCurves[curvePointer].yValues[i]) {
					lblCurve.setSyncNotSync(false);
					break;
				}
			}
		}
	}

	private void updateYvalues() {
		if (spinners != null) {
			for (int i = 0; i < 9; i++) {
				spinners[i].setValue(configFull.configCurves[curvePointer].yValues[i]);
			}
		}
	}
	
	private void updateCurveControls() {
    	paintPanel.yValues = configFull.configCurves[curvePointer].yValues;
    	comboBox_curveNumber.setSelectedIndex(curvePointer);
    	updateYvalues();
    	paintPanel.repaint();
    	updateCurveSyncState();
	}
	
	private void valueCustomNameChanged() {
		if (changeEventsAllowed) {
			firePropertyChange("valueCustomNameChanged", false, true);
		}
		updateCustomNameSyncState();
	}
	
	private void updateCustomNameSyncState() {		
		for (int i = 0; i < configFull.customNamesCount; i++) {
			if (configFull.configCustomNames[i].syncState == Constants.SYNC_STATE_UNKNOWN) {
				comboBoxSelectNameRenderer.setSyncStateAtIndex(Constants.SYNC_STATE_UNKNOWN,i);
			} else {
				comboBoxSelectNameRenderer.setSyncNotSyncedAtIndex(configFull.configCustomNames[i].name.equals(moduleConfigFull.configCustomNames[i].name), i);			
			}
		}
		if (configFull.configCustomNames[selectedCustomName].syncState == Constants.SYNC_STATE_UNKNOWN) {
			textFieldEditName.setForeground(Color.BLUE);
		} else {
			if (configFull.configCustomNames[selectedCustomName].name.equals(moduleConfigFull.configCustomNames[selectedCustomName].name)) {
				textFieldEditName.setForeground(Color.BLACK);
			} else {				
				textFieldEditName.setForeground(Color.RED);
			}
		}
		textFieldEditName.setCaretPosition(textFieldEditNameCaretPos);
	}

	private void updateCustomNameControls() {
		int pointer = customNamePointer;
		if (comboBoxSelectName != null) {
			comboBoxSelectName.removeAllItems();
			for (int i = 0; i < configFull.customNamesCount; i++) {
				comboBoxSelectName.addItem(configFull.configCustomNames[i].name);
			}
			comboBoxSelectNameRenderer.setLength(configFull.customNamesCount);
			comboBoxSelectName.setSelectedIndex(pointer);
			if (changeEventsAllowed) {
				firePropertyChange("CustomNamesChanged", false, true);
			}
			updateCustomNameSyncState();
		}
	}
	
	public void updateControls() {
		changeEventsAllowed = false;
		updateCurveControls();
		updateCustomNameControls();
		changeEventsAllowed = true;		
	}
	
	public int getCurvePointer() {
		return curvePointer;
	}
	public int getCustomNamePointer() {
		return customNamePointer;
	}
	public JButton getButton_curveGet() {
		return button_curveGet;
	}
	public JButton getButton_curveSend() {
		return button_curveSend;
	}
	public JButton getButton_curveGetAll() {
		return button_curveGetAll;
	}
	public JButton getButton_curveSendAll() {
		return button_curveSendAll;
	}
	public JButton getBtnCurveLoad() {
		return btnCurveLoad;
	}
	public JButton getBtnCurveSave() {
		return btnCurveSave;
	}
	public JButton getButton_customNameGet() {
		return button_customNameGet;
	}
	public JButton getButton_customNameSend() {
		return button_customNameSend;
	}
	public JButton getButton_customNamesGetAll() {
		return button_customNamesGetAll;
	}
	public JButton getButton_customNamesSendAll() {
		return button_customNamesSendAll;
	}
	public JButton getButton_customNameLoad() {
		return button_customNameLoad;
	}
	public JButton getButton_customNameSave() {
		return button_customNameSave;
	}
}
