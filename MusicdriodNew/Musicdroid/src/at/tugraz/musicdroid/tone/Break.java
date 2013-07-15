package at.tugraz.musicdroid.tone;

import java.io.Serializable;

public class Break extends Symbol implements Serializable {
	
	private static final long serialVersionUID = 4617673494732123149L;

	Break(NoteLength length) {
		super(length);
	}
	
	@Override
	public String toString() {
		return "[Break] noteLength=" + noteLength;
	}
}
