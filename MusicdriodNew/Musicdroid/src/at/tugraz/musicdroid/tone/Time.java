package at.tugraz.musicdroid.tone;

public class Time {
	
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
}
