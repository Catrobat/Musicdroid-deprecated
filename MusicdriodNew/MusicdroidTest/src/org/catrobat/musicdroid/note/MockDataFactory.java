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

import org.catrobat.musicdroid.note.symbol.AbstractSymbol;
import org.catrobat.musicdroid.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.note.symbol.NoteSymbol;

import java.util.LinkedList;
import java.util.List;

public class MockDataFactory {

	private static final String PROJECT_NAME = "TestMidi";

	private MockDataFactory() {
	}

	public static Project createProject() {
		Project project = new Project(PROJECT_NAME, 120);
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

		tick += NoteLength.getTickDurationFromNoteLengths(new NoteLength[] { NoteLength.QUARTER, NoteLength.EIGHT });

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

		tick += NoteLength.getTickDurationFromNoteLengths(new NoteLength[] { NoteLength.QUARTER, NoteLength.EIGHT });

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

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, true));

		tick += NoteLength.EIGHT.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));

		tick += NoteLength.WHOLE.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, true));

		tick += NoteLength.getTickDurationFromNoteLengths(new NoteLength[] { NoteLength.WHOLE, NoteLength.EIGHT,
				NoteLength.SIXTEENTH });

		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));

		tick += NoteLength.HALF.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));

		tick += NoteLength.WHOLE.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, true));

		tick += NoteLength.EIGHT.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.D1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));

		tick += NoteLength.WHOLE.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, true));

		tick += NoteLength.getTickDurationFromNoteLengths(new NoteLength[] { NoteLength.WHOLE, NoteLength.EIGHT,
				NoteLength.SIXTEENTH });

		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, true));

		tick += NoteLength.HALF.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.C1, false));

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

		tick += NoteLength.QUARTER.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F1, true));

		tick += NoteLength.QUARTER.getTickDuration() + NoteLength.SIXTEENTH.getTickDuration();

		track.addNoteEvent(tick, new NoteEvent(NoteName.E1, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F1, false));

		return track;
	}

	public static List<AbstractSymbol> createSymbolList1() {
		List<AbstractSymbol> symbols = new LinkedList<AbstractSymbol>();

		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER }, new NoteName[] { NoteName.C1, NoteName.C2 }));

		return symbols;
	}

	public static List<AbstractSymbol> createSymbolList2() {
		List<AbstractSymbol> symbols = new LinkedList<AbstractSymbol>();

		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER }, new NoteName[] { NoteName.C1, NoteName.D1 }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER, NoteLength.EIGHT },
				new NoteName[] { NoteName.E1 }));

		return symbols;
	}

	public static List<AbstractSymbol> createSymbolList3() {
		List<AbstractSymbol> symbols = new LinkedList<AbstractSymbol>();

		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER }, new NoteName[] { NoteName.C1, NoteName.D1 }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER, NoteLength.EIGHT },
				new NoteName[] { NoteName.E1 }));
		symbols.add(new BreakSymbol(new NoteLength[] { NoteLength.QUARTER }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER }, new NoteName[] { NoteName.C1 }));

		return symbols;
	}

	public static List<AbstractSymbol> createSymbolList4() {
		List<AbstractSymbol> symbols = new LinkedList<AbstractSymbol>();

		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER }, new NoteName[] { NoteName.C1 }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.EIGHT }, new NoteName[] { NoteName.D1 }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.WHOLE }, new NoteName[] { NoteName.C1 }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.WHOLE, NoteLength.EIGHT, NoteLength.SIXTEENTH },
				new NoteName[] { NoteName.E1 }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.HALF }, new NoteName[] { NoteName.C1 }));
		symbols.add(new BreakSymbol(new NoteLength[] { NoteLength.WHOLE }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.QUARTER }, new NoteName[] { NoteName.C1 }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.EIGHT }, new NoteName[] { NoteName.D1 }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.WHOLE }, new NoteName[] { NoteName.C1 }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.WHOLE, NoteLength.EIGHT, NoteLength.SIXTEENTH },
				new NoteName[] { NoteName.E1 }));
		symbols.add(new NoteSymbol(new NoteLength[] { NoteLength.HALF }, new NoteName[] { NoteName.C1 }));

		return symbols;
	}
}
