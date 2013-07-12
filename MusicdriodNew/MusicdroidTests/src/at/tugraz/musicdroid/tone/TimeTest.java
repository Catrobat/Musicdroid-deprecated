package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class TimeTest extends TestCase {

	public void testTime1() {
		Time time = new Time();
		
		assertEquals(4, time.getBeatsPerTact());
		assertEquals(NoteValue.QUARTER, time.getNoteLength());
	}
	
	public void testTime2() {
		Time time = new Time(19, NoteValue.SIXTEENTH);
		
		assertEquals(19, time.getBeatsPerTact());
		assertEquals(NoteValue.SIXTEENTH, time.getNoteLength());
	}
}
