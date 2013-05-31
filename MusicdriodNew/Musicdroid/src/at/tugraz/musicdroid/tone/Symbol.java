package at.tugraz.musicdroid.tone;

public abstract class Symbol {

	protected NoteValue length;
	
	Symbol(NoteValue length) {
		this.length = length;
	}
	
	public NoteValue getLength() {
		return length;
	}
}
