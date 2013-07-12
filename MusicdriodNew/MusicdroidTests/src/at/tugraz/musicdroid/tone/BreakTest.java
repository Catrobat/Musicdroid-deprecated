package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class BreakTest extends TestCase {

	public void testBreak() {
		Break b = new Break(NoteValue.WHOLE);
		
		assertEquals(NoteValue.WHOLE, b.getNoteLength());
	}
}
