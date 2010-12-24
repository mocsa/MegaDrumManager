package gui;

public class ConfigFull implements java.io.Serializable {
	public byte [] sysex_misc;
	public byte [] sysex_pedal;
	public byte [][] sysex_pads;
	public byte [][] sysex_3rds;
	public boolean [] altNote_linked;
	public boolean [] pressrollNote_linked;

	public ConfigFull() {
		sysex_misc = new byte[Constants.MD_SYSEX_MISC_SIZE];
		sysex_pedal = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
		sysex_pads = new byte[Constants.PADS_COUNT][Constants.MD_SYSEX_PAD_SIZE];
		altNote_linked = new boolean[Constants.PADS_COUNT];
		pressrollNote_linked = new boolean[Constants.PADS_COUNT];
		sysex_3rds = new byte[(Constants.PADS_COUNT-1)/2][Constants.MD_SYSEX_3RD_SIZE];
	}
}
