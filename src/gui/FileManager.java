package gui;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileManager {
	private JFileChooser fileChooser;
	private JFrame parent;
	File file = null;
	FileInputStream fis = null;
	BufferedInputStream bis = null;
	DataInputStream dis = null;
	
	public FileManager (JFrame parentFrame) {
		fileChooser = new JFileChooser();
		parent = parentFrame;
		//file = new File();
	}
	
	public void save_all(ConfigFull config) {
		int returnVal;
		returnVal = fileChooser.showSaveDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			System.out.printf("Selected file = %s", file.getName());
			if (file.exists()) {
				
			} else {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
