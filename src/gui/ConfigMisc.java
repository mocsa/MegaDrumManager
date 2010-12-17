package gui;

public class ConfigMisc {
	
	private short note_off;
	private short latency;
	private short pressroll;
	private short flags;
	private byte [] sysex_byte;
	private byte [] sysex_short;
	private boolean all_gains_low;
	private boolean big_vu_meter;
	private boolean quick_access;
	private boolean alt_false_tr_supp;
	private boolean inputs_priority;
	private byte[] sysex;
	
	public ConfigMisc (){
		note_off = 20;
		latency = 40;
		pressroll = 0;
		all_gains_low = false;
		big_vu_meter = false;
		quick_access = false;
		alt_false_tr_supp = false;
		inputs_priority = false;
		sysex = new byte[15];
		sysex_byte = new byte[2];
		sysex_short = new byte[4];
	}

	public short getNote_off(){
		return note_off;
	}

	public void setNote_off(short s){
		note_off = s;
	}
	
	public short getLatency(){
		return latency;
	}

	public void setLatency(short s){
		latency = s;
	}

	public short getPressroll(){
		return pressroll;
	}

	public void setPressroll(short s){
		pressroll = s;
	}

	public boolean getAll_gains_low(){
		return all_gains_low;
	}
	
	public void setAll_gains_low(boolean b){
		all_gains_low = b;
	}

	public boolean getBig_vu_meter(){
		return big_vu_meter;
	}
	
	public void setBig_vu_meter(boolean b){
		big_vu_meter = b;
	}
	
	public boolean getQuick_access(){
		return quick_access;
	}
	
	public void setQuick_access(boolean b){
		quick_access = b;
	}

	public boolean getAlt_false_tr_supp(){
		return alt_false_tr_supp;
	}
	
	public void setAlt_false_tr_supp(boolean b){
		alt_false_tr_supp = b;
	}

	public boolean getInputs_priority(){
		return inputs_priority;
	}
	
	public void setInputs_priority(boolean b){
		inputs_priority = b;
	}

	public byte[] getSysex(int chain_id){
		
		flags = (short) ((all_gains_low)?1:0|((big_vu_meter)?1:0<<2)
				|((quick_access)?1:0<<3)|((alt_false_tr_supp)?1:0<<5)|((inputs_priority)?1:0<<6));
		sysex[0] = (byte)0xf0;
		sysex[1] = Constants.MD_SYSEX;
		sysex[2] = (byte) chain_id;
		sysex[3] = Constants.MD_SYSEX_MISC;
		sysex_byte = Utils.sysex_get_byte((byte)note_off);
		sysex[4] = sysex_byte[0];
		sysex[5] = sysex_byte[1];
		sysex_byte = Utils.sysex_get_byte((byte)latency);
		sysex[6] = sysex_byte[0];
		sysex[7] = sysex_byte[1];
		sysex_short = Utils.sysex_get_short(flags);
		sysex[8] = sysex_short[0];
		sysex[9] = sysex_short[1];
		sysex[10] = sysex_short[2];
		sysex[11] = sysex_short[3];
		sysex_byte = Utils.sysex_get_byte((byte)pressroll);
		sysex[12] = sysex_byte[0];
		sysex[13] = sysex_byte[1];
		sysex[14] = (byte) 0xf7;
		return sysex;
	}
}
