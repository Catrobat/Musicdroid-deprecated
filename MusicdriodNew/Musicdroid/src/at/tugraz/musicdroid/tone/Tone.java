package at.tugraz.musicdroid.tone;

public class Tone extends Symbol {

	private NoteName name;
	
	Tone(NoteName name, NoteValue length) {
		super(length);
		this.name = name;
	}
	
	public NoteName getNoteName() {
		return name;
	}

	public Tone halfToneUp() {
		Tone newTone = new Tone(name.next(), noteLength);
		return newTone;
	}

	public Tone halfToneDown() {
		Tone newTone = new Tone(name.previous(), noteLength);
		return newTone;
	}
}
