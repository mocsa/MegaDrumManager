package gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

public class ViewMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5916757511373377344L;
	private JRadioButtonMenuItem radioHide;
	private JRadioButtonMenuItem radioShow;
	private JRadioButtonMenuItem radioDetach;
	//private String panelName;
	private ConfigOptions configOptions;
	// Show panels. 0 - Misc, 1 - Pedal, 2 - Pads, 3 - Curves
	public int panelPointer;
	private int [] prevState = { -1, -1, -1, -1, -1};
	
	/**
	 * Create the panel.
	 */
	public ViewMenu(String name, int panel) {
		//panelName = name;
		panelPointer = panel;
		setText(name);
		radioHide = new JRadioButtonMenuItem("Hide");
		add(radioHide);
		radioShow = new JRadioButtonMenuItem("Show");
		add(radioShow);
		radioDetach = new JRadioButtonMenuItem("Detach");
		add(radioDetach);
		updateControls();
		radioHide.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					if (prevState[panelPointer] == Constants.PANEL_HIDE) {
						radioHide.setSelected(true);
					}
				}
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (prevState[panelPointer] != Constants.PANEL_HIDE) {
						configOptions.showPanels[panelPointer] = Constants.PANEL_HIDE;
						prevState[panelPointer] = Constants.PANEL_HIDE;
						updateControls();
						firePropertyChange("resize", false, true);
					}
				}
			}
		});
		radioShow.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					if (prevState[panelPointer] == Constants.PANEL_SHOW) {
						radioShow.setSelected(true);
					}
				}
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (prevState[panelPointer] != Constants.PANEL_SHOW) {
						configOptions.showPanels[panelPointer] = Constants.PANEL_SHOW;
						prevState[panelPointer] = Constants.PANEL_SHOW;
						updateControls();
						firePropertyChange("resize", false, true);
					}
				}
			}
		});
		radioDetach.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					if (prevState[panelPointer] == Constants.PANEL_DETACH) {
						radioDetach.setSelected(true);
					}
				}
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (prevState[panelPointer] != Constants.PANEL_DETACH) {
						configOptions.showPanels[panelPointer] = Constants.PANEL_DETACH;
						prevState[panelPointer] = Constants.PANEL_DETACH;
						updateControls();
						firePropertyChange("resize", false, true);
					}
				}
			}
		});

	}
	
	public void setConfigOptions(ConfigOptions config) {
		configOptions = config;	
		updateControls();
	}
	
	public void updateControls() {
		if (configOptions != null ) {
			radioHide.setSelected(configOptions.showPanels[panelPointer] == Constants.PANEL_HIDE);
			radioShow.setSelected(configOptions.showPanels[panelPointer] == Constants.PANEL_SHOW);
			radioDetach.setSelected(configOptions.showPanels[panelPointer] == Constants.PANEL_DETACH);
		}
	}

}
