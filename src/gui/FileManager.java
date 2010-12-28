package gui;

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

public class FileManager {
	private JFileChooser fileChooser;
	private JFrame parent;
	private File file = null;
	private ConfigFileFilter configFileFilter;
	private SysexFileFilter sysexFileFilter;

	
	public FileManager (JFrame parentFrame) {
		fileChooser = new JFileChooser();
		parent = parentFrame;
		configFileFilter = new ConfigFileFilter();
		sysexFileFilter = new SysexFileFilter();
		
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
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				ObjectInputStream ois = new ObjectInputStream(fis);
				try {
					config = (ConfigOptions)ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(config);
				oos.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		return config;
	}
	
	public void saveLastOptions(ConfigOptions config) {
		file = new File(Constants.MD_MANAGER_CONFIG);	
		try {
			if (file.exists()) {
				file.delete();
			}
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
