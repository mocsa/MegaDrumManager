package gui;

import	javax.sound.midi.MidiMessage;
import	javax.sound.midi.ShortMessage;
import	javax.sound.midi.MetaMessage;
import	javax.sound.midi.SysexMessage;
import	javax.sound.midi.Receiver;

public class DumpReceiver
	implements	Receiver
{

	public byte[] bytemessage = null;

	public DumpReceiver()
	{
	}

	public void close()
	{
	}
	
	public byte[] getByteMessage()
	{
		byte[] msg;
		msg = bytemessage;
		bytemessage = null;
		return msg;
	}

	public void getShortMessage(ShortMessage message)
	{
		bytemessage = message.getMessage();
	}

	public void getSysexMessage(SysexMessage message)
	{
		bytemessage = message.getMessage();
	}

	public void getMetaMessage(MetaMessage message)
	{
		bytemessage = message.getMessage();
	}

	public void send(MidiMessage message, long lTimeStamp)
	{
		if (message instanceof ShortMessage)
		{
			getShortMessage((ShortMessage) message);
		}
		else if (message instanceof SysexMessage)
		{
			getSysexMessage((SysexMessage) message);
		}
		else if (message instanceof MetaMessage)
		{
			getMetaMessage((MetaMessage) message);
		}
		else
		{
			//strMessage = "unknown message type";
		}
	}

}

