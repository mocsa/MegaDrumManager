package gui;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class ConfigPedal {
	public boolean type;
	public boolean autoLevels;
	public boolean altIn;
	public boolean reverseLevels;
	public short curve;
	public short chickDelay;
	public short cc;
	public int lowLevel;
	public int highLevel;
	public short openLevel;
	public short closedLevel;
	public short shortThres;
	public short longThres;
	public short hhInput;
	public boolean softChicks;
	public short semiOpenLevel;
	public short halfOpenLevel;
	public short bowSemiOpenNote;
	public short edgeSemiOpenNote;
	public short bellSemiOpenNote;
	public short bowHalfOpenNote;
	public short edgeHalfOpenNote;
	public short bellHalfOpenNote;
	public short bowSemiClosedNote;
	public short edgeSemiClosedNote;
	public short bellSemiClosedNote;
	public short bowClosedNote;
	public short edgeClosedNote;
	public short bellClosedNote;
	public short chickNote;
	public short splashNote;
	
	public ConfigPedal (){
	}
	public void copyToPropertiesConfiguration(PropertiesConfiguration prop, PropertiesConfigurationLayout layout, String prefix) {
		layout.setComment(prefix+"type", "\n#Pedal settings");
		prop.setProperty(prefix+"type", type);
		prop.setProperty(prefix+"autoLevels", autoLevels);
		prop.setProperty(prefix+"altIn", altIn);
		prop.setProperty(prefix+"reverseLevels", reverseLevels);
		prop.setProperty(prefix+"curve", curve);
		prop.setProperty(prefix+"chickDelay", chickDelay);
		prop.setProperty(prefix+"cc", cc);
		prop.setProperty(prefix+"lowLevel", lowLevel);		
		prop.setProperty(prefix+"highLevel", highLevel);
		prop.setProperty(prefix+"openLevel", openLevel);
		prop.setProperty(prefix+"closedLevel", closedLevel);
		prop.setProperty(prefix+"shortThres", shortThres);
		prop.setProperty(prefix+"longThres", longThres);
		prop.setProperty(prefix+"hhInput", hhInput);
		prop.setProperty(prefix+"softChicks", softChicks);
		prop.setProperty(prefix+"semiOpenLevel", semiOpenLevel);
		prop.setProperty(prefix+"halfOpenLevel", halfOpenLevel);
		prop.setProperty(prefix+"bowSemiOpenNote", bowSemiOpenNote);
		prop.setProperty(prefix+"edgeSemiOpenNote", edgeSemiOpenNote);
		prop.setProperty(prefix+"bellSemiOpenNote", bellSemiOpenNote);
		prop.setProperty(prefix+"bowHalfOpenNote", bowHalfOpenNote);
		prop.setProperty(prefix+"edgeHalfOpenNote", edgeHalfOpenNote);
		prop.setProperty(prefix+"bellHalfOpenNote", bellHalfOpenNote);
		prop.setProperty(prefix+"bowSemiClosedNote", bowSemiClosedNote);		
		prop.setProperty(prefix+"edgeSemiClosedNote", edgeSemiClosedNote);
		prop.setProperty(prefix+"bellSemiClosedNote", bellSemiClosedNote);
		prop.setProperty(prefix+"bowClosedNote", bowClosedNote);
		prop.setProperty(prefix+"edgeClosedNote", edgeClosedNote);
		prop.setProperty(prefix+"bellClosedNote", bellClosedNote);
		prop.setProperty(prefix+"chickNote", chickNote);
		prop.setProperty(prefix+"splashNote", splashNote);
		
	}

	public void copyFromPropertiesConfiguration(PropertiesConfiguration prop, String prefix) {
		type = prop.getBoolean(prefix+"type", type);
		autoLevels = prop.getBoolean(prefix+"autoLevels", autoLevels);
		altIn = prop.getBoolean(prefix+"altIn", altIn);
		reverseLevels = prop.getBoolean(prefix+"reverseLevels", reverseLevels);
		curve = prop.getShort(prefix+"curve", curve);
		chickDelay = prop.getShort(prefix+"chickDelay", chickDelay);
		cc = prop.getShort(prefix+"cc", cc);
		lowLevel = prop.getInt(prefix+"lowLevel", lowLevel);		
		highLevel = prop.getInt(prefix+"highLevel", highLevel);
		openLevel = prop.getShort(prefix+"openLevel", openLevel);
		closedLevel = prop.getShort(prefix+"closedLevel", closedLevel);
		shortThres = prop.getShort(prefix+"shortThres", shortThres);
		longThres = prop.getShort(prefix+"longThres", longThres);
		hhInput = prop.getShort(prefix+"hhInput", hhInput);
		softChicks = prop.getBoolean(prefix+"softChicks", softChicks);
		semiOpenLevel = prop.getShort(prefix+"semiOpenLevel", semiOpenLevel);
		halfOpenLevel = prop.getShort(prefix+"halfOpenLevel", halfOpenLevel);
		bowSemiOpenNote = prop.getShort(prefix+"bowSemiOpenNote", bowSemiOpenNote);
		edgeSemiOpenNote = prop.getShort(prefix+"edgeSemiOpenNote", edgeSemiOpenNote);
		bellSemiOpenNote = prop.getShort(prefix+"bellSemiOpenNote", bellSemiOpenNote);
		bowHalfOpenNote = prop.getShort(prefix+"bowHalfOpenNote", bowHalfOpenNote);
		edgeHalfOpenNote = prop.getShort(prefix+"edgeHalfOpenNote", edgeHalfOpenNote);
		bellHalfOpenNote = prop.getShort(prefix+"bellHalfOpenNote", bellHalfOpenNote);
		bowSemiClosedNote = prop.getShort(prefix+"bowSemiClosedNote", bowSemiClosedNote);		
		edgeSemiClosedNote = prop.getShort(prefix+"edgeSemiClosedNote", edgeSemiClosedNote);
		bellSemiClosedNote = prop.getShort(prefix+"bellSemiClosedNote", bellSemiClosedNote);
		bowClosedNote = prop.getShort(prefix+"bowClosedNote", bowClosedNote);
		edgeClosedNote = prop.getShort(prefix+"edgeClosedNote", edgeClosedNote);
		bellClosedNote = prop.getShort(prefix+"bellClosedNote", bellClosedNote);
		chickNote = prop.getShort(prefix+"chickNote", chickNote);
		splashNote = prop.getShort(prefix+"splashNote", splashNote);
	}
	
}
