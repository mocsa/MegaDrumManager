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
		curve = Utils.validateShort(prop.getShort(prefix+"curve", curve),0,15,curve);
		chickDelay = Utils.validateShort(prop.getShort(prefix+"chickDelay", chickDelay),0,127,chickDelay);
		cc = Utils.validateShort(prop.getShort(prefix+"cc", cc),0,127,cc);
		lowLevel = Utils.validateInt(prop.getInt(prefix+"lowLevel", lowLevel),0,1023,lowLevel);		
		highLevel = Utils.validateInt(prop.getInt(prefix+"highLevel", highLevel),0,1023,highLevel);
		openLevel = Utils.validateShort(prop.getShort(prefix+"openLevel", openLevel),0,127,openLevel);
		closedLevel = Utils.validateShort(prop.getShort(prefix+"closedLevel", closedLevel),0,127,closedLevel);
		shortThres = Utils.validateShort(prop.getShort(prefix+"shortThres", shortThres),0,127,shortThres);
		longThres = Utils.validateShort(prop.getShort(prefix+"longThres", longThres),0,127,longThres);
		hhInput = Utils.validateShort(prop.getShort(prefix+"hhInput", hhInput),0,127,hhInput);
		softChicks = prop.getBoolean(prefix+"softChicks", softChicks);
		semiOpenLevel = Utils.validateShort(prop.getShort(prefix+"semiOpenLevel", semiOpenLevel),0,127,semiOpenLevel);
		halfOpenLevel = Utils.validateShort(prop.getShort(prefix+"halfOpenLevel", halfOpenLevel),0,127,halfOpenLevel);
		bowSemiOpenNote = Utils.validateShort(prop.getShort(prefix+"bowSemiOpenNote", bowSemiOpenNote),0,127,bowSemiOpenNote);
		edgeSemiOpenNote = Utils.validateShort(prop.getShort(prefix+"edgeSemiOpenNote", edgeSemiOpenNote),0,127,edgeSemiOpenNote);
		bellSemiOpenNote = Utils.validateShort(prop.getShort(prefix+"bellSemiOpenNote", bellSemiOpenNote),0,127,bellSemiOpenNote);
		bowHalfOpenNote = Utils.validateShort(prop.getShort(prefix+"bowHalfOpenNote", bowHalfOpenNote),0,127,bowHalfOpenNote);
		edgeHalfOpenNote = Utils.validateShort(prop.getShort(prefix+"edgeHalfOpenNote", edgeHalfOpenNote),0,127,edgeHalfOpenNote);
		bellHalfOpenNote = Utils.validateShort(prop.getShort(prefix+"bellHalfOpenNote", bellHalfOpenNote),0,127,bellHalfOpenNote);
		bowSemiClosedNote = Utils.validateShort(prop.getShort(prefix+"bowSemiClosedNote", bowSemiClosedNote),0,127,bowSemiClosedNote);		
		edgeSemiClosedNote = Utils.validateShort(prop.getShort(prefix+"edgeSemiClosedNote", edgeSemiClosedNote),0,127,edgeSemiClosedNote);
		bellSemiClosedNote = Utils.validateShort(prop.getShort(prefix+"bellSemiClosedNote", bellSemiClosedNote),0,127,bellSemiClosedNote);
		bowClosedNote = Utils.validateShort(prop.getShort(prefix+"bowClosedNote", bowClosedNote),0,127,bowClosedNote);
		edgeClosedNote = Utils.validateShort(prop.getShort(prefix+"edgeClosedNote", edgeClosedNote),0,127,edgeClosedNote);
		bellClosedNote = Utils.validateShort(prop.getShort(prefix+"bellClosedNote", bellClosedNote),0,127,bellClosedNote);
		chickNote = Utils.validateShort(prop.getShort(prefix+"chickNote", chickNote),0,127,chickNote);
		splashNote = Utils.validateShort(prop.getShort(prefix+"splashNote", splashNote),0,127,splashNote);
	}
	
}
