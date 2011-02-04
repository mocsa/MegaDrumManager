package gui;

import java.io.IOException;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class ConfigMisc {
	
	public boolean changed; 
	
	public short note_off = 20;
	public short latency = 40;
	public short pressroll = 0;
	public boolean all_gains_low = false;
	public boolean big_vu_meter = false;
	public boolean quick_access = false;
	public boolean alt_false_tr_supp = false;
	public boolean inputs_priority = false;

	public ConfigMisc (){
	}
	
	public void copyToPropertiesConfiguration(PropertiesConfiguration prop, PropertiesConfigurationLayout layout, String prefix) {
		layout.setComment(prefix+"note_off", "Misc settings");
		prop.setProperty(prefix+"note_off", note_off);
		prop.setProperty(prefix+"latency", latency);
		prop.setProperty(prefix+"pressroll", pressroll);
		prop.setProperty(prefix+"all_gains_low", all_gains_low);
		prop.setProperty(prefix+"big_vu_meter", big_vu_meter);
		prop.setProperty(prefix+"quick_access", quick_access);
		prop.setProperty(prefix+"alt_false_tr_supp", alt_false_tr_supp);
		prop.setProperty(prefix+"inputs_priority", inputs_priority);
	}

	public void copyFromPropertiesConfiguration(PropertiesConfiguration prop, String prefix) {
		note_off = Utils.validateShort(prop.getShort(prefix+"note_off", note_off),10,200,note_off);
		latency = Utils.validateShort(prop.getShort(prefix+"latency", latency),10,100,latency);
		pressroll = Utils.validateShort(prop.getShort(prefix+"pressroll", pressroll),0,10,pressroll);
		all_gains_low = prop.getBoolean(prefix+"all_gains_low", all_gains_low);
		big_vu_meter = prop.getBoolean(prefix+"big_vu_meter", big_vu_meter);
		quick_access = prop.getBoolean(prefix+"quick_access", quick_access);
		alt_false_tr_supp = prop.getBoolean(prefix+"alt_false_tr_supp", alt_false_tr_supp);
		inputs_priority = prop.getBoolean(prefix+"inputs_priority", inputs_priority);
	}
}
