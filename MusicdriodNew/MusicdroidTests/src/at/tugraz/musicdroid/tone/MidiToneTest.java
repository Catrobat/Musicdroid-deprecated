package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class MidiToneTest extends TestCase {

	public void createC1Test() {
		MidiTone c1 = MidiToneFactory.createC1();
		
		assertEquals(24, c1.getMidi());
	}
}
