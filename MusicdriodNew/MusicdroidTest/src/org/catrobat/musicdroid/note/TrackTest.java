/*******************************************************************************
 * Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid/licenseadditionalterm
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.catrobat.musicdroid.note;

import junit.framework.TestCase;

public class TrackTest extends TestCase {
	private static final String TAG = TrackTest.class.getSimpleName();

	public void testTrack1() {
		Tact tact = new Tact();
		Track track = new Track();

		assertEquals(tact.getBeatsPerTact(), track.getTact().getBeatsPerTact());
		assertEquals(tact.getNoteLength(), track.getTact().getNoteLength());
		assertEquals(Key.VIOLIN, track.getKey());
	}

	public void testTrack2() {
		Tact tact = new Tact(3, NoteLength.QUARTER);
		Track track = new Track(Key.BASS, tact, 60);

		assertEquals(tact, track.getTact());
		assertEquals(Key.BASS, track.getKey());
	}

	public void testAddSymbol() {
		Track track = new Track();

		track.addSymbol(new Note(NoteName.C1, NoteLength.QUARTER));

		assertEquals(1, track.size());
	}

	public void testRemoveSymbol() {
		Track track = new Track();

		Symbol symbol = new Note(NoteName.C1, NoteLength.QUARTER);
		track.addSymbol(symbol);
		track.removeSymbol(symbol);

		assertEquals(0, track.size());
	}

	public void testEquals1() {
		Track track1 = new Track();
		track1.addSymbol(new Break(NoteLength.HALF));

		Track track2 = new Track();
		track2.addSymbol(new Break(NoteLength.HALF));

		assertTrue(track1.equals(track2));
	}

	public void testEquals2() {
		Track track1 = new Track();
		track1.addSymbol(new Break(NoteLength.HALF));

		Track track2 = new Track();
		track2.addSymbol(new Break(NoteLength.WHOLE));

		assertFalse(track1.equals(track2));
	}

	public void testEquals3() {
		Track track1 = new Track();
		track1.addSymbol(new Break(NoteLength.HALF));

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

	public void testToString() {
		Key key = Key.BASS;
		Track track = new Track(key, new Tact(), 60);

		assertEquals("[Track] key=" + key + " symbolCount=" + track.size() + " beatsPerMinute=" + track.getBeatsPerMinute(),
				track.toString());
	}
}
