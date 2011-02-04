package gui;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class Config3rd {
	public short note;
	public short threshold = 30;
	public short pressrollNote;
	public short altNote;
	public short dampenedNote;

	public Config3rd () {
	}

	public void copyToPropertiesConfiguration(PropertiesConfiguration prop, PropertiesConfigurationLayout layout, String prefix, Integer id) {
		id = id*2+2;
		Integer rim = id+1;
		prefix = prefix+"["+id.toString()+"+"+rim.toString()+"].";
		layout.setComment(prefix+"note", "\n#Third zone for intputs "+id.toString() + " and " + rim.toString() + " settings");
		prop.setProperty(prefix+"note", note);
		prop.setProperty(prefix+"threshold", threshold);
		prop.setProperty(prefix+"altNote", altNote);
		prop.setProperty(prefix+"pressrollNote", pressrollNote);
		prop.setProperty(prefix+"dampenedNote", dampenedNote);
	}

	public void copyFromPropertiesConfiguration(PropertiesConfiguration prop, String prefix, Integer id) {
		id = id*2+2;
		Integer rim = id+1;
		prefix = prefix+"["+id.toString()+"+"+rim.toString()+"].";
		note = Utils.validateShort(prop.getShort(prefix+"note", note),0,127,note);
		threshold = Utils.validateShort(prop.getShort(prefix+"threshold", threshold),0,256,threshold);
		altNote = Utils.validateShort(prop.getShort(prefix+"altNote", altNote),0,127,altNote);
		pressrollNote = Utils.validateShort(prop.getShort(prefix+"pressrollNote", pressrollNote),0,127,pressrollNote);
		dampenedNote = Utils.validateShort(prop.getShort(prefix+"dampenedNote", dampenedNote),0,127,dampenedNote);
	}	
	
}
