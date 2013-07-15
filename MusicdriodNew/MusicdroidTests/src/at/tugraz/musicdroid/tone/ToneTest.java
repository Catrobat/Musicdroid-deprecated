package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class ToneTest extends TestCase {

	public void testTone() {
		NoteName name = NoteName.C1;
		NoteLength noteLength = NoteLength.EIGHT;
		Tone tone = new Tone(name, noteLength);
		
		assertEquals(name, tone.getNoteName());
		assertEquals(noteLength, tone.getNoteLength());
	}
	
	public void testHalfToneUp() {
		Tone c1 = new Tone(NoteName.C1, NoteLength.QUARTER);
		Tone c1s = c1.halfToneUp();
		
		assertEquals(c1.getNoteLength(), c1s.getNoteLength());
		assertEquals(c1.getNoteName().next(), c1s.getNoteName());
	}

	public void testHalfToneDown() {
		Tone c1s = new Tone(NoteName.C1S, NoteLength.QUARTER);
		Tone c1 = c1s.halfToneDown();
		
		assertEquals(c1.getNoteLength(), c1s.getNoteLength());
		assertEquals(c1s.getNoteName().previous(), c1.getNoteName());
	}
	
	public void testEquals1() {
		Tone tone1 = new Tone(NoteName.C1, NoteLength.QUARTER);
		Tone tone2 = new Tone(NoteName.C1, NoteLength.QUARTER);
		
		assertTrue(tone1.equals(tone2));
	}
	
	public void testEquals2() {
		Tone tone1 = new Tone(NoteName.C1, NoteLength.QUARTER);
		Tone tone2 = new Tone(NoteName.D1, NoteLength.QUARTER);
		
		assertFalse(tone1.equals(tone2));
	}
	
	public void testEquals3() {
		Tone tone1 = new Tone(NoteName.C1, NoteLength.QUARTER);
		Tone tone2 = new Tone(NoteName.C1, NoteLength.HALF);
		
		assertFalse(tone1.equals(tone2));
	}
	
	public void testEquals4() {
		Tone tone = new Tone(NoteName.C1, NoteLength.QUARTER);
		
		assertFalse(tone.equals(null));
	}
	
	public void testEquals5() {
		Tone tone = new Tone(NoteName.C1, NoteLength.QUARTER);
		
		assertFalse(tone.equals(""));
	}
	
	public void testToString() {
		NoteLength noteLength = NoteLength.WHOLE;
		NoteName name = NoteName.A1;
		Tone tone = new Tone(name, noteLength);
		
		assertEquals("[Tone] noteLength=" + noteLength + " name=" + name, tone.toString());
	}
}
