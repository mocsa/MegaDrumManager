package gui;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class ConfigPad {
	public short note = 0;
	public short channel = 9;
	public short curve = 0;
	public short threshold = 30;
	public short retrigger = 10;
	public short levelMax = 64;
	public short minScan = 20;
	public boolean type = false;
	public boolean autoLevel = true;
	public boolean dual = false;
	public boolean threeWay = false;
	public boolean special = false;
	public short gain = 0;
	public short xtalkLevel = 3;
	public short xtalkGroup = 0;
	public short dynTime = 3;
	public short dynLevel = 4;
	public short compression = 0;
	public short shift = 0;
	public short name = 0;
	public short altNote = 0;
	public short pressrollNote = 0;
	public boolean altNote_linked = false;
	public boolean pressrollNote_linked = false;

	
	public ConfigPad (){
	}
	
	public void copyToPropertiesConfiguration(PropertiesConfiguration prop, PropertiesConfigurationLayout layout, String prefix, Integer id) {
		id++;
		prefix = prefix+"["+id.toString()+"].";
		layout.setComment(prefix+"note", "\n#Input "+id.toString()+" settings");
		prop.setProperty(prefix+"note", note);
		prop.setProperty(prefix+"channel", channel);
		prop.setProperty(prefix+"curve", curve);
		prop.setProperty(prefix+"threshold", threshold);
		prop.setProperty(prefix+"retrigger", retrigger);
		prop.setProperty(prefix+"levelMax", levelMax);
		prop.setProperty(prefix+"minScan", minScan);
		prop.setProperty(prefix+"type", type);		
		prop.setProperty(prefix+"autoLevel", autoLevel);
		prop.setProperty(prefix+"dual", dual);
		prop.setProperty(prefix+"threeWay", threeWay);
		prop.setProperty(prefix+"special", special);
		prop.setProperty(prefix+"gain", gain);
		prop.setProperty(prefix+"xtalkLevel", xtalkLevel);
		prop.setProperty(prefix+"xtalkGroup", xtalkGroup);
		prop.setProperty(prefix+"dynTime", dynTime);
		prop.setProperty(prefix+"dynLevel", dynLevel);
		prop.setProperty(prefix+"compression", compression);
		prop.setProperty(prefix+"shift", shift);
		prop.setProperty(prefix+"name", name);
		prop.setProperty(prefix+"altNote", altNote);
		prop.setProperty(prefix+"pressrollNote", pressrollNote);
		prop.setProperty(prefix+"altNote_linked", altNote_linked);
		prop.setProperty(prefix+"pressrollNote_linked", pressrollNote_linked);				
	}

	public void copyFromPropertiesConfiguration(PropertiesConfiguration prop, String prefix, Integer id) {
		id++;
		prefix = prefix+"["+id.toString()+"].";
		note = prop.getShort(prefix+"note", note);
		channel = prop.getShort(prefix+"channel", channel);
		curve = prop.getShort(prefix+"curve", curve);
		threshold = prop.getShort(prefix+"threshold", threshold);
		retrigger = prop.getShort(prefix+"retrigger", retrigger);
		levelMax = prop.getShort(prefix+"levelMax", levelMax);
		minScan = prop.getShort(prefix+"minScan", minScan);
		type = prop.getBoolean(prefix+"type", type);		
		autoLevel = prop.getBoolean(prefix+"autoLevel", autoLevel);
		dual = prop.getBoolean(prefix+"dual", dual);
		threeWay = prop.getBoolean(prefix+"threeWay", threeWay);
		special = prop.getBoolean(prefix+"special", special);
		gain = prop.getShort(prefix+"gain", gain);
		xtalkLevel = prop.getShort(prefix+"xtalkLevel", xtalkLevel);
		xtalkGroup = prop.getShort(prefix+"xtalkGroup", xtalkGroup);
		dynTime = prop.getShort(prefix+"dynTime", dynTime);
		dynLevel = prop.getShort(prefix+"dynLevel", dynLevel);
		compression = prop.getShort(prefix+"compression", compression);
		shift = prop.getShort(prefix+"shift", shift);
		name = prop.getShort(prefix+"name", name);
		altNote = prop.getShort(prefix+"altNote", altNote);
		pressrollNote = prop.getShort(prefix+"pressrollNote", pressrollNote);
		altNote_linked = prop.getBoolean(prefix+"altNote_linked", altNote_linked);
		pressrollNote_linked = prop.getBoolean(prefix+"pressrollNote_linked", pressrollNote_linked);				
	}	
}
