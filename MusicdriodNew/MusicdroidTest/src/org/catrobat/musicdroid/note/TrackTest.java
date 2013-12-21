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

import org.catrobat.musicdroid.note.testutil.NoteEventTestDataFactory;
import org.catrobat.musicdroid.note.testutil.TrackTestDataFactory;

public class TrackTest extends TestCase {

	public void testGetInstrument() {
		Track track = TrackTestDataFactory.createTrack();

		assertEquals(Track.DEFAULT_INSTRUMENT, track.getInstrument());
	}

	public void testAddNoteEvent1() {
		Track track = TrackTestDataFactory.createTrack();
		track.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());

		assertEquals(1, track.size());
	}

	public void testAddNoteEvent2() {
		Track track = TrackTestDataFactory.createTrack();
		track.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());
		track.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());

		assertEquals(2, track.size());
	}

	public void testGetNoteEventsForTick() {
		Track track = TrackTestDataFactory.createTrack();
		long tick = 0;
		NoteEvent noteEvent = NoteEventTestDataFactory.createNoteEvent();
		track.addNoteEvent(tick, noteEvent);

		assertEquals(noteEvent, track.getNoteEventsForTick(tick).get(0));
	}

	public void testEquals1() {
		long tick = 0;
		Track track1 = TrackTestDataFactory.createTrack();
		track1.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent());
		Track track2 = TrackTestDataFactory.createTrack();
		track2.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent());

		assertTrue(track1.equals(track2));
	}

	public void testEquals2() {
		long tick = 0;
		Track track1 = TrackTestDataFactory.createTrack();
		track1.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(NoteName.C1));
		Track track2 = TrackTestDataFactory.createTrack();
		track2.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(NoteName.C2));

		assertFalse(track1.equals(track2));
	}

	public void testEquals3() {
		Track track1 = TrackTestDataFactory.createTrack();
		track1.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());
		Track track2 = TrackTestDataFactory.createTrack();

		assertFalse(track1.equals(track2));
	}

	public void testEquals4() {
		Track track1 = TrackTestDataFactory.createTrack(Instrument.ACCORDION);
		Track track2 = TrackTestDataFactory.createTrack();

		assertFalse(track1.equals(track2));
	}

	public void testEquals5() {
		Track track = TrackTestDataFactory.createTrack();

		assertFalse(track.equals(null));
	}

	public void testEquals6() {
		Track track = TrackTestDataFactory.createTrack();

		assertFalse(track.equals(""));
	}

	public void testToString() {
		Track track = TrackTestDataFactory.createTrack();

		assertEquals("[Track] instrument= " + Track.DEFAULT_INSTRUMENT + " size: " + 0, track.toString());
	}
}
