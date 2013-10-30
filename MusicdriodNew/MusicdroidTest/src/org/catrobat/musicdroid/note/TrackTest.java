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
		Track track = new Track();

		assertEquals(Instrument.ACOUSTIC_GRAND_PIANO, track.getInstrument());
	}

	public void testTrack2() {
		Instrument instrument = Instrument.ACCORDION;
		Track track = new Track(instrument);

		assertEquals(instrument, track.getInstrument());
	}

	public void testAddNoteEventl() {
		Track track = new Track();

		NoteEvent noteEvent = new NoteEvent(NoteName.C1, 0, true);
		track.addNoteEvent(noteEvent);

		assertEquals(1, track.size());
	}

	public void testRemoveNoteEvent() {
		Track track = new Track();

		NoteEvent noteEvent = new NoteEvent(NoteName.C1, 0, true);
		track.addNoteEvent(noteEvent);
		track.removeNoteEvent(noteEvent);

		assertEquals(0, track.size());
	}

	public void testGetSymbol() {
		Track track = new Track();

		NoteEvent noteEvent = new NoteEvent(NoteName.C1, 0, true);
		track.addNoteEvent(noteEvent);

		assertEquals(noteEvent, track.getNoteEvent(0));
	}

	public void testEquals1() {
		Track track1 = new Track();
		track1.addNoteEvent(new NoteEvent(NoteName.C1, 0, true));

		Track track2 = new Track();
		track2.addNoteEvent(new NoteEvent(NoteName.C1, 0, true));

		assertTrue(track1.equals(track2));
	}

	public void testEquals2() {
		Track track1 = new Track();
		track1.addNoteEvent(new NoteEvent(NoteName.C1, 0, true));

		Track track2 = new Track();
		track2.addNoteEvent(new NoteEvent(NoteName.C2, 0, true));

		assertFalse(track1.equals(track2));
	}

	public void testEquals3() {
		Track track1 = new Track();
		track1.addNoteEvent(new NoteEvent(NoteName.C1, 0, true));

		Track track2 = new Track();

		assertFalse(track1.equals(track2));
	}

	public void testEquals4() {
		Track track1 = new Track(Instrument.ACCORDION);
		Track track2 = new Track();

		assertFalse(track1.equals(track2));
	}

	public void testEquals5() {
		Track track = new Track();

		assertFalse(track.equals(null));
	}

	public void testEquals6() {
		Track track = new Track();

		assertFalse(track.equals(""));
	}

	public void testToString() {
		Instrument instrument = Instrument.ACOUSTIC_GRAND_PIANO;
		Track track = new Track(instrument);

		assertEquals("[Track] instrument= " + instrument + " eventCount=" + track.size(), track.toString());
	}
}
