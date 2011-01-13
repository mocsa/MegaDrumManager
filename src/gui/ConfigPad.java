package gui;

public class ConfigPad {
	public short note;
	public short channel = 9;
	public short curve;
	public short threshold = 30;
	public short retrigger = 10;
	public short levelMax = 64;
	public short minScan = 20;
	public boolean type;
	public boolean autoLevel = true;
	public boolean dual;
	public boolean threeWay;
	public boolean special;
	public short gain = 0;
	public short xtalkLevel = 3;
	public short xtalkGroup;
	public short dynTime = 3;
	public short dynLevel = 4;
	public short compression;
	public short shift;
	public short name;
	public short altNote;
	public short pressrollNote;
	public boolean altNote_linked;
	public boolean pressrollNote_linked;

	
	public ConfigPad (){
	}	
	
}
