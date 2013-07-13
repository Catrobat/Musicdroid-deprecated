package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class BreakTest extends TestCase {

	public void testBreak() {
		Break br = new Break(NoteValue.WHOLE);
		
		assertEquals(NoteValue.WHOLE, br.getNoteLength());
	}
	
	public void testEquals1() {
		Break break1 = new Break(NoteValue.HALF);
		Break break2 = new Break(NoteValue.HALF);
		
		assertTrue(break1.equals(break2));
	}
	
	public void testEquals2() {
		Break break1 = new Break(NoteValue.HALF);
		Break break2 = new Break(NoteValue.WHOLE);
		
		assertFalse(break1.equals(break2));
	}
	
	public void testEquals3() {
		Break br = new Break(NoteValue.HALF);
		
		assertFalse(br.equals(null));
	}
	
	public void testEquals4() {
		Break br = new Break(NoteValue.HALF);
		
		assertFalse(br.equals(""));
	}
}
