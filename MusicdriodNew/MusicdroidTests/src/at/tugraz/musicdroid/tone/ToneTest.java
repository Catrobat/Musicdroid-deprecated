package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class ToneTest extends TestCase {

	public void halfToneUpTest() {
		Tone c1 = SymbolFactory.createC1(NoteValue.QUARTER);
		Tone c1b = c1.halfToneUp();
		
		assertEquals(c1.getLength(), c1b.getLength());
		assertEquals(c1.getMidi() + 1, c1b.getMidi());
		assertEquals(c1.getNoteName().next(), c1b.getNoteName());
	}

	public void halfToneDownTest() {
		Tone c1 = SymbolFactory.createC1(NoteValue.QUARTER);
		Tone c1c = c1.halfToneUp();
		
		assertEquals(c1.getLength(), c1c.getLength());
		assertEquals(c1.getMidi() - 1, c1c.getMidi());
		assertEquals(c1.getNoteName().previous(), c1c.getNoteName());
	}
}
