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
	//private FileInputStream fis = null;
	//private BufferedInputStream bis = null;
	//private DataInputStream dis = null;
	//private FileOutputStream fos = null;
	//private BufferedOutputStream bos = null;
	//private DataOutputStream dos = null;
	private ConfigFileFilter configFileFilter;
	private SysexFileFilter sysexFileFilter;

	
	public FileManager (JFrame parentFrame) {
		fileChooser = new JFileChooser();
		parent = parentFrame;
		configFileFilter = new ConfigFileFilter();
		sysexFileFilter = new SysexFileFilter();
		
		//file = new File();
	}
	
	public void save_all(ConfigFull config) {
		int returnVal;
		fileChooser.setFileFilter(configFileFilter);
		returnVal = fileChooser.showSaveDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (!(file.getName().toLowerCase().endsWith(".mds"))) {
				file = new File(file.getAbsolutePath() + ".mds");
			}
			if (file.exists()) {
				
			} else {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
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

	public ConfigFull load_all() {
		ConfigFull config = new ConfigFull();
		int returnVal;
		fileChooser.setFileFilter(configFileFilter);
		returnVal = fileChooser.showOpenDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
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
			} else {
			}
		}
		return config;
	}

}
