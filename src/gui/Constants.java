package gui;

public interface Constants {
	public static final byte SYSEX_START = (byte)0xf0;
	public static final byte SYSEX_END = (byte)0xf7;
	public static final byte MD_SYSEX = (byte)0x70;
	public static final byte MD_SYSEX_MISC = (byte)0x01;
	public static final byte MD_SYSEX_MISC_SIZE = 15;
	public static final byte MD_SYSEX_PEDAL = (byte)0x02;
	public static final byte MD_SYSEX_PEDAL_SIZE = 63;
	public static final byte MD_SYSEX_PAD = (byte)0x03;
	public static final byte MD_SYSEX_PAD_SIZE = 34;
	public static final String[] CURVES_LIST = { "Linear", "Log1", "Log2", "Log3", "Log4",
		"Exp1", "Exp2", "S1", "S2", "Strong1", "Strong2", "Max",
		"Custom1", "Custom2", "Custom3", "Custom4" }; 
}
