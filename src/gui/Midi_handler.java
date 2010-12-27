package gui;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Transmitter;
import javax.sound.midi.Receiver;
import	javax.sound.midi.Sequence;
import	javax.sound.midi.Sequencer;
//import	javax.sound.midi.Track;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.SysexMessage;

public class Midi_handler {

	//private int nPorts;
	//private int port;

	private MidiDevice midiin;
	private MidiDevice midiout;
	private MidiDevice midithru;
	private MidiDevice.Info[]	aInfos;
	private Receiver receiver;
	private Receiver thruReceiver;
	private DumpReceiver dump_receiver;
	private Transmitter	transmitter;
	
	public int config_chain_id;
	public ConfigMisc config_misc;
	public ConfigPedal config_pedal;
	public ConfigPad config_pad;
	public Config3rd config_3rd;

	public Midi_handler () {
		midiin = null;
		midiout = null;
		midithru = null;
		receiver = null;
		thruReceiver = null;
		transmitter = null;
		config_chain_id = 0;
		config_misc = new ConfigMisc();
		config_pedal = new ConfigPedal();
		config_pad = new ConfigPad();
		config_3rd = new Config3rd();
		dump_receiver = new DumpReceiver();
	}
	
	public void Close_all_ports() {
		if (midiin != null) {
			if (midiin.isOpen()) {
				midiin.close();
			}
		}
		if (midiout != null) {
			if (midiout.isOpen()) {
				midiout.close();
			}
		}
		if (midithru != null) {
			if (midithru.isOpen()) {
				midithru.close();
			}
		}
	}
	
//	public void send_sysex(Receiver rr, byte [] buf, int size) {
	public void send_sysex(byte [] buf) {
		SysexMessage	sysexMessage = new SysexMessage();

		try {
			sysexMessage.setMessage(buf, buf.length);
			receiver.send(sysexMessage, -1);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void send_config_misc() {
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(config_misc.getSysex(config_chain_id));
			}
		}
	}
	
	public void send_config_pedal() {
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(config_pedal.getSysex(config_chain_id));
			}
		}
	}
	
	public void send_config_pad(int pad_id) {
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(config_pad.getSysex(config_chain_id, pad_id));
			}
		}
	}

	public void send_config_3rd(int third_id) {
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(config_3rd.getSysex(config_chain_id, third_id));
			}
		}
	}
	
	public void request_config_misc() {
		byte [] sx = new byte[5];
		
		sx[0] = Constants.SYSEX_START;
		sx[1] = Constants.MD_SYSEX;
		sx[2] = (byte)config_chain_id;
		sx[3] = Constants.MD_SYSEX_MISC;
		sx[4] = Constants.SYSEX_END;
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(sx);
			}
		}
	}

	public void request_config_pedal() {
		byte [] sx = new byte[5];
		
		sx[0] = Constants.SYSEX_START;
		sx[1] = Constants.MD_SYSEX;
		sx[2] = (byte)config_chain_id;
		sx[3] = Constants.MD_SYSEX_PEDAL;
		sx[4] = Constants.SYSEX_END;
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(sx);
			}
		}
	}
	
	public void request_config_pad(int pad_id) {
		byte [] sx = new byte[6];
		
		sx[0] = Constants.SYSEX_START;
		sx[1] = Constants.MD_SYSEX;
		sx[2] = (byte)config_chain_id;
		sx[3] = Constants.MD_SYSEX_PAD;
		sx[4] = (byte)pad_id;
		sx[5] = Constants.SYSEX_END;
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(sx);
			}
		}
	}

	public void request_config_3rd(int third_id) {
		byte [] sx = new byte[6];
		
		sx[0] = Constants.SYSEX_START;
		sx[1] = Constants.MD_SYSEX;
		sx[2] = (byte)config_chain_id;
		sx[3] = Constants.MD_SYSEX_3RD;
		sx[4] = (byte)third_id;
		sx[5] = Constants.SYSEX_END;
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(sx);
			}
		}
	}

	public void clear_midi_input() {
		byte [] result;
		result = null;
		int size = 1;
		
		if (midiin != null) {
			if (midiin.isOpen()) {
				// Clear MIDI input buffer
				while (size > 0) {
					result = dump_receiver.getByteMessage();
					if (result == null)
					{
						size = 0;
						//out("input buffer cleared");
					}
					else
					{
						size = result.length;
					}			
				}
			}
		}
	}
	
	public void get_midi() {
		byte [] buffer;
		buffer = null;
		int size = 0;
		
		if (midiin != null) {
			if (midiin.isOpen()) {
				buffer = dump_receiver.getByteMessage();
				
				if (buffer != null) {
					size = buffer.length;
					if (( buffer[0] == Constants.SYSEX_START) && (buffer[size-1] == Constants.SYSEX_END)) {
						if (buffer[1] == Constants.MD_SYSEX) {
							if (buffer[2] == (byte) config_chain_id) {
								switch(buffer[3]) {
								case Constants.MD_SYSEX_MISC:
									config_misc.setFromSysex(buffer);
									break;
								case Constants.MD_SYSEX_PEDAL:
									config_pedal.setFromSysex(buffer);
									break;
								case Constants.MD_SYSEX_PAD:
									config_pad.setFromSysex(buffer);
									break;
								case Constants.MD_SYSEX_3RD:
									config_3rd.setFromSysex(buffer);
									break;
								default:
									break;
								}
							}
						}
					} else {
						// TO-DO
					}
				}
			}
		}
	}
	
	public String [] getMidiInList() {
		String [] list;
		int nPorts;
		int port;
		aInfos = MidiSystem.getMidiDeviceInfo(); 
		nPorts = aInfos.length;
		int[] table = new int[nPorts];
		
		port = 0;		
		for (int i = 0; i < nPorts; i++) {
			try
			{
				midiin = MidiSystem.getMidiDevice(aInfos[i]);
				if (midiin.getMaxTransmitters() != 0)
				{
					table[port] = i;
					port++;
				}
			}
			catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to list MIDI In devices");
			}
			
		}
		list = new String[port];
		for (int i = 0; i < port; i++) {
			list[i] = aInfos[table[i]].getName();
		}
		
		return list;
	}
	
	public String [] getMidiOutList() {
		String [] list;
		int nPorts;
		int port;
		aInfos = MidiSystem.getMidiDeviceInfo(); 
		nPorts = aInfos.length;
		int[] table = new int[nPorts];
		
		port = 0;		
		for (int i = 0; i < nPorts; i++) {
			try
			{
				midiout = MidiSystem.getMidiDevice(aInfos[i]);
				if (midiout.getMaxReceivers() != 0)
				{
					table[port] = i;
					port++;
				}
			}
			catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to list MIDI In devices");
			}
			
		}
		list = new String[port];
		for (int i = 0; i < port; i++) {
			list[i] = aInfos[table[i]].getName();
		}
		
		return list;		
	}
	
	public void initPorts(ConfigOptions options) {
		aInfos = MidiSystem.getMidiDeviceInfo(); 
		int nPorts;
		nPorts = aInfos.length;

		Close_all_ports();
		
 		if (!options.MidiInName.equals("")) {
			try
			{
				for (int i = 0; i < nPorts; i++) {
					midiin = MidiSystem.getMidiDevice(aInfos[i]);
					if (midiin.getMaxTransmitters() != 0) {
						if (aInfos[i].getName().equals(options.MidiInName)) {
					    	midiin = MidiSystem.getMidiDevice(aInfos[i]);
					    	midiin.open();
							transmitter = midiin.getTransmitter();
							transmitter.setReceiver(dump_receiver);		
							break;
						}
					}
				}
			} catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to open MIDI In device");
			}
 		}

 		if (!options.MidiOutName.equals("")) {
			try
			{
				for (int i = 0; i < nPorts; i++) {
					midiout = MidiSystem.getMidiDevice(aInfos[i]);
					if (midiout.getMaxReceivers() != 0) {
						if (aInfos[i].getName().equals(options.MidiOutName)) {
					    	midiout = MidiSystem.getMidiDevice(aInfos[i]);
					    	midiout.open();
					    	receiver = midiout.getReceiver();
							break;
						}
					}
				}
			} catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to open MIDI Out device");
			}
 		}

 		if ((!options.MidiThruName.equals("")) && (options.useThruPort)) {
			try
			{
				for (int i = 0; i < nPorts; i++) {
					midithru = MidiSystem.getMidiDevice(aInfos[i]);
					if (midithru.getMaxReceivers() != 0) {
						if (aInfos[i].getName().equals(options.MidiThruName)) {
					    	midithru = MidiSystem.getMidiDevice(aInfos[i]);
					    	midithru.open();
					    	thruReceiver = midithru.getReceiver();
							break;
						}
					}
				}
			} catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to open MIDI Out device");
			}
 		}
 		
	}
	
}
