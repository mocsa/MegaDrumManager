package gui;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

class ConfigFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".mds");
    }
    
    public String getDescription() {
        return "MegaDrum full config files (*.mds)";
    }
}

class SysexFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".syx");
    }
    
    public String getDescription() {
        return "Sysex files (*.syx)";
    }
}

class BinFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".bin");
    }
    
    public String getDescription() {
        return "Firmware files (*.bin)";
    }
}

public class FileManager {
	private JFileChooser fileChooser;
	private JFrame parent;
	private File file = null;
	private ConfigFileFilter configFileFilter;
	private SysexFileFilter sysexFileFilter;
	private BinFileFilter binFileFilter;
	private Properties prop;
	
	public FileManager (JFrame parentFrame) {
		fileChooser = new JFileChooser();
		parent = parentFrame;
		configFileFilter = new ConfigFileFilter();
		sysexFileFilter = new SysexFileFilter();
		binFileFilter = new BinFileFilter();
		prop = new Properties();
		//file = new File();
	}
	
	public void save_all(ConfigFull config, ConfigOptions options) {
		int returnVal;
		fileChooser.setFileFilter(configFileFilter);
		if (!options.lastFullPathConfig.equals("")) {
			fileChooser.setCurrentDirectory(new File(options.lastFullPathConfig));
		}
		returnVal = fileChooser.showSaveDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (!(file.getName().toLowerCase().endsWith(".mds"))) {
				file = new File(file.getAbsolutePath() + ".mds");
			}
			options.lastFullPathConfig = file.getAbsolutePath();
			if (file.exists()) {
				file.delete();
			}
			try {
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(config);
				oos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ConfigFull load_all(ConfigFull configOld, ConfigOptions options) {
		ConfigFull config = new ConfigFull();
		int returnVal;
		if (!options.lastFullPathConfig.equals("")) {
			fileChooser.setCurrentDirectory(new File(options.lastFullPathConfig));
		}
		fileChooser.setFileFilter(configFileFilter);
		returnVal = fileChooser.showOpenDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			options.lastFullPathConfig = file.getAbsolutePath();
			if (file.exists()) {
				try {
					FileInputStream fis = new FileInputStream(file);
					ObjectInputStream ois = new ObjectInputStream(fis);
					try {
						config = (ConfigFull)ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			return config;
		} else {
			return configOld;			
		}
	}
	
	public File selectFirmwareFile(ConfigOptions options) {
		int returnVal;
		file = null;
		if (!options.lastFullPathConfig.equals("")) {
			fileChooser.setCurrentDirectory(new File(options.lastFullPathConfig));
		}
		fileChooser.setFileFilter(binFileFilter);
		returnVal = fileChooser.showOpenDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			options.lastFullPathConfig = file.getAbsolutePath();
		}
		return file;
	}

	public ConfigFull loadAllSilent(ConfigFull configOld, ConfigOptions options) {
		ConfigFull config = new ConfigFull();
		file = new File(options.lastFullPathConfig);
		options.lastFullPathConfig = file.getAbsolutePath();
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				try {
					config = (ConfigFull)ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.printf("Error reading config from file\n");
					return configOld;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error reading file\n");
				e.printStackTrace();
				file.delete();
				return configOld;
			}				
			return config;
		} else {
			return configOld;
		}
	}

	public ConfigOptions loadLastOptions(ConfigOptions config) {
		file = new File(Constants.MD_MANAGER_CONFIG);
		try {
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				prop.load(fis);
				if ((prop.getProperty("MDconfigVersion") != null) && (Double.parseDouble(prop.getProperty("MDconfigVersion")) == Constants.MD_CONFIG_VERSION)) {
					config.useSamePort = Boolean.parseBoolean(prop.getProperty("useSamePort"));
					config.useThruPort = Boolean.parseBoolean(prop.getProperty("useThruPort"));
					config.autoOpenPorts = Boolean.parseBoolean(prop.getProperty("autoOpenPorts"));
					config.saveOnExit = Boolean.parseBoolean(prop.getProperty("saveOnExit"));
					config.lastDir = prop.getProperty("lastDir");
					config.lastFullPathConfig = prop.getProperty("lastFullPathConfig");
					config.MidiInName = prop.getProperty("MidiInName");
					config.MidiOutName = prop.getProperty("MidiOutName");
					config.MidiThruName = prop.getProperty("MidiThruName");
					config.chainId = Integer.parseInt(prop.getProperty("chainId"));
					config.inputsCount = Integer.parseInt(prop.getProperty("inputsCount"));
					config.sysexDelay = Integer.parseInt(prop.getProperty("sysexDelay"));
					config.mainWindowPosition = new Point(
							Integer.parseInt(prop.getProperty("mainWindowPositionX")),
							Integer.parseInt(prop.getProperty("mainWindowPositionY"))
							);
					for (int i = 0;i<Constants.PANELS_COUNT;i++) {
						config.framesPositions[i] = new Point (
								Integer.parseInt(prop.getProperty("framesPositions"+ ((Integer)i).toString()+"X")),
								Integer.parseInt(prop.getProperty("framesPositions"+ ((Integer)i).toString()+"Y"))							
								);
						config.showPanels[i] = Integer.parseInt(prop.getProperty("showPanels"+ ((Integer)i).toString()));
					}
				}
			} else {
				saveLastOptions(config);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			saveLastOptions(config);
		}	
		return config;
	}
	
	public void saveLastOptions(ConfigOptions config) {
		file = new File(Constants.MD_MANAGER_CONFIG);
		prop.setProperty("MDconfigVersion", Constants.MD_CONFIG_VERSION.toString());
		prop.setProperty("useSamePort", ((Boolean)config.useSamePort).toString());
		prop.setProperty("useThruPort", ((Boolean)config.useThruPort).toString());
		prop.setProperty("autoOpenPorts", ((Boolean)config.autoOpenPorts).toString());
		prop.setProperty("saveOnExit", ((Boolean)config.saveOnExit).toString());
		prop.setProperty("lastDir", config.lastDir);
		prop.setProperty("lastFullPathConfig", config.lastFullPathConfig);
		prop.setProperty("MidiInName", config.MidiInName);
		prop.setProperty("MidiOutName", config.MidiOutName);
		prop.setProperty("MidiThruName", config.MidiThruName);
		prop.setProperty("chainId", ((Integer)config.chainId).toString());
		prop.setProperty("inputsCount", ((Integer)config.inputsCount).toString());
		prop.setProperty("sysexDelay", ((Integer)config.sysexDelay).toString());
		prop.setProperty("mainWindowPositionX", ((Integer)config.mainWindowPosition.x).toString());
		prop.setProperty("mainWindowPositionY", ((Integer)config.mainWindowPosition.y).toString());
		for (int i = 0;i<Constants.PANELS_COUNT;i++) {
			prop.setProperty("framesPositions"+ ((Integer)i).toString()+"X", ((Integer)config.framesPositions[i].x).toString());
			prop.setProperty("framesPositions"+ ((Integer)i).toString()+"Y", ((Integer)config.framesPositions[i].y).toString());

			prop.setProperty("showPanels"+ ((Integer)i).toString(), ((Integer)config.showPanels[i]).toString());
		}
		try {
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			prop.store(fos, "MegaDrum config");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		
	}
		
}
