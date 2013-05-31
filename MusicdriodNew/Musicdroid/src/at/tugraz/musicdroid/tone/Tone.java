package at.tugraz.musicdroid.tone;

public class Tone extends Symbol {

	private NoteName name;
	private int midi;
	
	Tone(NoteValue length, NoteName name, int midi) {
		super(length);
		this.name = name;
		this.midi = midi;
	}
	
	public int getMidi() {
		return midi;
	}
	
	public NoteName getNoteName() {
		return name;
	}

	public Tone halfToneUp() {
		Tone newTone = new Tone(length, name.next(), midi + 1);
		return newTone;
	}

	public Tone halfToneDown() {
		Tone newTone = new Tone(length, name.previous(), midi - 1);
		return newTone;
	}
}
