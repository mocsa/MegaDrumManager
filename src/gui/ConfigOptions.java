package gui;

import java.awt.Point;

public class ConfigOptions implements java.io.Serializable {
	public boolean useSamePort = false;
	public boolean useThruPort = false;
	public boolean autoOpenPorts = false;
	public boolean saveOnExit = false;
	public String lastDir = "";
	public String lastFullPathConfig = "";
	public String MidiInName = "";
	public String MidiOutName = "";
	public String MidiThruName = "";
	public int chainId = 0;
	public int inputsCount = 55;
	public int sysexDelay = 30;
	public Point mainWindowPosition = new Point(10,10);
	// Show panels. 0 - Misc, 1 - Pedal, 2 - Pads, 3 - Curves, 4 - MIDI Log
	public Point [] framesPositions = { new Point(10,10), new Point(210,10), new Point(410,10), new Point(610,10), new Point(810,10)};
	public int [] showPanels = { Constants.PANEL_SHOW, Constants.PANEL_SHOW, Constants.PANEL_SHOW, Constants.PANEL_SHOW, Constants.PANEL_SHOW };
	
	public ConfigOptions() {
		
	}
}
