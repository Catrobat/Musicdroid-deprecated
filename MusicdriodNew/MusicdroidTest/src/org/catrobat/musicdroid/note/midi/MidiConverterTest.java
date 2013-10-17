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
package org.catrobat.musicdroid.note.midi;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;

import junit.framework.TestCase;

import org.catrobat.musicdroid.note.Instrument;
import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.Note;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.Tact;
import org.catrobat.musicdroid.note.Track;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MidiConverterTest extends TestCase {

	private static final int BEATS_PER_MINUTE = 120;

	private static final Instrument INSTRUMENT_ONE = Instrument.GUNSHOT;
	private static final Instrument INSTRUMENT_TWO = Instrument.WHISTLE;

	private static final Note NOTE_ONE_TRACK_ONE = new Note(NoteName.C4, NoteLength.QUARTER);
	private static final Note NOTE_ONE_TRACK_TWO = new Note(NoteName.B3, NoteLength.HALF);

	public void testConvertAndWriteMidi() {
		Project project = createProject();

		try {
			MidiConverter.convertAndWriteMidi(project);
		} catch (IOException e) {
			assertTrue(false);
		}

		File file = new File(project.getName() + ".midi");
		assertTrue(file.exists());
		file.delete();
	}

	public void testConvertMidi() {
		Project project = createProject();

		MidiFile midi = MidiConverter.convert(project);

		assertMidi(midi);
	}

	private Project createProject() {
		Project project = new Project("TestMidi", BEATS_PER_MINUTE);
		Track track1 = new Track(INSTRUMENT_ONE, Key.VIOLIN, new Tact());
		Track track2 = new Track(INSTRUMENT_TWO, Key.VIOLIN, new Tact());

		track1.addSymbol(NOTE_ONE_TRACK_ONE);

		track2.addSymbol(NOTE_ONE_TRACK_TWO);

		project.addTrack(track1);
		project.addTrack(track2);

		return project;
	}

	private void assertMidi(MidiFile midi) {
		assertEquals(3, midi.getTrackCount());

		ArrayList<MidiTrack> tracks = midi.getTracks();

		MidiTrack tempoTrack = tracks.get(0);
		MidiTrack noteTrack1 = tracks.get(1);
		MidiTrack noteTrack2 = tracks.get(2);

		assertTempoTrack(tempoTrack);
		assertNoteTrack1(noteTrack1);
		assertNoteTrack2(noteTrack2);
	}

	private void assertTempoTrack(MidiTrack tempoTrack) {
		assertEquals(1, tempoTrack.getEventCount());
		Iterator<MidiEvent> it = tempoTrack.getEvents().iterator();
		Tempo tempo = (Tempo) it.next();

		assertEquals(BEATS_PER_MINUTE, (int) tempo.getBpm());
	}

	private void assertNoteTrack1(MidiTrack noteTrack1) {
		assertEquals(3, noteTrack1.getEventCount());

		Iterator<MidiEvent> it = noteTrack1.getEvents().iterator();

		ProgramChange program = (ProgramChange) it.next();
		assertEquals(INSTRUMENT_ONE.getProgram(), program.getProgramNumber());

		NoteOn noteOn1 = (NoteOn) it.next();
		assertEquals(NOTE_ONE_TRACK_ONE.getNoteName().getMidi(), noteOn1.getNoteValue());
		assertEquals(0, noteOn1.getTick());

		NoteOn noteOn2 = (NoteOn) it.next();
		assertEquals(NOTE_ONE_TRACK_ONE.getNoteName().getMidi(), noteOn2.getNoteValue());
		assertEquals(NoteLength.calculateDuration(NOTE_ONE_TRACK_ONE.getNoteLength()), noteOn2.getTick());
	}

	private void assertNoteTrack2(MidiTrack noteTrack2) {
		assertEquals(3, noteTrack2.getEventCount());

		Iterator<MidiEvent> it = noteTrack2.getEvents().iterator();

		ProgramChange program = (ProgramChange) it.next();
		assertEquals(INSTRUMENT_TWO.getProgram(), program.getProgramNumber());

		NoteOn noteOn1 = (NoteOn) it.next();
		assertEquals(NOTE_ONE_TRACK_TWO.getNoteName().getMidi(), noteOn1.getNoteValue());
		assertEquals(0, noteOn1.getTick());

		NoteOn noteOn2 = (NoteOn) it.next();
		assertEquals(NOTE_ONE_TRACK_TWO.getNoteName().getMidi(), noteOn2.getNoteValue());
		assertEquals(NoteLength.calculateDuration(NOTE_ONE_TRACK_TWO.getNoteLength()), noteOn2.getTick());
	}
}
