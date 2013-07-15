package at.tugraz.musicdroid.tone;

import java.io.Serializable;

public class Tone extends Symbol implements Serializable {
	
	private static final long serialVersionUID = 2238272682118731619L;
	
	private NoteName name;
	
	Tone(NoteName name, NoteLength length) {
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
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Tone)) {
			return false;
		}
		
		Tone tone = (Tone) obj;
		
		if (super.equals(obj)) {
			return name.equals(tone.getNoteName());
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "[Tone] noteLength=" + noteLength + " name=" + name;
	}
}
