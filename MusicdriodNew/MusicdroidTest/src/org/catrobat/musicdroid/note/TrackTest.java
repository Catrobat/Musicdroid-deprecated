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

	public void testTrack1() {
		Tact tact = new Tact();
		Track track = new Track();

		assertEquals(tact.getBeatsPerTact(), track.getTact().getBeatsPerTact());
		assertEquals(tact.getNoteLength(), track.getTact().getNoteLength());
		assertEquals(Key.VIOLIN, track.getKey());
	}

	public void testTrack2() {
		Instrument instrument = Instrument.ACCORDION;
		Tact tact = new Tact(3, NoteLength.QUARTER);
		Track track = new Track(instrument, Key.BASS, tact);

		assertEquals(instrument, track.getInstrument());
		assertEquals(tact, track.getTact());
		assertEquals(Key.BASS, track.getKey());
	}

	public void testAddSymbol() {
		Track track = new Track();

		track.addSymbol(new Note(NoteLength.QUARTER, NoteName.C1));

		assertEquals(1, track.size());
	}

	public void testRemoveSymbol() {
		Track track = new Track();

		Symbol symbol = new Note(NoteLength.QUARTER, NoteName.C1);
		track.addSymbol(symbol);
		track.removeSymbol(symbol);

		assertEquals(0, track.size());
	}

	public void testGetSymbol() {
		Track track = new Track();

		Symbol symbol = new Break(NoteLength.QUARTER);
		track.addSymbol(symbol);

		assertEquals(symbol, track.getSymbol(0));
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
		Track track1 = new Track(Instrument.ACCORDION, Key.BASS, new Tact());
		Track track2 = new Track();

		assertFalse(track1.equals(track2));
	}

	public void testEquals5() {
		Track track1 = new Track(Instrument.ACOUSTIC_GRAND_PIANO, Key.VIOLIN, new Tact(12, NoteLength.SIXTEENTH));
		Track track2 = new Track();

		assertFalse(track1.equals(track2));
	}

	public void testEquals6() {
		Track track = new Track();

		assertFalse(track.equals(null));
	}

	public void testEquals7() {
		Track track = new Track();

		assertFalse(track.equals(""));
	}

	public void testToString() {
		Instrument instrument = Instrument.ACOUSTIC_GRAND_PIANO;
		Key key = Key.BASS;
		Track track = new Track(instrument, key, new Tact());

		assertEquals("[Track] instrument= " + instrument + " key=" + key + " symbolCount=" + track.size(),
				track.toString());
	}
}
