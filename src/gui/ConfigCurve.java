package gui;

public class ConfigCurve {
	public short changed_curve;
	public int [] yValues = {2, 32, 64, 96, 128, 160, 192, 224, 255};

	private byte [] sysex_byte;
	private byte [] sysex;

	public ConfigCurve() {
		changed_curve = -1;
		sysex = new byte[Constants.MD_SYSEX_CURVE_SIZE];
		sysex_byte = new byte[2];
	}

	public void copyVarsFrom(ConfigCurve source) {
		for (int i = 0; i < 9;i++) {
			yValues[i] = source.yValues[i];
		}
	}

	public byte [] getSysex(int chain_id, int curve_id) {
		int i = 0;

		sysex[i++] = Constants.SYSEX_START;
		sysex[i++] = Constants.MD_SYSEX;
		sysex[i++] = (byte) chain_id;
		sysex[i++] = Constants.MD_SYSEX_CURVE;
		sysex[i++] = (byte)curve_id;	//Placeholder for curve id

		for (int p = 0; p < 9;p++) {
			sysex_byte = Utils.byte2sysex((byte)yValues[p]);
			sysex[i++] = sysex_byte[0];
			sysex[i++] = sysex_byte[1];
		}
		sysex[i++] = Constants.SYSEX_END;
		return sysex;
	}

	public void setFromSysex(byte [] sx) {
		int i = 4;
		if (sx.length >= Constants.MD_SYSEX_CURVE_SIZE) {
			changed_curve = sx[i++];
			for (int p = 0; p < 9;p++) {
				sysex_byte[0] = sx[i++];
				sysex_byte[1] = sx[i++];
				yValues[p] = Utils.sysex2byte(sysex_byte);
				if (yValues[p]<0) {
					yValues[p] += 256;
				}
			}
		}
	}

	
}
