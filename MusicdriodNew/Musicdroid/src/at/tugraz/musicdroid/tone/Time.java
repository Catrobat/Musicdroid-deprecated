package at.tugraz.musicdroid.tone;

import java.io.Serializable;

public class Time implements Serializable {
	
	private static final long serialVersionUID = 888797518903394570L;
	
	private int beatsPerTact;
	private NoteLength noteLength;
	
	public Time() {
		this(4, NoteLength.QUARTER);
	}
	
	public Time(int beatsPerTact, NoteLength noteLength) {
		this.beatsPerTact = beatsPerTact;
		this.noteLength = noteLength;
	}
	
	public int getBeatsPerTact() {
		return beatsPerTact;
	}
	
	public NoteLength getNoteLength() {
		return noteLength;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Time)) {
			return false;
		}
		
		Time time = (Time) obj;
		
		return (beatsPerTact == time.getBeatsPerTact()) && noteLength.equals(time.getNoteLength());
	}
	
	@Override
	public String toString() {
		return "[Time] beatsPerTact=" + beatsPerTact + " noteLength=" + noteLength;
	}
}
