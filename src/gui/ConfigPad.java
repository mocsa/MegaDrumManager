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
		note = Utils.validateShort(prop.getShort(prefix+"note", note),0,127,note);
		channel = Utils.validateShort(prop.getShort(prefix+"channel", channel),0,15,channel);
		curve = Utils.validateShort(prop.getShort(prefix+"curve", curve),0,15,curve);
		threshold = Utils.validateShort(prop.getShort(prefix+"threshold", threshold),1,127,threshold);
		retrigger = Utils.validateShort(prop.getShort(prefix+"retrigger", retrigger),0,127,retrigger);
		levelMax = Utils.validateShort(prop.getShort(prefix+"levelMax", levelMax),64,1023,levelMax);
		minScan = Utils.validateShort(prop.getShort(prefix+"minScan", minScan),10,100,minScan);
		type = prop.getBoolean(prefix+"type", type);		
		autoLevel = prop.getBoolean(prefix+"autoLevel", autoLevel);
		dual = prop.getBoolean(prefix+"dual", dual);
		threeWay = prop.getBoolean(prefix+"threeWay", threeWay);
		special = prop.getBoolean(prefix+"special", special);
		gain = Utils.validateShort(prop.getShort(prefix+"gain", gain),0,8,gain);
		xtalkLevel = Utils.validateShort(prop.getShort(prefix+"xtalkLevel", xtalkLevel),0,7,xtalkLevel);
		xtalkGroup = Utils.validateShort(prop.getShort(prefix+"xtalkGroup", xtalkGroup),0,7,xtalkGroup);
		dynTime = Utils.validateShort(prop.getShort(prefix+"dynTime", dynTime),0,15,dynTime);
		dynLevel = Utils.validateShort(prop.getShort(prefix+"dynLevel", dynLevel),0,15,dynLevel);
		compression = Utils.validateShort(prop.getShort(prefix+"compression", compression),0,7,compression);
		shift = Utils.validateShort(prop.getShort(prefix+"shift", shift),0,7,shift);
		name = Utils.validateShort(prop.getShort(prefix+"name", name),0,127,name);
		altNote = Utils.validateShort(prop.getShort(prefix+"altNote", altNote),0,127,altNote);
		pressrollNote = Utils.validateShort(prop.getShort(prefix+"pressrollNote", pressrollNote),0,127,pressrollNote);
		altNote_linked = prop.getBoolean(prefix+"altNote_linked", altNote_linked);
		pressrollNote_linked = prop.getBoolean(prefix+"pressrollNote_linked", pressrollNote_linked);				
	}	
}
