package gui;

import java.util.EventListener;

public interface MidiEventListener extends EventListener {
    public void midiEventOccurred(MidiEvent evt);
}
