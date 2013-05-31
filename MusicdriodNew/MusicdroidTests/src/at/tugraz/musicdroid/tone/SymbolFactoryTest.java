package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class SymbolFactoryTest extends TestCase {

	public void createC1Test() {
		Tone c1 = SymbolFactory.createC1(NoteValue.QUARTER);
		
		assertEquals(NoteValue.QUARTER, c1.getLength());
		assertEquals(24, c1.getMidi());
		assertEquals(NoteName.C1, c1.getNoteName());
	}
	
	public void createBreakTest() {
		Break b = SymbolFactory.createBreak(NoteValue.QUARTER);
		
		assertEquals(NoteValue.QUARTER, b.getLength());
	}
}
