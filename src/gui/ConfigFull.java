package gui;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class ConfigFull implements java.io.Serializable {

	public ConfigMisc configMisc;
	public ConfigPedal configPedal;
	public ConfigPad [] configPads;
	public Config3rd [] config3rds;
	public ConfigCurve [] configCurves;
	public ConfigCustomName [] configCustomNames;
	public int customNamesCount;
	private static final String configMiscPrefix = "misc.";
	private static final String configPedalPrefix = "pedal.";
	private static final String configPadPrefix = "input";
	private static final String config3rdPrefix = "pad3d_zone";
	private static final String configCurvePrefix = "curve";
	private static final String configCustomNamePrefix = "customName";
	
	public ConfigFull() {
		
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
		for (Integer i = 0; i < Constants.CURVES_COUNT;i++) {
			configCurves[i] = new ConfigCurve();
		}
		configCustomNames = new ConfigCustomName[Constants.CUSTOM_NAMES_MAX];
		for (Integer i = 0; i < Constants.CUSTOM_NAMES_MAX;i++) {
			configCustomNames[i] = new ConfigCustomName();
        	configCustomNames[i].name = "Custom" + i.toString(); 
		}
		customNamesCount = Constants.CUSTOM_NAMES_MAX;
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
		for (Integer i = 0; i < Constants.CURVES_COUNT;i++) {
			configCurves[i].copyToPropertiesConfiguration(prop, layout,configCurvePrefix, i);
		}
		layout.setComment(configCustomNamePrefix+"Count", "\n#Custom Pads Names");
		prop.setProperty(configCustomNamePrefix+"Count", customNamesCount);
		for (Integer i = 0; i < Constants.CUSTOM_NAMES_MAX;i++) {
			configCustomNames[i].copyToPropertiesConfiguration(prop, layout,configCustomNamePrefix, i);
		}
	}
	
	public void copyFromPropertiesConfiguration(PropertiesConfiguration prop) throws ConversionException {
		configMisc.copyFromPropertiesConfiguration(prop, configMiscPrefix);
		configPedal.copyFromPropertiesConfiguration(prop, configPedalPrefix);
		for (Integer i = 0; i < Constants.PADS_COUNT;i++) {
			configPads[i].copyFromPropertiesConfiguration(prop, configPadPrefix, i);
			if ((i>0) && ((i&0x01)==0)) {
				config3rds[(i-1)/2].copyFromPropertiesConfiguration(prop, config3rdPrefix, (i-1)/2);				
			}
		}
		for (Integer i = 0; i < Constants.CURVES_COUNT;i++) {
			configCurves[i].copyFromPropertiesConfiguration(prop, configCurvePrefix, i);
		}
		customNamesCount = Utils.validateInt(prop.getInt(configCustomNamePrefix+"Count", customNamesCount),2,32,customNamesCount);
		for (Integer i = 0; i < Constants.CUSTOM_NAMES_MAX;i++) {
			configCustomNames[i].copyFromPropertiesConfiguration(prop, configCustomNamePrefix, i);
		}
	}
}
