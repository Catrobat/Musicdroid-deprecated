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
	
	public void testEquals1() {
		Track track1 = new Track();
		track1.addSymbol(new Break(NoteValue.HALF));
		
		Track track2 = new Track();
		track2.addSymbol(new Break(NoteValue.HALF));
		
		assertTrue(track1.equals(track2));
	}
	
	public void testEquals2() {
		Track track1 = new Track();
		track1.addSymbol(new Break(NoteValue.HALF));
		
		Track track2 = new Track();
		track2.addSymbol(new Break(NoteValue.WHOLE));
		
		assertFalse(track1.equals(track2));
	}
	
	public void testEquals3() {
		Track track1 = new Track();
		track1.addSymbol(new Break(NoteValue.HALF));
		
		Track track2 = new Track();
		
		assertFalse(track1.equals(track2));
	}
	
	public void testEquals4() {
		Track track = new Track();
		
		assertFalse(track.equals(null));
	}
	
	public void testEquals5() {
		Track track = new Track();
		
		assertFalse(track.equals(""));
	}
}
