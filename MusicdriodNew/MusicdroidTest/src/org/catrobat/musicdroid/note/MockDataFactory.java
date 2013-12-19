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
package org.catrobat.musicdroid.note;

import org.catrobat.musicdroid.note.symbol.BoundNoteSymbol;
import org.catrobat.musicdroid.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.note.symbol.Symbol;

import java.util.LinkedList;
import java.util.List;

public class MockDataFactory {

	private static final String PROJECT_NAME = "TestMidi";

	private MockDataFactory() {
	}

	public static Project createProject() {
		Project project = new Project(PROJECT_NAME, Project.DEFAULT_BEATS_PER_MINUTE);
		Track track1 = createTrack5(Instrument.GUNSHOT);
		Track track2 = createTrack5(Instrument.WHISTLE);

		project.addTrack(track1);
		project.addTrack(track2);

		return project;
	}

	public static Track createTrack1() {
		Track track = new Track();

		long tick = 0;

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C2, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C2, false));

		return track;
	}

	public static Track createTrack2() {
		Track track = new Track();

		long tick = 0;

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, true));

		tick += NoteLength.QUARTER.getTickDuration();
		tick += NoteLength.EIGHT.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, false));

		return track;
	}

	public static Track createTrack3() {
		Track track = new Track();

		long tick = 0;

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, true));

		tick += NoteLength.QUARTER.getTickDuration();
		tick += NoteLength.EIGHT.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, false));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));

		return track;
	}

	public static Track createTrack4() {
		Track track = new Track();

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

	public static Track createTrack5(Instrument instrument) {
		Track track = new Track(instrument);

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

	public static List<Symbol> createSymbolList1() {
		List<Symbol> symbols = new LinkedList<Symbol>();

		NoteSymbol noteSymbol = new NoteSymbol();
		noteSymbol.addNote(NoteName.C1, NoteLength.QUARTER);
		noteSymbol.addNote(NoteName.C2, NoteLength.QUARTER);
		symbols.add(noteSymbol);

		return symbols;
	}

	public static List<Symbol> createSymbolList2() {
		List<Symbol> symbols = new LinkedList<Symbol>();

		NoteSymbol noteSymbol1 = new NoteSymbol();
		noteSymbol1.addNote(NoteName.C1, NoteLength.QUARTER);
		noteSymbol1.addNote(NoteName.D1, NoteLength.QUARTER);
		symbols.add(noteSymbol1);

		NoteSymbol noteSymbol2 = new NoteSymbol();
		noteSymbol2.addNote(NoteName.E1, NoteLength.QUARTER_DOT);
		symbols.add(noteSymbol2);

		return symbols;
	}

	public static List<Symbol> createSymbolList3() {
		List<Symbol> symbols = new LinkedList<Symbol>();

		NoteSymbol noteSymbol1 = new NoteSymbol();
		noteSymbol1.addNote(NoteName.C1, NoteLength.QUARTER);
		noteSymbol1.addNote(NoteName.D1, NoteLength.QUARTER);
		symbols.add(noteSymbol1);

		NoteSymbol noteSymbol2 = new NoteSymbol();
		noteSymbol2.addNote(NoteName.E1, NoteLength.QUARTER_DOT);
		symbols.add(noteSymbol2);

		BreakSymbol breakSymbol1 = new BreakSymbol(NoteLength.QUARTER);
		symbols.add(breakSymbol1);

		NoteSymbol noteSymbol3 = new NoteSymbol();
		noteSymbol3.addNote(NoteName.C1, NoteLength.QUARTER);
		symbols.add(noteSymbol3);

		return symbols;
	}

	public static List<Symbol> createSymbolList4() {
		List<Symbol> symbols = new LinkedList<Symbol>();

		NoteSymbol noteSymbol1 = new NoteSymbol();
		noteSymbol1.addNote(NoteName.C1, NoteLength.QUARTER);
		noteSymbol1.addNote(NoteName.D1, NoteLength.HALF);

		NoteSymbol noteSymbol2 = new NoteSymbol();
		noteSymbol2.addNote(NoteName.E1, NoteLength.QUARTER);
		noteSymbol2.addNote(NoteName.F1, NoteLength.QUARTER);

		BoundNoteSymbol boundNoteSymbol1 = new BoundNoteSymbol();
		boundNoteSymbol1.addNoteSymbol(noteSymbol1);
		boundNoteSymbol1.addNoteSymbol(noteSymbol2);
		symbols.add(boundNoteSymbol1);

		BreakSymbol breakSymbol1 = new BreakSymbol(NoteLength.QUARTER_DOT);
		symbols.add(breakSymbol1);

		NoteSymbol noteSymbol3 = new NoteSymbol();
		noteSymbol3.addNote(NoteName.C2, NoteLength.QUARTER_DOT);
		noteSymbol3.addNote(NoteName.D2, NoteLength.QUARTER_DOT);
		symbols.add(noteSymbol3);

		NoteSymbol noteSymbol4 = new NoteSymbol();
		noteSymbol4.addNote(NoteName.B3, NoteLength.QUARTER);
		symbols.add(noteSymbol4);

		return symbols;
	}
}
