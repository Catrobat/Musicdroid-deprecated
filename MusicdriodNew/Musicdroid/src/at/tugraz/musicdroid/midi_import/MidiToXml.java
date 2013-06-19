package at.tugraz.musicdroid.midi_import;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.R;

import com.leff.midi.MidiFile;
import com.leff.midi.util.MidiProcessor;

public class MidiToXml {

	private long length_;
	
	public long getLength() {
		return length_;
	}

	public void setLength(long length) {
		this.length_ = length;
	}

	public MidiToXml(){
		length_=0;
	}

	public void readMidi(){
		File f= new File("/sdcard/MERCBENZ.midi");
		MidiFile midifile;
		try {
			midifile = new MidiFile(f);
			MidiProcessor mipro= new MidiProcessor(midifile);
			//mipro.start();
			length_=midifile.getLengthInTicks();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
