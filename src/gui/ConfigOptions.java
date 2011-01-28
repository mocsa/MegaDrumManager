package gui;

import java.awt.Point;

public class ConfigOptions implements java.io.Serializable {
	public boolean useSamePort = false;
	public boolean useThruPort = false;
	public boolean autoOpenPorts = false;
	public boolean saveOnExit = false;
	public boolean interactive = false;
	public String lastDir = "";
	public int lastConfig = 0;
	public String [] configsNames;
	public String lastFullPathConfig = "";
	public String lastFullPathFirmware = "";
	public String lastFullPathSysex = "";
	public String MidiInName = "";
	public String MidiOutName = "";
	public String MidiThruName = "";
	public int chainId = 0;
	public int inputsCount = 55;
	public int sysexDelay = 30;
	public Point mainWindowPosition = new Point(10,10);
	// Show panels. 0 - Misc, 1 - Pedal, 2 - Pads, 3 - Curves, 4 - MIDI Log
	public Point [] framesPositions = { new Point(10,10), new Point(210,10), new Point(410,10), new Point(610,10), new Point(810,10)};
	public int [] showPanels = { Constants.PANEL_SHOW, Constants.PANEL_SHOW, Constants.PANEL_SHOW, Constants.PANEL_SHOW, Constants.PANEL_HIDE };
	
	public ConfigOptions() {
		configsNames = new String[Constants.CONFIGS_COUNT];
		for (Integer i = 1;i<=Constants.CONFIGS_COUNT;i++) {
			configsNames[i-1] = "Config"+i.toString();
		}
		
	}
}
