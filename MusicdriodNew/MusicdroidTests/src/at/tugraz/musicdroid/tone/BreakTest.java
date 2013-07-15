package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class BreakTest extends TestCase {

	public void testBreak() {
		Break br = new Break(NoteLength.WHOLE);
		
		assertEquals(NoteLength.WHOLE, br.getNoteLength());
	}
	
	public void testEquals1() {
		Break break1 = new Break(NoteLength.HALF);
		Break break2 = new Break(NoteLength.HALF);
		
		assertTrue(break1.equals(break2));
	}
	
	public void testEquals2() {
		Break break1 = new Break(NoteLength.HALF);
		Break break2 = new Break(NoteLength.WHOLE);
		
		assertFalse(break1.equals(break2));
	}
	
	public void testEquals3() {
		Break br = new Break(NoteLength.HALF);
		
		assertFalse(br.equals(null));
	}
	
	public void testEquals4() {
		Break br = new Break(NoteLength.HALF);
		
		assertFalse(br.equals(""));
	}
	
	public void testToString() {
		NoteLength noteLength = NoteLength.WHOLE;
		Break br = new Break(noteLength);
		
		assertEquals("[Break] noteLength=" + noteLength, br.toString());
	}
}
