package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import com.jgoodies.forms.layout.Sizes;

class ZoneButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7344379698600413547L;
	private String name;
	/**
	 * Create the panel.
	 */
	public ZoneButton(String text) {
		name = text;
		setIcon(new ImageIcon(ControlsPadCommon.class.getResource("/icons12x12/46.png")));
		setToolTipText("Copy this 3rd zone setting to All 3rd zones");
	}

	public String getName() {
		return name;
	}
}

public class ThirdZoneControls extends JPanel implements ValueChangedListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2928331841555596211L;

	//private Boolean changeEventsAllowed = false;
	private Boolean controlsInited = false;

	private NoteSpinControl noteSpinControl_note;
	private NoteSpinControl noteSpinControl_altNote;
	private NoteSpinControl noteSpinControl_pressrollNote;
	private NoteSpinControl noteSpinControl_dampenedNote;
	private JSpinnerCustom spinner_threshold;
	private JSpinnerCustom spinner_midPointWidth;
	private JSliderCustom slider_midPoint;

	//private Config3rd config3rd;
	private ConfigFull configFull;
	private int	configIndex = 0;
	//private boolean inUpdate = false;

	//private ArrayList<Object> controls; 
	private ZoneButton zoneButton_note;
	private ZoneButton zoneButton_altNote;
	private ZoneButton zoneButton_pressrollNote;
	private ZoneButton zoneButton_dampenedNote;
	private ZoneButton zoneButton_threshold;
	private ZoneButton zoneButton_midwidth;
	private ZoneButton zoneButton_midpoint;
	
	public String pressedPadButtonName;

	/**
	 * Create the panel.
	 */
	public ThirdZoneControls(ConfigFull config) {
		configFull = config;
		configIndex = 0;
		//controls = new ArrayList<Object>();
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:default"),
				ColumnSpec.decode("3px"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("3px"),
				new ColumnSpec(ColumnSpec.FILL, Sizes.bounded(Sizes.PREFERRED, Sizes.constant("12dlu", true), Sizes.constant("12dlu", true)), 0),
				ColumnSpec.decode("10dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("3px"),
				ColumnSpec.decode("60dlu"),
				ColumnSpec.decode("3px"),
				new ColumnSpec(ColumnSpec.FILL, Sizes.bounded(Sizes.PREFERRED, Sizes.constant("12dlu", true), Sizes.constant("12dlu", true)), 0),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblNote, "1, 1, right, default");
		
		noteSpinControl_note = new NoteSpinControl(configFull);
		noteSpinControl_note.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (noteSpinControl_altNote.getCheckBox().isSelected()) {
					noteSpinControl_altNote.setValueWithoutEvents(noteSpinControl_note.getValue());
				}
				if (noteSpinControl_pressrollNote.getCheckBox().isSelected()) {
					noteSpinControl_pressrollNote.setValueWithoutEvents(noteSpinControl_note.getValue());
				}
			}
		});
		//controls.add(noteSpinControl_note.getSpinner());
		add(noteSpinControl_note, "3, 1, fill, fill");
		
		zoneButton_note = new ZoneButton("note");
		add(zoneButton_note, "5, 1");
		
		JLabel lblMidpoint = new JLabel("MidPoint");
		lblMidpoint.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMidpoint, "7, 1, right, default");
		
		slider_midPoint = new JSliderCustom();
		//controls.add(slider_midPoint);
		slider_midPoint.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (configFull != null) {
					configFull.config3rds[configIndex].threshold = (configFull.config3rds[configIndex].threshold&0x0f)|((slider_midPoint.getValue()&0x0f)<<4);
					updateControls();					
				}
			}
		});
		slider_midPoint.setPaintLabels(true);
		slider_midPoint.setSnapToTicks(true);
		slider_midPoint.setPaintTicks(true);
		slider_midPoint.setMinorTickSpacing(1);
		slider_midPoint.setValue(7);
		slider_midPoint.setMaximum(15);
		add(slider_midPoint, "9, 1, fill, fill");
		
		zoneButton_midpoint = new ZoneButton("midpoint");
		add(zoneButton_midpoint, "11, 1");
		
		JLabel lblAltNote = new JLabel("Alt Note");
		lblAltNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblAltNote, "1, 2, right, default");
		
		noteSpinControl_altNote = new NoteSpinControl(configFull);
		noteSpinControl_altNote.getCheckBox().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noteSpinControl_altNote.getSpinner().setEnabled(!noteSpinControl_altNote.getCheckBox().isSelected());
				if (noteSpinControl_altNote.getCheckBox().isSelected()) {
					noteSpinControl_altNote.setValueWithoutEvents(noteSpinControl_note.getValue());
				}
			}
		});
		noteSpinControl_altNote.getCheckBox().setVisible(true);
		noteSpinControl_altNote.getCheckBox().setToolTipText("Linked to Note");
		//controls.add(noteSpinControl_altNote.getSpinner());
		add(noteSpinControl_altNote, "3, 2, fill, fill");
		
		zoneButton_altNote = new ZoneButton("altNote");
		add(zoneButton_altNote, "5, 2");
		
		JLabel lblMidpointWidth = new JLabel("MidPoint Width");
		lblMidpointWidth.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMidpointWidth, "7, 2, right, default");
		
		spinner_midPointWidth = new JSpinnerCustom(this);
		//controls.add(spinner_midPointWidth);
		spinner_midPointWidth.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configFull.config3rds[configIndex].threshold = (configFull.config3rds[configIndex].threshold&0xf0)|((Integer)spinner_midPointWidth.getValue()&0x0f);
				updateControls();
			}
		});				

		spinner_midPointWidth.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(15), new Integer(1)));
		add(spinner_midPointWidth, "9, 2, left, fill");
		
		zoneButton_midwidth = new ZoneButton("midwidth");
		add(zoneButton_midwidth, "11, 2");
		
		JLabel lblPressrollNote = new JLabel("Pressroll Note");
		lblPressrollNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblPressrollNote, "1, 3, right, default");
		
		noteSpinControl_pressrollNote = new NoteSpinControl(configFull);
		noteSpinControl_pressrollNote.getCheckBox().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noteSpinControl_pressrollNote.getSpinner().setEnabled(!noteSpinControl_pressrollNote.getCheckBox().isSelected());
				if (noteSpinControl_pressrollNote.getCheckBox().isSelected()) {
					noteSpinControl_pressrollNote.setValueWithoutEvents(noteSpinControl_note.getValue());
				}
			}
		});
		noteSpinControl_pressrollNote.getCheckBox().setVisible(true);
		noteSpinControl_pressrollNote.getCheckBox().setToolTipText("Linked to Note");
		//controls.add(noteSpinControl_pressrollNote.getSpinner());
		add(noteSpinControl_pressrollNote, "3, 3, fill, fill");
		
		zoneButton_pressrollNote = new ZoneButton("pressrollNote");
		add(zoneButton_pressrollNote, "5, 3");
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblThreshold, "7, 3, right, default");
		
		spinner_threshold = new JSpinnerCustom(this);
		//controls.add(spinner_threshold);
		spinner_threshold.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				configFull.config3rds[configIndex].threshold = (Integer)spinner_threshold.getValue();
				updateControls();
			}
		});				
		spinner_threshold.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(255), new Integer(1)));
		add(spinner_threshold, "9, 3, left, fill");
		
		zoneButton_threshold = new ZoneButton("threshold");
		add(zoneButton_threshold, "11, 3");
		
		JLabel lblDampenedNote = new JLabel("Dampened Note");
		lblDampenedNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblDampenedNote, "1, 4");
		
		noteSpinControl_dampenedNote = new NoteSpinControl(configFull);
		//noteSpinControl_dampenedNote.getCheckBox().setVisible(true);
		//controls.add(noteSpinControl_dampenedNote.getSpinner());
		add(noteSpinControl_dampenedNote, "3, 4, fill, fill");
		
		zoneButton_dampenedNote = new ZoneButton("dampenedNote");
		add(zoneButton_dampenedNote, "5, 4");

		for (Object control: this.getComponents()) {
			//System.out.printf("Component -> %s\n",control.getClass().toString());
			if (control.getClass().equals(JSliderCustom.class)) {
				((JSliderCustom) control).setValueChangedListener(this);
			} else if (control.getClass().equals (NoteSpinControl.class)) {
				NoteSpinControl noteSpinControl = ((NoteSpinControl) control);
				noteSpinControl.setEventListener(this);
			} else if (control.getClass().equals(JSpinner.class)) {
				((JSpinner) control).setFont(new Font("Tahoma", Font.PLAIN, 11));
			} else if (control.getClass().equals(ZoneButton.class)) {
				((ZoneButton)control).addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//System.out.printf("Button -> %s\n",((ZoneButton)arg0.getSource()).getName());
						pressedPadButtonName = ((ZoneButton)arg0.getSource()).getName();
						firePropertyChange("copyButton", false, true);
					}
				});
			}
		}
		
		controlsInited = true;
	}

	public void valueChanged() {
		if (noteSpinControl_altNote.getCheckBox().isSelected()) {
			noteSpinControl_altNote.setValueWithoutEvents(noteSpinControl_note.getValue());
		}
		if (noteSpinControl_pressrollNote.getCheckBox().isSelected()) {
			noteSpinControl_pressrollNote.setValueWithoutEvents(noteSpinControl_note.getValue());
		}
		updateConfig();
		firePropertyChange("valueChanged", false, true);
		//updateControls();
	}

	public void updateControls() {
		if (controlsInited) {
			//if (!inUpdate) {
				//inUpdate = true;
				noteSpinControl_note.setValueWithoutEvents(configFull.config3rds[configIndex].note);
				noteSpinControl_altNote.setValueWithoutEvents(configFull.config3rds[configIndex].altNote);
				noteSpinControl_pressrollNote.setValueWithoutEvents(configFull.config3rds[configIndex].pressrollNote);
				noteSpinControl_dampenedNote.setValueWithoutEvents(configFull.config3rds[configIndex].dampenedNote);
				noteSpinControl_altNote.getCheckBox().setSelected(configFull.config3rds[configIndex].altNote_linked);
				noteSpinControl_pressrollNote.getCheckBox().setSelected(configFull.config3rds[configIndex].pressrollNote_linked);
				spinner_threshold.setValueWithoutEvent(configFull.config3rds[configIndex].threshold);
				spinner_midPointWidth.setValueWithoutEvent(configFull.config3rds[configIndex].threshold&0x0f);
				slider_midPoint.setValueWithoutEvent((configFull.config3rds[configIndex].threshold&0xf0)>>4);
				//inUpdate = false;
			//}
		}
	}
	
	public void updateConfig() {
		if (controlsInited) {
			configFull.config3rds[configIndex].note = (Integer)noteSpinControl_note.getValue();
			configFull.config3rds[configIndex].altNote = (Integer)noteSpinControl_altNote.getValue();
			configFull.config3rds[configIndex].pressrollNote = (Integer)noteSpinControl_pressrollNote.getValue();
			configFull.config3rds[configIndex].dampenedNote = (Integer)noteSpinControl_dampenedNote.getValue();
			configFull.config3rds[configIndex].altNote_linked = noteSpinControl_altNote.getCheckBox().isSelected();
			configFull.config3rds[configIndex].pressrollNote_linked = noteSpinControl_pressrollNote.getCheckBox().isSelected();
		}
	}
	
	public void setAsSwitch(boolean switch_pad) {
		spinner_threshold.setEnabled(switch_pad);
		spinner_midPointWidth.setEnabled(!switch_pad);
		slider_midPoint.setEnabled(!switch_pad);
	}

	public void setConfigIndex(int index) {
		configIndex = index;
		updateControls();
	}
	
}
