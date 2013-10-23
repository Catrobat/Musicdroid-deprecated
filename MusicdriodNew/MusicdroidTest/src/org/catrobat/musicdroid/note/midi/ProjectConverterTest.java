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

import org.catrobat.musicdroid.note.Break;
import org.catrobat.musicdroid.note.Chord;
import org.catrobat.musicdroid.note.Instrument;
import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.Note;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.Symbol;
import org.catrobat.musicdroid.note.Tact;
import org.catrobat.musicdroid.note.Track;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ProjectConverterTest extends TestCase {

	private static final String PROJECT_NAME = "TestMidi";

	public void testConvertProjectAndWriteMidi() throws MidiException {
		ProjectConverter converter = new ProjectConverter();
		Project project = createProject();

		try {
			converter.convertProjectAndWriteMidi(project);
		} catch (IOException e) {
			assertTrue(false);
		}

		File file = new File(project.getName() + ".midi");
		assertTrue(file.exists());
		file.delete();
	}

	public void testConvertProject() throws MidiException {
		ProjectConverter converter = new ProjectConverter();
		Project project = createProject();

		MidiFile midi = converter.convertProject(project);

		assertMidi(project, midi);
	}

	private Project createProject() {
		Project project = new Project(PROJECT_NAME, 120);
		Track track1 = new Track(Instrument.GUNSHOT, Key.VIOLIN, new Tact());
		Track track2 = new Track(Instrument.WHISTLE, Key.VIOLIN, new Tact());

		track1.addSymbol(new Break(NoteLength.QUARTER));
		track1.addSymbol(new Chord(NoteLength.QUARTER, new NoteName[] { NoteName.C1, NoteName.C2 }));
		track1.addSymbol(new Break(NoteLength.QUARTER));
		track1.addSymbol(new Chord(NoteLength.QUARTER, new NoteName[] { NoteName.C1, NoteName.C2 }));

		track2.addSymbol(new Note(NoteLength.WHOLE, NoteName.B1));
		track2.addSymbol(new Note(NoteLength.HALF, NoteName.B2));
		track2.addSymbol(new Note(NoteLength.HALF, NoteName.B2));
		track2.addSymbol(new Note(NoteLength.QUARTER, NoteName.B3));
		track2.addSymbol(new Note(NoteLength.QUARTER, NoteName.B3));
		track2.addSymbol(new Note(NoteLength.QUARTER, NoteName.B3));
		track2.addSymbol(new Note(NoteLength.QUARTER, NoteName.B3));
		track2.addSymbol(new Note(NoteLength.EIGHT, NoteName.B4));
		track2.addSymbol(new Note(NoteLength.EIGHT, NoteName.B4));
		track2.addSymbol(new Note(NoteLength.EIGHT, NoteName.B4));
		track2.addSymbol(new Note(NoteLength.EIGHT, NoteName.B4));
		track2.addSymbol(new Note(NoteLength.EIGHT, NoteName.B4));
		track2.addSymbol(new Note(NoteLength.EIGHT, NoteName.B4));
		track2.addSymbol(new Note(NoteLength.EIGHT, NoteName.B4));
		track2.addSymbol(new Note(NoteLength.EIGHT, NoteName.B4));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));
		track2.addSymbol(new Note(NoteLength.SIXTEENTH, NoteName.B5));

		project.addTrack(track1);
		project.addTrack(track2);

		return project;
	}

	private void assertMidi(Project project, MidiFile midi) {
		int tempoTrackCount = 1;
		int noteTrackCount = project.size();

		int trackCount = tempoTrackCount + noteTrackCount;
		assertEquals(trackCount, midi.getTrackCount());

		ArrayList<MidiTrack> tracks = midi.getTracks();

		MidiTrack tempoTrack = tracks.get(0);
		assertTempoTrack(project, tempoTrack);

		for (int i = 1; i < tracks.size(); i++) {
			MidiTrack noteTrack = tracks.get(i);
			assertNoteTrack(project.getTrack(i - 1), noteTrack);
		}
	}

	private void assertTempoTrack(Project project, MidiTrack tempoTrack) {
		assertEquals(1, tempoTrack.getEventCount());

		Iterator<MidiEvent> it = tempoTrack.getEvents().iterator();
		Tempo tempo = (Tempo) it.next();

		assertEquals(project.getBeatsPerMinute(), (int) tempo.getBpm());
	}

	private void assertNoteTrack(Track track, MidiTrack noteTrack1) {
		int noteOnEventCount = getNoteOnEventCount(track);

		int noteOffEventCount = noteOnEventCount;
		int programChangeEventCount = 1;

		int eventCount = noteOnEventCount + noteOffEventCount + programChangeEventCount;
		assertEquals(eventCount, noteTrack1.getEventCount());

		Iterator<MidiEvent> it = noteTrack1.getEvents().iterator();

		ProgramChange program = (ProgramChange) it.next();
		assertEquals(track.getInstrument().getProgram(), program.getProgramNumber());

		int i = 0;
		int tick = 0;

		while (it.hasNext()) {
			NoteOn noteOn = (NoteOn) it.next();
			Symbol symbol = track.getSymbol(i);

			if (symbol instanceof Break) {
				tick += NoteLength.calculateDuration(symbol.getNoteLength());
				continue;
			}

			i++;
			Note note = (Note) symbol;

			assertEquals(note.getNoteName().getMidi(), noteOn.getNoteValue());
			assertEquals(tick, noteOn.getTick());

			NoteOn noteOff = (NoteOn) it.next();
			tick += NoteLength.calculateDuration(symbol.getNoteLength());
			assertEquals(tick, noteOff.getTick());
		}
	}

	private int getNoteOnEventCount(Track track) {
		int noteOnEventCount = 0;

		for (int i = 0; i < track.size(); i++) {
			Symbol symbol = track.getSymbol(i);

			if (symbol instanceof Note) {
				noteOnEventCount++;
			} else if (symbol instanceof Chord) {
				Chord chord = (Chord) symbol;

				for (int j = 0; j < chord.size(); j++) {
					noteOnEventCount++;
				}
			}
		}

		return noteOnEventCount;
	}

	public void testAddInstrumentAndGetChannel() throws MidiException {
		ProjectConverter converter = new ProjectConverter();

		int[] expectedChannels = { 1, 1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
		Instrument[] instruments = { Instrument.ACCORDION, Instrument.ACCORDION, Instrument.ACOUSTIC_BASS,
				Instrument.ACOUSTIC_BASS, Instrument.ACOUSTIC_GRAND_PIANO, Instrument.AGOGO_BELLS, Instrument.APPLAUSE,
				Instrument.BAGPIPE, Instrument.BANJO, Instrument.BASSOON, Instrument.BRASS_SECTION, Instrument.CELLO,
				Instrument.CHURCH_ORGAN, Instrument.CLARINET, Instrument.DISTORTION_GUITAR,
				Instrument.ELECTRIC_BASS_FINGER, Instrument.FX_1_RAIN, Instrument.FX_8_SCI_FI };

		for (int i = 0; i < instruments.length; i++) {
			int actualChannel = converter.addInstrumentAndGetChannel(instruments[i]);

			assertEquals(expectedChannels[i], actualChannel);
		}

		try {
			converter.addInstrumentAndGetChannel(Instrument.HARMONICA);
			assertTrue(false);
		} catch (MidiException e) {
		}
	}
}
