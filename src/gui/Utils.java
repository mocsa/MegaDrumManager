package gui;

public class Utils {

	public static byte [] byte2sysex (byte b) {
		byte [] result = new byte[2];

		result[0] = (byte)((b&0xf0)>>4);
		result[1] = (byte)(b&0x0f);
		return result;
	}

	public static byte [] short2sysex (short s) {
		byte [] result = new byte[4];

		result[0] = (byte)((s&0x00f0)>>4);
		result[1] = (byte)(s&0x000f);
		result[2] = (byte)((s&0xf000)>>12);
		result[3] = (byte)((s&0x0f00)>>8);
		return result;
	}
	
	public static byte sysex2byte (byte [] sx) {
		byte result;
		
		result = (byte)(((sx[0]&0x0f)<<4)|(sx[1]&0x0f));
		return result;
	}

	public static short sysex2short (byte [] sx) {
		short result;
		
		result = (short)(((sx[0]&0x0f)<<4)|(sx[1]&0x0f)|((sx[2]&0xf000)<<12)|((sx[2]&0x0f00)<<8));
		return result;
	}

	public static void copyConfigPadToConfigFull(ConfigPad [] src, ConfigFull dst, int pad_id) {
		dst.altNote_linked[pad_id] = src[pad_id].altNote_linked;
		dst.pressrollNote_linked[pad_id] = src[pad_id].pressrollNote_linked;
	}
	
	public static void copyConfigFullToConfigPad(ConfigFull src, ConfigPad [] dst, int pad_id) {
		dst[pad_id].altNote_linked = src.altNote_linked[pad_id];
		dst[pad_id].pressrollNote_linked = src.pressrollNote_linked[pad_id];
	}
	
	public static void copyConfigPadToSysex(ConfigPad config, byte [] sysex, int chainId, int padId) {
		byte [] sysex_byte = new byte[2];
		byte [] sysex_short = new byte[4];
		byte flags;
		int i = 0;

		sysex[i++] = Constants.SYSEX_START;
		sysex[i++] = Constants.MD_SYSEX;
		sysex[i++] = (byte)chainId; 
		sysex[i++] = Constants.MD_SYSEX_PAD;
		sysex[i++] = (byte)(padId + 1);
		
		sysex_byte = byte2sysex((byte)config.note);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)((config.channel<<4)|(config.curve)));
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)config.threshold);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)config.retrigger);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_short = short2sysex((short)config.levelMax);
		sysex[i++] = sysex_short[0];
		sysex[i++] = sysex_short[1];
		sysex[i++] = sysex_short[2];
		sysex[i++] = sysex_short[3];
		sysex_byte = byte2sysex((byte)config.minScan);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		flags = (byte) (((config.type)?1:0)|(((config.autoLevel)?1:0)<<1)|(((config.dual)?1:0)<<2)|(((config.threeWay)?1:0)<<3)
				|(config.gain<<4));
		sysex_byte = byte2sysex(flags);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)((config.xtalkGroup<<3)|(config.xtalkLevel)));
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)((config.dynLevel<<4)|(config.dynTime)));
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)(((config.special?1:0)<<6)|(config.shift<<3)|(config.compression)));
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];		
		sysex_byte = byte2sysex((byte)config.name);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)config.pressrollNote);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)config.altNote);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex[i++] = Constants.SYSEX_END;

	}

	public static void copySysexToConfigPad(byte [] sysex, ConfigPad config) {
		byte [] sysex_byte = new byte[2];
		byte [] sysex_short = new byte[4];
		byte flags;
		int i = 5;

		if (sysex.length >= Constants.MD_SYSEX_PAD_SIZE) {
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.note = sysex2byte(sysex_byte);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			flags = sysex2byte(sysex_byte);
			config.curve = (short)(flags&0x0f);
			config.channel = (short)((flags&0xf0)>>4);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.threshold = sysex2byte(sysex_byte);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.retrigger = sysex2byte(sysex_byte);
			sysex_short[0] = sysex[i++];
			sysex_short[1] = sysex[i++];
			sysex_short[2] = sysex[i++];
			sysex_short[3] = sysex[i++];
			config.levelMax = sysex2short(sysex_short);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.minScan = sysex2byte(sysex_byte);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			flags = sysex2byte(sysex_byte);
			config.gain = (short)((flags&0xf0)>>4);
			config.type = ((flags&1) != 0);
			config.autoLevel = ((flags&(1<<1)) != 0);
			config.dual = ((flags&(1<<2)) != 0);
			config.threeWay = ((flags&(1<<3)) != 0);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			flags = sysex2byte(sysex_byte);
			config.xtalkGroup = (short)((flags&0x38)>>3);
			config.xtalkLevel = (short)(flags&0x07);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			flags = sysex2byte(sysex_byte);
			config.dynTime = (short)(flags&0x0f);
			config.dynLevel = (short)((flags&0xf0)>>4);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			flags = sysex2byte(sysex_byte);
			config.shift = (short)((flags&0x38)>>3);
			config.compression = (short)(flags&0x07);
			config.special = ((flags&(1<<6)) != 0);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.name = sysex2byte(sysex_byte);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.pressrollNote = sysex2byte(sysex_byte);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.altNote = sysex2byte(sysex_byte);
		}
	}

	public static void copyConfig3rdToSysex(Config3rd config, byte [] sysex, int chainId, int padId) {
		byte [] sysex_byte = new byte[2];
		byte [] sysex_short = new byte[4];
		int i = 0;

		sysex[i++] = Constants.SYSEX_START;
		sysex[i++] = Constants.MD_SYSEX;
		sysex[i++] = (byte)chainId;
		sysex[i++] = Constants.MD_SYSEX_3RD;
		sysex[i++] = (byte)padId;
		
		sysex_byte = byte2sysex((byte)config.note);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)config.threshold);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)config.pressrollNote);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)config.altNote);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)config.dampenedNote);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex[i++] = Constants.SYSEX_END;		
	}
	
	public static void copySysexToConfig3rd(byte [] sysex, Config3rd config) {
		byte [] sysex_byte = new byte[2];
		byte [] sysex_short = new byte[4];
		int i = 5;
		if (sysex.length >= Constants.MD_SYSEX_3RD_SIZE) {
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.note = sysex2byte(sysex_byte);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.threshold = sysex2byte(sysex_byte);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.pressrollNote = sysex2byte(sysex_byte);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.altNote = sysex2byte(sysex_byte);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.dampenedNote = sysex2byte(sysex_byte);
		}
		
	}
	
	public static void copySysexToConfigMisc(byte [] sysex, ConfigMisc config) {
		byte [] sysex_byte = new byte[2];
		byte [] sysex_short = new byte[4];
		short flags;
		int i = 4;
		//System.out.printf("sysex_byte size: %d\n", sysex_byte.length);
		//System.out.printf("sx size: %d\n", sx.length);
		if (sysex.length >= Constants.MD_SYSEX_MISC_SIZE) {
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.note_off = sysex2byte(sysex_byte);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.latency = sysex2byte(sysex_byte);
			sysex_short[0] = sysex[i++];
			sysex_short[1] = sysex[i++];
			sysex_short[2] = sysex[i++];
			sysex_short[3] = sysex[i++];
			flags = sysex2short(sysex_short);
			sysex_byte[0] = sysex[i++];
			sysex_byte[1] = sysex[i++];
			config.pressroll = sysex2byte(sysex_byte);
			
			config.all_gains_low = ((flags&1) != 0);
			config.big_vu_meter = ((flags&(1<<2)) != 0);
			config.quick_access = ((flags&(1<<3)) != 0);
			config.alt_false_tr_supp = ((flags&(1<<5)) != 0);
			config.inputs_priority = ((flags&(1<<6)) != 0);
		}
	}
	
	public static void copyConfigMiscToSysex(ConfigMisc config, byte [] sysex, int chainId) {
		byte [] sysex_byte = new byte[2];
		byte [] sysex_short = new byte[4];
		short flags;
		int i = 0;
		
		flags = (short) (((config.all_gains_low)?1:0)|(((config.big_vu_meter)?1:0)<<2)
				|(((config.quick_access)?1:0)<<3)|(((config.alt_false_tr_supp)?1:0)<<5)|(((config.inputs_priority)?1:0)<<6));
		sysex[i++] = Constants.SYSEX_START;
		sysex[i++] = Constants.MD_SYSEX;
		sysex[i++] = (byte) chainId;
		sysex[i++] = Constants.MD_SYSEX_MISC;
		sysex_byte = byte2sysex((byte)config.note_off);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = byte2sysex((byte)config.latency);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_short = Utils.short2sysex(flags);
		sysex[i++] = sysex_short[0];
		sysex[i++] = sysex_short[1];
		sysex[i++] = sysex_short[2];
		sysex[i++] = sysex_short[3];
		sysex_byte = short2sysex((byte)config.pressroll);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex[i++] = Constants.SYSEX_END;		
	}
	

	//	public void copyConfigPad(ConfigPad src, ConfigPad dst) {
//		dst.note = src.note;
//		dst.channel = src.channel;
//		dst.curve = src.curve;
//		dst.threshold =  src.threshold;
//		dst.retrigger = src.retrigger;
//		dst.levelMax = src.levelMax;
//		dst.minScan =  src.minScan;
//		dst.type = src.type;
//		dst.autoLevel = src.autoLevel;
//		dst.dual = src.dual;
//		dst.threeWay = src.threeWay;
//		dst.special = src.special;
//		dst.gain = src.gain;
//		dst.xtalkLevel = src.xtalkLevel;
//		dst.xtalkGroup = src.xtalkGroup;
//		dst.dynTime = src.dynTime;
//		dst.dynLevel = src.dynLevel;
//		dst.compression = src.compression;
//		dst.shift = src.shift;
//		dst.name = src.name;
//		dst.altNote = src.altNote;
//		dst.pressrollNote = src.pressrollNote;
//		dst.altNote_linked = src.altNote_linked;
//		dst.pressrollNote_linked = src.pressrollNote_linked;		
//	}
}
