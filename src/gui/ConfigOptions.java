package gui;

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
	public int inputCount = 55;
	
	public ConfigOptions() {
		
	}
}
