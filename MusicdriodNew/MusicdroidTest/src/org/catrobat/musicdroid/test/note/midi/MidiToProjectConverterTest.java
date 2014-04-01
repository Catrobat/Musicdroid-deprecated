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
package org.catrobat.musicdroid.test.note.midi;

import android.test.AndroidTestCase;

import com.leff.midi.MidiFile;

import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.midi.MidiException;
import org.catrobat.musicdroid.note.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.test.note.midi.testutil.MidiFileTestDataFactory;
import org.catrobat.musicdroid.test.note.testutil.ProjectTestDataFactory;

public class MidiToProjectConverterTest extends AndroidTestCase {

	public void testConvertMidi() throws MidiException {
		ProjectToMidiConverter projectConverter = new ProjectToMidiConverter();
		MidiToProjectConverter midiConverter = new MidiToProjectConverter();
		Project expectedProject = ProjectTestDataFactory.createProjectWithSemiComplexTracks();
		MidiFile midi = projectConverter.convertProject(expectedProject);

		Project actualProject = midiConverter.convertMidi(midi, Project.DEFAULT_NAME);

		assertEquals(expectedProject, actualProject);
	}

	public void testReadFileAndConvertMidi() {
		assertTrue(MidiFileTestDataFactory.createAndReadMidiFile());
	}

	public void testValidateMidiFile1() {
		MidiToProjectConverter converter = new MidiToProjectConverter();
		MidiFile midiFile = MidiFileTestDataFactory.createMidiFile();

		try {
			converter.validateMidiFile(midiFile);
			fail();
		} catch (MidiException e) {
		}
	}

	public void testValidateMidiFile2() {
		MidiToProjectConverter converter = new MidiToProjectConverter();
		MidiFile midiFile = MidiFileTestDataFactory.createMidiFileWithEmptyTrack();

		try {
			converter.validateMidiFile(midiFile);
			fail();
		} catch (MidiException e) {
		}
	}

	public void testValidateMidiFile3() {
		MidiToProjectConverter converter = new MidiToProjectConverter();
		MidiFile midiFile = MidiFileTestDataFactory.createMidiFileWithTrackContainingTempoEvent();

		try {
			converter.validateMidiFile(midiFile);
			fail();
		} catch (MidiException e) {
		}
	}

	public void testValidateMidiFile4() {
		MidiToProjectConverter converter = new MidiToProjectConverter();
		MidiFile midiFile = MidiFileTestDataFactory.createMidiFileWithTrackContainingSomeTextEvent();

		try {
			converter.validateMidiFile(midiFile);
			fail();
		} catch (MidiException e) {
		}
	}
}