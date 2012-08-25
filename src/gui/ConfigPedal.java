package gui;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class ConfigPedal {
	public boolean type;
	public boolean autoLevels;
	public boolean altIn;
	public boolean reverseLevels;
	public int curve;
	public int chickDelay;
	public int cc;
	public int ccRdcLvl;
	public int lowLevel;
	public int highLevel;
	public int openLevel;
	public int closedLevel;
	public int shortThres;
	public int longThres;
	public int hhInput = 2;
	public boolean softChicks;
	public int semiOpenLevel;
	public int halfOpenLevel;
	public int bowSemiOpenNote;
	public int edgeSemiOpenNote;
	public int bellSemiOpenNote;
	public int bowHalfOpenNote;
	public int edgeHalfOpenNote;
	public int bellHalfOpenNote;
	public int bowSemiClosedNote;
	public int edgeSemiClosedNote;
	public int bellSemiClosedNote;
	public int bowClosedNote;
	public int edgeClosedNote;
	public int bellClosedNote;
	public int chickNote;
	public int splashNote;
	
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
		prop.setProperty(prefix+"ccRdcLvl", ccRdcLvl);
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
		curve = Utils.validateInt(prop.getInt(prefix+"curve", curve),0,15,curve);
		chickDelay = Utils.validateInt(prop.getInt(prefix+"chickDelay", chickDelay),0,127,chickDelay);
		cc = Utils.validateInt(prop.getInt(prefix+"cc", cc),0,127,cc);
		ccRdcLvl = Utils.validateInt(prop.getInt(prefix+"ccRdcLvl", ccRdcLvl),0,127,ccRdcLvl);
		lowLevel = Utils.validateInt(prop.getInt(prefix+"lowLevel", lowLevel),0,1023,lowLevel);		
		highLevel = Utils.validateInt(prop.getInt(prefix+"highLevel", highLevel),0,1023,highLevel);
		openLevel = Utils.validateInt(prop.getInt(prefix+"openLevel", openLevel),0,127,openLevel);
		closedLevel = Utils.validateInt(prop.getInt(prefix+"closedLevel", closedLevel),0,127,closedLevel);
		shortThres = Utils.validateInt(prop.getInt(prefix+"shortThres", shortThres),0,127,shortThres);
		longThres = Utils.validateInt(prop.getInt(prefix+"longThres", longThres),0,127,longThres);
		hhInput = Utils.validateInt(prop.getInt(prefix+"hhInput", hhInput),2,127,hhInput);
		softChicks = prop.getBoolean(prefix+"softChicks", softChicks);
		semiOpenLevel = Utils.validateInt(prop.getInt(prefix+"semiOpenLevel", semiOpenLevel),0,127,semiOpenLevel);
		halfOpenLevel = Utils.validateInt(prop.getInt(prefix+"halfOpenLevel", halfOpenLevel),0,127,halfOpenLevel);
		bowSemiOpenNote = Utils.validateInt(prop.getInt(prefix+"bowSemiOpenNote", bowSemiOpenNote),0,127,bowSemiOpenNote);
		edgeSemiOpenNote = Utils.validateInt(prop.getInt(prefix+"edgeSemiOpenNote", edgeSemiOpenNote),0,127,edgeSemiOpenNote);
		bellSemiOpenNote = Utils.validateInt(prop.getInt(prefix+"bellSemiOpenNote", bellSemiOpenNote),0,127,bellSemiOpenNote);
		bowHalfOpenNote = Utils.validateInt(prop.getInt(prefix+"bowHalfOpenNote", bowHalfOpenNote),0,127,bowHalfOpenNote);
		edgeHalfOpenNote = Utils.validateInt(prop.getInt(prefix+"edgeHalfOpenNote", edgeHalfOpenNote),0,127,edgeHalfOpenNote);
		bellHalfOpenNote = Utils.validateInt(prop.getInt(prefix+"bellHalfOpenNote", bellHalfOpenNote),0,127,bellHalfOpenNote);
		bowSemiClosedNote = Utils.validateInt(prop.getInt(prefix+"bowSemiClosedNote", bowSemiClosedNote),0,127,bowSemiClosedNote);		
		edgeSemiClosedNote = Utils.validateInt(prop.getInt(prefix+"edgeSemiClosedNote", edgeSemiClosedNote),0,127,edgeSemiClosedNote);
		bellSemiClosedNote = Utils.validateInt(prop.getInt(prefix+"bellSemiClosedNote", bellSemiClosedNote),0,127,bellSemiClosedNote);
		bowClosedNote = Utils.validateInt(prop.getInt(prefix+"bowClosedNote", bowClosedNote),0,127,bowClosedNote);
		edgeClosedNote = Utils.validateInt(prop.getInt(prefix+"edgeClosedNote", edgeClosedNote),0,127,edgeClosedNote);
		bellClosedNote = Utils.validateInt(prop.getInt(prefix+"bellClosedNote", bellClosedNote),0,127,bellClosedNote);
		chickNote = Utils.validateInt(prop.getInt(prefix+"chickNote", chickNote),0,127,chickNote);
		splashNote = Utils.validateInt(prop.getInt(prefix+"splashNote", splashNote),0,127,splashNote);
	}
	
}
