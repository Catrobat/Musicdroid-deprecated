package at.tugraz.musicdroid.tone;

import java.io.Serializable;

public class Time implements Serializable {
	
	private static final long serialVersionUID = 888797518903394570L;
	
	private int beatsPerTact;
	private NoteValue noteLength;
	
	public Time() {
		this(4, NoteValue.QUARTER);
	}
	
	public Time(int beatsPerTact, NoteValue noteLength) {
		this.beatsPerTact = beatsPerTact;
		this.noteLength = noteLength;
	}
	
	public int getBeatsPerTact() {
		return beatsPerTact;
	}
	
	public NoteValue getNoteLength() {
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
