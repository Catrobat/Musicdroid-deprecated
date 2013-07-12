package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class TrackTest extends TestCase {

	public void testTrack1() {
		Time time = new Time();
		Track track = new Track();
		
		assertEquals(time.getBeatsPerTact(), track.getTime().getBeatsPerTact());
		assertEquals(time.getNoteLength(), track.getTime().getNoteLength());
		assertEquals(Key.VIOLIN, track.getKey());
	}
	
	public void testTrack2() {
		Time time = new Time(3, NoteValue.QUARTER);
		Track track = new Track(Key.BASS, time);
		
		assertEquals(time, track.getTime());
		assertEquals(Key.BASS, track.getKey());
	}

	public void testAddSymbol() {
		Track track = new Track();
		
		track.addSymbol(new Tone(NoteName.C1, NoteValue.QUARTER));
		
		assertEquals(1, track.size());
	}

	public void testRemoveSymbol() {
		Track track = new Track();
		
		Symbol symbol = new Tone(NoteName.C1, NoteValue.QUARTER);
		track.addSymbol(symbol);
		track.removeSymbol(symbol);
		
		assertEquals(0, track.size());
	}

}
