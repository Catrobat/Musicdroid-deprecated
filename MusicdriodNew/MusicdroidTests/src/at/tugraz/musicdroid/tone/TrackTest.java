package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class TrackTest extends TestCase {

	public void testTrack1() {
		Track track = new Track();
		
		assertEquals(Time.FOUR_FOUR, track.getTime());
	}

	public void testTrack2() {
		Track track = new Track(Time.THREE_FOUR);
		
		assertEquals(Time.THREE_FOUR, track.getTime());
	}

	public void testAddSymbol() {
		Track track = new Track();
		
		track.addSymbol(SymbolFactory.createC1(NoteValue.QUARTER));
		
		assertEquals(1, track.size());
	}

	public void testRemoveSymbol() {
		Track track = new Track();
		
		Symbol symbol = SymbolFactory.createC1(NoteValue.QUARTER);
		track.addSymbol(symbol);
		track.removeSymbol(symbol);
		
		assertEquals(0, track.size());
	}

}
