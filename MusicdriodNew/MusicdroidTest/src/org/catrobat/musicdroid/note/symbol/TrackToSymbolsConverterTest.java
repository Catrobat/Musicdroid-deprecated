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
package org.catrobat.musicdroid.note.symbol;

import junit.framework.TestCase;

import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Track;

import java.util.LinkedList;
import java.util.List;

public class TrackToSymbolsConverterTest extends TestCase {

	// TODO fw mehr probieren

	public void testConvertTrack1() {
		TrackToSymbolsConverter trackConverter = new TrackToSymbolsConverter();
		List<AbstractSymbol> expectedSymbols = new LinkedList<AbstractSymbol>();
		expectedSymbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER }, new NoteName[] { NoteName.C1,
				NoteName.C2 }));

		long tick = 0;
		Track track = new Track();
		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C2, true));
		tick += NoteLength.QUARTER.getTickDuration();
		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C2, false));
		List<AbstractSymbol> actualSymbols = trackConverter.convertTrack(track);

		assertEquals(expectedSymbols.size(), actualSymbols.size());
		assertEquals(expectedSymbols, actualSymbols);
	}

	public void testConvertTrack2() {
		TrackToSymbolsConverter trackConverter = new TrackToSymbolsConverter();
		List<AbstractSymbol> expectedSymbols = new LinkedList<AbstractSymbol>();
		expectedSymbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER }, new NoteName[] { NoteName.C1,
				NoteName.D1 }));
		expectedSymbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER, NoteLength.EIGHT },
				new NoteName[] { NoteName.E1 }));

		long tick = 0;
		Track track = new Track();
		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, true));
		tick += NoteLength.QUARTER.getTickDuration();
		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, true));
		tick += NoteLength.getTickDurationFromNoteLengths(new NoteLength[] { NoteLength.QUARTER, NoteLength.EIGHT });
		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, false));
		List<AbstractSymbol> actualSymbols = trackConverter.convertTrack(track);

		assertEquals(expectedSymbols.size(), actualSymbols.size());
		assertEquals(expectedSymbols, actualSymbols);
	}
}
