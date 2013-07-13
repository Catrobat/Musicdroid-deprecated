package at.tugraz.musicdroid.tone;

import java.io.Serializable;

public class Break extends Symbol implements Serializable {
	
	private static final long serialVersionUID = 4617673494732123149L;

	Break(NoteValue length) {
		super(length);
	}
}
