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
	
	public void testEquals1() {
		Tone tone1 = new Tone(NoteName.C1, NoteValue.QUARTER);
		Tone tone2 = new Tone(NoteName.C1, NoteValue.QUARTER);
		
		assertTrue(tone1.equals(tone2));
	}
	
	public void testEquals2() {
		Tone tone1 = new Tone(NoteName.C1, NoteValue.QUARTER);
		Tone tone2 = new Tone(NoteName.D1, NoteValue.QUARTER);
		
		assertFalse(tone1.equals(tone2));
	}
	
	public void testEquals3() {
		Tone tone1 = new Tone(NoteName.C1, NoteValue.QUARTER);
		Tone tone2 = new Tone(NoteName.C1, NoteValue.HALF);
		
		assertFalse(tone1.equals(tone2));
	}
	
	public void testEquals4() {
		Tone tone = new Tone(NoteName.C1, NoteValue.QUARTER);
		
		assertFalse(tone.equals(null));
	}
	
	public void testEquals5() {
		Tone tone = new Tone(NoteName.C1, NoteValue.QUARTER);
		
		assertFalse(tone.equals(""));
	}
}
