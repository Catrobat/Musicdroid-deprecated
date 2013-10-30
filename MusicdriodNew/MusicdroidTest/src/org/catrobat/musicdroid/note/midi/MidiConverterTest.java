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
import com.leff.midi.event.meta.Tempo;

import junit.framework.TestCase;

import org.catrobat.musicdroid.note.Instrument;
import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.Track;

public class MidiConverterTest extends TestCase {

	private static final String PROJECT_NAME = "TestMidi";

	public void testConvertMidi() throws MidiException {
		ProjectConverter projectConverter = new ProjectConverter();
		MidiConverter midiConverter = new MidiConverter();
		Project expectedProject = createProject();
		MidiFile midi = projectConverter.convertProject(expectedProject);

		Project actualProject = midiConverter.convertMidi(midi, PROJECT_NAME);

		assertEquals(expectedProject, actualProject);
	}

	private Project createProject() {
		Project project = new Project(PROJECT_NAME, 120);
		Track track1 = new Track(Instrument.GUNSHOT);
		Track track2 = new Track(Instrument.WHISTLE);

		track1.addNoteEvent(new NoteEvent(NoteName.C1, 0, true));
		track1.addNoteEvent(new NoteEvent(NoteName.C2, 0, true));
		track1.addNoteEvent(new NoteEvent(NoteName.C1, 64, false));
		track1.addNoteEvent(new NoteEvent(NoteName.C2, 128, false));

		track2.addNoteEvent(new NoteEvent(NoteName.C1, 0, true));
		track2.addNoteEvent(new NoteEvent(NoteName.C2, 0, true));
		track2.addNoteEvent(new NoteEvent(NoteName.C1, 64, false));
		track2.addNoteEvent(new NoteEvent(NoteName.C2, 128, false));

		project.addTrack(track1);
		project.addTrack(track2);

		return project;
	}

	public void testGetBeatsPerMinute1() throws MidiException {
		MidiConverter midiConverter = new MidiConverter();
		int expectedBeatsPerMinute = 100;
		MidiTrack tempoTrack = new MidiTrack();
		Tempo tempo = new Tempo();
		tempo.setBpm(expectedBeatsPerMinute);
		tempoTrack.insertEvent(tempo);

		int actualBeatsPerMinute = midiConverter.getBeatsPerMinute(tempoTrack);

		assertEquals(expectedBeatsPerMinute, actualBeatsPerMinute);
	}

	public void testGetBeatsPerMinute2() {
		MidiConverter midiConverter = new MidiConverter();
		MidiTrack tempoTrack = new MidiTrack();

		try {
			midiConverter.getBeatsPerMinute(tempoTrack);
			assertTrue(false);
		} catch (MidiException e) {
		}
	}
}