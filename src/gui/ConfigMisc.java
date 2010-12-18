package gui;

public class ConfigMisc {
	
	public boolean changed; 
	
	public short note_off;
	public short latency;
	public short pressroll;
	public boolean all_gains_low;
	public boolean big_vu_meter;
	public boolean quick_access;
	public boolean alt_false_tr_supp;
	public boolean inputs_priority;

	private byte[] sysex;
	private short flags;
	private byte [] sysex_byte;
	private byte [] sysex_short;
	
	public ConfigMisc (){
		changed = false;
		
		note_off = 20;
		latency = 40;
		pressroll = 0;
		all_gains_low = false;
		big_vu_meter = false;
		quick_access = false;
		alt_false_tr_supp = false;
		inputs_priority = false;
		sysex = new byte[Constants.MD_SYSEX_MISC_SIZE];
		sysex_byte = new byte[2];
		sysex_short = new byte[4];
	}
	
	public byte[] getSysex(int chain_id){
		int i = 0;
		
		flags = (short) (((all_gains_low)?1:0)|(((big_vu_meter)?1:0)<<2)
				|(((quick_access)?1:0)<<3)|(((alt_false_tr_supp)?1:0)<<5)|(((inputs_priority)?1:0)<<6));
		sysex[i++] = Constants.SYSEX_START;
		sysex[i++] = Constants.MD_SYSEX;
		sysex[i++] = (byte) chain_id;
		sysex[i++] = Constants.MD_SYSEX_MISC;
		sysex_byte = Utils.byte2sysex((byte)note_off);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)latency);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_short = Utils.short2sysex(flags);
		sysex[i++] = sysex_short[0];
		sysex[i++] = sysex_short[1];
		sysex[i++] = sysex_short[2];
		sysex[i++] = sysex_short[3];
		sysex_byte = Utils.short2sysex((byte)pressroll);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex[i++] = Constants.SYSEX_END;
		return sysex;
	}
	
	public void setFromSysex(byte [] sx) {
		int i = 4;
		//System.out.printf("sysex_byte size: %d\n", sysex_byte.length);
		//System.out.printf("sx size: %d\n", sx.length);
		if (sx.length >= Constants.MD_SYSEX_MISC_SIZE) {
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			note_off = Utils.sysex2byte(sysex_byte);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			latency = Utils.sysex2byte(sysex_byte);
			sysex_short[0] = sx[i++];
			sysex_short[0] = sx[i++];
			sysex_short[0] = sx[i++];
			sysex_short[0] = sx[i++];
			flags = Utils.sysex2short(sysex_short);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			pressroll = Utils.sysex2byte(sysex_byte);
			
			all_gains_low = ((flags&1) != 0);
			big_vu_meter = ((flags&(1<<2)) != 0);
			quick_access = ((flags&(1<<3)) != 0);
			alt_false_tr_supp = ((flags&(1<<5)) != 0);
			inputs_priority = ((flags&(1<<6)) != 0);
			
			changed = true;
		}
	}
}
