package gui;

import java.io.IOException;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class ConfigGlobalMisc {
	
	public boolean changed; 
	
	public short lcd_contrast = 50;
	public short inputs_count = 18;

	public ConfigGlobalMisc (){
	}
	
	public void copyToPropertiesConfiguration(PropertiesConfiguration prop, PropertiesConfigurationLayout layout, String prefix) {
		layout.setComment(prefix+"lcd_contrast", "Global Misc settings");
		prop.setProperty(prefix+"lcd_contrast", lcd_contrast);
		prop.setProperty(prefix+"inputs_count", inputs_count);
	}

	public void copyFromPropertiesConfiguration(PropertiesConfiguration prop, String prefix) {
		lcd_contrast = Utils.validateShort(prop.getShort(prefix+"lcd_contrast", lcd_contrast),1,100,lcd_contrast);
		inputs_count = Utils.validateShort(prop.getShort(prefix+"inputs_count", inputs_count),18,56,inputs_count);
	}
}
