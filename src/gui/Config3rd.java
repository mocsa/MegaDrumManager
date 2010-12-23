package gui;

public class Config3rd {
	public short changed_3rd;
	public short note;
	public short threshold = 30;
	public short pressrollNote;
	public short altNote;
	public short dampenedNote;
	private byte [] sysex_byte;
	private byte [] sysex_short;
	private byte [] sysex;
	private byte flags;

	public Config3rd () {
		changed_3rd = 0;
		sysex = new byte[Constants.MD_SYSEX_3RD_SIZE];
		sysex_byte = new byte[2];
		sysex_short = new byte[4];		
	}
	
	public void copyVarsFrom(Config3rd source) {
		note = source.note;
		threshold = source.threshold;
		pressrollNote = source.pressrollNote;
		altNote = source.altNote;
		dampenedNote = source.dampenedNote;
	}
	public byte [] getSysex(int chain_id, int pad_id) {
		int i = 0;

		sysex[i++] = Constants.SYSEX_START;
		sysex[i++] = Constants.MD_SYSEX;
		sysex[i++] = (byte) chain_id;
		sysex[i++] = Constants.MD_SYSEX_3RD;
		sysex[i++] = (byte)pad_id;	//Placeholder for pad id
		
		sysex_byte = Utils.byte2sysex((byte)note);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)threshold);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)pressrollNote);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)altNote);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)dampenedNote);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex[i++] = Constants.SYSEX_END;
		return sysex;
	}

	public void setFromSysex(byte [] sx) {
		int i = 4;
		if (sx.length >= Constants.MD_SYSEX_3RD_SIZE) {
			changed_3rd = (short)(sx[i++] + 1);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			note = Utils.sysex2byte(sysex_byte);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			threshold = Utils.sysex2byte(sysex_byte);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			pressrollNote = Utils.sysex2byte(sysex_byte);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			altNote = Utils.sysex2byte(sysex_byte);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			dampenedNote = Utils.sysex2byte(sysex_byte);
		}
	}

}
