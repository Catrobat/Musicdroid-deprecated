package at.tugraz.musicdroid.tone;

public abstract class Symbol {

	protected NoteLength noteLength;
	
	Symbol(NoteLength noteLength) {
		this.noteLength = noteLength;
	}
	
	public NoteLength getNoteLength() {
		return noteLength;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Symbol)) {
			return false;
		}
		
		Symbol symbol = (Symbol) obj;
		
		return noteLength.equals(symbol.getNoteLength());
	}
	
	@Override
	public String toString() {
		return "[Symbol] noteLength=" + noteLength;
	}
}
