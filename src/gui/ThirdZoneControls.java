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

public class ThirdZoneControls extends JPanel {
	private Boolean changeEventsAllowed = false;

	private NoteSpinControl noteSpinControl_note;
	private NoteSpinControl noteSpinControl_altNote;
	private NoteSpinControl noteSpinControl_pressrollNote;
	private NoteSpinControl noteSpinControl_dampenedNote;
	private JSpinner spinner_threshold;
	private JSpinner spinner_midPointWidth;
	private JSlider slider_midPoint;

	private Config3rd config3rd;
	private boolean inUpdate = false;
	private boolean allInitialized = false;

	private ArrayList<Object> controls; 
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
	public ThirdZoneControls() {
		controls = new ArrayList<Object>();
		config3rd = new Config3rd();
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:default"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				new ColumnSpec(ColumnSpec.FILL, Sizes.bounded(Sizes.PREFERRED, Sizes.constant("12dlu", true), Sizes.constant("12dlu", true)), 0),
				ColumnSpec.decode("10dlu"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("60dlu"),
				ColumnSpec.decode("2dlu"),
				new ColumnSpec(ColumnSpec.FILL, Sizes.bounded(Sizes.PREFERRED, Sizes.constant("12dlu", true), Sizes.constant("12dlu", true)), 0),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblNote, "1, 1, right, default");
		
		noteSpinControl_note = new NoteSpinControl();
		noteSpinControl_note.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (noteSpinControl_altNote.getCheckBox().isSelected()) {
					noteSpinControl_altNote.getSpinner().setValue(noteSpinControl_note.getSpinner().getValue());
				}
				if (noteSpinControl_pressrollNote.getCheckBox().isSelected()) {
					noteSpinControl_pressrollNote.getSpinner().setValue(noteSpinControl_note.getSpinner().getValue());
				}
			}
		});
		controls.add(noteSpinControl_note.getSpinner());
		add(noteSpinControl_note, "3, 1, fill, fill");
		
		zoneButton_note = new ZoneButton("note");
		add(zoneButton_note, "5, 1");
		
		JLabel lblMidpoint = new JLabel("MidPoint");
		lblMidpoint.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMidpoint, "7, 1, right, default");
		
		slider_midPoint = new JSlider();
		controls.add(slider_midPoint);
		slider_midPoint.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				config3rd.threshold = (short)((config3rd.threshold&0x0f)|((slider_midPoint.getValue()&0x0f)<<4));
				updateControls();
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
		
		noteSpinControl_altNote = new NoteSpinControl();
		noteSpinControl_altNote.getCheckBox().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noteSpinControl_altNote.getSpinner().setEnabled(!noteSpinControl_altNote.getCheckBox().isSelected());
				if (noteSpinControl_altNote.getCheckBox().isSelected()) {
					noteSpinControl_altNote.getSpinner().setValue(noteSpinControl_note.getSpinner().getValue());
				}
			}
		});
		noteSpinControl_altNote.getCheckBox().setVisible(true);
		noteSpinControl_altNote.getCheckBox().setToolTipText("Linked to Note");
		controls.add(noteSpinControl_altNote.getSpinner());
		add(noteSpinControl_altNote, "3, 2, fill, fill");
		
		zoneButton_altNote = new ZoneButton("altNote");
		add(zoneButton_altNote, "5, 2");
		
		JLabel lblMidpointWidth = new JLabel("MidPoint Width");
		lblMidpointWidth.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMidpointWidth, "7, 2, right, default");
		
		spinner_midPointWidth = new JSpinner();
		controls.add(spinner_midPointWidth);
		spinner_midPointWidth.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				config3rd.threshold = (short)((config3rd.threshold&0xf0)|((Short)spinner_midPointWidth.getValue()&0x0f));
				updateControls();
			}
		});
		spinner_midPointWidth.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 15), new Short((short) 1)));
		add(spinner_midPointWidth, "9, 2, left, fill");
		
		zoneButton_midwidth = new ZoneButton("midwidth");
		add(zoneButton_midwidth, "11, 2");
		
		JLabel lblPressrollNote = new JLabel("Pressroll Note");
		lblPressrollNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblPressrollNote, "1, 3, right, default");
		
		noteSpinControl_pressrollNote = new NoteSpinControl();
		noteSpinControl_pressrollNote.getCheckBox().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noteSpinControl_pressrollNote.getSpinner().setEnabled(!noteSpinControl_pressrollNote.getCheckBox().isSelected());
				if (noteSpinControl_pressrollNote.getCheckBox().isSelected()) {
					noteSpinControl_pressrollNote.getSpinner().setValue(noteSpinControl_note.getSpinner().getValue());
				}
			}
		});
		noteSpinControl_pressrollNote.getCheckBox().setVisible(true);
		noteSpinControl_pressrollNote.getCheckBox().setToolTipText("Linked to Note");
		controls.add(noteSpinControl_pressrollNote.getSpinner());
		add(noteSpinControl_pressrollNote, "3, 3, fill, fill");
		
		zoneButton_pressrollNote = new ZoneButton("pressrollNote");
		add(zoneButton_pressrollNote, "5, 3");
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblThreshold, "7, 3, right, default");
		
		spinner_threshold = new JSpinner();
		controls.add(spinner_threshold);
		spinner_threshold.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				config3rd.threshold = (short)(Short)(spinner_threshold.getValue());
				updateControls();
		}
		});
		spinner_threshold.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 255), new Short((short) 1)));
		add(spinner_threshold, "9, 3, left, fill");
		
		zoneButton_threshold = new ZoneButton("threshold");
		add(zoneButton_threshold, "11, 3");
		
		JLabel lblDampenedNote = new JLabel("Dampened Note");
		lblDampenedNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblDampenedNote, "1, 4");
		
		noteSpinControl_dampenedNote = new NoteSpinControl();
		//noteSpinControl_dampenedNote.getCheckBox().setVisible(true);
		controls.add(noteSpinControl_dampenedNote.getSpinner());
		add(noteSpinControl_dampenedNote, "3, 4, fill, fill");
		
		zoneButton_dampenedNote = new ZoneButton("dampenedNote");
		add(zoneButton_dampenedNote, "5, 4");

		for (Object control : controls) {
			if (control.getClass().equals(JSlider.class)) {
				((JSlider) control).addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						valueChanged();
					}
				});
				
			}
			if (control.getClass().equals(JSpinner.class)) {
				((JSpinner) control).addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						valueChanged();
					}
				});
				
			}
		}

		for (Object control: this.getComponents()) {
			//System.out.printf("Component -> %s\n",control.getClass().toString());
			if (control.getClass().equals(ZoneButton.class)) {
				((ZoneButton)control).addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//System.out.printf("Button -> %s\n",((ZoneButton)arg0.getSource()).getName());
						pressedPadButtonName = ((ZoneButton)arg0.getSource()).getName();
						firePropertyChange("copyButton", false, true);
					}
				});
			}
		}
		
		allInitialized = true;
	}

	private void valueChanged() {
		if (changeEventsAllowed) {
			updateConfig();
			firePropertyChange("valueChanged", false, true);
		}
	}

	public void updateControls() {
		if (allInitialized) {
			if (!inUpdate) {
				inUpdate = true;
				noteSpinControl_note.getSpinner().setValue(config3rd.note);
				noteSpinControl_altNote.getSpinner().setValue(config3rd.altNote);
				noteSpinControl_pressrollNote.getSpinner().setValue(config3rd.pressrollNote);
				noteSpinControl_dampenedNote.getSpinner().setValue(config3rd.dampenedNote);
				spinner_threshold.setValue(config3rd.threshold);
				spinner_midPointWidth.setValue((short)(config3rd.threshold&0x0f));
				slider_midPoint.setValue((short)((config3rd.threshold&0xf0)>>4));
				inUpdate = false;
			}
		}
	}
	
	public void updateConfig() {
		config3rd.note = ((Short)noteSpinControl_note.getSpinner().getValue()).shortValue();
		config3rd.altNote = ((Short)noteSpinControl_altNote.getSpinner().getValue()).shortValue();
		config3rd.pressrollNote = ((Short)noteSpinControl_pressrollNote.getSpinner().getValue()).shortValue();
		config3rd.dampenedNote = ((Short)noteSpinControl_dampenedNote.getSpinner().getValue()).shortValue();
		config3rd.threshold = (Short)spinner_threshold.getValue();
	}
	
	public void setAsSwitch(boolean switch_pad) {
		spinner_threshold.setEnabled(switch_pad);
		spinner_midPointWidth.setEnabled(!switch_pad);
		slider_midPoint.setEnabled(!switch_pad);
	}
	
	public void setConfig(Config3rd config) {
		changeEventsAllowed = false;
		config3rd = config;
		updateControls();
		changeEventsAllowed = true;
	}
	
}
