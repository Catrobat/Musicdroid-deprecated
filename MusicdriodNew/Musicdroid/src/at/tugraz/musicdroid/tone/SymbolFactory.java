package at.tugraz.musicdroid.tone;

public class SymbolFactory {

	private SymbolFactory() {
	}
	
	public static Tone createC1(NoteValue length) {
		return new Tone(length, NoteName.C1, 24);
	}
	
	public static Break createBreak(NoteValue length) {
		return new Break(length);
	}
}
