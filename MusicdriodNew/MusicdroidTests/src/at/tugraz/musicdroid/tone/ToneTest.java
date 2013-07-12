package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class ToneTest extends TestCase {

	public void testTone() {
		NoteName name = NoteName.C1;
		NoteValue noteLength = NoteValue.EIGHT;
		Tone tone = new Tone(name, noteLength);
		
		assertEquals(name, tone.getNoteName());
		assertEquals(noteLength, tone.getNoteLength());
	}
	
	public void testHalfToneUp() {
		Tone c1 = new Tone(NoteName.C1, NoteValue.QUARTER);
		Tone c1s = c1.halfToneUp();
		
		assertEquals(c1.getNoteLength(), c1s.getNoteLength());
		assertEquals(c1.getNoteName().next(), c1s.getNoteName());
	}

	public void testHalfToneDown() {
		Tone c1s = new Tone(NoteName.C1S, NoteValue.QUARTER);
		Tone c1 = c1s.halfToneDown();
		
		assertEquals(c1.getNoteLength(), c1s.getNoteLength());
		assertEquals(c1s.getNoteName().previous(), c1.getNoteName());
	}
}
