package gui;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class ConfigFull implements java.io.Serializable {
//	public byte [] sysex_misc;
//	public byte [] sysex_pedal;
//	public byte [][] sysex_pads;
//	public byte [][] sysex_3rds;
	public byte [][] sysex_curves;
//	public boolean [] altNote_linked;
//	public boolean [] pressrollNote_linked;

	public ConfigMisc configMisc;
	public ConfigPedal configPedal;
	public ConfigPad [] configPads;
	public Config3rd [] config3rds;
	public ConfigCurve [] configCurves;
	private static final String configMiscPrefix = "misc.";
	private static final String configPedalPrefix = "pedal.";
	private static final String configPadPrefix = "input";
	private static final String config3rdPrefix = "pad3d_zone";
	
	public ConfigFull() {
//		sysex_misc = new byte[Constants.MD_SYSEX_MISC_SIZE];
//		sysex_pedal = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
//		sysex_pads = new byte[Constants.PADS_COUNT][Constants.MD_SYSEX_PAD_SIZE];
//		altNote_linked = new boolean[Constants.PADS_COUNT];
//		pressrollNote_linked = new boolean[Constants.PADS_COUNT];
//		sysex_3rds = new byte[(Constants.PADS_COUNT-1)/2][Constants.MD_SYSEX_3RD_SIZE];
		sysex_curves = new byte[Constants.CURVES_COUNT][Constants.MD_SYSEX_CURVE_SIZE];
		
		configMisc = new ConfigMisc();
		configPedal = new ConfigPedal();
		configPads = new ConfigPad[Constants.PADS_COUNT];
		config3rds = new Config3rd[(Constants.PADS_COUNT-1)/2];
		for (Integer i = 0; i < Constants.PADS_COUNT;i++) {
			configPads[i] = new ConfigPad();
			if ((i>0) && ((i&0x01)==0)) {
				config3rds[(i-1)/2] = new Config3rd();
			}
		}
		configCurves = new ConfigCurve[Constants.CURVES_COUNT];
	}
	
	public void copyToPropertiesConfiguration(PropertiesConfiguration prop, PropertiesConfigurationLayout layout) {
		configMisc.copyToPropertiesConfiguration(prop, layout,configMiscPrefix);
		configPedal.copyToPropertiesConfiguration(prop, layout,configPedalPrefix);
		for (Integer i = 0; i < Constants.PADS_COUNT;i++) {
			configPads[i].copyToPropertiesConfiguration(prop, layout,configPadPrefix, i);
			if ((i>0) && ((i&0x01)==0)) {
				config3rds[(i-1)/2].copyToPropertiesConfiguration(prop, layout,config3rdPrefix, (i-1)/2);				
			}
		}
	}
	public void copyFromPropertiesConfiguration(PropertiesConfiguration prop) {
		configMisc.copyFromPropertiesConfiguration(prop, configMiscPrefix);
		configPedal.copyFromPropertiesConfiguration(prop, configPedalPrefix);
		for (Integer i = 0; i < Constants.PADS_COUNT;i++) {
			configPads[i].copyFromPropertiesConfiguration(prop, configPadPrefix, i);
			if ((i>0) && ((i&0x01)==0)) {
				config3rds[(i-1)/2].copyFromPropertiesConfiguration(prop, config3rdPrefix, (i-1)/2);				
			}
		}
	}
}
