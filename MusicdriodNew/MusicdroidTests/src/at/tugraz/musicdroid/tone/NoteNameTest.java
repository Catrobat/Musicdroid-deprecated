package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class NoteNameTest extends TestCase {

	public void testMidi() {
		assertEquals(36, NoteName.C1.getMidi());
		assertEquals(48, NoteName.C2.getMidi());
		assertEquals(60, NoteName.C3.getMidi());
		assertEquals(72, NoteName.C4.getMidi());
		assertEquals(84, NoteName.C5.getMidi());
	}
	
	public void testNext() {
		NoteName a5s = NoteName.A5S;
		NoteName b5 = NoteName.B5;
		
		assertEquals(b5, a5s.next());
		assertEquals(b5, b5.next());
	}
	
	public void testPrevious() {
		NoteName c1 = NoteName.C1;
		NoteName c1s = NoteName.C1S;
		
		assertEquals(c1, c1.previous());
		assertEquals(c1, c1s.previous());
	}
}
