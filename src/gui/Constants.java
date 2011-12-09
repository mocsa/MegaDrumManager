package gui;

import java.awt.Color;

public interface Constants {
	//public static final int MIDI_BARS_COUNT = 32;
	public static final String MD_VERSION = "20111209";
	public static final Double MD_CONFIG_VERSION = 0.5;
	public static final int MIN_INPUTS = 18;
	public static final int MAX_INPUTS = 56;
	public static final int CONFIGS_COUNT = 8;
	public static final int PANEL_HIDE = 0;
	public static final int PANEL_SHOW = 1;
	public static final int PANEL_DETACH = 2;
	public static final int PANELS_COUNT = 5;
	public static final String[] PANELS_NAMES = { "Misc", "Pedal", "Pads", "Pads Extra", "MIDI Log"};
	public static final int Error_NoResponse = 0x00;
	public static final int Error_OK = 0x11;
	public static final int Error_CRC = 0x22;
	public static final int Error_Read = 0x23;
	public static final String MD_MANAGER_CONFIG = System.getProperty("user.home") + System.getProperty("file.separator") + "megadrummanager.cfg";
	public static final short PADS_COUNT = 55;
	public static final short CURVES_COUNT = 4;
	public static final short CUSTOM_NAMES_MAX = 32;
	public static final short SYSEX_DELAY = 50;
	public static final byte SYSEX_START = (byte)0xf0;
	public static final byte SYSEX_END = (byte)0xf7;
	public static final byte MD_SYSEX = (byte)0x70;
	public static final byte MD_SYSEX_MISC = (byte)0x01;
	public static final byte MD_SYSEX_MISC_SIZE = 15;
	public static final byte MD_SYSEX_PEDAL = (byte)0x02;
	public static final byte MD_SYSEX_PEDAL_SIZE = 63;
	public static final byte MD_SYSEX_PAD = (byte)0x03;
	public static final byte MD_SYSEX_PAD_SIZE = 34;
	public static final byte MD_SYSEX_3RD = (byte)0x04;
	public static final byte MD_SYSEX_3RD_SIZE = 16;
	public static final byte MD_SYSEX_VERSION = (byte)0x05;
	public static final byte MD_SYSEX_VERSION_SIZE = 13;
	public static final byte MD_SYSEX_CURVE = (byte)0x06;
	public static final byte MD_SYSEX_CURVE_SIZE = 24;
	public static final byte MD_SYSEX_POS = (byte)0x07;
	public static final byte MD_SYSEX_POS_SIZE = 12;
	public static final byte MD_SYSEX_CUSTOM_NAME = (byte)0x08;
	public static final byte MD_SYSEX_CUSTOM_NAME_SIZE = 22;
	public static final byte MD_SYSEX_GLOBAL_MISC = (byte)0x09;
	public static final byte MD_SYSEX_GLOBAL_MISC_SIZE = 9;
	public static final byte MD_SYSEX_SAVE_SLOT1 = (byte)0x7f;
	public static final String[] CURVES_LIST = { "Linear", "Log1", "Log2", "Log3", "Log4",
		"Exp1", "Exp2", "S1", "S2", "Strong1", "Strong2", "Max",
		"Custom1", "Custom2", "Custom3", "Custom4" };
	public static final String[] PADS_NAMES_LIST = { "Kick", "HiHatB",
			"HiHatE", "SnareH", "SnareR", "RideB", "RideE", "CrashB", "CrashE",
			"Tom1H", "Tom1R", "Tom2H", "Tom2R", "Tom3H", "Tom3R", "Tom4H",
			"Tom4R", "Aux1H", "Aux1R", "Aux2H", "Aux2R", "Aux3H", "Aux3R",
			"Aux4H", "Aux4R", "Aux5H", "Aux5R", "Aux6H", "Aux6R", "Aux7H",
			"Aux7R", "Aux8H", "Aux8R", "Aux9H", "Aux9R", "Aux10H", "Aux10R",
			"Aux11H", "Aux11R", "Aux12H", "Aux12R", "Aux13H", "Aux13R",
			"Aux14H", "Aux14R", "Aux15H", "Aux15R", "Aux16H", "Aux16R",
			"Aux17H", "Aux17R", "Aux18H", "Aux18R", "Aux19H", "Aux19R" };
	public static final String[] CUSTOM_PADS_NAMES_LIST = { "Kick", "HiHat",
			"Snare1", "Snare2", "Ride1", "Ride2", "Crash1", "Crash2", "Tom1",
			"Tom2", "Tom3", "Tom4", "Tom5", "Tom6", "Chinese1", "Chinese2",
			"Tambrn1", "Tambrn2", "Cowbell1", "Cowbell2", "Bongo1", "Bongo2",
			"Conga1", "Conga2", "Conga3", "Timbale1", "Timbale2", "Agogo1",
			"Agogo2", "Claves1", "Claves2", "Wood1", "Wood2", "Cuica1",
			"Cuica2", "Triangl1", "Triangl2", "Bass1", "Bass2", "Sizzle1",
			"Sizzle2", "Splash1", "Splash2", "Swish1", "Swish2", "Clash1",
			"Clash2", "Chenda1", "Chenda2", "Tenor1", "Tenor2", "Timpani1",
			"Timpani2", "Timpani3", "Timpani4",	"Crash3", "Crash4", "Crash5",
			"Splash3", "Splash4", "Splash5", "RotoTom1", "RotoTom2", "RotoTom3",
			"Sticks1", "Sticks2", "Sticks3", "HndClap1", "HndClap2", "HndClap3",
			"FngrSnap", "Mtronome" 
	};
	
	//public static final Color MIDI_NOTE_OFF_COLOR 	= Color.decode("0xff0000"); Red
	//public static final Color MIDI_NOTE_OFF_COLOR 	= Color.decode("0x00ff00"); Green
	//public static final Color MIDI_NOTE_OFF_COLOR 	= Color.decode("0x0000ff"); Blue
	public static final Color MIDI_NOTE_ON_COLOR		= Color.decode("0x007f00");
	public static final Color MIDI_NOTE_OFF_COLOR		= Color.decode("0x2fbf2f");
	public static final Color MIDI_AFTERTOUCH_COLOR		= Color.decode("0x007f7f");
	public static final Color MIDI_CC_COLOR				= Color.decode("0x7f7f00");
	public static final Color MIDI_PC_COLOR				= Color.decode("0x7f007f");
	public static final Color MIDI_CH_PR_COLOR			= Color.decode("0x00007f");
	public static final Color MIDI_PITCH_COLOR			= Color.decode("0x7f0000");
	
	public static final String [] NOTES_NAMES = {
		"C ",
		"C#",
		"D ",
		"D#",
		"E ",
		"F ",
		"F#",
		"G ",
		"G#",
		"A ",
		"A#",
		"B "		
	};
	
	public static final Color MD_HEAD_COLOR = Color.BLUE;
	public static final Color MD_RIM_COLOR = Color.GREEN;
	public static final Color MD_3RD_COLOR = Color.ORANGE;
	public static final Color MD_UNKNOWN_COLOR = Color.YELLOW;
	public static final Color MD_HIHAT_COLOR = Color.MAGENTA;
	public static final Color MD_AFTERTOUCH_ON_COLOR = Color.GRAY;
	public static final Color MD_AFTERTOUCH_OFF_COLOR = Color.LIGHT_GRAY;
	public static final String HELP_ABOUT = "<html><font size=2>MegaDrum Manager</font></html>\n" +
			"<html><font size=2>Version: " + MD_VERSION + "</font></html>\n" +
			"<html><font size=2>www.megadrum.info</font></html>\n"+
			"<html><font size=2>© 2007-2011 Dmitri Skachkov</font></html>";
	public static final String MIDI_PORTS_WARNING =
			"<html><font size=4>Before using MegaDrum Manager</font></html>\n" +
			"<html><font size=4>you first must set MIDI In/Out ports in Main->Options!!!</font></html>";
}
