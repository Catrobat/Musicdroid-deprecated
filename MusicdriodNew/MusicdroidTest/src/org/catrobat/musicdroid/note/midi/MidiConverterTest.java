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

import junit.framework.TestCase;

import org.catrobat.musicdroid.note.Note;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.Track;

import java.io.IOException;

public class MidiConverterTest extends TestCase {

	public void testConvertAndWriteMidi() {
		Project project = new Project("TestMidi", 60);
		Track track1 = new Track();
		Track track2 = new Track();

		track1.addSymbol(new Note(NoteName.C4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.D4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.E4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.F4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.G4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.A4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.C4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.D4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.E4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.F4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.G4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.A4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.C4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.D4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.E4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.F4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.G4, NoteLength.QUARTER));
		track1.addSymbol(new Note(NoteName.A4, NoteLength.QUARTER));

		track2.addSymbol(new Note(NoteName.A3, NoteLength.WHOLE));
		track2.addSymbol(new Note(NoteName.B3, NoteLength.HALF));
		track2.addSymbol(new Note(NoteName.B3, NoteLength.HALF));
		track2.addSymbol(new Note(NoteName.C4, NoteLength.QUARTER));
		track2.addSymbol(new Note(NoteName.C4, NoteLength.QUARTER));
		track2.addSymbol(new Note(NoteName.C4, NoteLength.QUARTER));
		track2.addSymbol(new Note(NoteName.C4, NoteLength.QUARTER));
		track2.addSymbol(new Note(NoteName.D4, NoteLength.EIGHT));
		track2.addSymbol(new Note(NoteName.D4, NoteLength.EIGHT));
		track2.addSymbol(new Note(NoteName.D4, NoteLength.EIGHT));
		track2.addSymbol(new Note(NoteName.D4, NoteLength.EIGHT));
		track2.addSymbol(new Note(NoteName.D4, NoteLength.EIGHT));
		track2.addSymbol(new Note(NoteName.D4, NoteLength.EIGHT));
		track2.addSymbol(new Note(NoteName.D4, NoteLength.EIGHT));
		track2.addSymbol(new Note(NoteName.D4, NoteLength.EIGHT));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));
		track2.addSymbol(new Note(NoteName.E4, NoteLength.SIXTEENTH));

		project.addTrack(track1);
		project.addTrack(track2);

		try {
			MidiConverter.convertAndWriteMidi(project);
		} catch (IOException e) {
			assertTrue(false);
		}
	}
}
