package at.tugraz.musicdroid.tone;

public abstract class Symbol {

	protected NoteValue noteLength;
	
	Symbol(NoteValue noteLength) {
		this.noteLength = noteLength;
	}
	
	public NoteValue getNoteLength() {
		return noteLength;
	}
}
