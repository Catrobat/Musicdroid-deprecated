/**
 *  Catroid: An on-device visual programming system for Android devices
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
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.test.note.testutil;

import org.catrobat.musicdroid.note.Instrument;
import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Track;

public final class TrackTestDataFactory {
	private TrackTestDataFactory() {
	}

	public static Track createTrack() {
		return new Track(Key.VIOLIN);
	}

	public static Track createTrack(Instrument instrument) {
		return new Track(Key.VIOLIN, instrument);
	}

	public static Track createSimpleTrack() {
		Track track = new Track(Key.VIOLIN);

		long tick = 0;

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, true));

		tick += NoteLength.QUARTER_DOT.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.F1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C2, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C2, false));

		return track;
	}

	public static Track createTrackWithBreak() {
		Track track = new Track(Key.VIOLIN);

		long tick = 0;

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, true));

		tick += NoteLength.QUARTER_DOT.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, false));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));

		return track;
	}

	public static Track createSemiComplexTrack(Instrument instrument) {
		Track track = new Track(Key.VIOLIN, instrument);

		long tick = 0;

		track.addNoteEvent(tick, new NoteEvent(NoteName.C2, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C2, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, false));

		tick += NoteLength.QUARTER_DOT.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F1, true));

		tick += NoteLength.QUARTER.getTickDuration() + NoteLength.SIXTEENTH.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F1, false));

		return track;
	}

	public static Track createComplexTrack() {
		Track track = new Track(Key.VIOLIN);

		long tick = 0;

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F1, false));

		tick += NoteLength.QUARTER_DOT.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C2, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D2, true));

		tick += NoteLength.QUARTER_DOT.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C2, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D2, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.B3, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.B3, false));

		return track;
	}
}
