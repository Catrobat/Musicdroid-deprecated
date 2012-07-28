package at.tugraz.ist.musicdroid;

import java.io.File;

import android.os.Environment;

public class MidiPlayer {
	private DrawTonesView toneView;
	private MidiFile midiFile;
	
	public MidiPlayer(DrawTonesView tone){
		toneView = tone;
		midiFile = new MidiFile();
		
		
	}
	
	public File createMidifile(){
		File directory;
		File path;
		directory = new File(Environment.getExternalStorageDirectory()+File.separator+"records"+File.separator+"piano_midi_sounds");
		
		
		
		
		return directory;
	}
}